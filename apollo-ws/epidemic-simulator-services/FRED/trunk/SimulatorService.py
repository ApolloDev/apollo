#!/usr/users/4/stbrown/bin/python 
# Copyright 2012 University of Pittsburgh
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may not
# use this file except in compliance with the License.  You may obtain a copy of
# the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
# License for the specific language governing permissions and limitations under
# the License.
from SimulatorService_services import runRequest
'''
Created on Nov 27, 2012

This is an example GenericEpidemicModelService implementation.

@author: John Levander, Shawn Brown
'''

from ZSI.wstools import logging
from SimulatorService_services_server import SimulatorService
from ZSI.ServiceContainer import AsServer
from SimulatorService_services_types import *
from ApolloFactory import *
from ApolloUtils import *
from SimulatorService_services import *
import os,sys
import shutil
#import fred
import random
import datetime
import paramiko
from fredUtils import FredSSHConn,FredInputFileSet,infectiousPeriod,incubationPeriod

fredConn = FredSSHConn()

class FredWebService(SimulatorService):
        _wsdl = "".join(open("simulatorservice.wsdl").readlines())
        
        factory = ApolloFactory()
        utils = ApolloUtils()       
        
        def soap_getSupportedLocations(self, ps, **kw):
            response = SimulatorService.soap_getSupportedLocations(self, ps, **kw)
            
            spl = self.factory.new_SupportedPopulationLocation()
            
            spl._populationLocation = "42003"
            spl._populationLocationDescription = "Allegheny County"
            
            response._supportedPopluationLocations.append(spl)
            return response
        
        #this method checks on the status of a job, indicated by the jobs runId
        def soap_getRunStatus(self, ps, **kw):
            response = SimulatorService.soap_getRunStatus(self, ps, **kw)
            #get the run id of the job 
            request = ps.Parse(getRunStatusRequest.typecode)
            runId = request._runId
	    	   	    
            status, message = fredConn._getStatus(runId)
            response._runStatus = self.factory.new_RunStatus()
            print "Fred status is:" + status
            response._runStatus._status = status.lower()
            print "Fred message is:" + message
            response._runStatus._message = message
            
            return response

        #this method runs an epidemic model
        def soap_run(self, ps, **kw):
	    fredConn._setup()
            response = SimulatorService.soap_run(self, ps, **kw)
            request = ps.Parse(runRequest.typecode)
            #extract the epidemic model input object from the request
            print "received run request"
            cfg = request._simulatorConfiguration
            
            #log the EpidemicModelInput request to a log file
            self.utils.log_simulatorConfiguration(cfg)

	    ### get population file for fractions
	    fipsCountDict = {}
	    with open("2005_2009_ver2_pop_counts_by_fips.txt","rb") as f:
		for line in f:
		    fipsCountDict[line.split()[0]] = int(line.split()[1].strip())
	    
	    ### STB CREATE FRED INPUT FILE
	    ### do some error checking for this version of the model
	    if cfg._simulatorTimeSpecification._timeStepUnit.lower() != 'days':
		print ('This verison of FRED only supports time_step_unit of days')
		print ('Apollo sent %s'%(cfg._simulatorTimeSpecification._timeStepUnit))
	        #### Put in Error Handling, have to ask John L.
		response._runId = -401
		return response
	    if cfg._simulatorTimeSpecification._timeStepValue != 1:
		print ('This version of FRED only supports a time_step_value of 1')
		print ('Apollo sent %d'%(cfg._simulatorTimeSpecification._timeStepValue))
		response._runId = -402
		return response
		
	    lenLoc = len(cfg._populationInitialization._populationLocation)
	    print "lenLoc = " + str(lenLoc)
	    if lenLoc != 5 and lenLoc != 2:
		print ('This version of the FRED only supports 2 (state) or 5 (county) NSIDS codes')
		response._runId = -403
		return response

	    if cfg._populationInitialization._populationLocation not in fipsCountDict.keys():
		print ('The location specified does not have a total population')
		response._runId = -406
		return response

            ### Make a random directory name so that multiple calls can be made
            randID = random.randint(0,100000)
            tempDirName = "fred.tmp.%d"%(randID)

            try:
                    os.mkdir(tempDirName)
            except:
                    print("Cannot make temporary FRED directory %s"%(tempDirName))
		    response._runId = -404
		    return response

	    fredInput = FredInputFileSet(cfg,tempDirName,fipsCountDict,self)
	    fredInput.createParam()
	    fredInput.parseControlMeasures()
	    
	    os.chdir(tempDirName)
            with open('starttime','wb') as f:
		    f.write('Nothing')

	    idPrefix = cfg._simulatorIdentification._softwareDeveloper +\
		       "_" + cfg._simulatorIdentification._softwareName +\
		       "_" + cfg._simulatorIdentification._softwareVersion + "_"
	    
	    fredConn.writeRunScript(idPrefix)
            os.chdir('../')
            fredConn._setup()
            fredConn._connect()
            fredConn._mkdir(tempDirName)
	    for fileNames in fredInput.fileList:
		fredConn._sendFile(tempDirName+'/'+fileNames)
            fredConn._sendFile(tempDirName+ '/' + fredConn.runScriptName)

            fredConn._sendFile(tempDirName+'/starttime')
		    
	    ### we do not need temp directory anymore
	    shutil.rmtree(tempDirName)
	
            print "Running FRED"
            sys.stdout.flush()

            #returnVal = fredConn._submitPBSJob(randID)
	    returnVal = fredConn._submitJob(randID)
            print returnVal 

	    ### parse the run ID
	    response._runId = idPrefix + str(returnVal)
            
            #ssh.close()
            #sftp.close()
            
            return response
        
#logger = logging.getLogger("");
#logger.setLevel(0)

#run a webserver on 8087
AsServer(port=8087, services=[FredWebService('fred'), ])


#!/usr/users/4/stbrown/bin/python
# Copyright 2013 University of Pittsburgh
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
'''
Created on Feb 13, 2013

@author: John Levander and Shawn Brown
'''

from VisualizerService_services_server import VisualizerService
from ZSI.ServiceContainer import AsServer
from ApolloFactory import *
from apollo import ApolloSimulatorOutput
from gaia import GAIA,ConfInfo,PlotInfo,Constants,computeBoundaries,computeColors,FIPSToUSFips
import random
import time
import shutil
import os,sys
from threading import Thread

class GaiaWebServiceConfiguration:
    def __init__(self,configFile_=None):
	self.statusDirectory = "./"
	self.tempDirectory = "./"
	self.outputDirectory = "./"
	self.port = 8087
	confDict = {}
	if configFile_ is not None:
	    with open(configFile_,"rb") as f:
		for line in f:
		    confDict[line.split(":")[0]] = line.split(":")[1].strip()
	    if confDict.has_key('status_directory'):
		self.statusDirectory = confDict['status_directory']
	    if confDict.has_key('temp_directory'):
		self.tempDirectory = confDict['temp_directory']
	    if confDict.has_key('output_directory'):
		self.outputDirectory = confDict['output_directory']
	    if confDict.has_key('server_port'):
		self.port = int(confDict['server_port'])

gaiaConf = GaiaWebServiceConfiguration("./gaiaWS.conf")

def ApolloToGaia(vc,gaiaFileName,gaiaOutputFileName,gaiaStyleFileName,statusFile):
	time1 = time.time()
	apolloSimOutput = ApolloSimulatorOutput(vc._visualizationOptions._runId)
	print "Got sim output"
	timeSeriesOutput = \
			 apolloSimOutput. getNewlyInfectedTimeSeriesForBlocks()
	time2 = time.time()
	print "Time to get database : %10.5f seconds"%(time2-time1)
	maxValue = -99999
        for pop in timeSeriesOutput.keys():
           thisMax = max(timeSeriesOutput[pop])
           if thisMax > maxValue: maxValue = thisMax

        colors = computeColors("255.255.0.0","255.0.0.255",6)
        borderPercents = [0.0,0.05,0.10,0.20,0.40,0.60,1.1]
        with open(gaiaStyleFileName,"wb") as f:
            f.write("id=1\n")
	    f.write("legend-support=1\n")
            for i in range(0,len(borderPercents)-1):
                f.write("%s 1.0 %d %d\n"%(colors[i],int(borderPercents[i]*maxValue+1),
                                          int(borderPercents[i+1]*maxValue)))

        maxTCount = 0
	countyList = []
        with open(gaiaFileName,"wb") as f:
            for pop in timeSeriesOutput.keys():
		##st = str(pop)[0:2]
##                ct = str(pop)[2:5]
##		if (st,ct) not in countyList:
##		    countyList.append((st,ct))
##                tr = str(pop)[5:11]
##                if tr[4:] == "00":
##                    tr = tr[:4]
##                bg = str(pop)[11:12]
		
		if str(pop)[0:5] not in countyList:
		    countList.append(str(pop)[0:5])
		    
		fipsString = FIPSToUSFips(str(pop))
                tcount = 1
                for count in timeSeriesOutput[pop]:
		    ### THIS IS A HACK because FRED at the moment
		    ### IS overcompensating on Day 0
		    if tcount == 1:
			if count != 0:
				f.write("USFIPS %s %d %d:1\n"\
                               	 	%(fipsString,1,tcount))
                    elif count != 0:
                        f.write("USFIPS %s %d %d:1\n"\
                                %(fipsString,count,tcount))
                    tcount += 1

                if tcount > maxTCount: maxTCount = tcount

            for i in range(0,maxTCount):    #if len(pop) > 12:
                #    print "BigPop: " + pop

		for stct in countyList:
		    f.write("USFIPS st%s.ct%s.tr*.bl* -1 %d:-1\n"%(st[0:2],ct[2:5],i))

        plotInfo = PlotInfo(input_filename_ = gaiaFileName,
                            output_filename_ = gaiaOutputFileName,
                            styles_filenames_ = [gaiaStyleFileName],
                            output_format_="gif",
                            bundle_format_="ogg",
                            max_resolution_ = 1000,
			    legend_="Number of Incident Infections",
			    legend_font_size_ = 16.0)

        gaia = GAIA(plotInfo)
        print "Calling GAIA"
        sys.stdout.flush()
	try:
        	gaia.call()
	except:
		with open(statusFile,"wb") as f:
			f.write("GaiaFailed")	
		return
	print "Finished GAIA"
	sys.stdout.flush()
	gaiaOutWDirFileName = gaiaConf.outputDirectory + "/" + gaiaOutputFileName
        shutil.move(gaiaOutputFileName+".ogg",gaiaOutWDirFileName+".ogg")
	print "setting to Complete"
	sys.stdout.flush()
	with open(statusFile,"wb") as f:
		f.write("Completed")


class GaiaWebService(VisualizerService):
    
        factory = ApolloFactory()
	
	
	def soap_run(self, ps, **kw):
	    response = VisualizerService.soap_run(self, ps, **kw)
	    
	    vc = self.request._visualizerConfiguration;
           
	    # use the below two lines if you have an authentication system
	    # vc._authenticaton._requesterId
	    # vc._authenticaton._requesterPassword
	    
	    vId = vc._visualizerIdentification
	    #print gaiaConf.statusDirectory
            #response._runId = vId._visualizerDeveloper + "_" + vId._visualizerName + "_" + vId._visualizerVersion + "_42"
	   
            ### We will find all instances of newly infected counts in the database that match the location at the beginning of the string
	   
	    ## Make connection to Apollo Database
	    #apolloDB = ApolloDB()
	    #apolloDB.connect()
	    timeStamp = int(time.time()) 
	    statusFile = gaiaConf.statusDirectory + "/" +vc._visualizationOptions._runId + ".status"
	    with open(statusFile,"wb") as f:
		f.write("Running")
	    
            gaiaFileName = "%s/gaia.input.%d.txt"%(gaiaConf.tempDirectory,timeStamp)
            gaiaStyleFileName = "%s/gaia.style.%d.txt"%(gaiaConf.tempDirectory,timeStamp)
            gaiaOutputFileName = "gaia.output.%d"%timeStamp
            gaiaOutWDirFileName =  "%s/%s"%(gaiaConf.outputDirectory,gaiaOutputFileName)
            print "Gaia Style File NAme = " + gaiaStyleFileName
	    t = Thread(target=ApolloToGaia,args=(vc,gaiaFileName,gaiaOutputFileName,
						 gaiaStyleFileName,statusFile))
            t.start()
	   
	    print "The client requests a visualization of the following runId: "\
		  + vc._visualizationOptions._runId
	    print "The client requests a visualization of the following location: "\
		  + vc._visualizationOptions._location
	    print "The client requests the following output format: "\
		  + vc._visualizationOptions._outputFormat
           
	    #old stuff, pre 1.1
	    #response._runId = vc._visualizationOptions._runId
	    #response._visualizerOutputResource.append(vrr);
	    
	    response._visualizerResult = self.factory.new_VisualizerResult()
	    response._visualizerResult._runId = vc._visualizationOptions._runId
	    response._visualizerResult._visualizerOutputResource = []
	    outputResource = self.factory.new_UrlOutputResource_Def()
	    
	    outputResource._description = "GAIA animation"
	    outputResource._URL = "http://warhol-fred.psc.edu/GAIA/"+gaiaOutputFileName + ".ogg" 
	    response._visualizerResult._visualizerOutputResource.append(outputResource)
        
	    return response;
        
	def soap_getRunStatus(self, ps, **kw):
	    response = VisualizerService.soap_getRunStatus(self, ps, **kw)
            
            print "The client requests the status of run " + self.request._runId
            statusFile = gaiaConf.statusDirectory + "/" +self.request._runId + ".status"
	    with open(statusFile,"rb") as f:
		status = f.readline()
 	    response._runStatus = self.factory.new_RunStatus()
	    print "Checking File " + statusFile
	    print "Got Status " + status		
	    if status == "Running":
		response._runStatus._status = "running"
		response._runStatus._message = "GAIA Webservice is creating the visualization"
	    elif status == "GaiaFailed":
		response._runStatus._status = "failed"
		response._runStatus._message = "GAIA Server has returned a failed response"
	    elif status == "Completed":
		response._runStatus._status = "completed"
		response._runStatus._message = "GAIA Webservice has finished creating the visualization"
       #     response._runStatus = self.factory.new_RunStatus()
       #     response._runStatus._status = "WAITING"
       #     response._runStatus._message = "method not implemented"
            return response
        
#run a webserver on 8087
AsServer(port=gaiaConf.port, services=[GaiaWebService('gaia'), ])

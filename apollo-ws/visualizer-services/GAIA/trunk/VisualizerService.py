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

@author: John Levander
'''

from VisualizerService_services_server import VisualizerService
from ZSI.ServiceContainer import AsServer
from ApolloFactory import *
from apollo import ApolloSimulatorOutput
from gaia import GAIA,ConfInfo,PlotInfo,Constants,computeBoundaries,computeColors
import random
import time
import shutil
import os,sys
from threading import Thread

def ApolloToGaia(vc,gaiaFileName,gaiaOutputFileName,gaiaStyleFileName,statusFile):
	time1 = time.time()
	apolloSimOutput = ApolloSimulatorOutput(vc._visualizationOptions._runId)
	print "Got sim output"
	timeSeriesOutput = \
               apolloSimOutput.getNewlyInfectedTimeSeriesWithinLocation(vc._visualizationOptions._location)
	time2 = time.time()
	print "Time to get database : %10.5f seconds"%(time2-time1)
           ### Create an input file for GAIA
        os.chdir("/scratch/stbrown")
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
        with open(gaiaFileName,"wb") as f:
            #f.write("USFIPS st42.ct003.tr*.bl* -1:-1\n")
            for pop in timeSeriesOutput.keys():
                #if len(pop) > 12:
                #    print "BigPop: " + pop
                st = str(pop)[0:2]
                ct = str(pop)[2:5]
                tr = str(pop)[5:11]
                if tr[4:] == "00":
                    tr = tr[:4]
                bg = str(pop)[11:12]
                #print str(pop) +": " +st + "." + ct + "." + tr + "." + bg
                #for count in timeSeriesOutput[pop]:
                tcount = 1
                for count in timeSeriesOutput[pop]:
		    ### THIS IS A HACK because FRED at the moment
		    ### IS overcompensating on Day 0
		    if tcount == 1:
			if count != 0:
				f.write("USFIPS st%s.ct%s.tr%s.bl%s %d %d:1\n"\
                               	 	%(st,ct,tr,bg,1,tcount))
                    elif count != 0:
                        f.write("USFIPS st%s.ct%s.tr%s.bl%s %d %d:1\n"\
                                %(st,ct,tr,bg,count,tcount))
                    tcount += 1

                if tcount > maxTCount: maxTCount = tcount

            for i in range(0,maxTCount):
                f.write("USFIPS st42.ct003.tr*.bl* -1 %d:-1\n"%i)

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
	gaiaOutWDirFileName = "/home/4/stbrown/GAIA/" + gaiaOutputFileName
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
            #response._runId = vId._visualizerDeveloper + "_" + vId._visualizerName + "_" + vId._visualizerVersion + "_42"
	   
            ### We will find all instances of newly infected counts in the database that match the location at the beginning of the string
	   
	    ## Make connection to Apollo Database
	    #apolloDB = ApolloDB()
	    #apolloDB.connect()
	    timeStamp = int(time.time())
	    statusFile = "/scratch/stbrown/"+vc._visualizationOptions._runId + ".status"
	    with open(statusFile,"wb") as f:
		f.write("Running")
            gaiaFileName = "gaia.input.%d.txt"%timeStamp
            gaiaStyleFileName = "gaia.style.%d.txt"%timeStamp
            gaiaOutputFileName = "gaia.output.%d"%timeStamp
            gaiaOutWDirFileName = "/home/4/stbrown/GAIA/" + gaiaOutputFileName
            print "Gaia Style File NAme = " + gaiaStyleFileName
	    t = Thread(target=ApolloToGaia,args=(vc,gaiaFileName,gaiaOutputFileName,gaiaStyleFileName,statusFile))
            t.start()
	   
	    print "The client requests a visualization of the following runId: " + vc._visualizationOptions._runId
	    print "The client requests a visualization of the following location: " + vc._visualizationOptions._location
	    print "The client requests the following output format: " + vc._visualizationOptions._outputFormat
           
	    #old stuff, pre 1.1
	    #response._runId = vc._visualizationOptions._runId
	    #response._visualizerOutputResource.append(vrr);
	    
	    response._visualizerResult = self.factory.new_VisualizerResult()
	    response._visualizerResult._runId = vc._visualizationOptions._runId
	    response._visualizerResult._visualizerOutputResource = []
	    outputResource = self.factory.new_UrlOutputResource_Def()
	    outputResource._description = "GAIA animation of Allegheny County"
	    outputResource._URL = "http://warhol-fred.psc.edu/GAIA/"+gaiaOutputFileName + ".ogg" 
	    response._visualizerResult._visualizerOutputResource.append(outputResource)
        
	    return response;
        
	def soap_getRunStatus(self, ps, **kw):
	    response = VisualizerService.soap_getRunStatus(self, ps, **kw)
            
            print "The client requests the status of run " + self.request._runId
            statusFile = "/scratch/stbrown/"+self.request._runId + ".status"
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
AsServer(port=8088, services=[GaiaWebService('gaia'), ])

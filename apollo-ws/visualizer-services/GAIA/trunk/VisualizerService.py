#!/usr/bin/env python
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

from VisualizerService_v2_0_2_server import VisualizerService_v2_0_2
from ZSI.ServiceContainer import AsServer
from ApolloFactory import *
import visWS
import paths
from apollo import ApolloDB
from gaia import GAIA,ConfInfo,PlotInfo,Constants,computeBoundaries,computeColors
import random
import time
import shutil
import os,sys
import hashlib
from threading import Thread
from logger import Log,ThreadLog

def ApolloToGaia(vizRunId,simulationRunId,simulationTitle,gaiaFileName,gaiaOutputFileName,
                 gaiaStyleFileName,gaiaURL,statusFile,logger,apollodb):
    
    try:
        sourceId = apollodb.getSimulatorIdFromRunId(simulationRunId)
        fileContents = apollodb.getRunDataContentFromRunIdAndLabel(simulationRunId_=simulationRunId,
                                                                   label_='newly_exposed.txt',
                                                                   source_software_=sourceId,
								   destination_software_=5)
    except Exception as e:
        logger.update('DB_ERROR',str(e))
	apollodb.setRunStatus(vizRunId,'failed',str(e))
        raise e
    
    logger.update('DB_SUCCESS')
    
    try:
        ### Get the maximum value from the input file    
        maxValue = -99999
        maxTime = -99999
        countyList = []
        for line in fileContents.split("\n"):
            lineSplit = line.split()
            if len(lineSplit) > 1:
                value = float(lineSplit[1])
                day = int(lineSplit[2].split(":")[0])
                              
                if value > maxValue: maxValue = value
                if day > maxTime: maxTime = day
                
                if lineSplit[0][2:7] not in countyList:
                    countyList.append(lineSplit[0][2:7])

        
        colors = computeColors("255.255.0.0","255.0.0.255",6)
        borderPercents = [0.0,0.05,0.10,0.20,0.40,0.60,1.1]
        with open(gaiaStyleFileName,"wb") as f:
            f.write("id=1\n")
            f.write("legend-support=1\n")
	    f.write("0.255.255.255 1.0 0 0\n")
            for i in range(0,len(borderPercents)-1):
                f.write("%s 1.0 %d %d\n"%(colors[i],int(borderPercents[i]*maxValue+1),
                                              int(borderPercents[i+1]*maxValue)))

        with open(gaiaFileName,"wb") as f:
            f.write(fileContents)
            for i in range(0,maxTime):
                for ct in countyList:
                    f.write("USFIPS st%s.ct%s.tr*.bl* -1 %d:-1\n"%(ct[0:2],ct[2:5],i))
                

        plotInfo = PlotInfo(input_filename_ = gaiaFileName,
                            output_filename_ = gaiaOutputFileName,
                            styles_filenames_ = [gaiaStyleFileName],
                            output_format_="gif",
                            bundle_format_="ogg",
                            max_resolution_ = 1000,
                            legend_="Incidence",
                            legend_font_size_ = 16.0,
			    title_=str(simulationTitle))
    except Exception,e:
        logger.update('GAIA_FILE_CREATION_FAILED',message=str(e))
	apollodb.setRunStatus(vizRunId,'failed',str(e))
	raise e
	
    gaia = GAIA(plotInfo)           
    logger.update('GAIA_FILE_CREATION_COMPLETE')

    logger.update('CALLING_GAIA')
    try:
        gaia.call()
    except Exception,e:
        logger.update('GAIA_FAILED',message=str(e))
	apollodb.setRunStatus(vizRunId,'failed',str(e))
        return
	
    logger.update('GAIA_SUCCESS')

    try:
        gaiaOutWDirFileName = visWS.configuration['local']['outputDirectory'] + "/" + gaiaOutputFileName
        shutil.move(gaiaOutputFileName+".ogg",gaiaOutWDirFileName+".ogg")
        
	### put the url in the database
	m = hashlib.md5()
	m.update(gaiaURL)
        m5hash = m.hexdigest()
	SQLString = 'INSERT INTO run_data_content(text_content, md5_hash_of_content)'\
		    ' values ("%s","%s")'%(gaiaURL,m5hash)
        apollodb.query(SQLString)
        runDataContentId = apollodb.insertID()
        runDataDescriptionId = apollodb.getRunDataDescriptionId(label_="movie.ogg",
	                                                        source_software_=5,
                                                                destination_software_=0,
                                                                format_="URL",
								type_="MOVIE")

	SQLString = "INSERT INTO run_data(run_Id,description_id,content_id) "\
            + "values ('%s','%d','%d')"%(vizRunId,runDataDescriptionId,runDataContentId)
        apollodb.query(SQLString)
	logger.update("DB_SUCCESS",message="Content updated")
    except Exception,e:
        logger.update('FILE_MOVE_FAILED',message=str(e))
	apollodb.setRunStatus(vizRunId,'failed',str(e))
	return
    logger.update("COMPLETED")
    apollodb.setRunStatus(vizRunId,'completed','Visualization Completed')

class GaiaWebService(VisualizerService_v2_0_2):
    _wsdl = "".join(open(visWS.configuration['local']['wsdlFile']).readlines()) 
    factory = ApolloFactory()

    LogDict = {}
    LogDict["GlobalLogger"] = Log(visWS.configuration['local']['logFile'])
    LogDict["GlobalLogger"].start()

    vizRunId = None
    def soap_runVisualization(self, ps, **kw):
 	try:
            apolloDB = ApolloDB(dbname_=visWS.configuration['local']['apolloDBName'])
            apolloDB.connect()
            response = VisualizerService_v2_0_2.soap_runVisualization(self, ps, **kw)
            vc = response[0]._runVisualizationMessage;
            vizRunId = response[0]._visualizationRunId
            forLogMoniker = vizRunId
            simulationRunValue = vc._simulationRunIds[0]
            simulationRunId = simulationRunValue._runIdentification
            simulationTitle = simulationRunValue._runLabel	
            
	except Exception as e:
            print str(e)
            
            if vizRunId is not None:
                apolloDB.setRunStatus(visRunId,'failed',str(e))
            raise e
        try:
        	### Check to see if this request is already running, and if so,
            apolloDB.setRunStatus(vizRunId,'staging','VisualizerService is translating the input information for visualization')
            if self.LogDict.has_key(forLogMoniker):
                if self.LogDict[forLogMoniker].pollStatusType() != 'RUNNING':
                    pass
                else:
                    previousStatus = self.LogDict[forLogMoniker].pollStatus()
                    self.LogDict[forLogMoniker].update('DUPLICATE_ID')
                    self.LogDict[forLogMoniker].update(previousStatus[0])
	except Exception as e:
            print str(e)
            apolloDB.setRunStatus(vizRunId,'failed',str(e))
            self.LogDict[forLogMoniker].update('FAILED')
            raise e
	try:
            self.LogDict[forLogMoniker] = ThreadLog(visWS.configuration['local']['logFile'],id_=forLogMoniker)
            self.LogDict[forLogMoniker].update(status="CALLRECV")
	except Exception as e:
            print str(e)
            self.LogDict[forLogMoniker].update('FAILED')
            apolloDB.setRunStatus(vizRunId,'failed',str(e))
            raise e
        
	try:
            timeStamp = int(time.time())
            vId = vc._visualizerIdentification
            statusFile = "%s/%s.status"%(visWS.configuration['local']['statusDirectory'],str(vizRunId))
            
            gaiaFileName = "%s/gaia.input.%d.txt"%(visWS.configuration['local']['tempDirectory'],timeStamp)
            gaiaStyleFileName = "%s/gaia.style.%d.txt"%(visWS.configuration['local']['tempDirectory'],timeStamp)
            gaiaOutputFileName = "gaia.output.%d"%timeStamp
            gaiaOutWDirFileName =  "%s/%s"%(visWS.configuration['local']['outputDirectory'],gaiaOutputFileName)
            gaiaURL = "%s/%s.ogg"%(visWS.configuration['local']['URL'],gaiaOutputFileName)
            apolloDB.setRunStatus(vizRunId,'running','Visualization has been subitted to GAIA for processing')
            t = Thread(target=ApolloToGaia,args=(vizRunId,simulationRunId,simulationTitle,
                                                 gaiaFileName,gaiaOutputFileName,
                                                 gaiaStyleFileName,gaiaURL,
                                                 statusFile,self.LogDict[forLogMoniker],
                                                 apolloDB))
            t.start()
            print "The client requests a visualization of the following runId: "\
                + str(vizRunId)

	except Exception as e:
            apolloDB.setRunStatus(vizRunId,'failed',str(e))
            self.LogDict[forLogMoniker].update('FAILED')
            print str(e)
            raise e

        return response;

##run a webserver on 8087
AsServer(port=visWS.configuration['local']['serverPort'], services=[GaiaWebService('gaia'), ])

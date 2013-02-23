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

@author: John Levander
'''

from ZSI.wstools import logging
from SimulatorService_services_server import SimulatorService
from ZSI.ServiceContainer import AsServer
from ApolloFactory import *
from ApolloUtils import *
from SimulatorService_services import *


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
	
            response._runStatus = self.factory.new_RunStatus()
            response._runStatus._status = 'RUNNING'
            response._runStatus._message = 'the run is currently running'
            
            return response
        
        
        #this method runs an epidemic simulator
        def soap_run(self, ps, **kw):
            response = SimulatorService.soap_run(self, ps, **kw)
            request = ps.Parse(runRequest.typecode)
            print "received run request"
            #extract the epidemic model input object from the request            
            cfg = request._simulatorConfiguration
            #example to get the number of time steps in the simulation
            #run_length = cfg._simulatorTimeSpecification._runLength
            '''ACME code here'''
            response._runId = "1"
            return response
        
#logger = logging.getLogger("");
#logger.setLevel(0)

#run a webserver on 8087
AsServer(port=8087, services=[FredWebService('acme'), ])


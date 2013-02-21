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

class SkeletonVisualizerWebService(VisualizerService):
    
    factory = ApolloFactory()
    
    def soap_run(self, ps, **kw):
        response = VisualizerService.soap_run(self, ps, **kw)
        vc = self.request._visualizerConfiguration;
           
            # use the below two lines if you have an authentication system
            # vc._authenticaton._requesterId
	    # vc._authenticaton._requesterPassword
	   
        #response._runId = vId._visualizerDeveloper + "_" + vId._visualizerName + "_" + vId._visualizerVersion + "_42"
	   	   
        print "The client requests a visualization of the following runId: " + vc._visualizationOptions._runId
        print "The client requests a visualization of the following location: " + vc._visualizationOptions._location
        print "The client requests the following output format: " + vc._visualizationOptions._outputFormat
           
        vrr = self.factory.new_VisualizerOutputResource()
        vrr._description = "Description of the visualization"
        vrr._URL = "http://www.example.com/some_movie.mp4"
        response._runId = vc._visualizationOptions._runId
        response._visualizerOutputResource.append(vrr);
        return response;
        
    def soap_getRunStatus(self, ps, **kw):
        response = VisualizerService.soap_getRunStatus(self, ps, **kw)

        print "The client requests the status of run " + self.request._runId

        response._runStatus = self.factory.new_RunStatus()
        response._runStatus._status = "WAITING"
        response._runStatus._message = "method not implemented"
        return response
        
#run a webserver on 8087
AsServer(port=8087, services=[SkeletonVisualizerWebService('visualizerservice'), ])

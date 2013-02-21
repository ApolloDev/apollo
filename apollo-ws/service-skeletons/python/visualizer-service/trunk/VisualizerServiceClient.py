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

from VisualizerService_services import *
#from SimulatorService_services_types import *
from ApolloFactory import *
import time

#create the service object
service = VisualizerServiceLocator().getVisualizerServiceEI("http://127.0.0.1:8087/gaia")

#create an epidemic model input object
factory = ApolloFactory()

runRequest = runRequest()


runRequest._visualizerConfiguration = factory.new_VisualizerConfiguration()

runRequest._visualizerConfiguration._authentication._requesterId = "fake"
runRequest._visualizerConfiguration._authentication._requesterPassword = "fake"

runRequest._visualizerConfiguration._visualizerIdentification._visualizerDeveloper = "PSC"
runRequest._visualizerConfiguration._visualizerIdentification._visualizerName = "GAIA"
runRequest._visualizerConfiguration._visualizerIdentification._visualizerVersion = "v1.0"

runRequest._visualizerConfiguration._visualizationOptions._runId = "UPitt,PSC,CMU_FRED_2.0.1_231023"
runRequest._visualizerConfiguration._visualizationOptions._location = "42003"
runRequest._visualizerConfiguration._visualizationOptions._outputFormat = "mp4"

print 'Calling "run"'
run_response = service.run(runRequest)

print "Webservice claims that the following resources will be available:"
for o in run_response._visualizerOutputResource:
    print "--" + o._description + " at " + o._URL

print "Run submitted with ID: " + str(run_response._runId)

get_run_status_request = getRunStatusRequest()
get_run_status_request._runId = run_response._runId
run_status_response = service.getRunStatus(get_run_status_request)

print '\nCalling "getRunStatus"'
print "Status Code: " +  run_status_response._runStatus._status + " Status Message: " + run_status_response._runStatus._message




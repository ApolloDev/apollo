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

'''
Created on Nov 27, 2012

This program sends a request to run an epidemic model to a
SimulatorService (implemented in SimulatorService.py).
As a result, the program prints the run id of the submitted job.
This program is for example purposes only. 

This can be thought of as a unit test for the webservice

@author: John Levander, Shawn Brown
'''
from SimulatorService_services import *
#from SimulatorService_services_types import *
from ApolloFactory import *
from fredConstants import _Const
from newObjectExample import *
import time,sys

#create the service object
service = SimulatorServiceLocator().getSimulatorServiceEI("http://127.0.0.1:8087/fred")

#create an epidemic model input object
factory = ApolloFactory()

#Not sure what these do, but I am pretty sure they are unnecessary
gslRequest = getSupportedLocationsRequest()
gslResponse = service.getSupportedLocations(gslRequest)

newObj = newObjectsExample()
em_input = newObj.noControlMeasures("42003")
em_input_fixedVacc = newObj.addFixedStartTimeVaccinationControlMeasure(\
    newObj.noControlMeasures("42003"), "ACIP")
em_input_fixedSchool = newObj.addFixedStartTimeAllSchoolClosureControlMeasure(newObj.noControlMeasures("42003"))
em_input_reactSchool = newObj.addReactiveAllSchoolClosureControlMeasure(newObj.noControlMeasures("42003"))
em_input_treatmentAV = newObj.addFixedStartTimeAntiviralControlMeasure(newObj.noControlMeasures("42003"))

#create a run request object
run_request = runRequest()
#the run request object has a single member variable, which is set to the epidemic model input object
run_request._simulatorConfiguration = em_input_fixedSchool

#print str(em_input_treatmentAV._controlMeasures._fixedStartTimeControlMeasures[0]._controlMeasure._antiviralTreatment._efficacyValues)
#batch_request = batchRunRequest()
#A = batch_request._batchRunSimulatorConfiguration
#batch_request._batchSimulatorConfiguration = btch_input
#print dir(batch_request)

#print 'Calling "run"'
###submit the request, receive the response

run_response = service.run(run_request)

print "Run submitted with ID: " + str(run_response._runId)
 
get_run_status_request = getRunStatusRequest()
get_run_status_request._runId = run_response._runId
run_status_response = service.getRunStatus(get_run_status_request)

while run_status_response._runStatus._status != "completed":
    get_run_status_request = getRunStatusRequest()
    get_run_status_request._runId = run_response._runId
    run_status_response = service.getRunStatus(get_run_status_request)

    print '\nCalling "getRunStatus"'
    print "Status Code: " +  run_status_response._runStatus._status + " Status Message: " + run_status_response._runStatus._message
    time.sleep(5)





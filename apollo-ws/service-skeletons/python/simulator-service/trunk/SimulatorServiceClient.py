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
import time

#create the service object
service = SimulatorServiceLocator().getSimulatorServiceEI("http://warhol-fred.psc.edu:8087/fred")

#create an epidemic model input object
factory = ApolloFactory()

gslRequest = getSupportedLocationsRequest()
gslResponse = service.getSupportedLocations(gslRequest)

print "This webservice supports the following locations:"
for d in gslResponse._supportedPopluationLocations:
    print "\tLocation:" + d._populationLocation
    print "\tDescription:" + d._populationLocationDescription



em_input = factory.new_SimulatorConfiguration()

#populate the member variables
em_input._authentication._requesterId = "Example User"
em_input._authentication._requesterPassword = "Example Password"

em_input._simulatorIdentification._simulatorDeveloper = "UPitt,PSC,CMU"
em_input._simulatorIdentification._simulatorName = "FRED"
em_input._simulatorIdentification._simulatorVersion = "2.0.1"

em_input._disease._diseaseName = "Influenza"
em_input._disease._infectiousPeriod = 3.2
em_input._disease._latentPeriod = 2.0
em_input._disease._reproductionNumber = 1.7
em_input._disease._asymptomaticInfectionFraction = 0.5
#em_input._disease._simulatedPopulation = ['susceptible', 'exposed', 'infectious', 'recovered']
#em_input._disease._pop_count = [1157474.0, 0.0, 100.0, 60920.0]

em_input._simulatorTimeSpecification._timeStepUnit = "days"
em_input._simulatorTimeSpecification._timeStepValue = 1
em_input._simulatorTimeSpecification._runLength = 150
#em_input._simulatorTimeSpecification._pop_count = 1218494
#em_input._simulatorTimeSpecification._disease = "influenza"
#em_input._simulatorTimeSpecification._population_location = "Allegheny County"

em_input._populationInitialization._populationLocation = "Allegheny County"
pds_s = factory.new_PopulationDiseaseState();
pds_s._diseaseState = 'susceptible'
pds_s._popCount = 1157474
pds_e = factory.new_PopulationDiseaseState();
pds_e._diseaseState = 'exposed'
pds_e._popCount = 0
pds_i = factory.new_PopulationDiseaseState();
pds_i._diseaseState ='infectious'
pds_i._popCount = 100
pds_r = factory.new_PopulationDiseaseState();
pds_r._diseaseState ='recovered'
pds_r._popCount = 60920
pds = em_input._populationInitialization._populationDiseaseState
pds.append(pds_r)
pds.append(pds_i)
pds.append(pds_e)
pds.append(pds_s)

em_input._vaccinationControlMeasure._vaccineCmCompliance = 0.60
em_input._vaccinationControlMeasure._vaccineEfficacy= 0.8
em_input._vaccinationControlMeasure._vaccineEfficacyDelay = 14.0
em_input._vaccinationControlMeasure._vaccineSupplySchedule = [100000.0] * 365
em_input._vaccinationControlMeasure._vaccinationAdminSchedule = [200000.0] * 365

em_input._antiviralControlMeasure._antiviralCmCompliance = 1.0
em_input._antiviralControlMeasure._antiviralEfficacy= 1.0
em_input._antiviralControlMeasure._antiviralEfficacyDelay = 0.0
em_input._antiviralControlMeasure._antiviralSupplySchedule= [0.0] * 365
em_input._antiviralControlMeasure._antiviralAdminSchedule = [0.0] * 365

#create a run request object
run_request = runRequest()
#the run request object has a single member variable, which is set to the epidemic model input object
run_request._simulatorConfiguration = em_input

print 'Calling "run"'
#submit the request, receive the response

run_response = service.run(run_request)

print "Run submitted with ID: " + str(run_response._runId)

get_run_status_request = getRunStatusRequest()
get_run_status_request._runId = run_response._runId
run_status_response = service.getRunStatus(get_run_status_request)

while run_status_response._runStatus._status != "COMPLETED":
    get_run_status_request = getRunStatusRequest()
    get_run_status_request._runId = run_response._runId
    run_status_response = service.getRunStatus(get_run_status_request)

    print '\nCalling "getRunStatus"'
    print "Status Code: " +  run_status_response._runStatus._status + " Status Message: " + run_status_response._runStatus._message
    time.sleep(30)





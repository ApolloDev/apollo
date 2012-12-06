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
GenericEpidemicModelService (implemented in EpiModelService.py).
As a result, the program prints the run id of the submitted job.

This program is for example purposes only. 

@author: John Levander
'''
from EpidemicSimulatorService_client import *
from EpidemicSimulatorService_types import *
from ApolloFactory import *

#create the service object
service = EpidemicSimulatorServiceLocator().getEpidemicSimulatorPort("http://localhost:8087/GenericEpidemicModelService/services/EpidemicSimulatorPort")

#create an epidemic model input object
factory = ApolloFactory()
em_input = factory.new_epidemic_model_input()

#populate the member variables
em_input._authentication._requester_id = "Example User"
em_input._authentication._requester_password = "Example Password"

em_input._simulator_identification._simulator_developer = "University of Pittsburgh"
em_input._simulator_identification._simulator_name = "Simple Epidemic Model"
em_input._simulator_identification._simulator_version = "1.0"

em_input._disease_dynamics._infectious_period = 3.2
em_input._disease_dynamics._latent_period = 2.0
em_input._disease_dynamics._reproduction_number = 1.7
em_input._disease_dynamics._asymptomatic_infection_fraction = 0.5
em_input._disease_dynamics._simulated_population = ['susceptible', 'exposed', 'infectious', 'recovered']
em_input._disease_dynamics._pop_count = [1157474.0, 0.0, 100.0, 60920.0]

em_input._simulator_configuration._time_step_unit = "DAYS"
em_input._simulator_configuration._time_step_value = 1
em_input._simulator_configuration._run_length = 365
em_input._simulator_configuration._pop_count = 12
em_input._simulator_configuration._disease = "influenza"
em_input._simulator_configuration._population_location = "Allegheny County"

#This (requester_id) is an error, it duplicates the requester_id in the authentication section
#it will be removed in the next version of the apollo types  
em_input._simulator_configuration._requester_id = "Example User"

em_input._vaccination_control_measure._vaccine_cm_compliance = 1.0
em_input._vaccination_control_measure._vaccine_efficacy= 1.0
em_input._vaccination_control_measure._vaccine_efficacy_delay = 0.0
#[0.0] * 365 creates a list of size 365, each entry has the value 0
em_input._vaccination_control_measure._vaccine_supply_schedule = [0.0] * 365
em_input._vaccination_control_measure._vaccination_admin_schedule = [0.0] * 365

em_input._antiviral_control_measure._antiviral_cm_compliance = 1.0
em_input._antiviral_control_measure._antiviral_efficacy= 1.0
em_input._antiviral_control_measure._antiviral_efficacy_delay = 0.0
em_input._antiviral_control_measure._antiviral_supply_schedule= [0.0] * 365
em_input._antiviral_control_measure._antiviral_admin_schedule = [0.0] * 365

#create a run request object
run_request = runRequest()
#the run request object has a single member variable, which is set to the epidemic model input object
run_request._epidemicModelinput = em_input

print 'Calling "run"'
#submit the request, receive the response
run_response = service.run(run_request)

print "Run submitted with ID: " + str(run_response._simulationRunResult._runId)

get_run_status_request = getRunStatusRequest()
get_run_status_request._runId = -1
run_status_response = service.getRunStatus(get_run_status_request)

print '\nCalling "getRunStatus"'
print "Status Code: " +  run_status_response._runStatus._status + " Status Message: " + run_status_response._runStatus._message





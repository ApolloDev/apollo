'''
Created on Nov 27, 2012

@author: John Levander
'''

from EpidemicSimulatorService_client import *
from EpidemicSimulatorService_types import *
from ApolloFactory import *

service = EpidemicSimulatorServiceLocator().getEpidemicSimulatorPort("http://localhost:8087/GenericEpidemicModelService/services/EpidemicSimulatorPort")
request = getSimulatorDescription()
response = service.getSimulatorDescription(request)

factory = ApolloFactory()
em_input = factory.new_epidemic_model_input()

em_input._authentication._requester_id = "Fake User"
em_input._authentication._requester_password = "Fake Password"

em_input._simulator_identification._simulator_developer = "University of Pittsburgh"
em_input._simulator_identification._simulator_name = "Simple Epidemic Model"
em_input._simulator_identification._simulator_version = "1.0"

em_input._disease_dynamics._infectious_period = 3.2
em_input._disease_dynamics._latent_period = 2.0
em_input._disease_dynamics._reproduction_number = 1.7
em_input._disease_dynamics._asymptomatic_infection_fraction = 0.5
em_input._disease_dynamics._population = ['susceptible', 'exposed', 'infectious', 'recovered']
em_input._disease_dynamics._pop_count = [1157474.0, 0.0, 100.0, 60920.0]

em_input._simulator_configuration._time_step_unit = "DAYS"
em_input._simulator_configuration._time_step_value = 1
em_input._simulator_configuration._run_length = 365
em_input._simulator_configuration._pop_count = 12
em_input._simulator_configuration._disease = "influenza"
em_input._simulator_configuration._population_location = "Allegheny County"
em_input._simulator_configuration._requester_id = "jdl"

em_input._vaccination_control_measure._vaccine_cm_compliance = 1.0
em_input._vaccination_control_measure._vaccine_efficacy= 1.0
em_input._vaccination_control_measure._vaccine_efficacy_delay = 0.0
em_input._vaccination_control_measure._vaccine_supply_schedule = [0.0] * 365
em_input._vaccination_control_measure._vaccination_admin_schedule = [0.0] * 365

em_input._antiviral_control_measure._antiviral_cm_compliance = 1.0
em_input._antiviral_control_measure._antiviral_efficacy= 1.0
em_input._antiviral_control_measure._antiviral_efficacy_delay = 0.0
em_input._antiviral_control_measure._antiviral_supply_schedule= [0.0] * 365
em_input._antiviral_control_measure._antiviral_admin_schedule = [0.0] * 365

run_request = run()
run_request._arg0 = em_input

run_response = service.run(run_request)
print "Run submitted with ID: " + str(run_response._return._runId)

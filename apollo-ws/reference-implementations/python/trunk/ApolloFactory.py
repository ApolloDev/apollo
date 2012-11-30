'''
Created on Nov 29, 2012

@author: John Levander
'''

from EpidemicSimulatorService_client import *
from EpidemicSimulatorService_types import *

class ApolloFactory:
    
    def new_epidemic_model_input(self):
        em_input = ns0.epidemic_model_input_Def(None).pyclass()
        em_input._authentication = ns0.authentication_Def(None).pyclass()
        em_input._simulator_identification = ns0.simulator_identification_Def(None).pyclass()
        em_input._simulator_configuration = ns0.simulator_configuration_Def(None).pyclass()
        em_input._disease_dynamics = ns0.disease_Def(None).pyclass()
        em_input._antiviral_control_measure = ns0.antiviral_control_measure_Def(None).pyclass()
        em_input._vaccination_control_measure = ns0.vaccination_control_measure_Def(None).pyclass()
        return em_input
    
    def new_epidemic_model_output(self):
        epidemic_model_output = ns0.epidemic_model_output_Def(None).pyclass()
#       self._population_time_series = None
#       self._control_measure_time_series = None

        epidemic_model_output._population_time_series = ns0.population_time_series_Def(None).pyclass()
#       self._pop_count = []
#       self._population = []

        epidemic_model_output._population_time_series._pop_count = [0.0] * 365
        
        epidemic_model_output._population_time_series._population = []
		
        epidemic_model_output._control_measure_time_series = ns0.control_measure_time_series_output_Def(None).pyclass()
#       self._received_vaccination = None
#       self._received_antiviral_treatment = None
        epidemic_model_output._control_measure_time_series._received_vaccination = []
        epidemic_model_output._control_measure_time_series._received_antiviral_treatment = []
        
        return epidemic_model_output
    
    def create_run_result(self):
        run_result = ns1.runResult_Def(None).pyclass()
#       self._output = None
#       self._runId = None
        run_result._runId = -1
        return run_result 
        
        
        
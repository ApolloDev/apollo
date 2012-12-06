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
Created on Nov 29, 2012

@author: John Levander
'''

from EpidemicSimulatorService_client import *
from EpidemicSimulatorService_types import *

class ApolloFactory:
    
    def new_epidemic_model_input(self):
        em_input = ns1.epidemic_model_input_Def(None).pyclass()
        em_input._authentication = ns1.authentication_Def(None).pyclass()
        em_input._simulator_identification = ns1.simulator_identification_Def(None).pyclass()
        em_input._simulator_configuration = ns1.simulator_configuration_Def(None).pyclass()
        em_input._disease_dynamics = ns1.disease_Def(None).pyclass()
        em_input._antiviral_control_measure = ns1.antiviral_control_measure_Def(None).pyclass()
        em_input._vaccination_control_measure = ns1.vaccination_control_measure_Def(None).pyclass()
        return em_input
    
    def new_epidemic_model_output(self):
        epidemic_model_output = ns1.epidemic_model_output_Def(None).pyclass()
#       self._population_time_series = None
#       self._control_measure_time_series = None

        epidemic_model_output._population_time_series = ns1.population_time_series_Def(None).pyclass()
#       self._pop_count = []
#       self._simulated_population = []

        epidemic_model_output._population_time_series._pop_count = [0.0] * 365
        
        epidemic_model_output._population_time_series._simulated_population = []
        
        epidemic_model_output._control_measure_time_series = ns1.control_measure_time_series_output_Def(None).pyclass()
#       self._received_vaccination = None
#       self._received_antiviral_treatment = None
        epidemic_model_output._control_measure_time_series._received_vaccination = []
        epidemic_model_output._control_measure_time_series._received_antiviral_treatment = []
        
        return epidemic_model_output
    
    def create_run_result(self):
        run_result = ns1.simulation_run_result_Def(None).pyclass()
#       self._model_output = None
#       self._runId = None
        run_result._runId = -1
        return run_result 
    
    def create_run_status(self):
        run_status = ns1.run_status_Def(None).pyclass()
        return run_status
        
        
        

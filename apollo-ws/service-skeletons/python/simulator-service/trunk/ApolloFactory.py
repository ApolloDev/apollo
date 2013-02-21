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

from SimulatorService_services_types import *

class ApolloFactory:
    
    def new_SimulatorConfiguration(self):
        cfg = ns1.SimulatorConfiguration_Def(None).pyclass()
        cfg._authentication = ns1.Authentication_Def(None).pyclass()
        cfg._simulatorIdentification = ns1.SimulatorIdentification_Def(None).pyclass()
        cfg._populationInitialization = ns1.SimulatedPopulation_Def(None).pyclass()
        cfg._simulatorTimeSpecification= ns1.SimulatorTimeSpecification_Def(None).pyclass()
        cfg._disease = ns1.Disease_Def(None).pyclass()
        cfg._antiviralControlMeasure = ns1.AntiviralControlMeasure_Def(None).pyclass()
        cfg._vaccinationControlMeasure = ns1.VaccinationControlMeasure_Def(None).pyclass()
        return cfg
    
    def new_RunStatus(self):
        run_status = ns1.RunStatus_Def(None).pyclass()
        return run_status
    
    def new_SupportedPopulationLocation(self):
        spl = ns1.SupportedPopulationLocation_Def(None).pyclass()
        return spl

    
    def new_PopulationDiseaseState(self):
        return ns1.PopulationDiseaseState_Def(None).pyclass()
        
        
        

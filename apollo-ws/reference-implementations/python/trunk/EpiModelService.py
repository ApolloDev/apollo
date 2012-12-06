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

This is an example GenericEpidemicModelService implementation.

@author: John Levander
'''

from ZSI.wstools import logging
from EpidemicSimulatorService_server import EpidemicSimulatorService
from ZSI.ServiceContainer import AsServer
from ApolloFactory import *
from ApolloUtils import *

class EpiModelService(EpidemicSimulatorService):
        _wsdl = "".join(open("epidemicsimulator.wsdl").readlines())
        factory = ApolloFactory()
        utils = ApolloUtils()
                        
        #this method checks on the status of a job, indicated by the jobs runId
        def soap_getRunStatus(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getRunStatus(self, ps, **kw)
            #get the run id of the job 
            runId = request._runId
            
            response._runStatus = self.factory.create_run_status()
            #just set the run to COMPLETED as an example
            response._runStatus._status = "COMPLETED"
            #enter a custom message
            response._runStatus._message = "The run completed successfully at 11/25/2012 16:34"
            
            return request, response
        
        #this method does not need to be implemented at this time
        def soap_getModelDescription(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getModelDescription(self, ps, **kw)
            return request, response
        
        #this method does not need to be implemented at this time
        def soap_getModelConfiguration(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getModelConfiguration(self, ps, **kw)
            return request, response
        
        #this method returns the native configuration file in string format
        def soap_getNativeConfigurationFile(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getNativeConfigurationFile(self, ps, **kw)
            #get the run id of the job 
            runId = request._runId
            
            #here, the native auto-generated configuration file would be read and returned (if a 
            #native configuration file was used) 
            response._nativeConfigurationFileContents = "r0=1.7\nlatent_period=3.0\n..."
            return request, response
        
        #this method does not need to be implemented at this time
        def soap_getPreconfiguredModels(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getPreconfiguredModels(self, ps, **kw)
            response._modelNames = []
            return request, response
        
        #this method returns the result of a model run
        def soap_getResult(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getResult(self, ps, **kw)
            #get the run id of the job 
            runId = request._runId
            
            #perhaps lookup the runId in a database or on the file system....
            #...code omitted
            
            #build the epidemic_model_output (based on the runId) and return it
            #see the method "soap_run" in this file for a better example of an EpidemicModelOutput object
            epidemic_model_output = self.factory.new_epidemic_model_output()
            epidemic_model_output._population_time_series._simulated_population = ["susceptible", "exposed", "infectious", "recovered"]
            epidemic_model_output._population_time_series._pop_count = [0.0] * 365 * 4
            epidemic_model_output._control_measure_time_series._received_antiviral_treatment = [0.0] * 365
            epidemic_model_output._control_measure_time_series._received_vaccination = [0.0] * 365
             
            response._epidemicModelOutput = epidemic_model_output
            return request, response
        
        def soap_getSimulatorDescription(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getSimulatorDescription(self, ps, **kw)
            response._simulatorDescription = "This simulator does not run any models.  This is an example service."
            return request, response
                
        #this method does not need to be implemented at this time
        def soap_releaseResources(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_releaseResources(self, ps, **kw)
            #get the run id of the job 
            runId = request._runId
            return request, response

        #this method does not need to be implemented at this time        
        def soap_runBatch(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_runBatch(self, ps, **kw)
            return request, response
            
        #this method runs an epidemic model
        def soap_run(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_run(self, ps, **kw)
            #extract the epidemic model input object from the request
            epidemic_model_input = request._epidemicModelinput
        
            #get the number of time steps in the simulation
            run_length = epidemic_model_input._simulator_configuration._run_length
        
            #log the EpidemicModelInput request to a log file
            self.utils.log_epidemic_model_input(epidemic_model_input)
           
            #create a run result, which consists of a:
            #    runId (set to a positive number if there are no results to return, set to -1 if
            #           the results are being returned in the EpidemicModelOutput object)
            #    EpidemicModelOutput (if the results of the simulation request are immediately known
            #                         due to a cache hit)
            run_result = self.factory.create_run_result()
            epidemic_model_output = self.factory.new_epidemic_model_output()
            
            #this example returns a time series for 4 populations
            epidemic_model_output._population_time_series._simulated_population = ["susceptible", "exposed", "infectious", "recovered"]
            
            #This list stores the time series for susceptible (the first population in the population object)
            #at index 0 through run_length.  The time series for exposed is stored at run_length*1 through 
            #run_length*2-1 etc...all values are zero in this example 
            epidemic_model_output._population_time_series._pop_count = [0.0] * run_length * 4
            
            #the number of agents that received antiviral_treatement per time step
            epidemic_model_output._control_measure_time_series._received_antiviral_treatment = [0.0] * run_length
            #the number of agents that received vaccination per time step
            epidemic_model_output._control_measure_time_series._received_vaccination = [0.0] * 365
             
            #set the output member of the run_result object to the EpidemicModelOutput object that was created above 
            run_result._output = epidemic_model_output
            
            #since an EpidemicModelOutput object is being returned, set runId to -1
            run_result._runId = -1
             
            response._simulationRunResult = run_result
            return request, response
        
logger = logging.getLogger("");
logger.setLevel(0)

#run a webserver on 8087
AsServer(port=8087, services=[EpiModelService(), ])

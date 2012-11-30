'''
Created on Nov 27, 2012

@author: John Levander
'''

from ZSI.wstools import logging
#from SeirSimulatorService_client import *
from EpidemicSimulatorService_server import EpidemicSimulatorService
#from ZSI.twisted.wsgi import (SOAPApplication, soapmethod, SOAPHandlerChainFactory)
from ZSI.ServiceContainer import AsServer
from ApolloFactory import *
from ApolloUtils import *

class EpiModelService(EpidemicSimulatorService):
        _wsdl = "".join(open("epidemicsimulator.wsdl").readlines())
        factory = ApolloFactory()
        utils = ApolloUtils()

        
                        
        def soap_getJobStatus(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_run(self, ps, **kw)
            
            return request, response
        
        def soap_getModelDescription(self, ps, **kw):
            request, response =  EpidemicSimulatorService.soap_getModelDescription(self, ps, **kw)
            return request, response
        
        def soap_getModelConfiguration(self, ps, **kw):
            request, response =  EpidemicSimulatorService.soap_getModelConfiguration(self, ps, **kw)
            return request, response
        
        def soap_getNativeConfigurationFile(self, ps, **kw):
            request, response =  EpidemicSimulatorService.soap_getNativeConfigurationFile(self, ps, **kw)
            return request, response
        
        def soap_getPreconfiguredModels(self, ps, **kw):
            request, response =  EpidemicSimulatorService.soap_getPreconfiguredModels(self, ps, **kw)
            return request, response
        
        def soap_getResult(self, ps, **kw):
            request, response =  EpidemicSimulatorService.soap_getResult(self, ps, **kw)
            return request, response
        
        def soap_getSimulatorDescription(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_getSimulatorDescription(self, ps, **kw)
            response._return = "Hello from Python"
            return request, response
                
        def soap_releaseResources(self, ps, **kw):
            request, response =  EpidemicSimulatorService.soap_releaseResources(self, ps, **kw)
            return request, response
        
        def soap_runGzipBatch(self, ps, **kw):
            request, response =  EpidemicSimulatorService.soap_runGzipBatch(self, ps, **kw)
            return request, response
            
        def soap_run(self, ps, **kw):
            request, response = EpidemicSimulatorService.soap_run(self, ps, **kw)
            epidemic_model_input = request._arg0

            self.utils.log_epidemic_model_input(epidemic_model_input)
          
           
            run_result = self.factory.create_run_result()
            epidemic_model_output = self.factory.new_epidemic_model_output()
            epidemic_model_output._population_time_series._population = [""]
            epidemic_model_output._population_time_series._pop_count = [0.0] * 365
            epidemic_model_output._control_measure_time_series._received_antiviral_treatment  = [0.0] * 365
            epidemic_model_output._control_measure_time_series._received_vaccination  = [0.0] * 365
             
            run_result._output = epidemic_model_output
            run_result._runId = -2
             
            response._return = run_result
            return request, response
        
        
        
logger = logging.getLogger("");
logger.setLevel(0)

AsServer(port=8087, services=[EpiModelService(),])


        
##################################################
# file: SimulatorService_v2_0_2_client.py
# 
# client stubs generated by "ZSI.generate.wsdl2python.WriteServiceModule"
#     /usr/local/bin/wsdl2py --complexType --lazy simulatorservice202.wsdl
# 
##################################################

from SimulatorService_v2_0_2_types import *
import urlparse, types
from ZSI.TCcompound import ComplexType, Struct
from ZSI import client
from ZSI.schema import GED, GTD
import ZSI
from ZSI.generate.pyclass import pyclass_type

# Locator
class SimulatorService_v2_0_2Locator:
    SimulatorServiceEndpoint_address = "http://localhost:8080/simulatorservice202/services/simulatorservice"
    def getSimulatorServiceEndpointAddress(self):
        return SimulatorService_v2_0_2Locator.SimulatorServiceEndpoint_address
    def getSimulatorServiceEndpoint(self, url=None, **kw):
        return SimulatorServiceBindingSOAP(url or SimulatorService_v2_0_2Locator.SimulatorServiceEndpoint_address, **kw)

# Methods
class SimulatorServiceBindingSOAP:
    def __init__(self, url, **kw):
        kw.setdefault("readerclass", None)
        kw.setdefault("writerclass", None)
        # no resource properties
        self.binding = client.Binding(url=url, **kw)
        # no ws-addressing

    # op: runSimulation
    def runSimulation(self, request, **kw):
        if isinstance(request, runSimulationRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="http://service.apollo.pitt.edu/simulatorservice/v2_0_2/runSimulation", **kw)
        # no output wsaction
        response = self.binding.Receive(runSimulationResponse.typecode)
        return response

    # op: getScenarioLocationCodesSupportedBySimulator
    def getScenarioLocationCodesSupportedBySimulator(self, request, **kw):
        if isinstance(request, getScenarioLocationCodesSupportedBySimulatorRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="http://service.apollo.pitt.edu/simulatorservice/v2_0_2/getScenarioLocationCodesSupportedBySimulator", **kw)
        # no output wsaction
        response = self.binding.Receive(getScenarioLocationCodesSupportedBySimulatorResponse.typecode)
        return response

    # op: runSimulations
    def runSimulations(self, request, **kw):
        if isinstance(request, runSimulationsRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="http://service.apollo.pitt.edu/simulatorservice/v2_0_2/runSimulations", **kw)
        # no output wsaction
        response = self.binding.Receive(runSimulationsResponse.typecode)
        return response

    # op: getPopulationAndEnvironmentCensus
    def getPopulationAndEnvironmentCensus(self, request, **kw):
        if isinstance(request, getPopulationAndEnvironmentCensusRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="http://service.apollo.pitt.edu/simulatorservice/v2_0_2/getPopulationAndEnvironmentCensus", **kw)
        # no output wsaction
        response = self.binding.Receive(getPopulationAndEnvironmentCensusResponse.typecode)
        return response

    # op: terminateRun
    def terminateRun(self, request, **kw):
        if isinstance(request, terminateRunRequest) is False:
            raise TypeError, "%s incorrect request type" % (request.__class__)
        # no input wsaction
        self.binding.Send(None, None, request, soapaction="http://service.apollo.pitt.edu/simulatorservice/v2_0_2/terminateRun", **kw)
        # no output wsaction
        response = self.binding.Receive(terminateRunResponse.typecode)
        return response

runSimulationRequest = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "runSimulation").pyclass

runSimulationResponse = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "runSimulationResponse").pyclass

getScenarioLocationCodesSupportedBySimulatorRequest = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "getScenarioLocationCodesSupportedBySimulator").pyclass

getScenarioLocationCodesSupportedBySimulatorResponse = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "getScenarioLocationCodesSupportedBySimulatorResponse").pyclass

runSimulationsRequest = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "runSimulations").pyclass

runSimulationsResponse = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "runSimulationsResponse").pyclass

getPopulationAndEnvironmentCensusRequest = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "getPopulationAndEnvironmentCensus").pyclass

getPopulationAndEnvironmentCensusResponse = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "getPopulationAndEnvironmentCensusResponse").pyclass

terminateRunRequest = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "terminateRun").pyclass

terminateRunResponse = GED("http://service.apollo.pitt.edu/simulatorservice/v2_0_2/", "terminateRunResponse").pyclass

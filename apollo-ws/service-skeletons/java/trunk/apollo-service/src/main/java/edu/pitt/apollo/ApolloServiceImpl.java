/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.pitt.apollo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceEI;
import edu.pitt.apollo.service.libraryservice.v2_0_1.LibraryServiceEI;
import edu.pitt.apollo.service.libraryservice.v2_0_1.LibraryServiceV201;
import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceV201;
import edu.pitt.apollo.service.visualizerservice.v2_0_1.VisualizerServiceEI;
import edu.pitt.apollo.service.visualizerservice.v2_0_1.VisualizerServiceV201;
import edu.pitt.apollo.types.v2_0_1.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.types.v2_0_1.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.GetLibraryItemUuidsResult;
import edu.pitt.apollo.types.v2_0_1.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_1.Location;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsResult;
import edu.pitt.apollo.types.v2_0_1.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.ServiceRecord;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.types.v2_0_1.VisualizerResult;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v2.0", endpointInterface = "edu.pitt.apollo.service.apolloservice.v2_0.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

	private static final String REGISTRY_FILENAME = "registered_services.xml";
	private static final String ERROR_FILENAME = "run_errors.txt";
	private static final String RUN_ERROR_PREFIX = "ApolloServiceError";
	public static final String APOLLO_WORKDIR_ENVIRONMENT_VARIABLE = "APOLLO_20_WORK_DIR";
	// private static final String DB4O_FILENAME = "db4o_db_20";
	private static final String CENSUS_DATA_FILENAME = "fred_census_data.csv";

	private static String APOLLO_DIR = "";
	// private static ObjectContainer db4o;
	private static List<PopulationAndEnvironmentCensus> censusData;

	static String getRegistryFilename() {
		return APOLLO_DIR + REGISTRY_FILENAME;
	}

	static String getErrorFilename() {
		return APOLLO_DIR + ERROR_FILENAME;
	}

	static String getRunErrorPrefix() {
		return RUN_ERROR_PREFIX;
	}

	private LibraryServiceEI getLibraryServicePort() {
		URL libraryServiceURL;
		try {
			libraryServiceURL = new URL("http://localhost:8080/libraryservice2.0/services/libraryservice?wsdl");

			return new LibraryServiceV201(libraryServiceURL).getLibraryServiceEndpoint();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private VisualizerServiceEI getVisualizerServicePort(URL url) {
		return new VisualizerServiceV201(url).getVisualizerServiceEndpoint();
	}

	private SimulatorServiceEI getSimulatorServicePort(URL url) {
		return new SimulatorServiceV201(url).getSimulatorServiceEndpoint();
	}

	@Override
	@WebResult(name = "methodCallStatus", targetNamespace = "")
	@RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.UnRegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/unRegisterService")
	@ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.UnRegisterServiceResponse")
	public MethodCallStatus unRegisterService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
		MethodCallStatus result = new MethodCallStatus();

		List<ServiceRegistrationRecord> records;
		try {
			// get the entire list of current service registration records
			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				// for each record currently in the registry, see if we can find
				// a record with a ServiceIdentification that is equal to one
				// that the user is trying to unregister
				if (RegistrationUtils.softwareIdentificationEqual(record.getSoftwareIdentification(),
						serviceRegistrationRecord.getSoftwareIdentification())) {
					// found the service the user wants to unregister, now check
					// that the username and password supplied with this request
					// match the username and password sent with the
					// registration request
					if (RegistrationUtils.authenticationEqual(record.getAuthentication(),
							serviceRegistrationRecord.getAuthentication())) {
						try {
							// the username/password match, so remove the record
							// from the registry
							RegistrationUtils.removeServiceRegistrationRecord(serviceRegistrationRecord);
						} catch (IOException e) {
							// there was en error removing the record, report
							// this error to the caller
							result.setMessage("Error Unregistering Service: " + e.getMessage());
							result.setStatus(MethodCallStatusEnum.FAILED);
							return result;
						}
						// removal succeeded
						result.setMessage("unregistration Successful!");
						result.setStatus(MethodCallStatusEnum.COMPLETED);
						return result;
					} else {
						// username/passwords do not match
						result.setMessage("Error Unregistering Service: Username/Password does not match orignial ServiceRegistrationRecord!");
						result.setStatus(MethodCallStatusEnum.FAILED);
						return result;
					}
				}
			}
			// couldn't find matching ServiceRecords
			result.setMessage("Error Unregistering Service: Service not registered at this registry.");
			result.setStatus(MethodCallStatusEnum.FAILED);
			return result;
		} catch (IOException e) {
			result.setMessage("Error Unregistering Service: Error reading registry!");
			result.setStatus(MethodCallStatusEnum.FAILED);
			return result;
		}

	}

	@Override
	@WebResult(name = "syntheticPopulationGenerationResult", targetNamespace = "")
	@RequestWrapper(localName = "runSyntheticPopulationGeneration", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RunSyntheticPopulationGeneration")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/runSyntheticPopulationGeneration")
	@ResponseWrapper(localName = "runSyntheticPopulationGenerationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RunSyntheticPopulationGenerationResponse")
	public SyntheticPopulationGenerationResult runSyntheticPopulationGeneration(
			@WebParam(name = "runSyntheticPopulationGenerationMessage", targetNamespace = "") RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "runSimulationsResult", targetNamespace = "")
	@RequestWrapper(localName = "runSimulations", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RunSimulations")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/runSimulations")
	@ResponseWrapper(localName = "runSimulationsResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RunSimulationsResponse")
	public RunSimulationsResult runSimulations(
			@WebParam(name = "runSimulationsMessage", targetNamespace = "") RunSimulationsMessage runSimulationsMessage) {
		RunSimulationsResult bsr = new RunSimulationsResult();

		String runId;
		try {
			URL url = null;
			try {
				// get the webservice WSDL URL for supplied
				// SimulatorIdentification
				url = RegistrationUtils.getUrlForSoftwareIdentification(runSimulationsMessage.getSoftwareIdentification());
				if (url == null) {
					runId = RunUtils.getErrorRunId();
					RunUtils.reportError(runId, "Service not registered.");
					bsr.setRunId(runId);
					return bsr;
				}
			} catch (IOException e) {
				runId = RunUtils.getErrorRunId();
				RunUtils.reportError(runId, "Error reading registry.");
				bsr.setRunId(runId);
				return bsr;
			}
			// run the simulator

			SimulatorServiceEI port = getSimulatorServicePort(url);

			// disable chunking for ZSI
			Client client = ClientProxy.getClient(port);
			HTTPConduit http = (HTTPConduit) client.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(36000);
			httpClientPolicy.setAllowChunking(false);
			http.setClient(httpClientPolicy);

			return port.runSimulations(runSimulationsMessage);
		} catch (Exception e) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(runId, "Problem with SimulatorService:" + e.getMessage());
			bsr.setRunId(runId);
			return bsr;
		}

	}

	@Override
	@WebResult(name = "methodCallStatus", targetNamespace = "")
	@RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/registerService")
	@ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RegisterServiceResponse")
	public MethodCallStatus registerService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
		MethodCallStatus result = new MethodCallStatus();

		List<ServiceRegistrationRecord> records;
		try {

			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				if (RegistrationUtils.softwareIdentificationEqual(record.getSoftwareIdentification(),
						serviceRegistrationRecord.getSoftwareIdentification())) {
					result.setMessage("Service is already registered.  Please unRegisterService to make changes to the existing ServiceRecord.");
					result.setStatus(MethodCallStatusEnum.FAILED);
					return result;
				}

				if (RegistrationUtils.serviceUrlEqual(record, serviceRegistrationRecord)) {
					result.setMessage("URL is already registered.");
					result.setStatus(MethodCallStatusEnum.FAILED);
					return result;
				}
			}

			// if we are here, it looks like a valid registration
			try {
				RegistrationUtils.addServiceRegistrationRecord(serviceRegistrationRecord);
				result.setMessage("Service Registration Successful!");
				result.setStatus(MethodCallStatusEnum.COMPLETED);
			} catch (IOException e) {
				result.setMessage("Error registering service: " + e.getMessage());
				result.setStatus(MethodCallStatusEnum.FAILED);
			}

		} catch (IOException e) {
			result.setMessage("Error reading registry!");
			result.setStatus(MethodCallStatusEnum.FAILED);
		}
		return result;
	}

	@Override
	@WebResult(name = "getLibraryItemsResult", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsGivenType", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetUuidsForLibraryItemsGivenType")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getUuidsForLibraryItemsGivenType")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsGivenTypeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetUuidsForLibraryItemsGivenTypeResponse")
	public GetLibraryItemUuidsResult getUuidsForLibraryItemsGivenType(@WebParam(name = "type", targetNamespace = "") String type) {
		return getLibraryServicePort().getUuidsForLibraryItemsGivenType(type);
	}

	@Override
	@WebResult(name = "addLibraryItemResult", targetNamespace = "")
	@RequestWrapper(localName = "addLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.AddLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/addLibraryItem")
	@ResponseWrapper(localName = "addLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.AddLibraryItemResponse")
	public AddLibraryItemResult addLibraryItem(
			@WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
			@WebParam(name = "apolloIndexableItem", targetNamespace = "") ApolloIndexableItem apolloIndexableItem,
			@WebParam(name = "itemDescription", targetNamespace = "") String itemDescription,
			@WebParam(name = "itemSource", targetNamespace = "") String itemSource,
			@WebParam(name = "itemType", targetNamespace = "") String itemType,
			@WebParam(name = "itemIndexingLabels", targetNamespace = "") List<String> itemIndexingLabels) {
		// TODO Auto-generated method stub
		return getLibraryServicePort().addLibraryItem(authentication, apolloIndexableItem, itemDescription, itemSource, itemType,
				itemIndexingLabels);
	}

	@Override
	@WebResult(name = "getConfigurationFileForSimulationResult", targetNamespace = "")
	@RequestWrapper(localName = "getConfigurationFileForSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetConfigurationFileForSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getConfigurationFileForSimulation")
	@ResponseWrapper(localName = "getConfigurationFileForSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetConfigurationFileForSimulationResponse")
	public GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(
			@WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {

		GetConfigurationFileForSimulationResult result = new GetConfigurationFileForSimulationResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setMethodCallStatus(status);

		String simName = runAndSoftwareIdentification.getSoftwareId().getSoftwareName();
		try {
			ServiceRecord serviceRecord = RegistrationUtils.getServiceRecordForSoftwareId(runAndSoftwareIdentification
					.getSoftwareId());

			URL url = new URL(serviceRecord.getUrl());

			// get the webservice WSDL URL for supplied
			// SimulatorIdentification
			if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareTypeEnum.SIMULATOR) {

				SimulatorServiceEI port = getSimulatorServicePort(url);
				return port.getConfigurationFileForSimulation(runAndSoftwareIdentification.getRunId());
			} else {
				status.setMessage("getConfigurationFile() not implemented for this service type.");
				status.setStatus(MethodCallStatusEnum.FAILED);
			}
		} catch (IOException e) {
			status.setMessage("General error occurred on server.");
			status.setStatus(MethodCallStatusEnum.FAILED);

		}
		return result;

	}

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetRunStatusResponse")
	public MethodCallStatus getRunStatus(
			@WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {
		if (runAndSoftwareIdentification.getRunId().startsWith(RUN_ERROR_PREFIX)) {
			MethodCallStatus rs = new MethodCallStatus();
			rs.setStatus(MethodCallStatusEnum.FAILED);
			rs.setMessage(RunUtils.getError(runAndSoftwareIdentification.getRunId()));
			return rs;
		}

		try {
			ServiceRecord serviceRecord = RegistrationUtils.getServiceRecordForSoftwareId(runAndSoftwareIdentification
					.getSoftwareId());

			URL url = new URL(serviceRecord.getUrl());

			// get the webservice WSDL URL for supplied
			// SimulatorIdentification
			if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareTypeEnum.SIMULATOR) {
				SimulatorServiceEI port = getSimulatorServicePort(url);
				return port.getRunStatus(runAndSoftwareIdentification.getRunId());
			} else if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareTypeEnum.VISUALIZER) {

				VisualizerServiceEI port = getVisualizerServicePort(url);
				return port.getRunStatus(runAndSoftwareIdentification.getRunId());
				// } else if (serviceRecord.getSoftwareIdentification()
				// .getSoftwareType() ==
				// ApolloSoftwareType.SYNTHETIC_POPULATION_GENERATOR) {
				// SyntheticPopulationServiceV20 ss = new
				// SyntheticPopulationServiceV20(
				// url);
				// SyntheticPopulationServiceEI port = ss
				// .getSyntheticPopulationServiceEndpoint();
				// return port.getRunStatus(runAndSoftwareIdentification
				// .getRunId());
			} else {
				MethodCallStatus rs = new MethodCallStatus();
				rs.setMessage("Not implemented yet...");
				rs.setStatus(MethodCallStatusEnum.FAILED);
				return rs;
			}
		} catch (IOException e) {
			MethodCallStatus rs = new MethodCallStatus();
			rs.setMessage("Error geting remote runStatus!");
			rs.setStatus(MethodCallStatusEnum.FAILED);
			return rs;
		}
	}

	@Override
	@WebResult(name = "getLibraryItemResult", targetNamespace = "")
	@RequestWrapper(localName = "getLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getLibraryItem")
	@ResponseWrapper(localName = "getLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetLibraryItemResponse")
	public GetLibraryItemResult getLibraryItem(@WebParam(name = "uuid", targetNamespace = "") String uuid) {
		return getLibraryServicePort().getLibraryItem(uuid);
	}

	@Override
	@WebResult(name = "serviceRecords", targetNamespace = "")
	@RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetRegisteredServices")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getRegisteredServices")
	@ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetRegisteredServicesResponse")
	public List<ServiceRecord> getRegisteredServices() {
		try {
			return RegistrationUtils.getRegisteredSoftware();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	@WebResult(name = "getPopulationAndEnvironmentCensusResult", targetNamespace = "")
	@RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetPopulationAndEnvironmentCensus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getPopulationAndEnvironmentCensus")
	@ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetPopulationAndEnvironmentCensusResponse")
	public GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensus(
			@WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification,
			@WebParam(name = "location", targetNamespace = "") String location) {
		GetPopulationAndEnvironmentCensusResult res = new GetPopulationAndEnvironmentCensusResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		status.setMessage("Success!");
		res.setPopulationAndEnvironmentCensus(getPopulationAndEnvironmentCensusGivenINCITS(location));
		return res;
	}

	@Override
	@WebResult(name = "visualizationResult", targetNamespace = "")
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RunVisualizationResponse")
	public VisualizerResult runVisualization(
			@WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {
		// String runId;
		// ByteArrayOutputStream baos = getJSONBytes(runVisualizationMessage);
		// String visConfigHash = getMd5HashFromString(baos.toString());
		// VisualizerResult result;
		//
		// // check the cache
		// try {
		// result = DbUtilsTest.checkVisualizerCache(visConfigHash);
		// if (result != null) {
		//
		// // check the status of the run
		// runId = result.getRunId();
		// RunAndSoftwareIdentification rasid = new
		// RunAndSoftwareIdentification();
		// rasid.setRunId(runId);
		// rasid.setSoftwareId(runVisualizationMessage
		// .getVisualizerIdentification());
		// MethodCallStatus status = getRunStatus(rasid);
		// MethodCallStatusEnum statusEnum = status.getStatus();
		//
		// if (statusEnum.equals(MethodCallStatusEnum.FAILED)) {
		// DbUtilsTest.deleteFromVisualizerCache(visConfigHash);
		// } else {
		// return result;
		// }
		//
		// }
		// } catch (ClassNotFoundException ex) {
		// runId = RunUtils.getErrorRunId();
		// RunUtils.reportError(runId,
		// "Problem with VisualizerService: ClassNotFoundException"
		// + ex.getMessage());
		// result = new VisualizerResult();
		// result.setRunId(runId);
		// ex.printStackTrace();
		//
		// return result;
		// } catch (SQLException ex) {
		// runId = RunUtils.getErrorRunId();
		// RunUtils.reportError(
		// runId,
		// "Problem with VisualizerService: SQLException"
		// + ex.getMessage());
		// result = new VisualizerResult();
		// result.setRunId(runId);
		// ex.printStackTrace();
		//
		// return result;
		// }
		//
		// try {
		// result = new VisualizerResult();
		// URL url = null;
		// try {
		// // get the webservice WSDL URL for supplied
		// // SimulatorIdentification
		// url = RegistrationUtils
		// .getUrlForSoftwareIdentification(runVisualizationMessage
		// .getVisualizerIdentification());
		// if (url == null) {
		// result.setRunId(RunUtils.getErrorRunId());
		// RunUtils.reportError(result.getRunId(),
		// "Service not registered.");
		// return result;
		// }
		// } catch (IOException e) {
		// result.setRunId(RunUtils.getErrorRunId());
		// RunUtils.reportError(result.getRunId(),
		// "Error reading registry.");
		// return result;
		// }
		// // run the simulator
		//
		// VisualizerServiceEI port = getVisualizerServicePort(url);
		//
		// // disable chunking for ZSI
		// Client client = ClientProxy.getClient(port);
		// HTTPConduit http = (HTTPConduit) client.getConduit();
		// HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		// httpClientPolicy.setConnectionTimeout(36000);
		// httpClientPolicy.setAllowChunking(false);
		// http.setClient(httpClientPolicy);
		//
		// System.out.println("Running the visualization...");
		// VisualizerResult visualizerResult = port
		// .runVisualization(runVisualizationMessage);
		// runId = visualizerResult.getRunId();
		//
		// // cacheVisualizerResult(runId, visConfigHash, visualizerResult,
		// // cacheFilePath);
		//
		// // cache the visualizer result
		// try {
		// String cacheId = DbUtilsTest.insertIntoVisualizerCache(runId,
		// visConfigHash);
		// DbUtilsTest.insertIntoVisualizerResultsCache(cacheId,
		// visualizerResult.getVisualizerOutputResource());
		// } catch (ClassNotFoundException ex) {
		// RunUtils.reportError(visualizerResult.getRunId(),
		// "Problem with VisualizerService: ClassNotFoundException"
		// + ex.getMessage());
		// ex.printStackTrace();
		// } catch (SQLException ex) {
		// RunUtils.reportError(
		// visualizerResult.getRunId(),
		// "Problem with VisualizerService: SQLException"
		// + ex.getMessage());
		// ex.printStackTrace();
		// }
		//
		// return visualizerResult;
		// } catch (Exception e) {
		// VisualizerResult visualizerResult = new VisualizerResult();
		// visualizerResult.setRunId(RunUtils.getErrorRunId());
		//
		// RunUtils.reportError(visualizerResult.getRunId(),
		// "Problem with VisualizerService:" + e.getMessage());
		// e.printStackTrace();
		// return visualizerResult;
		// }
		return null;
	}

	@Override
	@WebResult(name = "methodCallStatus", targetNamespace = "")
	@RequestWrapper(localName = "removeLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RemoveLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/removeLibraryItem")
	@ResponseWrapper(localName = "removeLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.RemoveLibraryItemResponse")
	public MethodCallStatus removeLibraryItem(
			@WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
			@WebParam(name = "uuid", targetNamespace = "") String uuid) {
		return getLibraryServicePort().removeLibraryItem(authentication, uuid);
	}

	@Override
	@WebResult(name = "getLibraryItemsResult", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTime", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetUuidsForLibraryItemsCreatedSinceDateTime")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getUuidsForLibraryItemsCreatedSinceDateTime")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTimeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetUuidsForLibraryItemsCreatedSinceDateTimeResponse")
	public GetLibraryItemUuidsResult getUuidsForLibraryItemsCreatedSinceDateTime(
			@WebParam(name = "creationDateTime", targetNamespace = "") XMLGregorianCalendar creationDateTime) {
		return getLibraryServicePort().getUuidsForLibraryItemsCreatedSinceDateTime(creationDateTime);
	}

	@Override
	@WebResult(name = "getLocationsSupportedBySimulatorResult", targetNamespace = "")
	@RequestWrapper(localName = "getScenarioLocationCodesSupportedBySimulator", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetScenarioLocationCodesSupportedBySimulator")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0/getScenarioLocationCodesSupportedBySimulator")
	@ResponseWrapper(localName = "getScenarioLocationCodesSupportedBySimulatorResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0/", className = "edu.pitt.apollo.service.apolloservice.v2_0.GetScenarioLocationCodesSupportedBySimulatorResponse")
	public GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator(
			@WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification) {
		GetScenarioLocationCodesSupportedBySimulatorResult res = new GetScenarioLocationCodesSupportedBySimulatorResult();
		for (PopulationAndEnvironmentCensus c : censusData) {
			res.getLocationCodes().add(c.getLocation().getApolloLocationCode());
			for (PopulationAndEnvironmentCensus cc : c.getSubLocationCensuses()) {
				res.getLocationCodes().add(cc.getLocation().getApolloLocationCode());
			}
		}
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		status.setMessage("Returned " + res.getLocationCodes().size() + " items.");
		res.setMethodCallStatus(status);
		return res;

	}

	private static PopulationAndEnvironmentCensus getPopulationAndEnvironmentCensusGivenINCITS(String INCITS) {

		for (PopulationAndEnvironmentCensus c : censusData) {
			if (INCITS.length() == 2) { // state
				if (c.getLocation().getApolloLocationCode().equalsIgnoreCase(INCITS)) {
					return c;
				}
			} else { // county
				if (c.getLocation().getApolloLocationCode().substring(0, 2).equalsIgnoreCase(INCITS.substring(0, 2))) {
					// down the rabbit hole
					for (PopulationAndEnvironmentCensus cc : c.getSubLocationCensuses()) {
						if (cc.getLocation().getApolloLocationCode().equalsIgnoreCase(INCITS)) {
							return cc;
						}
					}
				}
			}

		}

		return null;
	}

	public static void readCensusData() throws FileNotFoundException, DatatypeConfigurationException {
		File f = new File(APOLLO_DIR + "/" + CENSUS_DATA_FILENAME);
		censusData = new ArrayList<PopulationAndEnvironmentCensus>();

		GregorianCalendar cal = new GregorianCalendar();
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		date.setMonth(1);
		date.setDay(1);
		date.setYear(2009);

		Scanner s = new Scanner(f);
		boolean onHeader = true;
		while (s.hasNextLine()) {
			if (onHeader) {
				onHeader = false;
				s.nextLine();
				continue;

			}
			String[] cols = s.nextLine().split("\t");

			PopulationAndEnvironmentCensus c = new PopulationAndEnvironmentCensus();
			c.setReferenceDate(date);
			c.setDescription(cols[1]);
			Location l = new Location();
			l.setApolloLocationCode(cols[0]);
			c.setLocation(l);
			c.setNameOfAdministativeUnit(cols[1]);
			c.setNumberOfPeople(new BigInteger(cols[2]));
			c.setNumberOfSchools(new BigInteger(cols[3]));
			c.setNumberOfWorkplaces(new BigInteger(cols[4]));

			if (cols[0].length() == 2) { // state
				censusData.add(c);
			} else { // county
				PopulationAndEnvironmentCensus stateRoot = getPopulationAndEnvironmentCensusGivenINCITS(cols[0].substring(0, 2));
				stateRoot.getSubLocationCensuses().add(c);
			}
		}
		s.close();
	}

	// public static void main(String[] args) {
	// PopulationAndEnvironmentCensus c =
	// getPopulationAndEnvironmentCensusGivenINCITS("42");
	// c = getPopulationAndEnvironmentCensusGivenINCITS("42003");
	// }

	static {
		Map<String, String> env = System.getenv();
		APOLLO_DIR = env.get(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (APOLLO_DIR != null) {
			if (!APOLLO_DIR.endsWith(File.separator)) {
				APOLLO_DIR += File.separator;
			}
			System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);
		} else {
			System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " environment variable not found!");
			APOLLO_DIR = "";
		}
		// EmbeddedConfiguration configuration =
		// Db4oEmbedded.newConfiguration();
		// configuration.file().generateUUIDs(ConfigScope.GLOBALLY);
		// db4o = Db4oEmbedded.openFile(configuration, APOLLO_DIR + "/"
		// + DB4O_FILENAME);

		try {
			try {
				readCensusData();
			} catch (DatatypeConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// db4o.close();
	}

	@Override
	@WebResult(name = "simulationRunId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSimulationResponse")
	public BigInteger runSimulation(
			@WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
//		String runId = runSimulationMessage.getRunId().toString();
//		ByteArrayOutputStream baos = getJSONBytes(runSimulationMessage);
//		String simConfigHash = getMd5HashFromString(baos.toString());
//		// check for cached result
//		try {
//			runId = DbUtilsTest.checkRunCache(simConfigHash);
//			if (runId != null) {
//
//				// check the status of the run
//				RunAndSoftwareIdentification rasid = new RunAndSoftwareIdentification();
//				rasid.setRunId(runId);
//				rasid.setSoftwareId(runSimulationMessage.getSimulatorIdentification());
//				MethodCallStatus status = getRunStatus(rasid);
//				MethodCallStatusEnum statusEnum = status.getStatus();
//
//				if (statusEnum.equals(MethodCallStatusEnum.FAILED)) {
//					DbUtilsTest.deleteFromRunCache(simConfigHash);
//				} else {
//					return runId;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			runId = RunUtils.getErrorRunId();
//			RunUtils.reportError(runId, "Problem with SimulatorService: ClassNotFoundException: " + ex.getMessage());
//			ex.printStackTrace();
//			return runId;
//		} catch (SQLException ex) {
//			runId = RunUtils.getErrorRunId();
//			RunUtils.reportError(runId, "Problem with SimulatorService: SQLException: " + ex.getMessage());
//			ex.printStackTrace();
//			return runId;
//		}
//
//		try {
//			URL url = null;
//			try {
//				// get the webservice WSDL URL for supplied
//				// SimulatorIdentification
//				url = RegistrationUtils.getUrlForSoftwareIdentification(runSimulationMessage.getSimulatorIdentification());
//				System.out.println("URL resturned: " + url.toString());
//				if (url == null) {
//					runId = RunUtils.getErrorRunId();
//					RunUtils.reportError(runId, "Service not registered.");
//					return runId;
//				}
//			} catch (IOException e) {
//				runId = RunUtils.getErrorRunId();
//				RunUtils.reportError(runId, "Error reading registry.");
//				return runId;
//			}
//			// run the simulator
//
//			SimulatorServiceEI port = getSimulatorServicePort(url);
//
//			// disable chunking for ZSI
//			Client client = ClientProxy.getClient(port);
//			HTTPConduit http = (HTTPConduit) client.getConduit();
//			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//			httpClientPolicy.setConnectionTimeout(36000);
//			httpClientPolicy.setAllowChunking(false);
//			http.setClient(httpClientPolicy);
//
//			System.out.println("Apollo Service is running simulator with URL " + url);
//			runId = port.runSimulation(runSimulationMessage);
//			System.out.println("Returned run ID is + " + runId);
//
//			try {
//				DbUtilsTest.insertIntoRunCache(runId, simConfigHash);
//			} catch (ClassNotFoundException ex) {
//				runId = RunUtils.getErrorRunId();
//				RunUtils.reportError(runId, "Problem with SimulatorService: ClassNotFoundException: " + ex.getMessage());
//				ex.printStackTrace();
//			} catch (SQLException ex) {
//				runId = RunUtils.getErrorRunId();
//				RunUtils.reportError(runId, "Problem with SimulatorService: SQLException: " + ex.getMessage());
//				ex.printStackTrace();
//				return runId;
//			}
//
//			return runId;
//		} catch (Exception e) {
//			runId = RunUtils.getErrorRunId();
//			RunUtils.reportError(runId, "Problem with SimulatorService:" + e.getMessage());
//			e.printStackTrace();
//			return runId;
//		}

		return null;
	}

}

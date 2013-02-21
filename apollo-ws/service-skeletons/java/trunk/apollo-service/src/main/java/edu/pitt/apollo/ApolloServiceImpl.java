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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.service.apolloservice.ApolloServiceEI;
import edu.pitt.apollo.service.simulatorservice.SimulatorService;
import edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI;
import edu.pitt.apollo.service.visualizerservice.VisualizerService;
import edu.pitt.apollo.service.visualizerservice.VisualizerServiceEI;
import edu.pitt.apollo.types.Authentication;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SimulatorIdentification;
import edu.pitt.apollo.types.VisualizerConfiguration;
import edu.pitt.apollo.types.VisualizerOutputResource;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService", endpointInterface = "edu.pitt.apollo.service.apolloservice.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

	private static final String REGISTRY_FILENAME = "registered_services.xml";
	private static final String ERROR_FILENAME = "run_errors.txt";
	private static final String RUN_ERROR_PREFIX = "ApolloServiceError";
	private static String APOLLO_DIR = "";

	public static String getRegistryFilename() {
		return APOLLO_DIR + REGISTRY_FILENAME;
	}

	public static String getErrorFilename() {
		return APOLLO_DIR + ERROR_FILENAME;
	}

	public static String getRunErrorPrefix() {
		return RUN_ERROR_PREFIX;
	}

	@Override
	@RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/registerService")
	@ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterServiceResponse")
	public void registerService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord,
			@WebParam(mode = Mode.OUT, name = "registrationSuccessful", targetNamespace = "") Holder<Boolean> registrationSuccessful,
			@WebParam(mode = Mode.OUT, name = "message", targetNamespace = "") Holder<String> message) {

		List<ServiceRegistrationRecord> records;
		try {

			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				if (RegistrationUtils.serviceIdentificationEqual(
						record.getServiceRecord(),
						serviceRegistrationRecord.getServiceRecord())) {
					message.value = "Service is already registered.  Please unRegisterService to make changes to the existing ServiceRecord.";
					registrationSuccessful.value = false;
					return;
				}

				if (RegistrationUtils.serviceUrlEqual(record,
						serviceRegistrationRecord)) {
					message.value = "URL is already registered.";
					registrationSuccessful.value = false;
					return;
				}
			}

			// if we are here, it looks like a valid registration
			try {
				RegistrationUtils
						.addServiceRegistrationRecord(serviceRegistrationRecord);
				message.value = "Service Registration Successful!";
				registrationSuccessful.value = true;
			} catch (IOException e) {
				message.value = "Error registering service: " + e.getMessage();
				registrationSuccessful.value = false;
			}

		} catch (IOException e) {
			message.value = "Error reading registry!";
			registrationSuccessful.value = false;
		}

	}

	@Override
	@RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.UnRegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/unRegisterService")
	@ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.UnRegisterServiceResponse")
	public void unRegisterService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord,
			@WebParam(mode = Mode.OUT, name = "unRegistrationSuccessful", targetNamespace = "") Holder<Boolean> unRegistrationSuccessful,
			@WebParam(mode = Mode.OUT, name = "message", targetNamespace = "") Holder<String> message) {
		List<ServiceRegistrationRecord> records;
		try {
			// get the entire list of current service registration records
			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				// for each record currently in the registry, see if we can find
				// a record with a ServiceIdentification that is equal to one
				// that the user is trying to unregister
				if (RegistrationUtils.serviceIdentificationEqual(
						record.getServiceRecord(),
						serviceRegistrationRecord.getServiceRecord())) {
					// found the service the user wants to unregister, now check
					// that the username and password supplied with this request
					// match the username and password sent with the
					// registration request
					if (RegistrationUtils.authenticationEqual(
							record.getAuthentication(),
							serviceRegistrationRecord.getAuthentication())) {
						try {
							// the username/password match, so remove the record
							// from the registry
							RegistrationUtils
									.removeServiceRegistrationRecord(serviceRegistrationRecord);
						} catch (IOException e) {
							// there was en error removing the record, report
							// this error to the caller
							message.value = "Error Unregistering Service: "
									+ e.getMessage();
							unRegistrationSuccessful.value = false;
							return;
						}
						// removal succeeded
						message.value = "unregistration Successful!";
						unRegistrationSuccessful.value = true;
						return;
					} else {
						// username/passwords do not match
						message.value = "Error Unregistering Service: Username/Password does not match orignial ServiceRegistrationRecord!";
						unRegistrationSuccessful.value = false;
						return;
					}
				}
			}
			// couldn't find matching ServiceRecords
			message.value = "Error Unregistering Service: Service not registered at this registry.";
			unRegistrationSuccessful.value = false;
		} catch (IOException e) {
			message.value = "Error Unregisterig Service: Error reading registry!";
			unRegistrationSuccessful.value = false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@WebResult(name = "serviceRecords", targetNamespace = "")
	@RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRegisteredServices")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/getRegisteredServices")
	@ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRegisteredServicesResponse")
	public List<ServiceRecord> getRegisteredServices() {
		try {
			return RegistrationUtils.getServiceRecords();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulationResponse")
	public String runSimulation(
			@WebParam(name = "simulatorConfiguration", targetNamespace = "") SimulatorConfiguration simulatorConfiguration) {
		// this method must return a runId, even if there is an error
		String runId;
		try {
			String URL = null;
			try {
				// get the webservice WSDL URL for supplied
				// SimulatorIdentification
				URL = RegistrationUtils
						.getUrlForSimulatorIdentification(simulatorConfiguration
								.getSimulatorIdentification());
				if (URL == null) {
					runId = RunUtils.getErrorRunId();
					RunUtils.reportError(runId, "Service not registered.");
					return runId;
				}
			} catch (IOException e) {
				runId = RunUtils.getErrorRunId();
				RunUtils.reportError(runId, "Error reading registry.");
				return runId;
			}
			// run the simulator
			SimulatorService ss = new SimulatorService(new URL(URL));
			SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();
			
			//disable chunking for ZSI
		    Client client = ClientProxy.getClient(port);
	        HTTPConduit http = (HTTPConduit) client.getConduit();
	        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	        httpClientPolicy.setConnectionTimeout(36000);
	        httpClientPolicy.setAllowChunking(false);
	        http.setClient(httpClientPolicy);
			
			return port.run(simulatorConfiguration);
		} catch (MalformedURLException e) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(runId,
					"Problem with SimulatorService:" + e.getMessage());
			return runId;
		}
	}

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "") String runId,
			@WebParam(name = "serviceRecord", targetNamespace = "") ServiceRecord serviceRecord) {

		if (runId.startsWith(RUN_ERROR_PREFIX)) {
			RunStatus rs = new RunStatus();
			rs.setStatus(RunStatusEnum.FAILED);
			rs.setMessage(RunUtils.getError(runId));
			return rs;
		}

		String URL = null;
		try {
			// get the webservice WSDL URL for supplied
			// SimulatorIdentification
			if (serviceRecord.getSimulatorIdentification() != null) {
				URL = RegistrationUtils
						.getUrlForSimulatorIdentification(serviceRecord
								.getSimulatorIdentification());
				SimulatorService ss = new SimulatorService(new URL(URL));
				SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();
				return port.getRunStatus(runId);
			} else if (serviceRecord.getVisualizerIdentification() != null) {
				// not done yet
				RunStatus rs = new RunStatus();
				rs.setMessage("Not implemented yet...");
				rs.setStatus(RunStatusEnum.FAILED);
				return rs;
			} else {
				RunStatus rs = new RunStatus();
				rs.setMessage("Not implemented yet...");
				rs.setStatus(RunStatusEnum.FAILED);
				return rs;
			}
		} catch (IOException e) {
			RunStatus rs = new RunStatus();
			rs.setMessage("Error geting remote runStatus!");
			rs.setStatus(RunStatusEnum.FAILED);
			return rs;
		}
	}

	public static void main(String[] args) {
		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
		Authentication auth = new Authentication();
		auth.setRequesterId("John");
		auth.setRequesterPassword("password");
		srr.setAuthentication(auth);
		SimulatorIdentification sid = new SimulatorIdentification();

		sid.setSimulatorName("SEIR");
		sid.setSimulatorDeveloper("U. Pitt");
		sid.setSimulatorVersion("2.32 beta");

		ServiceRecord sr = new ServiceRecord();
		sr.setSimulatorIdentification(sid);
		srr.setServiceRecord(sr);
		srr.setUrl("www.google.com");

		Holder<Boolean> success = new Holder<Boolean>();
		Holder<String> msg = new Holder<String>();
		ApolloServiceImpl apollo = new ApolloServiceImpl();
		apollo.registerService(srr, success, msg);
		System.out.println(msg.value);

		apollo.unRegisterService(srr, success, msg);
		System.out.println(msg.value);
	}

	static {
		Map<String, String> env = System.getenv();
		APOLLO_DIR = env.get("APOLLO_WORK_DIR");
		if (APOLLO_DIR != null) {
			if (!APOLLO_DIR.endsWith(File.separator)) {
				APOLLO_DIR += File.separator;
			}
			System.out.println("APOLLO_DIR is now:" + APOLLO_DIR);
		} else {
			System.out.println("APOLLO_DIR environment variable not found!");
			APOLLO_DIR = "";
		}

	}

	@Override
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualizationResponse")
	public void runVisualization(
			@WebParam(name = "visualizerConfiguration", targetNamespace = "") VisualizerConfiguration visualizerConfiguration,
			@WebParam(mode = Mode.OUT, name = "runId", targetNamespace = "") Holder<String> runId,
			@WebParam(mode = Mode.OUT, name = "visualizerOutputResource", targetNamespace = "") Holder<List<VisualizerOutputResource>> visualizerOutputResource) {

		try {
			String URL = null;
			try {
				// get the webservice WSDL URL for supplied
				// SimulatorIdentification
				URL = RegistrationUtils
						.getUrlForVisualizerIdentification(visualizerConfiguration
								.getVisualizerIdentification());
				if (URL == null) {
					runId.value = RunUtils.getErrorRunId();
					RunUtils.reportError(runId.value, "Service not registered.");
					return;
				}
			} catch (IOException e) {
				runId.value = RunUtils.getErrorRunId();
				RunUtils.reportError(runId.value, "Error reading registry.");
				return;
			}
			// run the simulator
			VisualizerService ss = new VisualizerService(new URL(URL));
			VisualizerServiceEI port = ss.getVisualizerServiceEndpoint();

			//disable chunking for ZSI
		    Client client = ClientProxy.getClient(port);
	        HTTPConduit http = (HTTPConduit) client.getConduit();
	        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	        httpClientPolicy.setConnectionTimeout(36000);
	        httpClientPolicy.setAllowChunking(false);
	        http.setClient(httpClientPolicy);
			
			Holder<String> runIdHolder = new Holder<String>();

			Holder<List<VisualizerOutputResource>> visualizerOutputResourceHolder = new Holder<List<VisualizerOutputResource>>();
			visualizerOutputResourceHolder.value = new ArrayList<VisualizerOutputResource>();

			System.out.println("Running the visualization...");
			port.run(visualizerConfiguration, runIdHolder,
					visualizerOutputResourceHolder);
			runId.value = runIdHolder.value;
			visualizerOutputResource.value = visualizerOutputResourceHolder.value;
		} catch (MalformedURLException e) {
			runId.value = RunUtils.getErrorRunId();
			RunUtils.reportError(runId.value,
					"Problem with SimulatorService:" + e.getMessage());
			return;
		}

	}
}
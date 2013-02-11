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

import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.apolloservice.ApolloServiceEI;
import edu.pitt.apollo.types.Authentication;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SimulatorIdentification;
import edu.pitt.apollo.types.VisualizerConfiguration;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService", endpointInterface = "edu.pitt.apollo.service.apolloservice.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

	public static String REGISTRY = "registered_services.xml";

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
			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				if (RegistrationUtils.serviceIdentificationEqual(
						record.getServiceRecord(),
						serviceRegistrationRecord.getServiceRecord())) {
					// found the service they want to unregister, now check the
					// passwords
					if (RegistrationUtils.authenticationEqual(
							record.getAuthentication(),
							serviceRegistrationRecord.getAuthentication())) {
						try {
							RegistrationUtils
									.removeServiceRegistrationRecord(serviceRegistrationRecord);
						} catch (IOException e) {
							message.value = e.getMessage();
							unRegistrationSuccessful.value = false;
							return;
						}
						message.value = "unRegistration Successful!";
						unRegistrationSuccessful.value = true;
						return;
					} else { // passwords do not match
						message.value = "Username/Password does not match orignial ServiceRegistrationRecord!";
						unRegistrationSuccessful.value = false;
						return;
					}
				}
				message.value = "Service not registered at this registry.";
				unRegistrationSuccessful.value = false;
			}
		} catch (IOException e) {
			message.value = "Error reading registry!";
			unRegistrationSuccessful.value = false;
		}
	}

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulationResponse")
	public String runSimulation(
			@WebParam(name = "simulatorConfiguration", targetNamespace = "") SimulatorConfiguration simulatorConfiguration) {
		// TODO Auto-generated method stub
		return "This is not an error!";
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
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		RunStatus rs = new RunStatus();
		rs.setMessage("Hello from apolloservice");
		rs.setStatus(RunStatusEnum.COMPLETED);
		return rs;
	}

	@Override
	@RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/registerService")
	@ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterServiceResponse")
	public void registerService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord,
			@WebParam(mode = Mode.OUT, name = "registrationSuccessful", targetNamespace = "") Holder<Boolean> registrationSuccessful,
			@WebParam(mode = Mode.OUT, name = "message", targetNamespace = "") Holder<String> message) {

		message.value = null;
		// synchronized (this) {
		List<ServiceRegistrationRecord> records;
		try {

			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				if (RegistrationUtils.serviceIdentificationEqual(
						record.getServiceRecord(),
						serviceRegistrationRecord.getServiceRecord())) {
					message.value = "Service is already registered.  Please unRegisterService to make changes to the existing ServiceRecord.";
					registrationSuccessful.value = false;
				}
				if (message.value == null)
					if (RegistrationUtils.serviceUrlEqual(record,
							serviceRegistrationRecord)) {
						message.value = "URL is already registered.";
						registrationSuccessful.value = false;

					}
			}

			// if we are here, it looks like a valid registration
			if (message.value == null) {
				try {
					RegistrationUtils
							.addServiceRegistrationRecord(serviceRegistrationRecord);
					message.value = "Service Registration Successful!";
					registrationSuccessful.value = true;
				} catch (IOException e) {
					message.value = e.getMessage();
					registrationSuccessful.value = false;
				}
			}
		} catch (IOException e) {
			message.value = "Error reading registry!";
			registrationSuccessful.value = false;
		}

	}

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualizationResponse")
	public String runVisualization(
			@WebParam(name = "visualizerConfiguration", targetNamespace = "") VisualizerConfiguration visualizerConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
		Authentication auth = new Authentication();
		auth.setRequesterId("John");
		auth.setRequesterPassword("password");
		srr.setAuthentication(auth);
		SimulatorIdentification sid = new SimulatorIdentification();

		sid.setSimulatorName("SEIR");
		sid.setSimulatorDeveloper("John Lv");
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

}
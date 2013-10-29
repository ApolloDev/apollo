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
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.ConfigScope;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.Db4oUUID;

import edu.pitt.apollo.service.apolloservice._10._28._2013.ApolloServiceEI;
import edu.pitt.apollo.service.simulatorservice._10._28._2013.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice._10._28._2013.SimulatorServiceV131;
import edu.pitt.apollo.service.syntheticpopulationservice._10._28._2013.SyntheticPopulationServiceEI;
import edu.pitt.apollo.service.syntheticpopulationservice._10._28._2013.SyntheticPopulationServiceV131;
import edu.pitt.apollo.service.visualizerservice._10._28._2013.VisualizerServiceEI;
import edu.pitt.apollo.service.visualizerservice._10._28._2013.VisualizerServiceV131;
import edu.pitt.apollo.types._10._28._2013.ApolloIndexableItem;
import edu.pitt.apollo.types._10._28._2013.ApolloSoftwareType;
import edu.pitt.apollo.types._10._28._2013.BatchRunResult;
import edu.pitt.apollo.types._10._28._2013.BatchRunSimulatorConfiguration;
import edu.pitt.apollo.types._10._28._2013.CuratedLibraryItem;
import edu.pitt.apollo.types._10._28._2013.CuratedLibraryItemContainer;
import edu.pitt.apollo.types._10._28._2013.FixedStartTime;
import edu.pitt.apollo.types._10._28._2013.RunAndSoftwareIdentification;
import edu.pitt.apollo.types._10._28._2013.RunStatus;
import edu.pitt.apollo.types._10._28._2013.RunStatusEnum;
import edu.pitt.apollo.types._10._28._2013.SchoolClosureControlMeasure;
import edu.pitt.apollo.types._10._28._2013.SchoolClosureTargetFacilities;
import edu.pitt.apollo.types._10._28._2013.ServiceRecord;
import edu.pitt.apollo.types._10._28._2013.ServiceRegistrationRecord;
import edu.pitt.apollo.types._10._28._2013.ServiceRegistrationResult;
import edu.pitt.apollo.types._10._28._2013.SimulatorConfiguration;
import edu.pitt.apollo.types._10._28._2013.SyntheticPopulationConfiguration;
import edu.pitt.apollo.types._10._28._2013.SyntheticPopulationResult;
import edu.pitt.apollo.types._10._28._2013.VisualizerConfiguration;
import edu.pitt.apollo.types._10._28._2013.VisualizerResult;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v1.3.1", endpointInterface = "edu.pitt.apollo.service.apolloservice._10._28._2013.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

	private static final String REGISTRY_FILENAME = "registered_services.xml";
	private static final String ERROR_FILENAME = "run_errors.txt";
	private static final String RUN_ERROR_PREFIX = "ApolloServiceError";

	private static String APOLLO_DIR = "";
	private static ObjectContainer db4o;

	static String getRegistryFilename() {
		return APOLLO_DIR + REGISTRY_FILENAME;
	}

	static String getErrorFilename() {
		return APOLLO_DIR + ERROR_FILENAME;
	}

	static String getRunErrorPrefix() {
		return RUN_ERROR_PREFIX;
	}

	private ByteArrayOutputStream getJSONBytes(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
			mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			mapper.writeValue(baos, obj);

			return baos;
		} catch (IOException ex) {
			System.err
					.println("IO Exception JSON encoding and getting bytes from SimulatorConfiguration");
			return null;
		}
	}

	private static String getMd5HashFromString(String string) {
		return DigestUtils.md5Hex(string);
	}

	@Override
	@WebResult(name = "uuids", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsGivenType", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetUuidsForLibraryItemsGivenType")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/getUuidsForLibraryItemsGivenType")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsGivenTypeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetUuidsForLibraryItemsGivenTypeResponse")
	public List<String> getUuidsForLibraryItemsGivenType(
			@WebParam(name = "type", targetNamespace = "") String type) {
		List<String> resultList = new ArrayList<String>();

		CuratedLibraryItem cli = new CuratedLibraryItem();
		final ObjectSet<CuratedLibraryItem> allItems = db4o.queryByExample(cli);

		for (CuratedLibraryItem item : allItems) {
			if (item.getItemType().equals(type)) {
				resultList.add(item.getItemUuid());
			}
		}
		return resultList;
	}

	@Override
	@WebResult(name = "uuids", targetNamespace = "")
	@RequestWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTime", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetUuidsForLibraryItemsCreatedSinceDateTime")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/getUuidsForLibraryItemsCreatedSinceDateTime")
	@ResponseWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTimeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetUuidsForLibraryItemsCreatedSinceDateTimeResponse")
	public List<String> getUuidsForLibraryItemsCreatedSinceDateTime(
			@WebParam(name = "creationDateTime", targetNamespace = "") XMLGregorianCalendar creationDateTime) {
		List<String> resultList = new ArrayList<String>();

		CuratedLibraryItem cli = new CuratedLibraryItem();
		final ObjectSet<CuratedLibraryItem> allItems = db4o.queryByExample(cli);

		for (CuratedLibraryItem item : allItems) {
			int c = item.getItemCreationTime().compare(creationDateTime);
			if ((c == DatatypeConstants.EQUAL)
					|| (c == DatatypeConstants.GREATER)) {
				resultList.add(item.getItemUuid());
			}
		}
		return resultList;
	}

	@Override
	@WebResult(name = "curatedLibraryItemContainer", targetNamespace = "")
	@RequestWrapper(localName = "getLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetLibraryItem")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/getLibraryItem")
	@ResponseWrapper(localName = "getLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetLibraryItemResponse")
	public CuratedLibraryItemContainer getLibraryItem(
			@WebParam(name = "uuid", targetNamespace = "") String uuid) {
		long longPart = Long.valueOf(uuid.split(" ")[1]);
		String sig = uuid.split(" ")[0];
		byte[] signaturePart = new byte[sig.length()];
		for (int i = 0; i < sig.length(); i++) {
			signaturePart[i] = (byte) sig.charAt(i);
		}
		Db4oUUID db4oUuid = new Db4oUUID(longPart, signaturePart);
		Object o = db4o.ext().getByUUID(db4oUuid);

		CuratedLibraryItemContainer result = new CuratedLibraryItemContainer();
		result.setApolloIndexableItem((ApolloIndexableItem) o);
		CuratedLibraryItem cli = new CuratedLibraryItem();
		cli.setItemUuid(uuid);
		ObjectSet<Object> r = db4o.queryByExample(cli);
		CuratedLibraryItem item = (CuratedLibraryItem) r.get(0);
		db4o.activate(item, 100);
		db4o.activate(o, 100);

		result.setCuratedLibraryItem(item);
		return result;
	}

	@Override
	@WebResult(name = "uuid", targetNamespace = "")
	@RequestWrapper(localName = "addLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.AddLibraryItem")
	@WebMethod
	@ResponseWrapper(localName = "addLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.AddLibraryItemResponse")
	public String addLibraryItem(
			@WebParam(name = "apolloIndexableItem", targetNamespace = "") ApolloIndexableItem apolloIndexableItem,
			@WebParam(name = "itemDescription", targetNamespace = "") String itemDescription,
			@WebParam(name = "itemSource", targetNamespace = "") String itemSource,
			@WebParam(name = "itemType", targetNamespace = "") String itemType,
			@WebParam(name = "itemIndexingLabels", targetNamespace = "") List<String> itemIndexingLabels) {

		String apolloUuid = "";
		db4o.store(apolloIndexableItem);
		db4o.commit();
		Db4oUUID uuid = db4o.ext().getObjectInfo(apolloIndexableItem).getUUID();

		for (int i = 0; i < uuid.getSignaturePart().length; i++) {
			apolloUuid += (char) uuid.getSignaturePart()[i];
		}
		apolloUuid += " " + uuid.getLongPart();

		// Db4oUUId u = new Db4oUUID(longPart_, signaturePart_)
		System.out.println("uuid is " + apolloUuid);

		GregorianCalendar c = new GregorianCalendar();
		XMLGregorianCalendar date;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			CuratedLibraryItem cli = new CuratedLibraryItem();
			cli.setItemCreationTime(date);
			cli.setItemDescription(itemDescription);
			cli.setItemSource(itemSource);
			cli.setItemType(itemType);
			cli.setItemUuid(apolloUuid);
			cli.getItemIndexingLabels().addAll(itemIndexingLabels);
			db4o.store(cli);
			db4o.commit();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return apolloUuid;

	}

	@Override
	@WebResult(name = "configurationFile", targetNamespace = "")
	@RequestWrapper(localName = "getConfigurationFileForRun", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetConfigurationFileForRun")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/getConfigurationFileForRun")
	@ResponseWrapper(localName = "getConfigurationFileForRunResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetConfigurationFileForRunResponse")
	public String getConfigurationFileForRun(
			@WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {
		String simName = runAndSoftwareIdentification.getSoftwareId()
				.getSoftwareName();
		try {
			ServiceRecord serviceRecord = RegistrationUtils
					.getServiceRecordForSoftwareId(runAndSoftwareIdentification
							.getSoftwareId());

			URL url = new URL(serviceRecord.getUrl());

			// get the webservice WSDL URL for supplied
			// SimulatorIdentification
			if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.SIMULATOR) {
				SimulatorServiceV131 ss = new SimulatorServiceV131(url);
				SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();
				return port
						.getConfigurationFileForRun(runAndSoftwareIdentification
								.getRunId());
			} else if (serviceRecord.getSoftwareIdentification()
					.getSoftwareType() == ApolloSoftwareType.VISUALIZER) {
				VisualizerServiceV131 ss = new VisualizerServiceV131(url);
				VisualizerServiceEI port = ss.getVisualizerServiceEndpoint();
				return port
						.getConfigurationFileForRun(runAndSoftwareIdentification
								.getRunId());
			} else if (serviceRecord.getSoftwareIdentification()
					.getSoftwareType() == ApolloSoftwareType.SYNTHETIC_POPULATION_GENERATOR) {
				SyntheticPopulationServiceV131 ss = new SyntheticPopulationServiceV131(
						url);
				SyntheticPopulationServiceEI port = ss
						.getSyntheticPopulationServiceEndpoint();
				return port
						.getConfigurationFileForRun(runAndSoftwareIdentification
								.getRunId());
			} else {
				return "getConfigurationFile() not implemented for this service type.";
			}
		} catch (IOException e) {
			return "General error occurred on server.";
		}
	}

	@Override
	@WebResult(name = "batchRunSimulatorResult", targetNamespace = "")
	@RequestWrapper(localName = "batchRunSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.BatchRunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/batchRunSimulation")
	@ResponseWrapper(localName = "batchRunSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.BatchRunSimulationResponse")
	public BatchRunResult batchRunSimulation(
			@WebParam(name = "batchRunSimulatorConfiguration", targetNamespace = "") BatchRunSimulatorConfiguration batchRunSimulatorConfiguration) {
		BatchRunResult bsr = new BatchRunResult();

		String runId;
		try {
			URL url = null;
			try {
				// get the webservice WSDL URL for supplied
				// SimulatorIdentification
				url = RegistrationUtils
						.getUrlForSoftwareIdentification(batchRunSimulatorConfiguration
								.getSoftwareIdentification());
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
			SimulatorServiceV131 ss = new SimulatorServiceV131(url);
			SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();

			// disable chunking for ZSI
			Client client = ClientProxy.getClient(port);
			HTTPConduit http = (HTTPConduit) client.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(36000);
			httpClientPolicy.setAllowChunking(false);
			http.setClient(httpClientPolicy);

			return port.batchRun(batchRunSimulatorConfiguration);
		} catch (Exception e) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(runId,
					"Problem with SimulatorService:" + e.getMessage());
			bsr.setRunId(runId);
			return bsr;
		}

	}

	@Override
	@WebResult(name = "syntheticPopulationResult", targetNamespace = "")
	@RequestWrapper(localName = "generateSyntheticPopulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GenerateSyntheticPopulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/generateSyntheticPopulation")
	@ResponseWrapper(localName = "generateSyntheticPopulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GenerateSyntheticPopulationResponse")
	public SyntheticPopulationResult generateSyntheticPopulation(
			@WebParam(name = "syntheticPopulationConfiguration", targetNamespace = "") SyntheticPopulationConfiguration syntheticPopulationConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "serviceRegistrationResult", targetNamespace = "")
	@RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.UnRegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/unRegisterService")
	@ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.UnRegisterServiceResponse")
	public ServiceRegistrationResult unRegisterService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
		ServiceRegistrationResult result = new ServiceRegistrationResult();

		List<ServiceRegistrationRecord> records;
		try {
			// get the entire list of current service registration records
			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				// for each record currently in the registry, see if we can find
				// a record with a ServiceIdentification that is equal to one
				// that the user is trying to unregister
				if (RegistrationUtils.softwareIdentificationEqual(
						record.getSoftwareIdentification(),
						serviceRegistrationRecord.getSoftwareIdentification())) {
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
							result.setMessage("Error Unregistering Service: "
									+ e.getMessage());
							result.setActionSuccessful(false);
							return result;
						}
						// removal succeeded
						result.setMessage("unregistration Successful!");
						result.setActionSuccessful(true);
						return result;
					} else {
						// username/passwords do not match
						result.setMessage("Error Unregistering Service: Username/Password does not match orignial ServiceRegistrationRecord!");
						result.setActionSuccessful(false);
						return result;
					}
				}
			}
			// couldn't find matching ServiceRecords
			result.setMessage("Error Unregistering Service: Service not registered at this registry.");
			result.setActionSuccessful(false);
			return result;
		} catch (IOException e) {
			result.setMessage("Error Unregistering Service: Error reading registry!");
			result.setActionSuccessful(false);
			return result;
		}

	}

	@Override
	@WebResult(name = "serviceRegistrationResult", targetNamespace = "")
	@RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.RegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/registerService")
	@ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.RegisterServiceResponse")
	public ServiceRegistrationResult registerService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
		ServiceRegistrationResult result = new ServiceRegistrationResult();

		List<ServiceRegistrationRecord> records;
		try {

			records = RegistrationUtils.getServiceRegistrationRecords();

			for (ServiceRegistrationRecord record : records) {
				if (RegistrationUtils.softwareIdentificationEqual(
						record.getSoftwareIdentification(),
						serviceRegistrationRecord.getSoftwareIdentification())) {
					result.setMessage("Service is already registered.  Please unRegisterService to make changes to the existing ServiceRecord.");
					result.setActionSuccessful(false);
					return result;
				}

				if (RegistrationUtils.serviceUrlEqual(record,
						serviceRegistrationRecord)) {
					result.setMessage("URL is already registered.");
					result.setActionSuccessful(false);
					return result;
				}
			}

			// if we are here, it looks like a valid registration
			try {
				RegistrationUtils
						.addServiceRegistrationRecord(serviceRegistrationRecord);
				result.setMessage("Service Registration Successful!");
				result.setActionSuccessful(true);
			} catch (IOException e) {
				result.setMessage("Error registering service: "
						+ e.getMessage());
				result.setActionSuccessful(false);
			}

		} catch (IOException e) {
			result.setMessage("Error reading registry!");
			result.setActionSuccessful(false);
		}
		return result;

	}

	@Override
	@WebResult(name = "visualizerResult", targetNamespace = "")
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.RunVisualizationResponse")
	public VisualizerResult runVisualization(
			@WebParam(name = "visualizerConfiguration", targetNamespace = "") VisualizerConfiguration visualizerConfiguration) {
		String runId;
		ByteArrayOutputStream baos = getJSONBytes(visualizerConfiguration);
		String visConfigHash = getMd5HashFromString(baos.toString());
		VisualizerResult result;

		// check the cache
		try {
			result = DbUtils.checkVisualizerCache(visConfigHash);
			if (result != null) {

				// check the status of the run
				runId = result.getRunId();
				RunAndSoftwareIdentification rasid = new RunAndSoftwareIdentification();
				rasid.setRunId(runId);
				rasid.setSoftwareId(visualizerConfiguration
						.getVisualizerIdentification());
				RunStatus status = getRunStatus(rasid);
				RunStatusEnum statusEnum = status.getStatus();

				if (statusEnum.equals(RunStatusEnum.FAILED)) {
					DbUtils.deleteFromVisualizerCache(visConfigHash);
				} else {
					return result;
				}

			}
		} catch (ClassNotFoundException ex) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(runId,
					"Problem with VisualizerService: ClassNotFoundException"
							+ ex.getMessage());
			result = new VisualizerResult();
			result.setRunId(runId);
			ex.printStackTrace();

			return result;
		} catch (SQLException ex) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(
					runId,
					"Problem with VisualizerService: SQLException"
							+ ex.getMessage());
			result = new VisualizerResult();
			result.setRunId(runId);
			ex.printStackTrace();

			return result;
		}

		try {
			result = new VisualizerResult();
			URL url = null;
			try {
				// get the webservice WSDL URL for supplied
				// SimulatorIdentification
				url = RegistrationUtils
						.getUrlForSoftwareIdentification(visualizerConfiguration
								.getVisualizerIdentification());
				if (url == null) {
					result.setRunId(RunUtils.getErrorRunId());
					RunUtils.reportError(result.getRunId(),
							"Service not registered.");
					return result;
				}
			} catch (IOException e) {
				result.setRunId(RunUtils.getErrorRunId());
				RunUtils.reportError(result.getRunId(),
						"Error reading registry.");
				return result;
			}
			// run the simulator
			VisualizerServiceV131 ss = new VisualizerServiceV131(url);
			VisualizerServiceEI port = ss.getVisualizerServiceEndpoint();

			// disable chunking for ZSI
			Client client = ClientProxy.getClient(port);
			HTTPConduit http = (HTTPConduit) client.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(36000);
			httpClientPolicy.setAllowChunking(false);
			http.setClient(httpClientPolicy);

			System.out.println("Running the visualization...");
			VisualizerResult visualizerResult = port
					.run(visualizerConfiguration);
			runId = visualizerResult.getRunId();

			// cacheVisualizerResult(runId, visConfigHash, visualizerResult,
			// cacheFilePath);

			// cache the visualizer result
			try {
				String cacheId = DbUtils.insertIntoVisualizerCache(runId,
						visConfigHash);
				DbUtils.insertIntoVisualizerResultsCache(cacheId,
						visualizerResult.getVisualizerOutputResource());
			} catch (ClassNotFoundException ex) {
				RunUtils.reportError(visualizerResult.getRunId(),
						"Problem with VisualizerService: ClassNotFoundException"
								+ ex.getMessage());
				ex.printStackTrace();
			} catch (SQLException ex) {
				RunUtils.reportError(
						visualizerResult.getRunId(),
						"Problem with VisualizerService: SQLException"
								+ ex.getMessage());
				ex.printStackTrace();
			}

			return visualizerResult;
		} catch (Exception e) {
			VisualizerResult visualizerResult = new VisualizerResult();
			visualizerResult.setRunId(RunUtils.getErrorRunId());

			RunUtils.reportError(visualizerResult.getRunId(),
					"Problem with VisualizerService:" + e.getMessage());
			e.printStackTrace();
			return visualizerResult;
		}

	}

	@Override
	@WebResult(name = "serviceRecords", targetNamespace = "")
	@RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetRegisteredServices")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/getRegisteredServices")
	@ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetRegisteredServicesResponse")
	public List<ServiceRecord> getRegisteredServices() {
		try {
			return RegistrationUtils.getRegisteredSoftware();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {
		if (runAndSoftwareIdentification.getRunId()
				.startsWith(RUN_ERROR_PREFIX)) {
			RunStatus rs = new RunStatus();
			rs.setStatus(RunStatusEnum.FAILED);
			rs.setMessage(RunUtils.getError(runAndSoftwareIdentification
					.getRunId()));
			return rs;
		}

		try {
			ServiceRecord serviceRecord = RegistrationUtils
					.getServiceRecordForSoftwareId(runAndSoftwareIdentification
							.getSoftwareId());

			URL url = new URL(serviceRecord.getUrl());

			// get the webservice WSDL URL for supplied
			// SimulatorIdentification
			if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.SIMULATOR) {
				SimulatorServiceV131 ss = new SimulatorServiceV131(url);
				SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();
				return port.getRunStatus(runAndSoftwareIdentification
						.getRunId());
			} else if (serviceRecord.getSoftwareIdentification()
					.getSoftwareType() == ApolloSoftwareType.VISUALIZER) {
				VisualizerServiceV131 ss = new VisualizerServiceV131(url);
				VisualizerServiceEI port = ss.getVisualizerServiceEndpoint();
				return port.getRunStatus(runAndSoftwareIdentification
						.getRunId());
			} else if (serviceRecord.getSoftwareIdentification()
					.getSoftwareType() == ApolloSoftwareType.SYNTHETIC_POPULATION_GENERATOR) {
				SyntheticPopulationServiceV131 ss = new SyntheticPopulationServiceV131(
						url);
				SyntheticPopulationServiceEI port = ss
						.getSyntheticPopulationServiceEndpoint();
				return port.getRunStatus(runAndSoftwareIdentification
						.getRunId());
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

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/10/28/2013/", className = "edu.pitt.apollo.service.apolloservice._10._28._2013.RunSimulationResponse")
	public String runSimulation(
			@WebParam(name = "simulatorConfiguration", targetNamespace = "") SimulatorConfiguration simulatorConfiguration) {
		// this method must return a runId, even if there is an error
		String runId;
		ByteArrayOutputStream baos = getJSONBytes(simulatorConfiguration);
		String simConfigHash = getMd5HashFromString(baos.toString());
		// check for cached result
		try {
			runId = DbUtils.checkRunCache(simConfigHash);
			if (runId != null) {

				// check the status of the run
				RunAndSoftwareIdentification rasid = new RunAndSoftwareIdentification();
				rasid.setRunId(runId);
				rasid.setSoftwareId(simulatorConfiguration
						.getSimulatorIdentification());
				RunStatus status = getRunStatus(rasid);
				RunStatusEnum statusEnum = status.getStatus();

				if (statusEnum.equals(RunStatusEnum.FAILED)) {
					DbUtils.deleteFromRunCache(simConfigHash);
				} else {
					return runId;
				}
			}
		} catch (ClassNotFoundException ex) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(runId,
					"Problem with SimulatorService: ClassNotFoundException: "
							+ ex.getMessage());
			ex.printStackTrace();
			return runId;
		} catch (SQLException ex) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(
					runId,
					"Problem with SimulatorService: SQLException: "
							+ ex.getMessage());
			ex.printStackTrace();
			return runId;
		}

		try {
			URL url = null;
			try {
				// get the webservice WSDL URL for supplied
				// SimulatorIdentification
				url = RegistrationUtils
						.getUrlForSoftwareIdentification(simulatorConfiguration
								.getSimulatorIdentification());
				System.out.println("URL resturned: " + url.toString());
				if (url == null) {
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
			SimulatorServiceV131 ss = new SimulatorServiceV131(url);
			SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();

			// disable chunking for ZSI
			Client client = ClientProxy.getClient(port);
			HTTPConduit http = (HTTPConduit) client.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(36000);
			httpClientPolicy.setAllowChunking(false);
			http.setClient(httpClientPolicy);

			System.out.println("Apollo Service is running simulator with URL "
					+ url);
			runId = port.run(simulatorConfiguration);
			System.out.println("Returned run ID is + " + runId);

			try {
				DbUtils.insertIntoRunCache(runId, simConfigHash);
			} catch (ClassNotFoundException ex) {
				runId = RunUtils.getErrorRunId();
				RunUtils.reportError(runId,
						"Problem with SimulatorService: ClassNotFoundException: "
								+ ex.getMessage());
				ex.printStackTrace();
			} catch (SQLException ex) {
				runId = RunUtils.getErrorRunId();
				RunUtils.reportError(
						runId,
						"Problem with SimulatorService: SQLException: "
								+ ex.getMessage());
				ex.printStackTrace();
				return runId;
			}

			return runId;
		} catch (Exception e) {
			runId = RunUtils.getErrorRunId();
			RunUtils.reportError(runId,
					"Problem with SimulatorService:" + e.getMessage());
			e.printStackTrace();
			return runId;
		}

	}

	public static void main(String args[])
			throws DatatypeConfigurationException {
		ApolloServiceImpl apollo = new ApolloServiceImpl();
		SchoolClosureControlMeasure scm = new SchoolClosureControlMeasure();
		FixedStartTime fst = new FixedStartTime();
		fst.setStartTime(new BigInteger("0"));
		fst.setStopTime(new BigInteger("100"));
		scm.setControlMeasureStartTime(fst);
		scm.setControlMeasureCompliance(0.7);
		scm.setControlMeasureNamedPrioritizationScheme("Close em all");
		scm.setControlMeasureReactiveEndPointFraction(0.32);
		scm.setControlMeasureResponseDelay(3d);
		scm.setDescription("Test.");
		scm.setSchoolClosureDuration(new BigInteger("35"));
		scm.setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities.ALL);
		List<String> indexingValues = new ArrayList<String>();
		indexingValues.add("Nigeria");
		indexingValues.add("Faket");
		indexingValues.add("SchoolClosureControlMeasure");
		indexingValues.add("2013");

		String uuid = apollo.addLibraryItem(scm, scm.getDescription(),
				"Made up by John Levander", "SchoolClosureControlMeasure",
				indexingValues);

		CuratedLibraryItemContainer clic = apollo.getLibraryItem(uuid);
		System.out.println(clic.getCuratedLibraryItem().getItemCreationTime()
				.toString());
		GregorianCalendar c = new GregorianCalendar();
		// c.add(Calendar.YEAR, -1);
		XMLGregorianCalendar date;

		date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		List<String> l = apollo
				.getUuidsForLibraryItemsCreatedSinceDateTime(date);
		System.out.println(l);

		l = apollo
				.getUuidsForLibraryItemsGivenType("SchoolClosureControlMeasure");
		System.out.println(l);

	}

	static {
		Map<String, String> env = System.getenv();
		APOLLO_DIR = env.get("APOLLO_131_WORK_DIR");
		if (APOLLO_DIR != null) {
			if (!APOLLO_DIR.endsWith(File.separator)) {
				APOLLO_DIR += File.separator;
			}
			System.out.println("APOLLO_131_WORK_DIR is now:" + APOLLO_DIR);
		} else {
			System.out.println("APOLLO_131_WORK_DIR environment variable not found!");
			APOLLO_DIR = "";
		}
		EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
		configuration.file().generateUUIDs(ConfigScope.GLOBALLY);
		db4o = Db4oEmbedded.openFile(configuration, APOLLO_DIR + "/db4o_db_131");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		db4o.close();
	}

}

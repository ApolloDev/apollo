/*
 * Copyright 2015 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo;

import static edu.pitt.apollo.GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE;
import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartSimulationMethod;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartVisualizationMethod;
import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.restrunmanagerserviceconnector.RestRunManagerServiceConnector;
import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;
;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.services_common.v4_0.RunResult;
import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.jws.WebParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nem41
 */


public class BrokerServiceImpl implements ContentManagementInterface, RunManagementInterface, JobRunningServiceInterface, SoftwareRegistryInterface {

	private static final String BROKER_SERVICE_PROPERTIES = "broker_service.properties";
	private static final String DATA_SERVICE_URL_PROPERTY_TOKEN = "dataServiceUrl";
	private static final String AUTHENTICATION_USER = "authenticationUser";
	private static final String AUTHENTICATION_PASSWORD = "authenticationPassword";
	private static String runManagerServiceUrl;
	private static String dataServiceUrl;
	private static Authentication dataServiceAuthentication;
	private static RunManagerServiceConnector runManagerServiceConnector;
	private static RunManagerServiceConnector dataServiceConnector;
	protected static final ApolloServiceQueue apolloServiceQueue;
	Logger logger = LoggerFactory.getLogger(BrokerServiceImpl.class);

	static {
		apolloServiceQueue = new ApolloServiceQueue();
	}

	private static RunManagerServiceConnector getRunManagerServiceConnector() throws DatastoreException {
		try {
			if (runManagerServiceConnector == null) {
				runManagerServiceConnector = new RestRunManagerServiceConnector(getRunManagerServiceUrl());
			}

			return runManagerServiceConnector;
		} catch (IOException ex) {
			throw new DatastoreException("IOException loading run manager service connector: " + ex.getMessage());
		}
	}

	protected static String getDataServiceUrl() throws IOException {
		if (dataServiceUrl == null) {
			String apolloDir = ApolloServiceConstants.APOLLO_DIR;

			File configurationFile = new File(apolloDir + File.separator + BROKER_SERVICE_PROPERTIES);
			Properties brokerServiceProperties = new Properties();

			try (InputStream input = new FileInputStream(configurationFile)) {
				// load a properties file
				brokerServiceProperties.load(input);
				dataServiceUrl = brokerServiceProperties.getProperty(DATA_SERVICE_URL_PROPERTY_TOKEN);
			}
		}
		return dataServiceUrl;
	}

	protected static Authentication getDataServiceAuthentication() throws IOException {
		if (dataServiceAuthentication == null) {
			String apolloDir = ApolloServiceConstants.APOLLO_DIR;

			File configurationFile = new File(apolloDir + File.separator + BROKER_SERVICE_PROPERTIES);
			Properties brokerServiceProperties = new Properties();

			try (InputStream input = new FileInputStream(configurationFile)) {
				// load a properties file
				brokerServiceProperties.load(input);

				String authenticationUser = brokerServiceProperties.getProperty(AUTHENTICATION_USER);
				String authenticationPassword = brokerServiceProperties.getProperty(AUTHENTICATION_PASSWORD);

				dataServiceAuthentication = new Authentication();
				dataServiceAuthentication.setRequesterId(authenticationUser);
				dataServiceAuthentication.setRequesterPassword(authenticationPassword);
			}
		}
		return dataServiceAuthentication;
	}

	protected static String getRunManagerServiceUrl() throws IOException {
		if (runManagerServiceUrl == null) {
			String initRunManagerServiceUrl = null;
			Authentication authentication = getDataServiceAuthentication();
			try {
				RunManagerServiceConnector dataServiceConnector = getRunManagerServiceConnector();
				List<ServiceRegistrationRecord> softwareRecords = dataServiceConnector.getListOfRegisteredSoftwareRecords(authentication);

				for (ServiceRegistrationRecord record : softwareRecords) {
					SoftwareIdentification softwareId = record.getSoftwareIdentification();
					if (softwareId.getSoftwareType().equals(ApolloSoftwareTypeEnum.RUN_MANAGER)) {
						initRunManagerServiceUrl = dataServiceConnector.getURLForSoftwareIdentification(softwareId, authentication);
					}
				}

				if (initRunManagerServiceUrl == null) {
					throw new RuntimeException("No registered software with type RUN_MANAGER was found");
				} else {
					runManagerServiceUrl = initRunManagerServiceUrl;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
		return runManagerServiceUrl;
	}

	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DatastoreException {
		getRunManagerServiceConnector().associateContentWithRunId(runId, content, sourceSoftware, destinationSoftware, contentLabel, contentDataFormat, contentDataType, authentication);
	}

	@Override
	public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DatastoreException {
		return getRunManagerServiceConnector().getListOfFilesForRunId(runId, authentication);
	}

	@Override
	public Map<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DatastoreException {
		return getRunManagerServiceConnector().getListOfURLsForRunId(runId, authentication);
	}

	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DatastoreException {
		getRunManagerServiceConnector().removeFileAssociationWithRun(runId, fileId, authentication);
	}

	@Override
	public String getContentForContentId(BigInteger urlId, Authentication authentication) throws DatastoreException {
		return getRunManagerServiceConnector().getContentForContentId(urlId, authentication);
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException {
		return getRunManagerServiceConnector().getURLForSoftwareIdentification(softwareId, authentication);
	}

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		return getRunManagerServiceConnector().getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		return getRunManagerServiceConnector().getSoftwareIdentificationForRun(runId, authentication);
	}

	@Override
	public InsertRunResult insertRun(RunMessage message) throws RunManagementException {
		return getRunManagerServiceConnector().insertRun(message);
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		getRunManagerServiceConnector().updateStatusOfRun(runId, statusEnumToSet, messageToSet, authentication);
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		getRunManagerServiceConnector().updateLastServiceToBeCalledForRun(runId, softwareIdentification, authentication);
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		return getRunManagerServiceConnector().getLastServiceToBeCalledForRun(runId, authentication);
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		getRunManagerServiceConnector().addRunIdsToSimulationGroupForRun(runId, runIds, authentication);
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		getRunManagerServiceConnector().removeRunData(runId, authentication);
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		return getRunManagerServiceConnector().getRunStatus(runId, authentication);
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		try {
			getRunManagerServiceConnector().run(runId, authentication);
		} catch (RunManagementException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		try {
			getRunManagerServiceConnector().terminate(runId, authentication);
		} catch (RunManagementException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public List<ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DatastoreException {
		return getRunManagerServiceConnector().getListOfRegisteredSoftwareRecords(authentication);
	}

	public RunResult runSimulation(RunSimulationMessage runSimulationMessage) {
		try {
			InsertAndStartSimulationMethod method = new InsertAndStartSimulationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
			return method.insertAndStartRun(runSimulationMessage, BrokerServiceImpl.getDataServiceAuthentication());
		} catch (IOException e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}

	public RunResult runVisualization(RunVisualizationMessage runVisualizationMessage) {
		try {
			InsertAndStartVisualizationMethod method = new InsertAndStartVisualizationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
			return method.insertAndStartRun(runVisualizationMessage, BrokerServiceImpl.getDataServiceAuthentication());
		} catch (IOException e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}

	public RunResult runSimulations(
			edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage runSimulationsMessage) {
		InsertAndStartSimulationMethod method = null;
		try {
			method = new InsertAndStartSimulationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
			return method.insertAndStartRun(runSimulationsMessage, BrokerServiceImpl.getDataServiceAuthentication());
		} catch (IOException e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}

	public RunResult runInfectiousDiseaseTransmissionExperiment(RunInfectiousDiseaseTransmissionExperimentMessage runInfectiousDiseaseTransmissionExperimentMessage) {
		InsertAndStartSimulationMethod method = null;
		try {
			method = new InsertAndStartSimulationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
			return method.insertAndStartRun(runInfectiousDiseaseTransmissionExperimentMessage, BrokerServiceImpl.getDataServiceAuthentication());
		} catch (IOException e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
	}
}

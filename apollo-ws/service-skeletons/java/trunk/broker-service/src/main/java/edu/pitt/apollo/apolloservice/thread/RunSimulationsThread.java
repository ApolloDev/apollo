package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.ApolloServiceConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.exception.BatchException;
import edu.pitt.apollo.apolloservice.methods.run.RunMethod;
import edu.pitt.apollo.apolloservice.methods.run.RunMethodForSimulationAndVisualization;
import edu.pitt.apollo.apolloservice.methods.run.RunResultAndSimulationGroupId;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_0.FixedDuration;
import edu.pitt.apollo.types.v3_0_0.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v3_0_0.InfectionStateEnum;
import edu.pitt.apollo.types.v3_0_0.PopulationInfectionAndImmunityCensusDataCell;
import edu.pitt.apollo.types.v3_0_0.ReproductionNumber;
import edu.pitt.apollo.types.v3_0_0.UnitOfTimeEnum;
import java.sql.SQLException;
import javax.xml.ws.WebServiceException;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class RunSimulationsThread extends RunApolloServiceThread {

	private static final String FILE_NAME_FOR_INPUTS_WITH_RUN_IDS = "batch_inputs_with_run_ids.txt";
	private static final SoftwareIdentification brokerServiceSoftwareId;
	private static final int BROKER_SERVICE_SOFTWARE_ID_KEY;
	Exception error = null;
	private final RunSimulationsMessage message;
	BigInteger simulationGroupId;

	private URL configFileUrl;
	private Authentication authentication;

	public static final Random rng = new Random(System.currentTimeMillis());

	private class BatchConfigRecord {

		Double r0;
		Double infectiousPeriod;
		Double latentPeriod;
		Double percentSusceptible;
		Double percentExposed;
		Double percentInfectious;
		Double percentRecovered;

		public BatchConfigRecord(String[] columns) {
			percentSusceptible = Double.valueOf(columns[0]);
			percentExposed = Double.valueOf(columns[1]);
			percentInfectious = Double.valueOf(columns[2]);
			percentRecovered = Double.valueOf(columns[3]);
			r0 = Double.valueOf(columns[4]);
			latentPeriod = Double.valueOf(columns[5]);
			infectiousPeriod = Double.valueOf(columns[6]);
		}
	}

	public static boolean isNonErrorCachedStatus(
			MethodCallStatusEnum methodCallStatusEnum) {
		switch (methodCallStatusEnum) {
			case AUTHENTICATION_FAILURE:
			case FAILED:
			case UNAUTHORIZED:
			case UNKNOWN_RUNID:
				return false;
			default:
				return true;
		}
	}

	public static String getRandomFilenameString() {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			String time = ((Long) System.currentTimeMillis()).toString();
			String rnd = ((Long) rng.nextLong()).toString();
			String toHash = time + rnd;
			md5.update(toHash.getBytes());
			String filename = new BigInteger(1, md5.digest()).toString(16);
			filename = filename + ".txt";
			return filename;
		} catch (NoSuchAlgorithmException e) {
			// This is not likely to happen as MD5 isn't going anywhere.
			return null;
		}
	}

	public static synchronized File getTemporaryFile() {
		File f = new File(getRandomFilenameString());
		if (f.exists()) {
			while (f.exists()) {
				f = new File(getRandomFilenameString());
			}
		}
		return f;
	}

	public static String downloadUrlToFile(URL configFileUrl)
			throws BatchException, IOException {
		ReadableByteChannel rbc;

		try {
			rbc = Channels.newChannel(configFileUrl.openStream());

			File storageFile = getTemporaryFile();
			if (storageFile != null) {
				FileOutputStream fos = new FileOutputStream(storageFile);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				return storageFile.getAbsolutePath();
			} else {
				throw new BatchException(
						"Unable to create temporary storage file for the batch processing.");
			}
		} catch (IOException e) {
			throw new IOException("Error downloading URL:" + configFileUrl
					+ ".  Error was: " + e.getMessage(), e);
		}
	}

	public static RunSimulationMessage populateTemplateWithRecord(
			RunSimulationsMessage template, BatchConfigRecord batchConfigRecord) {

		RunSimulationMessage runSimulationMessage = new RunSimulationMessage();
		runSimulationMessage.setAuthentication(template.getAuthentication());
		runSimulationMessage.setInfectiousDiseaseScenario(template
				.getBaseInfectiousDiseaseScenario());
		runSimulationMessage.setSimulatorIdentification(template
				.getSimulatorIdentification());
		runSimulationMessage.setSimulatorTimeSpecification(template
				.getSimulatorTimeSpecification());

		ReproductionNumber r0 = new ReproductionNumber();
		r0.setExactValue(batchConfigRecord.r0);

		FixedDuration latentPeriod = new FixedDuration();
		latentPeriod.setValue(batchConfigRecord.latentPeriod);
		latentPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);

		FixedDuration infectiousPeriod = new FixedDuration();
		infectiousPeriod.setValue(batchConfigRecord.infectiousPeriod);
		infectiousPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);

		InfectionAcquisitionFromInfectiousHost infection = runSimulationMessage
				.getInfectiousDiseaseScenario().getInfections().get(0)
				.getInfectionAcquisitionsFromInfectiousHosts().get(0);

		infection.setLatentPeriodDuration(latentPeriod);
		infection.setInfectiousPeriodDuration(infectiousPeriod);
		infection.getBasicReproductionNumbers().set(0, r0);

		List<PopulationInfectionAndImmunityCensusDataCell> censusDataCells = runSimulationMessage
				.getInfectiousDiseaseScenario()
				.getPopulationInfectionAndImmunityCensuses().get(0)
				.getCensusData().getCensusDataCells();

		censusDataCells.clear();
		censusDataCells.add(createPiiDataCell(InfectionStateEnum.SUSCEPTIBLE,
				batchConfigRecord.percentSusceptible));
		censusDataCells.add(createPiiDataCell(InfectionStateEnum.LATENT,
				batchConfigRecord.percentExposed));
		censusDataCells.add(createPiiDataCell(InfectionStateEnum.INFECTIOUS,
				batchConfigRecord.percentInfectious));
		censusDataCells.add(createPiiDataCell(InfectionStateEnum.RECOVERED,
				batchConfigRecord.percentRecovered));

		return runSimulationMessage;

	}

	private static PopulationInfectionAndImmunityCensusDataCell createPiiDataCell(
			InfectionStateEnum infectionStateEnum, Double fractionInState) {
		PopulationInfectionAndImmunityCensusDataCell piiDataCell = new PopulationInfectionAndImmunityCensusDataCell();
		piiDataCell.setFractionInState(fractionInState);
		piiDataCell.setInfectionState(infectionStateEnum);
		return piiDataCell;
	}

	public void queueAndTranslateRuns() {
		String filename = null;
		SoftwareIdentification simulatorIdentification = message
				.getSimulatorIdentification();
		try {
			try {
				filename = downloadUrlToFile(configFileUrl);
			} catch (BatchException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"Error downloading batch configuration file, error was: "
						+ simulatorIdentification.getSoftwareName()
						+ ", version: "
						+ simulatorIdentification.getSoftwareVersion()
						+ ", developer: "
						+ simulatorIdentification
						.getSoftwareDeveloper()
						+ " for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;

			} catch (IOException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"Error downloading batch configuration file, error was: "
						+ simulatorIdentification.getSoftwareName()
						+ ", version: "
						+ simulatorIdentification.getSoftwareVersion()
						+ ", developer: "
						+ simulatorIdentification
						.getSoftwareDeveloper()
						+ " for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;

			}

			File inputsFile = new File(filename);
//			String inputsFileWithRunIdsPath = OUTPUT_DIRECTORY + runId + File.separator + FILE_NAME_FOR_INPUTS_WITH_RUN_IDS;
//			String urlForFileWithRunIds = BASE_URL + runId + "/" + FILE_NAME_FOR_INPUTS_WITH_RUN_IDS;

//			File inputFileWithRunIds = new File(inputsFileWithRunIdsPath);
//			inputFileWithRunIds.getParentFile().mkdirs();
			StringBuilder stBuild = new StringBuilder();

			Scanner sc = new Scanner(inputsFile);
			try {
				int lineCounter = 1;
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] params = line.split(",");
					BatchConfigRecord batchConfigRecord = new BatchConfigRecord(
							params);
					if (message instanceof RunSimulationsMessage) {
						RunSimulationMessage currentRunSimulationMessage = populateTemplateWithRecord(
								message, batchConfigRecord);
						RunMethod runMethod = new RunMethodForSimulationAndVisualization(
								authentication,
								message.getSimulatorIdentification(),
								currentRunSimulationMessage);
						RunResultAndSimulationGroupId runResultandSimulationGroupId = runMethod.stageInDatabase(simulationGroupId);
						RunResult runResult = runResultandSimulationGroupId.getRunResult();

						String lineWithRunId = line + "," + runResult.getRunId();
						stBuild.append(lineWithRunId).append("\n");
//						boolean translatorRunSuccessful = TranslatorServiceAccessor
//								.runTranslatorAndReturnIfRunWasSuccessful(
//										runId, dbUtils);
//						
//						if (!translatorRunSuccessful) {
//							return;
//						}
						if (!isNonErrorCachedStatus(runResult.getMethodCallStatus().getStatus())) {
							ApolloServiceErrorHandler
									.writeErrorToErrorFile(
											"Error staging job using line "
											+ String.valueOf(lineCounter)
											+ " of the batch configuration file. "
											+ "Broker returned the following status: "
											+ runResult
											+ simulatorIdentification
											.getSoftwareVersion()
											+ ", developer: "
											+ simulatorIdentification
											.getSoftwareDeveloper()
											+ "  run id " + runId + ".",
											runId);
							return;

						}
					}
					lineCounter++;

				}
				boolean translatorRunSuccessful = TranslatorServiceAccessor
						.runTranslatorAndReturnIfRunWasSuccessful(
								runId, dbUtils);

				if (!translatorRunSuccessful) {
					return;
				}
			} finally {
				sc.close();
			}

			try {
				// add the text data content for the URL
				int dataContentKey = dbUtils.addTextDataContent(stBuild.toString());
				int runDataDescriptionId = dbUtils.getRunDataDescriptionId(ApolloDbUtils.DbContentDataFormatEnum.TEXT, FILE_NAME_FOR_INPUTS_WITH_RUN_IDS,
						ApolloDbUtils.DbContentDataType.CONFIGURATION_FILE, BROKER_SERVICE_SOFTWARE_ID_KEY, ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID);
				dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);

			} catch (ApolloDatabaseException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"ApolloDatabaseException inserting text data content for run: " + ex.getMessage(), runId);
			} catch (ClassNotFoundException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"ClassNotFoundException inserting text data content for run: " + ex.getMessage(), runId);
			} catch (SQLException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"SQLException inserting text data content for run: " + ex.getMessage(), runId);
			}

		} catch (IOException e) {
			logger.error("Error writing error file!: " + e.getMessage());
		}

	}

	public RunSimulationsThread(Authentication authentication,
			RunSimulationsMessage message, BigInteger runId,
			BigInteger simulationGroupId) {
		super(runId);
		this.message = message;
		this.simulationGroupId = simulationGroupId;
		this.authentication = authentication;
		try {
			this.configFileUrl = new URL(message.getBatchConfigurationFile());
		} catch (MalformedURLException e) {
			error = e;
		}
	}

	private void startSimulations() {
		SoftwareIdentification simulatorIdentification = message.getSimulatorIdentification();
		String url = null;
		try {
			try {

				url = dbUtils.getUrlForSoftwareIdentification(simulatorIdentification);
				SimulatorServiceEI simulatorPort;
				try {
					simulatorPort = new SimulatorServiceV300(new URL(url)).getSimulatorServiceEndpoint();
				} catch (Exception e) {
					ApolloServiceErrorHandler.writeErrorToErrorFile(
							"Unable to get simulator port for url: " + url + "\n\tError was: " + e.getMessage(),
							runId);
					return;
				}

				// disable chunking for ZSI
				Client simulatorClient = ClientProxy.getClient(simulatorPort);
				HTTPConduit simulatorHttp = (HTTPConduit) simulatorClient.getConduit();
				HTTPClientPolicy simulatorHttpClientPolicy = new HTTPClientPolicy();
				simulatorHttpClientPolicy.setConnectionTimeout(36000);
				simulatorHttpClientPolicy.setAllowChunking(false);
				simulatorHttp.setClient(simulatorHttpClientPolicy);
				try {
					simulatorPort.runSimulations(runId);
				} catch (WebServiceException e) {
					ApolloServiceErrorHandler.writeErrorToErrorFile("Error calling runSimulation(): " + "\n\tError was: " + e.getMessage(),
							runId);
					return;
				}
			} catch (ApolloDatabaseKeyNotFoundException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"Apollo database key not found attempting to get URL for simulator: "
						+ simulatorIdentification.getSoftwareName() + ", version: "
						+ simulatorIdentification.getSoftwareVersion() + ", developer: "
						+ simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;
			} catch (ClassNotFoundException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"ClassNotFoundException attempting to get URL for simulator: "
						+ simulatorIdentification.getSoftwareName() + ", version: "
						+ simulatorIdentification.getSoftwareVersion() + ", developer: "
						+ simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;
			} catch (MalformedURLException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"MalformedURLException attempting to create port for simulator: "
						+ simulatorIdentification.getSoftwareName() + ", version: "
						+ simulatorIdentification.getSoftwareVersion() + ", developer: "
						+ simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ". URL was: " + url
						+ ". Error message was: " + ex.getMessage(), runId);
				return;
			} catch (SQLException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"SQLException attempting to get URL for simulator: " + simulatorIdentification.getSoftwareName()
						+ ", version: " + simulatorIdentification.getSoftwareVersion() + ", developer: "
						+ simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;
			}
			try {
				dbUtils.updateLastServiceToBeCalledForRun(runId, simulatorIdentification);
			} catch (ApolloDatabaseKeyNotFoundException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile("Apollo database key not found attempting to update last service"
						+ " call for run id " + runId + ": " + ex.getMessage(), runId);
			} catch (SQLException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile("SQLException attempting to update last service" + " call for run id " + runId
						+ ": " + ex.getMessage(), runId);
			} catch (ClassNotFoundException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile("ClassNotFoundException attempting to update last service" + " call for run id "
						+ runId + ": " + ex.getMessage(), runId);
			} catch (ApolloDatabaseException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile("ApolloDatabaseException attempting to update last service" + " call for run id "
						+ runId + ": " + ex.getMessage(), runId);
			}
		} catch (IOException ex) {
			logger.error("Error writing error file!: " + ex.getMessage());

		}
	}

	@Override
	public void run() {
		if (error != null) {
			SoftwareIdentification simulatorIdentification = message
					.getSimulatorIdentification();

			try {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"Error staging batch run for: "
						+ simulatorIdentification.getSoftwareName()
						+ ", version: "
						+ simulatorIdentification.getSoftwareVersion()
						+ ", developer: "
						+ simulatorIdentification
						.getSoftwareDeveloper()
						+ " for run id " + runId + ": "
						+ error.getMessage(), runId);
			} catch (IOException e) {
				logger.error("Error writing error file!: " + e.getMessage());
			}
			return;
		}
		queueAndTranslateRuns();
		startSimulations();

	}

	@Override
	public void setAuthenticationPasswordFieldToBlank() {
		message.getAuthentication().setRequesterPassword("");
	}

	static {

		brokerServiceSoftwareId = new SoftwareIdentification();
		brokerServiceSoftwareId.setSoftwareDeveloper("UPitt");
		brokerServiceSoftwareId.setSoftwareName("Broker Service");
		brokerServiceSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.BROKER);
		brokerServiceSoftwareId.setSoftwareVersion("3.0.0");

		try {
			BROKER_SERVICE_SOFTWARE_ID_KEY = dbUtils.getSoftwareIdentificationKey(brokerServiceSoftwareId);
		} catch (ApolloDatabaseException ex) {
			throw new ExceptionInInitializerError("ApolloDatabaseException getting broker service software key: " + ex.getMessage());
		}
	}

}

package edu.pitt.apollo.apolloservice.thread;

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

public class RunSimulationsThread extends RunApolloServiceThread {

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

			Scanner sc = new Scanner(new File(filename));
			try {
				int lineCounter = 1;
				while (sc.hasNextLine()) {
					String[] params = sc.nextLine().split(",");
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
//						boolean translatorRunSuccessful = TranslatorServiceAccessor
//								.runTranslatorAndReturnIfRunWasSuccessful(
//										runId, dbUtils);
//						
//						if (!translatorRunSuccessful) {
//							return;
//						}
						
						if (!isNonErrorCachedStatus(runResult.getMethodCallStatus().getStatus())) 
						{
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

	}

	@Override
	public void setAuthenticationPasswordFieldToBlank() {
		message.getAuthentication().setRequesterPassword("");
	}

}

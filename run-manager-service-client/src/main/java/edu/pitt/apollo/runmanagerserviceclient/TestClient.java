package edu.pitt.apollo.runmanagerserviceclient;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.runmanagerservice.RunManagerServiceImpl;
import edu.pitt.apollo.services_common.v4_0_1.*;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_1.FixedDuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jdl50 on 6/2/15.
 */
public class TestClient {

	private static final String REST_DATA_SERVICE_TEST_CLIENT_PROPERTIES_FILE = "run_manager_service_test_client.properties";
	static Logger logger = LoggerFactory.getLogger(TestClient.class.getName());

	public enum SimulatorIdentificationEnum {

		FRED,
		FLUTE,
		SEIR
	}

	private static Authentication getAuthentication() throws FileNotFoundException, IOException {
		InputStream fis = TestClient.class.getResourceAsStream("/" + REST_DATA_SERVICE_TEST_CLIENT_PROPERTIES_FILE);
		Properties properties = new Properties();
		properties.load(fis);
		fis.close();

		Authentication auth = new Authentication();
		return auth;
	}

	private static RunSimulationMessage getRunSimulationMessage() throws IOException, DatastoreException {
		RunSimulationMessage runSimulationMessage
				= ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);

		FixedDuration latentPeriodDuration = (FixedDuration) runSimulationMessage.getInfectiousDiseaseScenario().
				getInfections().get(0).getInfectionAcquisitionsFromInfectedHosts().get(0)
				.getInfectiousPeriodDuration();

		latentPeriodDuration.setValue(new Double(String.valueOf(latentPeriodDuration.getValue()) + System.currentTimeMillis()));
		System.out.println("Set LPD to: " + latentPeriodDuration);
		return runSimulationMessage;
	}

	private static void run(RunMessage message, Authentication authentication) throws IOException, JobRunningServiceException, RunManagementException {
		RunManagementInterface runManagementInterface = new RunManagerServiceImpl();
		InsertRunResult runResult = runManagementInterface.insertRun(message, authentication);
		if (!runResult.isRunCached()) {
			BigInteger runId = runResult.getRunId();
			while (runManagementInterface.getRunStatus(runId, getAuthentication()).getStatus() != (MethodCallStatusEnum.TRANSLATION_COMPLETED)) {
				MethodCallStatus status = runManagementInterface.getRunStatus(runId, getAuthentication());
				System.out.println("Status of run " + runId + " is (" + status.getStatus().value() + ")" + status.getMessage());
			}

			JobRunningServiceInterface jobRunningServiceInterface = new RunManagerServiceImpl();
			jobRunningServiceInterface.run(runId, getAuthentication());

			while (runManagementInterface.getRunStatus(runId, getAuthentication()).getStatus() != (MethodCallStatusEnum.COMPLETED)) {
				MethodCallStatus status = runManagementInterface.getRunStatus(runId, getAuthentication());
				System.out.println("Status of run " + runId + " is (" + status.getStatus().value() + ") " + status.getMessage());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void testBatch() throws IOException {
		RunSimulationsMessage runSimulationsMessage = getRunSimulationsMessage(100);
	}

	public static RunSimulationsMessage getRunSimulationsMessage(int numRuns) throws IOException {
		RunSimulationMessage runSimulationMessage = ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);
		RunSimulationsMessage runSimulationsMessage = new RunSimulationsMessage();
		runSimulationsMessage
				.setBaseInfectiousDiseaseScenario(runSimulationMessage
						.getInfectiousDiseaseScenario());

		runSimulationsMessage.setSoftwareIdentification(runSimulationMessage
				.getSoftwareIdentification());
		runSimulationsMessage
				.setSimulatorTimeSpecification(runSimulationMessage
						.getSimulatorTimeSpecification());
		String url = createBatchConfigurationFile(numRuns);
		runSimulationsMessage.setBatchConfigurationFile(url);
		return runSimulationsMessage;
	}

	public static String createBatchConfigurationFile(int num) throws IOException {
		String APOLLO_DIR = "";
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			APOLLO_DIR = apolloDir;
			File f = new File(ApolloServiceConstants.APOLLO_DIR + "tmp");
			f.mkdirs();
		}

		logger.debug("Attempting to load file {}..." + APOLLO_DIR + "rand.txt");
		File randFile = new File(APOLLO_DIR + "rand.txt");
		BufferedReader br = new BufferedReader(new FileReader(randFile));
		String rand = br.readLine();
		br.close();

		String filename = APOLLO_DIR + "tmp" + File.separator + "test" + System.currentTimeMillis() + ".jdl";
		DecimalFormat df = new DecimalFormat("0.000");
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		Double lastNum = 2.005;
		for (int i = 0; i < num; i++) {
			bw.write(i + "," + "0.99,0.005,0.005,0.00,1." + rand + ",2.1," + df.format(lastNum)
					+ ",0\n");
			lastNum += 0.005;
		}
		bw.close();

		Sardine sardine = SardineFactory.begin();

		InputStream inputStream = new FileInputStream(filename);
		String fn = new File(filename).getName();
		String url = "https://betaweb.rods.pitt.edu/jdl50_web_dav-source/"
				+ fn;
		sardine.put("https://betaweb.rods.pitt.edu/jdl50_web_dav-source/"
				+ fn, inputStream);
		inputStream.close();
		return url;

	}

	private RunSimulationMessage getOdsBaseRunSimulationMessage() throws DatatypeConfigurationException {
		RunSimulationMessage message = ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);
		message.setInfectiousDiseaseScenario(InfectiousDiseaseScenarioCreator.getBaseInfectiousDiseaseScenario("42003"));
		return message;
	}

	public static void main(String[] args) throws IOException, RunManagementException, JobRunningServiceException {
		RunSimulationMessage runSimulationMessage = TestClient.getRunSimulationMessage();
        Authentication authentication = new Authentication();
		//RunSimulationsMessage runSimulationsMessage = TestClient.getRunSimulationsMessage(20);
		run(runSimulationMessage, authentication);
	}

}

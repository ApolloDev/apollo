package edu.pitt.apollo.client.wrapper;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import edu.pitt.rods.SeirSimulatorService;
import edu.pitt.apollo.service.epidemicsimulator.EpidemicSimulator;
import edu.pitt.apollo.types.EpidemicModelInput;
import edu.pitt.apollo.types.SimulationRunResult;

public class SeirModelServiceWrapper {

	private static final QName SERVICE_NAME = new QName(
			"http://rods.pitt.edu/", "SeirSimulatorService");

	private URL wsdlURL = null;

	private String workingDirectory;

	public SeirModelServiceWrapper(URL wsdlURL, String workingDirectory)
			throws MalformedURLException {
		this.wsdlURL = wsdlURL;
		this.workingDirectory = workingDirectory;
		if (!this.workingDirectory.endsWith("/")) {
			this.workingDirectory += "/";
		}
	}

	private EpidemicSimulator getPort() {
		EpidemicSimulator port = null;
		SeirSimulatorService ss = new SeirSimulatorService(wsdlURL, SERVICE_NAME);
		port = ss.getSimulationServerPort();
		return port;
	}

	public SimulationRunResult run(EpidemicModelInput input) {
		EpidemicSimulator port = getPort();
		return port.run(input);
	}

//	public Iterator<EpidemicModelOutput> runBatch(List<EpidemicModelInput> input)
//			throws IOException {
//		EpidemicSimulator port = getPort();
//		GsonHelper<EpidemicModelInput> gsonHelper = new GsonHelper<EpidemicModelInput>(
//				EpidemicModelInput.class);
//		String fn = workingDirectory + System.currentTimeMillis() + ".gz";
//		FileOutputStream fos = new FileOutputStream(fn);
//		GZIPOutputStream gos = new GZIPOutputStream(fos);
//		gsonHelper.writeJsonStream(gos, input);
//		DataSource fds = new FileDataSource(fn);
//		DataHandler dataHandler = new DataHandler(fds);
//		DataHandler result = port.runGzipBatch(dataHandler);
//		GZIPInputStream gis = new GZIPInputStream(result.getInputStream());
//		return new EpidemicModelOutputIterator(gis, "UTF-8");
//	}

	public static void main(String[] args) throws Exception {
		SeirModelTestHelper
				.testRun(new URL(
						//"http://research3.rods.pitt.edu:9001/ApolloSeirModelService/services/SeirServerPort?wsdl"));
						"http://research3.rods.pitt.edu:9001/Seir/services/seirepimodelsimulator?wsdl"));
//		SeirModelTestHelper
//				.testRunBatch(
//						new URL(
//								"http://research3.rods.pitt.edu:9001/ApolloSeirModelService/services/SeirServerPort?wsdl"),
//								//"http://localhost:8080/ApolloSeirModelService/services/SeirServerPort?wsdl"),
//						5);
	}

}

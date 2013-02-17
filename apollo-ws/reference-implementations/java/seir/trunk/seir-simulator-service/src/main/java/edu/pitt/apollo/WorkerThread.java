package edu.pitt.apollo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import edu.pitt.apollo.client.wrapper.EpidemicModelInputHelper;
import edu.pitt.apollo.client.wrapper.EpidemicModelOutputHelper;
import edu.pitt.apollo.client.wrapper.SeirModelServiceWrapper;
import edu.pitt.apollo.types.ApolloFactory;
import edu.pitt.apollo.types.EpidemicModelInput;
import edu.pitt.apollo.types.SimulationRunResult;
import edu.pitt.apollo.types.SimulatorConfiguration;

public class WorkerThread extends Thread {

	SimulatorConfiguration sc;
	String runId;

	public WorkerThread(SimulatorConfiguration sc, String runId) {
		super();
		this.sc = sc;
		this.runId = runId;
	}

	public void run() {
		URL wsdlURL;
		try {
			RunUtils.setStarted(runId);

			wsdlURL = new URL(
					"https://betaweb.rods.pitt.edu/SeirEpiModelService/services/seirepimodelsimulator?wsdl");

			SeirModelServiceWrapper wrapper = new SeirModelServiceWrapper(
					wsdlURL, "");

			EpidemicModelInput input = ApolloFactory.createEpidemicModelInput();
			input.getDiseaseDynamics().setAsymptomaticInfectionFraction(sc.getDisease().getAsymptomaticInfectionFraction());
			input.getDiseaseDynamics().setInfectiousPeriod(sc.getDisease().getInfectiousPeriod());
			input.getDiseaseDynamics().setLatentPeriod(sc.getDisease().getLatentPeriod());
			input.getDiseaseDynamics().setReproductionNumber(sc.getDisease().getReproductionNumber());
			
			input.getSimulatorConfiguration().g

			System.out.println(EpidemicModelInputHelper.print(input));
			SimulationRunResult rr = wrapper.run(input);
			System.out.println(EpidemicModelOutputHelper.print(rr
					.getModelOutput()));
			System.out.println(rr.getModelOutput().getPopulationTimeSeries()
					.getSimulatedPopulation().get(0));
			RunUtils.setFinished(runId);

		} catch (Exception e) {
			try {
				RunUtils.setError(runId, e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}

package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

import edu.pitt.apollo.examples.runsimulationmessages.ExampleUseOfConditionalProbabilityDistribution;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleUseOfUnconditionalProbabilityDistribution;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;

public class TutorialChapter7_ProbabilityDistributionExample extends
		TutorialWebServiceClient {

	public TutorialChapter7_ProbabilityDistributionExample() throws MalformedURLException {
		super();
	}

	public static void main(String[] args) throws MalformedURLException {
		RunSimulationMessage runSimulationMessage = null;

		ExampleUseOfConditionalProbabilityDistribution conditionalProbabilityDistributionExample = new ExampleUseOfConditionalProbabilityDistribution();

		runSimulationMessage = conditionalProbabilityDistributionExample.getRunSimulationMessage();

		ExampleUseOfUnconditionalProbabilityDistribution unconditionalProbabilityDistributionExample = new ExampleUseOfUnconditionalProbabilityDistribution();
		runSimulationMessage = unconditionalProbabilityDistributionExample.getRunSimulationMessage();

		// we do not run this example as neither the SEIR, FRED, or FluTE
		// simulators can support this configuration
	}
}

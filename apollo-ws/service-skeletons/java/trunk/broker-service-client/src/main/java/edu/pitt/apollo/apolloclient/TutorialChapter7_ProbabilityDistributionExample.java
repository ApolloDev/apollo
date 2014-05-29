package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

import edu.pitt.apollo.examples.runsimulationmessages.ExampleConditionalProbabilityDistribution;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleUnconditionalProbabilityDistribution;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;

public class TutorialChapter7_ProbabilityDistributionExample extends TutorialWebServiceClient {

	public TutorialChapter7_ProbabilityDistributionExample() throws MalformedURLException {
		super();
	}

	public static void main(String[] args) throws MalformedURLException {
		RunSimulationMessage runSimulationMessage = null;

		ExampleConditionalProbabilityDistribution conditionalProbabilityDistributionExample = new ExampleConditionalProbabilityDistribution();

		runSimulationMessage = conditionalProbabilityDistributionExample
				.setLatentPeriodToConditionalProbabilityDistribution(ApolloServiceTypeFactory
						.getDefaultRunSimulationMessage());

		ExampleUnconditionalProbabilityDistribution unconditionalProbabilityDistributionExample = new ExampleUnconditionalProbabilityDistribution();
		runSimulationMessage = unconditionalProbabilityDistributionExample
				.setLatentPeriodToUnconditionalProbabilityDistribution(ApolloServiceTypeFactory
						.getDefaultRunSimulationMessage());

		// we do not run this example as neither the SEIR, FRED, or FluTE
		// simulators can support this configuration
	}
}

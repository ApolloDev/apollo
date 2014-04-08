package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

import edu.pitt.apollo.examples.TutorialChapter7_ProbabilityDistributionConfig;

public class TutorialChapter7_ProbabilityDistributionExample extends
		TutorialChapter2_BasicRunSimulationExample {

	public TutorialChapter7_ProbabilityDistributionExample() throws MalformedURLException{
		super();
	}

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {

		TutorialChapter7_ProbabilityDistributionConfig tutorial = new TutorialChapter7_ProbabilityDistributionConfig();
		tutorial.setLatentPeriodToConditionalProbabilityDistribution(tutorial.getConditionalProbabilityDistribution());
		tutorial.setLatentPeriodToUnconditionalProbabilityDistribution(tutorial.getUnconditionalProbabilityDistribution());
		//we do not run this example as neither the SEIR, FRED, or FluTE simulators can support this configuration
	}
}

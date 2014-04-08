package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

import edu.pitt.apollo.examples.TutorialChapter7_ProbabilityDistributionConfig;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;
import edu.pitt.apollo.types.v2_0_1.VisualizerResult;

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

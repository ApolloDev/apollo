package edu.pitt.apollo.examples.runsimulationmessages;

import edu.pitt.apollo.simulator_service_types.v2_1_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_1_0.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v2_1_0.ProbabilityValuePair;
import edu.pitt.apollo.types.v2_1_0.UncertainDuration;
import edu.pitt.apollo.types.v2_1_0.UnconditionalProbabilityDistribution;
import edu.pitt.apollo.types.v2_1_0.UnitOfTimeEnum;

public class ExampleUnconditionalProbabilityDistribution  {
	
	static final double ONE_DAY_LATENT_PERIOD = 1d;
	static final double TWO_DAY_LATENT_PERIOD = 2d;
	static final double THREE_DAY_LATENT_PERIOD = 3d;

	private void addValueAndProbilityToDiscreteDistribution(
			DiscreteNonparametricProbabilityDistribution distribution, double value, double probabilityOfValue) {
		ProbabilityValuePair valueAndAssociatedProbability = new ProbabilityValuePair();
		valueAndAssociatedProbability.setValue(value);
		valueAndAssociatedProbability.setProbability(probabilityOfValue);
		
		distribution.getProbabilityValuePairs().add(valueAndAssociatedProbability);
	}

	private UnconditionalProbabilityDistribution getDistributionRepresentingLatentPeriodDuration() {
		DiscreteNonparametricProbabilityDistribution latentPeriodDuration = new DiscreteNonparametricProbabilityDistribution();

		double probabilityThatDurationOfLatentPeriodIsOneDay = 0.2;
		double probabilityThatDurationOfLatentPeriodIsTwoDays = 0.6;
		double probabilityThatDurationOfLatentPeriodIsThreeDays = 0.2;

		addValueAndProbilityToDiscreteDistribution(latentPeriodDuration,
				ONE_DAY_LATENT_PERIOD, probabilityThatDurationOfLatentPeriodIsOneDay);
		
		addValueAndProbilityToDiscreteDistribution(latentPeriodDuration,
				TWO_DAY_LATENT_PERIOD, probabilityThatDurationOfLatentPeriodIsTwoDays);
		
		addValueAndProbilityToDiscreteDistribution(latentPeriodDuration,
				THREE_DAY_LATENT_PERIOD, probabilityThatDurationOfLatentPeriodIsThreeDays);
		
		return latentPeriodDuration;
	}

	public RunSimulationMessage setLatentPeriodToUnconditionalProbabilityDistribution(RunSimulationMessage runSimulationMessage) {
		UnconditionalProbabilityDistribution distributionRepresentingLatentPeriodDuration = getDistributionRepresentingLatentPeriodDuration();
		
		UncertainDuration uncertainDurationOfLatentPeriod = new UncertainDuration();
		uncertainDurationOfLatentPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		uncertainDurationOfLatentPeriod
				.setProbabilityDistribution(distributionRepresentingLatentPeriodDuration);

		runSimulationMessage.getInfectiousDiseaseScenario().getInfections().get(0)
				.getInfectionAcquisitionsFromInfectiousHosts().get(0).setLatentPeriodDuration(uncertainDurationOfLatentPeriod);

		return runSimulationMessage;
	}
}

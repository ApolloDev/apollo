package edu.pitt.apollo.examples;

import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import java.math.BigInteger;

import edu.pitt.apollo.types.v4_0.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v4_0.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v4_0.ConditioningVariable;
import edu.pitt.apollo.types.v4_0.CategoricalVariableEnum;
import edu.pitt.apollo.types.v4_0.CategoricalVariableNode;
import edu.pitt.apollo.types.v4_0.CategoryValueNode;
import edu.pitt.apollo.types.v4_0.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v4_0.GenderCategoryDefinition;
import edu.pitt.apollo.types.v4_0.GenderEnum;
import edu.pitt.apollo.types.v4_0.ProbabilityValuePair;
import edu.pitt.apollo.types.v4_0.UncertainDuration;
import edu.pitt.apollo.types.v4_0.UnitOfTimeEnum;

public class ExampleConditionalProbabilityDistribution {

	static final double ONE_DAY_LATENT_PERIOD = 1d;
	static final double TWO_DAY_LATENT_PERIOD = 2d;
	static final double THREE_DAY_LATENT_PERIOD = 3d;

	public ExampleConditionalProbabilityDistribution() {
		super();
	}

	DiscreteNonparametricProbabilityDistribution createThreeDayLatentPeriodDurationDistribution(
			double probabilityThatDurationOfLatentPeriodIsOneDay,
			double probabilityThatDurationOfLatentPeriodIsTwoDays,
			double probabilityThatDurationOfLatentPeriodIsThreeDays) {
		DiscreteNonparametricProbabilityDistribution latentPeriodDuration = new DiscreteNonparametricProbabilityDistribution();

		addValueAndProbilityToDiscreteDistribution(latentPeriodDuration, ONE_DAY_LATENT_PERIOD,
				probabilityThatDurationOfLatentPeriodIsOneDay);
		addValueAndProbilityToDiscreteDistribution(latentPeriodDuration, TWO_DAY_LATENT_PERIOD,
				probabilityThatDurationOfLatentPeriodIsTwoDays);
		addValueAndProbilityToDiscreteDistribution(latentPeriodDuration, THREE_DAY_LATENT_PERIOD,
				probabilityThatDurationOfLatentPeriodIsThreeDays);

		return latentPeriodDuration;
	}

	private void addValueAndProbilityToDiscreteDistribution(
			DiscreteNonparametricProbabilityDistribution distribution, double value, double probabilityOfValue) {
		ProbabilityValuePair valueAndAssociatedProbability = new ProbabilityValuePair();
		valueAndAssociatedProbability.setValue(value);
		valueAndAssociatedProbability.setProbability(probabilityOfValue);
		distribution.getProbabilityValuePairs().add(valueAndAssociatedProbability);
	}

	private CategoryValueNode createAgeGroupConditionedOnGender(Integer ageRangeLowerBound,
			Integer ageRangeUpperBound, double probabilityThatDurationOfLatentPeriodIsOneDay,
			double probabilityThatDurationOfLatentPeriodIsTwoDays,
			double probabilityThatDurationOfLatentPeriodIsThreeDays) {

		DiscreteNonparametricProbabilityDistribution latentPeriodDuration = createThreeDayLatentPeriodDurationDistribution(
				probabilityThatDurationOfLatentPeriodIsOneDay, probabilityThatDurationOfLatentPeriodIsTwoDays,
				probabilityThatDurationOfLatentPeriodIsThreeDays);

		CategoricalVariableNode gender = new CategoricalVariableNode();
		gender.setName(CategoricalVariableEnum.SEX);
		CategoryValueNode male = createGenderCategoryDefinitionAndAssignDistribution(GenderEnum.M, latentPeriodDuration);
		CategoryValueNode female = createGenderCategoryDefinitionAndAssignDistribution(GenderEnum.F, latentPeriodDuration);
		gender.getCategories().add(male);
		gender.getCategories().add(female);

		AgeRangeCategoryDefinition ageGroupRange = new AgeRangeCategoryDefinition();
		ageGroupRange.setLowerBound(new BigInteger(ageRangeLowerBound.toString()));
		ageGroupRange.setUpperBound(new BigInteger(ageRangeUpperBound.toString()));
		
		CategoryValueNode ageGroup = new CategoryValueNode();
		ageGroup.setCategoryDefinition(ageGroupRange);
		ageGroup.setNextCategoricalVariable(gender);
		return ageGroup;
	}

	private CategoryValueNode createAgeGroupConditionedOnGender(Integer ageRangeLowerBound,
			Integer ageRangeUpperBound, double probabilityThatDurationOfLatentPeriodForMalesIsOneDay,
			double probabilityThatDurationOfLatentPeriodForMalesIsTwoDays,
			double probabilityThatDurationOfLatentPeriodForMalesIsThreeDays,
			double probabilityThatDurationOfLatentPeriodForFemalesIsOneDay,
			double probabilityThatDurationOfLatentPeriodForFemalesIsTwoDays,
			double probabilityThatDurationOfLatentPeriodForFemalesIsThreeDays) {

		DiscreteNonparametricProbabilityDistribution latentPeriodDurationForMales = createThreeDayLatentPeriodDurationDistribution(
				probabilityThatDurationOfLatentPeriodForMalesIsOneDay,
				probabilityThatDurationOfLatentPeriodForMalesIsTwoDays,
				probabilityThatDurationOfLatentPeriodForMalesIsThreeDays);

		DiscreteNonparametricProbabilityDistribution latentPeriodDurationForFemales = createThreeDayLatentPeriodDurationDistribution(
				probabilityThatDurationOfLatentPeriodForFemalesIsOneDay,
				probabilityThatDurationOfLatentPeriodForFemalesIsTwoDays,
				probabilityThatDurationOfLatentPeriodForFemalesIsThreeDays);

		CategoricalVariableNode gender = new CategoricalVariableNode();
		gender.setName(CategoricalVariableEnum.SEX);
		CategoryValueNode maleGenderCategoryDefinition = createGenderCategoryDefinitionAndAssignDistribution(GenderEnum.M,
				latentPeriodDurationForMales);
		CategoryValueNode femaleGenderCategoryDefinition = createGenderCategoryDefinitionAndAssignDistribution(GenderEnum.F,
				latentPeriodDurationForFemales);
		gender.getCategories().add(maleGenderCategoryDefinition);
		gender.getCategories().add(femaleGenderCategoryDefinition);

		AgeRangeCategoryDefinition ageGroupRange = new AgeRangeCategoryDefinition();
		ageGroupRange.setLowerBound(new BigInteger(ageRangeLowerBound.toString()));
		ageGroupRange.setUpperBound(new BigInteger(ageRangeUpperBound.toString()));

		CategoryValueNode ageGroup = new CategoryValueNode();
		ageGroup.setCategoryDefinition(ageGroupRange);
		ageGroup.setNextCategoricalVariable(gender);
		return ageGroup;
	}

	private CategoryValueNode createGenderCategoryDefinitionAndAssignDistribution(GenderEnum gender,
			DiscreteNonparametricProbabilityDistribution discreteNonparametricProbabilityDistribution) {
		CategoryValueNode GenderCategoryDefinition = new CategoryValueNode();
		GenderCategoryDefinition GenderCategoryDefinitionDefinition = new GenderCategoryDefinition();
		GenderCategoryDefinitionDefinition.setSex(gender);
		GenderCategoryDefinition.setCategoryDefinition(GenderCategoryDefinitionDefinition);
//		GenderCategoryDefinition.setUnconditionalProbabilityDistribution(discreteNonparametricProbabilityDistribution);
		return GenderCategoryDefinition;

	}

	private ConditionalProbabilityDistribution getDistributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender() {
		int childAgeRangeLowerBound = 0;
		int childAgeRangeUpperBound = 5;
		double probabilityThatDurationOfLatentPeriodForChildrenIsOneDay = 0.5;
		double probabilityThatDurationOfLatentPeriodForChildrenIsTwoDays = 0.4;
		double probabilityThatDurationOfLatentPeriodForChildrenIsThreeDays = 0.1;

		int youngPersonAgeRangeLowerBound = 6;
		int youngPersonAgeRangeUpperBound = 18;
		double probabilityThatDurationOfLatentPeriodForYoungPersonsIsOneDay = 0.2;
		double probabilityThatDurationOfLatentPeriodForYoungPersonsIsTwoDays = 0.6;
		double probabilityThatDurationOfLatentPeriodForYoungPersonsIsThreeDays = 0.2;

		int adultAgeRangeLowerBound = 19;
		int adultAgeRangeUpperBound = 99;
		double probabilityThatDurationOfLatentPeriodForAdultMalesIsOneDay = 0.3;
		double probabilityThatDurationOfLatentPeriodForAdultMalesIsTwoDays = 0.5;
		double probabilityThatDurationOfLatentPeriodForAdultMalesIsThreeDays = 0.2;

		double probabilityThatDurationOfLatentPeriodForAdultFemalesIsOneDay = 0.2;
		double probabilityThatDurationOfLatentPeriodForAdultFemalesIsTwoDays = 0.3;
		double probabilityThatDurationOfLatentPeriodForAdultFemalesIsThreeDays = 0.5;

		CategoryValueNode childAgeGroup = createAgeGroupConditionedOnGender(childAgeRangeLowerBound,
				childAgeRangeUpperBound, probabilityThatDurationOfLatentPeriodForChildrenIsOneDay,
				probabilityThatDurationOfLatentPeriodForChildrenIsTwoDays,
				probabilityThatDurationOfLatentPeriodForChildrenIsThreeDays);

		CategoryValueNode youngPersonAgeGroup = createAgeGroupConditionedOnGender(youngPersonAgeRangeLowerBound,
				youngPersonAgeRangeUpperBound, probabilityThatDurationOfLatentPeriodForYoungPersonsIsOneDay,
				probabilityThatDurationOfLatentPeriodForYoungPersonsIsTwoDays,
				probabilityThatDurationOfLatentPeriodForYoungPersonsIsThreeDays);

		CategoryValueNode adultAgeRange = createAgeGroupConditionedOnGender(adultAgeRangeLowerBound,
				adultAgeRangeUpperBound, probabilityThatDurationOfLatentPeriodForAdultMalesIsOneDay,
				probabilityThatDurationOfLatentPeriodForAdultMalesIsTwoDays,
				probabilityThatDurationOfLatentPeriodForAdultMalesIsThreeDays,
				probabilityThatDurationOfLatentPeriodForAdultFemalesIsOneDay,
				probabilityThatDurationOfLatentPeriodForAdultFemalesIsTwoDays,
				probabilityThatDurationOfLatentPeriodForAdultFemalesIsThreeDays);

		ConditioningVariable ageGroups = new ConditioningVariable();
		ageGroups.setName(CategoricalVariableEnum.AGE_RANGE);
		ageGroups.getCategories().add(childAgeGroup);
		ageGroups.getCategories().add(youngPersonAgeGroup);
		ageGroups.getCategories().add(adultAgeRange);

		ConditionalProbabilityDistribution distributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender = new ConditionalProbabilityDistribution();
		distributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender
				.setFirstConditioningVariable(ageGroups);
		return distributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender;
	}

	public RunSimulationMessage setLatentPeriodToConditionalProbabilityDistribution(RunSimulationMessage runSimulationMessage) {
		
		ConditionalProbabilityDistribution distributionRepresentingLatentPeriodDuration = getDistributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender();
		
		UncertainDuration uncertainDuration = new UncertainDuration();
		uncertainDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
		uncertainDuration.setProbabilityDistribution(distributionRepresentingLatentPeriodDuration);

		runSimulationMessage.getInfectiousDiseaseScenario().getInfections().get(0)
				.getInfectionAcquisitionsFromInfectedHosts().get(0).setLatentPeriodDuration(uncertainDuration);

		return runSimulationMessage;
	}
}

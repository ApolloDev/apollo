package edu.pitt.apollo.examples;

import java.math.BigInteger;

import edu.pitt.apollo.types.v2_0_1.AgeRangeCategory;
import edu.pitt.apollo.types.v2_0_1.Category;
import edu.pitt.apollo.types.v2_0_1.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v2_0_1.ConditioningVariable;
import edu.pitt.apollo.types.v2_0_1.ConditioningVariableEnum;
import edu.pitt.apollo.types.v2_0_1.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v2_0_1.GenderCategory;
import edu.pitt.apollo.types.v2_0_1.GenderEnum;
import edu.pitt.apollo.types.v2_0_1.ProbabilityValuePair;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.UncertainDuration;
import edu.pitt.apollo.types.v2_0_1.UnitOfTimeEnum;

public class ExampleUseOfConditionalProbabilityDistribution extends ExampleRunSimulationMessageFactory {

	static final double ONE_DAY_LATENT_PERIOD = 1d;
	static final double TWO_DAY_LATENT_PERIOD = 2d;
	static final double THREE_DAY_LATENT_PERIOD = 3d;

	public ExampleUseOfConditionalProbabilityDistribution() {
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

	private Category createAgeGroupConditionedOnGender(Integer ageRangeLowerBound,
			Integer ageRangeUpperBound, double probabilityThatDurationOfLatentPeriodIsOneDay,
			double probabilityThatDurationOfLatentPeriodIsTwoDays,
			double probabilityThatDurationOfLatentPeriodIsThreeDays) {

		DiscreteNonparametricProbabilityDistribution latentPeriodDuration = createThreeDayLatentPeriodDurationDistribution(
				probabilityThatDurationOfLatentPeriodIsOneDay, probabilityThatDurationOfLatentPeriodIsTwoDays,
				probabilityThatDurationOfLatentPeriodIsThreeDays);

		ConditioningVariable gender = new ConditioningVariable();
		gender.setName(ConditioningVariableEnum.GENDER);
		Category male = createGenderCategoryAndAssignDistribution(GenderEnum.M, latentPeriodDuration);
		Category female = createGenderCategoryAndAssignDistribution(GenderEnum.F, latentPeriodDuration);
		gender.getCategories().add(male);
		gender.getCategories().add(female);

		AgeRangeCategory ageGroupRange = new AgeRangeCategory();
		ageGroupRange.setLowerBound(new BigInteger(ageRangeLowerBound.toString()));
		ageGroupRange.setUpperBound(new BigInteger(ageRangeUpperBound.toString()));

		Category ageGroup = new Category();
		ageGroup.setCategoryDefinition(ageGroupRange);
		ageGroup.setConditioningVariable(gender);
		return ageGroup;
	}

	private Category createAgeGroupConditionedOnGender(Integer ageRangeLowerBound,
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

		ConditioningVariable gender = new ConditioningVariable();
		gender.setName(ConditioningVariableEnum.GENDER);
		Category maleGenderCategory = createGenderCategoryAndAssignDistribution(GenderEnum.M,
				latentPeriodDurationForMales);
		Category femaleGenderCategory = createGenderCategoryAndAssignDistribution(GenderEnum.F,
				latentPeriodDurationForFemales);
		gender.getCategories().add(maleGenderCategory);
		gender.getCategories().add(femaleGenderCategory);

		AgeRangeCategory ageGroupRange = new AgeRangeCategory();
		ageGroupRange.setLowerBound(new BigInteger(ageRangeLowerBound.toString()));
		ageGroupRange.setUpperBound(new BigInteger(ageRangeUpperBound.toString()));

		Category ageGroup = new Category();
		ageGroup.setCategoryDefinition(ageGroupRange);
		ageGroup.setConditioningVariable(gender);
		return ageGroup;
	}

	private Category createGenderCategoryAndAssignDistribution(GenderEnum gender,
			DiscreteNonparametricProbabilityDistribution discreteNonparametricProbabilityDistribution) {
		Category genderCategory = new Category();
		GenderCategory genderCategoryDefinition = new GenderCategory();
		genderCategoryDefinition.setGender(gender);
		genderCategory.setCategoryDefinition(genderCategoryDefinition);
		genderCategory.setUnconditionalProbabilityDistribution(discreteNonparametricProbabilityDistribution);
		return genderCategory;

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

		Category childAgeGroup = createAgeGroupConditionedOnGender(childAgeRangeLowerBound,
				childAgeRangeUpperBound, probabilityThatDurationOfLatentPeriodForChildrenIsOneDay,
				probabilityThatDurationOfLatentPeriodForChildrenIsTwoDays,
				probabilityThatDurationOfLatentPeriodForChildrenIsThreeDays);

		Category youngPersonAgeGroup = createAgeGroupConditionedOnGender(youngPersonAgeRangeLowerBound,
				youngPersonAgeRangeUpperBound, probabilityThatDurationOfLatentPeriodForYoungPersonsIsOneDay,
				probabilityThatDurationOfLatentPeriodForYoungPersonsIsTwoDays,
				probabilityThatDurationOfLatentPeriodForYoungPersonsIsThreeDays);

		Category adultAgeRange = createAgeGroupConditionedOnGender(adultAgeRangeLowerBound,
				adultAgeRangeUpperBound, probabilityThatDurationOfLatentPeriodForAdultMalesIsOneDay,
				probabilityThatDurationOfLatentPeriodForAdultMalesIsTwoDays,
				probabilityThatDurationOfLatentPeriodForAdultMalesIsThreeDays,
				probabilityThatDurationOfLatentPeriodForAdultFemalesIsOneDay,
				probabilityThatDurationOfLatentPeriodForAdultFemalesIsTwoDays,
				probabilityThatDurationOfLatentPeriodForAdultFemalesIsThreeDays);

		ConditioningVariable ageGroups = new ConditioningVariable();
		ageGroups.setName(ConditioningVariableEnum.AGE_RANGE);
		ageGroups.getCategories().add(childAgeGroup);
		ageGroups.getCategories().add(youngPersonAgeGroup);
		ageGroups.getCategories().add(adultAgeRange);

		ConditionalProbabilityDistribution distributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender = new ConditionalProbabilityDistribution();
		distributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender
				.setFirstConditioningVariable(ageGroups);
		return distributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender;
	}

	public RunSimulationMessage getDefaultRunSimulationMessageAndSetLatentPeriodToConditionalProbabilityDistribution() {
		RunSimulationMessage runSimulationMessage = super.getRunSimulationMessage();

		UncertainDuration uncertainDuration = new UncertainDuration();
		ConditionalProbabilityDistribution distributionRepresentingLatentPeriodDuration = getDistributionRepresentingLatentPeriodDurationConditionedOnAgeRangeAndGender();
		uncertainDuration.setUnitOfTime(UnitOfTimeEnum.DAY);
		uncertainDuration.setProbabilityDistribution(distributionRepresentingLatentPeriodDuration);

		runSimulationMessage.getInfectiousDiseaseScenario().getInfections().get(0)
				.setLatentPeriodDuration(uncertainDuration);

		return runSimulationMessage;
	}
}

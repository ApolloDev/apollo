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
import edu.pitt.apollo.types.v2_0_1.UnconditionalProbabilityDistribution;

public class TutorialChapter7_ProbabilityDistributionConfig extends TutorialChapter2_ExampleConfig {

	public TutorialChapter7_ProbabilityDistributionConfig() {
		super();
	}

	public ConditioningVariable getAgeRangeConditioningVariable() {
		ConditioningVariable ageRangeConditioningVariable = new ConditioningVariable();

		ageRangeConditioningVariable.setName(ConditioningVariableEnum.AGE_RANGE);

		Category firstAgeRangeCategory = new Category();
		Category secondAgeRangeCategory = new Category();
		Category thirdAgeRangeCategory = new Category();

		AgeRangeCategory firstAgeRangeCategoryDefinition = new AgeRangeCategory();
		firstAgeRangeCategoryDefinition.setLowerBound(new BigInteger("0"));
		firstAgeRangeCategoryDefinition.setUpperBound(new BigInteger("5"));
		firstAgeRangeCategory.setCategoryDefinition(firstAgeRangeCategoryDefinition);
		firstAgeRangeCategory.setConditioningVariable(getFirstAgeRangeCategoryConditioningVariable());

		AgeRangeCategory secondAgeRangeCategoryDefinition = new AgeRangeCategory();
		secondAgeRangeCategoryDefinition.setLowerBound(new BigInteger("6"));
		secondAgeRangeCategoryDefinition.setUpperBound(new BigInteger("18"));
		secondAgeRangeCategory.setCategoryDefinition(secondAgeRangeCategoryDefinition);
		secondAgeRangeCategory.setConditioningVariable(getSecondAgeRangeCategoryConditioningVariable());

		AgeRangeCategory thirdAgeRangeCategoryDefinition = new AgeRangeCategory();
		thirdAgeRangeCategoryDefinition.setLowerBound(new BigInteger("19"));
		thirdAgeRangeCategoryDefinition.setUpperBound(new BigInteger("99"));
		thirdAgeRangeCategory.setCategoryDefinition(thirdAgeRangeCategoryDefinition);
		thirdAgeRangeCategory.setConditioningVariable(getThirdAgeRangeCategoryConditioningVariable());

		ageRangeConditioningVariable.getCategories().add(firstAgeRangeCategory);
		ageRangeConditioningVariable.getCategories().add(secondAgeRangeCategory);
		ageRangeConditioningVariable.getCategories().add(thirdAgeRangeCategory);

		return ageRangeConditioningVariable;
	}

	public ConditioningVariable getFirstAgeRangeCategoryConditioningVariable() {
		ConditioningVariable genderConditioningVariable = new ConditioningVariable();

		genderConditioningVariable.setName(ConditioningVariableEnum.GENDER);

		Category maleGenderCategory = new Category();

		ProbabilityValuePair probabilityValuePairDay1 = new ProbabilityValuePair();
		probabilityValuePairDay1.setValue(1.0);
		probabilityValuePairDay1.setProbability(0.5);

		ProbabilityValuePair probabilityValuePairDay2 = new ProbabilityValuePair();
		probabilityValuePairDay2.setValue(2.0);
		probabilityValuePairDay2.setProbability(0.4);

		ProbabilityValuePair probabilityValuePairDay3 = new ProbabilityValuePair();
		probabilityValuePairDay3.setValue(3.0);
		probabilityValuePairDay3.setProbability(0.1);

		DiscreteNonparametricProbabilityDistribution discreteNonparametricProbabilityDistribution = new DiscreteNonparametricProbabilityDistribution();
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay1);
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay2);
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay3);

		GenderCategory maleCategoryDefinition = new GenderCategory();
		maleCategoryDefinition.setGender(GenderEnum.M);
		maleGenderCategory.setUnconditionalProbabilityDistribution(discreteNonparametricProbabilityDistribution);
		maleGenderCategory.setCategoryDefinition(maleCategoryDefinition);

		Category femaleGenderCategory = new Category();
		GenderCategory femaleCategoryDefinition = new GenderCategory();
		femaleCategoryDefinition.setGender(GenderEnum.F);
		femaleGenderCategory.setUnconditionalProbabilityDistribution(discreteNonparametricProbabilityDistribution);

		genderConditioningVariable.getCategories().add(maleGenderCategory);
		genderConditioningVariable.getCategories().add(femaleGenderCategory);

		return genderConditioningVariable;
	}

	public ConditioningVariable getSecondAgeRangeCategoryConditioningVariable() {
		ConditioningVariable genderConditioningVariable = new ConditioningVariable();
		genderConditioningVariable.setName(ConditioningVariableEnum.GENDER);

		Category maleGenderCategory = new Category();

		ProbabilityValuePair probabilityValuePairDay1 = new ProbabilityValuePair();
		probabilityValuePairDay1.setValue(1.0);
		probabilityValuePairDay1.setProbability(0.2);

		ProbabilityValuePair probabilityValuePairDay2 = new ProbabilityValuePair();
		probabilityValuePairDay2.setValue(2.0);
		probabilityValuePairDay2.setProbability(0.6);

		ProbabilityValuePair probabilityValuePairDay3 = new ProbabilityValuePair();
		probabilityValuePairDay3.setValue(3.0);
		probabilityValuePairDay3.setProbability(0.2);

		DiscreteNonparametricProbabilityDistribution discreteNonparametricProbabilityDistribution = new DiscreteNonparametricProbabilityDistribution();
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay1);
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay2);
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay3);

		GenderCategory maleCategoryDefinition = new GenderCategory();
		maleCategoryDefinition.setGender(GenderEnum.M);
		maleGenderCategory.setUnconditionalProbabilityDistribution(discreteNonparametricProbabilityDistribution);
		maleGenderCategory.setCategoryDefinition(maleCategoryDefinition);

		Category femaleGenderCategory = new Category();
		GenderCategory femaleCategoryDefinition = new GenderCategory();
		femaleCategoryDefinition.setGender(GenderEnum.F);
		femaleGenderCategory.setUnconditionalProbabilityDistribution(discreteNonparametricProbabilityDistribution);

		genderConditioningVariable.getCategories().add(maleGenderCategory);
		genderConditioningVariable.getCategories().add(femaleGenderCategory);
		return genderConditioningVariable;
	}

	public ConditioningVariable getThirdAgeRangeCategoryConditioningVariable() {
		ConditioningVariable genderConditioningVariable = new ConditioningVariable();
		genderConditioningVariable.setName(ConditioningVariableEnum.GENDER);

		Category maleGenderCategory = new Category();
		GenderCategory maleCategoryDefinition = new GenderCategory();
		maleCategoryDefinition.setGender(GenderEnum.M);
		maleGenderCategory.setCategoryDefinition(maleCategoryDefinition);

		ProbabilityValuePair probabilityValuePairDay1Male = new ProbabilityValuePair();
		probabilityValuePairDay1Male.setValue(1.0);
		probabilityValuePairDay1Male.setProbability(0.3);

		ProbabilityValuePair probabilityValuePairDay2Male = new ProbabilityValuePair();
		probabilityValuePairDay2Male.setValue(2.0);
		probabilityValuePairDay2Male.setProbability(0.5);

		ProbabilityValuePair probabilityValuePairDay3Male = new ProbabilityValuePair();
		probabilityValuePairDay3Male.setValue(3.0);
		probabilityValuePairDay3Male.setProbability(0.2);

		ProbabilityValuePair probabilityValuePairDay1Female = new ProbabilityValuePair();
		probabilityValuePairDay1Female.setValue(1.0);
		probabilityValuePairDay1Female.setProbability(0.2);

		ProbabilityValuePair probabilityValuePairDay2Female = new ProbabilityValuePair();
		probabilityValuePairDay2Female.setValue(2.0);
		probabilityValuePairDay2Female.setProbability(0.3);

		ProbabilityValuePair probabilityValuePairDay3Female = new ProbabilityValuePair();
		probabilityValuePairDay3Female.setValue(3.0);
		probabilityValuePairDay3Female.setProbability(0.5);

		DiscreteNonparametricProbabilityDistribution DiscreteNonparametricProbabilityDistributionForMales = new DiscreteNonparametricProbabilityDistribution();
		DiscreteNonparametricProbabilityDistributionForMales.getProbabilityValuePairs().add(probabilityValuePairDay1Male);
		DiscreteNonparametricProbabilityDistributionForMales.getProbabilityValuePairs().add(probabilityValuePairDay2Male);
		DiscreteNonparametricProbabilityDistributionForMales.getProbabilityValuePairs().add(probabilityValuePairDay3Male);

		DiscreteNonparametricProbabilityDistribution DiscreteNonparametricProbabilityDistributionForFemales = new DiscreteNonparametricProbabilityDistribution();
		DiscreteNonparametricProbabilityDistributionForMales.getProbabilityValuePairs().add(probabilityValuePairDay1Female);
		DiscreteNonparametricProbabilityDistributionForMales.getProbabilityValuePairs().add(probabilityValuePairDay2Female);
		DiscreteNonparametricProbabilityDistributionForMales.getProbabilityValuePairs().add(probabilityValuePairDay3Female);

		maleGenderCategory.setUnconditionalProbabilityDistribution(DiscreteNonparametricProbabilityDistributionForMales);

		Category femaleGenderCategory = new Category();
		GenderCategory femaleCategoryDefinition = new GenderCategory();
		femaleCategoryDefinition.setGender(GenderEnum.F);
		femaleGenderCategory.setCategoryDefinition(femaleCategoryDefinition);
		femaleGenderCategory.setUnconditionalProbabilityDistribution(DiscreteNonparametricProbabilityDistributionForFemales);

		genderConditioningVariable.getCategories().add(maleGenderCategory);
		genderConditioningVariable.getCategories().add(femaleGenderCategory);

		return genderConditioningVariable;
	}

	public ConditionalProbabilityDistribution getConditionalProbabilityDistribution() {
		ConditionalProbabilityDistribution conditionalProbabilityDistribution = new ConditionalProbabilityDistribution();
		conditionalProbabilityDistribution.setFirstConditioningVariable(getAgeRangeConditioningVariable());
		return conditionalProbabilityDistribution;
	}

	public UnconditionalProbabilityDistribution getUnconditionalProbabilityDistribution() {
		ProbabilityValuePair probabilityValuePairDay1 = new ProbabilityValuePair();
		probabilityValuePairDay1.setValue(1.0);
		probabilityValuePairDay1.setProbability(0.2);

		ProbabilityValuePair probabilityValuePairDay2 = new ProbabilityValuePair();
		probabilityValuePairDay2.setValue(2.0);
		probabilityValuePairDay2.setProbability(0.6);

		ProbabilityValuePair probabilityValuePairDay3 = new ProbabilityValuePair();
		probabilityValuePairDay3.setValue(3.0);
		probabilityValuePairDay3.setProbability(0.2);

		DiscreteNonparametricProbabilityDistribution discreteNonparametricProbabilityDistribution = new DiscreteNonparametricProbabilityDistribution();
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay1);
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay2);
		discreteNonparametricProbabilityDistribution.getProbabilityValuePairs().add(probabilityValuePairDay3);

		return discreteNonparametricProbabilityDistribution;
	}

	public RunSimulationMessage setLatentPeriodToConditionalProbabilityDistribution(
			ConditionalProbabilityDistribution conditionalProbabilityDistribution) {
		RunSimulationMessage runSimulationMessage = getRunSimulationMessage();
		((UncertainDuration) runSimulationMessage.getInfectiousDiseaseScenario().getInfections().get(0).getLatentPeriodDuration())
				.setProbabilityDistribution(conditionalProbabilityDistribution);
		return runSimulationMessage;
	}

	public RunSimulationMessage setLatentPeriodToUnconditionalProbabilityDistribution(
			UnconditionalProbabilityDistribution unconditionalProbabilityDistribution) {
		RunSimulationMessage runSimulationMessage = getRunSimulationMessage();
		((UncertainDuration) runSimulationMessage.getInfectiousDiseaseScenario().getInfections().get(0).getLatentPeriodDuration())
				.setProbabilityDistribution(unconditionalProbabilityDistribution);
		return runSimulationMessage;
	}

}

package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.net.MalformedURLException;

import edu.pitt.apollo.types.v2_0.AgeRange;
import edu.pitt.apollo.types.v2_0.Category;
import edu.pitt.apollo.types.v2_0.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v2_0.ConditioningVariable;

public class ConditionalProbabilityDistributionExample  extends RunSimulationWithVaccinationControlStrategyExample{

	public ConditionalProbabilityDistributionExample() throws MalformedURLException {
		super();
	}
	
	public ConditionalProbabilityDistribution getConditionalProbabilityDistribution() {
		ConditionalProbabilityDistribution conditionalProbabilityDistribution = new ConditionalProbabilityDistribution();
		ConditioningVariable ageRangeConditioningVariable = new ConditioningVariable();
		ageRangeConditioningVariable.setVariableName("ageRange");
		
		AgeRange firstAgeRangeCategoryDefinition = new AgeRange();
		firstAgeRangeCategoryDefinition.setLowerBound(new BigInteger("0"));
		firstAgeRangeCategoryDefinition.setUpperBound(new BigInteger("5"));
		
		AgeRange secondAgeRangeCategoryDefinition = new AgeRange();
		secondAgeRangeCategoryDefinition.setLowerBound(new BigInteger("6"));
		secondAgeRangeCategoryDefinition.setUpperBound(new BigInteger("18"));
		
		AgeRange thirdAgeRangeCategoryDefinition = new AgeRange();
		thirdAgeRangeCategoryDefinition.setLowerBound(new BigInteger("19"));
		thirdAgeRangeCategoryDefinition.setUpperBound(new BigInteger("99"));
		
		Category firstAgeRangeCategory = new Category();
		//firstAgeRangeCategory.setConditioningVariable(value)
		
		
		Category secondAgeRangeCategory = new Category();
		Category thirdAgeRangeCategory = new Category();
		
		//ageRangeConditioningVariable.getValues().
		//conditionalProbabilityDistribution.
		
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//first row is age
		//second row is age values (0-5, 6-18, 19-99+)
		//third row is gender conditioning variable
		//fourth row is male/female
		//for the values...kids have shorter latent periods, but there is no difference between male and female...in old people it is different between male and female (so we need four Probability-value pairs)
		 
	}

}

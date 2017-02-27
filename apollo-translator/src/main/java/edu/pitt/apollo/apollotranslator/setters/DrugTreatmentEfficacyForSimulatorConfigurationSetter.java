package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_1.DrugTreatmentEfficacyForSimulatorConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 19, 2014
 * Time: 10:13:06 AM
 * Class: DrugTreatmentEfficacyForSimulatorConfigurationSetter
 */
public class DrugTreatmentEfficacyForSimulatorConfigurationSetter extends TreatmentEfficacySetter<DrugTreatmentEfficacyForSimulatorConfiguration> {

	private static final String AVERAGE_DRUG_EFFICACY = "averageDrugEfficacy";
	private static final String DRUG_EFFICACY_CONDITIONED_ON_AGE_RANGE = "drugEfficacyConditionedOnAgeRange";
	private static final String DRUG_EFFICACY_CONDITIONED_ON_DISEASE_OUTCOME = "drugEfficacyConditionedOnCurrentDiseaseOutcome";

	public DrugTreatmentEfficacyForSimulatorConfigurationSetter() {

	}

	public DrugTreatmentEfficacyForSimulatorConfigurationSetter(String type, String section,
			ApolloTranslationEngine apolloTranslationEngine) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setAverageDrugTreatmentEfficacy(double efficacy) throws ApolloSetterException {
		return setValue(AVERAGE_DRUG_EFFICACY, Double.toString(efficacy), section);
	}

	public List<SetterReturnObject> setDrugTreatmentEfficacyByAgeRange(ConditionalProbabilityDistribution dist) throws ApolloSetterException {
		ConditionalProbabilityDistributionSetter setter = new ConditionalProbabilityDistributionSetter(apolloTranslationEngine, 
				type + "." + DRUG_EFFICACY_CONDITIONED_ON_AGE_RANGE, section);
		return setter.set(dist);
	}
	
	public List<SetterReturnObject> setDrugTreatmentEfficacyConditionedOnOcurrentDiseaseOutcome(ConditionalProbabilityDistribution dist) throws ApolloSetterException {
		ConditionalProbabilityDistributionSetter setter = new ConditionalProbabilityDistributionSetter(apolloTranslationEngine, 
				type + "." + DRUG_EFFICACY_CONDITIONED_ON_DISEASE_OUTCOME, section);
		return setter.set(dist);
	}

	@Override
	public List<SetterReturnObject> set(DrugTreatmentEfficacyForSimulatorConfiguration efficacy) throws ApolloSetterException {
		List<SetterReturnObject> result = new ArrayList<SetterReturnObject>();
        
        result.addAll(setTreatmentEfficacy(efficacy));
		result.addAll(setAverageDrugTreatmentEfficacy(efficacy.getAverageDrugEfficacy()));
		
		if (efficacy.getDrugEfficacyConditionedOnAgeRange() != null) {
			result.addAll(setDrugTreatmentEfficacyByAgeRange(efficacy.getDrugEfficacyConditionedOnAgeRange()));
		}
		
		if (efficacy.getDrugEfficaciesConditionedOnCurrentDiseaseOutcome() != null) {
			result.addAll(setDrugTreatmentEfficacyConditionedOnOcurrentDiseaseOutcome(efficacy.getDrugEfficaciesConditionedOnCurrentDiseaseOutcome()));
		}
		
		return result;
	}

}

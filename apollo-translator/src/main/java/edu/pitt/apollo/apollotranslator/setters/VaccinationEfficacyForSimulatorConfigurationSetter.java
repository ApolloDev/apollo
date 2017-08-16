package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_2.VaccinationEfficacyConditionedOnTimeSinceDose;
import edu.pitt.apollo.types.v4_0_2.VaccinationEfficacyForSimulatorConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 8, 2013
 * Time: 5:16:37 PM
 * Class: VaccinationEfficacyForSimulatorConfigurationSetter
 * IDE: NetBeans 6.9.1
 */
public class VaccinationEfficacyForSimulatorConfigurationSetter extends TreatmentEfficacySetter<VaccinationEfficacyForSimulatorConfiguration> {
    
    private static final String AVERAGE_VACCINATION_EFFICACY = "averageVaccinationEfficacy";
    private static final String VACC_EFFICACIES_BY_DOSE = "vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose";
    private static final String VACC_EFFICACY_BY_AGE_RANGE = "vaccinationEfficacyConditionedOnAgeRange"; 
    
    public VaccinationEfficacyForSimulatorConfigurationSetter() {
        
    }
    
    public VaccinationEfficacyForSimulatorConfigurationSetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(type, section, apolloTranslationEngine);
    }
    
    private List<SetterReturnObject> setAverageVaccinationEfficacy(double efficacy) throws ApolloSetterException {
        return setValue(AVERAGE_VACCINATION_EFFICACY, Double.toString(efficacy), section);
    }
    
    public VaccinationEfficacyForSimulatorConfigurationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix,
            String section) {
        super(prefix, section, apolloTranslationEngine);
    }
    
    public List<SetterReturnObject> setVaccEfficacySinceDose(VaccinationEfficacyConditionedOnTimeSinceDose v) throws ApolloSetterException {
        VaccinationEfficacyConditionedOnTimeSinceDoseSetter s = new VaccinationEfficacyConditionedOnTimeSinceDoseSetter(type + "." + VACC_EFFICACIES_BY_DOSE, section, apolloTranslationEngine);
        return s.set(v);
    }
    
    public List<SetterReturnObject> setVaccinationEfficacyByAgeRange(ConditionalProbabilityDistribution dist) throws ApolloSetterException {
        ConditionalProbabilityDistributionSetter setter = new ConditionalProbabilityDistributionSetter(apolloTranslationEngine, type + "." + VACC_EFFICACY_BY_AGE_RANGE, section);
        return setter.set(dist);
    }
    
    public List<SetterReturnObject> set(VaccinationEfficacyForSimulatorConfiguration vefsc) throws ApolloSetterException {
        List<SetterReturnObject> result = new ArrayList<SetterReturnObject>();
        
        result.addAll(setTreatmentEfficacy(vefsc));
        result.addAll(setAverageVaccinationEfficacy(vefsc.getAverageVaccinationEfficacy()));
        
        if (vefsc.getVaccinationEfficaciesConditionedOnTimeSinceMostRecentDose() != null) {
            result.addAll(setVaccEfficacySinceDose(vefsc.getVaccinationEfficaciesConditionedOnTimeSinceMostRecentDose()));
        }
        
        if (vefsc.getVaccinationEfficacyConditionedOnAgeRange() != null) {
            result.addAll(setVaccinationEfficacyByAgeRange(vefsc.getVaccinationEfficacyConditionedOnAgeRange()));
        }
        
        return result;
    }
}

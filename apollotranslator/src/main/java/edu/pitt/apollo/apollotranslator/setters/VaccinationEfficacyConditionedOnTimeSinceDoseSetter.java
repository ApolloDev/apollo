package edu.pitt.apollo.apollotranslator.setters;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.TimeAxisCategoryLabels;
import edu.pitt.apollo.types.v4_0.VaccinationEfficacyConditionedOnTimeSinceDose;

/**
 * Created by JDL50 on 1/13/14.
 */
public class VaccinationEfficacyConditionedOnTimeSinceDoseSetter extends AbstractTypedSetter<VaccinationEfficacyConditionedOnTimeSinceDose> {

    public static final String NUM_DOSES = "numberOfDosesAdministered";
    public static final String TIME_INTERVAL_lABELS = "timeIntervalLabelDefinitions";
    public static final String EFFICACY_ARRAY = "vaccinationEfficacyConditionedOnTimeSinceMostRecentDose";

    public VaccinationEfficacyConditionedOnTimeSinceDoseSetter(String prefix,
            String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(prefix, section, apolloTranslationEngine);
    }

    public List<SetterReturnObject> setNumberOfDosesAdministered(BigInteger numberOfDosesAdministered) throws ApolloSetterException {
        return setValue(NUM_DOSES, numberOfDosesAdministered.toString(), section);
    }

    public List<SetterReturnObject> setTimeIntervalLabelDefinitions(List<TimeAxisCategoryLabels> defs) throws ApolloSetterException {
        List<SetterReturnObject> sroList;
        if (defs != null && defs.size() > 0) {

            sroList = setValue(TIME_INTERVAL_lABELS, "(list values described below)", section);

            ListSetter setter = new ListSetter(TimeAxisCategoryLabelsSetter.class, TimeAxisCategoryLabels.class,
                    defs, apolloTranslationEngine, section, type + "." + TIME_INTERVAL_lABELS);

            List<SetterReturnObject> result = setter.set();
            sroList.get(0).setSubApolloParameters(result); // use the first sro for now since it will be the one to have
            // its Apollo param value string printed. This should be improved in the future
        } else {
            sroList = setValue(TIME_INTERVAL_lABELS, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;
    }

    public List<SetterReturnObject> setVaccinationEfficacyByTimeSinceMostRecentDose(List<Double> efficacyArray) throws ApolloSetterException {
        return setValue(EFFICACY_ARRAY, Arrays.toString(efficacyArray.toArray()), section);
    }

    @Override
    public List<SetterReturnObject> set(VaccinationEfficacyConditionedOnTimeSinceDose vaccinationEfficacyByTimeSinceDose) throws ApolloSetterException {
        results.addAll(setNumberOfDosesAdministered(vaccinationEfficacyByTimeSinceDose.getNumberOfDosesAdministered()));
        results.addAll(setTimeIntervalLabelDefinitions(vaccinationEfficacyByTimeSinceDose.getTimeIntervalLabelDefinitions()));
        results.addAll(setVaccinationEfficacyByTimeSinceMostRecentDose(vaccinationEfficacyByTimeSinceDose.getVaccinationEfficacyConditionedOnTimeSinceMostRecentDose()));
        return results;
    }
}

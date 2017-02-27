package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.CaseQuarantineControlMeasure;
import edu.pitt.apollo.types.v4_0_1.Duration;
import edu.pitt.apollo.types.v4_0_1.ProbabilisticParameter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 24, 2014
 * Time: 12:27:32 PM
 * Class: CaseQuarantineControlMeasureSetter
 * IDE: NetBeans 6.9.1
 */
public class CaseQuarantineControlMeasureSetter extends InfectiousDiseaseControlMeasureSetter<CaseQuarantineControlMeasure> {

    public static final String SECTION = "CASE QUARANTINE CONTROL STRATEGY";
    private static final String QUARANTINE_PERIOD = "quarantinePeriod";
    private static final String COMPLIANCE = "compliance";
    private static final String HOUSEHOLD_TRANSMISSION_MULTIPLIER = "householdTransmissionMultiplier";
    private static final String SCHOOL_TRANSMISSION_MULTIPLIER = "schoolTransmissionMultiplier";
    private static final String WORKPLACE_TRANSMISSION_MULTIPLIER = "workplaceTransmissionMultiplier";

    public CaseQuarantineControlMeasureSetter() {
    }

    public CaseQuarantineControlMeasureSetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(apolloTranslationEngine, type, section);
    }

    private List<SetterReturnObject> setQuarantinePeriod(Duration duration) throws ApolloSetterException {
        DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + QUARANTINE_PERIOD, SECTION);
        return setter.set(duration);
    }

    private List<SetterReturnObject> setCompliance(ProbabilisticParameter compliance) throws ApolloSetterException {
        ProbabilisticParameterSetter setter = new ProbabilisticParameterSetter(apolloTranslationEngine, type + "." + COMPLIANCE, section);
        return setter.set(compliance);
    }

    private List<SetterReturnObject> setHouseholdTransmissionMultiplier(double multiplier) throws ApolloSetterException {
        return setValue(HOUSEHOLD_TRANSMISSION_MULTIPLIER, Double.toString(multiplier), SECTION);
    }

    private List<SetterReturnObject> setSchoolTransmissionMultiplier(double multiplier) throws ApolloSetterException {
        return setValue(SCHOOL_TRANSMISSION_MULTIPLIER, Double.toString(multiplier), SECTION);
    }

    private List<SetterReturnObject> setWorkplaceTransmissionMultiplier(double multiplier) throws ApolloSetterException {
        return setValue(WORKPLACE_TRANSMISSION_MULTIPLIER, Double.toString(multiplier), SECTION);
    }

    @Override
    public List<SetterReturnObject> set(CaseQuarantineControlMeasure t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setInfectiousDiseaseControlMeasure(t));
        list.addAll(setQuarantinePeriod(t.getQuarantinePeriod()));
        list.addAll(setCompliance(t.getCompliance()));
        list.addAll(setHouseholdTransmissionMultiplier(t.getHouseholdTransmissionMultiplier()));
        list.addAll(setSchoolTransmissionMultiplier(t.getSchoolTransmissionMultiplier()));
        list.addAll(setWorkplaceTransmissionMultiplier(t.getWorkplaceTransmissionMultiplier()));
        return list;
    }
}

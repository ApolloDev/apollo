package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ProbabilisticParameter;
import edu.pitt.apollo.types.v4_0_2.VoluntaryHouseholdQuarantineControlMeasure;

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
public class VoluntaryHouseholdQuarantineControlMeasureSetter extends InfectiousDiseaseControlMeasureSetter<VoluntaryHouseholdQuarantineControlMeasure> {

    public static final String SECTION = "VOLUNTARY HOUSEHOLD QUARANTINE CONTROL STRATEGY";
    private static final String COMPLIANCE = "compliance";

    public VoluntaryHouseholdQuarantineControlMeasureSetter() {
    }

    public VoluntaryHouseholdQuarantineControlMeasureSetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(apolloTranslationEngine, type, section);
    }

    private List<SetterReturnObject> setCompliance(ProbabilisticParameter compliance) throws ApolloSetterException {
        ProbabilisticParameterSetter setter = new ProbabilisticParameterSetter(apolloTranslationEngine, type + "." + COMPLIANCE, section);
        return setter.set(compliance);
    }

    @Override
    public List<SetterReturnObject> set(VoluntaryHouseholdQuarantineControlMeasure t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        list.addAll(setInfectiousDiseaseControlMeasure(t));
        list.addAll(setCompliance(t.getCompliance()));
        return list;
    }
}

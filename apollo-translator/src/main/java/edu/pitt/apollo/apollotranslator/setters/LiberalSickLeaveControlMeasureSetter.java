package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.LiberalSickLeaveControlMeasure;
import edu.pitt.apollo.types.v4_0.ProbabilisticParameter;
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
public class LiberalSickLeaveControlMeasureSetter extends InfectiousDiseaseControlMeasureSetter<LiberalSickLeaveControlMeasure> {

    public static final String SECTION = "LIBERAL SICK LEAVE CONTROL STRATEGY";
    private static final String COMPLIANCE = "compliance";

    public LiberalSickLeaveControlMeasureSetter() {
    }

    public LiberalSickLeaveControlMeasureSetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(apolloTranslationEngine, type, section);
    }

    private List<SetterReturnObject> setCompliance(ProbabilisticParameter compliance) throws ApolloSetterException {
        ProbabilisticParameterSetter setter = new ProbabilisticParameterSetter(apolloTranslationEngine, type + "." + COMPLIANCE, section);
        return setter.set(compliance);
    }

    @Override
    public List<SetterReturnObject> set(LiberalSickLeaveControlMeasure t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<>();
        list.addAll(setInfectiousDiseaseControlMeasure(t));
        list.addAll(setCompliance(t.getCompliance()));
        return list;
    }
}

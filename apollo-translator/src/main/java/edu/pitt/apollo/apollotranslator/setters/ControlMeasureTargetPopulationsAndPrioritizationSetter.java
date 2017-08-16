package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ControlMeasureTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v4_0_2.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v4_0_2.TargetPriorityPopulation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 1, 2014
 * Time: 4:32:42 PM
 * Class: ControlMeasureTargetPopulationsAndPrioritizationSetter
 * IDE: NetBeans 6.9.1
 */
public class ControlMeasureTargetPopulationsAndPrioritizationSetter extends AbstractTypedSetter<ControlMeasureTargetPopulationsAndPrioritization> {

    public static final String CONTROL_MEASURE_NAMED_PRIORITIZATION_SCHEME_FIELD = "controlStrategyNamedPrioritizationScheme";
    public static final String CONTROL_MEASURE_TARGET_POPULATIONS_AND_PRIORITIZATION = "controlStrategyTargetPopulationsAndPrioritization";

    public ControlMeasureTargetPopulationsAndPrioritizationSetter() {
    }

    public ControlMeasureTargetPopulationsAndPrioritizationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setControlMeasureNamedPrioritizationScheme(NamedPrioritizationSchemeEnum scheme) throws ApolloSetterException {
        return setValue(CONTROL_MEASURE_NAMED_PRIORITIZATION_SCHEME_FIELD, scheme.toString(), section);
    }

    private List<SetterReturnObject> setControlMeasureTargetPopulationsAndPrioritization(List<TargetPriorityPopulation> populations) throws ApolloSetterException {

        List<SetterReturnObject> sroList;
        if (populations != null && populations.size() > 0) {

            sroList = setValue(CONTROL_MEASURE_TARGET_POPULATIONS_AND_PRIORITIZATION, "(list values described below)", section);

            ListSetter setter = new ListSetter(TargetPriorityPopulationSetter.class, TargetPriorityPopulation.class,
                    populations, apolloTranslationEngine, section, type + "." + CONTROL_MEASURE_TARGET_POPULATIONS_AND_PRIORITIZATION);

//            TargetPriorityPopulationSetter setter = new TargetPriorityPopulationSetter(apolloTranslationEngine,
//                    type + "." + CONTROL_MEASURE_TARGET_POPULATIONS_AND_PRIORITIZATION, section);
            List<SetterReturnObject> result = setter.set();
//            if (populations != null && populations.size() > 0) {
//                for (int i = 0; i < populations.size(); i++) {
//                    TargetPriorityPopulationSetter setter = new TargetPriorityPopulationSetter(apolloTranslationEngine,
//                            type + "." + CONTROL_MEASURE_TARGET_POPULATIONS_AND_PRIORITIZATION + "." + "[" + i + "]", section);
//                    TargetPriorityPopulation population = populations.get(i);
//                    result.addAll(setter.setTargetPriorityPopulation(population));
//                }
//
            sroList.get(0).setSubApolloParameters(result); // use the first sro for now since it will be the one to have
//                // its Apollo param value string printed. This should be improved in the future
//            }

        } else {
            sroList = setValue(CONTROL_MEASURE_TARGET_POPULATIONS_AND_PRIORITIZATION, PARAM_IS_NOT_SET_LABEL, section);
        }

        return sroList;

    }
    
    private List<SetterReturnObject> setControlMeasureTargetPopulationsAndPrioritization() throws ApolloSetterException {
        return setValue("", GENERIC_IS_NOT_NULL_LABEL, section);
    }

    @Override
    public List<SetterReturnObject> set(ControlMeasureTargetPopulationsAndPrioritization t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setControlMeasureTargetPopulationsAndPrioritization());
        if (t.getControlMeasureNamedPrioritizationScheme() != null) {
            list.addAll(setControlMeasureNamedPrioritizationScheme(t.getControlMeasureNamedPrioritizationScheme()));
        } else if (t.getControlMeasureTargetPopulationsAndPrioritization() != null) {
            list.addAll(setControlMeasureTargetPopulationsAndPrioritization(t.getControlMeasureTargetPopulationsAndPrioritization()));
        }
        return list;
    }
}

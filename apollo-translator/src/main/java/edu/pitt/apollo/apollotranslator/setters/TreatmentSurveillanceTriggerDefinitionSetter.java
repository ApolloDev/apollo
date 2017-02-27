package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.OperatorEnum;
import edu.pitt.apollo.types.v4_0_1.TreatmentSurveillanceCapability;
import edu.pitt.apollo.types.v4_0_1.TreatmentSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v4_0_1.UnitOfMeasureEnum;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 4, 2014
 * Time: 11:29:47 AM
 * Class: TreatmentSurveillanceTriggerDefinitionSetter
 * IDE: NetBeans 6.9.1
 */
public class TreatmentSurveillanceTriggerDefinitionSetter extends AbstractTypedSetter<TreatmentSurveillanceTriggerDefinition> {

    private static final String REACTIVE_CONTROL_STRATEGY_TEST = "reactiveControlMeasureTest";
    private static final String REACTIVE_CONTROL_STRATEGY_THRESHOLD = "reactiveControlMeasureThreshold";
    private static final String REACTIVE_CONTROL_STRATEGY_OPERATOR = "reactiveControlMeasureOperator";
    private static final String UNIT_OF_MEASURE_FOR_THRESHOLD = "unitOfMeasureForThreshold";
    private static final String TREATMENT_SURVEILLANCE_CAPABILITY = "treatmentSurveillanceCapability";

    public TreatmentSurveillanceTriggerDefinitionSetter() {
    }

    public TreatmentSurveillanceTriggerDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setReactiveControlMeasureTest(String test) throws ApolloSetterException {
        return setValue(REACTIVE_CONTROL_STRATEGY_TEST, test, section);
    }

    private List<SetterReturnObject> setReactiveControlMeasureThreshold(int threshold) throws ApolloSetterException {
        return setValue(REACTIVE_CONTROL_STRATEGY_THRESHOLD, Integer.toString(threshold), section);
    }

    private List<SetterReturnObject> setReactiveControlMeasureOperator(OperatorEnum operator) throws ApolloSetterException {
        return setValue(REACTIVE_CONTROL_STRATEGY_OPERATOR, operator.toString(), section);
    }

    private List<SetterReturnObject> setUnitOfMeasureForThreshold(UnitOfMeasureEnum unit) throws ApolloSetterException {
        return setValue(UNIT_OF_MEASURE_FOR_THRESHOLD, unit.toString(), section);
    }

    private List<SetterReturnObject> setTreatmentSurveillanceCapability(TreatmentSurveillanceCapability capability) throws ApolloSetterException {
        TreatmentSurveillanceCapabilitySetter setter = new TreatmentSurveillanceCapabilitySetter(apolloTranslationEngine, type + "." + TREATMENT_SURVEILLANCE_CAPABILITY, section);
        return setter.set(capability);
    }

    private List<SetterReturnObject> setReactiveTriggerDefinition() throws ApolloSetterException {
        return setValue("", GENERIC_IS_NOT_NULL_LABEL, section);
    }

    @Override
    public List<SetterReturnObject> set(TreatmentSurveillanceTriggerDefinition t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setReactiveTriggerDefinition());
        list.addAll(setReactiveControlMeasureTest(t.getReactiveControlMeasureTest()));
        list.addAll(setReactiveControlMeasureThreshold(t.getReactiveControlMeasureThreshold()));
        list.addAll(setReactiveControlMeasureOperator(t.getReactiveControlMeasureOperator()));
        list.addAll(setUnitOfMeasureForThreshold(t.getUnitOfMeasureForThreshold()));
        list.addAll(setTreatmentSurveillanceCapability(t.getTreatmentSurveillanceCapability()));
        return list;
    }
}

package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v4_0_1.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v4_0_1.OperatorEnum;
import edu.pitt.apollo.types.v4_0_1.UnitOfMeasureEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 24, 2014
 * Time: 10:42:10 AM
 * Class: DiseaseSurveillanceTriggerDefinitionSetter
 * IDE: NetBeans 6.9.1
 */
public class DiseaseSurveillanceTriggerDefinitionSetter extends AbstractTypedSetter<DiseaseSurveillanceTriggerDefinition> {

    private static final String REACTIVE_CONTROL_STRATEGY_TEST = "reactiveControlMeasureTest";
    private static final String REACTIVE_CONTROL_STRATEGY_THRESHOLD = "reactiveControlMeasureThreshold";
    private static final String REACTIVE_CONTROL_STRATEGY_OPERATOR = "reactiveControlMeasureOperator";
    private static final String UNIT_OF_MEASURE_FOR_THRESHOLD = "unitOfMeasureForThreshold";
    private static final String DISEASE_SURVEILLANCE_CAPABILITY = "diseaseSurveillanceCapability";

    public DiseaseSurveillanceTriggerDefinitionSetter() {
        
    }
    
    public DiseaseSurveillanceTriggerDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
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

    private List<SetterReturnObject> setDiseaseSurveillanceCapability(DiseaseSurveillanceCapability capability) throws ApolloSetterException {
        DiseaseSurveillanceCapabilitySetter setter = new DiseaseSurveillanceCapabilitySetter(apolloTranslationEngine, type + "." + DISEASE_SURVEILLANCE_CAPABILITY, section);
        return setter.set(capability);
    }
    
    private List<SetterReturnObject> setReactiveTriggerDefinition() throws ApolloSetterException {
        return setValue("", GENERIC_IS_NOT_NULL_LABEL, section);
    }

    @Override
    public List<SetterReturnObject> set(DiseaseSurveillanceTriggerDefinition t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setReactiveTriggerDefinition());
        list.addAll(setReactiveControlMeasureTest(t.getReactiveControlMeasureTest()));
        list.addAll(setReactiveControlMeasureThreshold(t.getReactiveControlMeasureThreshold()));
        list.addAll(setReactiveControlMeasureOperator(t.getReactiveControlMeasureOperator()));
        list.addAll(setUnitOfMeasureForThreshold(t.getUnitOfMeasureForThreshold()));
        list.addAll(setDiseaseSurveillanceCapability(t.getDiseaseSurveillanceCapability()));
        return list;
    }
}

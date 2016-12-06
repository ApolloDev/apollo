package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_1.ConditioningVariable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 4:11:02 PM
 * Class: ConditionalProbabilityDistributionSetter
 * IDE: NetBeans 6.9.1
 */
public class ConditionalProbabilityDistributionSetter extends AbstractTypedSetter<ConditionalProbabilityDistribution> {

    private static final String FIRST_CONDITIONAING_VARIABLE = "firstConditioningVariable";

    public ConditionalProbabilityDistributionSetter() {
    }

    public ConditionalProbabilityDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setFirstConditioningVariable(ConditioningVariable variable) throws ApolloSetterException {
        ConditioningVariableSetter setter = new ConditioningVariableSetter(apolloTranslationEngine, type + "." + FIRST_CONDITIONAING_VARIABLE, section);
        return setter.set(variable);
    }

    @Override
    public List<SetterReturnObject> set(ConditionalProbabilityDistribution t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setFirstConditioningVariable(t.getFirstConditioningVariable()));
        return list;
    }
}

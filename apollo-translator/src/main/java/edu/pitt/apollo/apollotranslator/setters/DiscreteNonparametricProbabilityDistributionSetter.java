package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v4_0.ProbabilityValuePair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Mar 21, 2014
 Time: 4:01:20 PM
 Class: DiscreteNonparametricProbabilityDistributionSetter
 IDE: NetBeans 6.9.1
 */
public class DiscreteNonparametricProbabilityDistributionSetter extends AbstractTypedSetter<DiscreteNonparametricProbabilityDistribution> {

    private static final String PROBABILITY_VALUE_PAIRS = "probabilityValuePairs";

    public DiscreteNonparametricProbabilityDistributionSetter() {
    }

    public DiscreteNonparametricProbabilityDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    public List<SetterReturnObject> setProbabilityValuePairs(List<ProbabilityValuePair> pairs) throws ApolloSetterException {
        ListSetter setter = new ListSetter(ProbabilityValuePairSetter.class,
                ProbabilityValuePair.class,
                pairs, apolloTranslationEngine, section, type + "." + PROBABILITY_VALUE_PAIRS);

        return setter.set();
    }

    @Override
    public List<SetterReturnObject> set(DiscreteNonparametricProbabilityDistribution t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

        list.addAll(setProbabilityValuePairs(t.getProbabilityValuePairs()));

        return list;
    }
}

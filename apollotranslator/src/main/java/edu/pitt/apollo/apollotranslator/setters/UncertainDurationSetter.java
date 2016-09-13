package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.ProbabilityDistribution;
import edu.pitt.apollo.types.v4_0.UncertainDuration;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 3, 2014
 * Time: 1:27:53 PM
 * Class: UncertainDurationSetter
 * IDE: NetBeans 6.9.1
 */
public class UncertainDurationSetter extends AbstractTypedSetter<UncertainDuration> {

    private static final String PROBABILITY_DISTRIBUTION = "probabilityDistribution";

    public UncertainDurationSetter() {
    }

    public UncertainDurationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setProbabilityDistribution(ProbabilityDistribution dist) throws ApolloSetterException {
        ProbabilityDistributionSetter setter = new ProbabilityDistributionSetter(apolloTranslationEngine, type + "." + PROBABILITY_DISTRIBUTION, section);
        return setter.set(dist);
    }

    @Override
    public List<SetterReturnObject> set(UncertainDuration t) throws ApolloSetterException {
        List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
        list.addAll(setProbabilityDistribution(t.getProbabilityDistribution()));
        return list;
    }
}

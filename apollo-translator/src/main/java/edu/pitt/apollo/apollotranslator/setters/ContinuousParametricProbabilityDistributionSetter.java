package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.types.v4_0.ContinuousParametricProbabilityDistribution;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 5:06:25 PM
 * Class: ContinuousParametricProbabilityDistributionSetter
 * IDE: NetBeans 6.9.1
 */
public abstract class ContinuousParametricProbabilityDistributionSetter<T extends ContinuousParametricProbabilityDistribution> extends AbstractTypedSetter<T> {

    public ContinuousParametricProbabilityDistributionSetter() {
    }

    public ContinuousParametricProbabilityDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }
}

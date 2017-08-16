package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ContinuousUniformDistribution;
import edu.pitt.apollo.types.v4_0_2.LogNormalDistribution;
import edu.pitt.apollo.types.v4_0_2.ParametricProbabilityDistribution;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 5:09:35 PM
 * Class: ParametricProbabilityDistributionSetter
 * IDE: NetBeans 6.9.1
 */
public class ParametricProbabilityDistributionSetter extends AbstractTypedSetter<ParametricProbabilityDistribution> {

	public ParametricProbabilityDistributionSetter() {
	}

	public ParametricProbabilityDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(ParametricProbabilityDistribution t) throws ApolloSetterException {

		AbstractTypedSetter setter;
		if (t instanceof LogNormalDistribution) {
			setter = new LogNormalDistributionSetter(apolloTranslationEngine, type, section);
		} else if (t instanceof ContinuousUniformDistribution) {
			setter = new ContinuousUniformDistributionSetter(apolloTranslationEngine, type, section);
		} else {
			throw new ApolloSetterException("Unrecognized probability distribution " + t.getClass().getCanonicalName() + " in ParametricProbabilityDistributionSetter");
		}

		return setter.set(t);
	}
}

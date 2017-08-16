package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.NonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_2.ParametricProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_2.UnconditionalProbabilityDistribution;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 4:31:58 PM
 * Class: UnconditionalProbabilityDistributionSetter
 * IDE: NetBeans 6.9.1
 */
public class UnconditionalProbabilityDistributionSetter extends AbstractTypedSetter<UnconditionalProbabilityDistribution> {

	public UnconditionalProbabilityDistributionSetter() {
	}

	public UnconditionalProbabilityDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(UnconditionalProbabilityDistribution t) throws ApolloSetterException {

		AbstractTypedSetter setter;
		if (t instanceof ParametricProbabilityDistribution) {
			setter = new ParametricProbabilityDistributionSetter(apolloTranslationEngine, type, section);
		} else if (t instanceof NonparametricProbabilityDistribution) {
			setter = new NonparametricProbabilityDistributionSetter(apolloTranslationEngine, type, section);
		} else {
			throw new ApolloSetterException("Unrecognized probability distribution in UnconditionalProbabilityDistributionSetter");
		}

		return setter.set(t);
	}
}

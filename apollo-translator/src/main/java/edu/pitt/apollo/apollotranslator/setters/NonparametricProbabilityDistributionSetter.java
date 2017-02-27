package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.DiscreteNonparametricProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_1.NonparametricProbabilityDistribution;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 19, 2014
 * Time: 2:37:58 PM
 * Class: NonparametricProbabilityDistributionSetter
 */
public class NonparametricProbabilityDistributionSetter extends AbstractTypedSetter<NonparametricProbabilityDistribution> {

	public NonparametricProbabilityDistributionSetter() {
	}

	public NonparametricProbabilityDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(NonparametricProbabilityDistribution t) throws ApolloSetterException {
		AbstractTypedSetter setter;
		if (t instanceof DiscreteNonparametricProbabilityDistribution) {
			setter = new DiscreteNonparametricProbabilityDistributionSetter(apolloTranslationEngine, type, section);
		} else {
			throw new ApolloSetterException("Unrecognized probability distribution in ParametricProbabilityDistributionSetter");
		}

		return setter.set(t);
	}

}

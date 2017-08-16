package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.ConditionalProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_2.ProbabilityDistribution;
import edu.pitt.apollo.types.v4_0_2.UnconditionalProbabilityDistribution;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2014
 * Time: 4:46:17 PM
 * Class: ProbabilityDistributionSetter
 * IDE: NetBeans 6.9.1
 */
public class ProbabilityDistributionSetter extends AbstractTypedSetter<ProbabilityDistribution> {

	public ProbabilityDistributionSetter() {
	}

	public ProbabilityDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(ProbabilityDistribution t) throws ApolloSetterException {

		AbstractTypedSetter setter;
		if (t instanceof UnconditionalProbabilityDistribution) {
			setter = new UnconditionalProbabilityDistributionSetter(apolloTranslationEngine, type, section);
		} else if (t instanceof ConditionalProbabilityDistribution) {
			setter = new ConditionalProbabilityDistributionSetter(apolloTranslationEngine, type, section);
		} else {
			throw new ApolloSetterException("Unrecognized probability distribution type in ProbabilityDistributionSetter");
		}

		return setter.set(t);
	}
}

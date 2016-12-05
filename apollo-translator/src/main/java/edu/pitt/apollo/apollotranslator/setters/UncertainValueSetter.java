package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.MeanWithConfidenceInterval;
import edu.pitt.apollo.types.v4_0.MeanWithStandardDeviation;
import edu.pitt.apollo.types.v4_0.ProbabilityDistribution;
import edu.pitt.apollo.types.v4_0.UncertainValue;
import java.util.List;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time: 1:27:53 PM Class: UncertainDurationSetter IDE: NetBeans 6.9.1
 */
public class UncertainValueSetter extends AbstractTypedSetter<UncertainValue> {


	public UncertainValueSetter() {
	}

	public UncertainValueSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(UncertainValue t) throws ApolloSetterException {
		AbstractTypedSetter setter;
		if (t instanceof ProbabilityDistribution) {
			setter = new ProbabilityDistributionSetter(apolloTranslationEngine, type, section);
		} else if (t instanceof MeanWithStandardDeviation) {
			setter = new MeanWithStandardDeviationSetter(apolloTranslationEngine, type, section);
		} else if (t instanceof MeanWithConfidenceInterval) {
			setter = new MeanWithConfidenceIntervalSetter(apolloTranslationEngine, type, section);
		} else {
			throw new ApolloSetterException("Unrecognized uncertain value type in UncertainValueSetter");
		}

		return setter.set(t);
	}
}

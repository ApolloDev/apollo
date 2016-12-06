package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ContinuousUniformDistribution;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 17, 2014
 * Time: 2:44:08 PM
 * Class: ContinuousUniformDistributionSetter
 */
public class ContinuousUniformDistributionSetter extends ContinuousParametricProbabilityDistributionSetter<ContinuousUniformDistribution> {

	private static final String MINIMUM_VALUE = "minimumValue";
	private static final String MAXIMUM_VALUE = "maximumValue";

	public ContinuousUniformDistributionSetter() {

	}

	public ContinuousUniformDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(apolloTranslationEngine, prefix, section);
	}

	private List<SetterReturnObject> setMinimumValue(double minimumValue) throws ApolloSetterException {
		return setValue(MINIMUM_VALUE, Double.toString(minimumValue), section);
	}

	private List<SetterReturnObject> setMaximumValue(double maximumValue) throws ApolloSetterException {
		return setValue(MAXIMUM_VALUE, Double.toString(maximumValue), section);
	}

	@Override
	public List<SetterReturnObject> set(ContinuousUniformDistribution t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

		list.addAll(setMinimumValue(t.getMinimumValue()));
		list.addAll(setMaximumValue(t.getMaximumValue()));
				
		return list;
	}

}

package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.LogNormalDistribution;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 19, 2014
 * Time: 11:34:09 AM
 * Class: LogNormalDistributionSetter
 */
public class LogNormalDistributionSetter extends ContinuousParametricProbabilityDistributionSetter<LogNormalDistribution> {

	private static final String MEAN = "mean";
	private static final String STANDARD_DEVIATION = "standardDeviation";
	private static final String SHIFT_RIGHT = "shiftRight";
	private static final String CUT_TAIL_AT = "cutTailAt";

	public LogNormalDistributionSetter() {

	}

	public LogNormalDistributionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(apolloTranslationEngine, prefix, section);
	}

	public List<SetterReturnObject> setMean(double mean) throws ApolloSetterException {
		return setValue(MEAN, Double.toString(mean), section);
	}

	public List<SetterReturnObject> setStandardDeviation(double std) throws ApolloSetterException {
		return setValue(STANDARD_DEVIATION, Double.toString(std), section);
	}

	public List<SetterReturnObject> setShiftRight(Double shiftRight) throws ApolloSetterException {
		return setValue(SHIFT_RIGHT, Double.toString(shiftRight), section);
	}

	public List<SetterReturnObject> setCutTailAt(Double cutTailAt) throws ApolloSetterException {
		return setValue(CUT_TAIL_AT, Double.toString(cutTailAt), section);
	}

	@Override
	public List<SetterReturnObject> set(LogNormalDistribution dist) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setMean(dist.getMean()));
		list.addAll(setStandardDeviation(dist.getStandardDeviation()));
		if (dist.getShiftRight() != null) {
			list.addAll(setShiftRight(dist.getShiftRight()));
		}
		if (dist.getCutTailAt() != null) {
			list.addAll(setCutTailAt(dist.getCutTailAt()));
		}

		return list;
	}

}

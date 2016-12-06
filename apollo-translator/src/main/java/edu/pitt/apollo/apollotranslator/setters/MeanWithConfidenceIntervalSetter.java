package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.MeanWithConfidenceInterval;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 15, 2014
 * Time: 5:51:26 PM
 * Class: MeanWithConfidenceIntervalSetter
 */
public class MeanWithConfidenceIntervalSetter extends AbstractTypedSetter<MeanWithConfidenceInterval> {

	private static final String MEAN = "mean";
	private static final String LOWER_BOUND = "lowerBound";
	private static final String UPPER_BOUND = "upperBound";
	private static final String CONFIDENCE = "confidence";
	private static final String SAMPLE_SIZE = "sampleSize";

	public MeanWithConfidenceIntervalSetter() {

	}

	public MeanWithConfidenceIntervalSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setMean(double mean) throws ApolloSetterException {
		return setValue(MEAN, Double.toString(mean), section);
	}

	private List<SetterReturnObject> setLowerBound(double lowerBound) throws ApolloSetterException {
		return setValue(LOWER_BOUND, Double.toString(lowerBound), section);
	}

	private List<SetterReturnObject> setUpperBound(double upperBound) throws ApolloSetterException {
		return setValue(UPPER_BOUND, Double.toString(upperBound), section);
	}

	private List<SetterReturnObject> setConfidence(BigInteger confidence) throws ApolloSetterException {
		return setValue(CONFIDENCE, confidence.toString(), section);
	}

	private List<SetterReturnObject> setSampleSize(BigInteger sampleSize) throws ApolloSetterException {
		return setValue(SAMPLE_SIZE, sampleSize.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(MeanWithConfidenceInterval t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setMean(t.getMean()));
		list.addAll(setLowerBound(t.getLowerBound()));
		list.addAll(setUpperBound(t.getUpperBound()));
		list.addAll(setConfidence(t.getConfidence()));
		if (t.getSampleSize() != null) {
			list.addAll(setSampleSize(t.getSampleSize()));
		}

		return list;
	}

}

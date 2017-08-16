package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.MeanWithStandardDeviation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 15, 2014
 * Time: 5:43:15 PM
 * Class: MeanWithStandardDeviationSetter
 */
public class MeanWithStandardDeviationSetter extends AbstractTypedSetter<MeanWithStandardDeviation> {

	private static final String MEAN = "mean";
	private static final String STANDARD_DEVIATION = "standardDeviation";
	private static final String SAMPLE_SIZE = "sampleSize";
	
	public MeanWithStandardDeviationSetter() {
		
	}
	
	public MeanWithStandardDeviationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}
	
	private List<SetterReturnObject> setMean(double mean) throws ApolloSetterException {
		return setValue(MEAN, Double.toString(mean), section);
	}
	
	private List<SetterReturnObject> setStandardDeviation(double standardDeviation) throws ApolloSetterException {
		return setValue(STANDARD_DEVIATION, Double.toString(standardDeviation), section);
	}
	
	private List<SetterReturnObject> setSampleSize(BigInteger sampleSize) throws ApolloSetterException {
		return setValue(SAMPLE_SIZE, sampleSize.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(MeanWithStandardDeviation t) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setMean(t.getMean()));
		list.addAll(setStandardDeviation(t.getStandardDeviation()));
		if (t.getSampleSize() != null) {
			list.addAll(setSampleSize(t.getSampleSize()));
		}
		
		return list;
	}

}

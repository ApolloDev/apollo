package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.ProbabilityDistribution;
import edu.pitt.apollo.types.v4_0.ReproductionNumber;
import edu.pitt.apollo.types.v4_0.UncertainValue;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Oct 8, 2014 Time: 10:57:19 AM Class: ReproductionNumberSetter
 */
public class ReproductionNumberSetter extends AbstractTypedSetter<ReproductionNumber> {

	private static final String EXACT_VALUE = "exactValue";
	private static final String UNCERTAIN_VALUE = "uncertainValue";
	private static final String REFERENCE_ID = "referenceId";

	public ReproductionNumberSetter() {
	}

	public ReproductionNumberSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setExactValue(Double value) throws ApolloSetterException {
		return setValue(EXACT_VALUE, value.toString(), section);
	}

	private List<SetterReturnObject> setUncertainValue(UncertainValue uncertainValue) throws ApolloSetterException {
		UncertainValueSetter setter = new UncertainValueSetter(apolloTranslationEngine, type + "." + UNCERTAIN_VALUE, section);
		return setter.set(uncertainValue);
	}

	private List<SetterReturnObject> setReferenceId(BigInteger id) throws ApolloSetterException {
		return setValue(REFERENCE_ID, id.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(ReproductionNumber t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		if (t.getExactValue() != null) {
			list.addAll(setExactValue(t.getExactValue()));
		} else if (t.getUncertainValue() != null) {
			list.addAll(setUncertainValue(t.getUncertainValue()));
		}

		if (t.getReferenceId() != null) {
			list.addAll(setReferenceId(t.getReferenceId()));
		}

		return list;
	}

}

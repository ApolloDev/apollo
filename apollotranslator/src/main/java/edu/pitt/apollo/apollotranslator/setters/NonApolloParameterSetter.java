package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.NonApolloParameter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Mar 24, 2014 Time: 1:00:15 PM Class: NonApolloParameterSetter IDE: NetBeans 6.9.1
 */
public class NonApolloParameterSetter extends AbstractTypedSetter<NonApolloParameter> {

	public static final String SECTION = "NON APOLLO PARAMETERS";
	private static final String DESCRITION = "description";
	private static final String PARAMETER_NAME = "parameterName";
	private static final String NEW_ELEMENT = "parameterValue";

	public NonApolloParameterSetter() {
	}

	public NonApolloParameterSetter(String type, String section,
			ApolloTranslationEngine apolloTranslationEngine) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setDescription(String description) throws ApolloSetterException {
		return setValue(DESCRITION, description, SECTION);
	}

	private List<SetterReturnObject> setParameterName(String name) throws ApolloSetterException {
		return setValue(PARAMETER_NAME, name, SECTION);
	}

	private List<SetterReturnObject> setParameterValue(double value) throws ApolloSetterException {
		return setValue(NEW_ELEMENT, Double.toString(value), SECTION);
	}

	private List<SetterReturnObject> setParameterValue(int value) throws ApolloSetterException {
		return setValue(NEW_ELEMENT, Integer.toString(value), SECTION);
	}

	private List<SetterReturnObject> setParameterValue(String value) throws ApolloSetterException {
		return setValue(NEW_ELEMENT, value, SECTION);
	}

	@Override
	public List<SetterReturnObject> set(NonApolloParameter t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<>();
		list.addAll(setDescription(t.getDescription()));
		list.addAll(setParameterName(t.getParameterName()));
		if (t.getParameterFloatValue() != null) {
			list.addAll(setParameterValue(t.getParameterFloatValue()));
		} else if (t.getParameterTokenValue() != null) {
			list.addAll(setParameterValue(t.getParameterTokenValue()));
		} else {
			list.addAll(setParameterValue(t.getParameterIntValue()));
		}
		return list;
	}
}

package edu.pitt.apollo.apollotranslator.setters;

import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

public class NonScenarioParameterSetter extends AbstractSetter {

	public static final String SECTION = AbstractSetter.UNSPECIFIED_PARAM_SECTION;
	public static final String NON_APOLLO_FIELD = "nonScenarioParameter";
	public NonScenarioParameterSetter(
			ApolloTranslationEngine apolloTranslationEngine) {
		super(apolloTranslationEngine);
	}
	
	public List<SetterReturnObject> set(String value) throws ApolloSetterException {
        return setValue(NON_APOLLO_FIELD, value, SECTION);

    }
	
	

}

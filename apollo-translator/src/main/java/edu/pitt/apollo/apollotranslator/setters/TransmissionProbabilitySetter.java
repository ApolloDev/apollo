package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ContactDefinitionEnum;
import edu.pitt.apollo.types.v4_0_1.TransmissionProbability;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 9:45:50 AM
 * Class: TransmissionProbabilitySetter
 */
public class TransmissionProbabilitySetter extends AbstractTypedSetter<TransmissionProbability> {

	private static final String CONTACT_DEFINITION = "contactDefinition";
	private static final String PROBABILITY = "probability";
	
	public TransmissionProbabilitySetter() {
		
	}
	
	public TransmissionProbabilitySetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
		super(type, section, apolloTranslationEngine);
	}
	
	private List<SetterReturnObject> setContactDefinition(ContactDefinitionEnum contactDefinition) throws ApolloSetterException {
		return setValue(CONTACT_DEFINITION, contactDefinition.toString(), section);
	}
	
	private List<SetterReturnObject> setProbability(double probability) throws ApolloSetterException {
		return setValue(PROBABILITY, Double.toString(probability), section);
	}
	
	@Override
	public List<SetterReturnObject> set(TransmissionProbability t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setContactDefinition(t.getContactDefinition()));
		list.addAll(setProbability(t.getProbability()));
		
		return list;
	}

}

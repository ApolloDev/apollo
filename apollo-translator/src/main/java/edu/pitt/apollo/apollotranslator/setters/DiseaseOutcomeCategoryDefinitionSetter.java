package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.DiseaseOutcomeCategoryDefinition;
import edu.pitt.apollo.types.v4_0_2.DiseaseOutcomeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 19, 2014
 * Time: 1:54:31 PM
 * Class: DiseaseOutcomeCategoryDefinitionSetter
 */
public class DiseaseOutcomeCategoryDefinitionSetter extends AbstractTypedSetter<DiseaseOutcomeCategoryDefinition> {

	private static final String DISEASE_OUTCOME = "diseaseOutcome";
	
	public DiseaseOutcomeCategoryDefinitionSetter() {
	}

	public DiseaseOutcomeCategoryDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setDiseaseOutcome(DiseaseOutcomeEnum outcome) throws ApolloSetterException {
		return setValue(DISEASE_OUTCOME, outcome.toString(), section);
	}
	
	@Override
	public List<SetterReturnObject> set(DiseaseOutcomeCategoryDefinition t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setDiseaseOutcome(t.getDiseaseOutcome()));
		return list;
	}

}

package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.DiseaseOutcomeEnum;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 19, 2014
 * Time: 2:56:35 PM
 * Class: DiseaseOutcomeSetter
 */
public class DiseaseOutcomeSetter extends AbstractTypedSetter<DiseaseOutcomeEnum> {

	public DiseaseOutcomeSetter() {

	}

	public DiseaseOutcomeSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	@Override
	public List<SetterReturnObject> set(DiseaseOutcomeEnum outcome) throws ApolloSetterException {
		return setValue("", outcome.value(), section);
	}

}

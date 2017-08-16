package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v4_0_2.DiseaseOutcomeWithProbability;
import edu.pitt.apollo.types.v4_0_2.ProbabilisticParameter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 1, 2014
 * Time: 3:08:59 PM
 * Class: DiseaseOutcomeWithProbabilitySetter
 * IDE: NetBeans 6.9.1
 */
public class DiseaseOutcomeWithProbabilitySetter extends AbstractTypedSetter<DiseaseOutcomeWithProbability> {

	public static final String DISEASE_OUTCOME_FIELD = "diseaseOutcome";
	public static final String PROBABILITY_FIELD = "probability";

	public DiseaseOutcomeWithProbabilitySetter() {

	}

	public DiseaseOutcomeWithProbabilitySetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setDiseaseOutcome(DiseaseOutcomeEnum outcome) throws ApolloSetterException {
		DiseaseOutcomeSetter setter = new DiseaseOutcomeSetter(apolloTranslationEngine, type + "." + DISEASE_OUTCOME_FIELD, section);
		return setter.set(outcome);
	}

	private List<SetterReturnObject> setProbability(ProbabilisticParameter value) throws ApolloSetterException {
		ProbabilisticParameterSetter setter = new ProbabilisticParameterSetter(apolloTranslationEngine, type + "." + PROBABILITY_FIELD, section);
		return setter.set(value);
	}

	@Override
	public List<SetterReturnObject> set(DiseaseOutcomeWithProbability t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setDiseaseOutcome(t.getDiseaseOutcome()));
		list.addAll(setProbability(t.getProbability()));
		return list;
	}

}

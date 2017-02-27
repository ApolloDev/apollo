package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.ApolloPathogenCode;
import edu.pitt.apollo.types.v4_0_1.DiseaseOutcomeWithProbability;
import edu.pitt.apollo.types.v4_0_1.Duration;
import edu.pitt.apollo.types.v4_0_1.InfectiousDisease;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 1, 2014
 * Time: 2:57:28 PM
 * Class: InfectiousDiseaseSetter
 * IDE: NetBeans 6.9.1
 */
public class InfectiousDiseaseSetter extends AbstractTypedSetter<InfectiousDisease> {

	public static final String SECTION = "DISEASES";
	public static final String DISEASE_ID_FIELD = "diseaseId";
	public static final String SPECIES_WITH_DISEASE_FIELD = "speciesWithDisease";
	public static final String CAUSAL_PATHOGEN_FIELD = "causalPathogen";
	public static final String DISEASE_OUTCOMES_FIELD = "diseaseOutcomesWithProbabilities";
	public static final String INCUBATION_PERIOD_FIELD = "incubationPeriod";
	public static final String PRODROMAL_PEROID_FIELD = "prodromalPeriod";
	public static final String FULMINANT_PERIOD_FIELD = "fulminantPeriod";

	public InfectiousDiseaseSetter() {

	}

	public InfectiousDiseaseSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setDiseaseId(String id) throws ApolloSetterException {
		return setValue(DISEASE_ID_FIELD, id, section);
	}

	private List<SetterReturnObject> setSpeciesWithDisease(String id) throws ApolloSetterException {
		return setValue(SPECIES_WITH_DISEASE_FIELD, id, section);
	}

	private List<SetterReturnObject> setCausalPathogen(ApolloPathogenCode code) throws ApolloSetterException {
		ApolloPathogenCodeSetter setter = new ApolloPathogenCodeSetter(apolloTranslationEngine, type + "." + CAUSAL_PATHOGEN_FIELD, section);
		return setter.set(code);
	}

	private List<SetterReturnObject> setDiseaseOutcomes(List<DiseaseOutcomeWithProbability> outcomes) throws ApolloSetterException {

		List<SetterReturnObject> sroList;
		if (outcomes != null && outcomes.size() > 0) {

			sroList = setValue(DISEASE_OUTCOMES_FIELD, "(list values described below)", section);
			ListSetter setter = new ListSetter(DiseaseOutcomeWithProbabilitySetter.class, DiseaseOutcomeWithProbability.class,
					outcomes, apolloTranslationEngine, section, type + "." + DISEASE_OUTCOMES_FIELD);

//            VaccinationEfficacySetter vaccinationEfficacySetter = new VaccinationEfficacySetter(apolloTranslationEngine, type
//                    + "." + VACCINATION_EFFICACIES, section);
			List<SetterReturnObject> result = setter.set();

			sroList.get(0).setSubApolloParameters(result);
		} else {
			sroList = setValue(DISEASE_OUTCOMES_FIELD, PARAM_IS_NOT_SET_LABEL, section);
		}

		return sroList;
	}

	private List<SetterReturnObject> setIncubationPeriod(Duration period) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + INCUBATION_PERIOD_FIELD, section);
		return setter.set(period);
	}

	private List<SetterReturnObject> setProdromalPeriod(Duration period) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + PRODROMAL_PEROID_FIELD, section);
		return setter.set(period);
	}

	private List<SetterReturnObject> setFulminantPeriod(Duration period) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + FULMINANT_PERIOD_FIELD, section);
		return setter.set(period);
	}

	@Override
	public List<SetterReturnObject> set(InfectiousDisease disease) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setDiseaseId(disease.getDisease()));
		list.addAll(setSpeciesWithDisease(disease.getSpeciesWithDisease()));
		list.addAll(setCausalPathogen(disease.getCausalPathogen()));
		list.addAll(setDiseaseOutcomes(disease.getDiseaseOutcomesWithProbabilities()));

		if (disease.getIncubationPeriod() != null) {
			list.addAll(setIncubationPeriod(disease.getIncubationPeriod()));
		}
		if (disease.getProdromalPeriod() != null) {
			list.addAll(setProdromalPeriod(disease.getProdromalPeriod()));
		}
		if (disease.getFulminantPeriod() != null) {
			list.addAll(setFulminantPeriod(disease.getFulminantPeriod()));
		}
		
		return list;
	}

}

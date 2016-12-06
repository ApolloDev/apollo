package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.DrugTreatment;
import edu.pitt.apollo.types.v4_0_1.DrugTreatmentEfficacyForSimulatorConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 19, 2014
 * Time: 10:28:17 AM
 * Class: DrugTreatmentSetter
 */
public class DrugTreatmentSetter extends TreatmentSetter<DrugTreatment> {

	private static final String SECTION = IndividualTreatmentControlMeasureSetter.DRUG_TREATMENT_CONTROL_STRATEGY_SECTION;
	private static final String DRUG_ID = "drugId";
	private static final String DRUG_TREATMENT_EFFICACY = "drugTreatmentEfficacy";

	public DrugTreatmentSetter(ApolloTranslationEngine apolloTranslatorEngine, String type) {
		super(type, SECTION, apolloTranslatorEngine);
	}

	private List<SetterReturnObject> setDrugId(String id) throws ApolloSetterException {
		return setValue(DRUG_ID, id, SECTION);
	}

	private List<SetterReturnObject> setDrugTreatmentEfficacies(List<DrugTreatmentEfficacyForSimulatorConfiguration> efficacies) throws ApolloSetterException {

		List<SetterReturnObject> sroList;
		if (efficacies != null && efficacies.size() > 0) {

			sroList = setValue(DRUG_TREATMENT_EFFICACY, "(list values described below)", section);
			ListSetter setter = new ListSetter(DrugTreatmentEfficacyForSimulatorConfigurationSetter.class, DrugTreatmentEfficacyForSimulatorConfiguration.class,
					efficacies, apolloTranslationEngine, section, type + "." + DRUG_TREATMENT_EFFICACY);

			List<SetterReturnObject> result = setter.set();

			sroList.get(0).setSubApolloParameters(result);
		} else {
			sroList = setValue(DRUG_TREATMENT_EFFICACY, PARAM_IS_NOT_SET_LABEL, section);
		}

		return sroList;
	}

	@Override
	public List<SetterReturnObject> set(DrugTreatment treatment) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setTreatment(treatment));
		list.addAll(setDrugId(treatment.getDrugId()));
		list.addAll(setDrugTreatmentEfficacies(treatment.getDrugTreatmentEfficacy()));
		return list;
	}

}

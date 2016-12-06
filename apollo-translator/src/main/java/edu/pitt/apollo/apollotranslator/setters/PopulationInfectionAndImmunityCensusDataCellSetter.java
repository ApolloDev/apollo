package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.AgeRangeCategoryDefinition;
import edu.pitt.apollo.types.v4_0_1.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v4_0_1.GenderEnum;
import edu.pitt.apollo.types.v4_0_1.InfectionStateEnum;
import edu.pitt.apollo.types.v4_0_1.PopulationInfectionAndImmunityCensusDataCell;
import java.util.ArrayList;
import java.util.List;

public class PopulationInfectionAndImmunityCensusDataCellSetter extends
		AbstractTypedSetter<PopulationInfectionAndImmunityCensusDataCell> {

	public static final String AGE_RANGE_FIELD = "ageRange";
	public static final String GENDER_FIELD = "gender";
	public static final String INFECTION_STATE_FIELD = "infectionState";
	public static final String DISEASE_STATE_FIELD = "diseaseState";
	public static final String FRACTION_IN_INFECTED_STATE_FIELD = "fractionInState";

	public PopulationInfectionAndImmunityCensusDataCellSetter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PopulationInfectionAndImmunityCensusDataCellSetter(String type,
			String section, ApolloTranslationEngine apolloTranslationEngine) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setInfectionState(InfectionStateEnum state)
			throws ApolloSetterException {
		InfectionStateSetter setter = new InfectionStateSetter(
				apolloTranslationEngine, type + "." + INFECTION_STATE_FIELD, section);
		return setter.set(state);
	}

	private List<SetterReturnObject> setDiseaseState(DiseaseOutcomeEnum state)
			throws ApolloSetterException {
		DiseaseOutcomeSetter setter = new DiseaseOutcomeSetter(
				apolloTranslationEngine, type + "." + DISEASE_STATE_FIELD, section);
		return setter.set(state);
	}

	private List<SetterReturnObject> setFractionInState(double fraction)
			throws ApolloSetterException {
		return setValue(FRACTION_IN_INFECTED_STATE_FIELD,
				Double.toString(fraction), section);
	}

	private List<SetterReturnObject> setAgeRange(AgeRangeCategoryDefinition range)
			throws ApolloSetterException {
		AgeRangeCategoryDefinitionSetter setter = new AgeRangeCategoryDefinitionSetter(apolloTranslationEngine,
				type + "." + AGE_RANGE_FIELD, section);
		return setter.set(range);
	}

	private List<SetterReturnObject> setGender(GenderEnum gender)
			throws ApolloSetterException {
		GenderCategorySetter setter = new GenderCategorySetter(apolloTranslationEngine,
				type + "." + GENDER_FIELD, section);
		return setter.set(gender);
	}

	// private List<SetterReturnObject>
	// setPopulationStrataDefinition(PopulationTreatmentCensusDataCell
	// populationStrataDefinition) throws ApolloSetterException {
	//
	// PopulationStrataDefinitionSetter populationStrataDefinitionSetter = new
	// PopulationStrataDefinitionSetter(apolloTranslationEngine,
	// type + "." + DESCRIPTION_FIELD, section);
	// return populationStrataDefinitionSetter.set(populationStrataDefinition);
	// }
	//
	// private List<SetterReturnObject> setValues(List<Double> values) throws
	// ApolloSetterException {
	// List<SetterReturnObject> list = setValue(VALUES_FIELD,
	// "(list values described below)", section);
	// ListSetter setter = new ListSetter(DoubleSetter.class, Double.class,
	// values, apolloTranslationEngine,
	// section, type + "." + VALUES_FIELD);
	//
	// // DoubleSetter doubleSetter = new DoubleSetter(apolloTranslationEngine,
	// type + "." + VALUES_FIELD, section);
	// list.get(0).setSubApolloParameters(setter.set());
	// return list;
	// }
	//
	// private List<SetterReturnObject> setPopulationStrataArray() throws
	// ApolloSetterException {
	// return setValue("", "(described below)", section);
	// }
	@Override
	public List<SetterReturnObject> set(
			PopulationInfectionAndImmunityCensusDataCell dataCell)
			throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<>();
		if (dataCell.getAgeRange() != null) {
			list.addAll(setAgeRange(dataCell.getAgeRange()));
		}
		if (dataCell.getSex()!= null) {
			list.addAll(setGender(dataCell.getSex()));
		}
		if (dataCell.getInfectionState() != null) {
			list.addAll(setInfectionState(dataCell.getInfectionState()));
		} else if (dataCell.getDiseaseState() != null) {
			list.addAll(setDiseaseState(dataCell.getDiseaseState()));
		}
		list.addAll(setFractionInState(dataCell
				.getFractionInState()));
		return list;

	}
}

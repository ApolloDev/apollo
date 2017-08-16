package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 2, 2014 Time: 10:35:08 AM Class: InfectiousDiseaseScenarioSetter IDE: NetBeans 6.9.1
 */
public class InfectiousDiseaseScenarioSetter extends AbstractTypedSetter<InfectiousDiseaseScenario> {

	public static final String SECTION = "INFECTIOUS DISEASE SCENARIO";
	public static final String INFECTIONS_SECTION = "INFECTIONS";
	public static final String POPULATION_SECTION = "POPULATION";
	public static final String ABIOTIC_THING_CENSUSES_SECTION = "ABIOTIC THING CENSUSES";
	public static final String POPULATION_FIELD = "populations";
	public static final String ABIOTIC_THING_CENSUSES_FIELD = "abioticThingCensuses";
	public static final String SCENARIO_DATE_FIELD = "scenarioDate";
	public static final String LOCATION_FIELD = "scenarioLocation";
	public static final String INFECTIONS_FIELD = "infections";
	public static final String DISEASES_FIELD = "diseases";
	public static final String INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD = "infectiousDiseaseControlStrategies";
	public static final String NON_APOLLO_PARAMETERS = "nonApolloParameters";
	public static final String INDIVIDUAL_BEHAVIORS = "behaviors";
	public static final String INDIVIDUAL_REPRODUCTIONS = "reproductions";
	public static final String INDIVIDUAL_LIFE_CYCLES = "lifeCycles";

	public InfectiousDiseaseScenarioSetter(ApolloTranslationEngine apolloTranslationEngine) {
		super("infectiousDiseaseScenario", SECTION, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setLocation(Location location) throws ApolloSetterException {
		// the type and section should be made final variables, possibly move all sections and type declarations to this class
		LocationSetter setter = new LocationSetter(apolloTranslationEngine, type + "." + LOCATION_FIELD, "LOCATION");
		return setter.set(location);
	}

	private List<SetterReturnObject> setPopulations(
			List<Population> censuses)
			throws ApolloSetterException {
		List<SetterReturnObject> list = setValue(POPULATION_FIELD, "(list values described below)", POPULATION_SECTION);
		ListSetter setter = new ListSetter(PopulationSetter.class,
				Population.class, censuses, apolloTranslationEngine,
				POPULATION_SECTION, type + "." + POPULATION_FIELD);

		list.get(0).setSubApolloParameters(setter.set());
		return list;
	}

	private List<SetterReturnObject> setAbioticThingCensus(List<AbioticThingCensus> censuses) throws ApolloSetterException {
		List<SetterReturnObject> list = setValue(ABIOTIC_THING_CENSUSES_FIELD, "(list values described below)", ABIOTIC_THING_CENSUSES_SECTION);
		ListSetter setter = new ListSetter(CensusSetter.class,
				Census.class, censuses, apolloTranslationEngine,
				ABIOTIC_THING_CENSUSES_SECTION, type + "." + ABIOTIC_THING_CENSUSES_FIELD);

		list.get(0).setSubApolloParameters(setter.set());
		return list;
	}

	private List<SetterReturnObject> setControlStrategies(List<InfectiousDiseaseControlMeasure> controlStrategies) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setIndividualTreatmentControlStrategies(controlStrategies));
		list.addAll(setPlaceClosureControlStrategies(controlStrategies));
		list.addAll(setCaseQuarantineControlStrategies(controlStrategies));
		list.addAll(setVectorControlStrategies(controlStrategies));
		list.addAll(setVoluntaryHouseholdQuarantineControlStrategies(controlStrategies));
		list.addAll(setLiberalSickLeaveControlStrategies(controlStrategies));

		return list;
	}

	private List<SetterReturnObject> setIndividualTreatmentControlStrategies(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
		ListSetter setter = new ListSetter(IndividualTreatmentControlMeasureSetter.class, IndividualTreatmentControlMeasure.class,
				cmList, apolloTranslationEngine, "", type + "." + INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD);

		return setter.set();
	}

	private List<SetterReturnObject> setVectorControlStrategies(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
		ListSetter setter = new ListSetter(VectorControlMeasureSetter.class, VectorControlMeasure.class,
				cmList, apolloTranslationEngine, "", type + "." + INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD);

		return setter.set();
	}

	private List<SetterReturnObject> setLiberalSickLeaveControlStrategies(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
		ListSetter setter = new ListSetter(LiberalSickLeaveControlMeasureSetter.class, LiberalSickLeaveControlMeasure.class,
				cmList, apolloTranslationEngine, "", type + "." + INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD);

		return setter.set();
	}

	private List<SetterReturnObject> setVoluntaryHouseholdQuarantineControlStrategies(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
		ListSetter setter = new ListSetter(VoluntaryHouseholdQuarantineControlMeasureSetter.class, VoluntaryHouseholdQuarantineControlMeasure.class,
				cmList, apolloTranslationEngine, "", type + "." + INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD);

		return setter.set();
	}

	private List<SetterReturnObject> setCaseQuarantineControlStrategies(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
		ListSetter setter = new ListSetter(CaseQuarantineControlMeasureSetter.class, CaseQuarantineControlMeasure.class,
				cmList, apolloTranslationEngine, CaseQuarantineControlMeasureSetter.SECTION, type + "." + INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD);
		return setter.set();
	}

	private List<SetterReturnObject> setPlaceClosureControlStrategies(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
		ListSetter setter = new ListSetter(PlaceClosureControlMeasureSetter.class, PlaceClosureControlMeasure.class,
				cmList, apolloTranslationEngine, PlaceClosureControlMeasureSetter.SECTION, type + "." + INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD);
		return setter.set();
	}

	//    private List<SetterReturnObject> setNamedControlMeasures(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
////        NamedControlMeasureSetter ncms = new NamedControlMeasureSetter(apolloTranslationEngine);
//        ListSetter setter = new ListSetter(NamedControlMeasureSetter.class, InfectiousDiseaseControlMeasure.class, cmList, apolloTranslationEngine,
//                NamedControlMeasureSetter.SECTION, type + "." + INFECTIOUS_DISEASE_CONTROL_STRATEGIES_FIELD);
//
//        return setter.set();
//    }
	private List<SetterReturnObject> setNonApolloParameters(List<NonApolloParameter> params) throws ApolloSetterException {
		ListSetter setter = new ListSetter(NonApolloParameterSetter.class, NonApolloParameter.class, params, apolloTranslationEngine,
				NonApolloParameterSetter.SECTION, type + "." + NON_APOLLO_PARAMETERS);

		return setter.set();
	}

	//    private List<SetterReturnObject> setDiseases(List<InfectiousDisease> diseases)
//            throws ApolloSetterException {
//        ListSetter setter = new ListSetter(InfectiousDiseaseSetter.class, InfectiousDisease.class, diseases, apolloTranslationEngine,
//                InfectiousDiseaseSetter.SECTION, type + "." + DISEASES_FIELD);
//
//        return setter.set();
//    }
	private List<SetterReturnObject> setInfections(List<Infection> infections) throws ApolloSetterException {
		List<SetterReturnObject> list = setValue(INFECTIONS_FIELD, "(list values described below)", INFECTIONS_SECTION);

		ListSetter setter = new ListSetter(InfectionSetter.class, Infection.class, infections, apolloTranslationEngine,
				INFECTIONS_SECTION, type + "." + INFECTIONS_FIELD);

		list.addAll(setter.set());
		return list;
	}

	private List<SetterReturnObject> setScenarioDate(XMLGregorianCalendar date) throws ApolloSetterException {
		if (date == null) {
			throw new ApolloSetterException(SCENARIO_DATE_FIELD + " is not set.");
		}
		return setValue(SCENARIO_DATE_FIELD, date.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(InfectiousDiseaseScenario t) throws ApolloSetterException {
		// this determines the order of the sections in the output
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

		list.addAll(setScenarioDate(t.getScenarioDate()));
		list.addAll(setLocation(t.getScenarioLocation()));
		if (t.getAbioticThingCensuses() != null && t.getAbioticThingCensuses().size() > 0) {
			list.addAll(setAbioticThingCensus(t.getAbioticThingCensuses()));
		}
		list.addAll(setPopulations(t.getPopulations()));
		list.addAll(setInfections(t.getInfections()));
		list.addAll(setControlStrategies(t.getInfectiousDiseaseControlStrategies()));
		list.addAll(setNonApolloParameters(t.getNonApolloParameters()));

		return list;
	}
}

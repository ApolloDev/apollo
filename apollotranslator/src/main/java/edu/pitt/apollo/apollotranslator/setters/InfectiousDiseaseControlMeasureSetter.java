package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.*;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 1, 2014 Time: 3:41:26 PM Class: InfectiousDiseaseControlMeasureSetter IDE: NetBeans 6.9.1
 */
public abstract class InfectiousDiseaseControlMeasureSetter<T extends InfectiousDiseaseControlMeasure>
		extends AbstractTypedSetter<T> {

	// InfectiousDiseaseControlMeasure is the base class for control strategies
	// Note that IndividualTreatmentControlMeasure extends it, and contains the
	// schedules
	public static final String DESCRIPTION_FIELD = "description";
	public static final String CONTROL_STRATEGY_START_TIME_FIELD = "controlMeasureStartTime";
	public static final String CONTROL_STRATEGY_RESPONSE_DELAY_FIELD = "controlMeasureResponseDelay";
	public static final String LOCATION = "location";
	public static final String CONTROL_STRATEGY_STOP_TIME = "controlMeasureStopTime";
	public static final String CONTROL_STRATEGY_STAND_DOWN_DELAY = "controlMeasureStandDownDelay";
	public static final String LOGISTICAL_SYSTEMS = "logisticalSystems";
	public static final String TEMPLATED_INFECTIOUS_DISEASE_CONTROL_MEASURE_URLS = "templatedInfectiousDiseaseControlMeasureUrlsForSoftware";

	public InfectiousDiseaseControlMeasureSetter() {
	}

	public InfectiousDiseaseControlMeasureSetter(
			ApolloTranslationEngine apolloTranslationEngine, String prefix,
			String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setDescription(String description)
			throws ApolloSetterException {
		return setValue(DESCRIPTION_FIELD, description, section);
	}

	private List<SetterReturnObject> setLocation(Location location) throws ApolloSetterException {
		LocationSetter setter = new LocationSetter(apolloTranslationEngine, type + "." + LOCATION, section);
		return setter.set(location);
	}

	private List<SetterReturnObject> setControlMeasureStartTime(List<TriggerDefinition> triggers) throws ApolloSetterException {
		List<SetterReturnObject> sroList;
		if (triggers != null && triggers.size() > 0) {

			sroList = setValue(CONTROL_STRATEGY_START_TIME_FIELD, "(list values described below)", section);
			ListSetter setter = new ListSetter(TriggerDefinitionSetter.class, TriggerDefinition.class,
					triggers, apolloTranslationEngine, section, type + "." + CONTROL_STRATEGY_START_TIME_FIELD);

			List<SetterReturnObject> result = setter.set();

			sroList.get(0).setSubApolloParameters(result);
		} else {
			sroList = setValue(CONTROL_STRATEGY_START_TIME_FIELD, PARAM_IS_NOT_SET_LABEL, section);
		}

		return sroList;
	}

	private List<SetterReturnObject> setControlMeasureStopTime(List<TriggerDefinition> triggers) throws ApolloSetterException {
		List<SetterReturnObject> sroList;
		if (triggers != null && triggers.size() > 0) {

			sroList = setValue(CONTROL_STRATEGY_STOP_TIME, "(list values described below)", section);
			ListSetter setter = new ListSetter(TriggerDefinitionSetter.class, TriggerDefinition.class,
					triggers, apolloTranslationEngine, section, type + "." + CONTROL_STRATEGY_STOP_TIME);

			List<SetterReturnObject> result = setter.set();

			sroList.get(0).setSubApolloParameters(result);
		} else {
			sroList = setValue(CONTROL_STRATEGY_STOP_TIME, PARAM_IS_NOT_SET_LABEL, section);
		}

		return sroList;
	}

	private List<SetterReturnObject> setControlMeasureStandDownDelay(Duration duration) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + CONTROL_STRATEGY_STAND_DOWN_DELAY, section);
		return setter.set(duration);
	}

	private List<SetterReturnObject> setControlMeasureResponseDelay(Duration duration) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + CONTROL_STRATEGY_RESPONSE_DELAY_FIELD, section);
		return setter.set(duration);
	}

	private List<SetterReturnObject> setLogisticalSystems(List<LogisticalSystem> logisticalSystems) throws ApolloSetterException {
		ListSetter setter = new ListSetter(LogisticalSystemSetter.class, LogisticalSystem.class, logisticalSystems,
				apolloTranslationEngine, section, type + "." + LOGISTICAL_SYSTEMS);
		return setter.set();
	}

	private List<SetterReturnObject> setTemplatedInfectiousDiseaseControlMeasureURLs(List<TemplatedInfectiousDiseaseControlMeasureUrlForSoftware> list) throws ApolloSetterException {
		List<SetterReturnObject> sroList;
		if (list != null && list.size() > 0) {

			sroList = setValue(TEMPLATED_INFECTIOUS_DISEASE_CONTROL_MEASURE_URLS, "(list values described below)", section);
			ListSetter setter = new ListSetter(TemplatedInfectiousDiseaseControlMeasureUrlForSoftwareSetter.class, 
					TemplatedInfectiousDiseaseControlMeasureUrlForSoftware.class,
					list, apolloTranslationEngine, section, type + "." + TEMPLATED_INFECTIOUS_DISEASE_CONTROL_MEASURE_URLS);

			List<SetterReturnObject> result = setter.set();

			sroList.get(0).setSubApolloParameters(result);
		} else {
			sroList = setValue(TEMPLATED_INFECTIOUS_DISEASE_CONTROL_MEASURE_URLS, PARAM_IS_NOT_SET_LABEL, section);
		}

		return sroList;
	}

	public List<SetterReturnObject> setInfectiousDiseaseControlMeasure() throws ApolloSetterException {
		return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
	}

	public List<SetterReturnObject> setInfectiousDiseaseControlMeasure(
			InfectiousDiseaseControlMeasure t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setInfectiousDiseaseControlMeasure());
		list.addAll(setDescription(t.getDescription()));
		if (t.getLocation() != null) {
			list.addAll(setLocation(t.getLocation()));
		}
		list.addAll(setControlMeasureStartTime(t.getControlMeasureStartTime()));
		list.addAll(setControlMeasureStopTime(t.getControlMeasureStopTime()));
		list.addAll(setControlMeasureResponseDelay(t.getControlMeasureResponseDelay()));
		list.addAll(setControlMeasureStandDownDelay(t.getControlMeasureStandDownDelay()));
		if (t.getLogisticalSystems() != null && t.getLogisticalSystems().size() > 0) {
			list.addAll(setLogisticalSystems(t.getLogisticalSystems()));
		}
		if (t.getTemplatedInfectiousDiseaseControlMeasureUrlsForSoftware() != null
				&& t.getTemplatedInfectiousDiseaseControlMeasureUrlsForSoftware().size() > 0) {
			list.addAll(setTemplatedInfectiousDiseaseControlMeasureURLs(t.getTemplatedInfectiousDiseaseControlMeasureUrlsForSoftware()));
		}
		return list;
	}
}

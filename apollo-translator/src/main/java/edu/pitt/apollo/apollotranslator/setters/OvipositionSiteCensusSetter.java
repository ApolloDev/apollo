package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.OvipositionSiteCensus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 4, 2014
 Time: 5:49:20 PM
 Class: OvipositionSiteCensusSetter
 */
public class OvipositionSiteCensusSetter extends AbstractTypedSetter<OvipositionSiteCensus> {

	private static final String BRETEAU_INDEX = "breteauIndex";
	private static final String CARRYING_CAPACITY_PER_HOUSE = "carryingCapacityPerHouse";
	private static final String HETEROGENOUS_CARRYING_CAPACITY = "heterogenousCarryingCapacity";
	private static final String RATIO_OF_OUTDOOR_TO_INDOOR_OVISITES = "ratioOfOutdoorToIndoorOvisites";
	private static final String MAXIMUM_RATIO_OF_OUTDOOR_CARRYING_CAPACITY_TO_INDOOR = "maximumRatioOfOutdoorCarryingCapacityToIndoor";

	public OvipositionSiteCensusSetter() {

	}

	public OvipositionSiteCensusSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setBreteauIndex(BigInteger value) throws ApolloSetterException {
		return setValue(BRETEAU_INDEX, value.toString(), section);
	}

	private List<SetterReturnObject> setCarryingCapacityPerHouse(Double value) throws ApolloSetterException {
		return setValue(CARRYING_CAPACITY_PER_HOUSE, value.toString(), section);
	}

	private List<SetterReturnObject> setHeterogeneousCarryingCapacity(Boolean value) throws ApolloSetterException {
		return setValue(HETEROGENOUS_CARRYING_CAPACITY, value.toString(), section);
	}

	private List<SetterReturnObject> setRatioOfOutdoorToIndoorOvisites(Double value) throws ApolloSetterException {
		return setValue(RATIO_OF_OUTDOOR_TO_INDOOR_OVISITES, value.toString(), section);
	}

	private List<SetterReturnObject> setMaximumRatioOfOutdoorCarryingCapacityToIndoor(Double value) throws ApolloSetterException {
		return setValue(MAXIMUM_RATIO_OF_OUTDOOR_CARRYING_CAPACITY_TO_INDOOR, value.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(OvipositionSiteCensus t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		if (t.getBreteauIndex() != null) {
			list.addAll(setBreteauIndex(t.getBreteauIndex()));
		}
		if (t.getCarryingCapacityPerHouse() != null) {
			list.addAll(setCarryingCapacityPerHouse(t.getCarryingCapacityPerHouse()));
		}
		if (t.getRatioOfOutdoorToIndoorOvisites() != null) {
			list.addAll(setRatioOfOutdoorToIndoorOvisites(t.getRatioOfOutdoorToIndoorOvisites()));
		}
		if (t.getMaximumRatioOfOutdoorCarryingCapacityToIndoor() != null) {
			list.addAll(setMaximumRatioOfOutdoorCarryingCapacityToIndoor(t.getMaximumRatioOfOutdoorCarryingCapacityToIndoor()));
		}
		if (t.isHeterogenousCarryingCapacity() != null) {
			list.addAll(setHeterogeneousCarryingCapacity(t.isHeterogenousCarryingCapacity()));
		}

		return list;
	}

}

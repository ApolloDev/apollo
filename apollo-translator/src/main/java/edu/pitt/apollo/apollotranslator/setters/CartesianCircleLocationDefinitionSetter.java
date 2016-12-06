package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.CartesianCircleLocationDefinition;
import edu.pitt.apollo.types.v4_0_1.Distance;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 8, 2014
 Time: 11:12:44 AM
 Class: CartesianCircleLocationDefinitionSetter
 */
public class CartesianCircleLocationDefinitionSetter extends AbstractTypedSetter<CartesianCircleLocationDefinition> {

	private static final String EAST_WEST_OFFSET_FROM_CARTESIAN_CENTER = "eastWestOffsetFromCartesianCenter";
	private static final String NORTH_SOUTH_OFFSET_FROM_CARTESIAN_CENTER = "northSouthOffsetFromCartesianCenter";
	private static final String ALTITUDE_RELATIVE_TO_CARTESIAN_CENTER = "altitudeRelativeToCartesianCenter";
	private static final String RADIUS = "radius";

	public CartesianCircleLocationDefinitionSetter() {

	}

	public CartesianCircleLocationDefinitionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setEastWestOffsetFromCartesianCenter(Distance offset) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + EAST_WEST_OFFSET_FROM_CARTESIAN_CENTER, section);
		return setter.set(offset);
	}

	private List<SetterReturnObject> setNorthSouthOffsetFromCartesianCenter(Distance offset) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + NORTH_SOUTH_OFFSET_FROM_CARTESIAN_CENTER, section);
		return setter.set(offset);
	}

	private List<SetterReturnObject> setAltitudeRelativeCartesianCenter(Distance altitude) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + ALTITUDE_RELATIVE_TO_CARTESIAN_CENTER, section);
		return setter.set(altitude);
	}

	private List<SetterReturnObject> setRadius(Distance radius) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + RADIUS, section);
		return setter.set(radius);
	}

	@Override
	public List<SetterReturnObject> set(CartesianCircleLocationDefinition t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		
		list.addAll(setEastWestOffsetFromCartesianCenter(t.getEastWestOffsetFromCartesianCenter()));
		list.addAll(setNorthSouthOffsetFromCartesianCenter(t.getNorthSouthOffsetFromCartesianCenter()));
		list.addAll(setAltitudeRelativeCartesianCenter(t.getAltitudeRelativeToCartesianCenter()));
		list.addAll(setRadius(t.getRadius()));
		
		return list;
	}

}

package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.CartesianCircleLocationDefinition;
import edu.pitt.apollo.types.v4_0_1.Location;
import edu.pitt.apollo.types.v4_0_1.LocationDefinition;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 5, 2014
 * Time: 4:47:53 PM
 * Class: LocationSetter
 * IDE: NetBeans 6.9.1
 */
public class LocationSetter extends AbstractTypedSetter<Location> {

	private static final String LOCATION_CODE_FIELD = "locationCode";
	private static final String LOCATION_DEFINITION_FIELD = "locationDefinition";
	private static final String CARTESIAN_CIRCLE_LOCATION_DEFINITION = "cartesianCircleLocationDefinition";
	private static final String TEXTUAL_DESCRIPTION = "textualDescription";

	public LocationSetter() {
	}

	public LocationSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	public List<SetterReturnObject> setLocation() throws ApolloSetterException {
		return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
	}

	private List<SetterReturnObject> setLocationCode(String code) throws ApolloSetterException {
		return setValue(LOCATION_CODE_FIELD, code, section);
	}

	private List<SetterReturnObject> setLocationDefinition(LocationDefinition definition) throws ApolloSetterException {
		LocationDefinitionSetter setter = new LocationDefinitionSetter(type + "." + LOCATION_DEFINITION_FIELD, section, apolloTranslationEngine);
		return setter.set(definition);
	}
	
	private List<SetterReturnObject> setCartesianCircleLocationDefinition(CartesianCircleLocationDefinition definition) throws ApolloSetterException {
		CartesianCircleLocationDefinitionSetter setter = new CartesianCircleLocationDefinitionSetter(apolloTranslationEngine, 
				type + "." + CARTESIAN_CIRCLE_LOCATION_DEFINITION, section);
		return setter.set(definition);
	}
	
	private List<SetterReturnObject> setTextualDescription(String description) throws ApolloSetterException {
		return setValue(TEXTUAL_DESCRIPTION, description, section);
	}

	@Override
	public List<SetterReturnObject> set(Location t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setLocation());
		if (t.getApolloLocationCode() != null) {
			list.addAll(setLocationCode(t.getApolloLocationCode()));
		} else if (t.getLocationDefinition() != null) {
			list.addAll(setLocationDefinition(t.getLocationDefinition()));
		} else if (t.getCartesianCircleLocationDefinition() != null) {
			list.addAll(setCartesianCircleLocationDefinition(t.getCartesianCircleLocationDefinition()));
		}
		list.addAll(setTextualDescription(t.getTextualDescription()));

		return list;
	}
}

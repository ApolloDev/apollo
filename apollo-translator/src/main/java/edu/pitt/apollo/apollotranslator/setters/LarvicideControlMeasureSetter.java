package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.Distance;
import edu.pitt.apollo.types.v4_0_1.LarvicideControlMeasure;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 5, 2014
 Time: 6:21:01 PM
 Class: LarvicideControlMeasureSetter
 */
public class LarvicideControlMeasureSetter extends AbstractTypedSetter<LarvicideControlMeasure> {

	public static final String SECTION = "LARVICIDE CONTROL STRATEGY";
	private static final String COVER_RADIUS = "coverRadius";

	public LarvicideControlMeasureSetter() {

	}

	public LarvicideControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setCoverRadius(Distance radius) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + COVER_RADIUS, section);
		return setter.set(radius);
	}

	@Override
	public List<SetterReturnObject> set(LarvicideControlMeasure t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setCoverRadius(t.getCoverRadius()));

		return list;

	}

}

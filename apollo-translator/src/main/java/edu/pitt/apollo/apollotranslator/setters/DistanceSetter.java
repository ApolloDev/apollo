package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.Distance;
import edu.pitt.apollo.types.v4_0_1.UnitOfDistanceEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 2, 2014
 * Time: 4:21:43 PM
 * Class: DistanceSetter
 */
public class DistanceSetter extends AbstractTypedSetter<Distance> {

	private static final String UNIT_OF_DISTANCE = "unitOfDistance";
	private static final String VALUE = "value";

	public DistanceSetter() {

	}

	public DistanceSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setUnitOfDistance(UnitOfDistanceEnum unit) throws ApolloSetterException {
		return setValue(UNIT_OF_DISTANCE, unit.toString(), section);
	}

	private List<SetterReturnObject> setValueField(double value) throws ApolloSetterException {
		return setValue(VALUE, Double.toString(value), section);
	}

	@Override
	public List<SetterReturnObject> set(Distance t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

		list.addAll(setUnitOfDistance(t.getUnitOfDistance()));
		list.addAll(setValueField(t.getValue()));

		return list;
	}

}

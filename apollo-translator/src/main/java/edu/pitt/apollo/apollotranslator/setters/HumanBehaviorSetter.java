package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.HumanBehavior;
import edu.pitt.apollo.types.v4_0_2.Rate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 2, 2014
 * Time: 1:57:56 PM
 * Class: HumanBehaviorSetter
 */
public class HumanBehaviorSetter extends AbstractTypedSetter<HumanBehavior> {

	private static final String SPEED_OF_MOVEMENT = "speedOfMovement";
	private static final String BUILDING_AFFINITY = "buildingAffinity";

	public HumanBehaviorSetter() {

	}

	public HumanBehaviorSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	public List<SetterReturnObject> setSpeedOfMovement(Rate rate) throws ApolloSetterException {
		RateSetter setter = new RateSetter(apolloTranslationEngine, type + "." + SPEED_OF_MOVEMENT, section);
		return setter.set(rate);
	}

	public List<SetterReturnObject> setBuildingAffinity(double value) throws ApolloSetterException {
		return setValue(BUILDING_AFFINITY, Double.toString(value), section);
	}

	@Override
	public List<SetterReturnObject> set(HumanBehavior behavior) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();

		if (behavior.getSpeedOfMovement() != null) {
			list.addAll(setSpeedOfMovement(behavior.getSpeedOfMovement()));
		}
		if (behavior.getBuildingAffinity() != null) {
			list.addAll(setBuildingAffinity(behavior.getBuildingAffinity()));
		}
		
		return list;
	}

}

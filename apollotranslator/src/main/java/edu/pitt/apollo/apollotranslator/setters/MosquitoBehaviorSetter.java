package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 2, 2014
 Time: 4:16:39 PM
 Class: MosquitoBehaviorSetter
 */
public class MosquitoBehaviorSetter extends AbstractTypedSetter<MosquitoBehavior> {

	private static final String BITE_PROBABILITY = "biteProbability";
	private static final String BITE_RADIUS = "biteRadius";
	private static final String TIME_BETWEEN_BITES = "timeBetweenBites";
	private static final String MIGRATION_SPEED = "migrationSpeed";
	private static final String SHADE_AFFINITY = "shadeAffinity";
	private static final String MALE_MATING_RADIUS = "maleMatingRadius";
	private static final String MATING_PROBABILITY = "matingProbability";
	private static final String STRAIGHT_TRAVEL_AFTER_OVIPOSIT = "straightTravelAfterOviposit";

	public MosquitoBehaviorSetter() {

	}

	public MosquitoBehaviorSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setBiteProbability(ProbabilisticParameter biteProbability) throws ApolloSetterException {
		ProbabilisticParameterSetter setter = new ProbabilisticParameterSetter(apolloTranslationEngine, type + "." + BITE_PROBABILITY, section);
		return setter.set(biteProbability);
	}

	private List<SetterReturnObject> setBiteRadius(Distance radius) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + BITE_RADIUS, section);
		return setter.set(radius);
	}

	private List<SetterReturnObject> setTimeBetweenBites(Duration time) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + TIME_BETWEEN_BITES, section);
		return setter.set(time);
	}

	private List<SetterReturnObject> setMigrationSpeed(Rate speed) throws ApolloSetterException {
		RateSetter setter = new RateSetter(apolloTranslationEngine, type + "." + MIGRATION_SPEED, section);
		return setter.set(speed);
	}

	private List<SetterReturnObject> setShadeAffinity(Double fraction) throws ApolloSetterException {
		return setValue(SHADE_AFFINITY, fraction.toString(), section);
	}

	private List<SetterReturnObject> setMaleMatingRadius(Distance radius) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + MALE_MATING_RADIUS, section);
		return setter.set(radius);
	}

	private List<SetterReturnObject> setMatingProbability(Double probability) throws ApolloSetterException {
		return setValue(MATING_PROBABILITY, probability.toString(), section);
	}

	private List<SetterReturnObject> setStraightTravelAfterOviposit(Distance distance) throws ApolloSetterException {
		DistanceSetter setter = new DistanceSetter(apolloTranslationEngine, type + "." + STRAIGHT_TRAVEL_AFTER_OVIPOSIT, section);
		return setter.set(distance);
	}

	@Override
	public List<SetterReturnObject> set(MosquitoBehavior t) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		if (t.getBiteProbability() != null) {
			list.addAll(setBiteProbability(t.getBiteProbability()));
		}
		if (t.getBiteRadius() != null) {
			list.addAll(setBiteRadius(t.getBiteRadius()));
		}
		if (t.getTimeBetweenBites() != null) {
			list.addAll(setTimeBetweenBites(t.getTimeBetweenBites()));
		}
		if (t.getMigrationSpeed() != null) {
			list.addAll(setMigrationSpeed(t.getMigrationSpeed()));
		}
		if (t.getShadeAffinity() != null) {
			list.addAll(setShadeAffinity(t.getShadeAffinity()));
		}
		if (t.getMaleMatingRadius() != null) {
			list.addAll(setMaleMatingRadius(t.getMaleMatingRadius()));
		}
		if (t.getMatingProbability() != null) {
			list.addAll(setMatingProbability(t.getMatingProbability()));
		}
		if (t.getStraightTravelAfterOviposit() != null) {
			list.addAll(setStraightTravelAfterOviposit(t.getStraightTravelAfterOviposit()));
		}

		return list;
	}

}

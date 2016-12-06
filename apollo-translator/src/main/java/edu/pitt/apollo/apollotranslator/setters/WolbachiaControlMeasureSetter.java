package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.Duration;
import edu.pitt.apollo.types.v4_0_1.WolbachiaControlMeasure;
import edu.pitt.apollo.types.v4_0_1.WolbachiaReleaseSiteEnum;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 5:17:06 PM
 * Class: WolbachiaControlMeasureSetter
 */
public class WolbachiaControlMeasureSetter extends AbstractTypedSetter<WolbachiaControlMeasure> {

	public static final String SECTION = "WOLBACHIA CONTROL STRATEGY";
	private static final String WOLBACHIA_SITES_EVERY_NTH = "wolbachiaSitesEveryNth";
	private static final String WOLBACHIA_SEED_ADULTS_PER_HOUSE = "wolbachiaSeedAdultsPerHouse";
	private static final String CLEAR_WOLBACHIA_ON_MIGRATION = "clearWolbachiaOnMigration";
	private static final String WOLBACHIA_RELEASE_INTERVAL = "wolbachiaReleaseInterval";
	private static final String WOLBACHIA_RELEASE_SITES = "wolbachiaReleaseSites";
	private static final String WOLBACHIA_EFFECT_ON_EGG_MORTALITY_RATE = "wolbachiaEffectOnEggMortalityRate";
	private static final String WOLBACHIA_EFFECT_ON_ADULT_MORTALITY_RATE = "wolbachiaEffectOnAdultMortalityRate";
	private static final String WOLBACHIA_EFFECT_ON_LARVAL_MORTALITY_RATE = "wolbachiaEffectOnLarvalMortalityRate";
	private static final String WOLBACHIA_EFFECT_ON_FECUNDITY = "wolbachiaEffectOnFecundity";
	private static final String WOLBACHIA_EFFECT_ON_MATING_PROBABILITY = "wolbachiaEffectOnMatingProbability";
	private static final String WOLBACHIA_EFFECT_ON_LEAKAGE_RATE = "wolbachiaEffectOnLeakageRate";
	private static final String WOLBACHIA_EFFECT_ON_VECTORIAL_CAPACITY = "wolbachiaEffectOnVectorialCapacity";
	private static final String WOLBACHIA_PRE_RELEASE_ADULT_SUPPRESSION_EFFICACY = "wolbachiaPreReleaseAdultSuppressionEfficacy";
	private static final String WOLBACHIA_PRE_RELEASE_LARVAL_SUPPRESSION_EFFICACY = "wolbachiaPreReleaseLarvalSuppressionEfficacy";

	public WolbachiaControlMeasureSetter() {

	}

	public WolbachiaControlMeasureSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setWolbachiaSitesEveryNth(BigInteger sites) throws ApolloSetterException {
		return setValue(WOLBACHIA_SITES_EVERY_NTH, sites.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaSeedAdultsPerHouse(BigInteger number) throws ApolloSetterException {
		return setValue(WOLBACHIA_SEED_ADULTS_PER_HOUSE, number.toString(), section);
	}

	private List<SetterReturnObject> setClearWolbachiaOnMigration(Boolean value) throws ApolloSetterException {
		return setValue(CLEAR_WOLBACHIA_ON_MIGRATION, value.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaReleaseInterval(Duration interval) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + WOLBACHIA_RELEASE_INTERVAL, section);
		return setter.set(interval);
	}

	private List<SetterReturnObject> setWolbachiaReleaseSites(WolbachiaReleaseSiteEnum sites) throws ApolloSetterException {
		return setValue(WOLBACHIA_RELEASE_SITES, sites.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaEffectOnEggMortalityRate(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_EFFECT_ON_EGG_MORTALITY_RATE, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaEffectOnAdultMortalityRate(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_EFFECT_ON_ADULT_MORTALITY_RATE, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaEffectOnLarvalMortalityRate(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_EFFECT_ON_LARVAL_MORTALITY_RATE, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaEffectOnFecundity(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_EFFECT_ON_FECUNDITY, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaEffectOnMatingProbability(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_EFFECT_ON_MATING_PROBABILITY, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaEffectOnLeakagRate(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_EFFECT_ON_LEAKAGE_RATE, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaEffectOnVectorialCapacity(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_EFFECT_ON_VECTORIAL_CAPACITY, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaPreReleaseAdultSuppressionEfficacy(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_PRE_RELEASE_ADULT_SUPPRESSION_EFFICACY, fraction.toString(), section);
	}

	private List<SetterReturnObject> setWolbachiaPreReleaseLarvalSuppressionEfficacy(Double fraction) throws ApolloSetterException {
		return setValue(WOLBACHIA_PRE_RELEASE_LARVAL_SUPPRESSION_EFFICACY, fraction.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(WolbachiaControlMeasure t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		if (t.getWolbachiaSitesEveryNth() != null) {
			list.addAll(setWolbachiaSitesEveryNth(t.getWolbachiaSitesEveryNth()));
		}
		if (t.getWolbachiaEffectOnAdultMortalityRate() != null) {
			list.addAll(setWolbachiaEffectOnAdultMortalityRate(t.getWolbachiaEffectOnAdultMortalityRate()));
		}
		if (t.getWolbachiaEffectOnEggMortalityRate() != null) {
			list.addAll(setWolbachiaEffectOnEggMortalityRate(t.getWolbachiaEffectOnEggMortalityRate()));
		}
		if (t.getWolbachiaEffectOnFecundity() != null) {
			list.addAll(setWolbachiaEffectOnFecundity(t.getWolbachiaEffectOnFecundity()));
		}
		if (t.getWolbachiaEffectOnLarvalMortalityRate() != null) {
			list.addAll(setWolbachiaEffectOnLarvalMortalityRate(t.getWolbachiaEffectOnLarvalMortalityRate()));
		}
		if (t.getWolbachiaEffectOnLeakageRate() != null) {
			list.addAll(setWolbachiaEffectOnLeakagRate(t.getWolbachiaEffectOnLeakageRate()));
		}
		if (t.getWolbachiaEffectOnMatingProbability() != null) {
			list.addAll(setWolbachiaEffectOnMatingProbability(t.getWolbachiaEffectOnMatingProbability()));
		}
		if (t.getWolbachiaEffectOnVectorialCapacity() != null) {
			list.addAll(setWolbachiaEffectOnVectorialCapacity(t.getWolbachiaEffectOnVectorialCapacity()));
		}
		if (t.getWolbachiaReleaseInterval() != null) {
			list.addAll(setWolbachiaReleaseInterval(t.getWolbachiaReleaseInterval()));
		}
		if (t.getWolbachiaReleaseSites() != null) {
			list.addAll(setWolbachiaReleaseSites(t.getWolbachiaReleaseSites()));
		}
		if (t.getWolbachiaSeedAdultsPerHouse() != null) {
			list.addAll(setWolbachiaSeedAdultsPerHouse(t.getWolbachiaSeedAdultsPerHouse()));
		}
		if (t.isClearWolbachiaOnMigration() != null) {
			list.addAll(setClearWolbachiaOnMigration(t.isClearWolbachiaOnMigration()));
		}
		if (t.getWolbachiaPreReleaseAdultSuppressionEfficacy() != null) {
			list.addAll(setWolbachiaPreReleaseAdultSuppressionEfficacy(t.getWolbachiaPreReleaseAdultSuppressionEfficacy()));
		}
		if (t.getWolbachiaPreReleaseLarvalSuppressionEfficacy() != null) {
			list.addAll(setWolbachiaPreReleaseLarvalSuppressionEfficacy(t.getWolbachiaPreReleaseLarvalSuppressionEfficacy()));
		}

		return list;
	}

}

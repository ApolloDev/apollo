package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.Duration;
import edu.pitt.apollo.types.v4_0_1.MosquitoReproduction;
import edu.pitt.apollo.types.v4_0_1.Rate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 2, 2014
 * Time: 5:24:01 PM
 * Class: MosquitoReproductionSetter
 */
public class MosquitoReproductionSetter extends AbstractTypedSetter<MosquitoReproduction> {

	private static final String GONOTROPHIC_PERIOD_DURATION = "gonotrophicPeriodDuration";
	private static final String EGGS_PER_BROOD = "eggsPerBrood";
	private static final String MINIMUM_EGGS_PER_OVIPOSITION = "minimumEggsPerOviposition";
	private static final String AGE_DEPENDENT_FECUNDITY_REDUCTION = "ageDependentFecundityReduction";

	public MosquitoReproductionSetter() {

	}

	public MosquitoReproductionSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setGonotrophicPeriodDuration(Duration period) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + GONOTROPHIC_PERIOD_DURATION, section);
		return setter.set(period);
	}

	private List<SetterReturnObject> setEggsPerBrood(BigInteger eggs) throws ApolloSetterException {
		return setValue(EGGS_PER_BROOD, eggs.toString(), section);
	}

	private List<SetterReturnObject> setMinimumEggsPerOviposition(BigInteger eggs) throws ApolloSetterException {
		return setValue(MINIMUM_EGGS_PER_OVIPOSITION, eggs.toString(), section);
	}

	private List<SetterReturnObject> setAgeDependentFecundityReduction(Rate rate) throws ApolloSetterException {
		RateSetter setter = new RateSetter(apolloTranslationEngine, type + "." + AGE_DEPENDENT_FECUNDITY_REDUCTION, section);
		return setter.set(rate);
	}

	@Override
	public List<SetterReturnObject> set(MosquitoReproduction t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		
		list.addAll(setGonotrophicPeriodDuration(t.getGonotrophicPeriodDuration()));
		list.addAll(setEggsPerBrood(t.getEggsPerBrood()));
		list.addAll(setMinimumEggsPerOviposition(t.getMinimumEggsPerOviposition()));
		if (t.getAgeDependentFecundityReduction() != null) {
			list.addAll(setAgeDependentFecundityReduction(t.getAgeDependentFecundityReduction()));
		}
		
		return list;
	}

}

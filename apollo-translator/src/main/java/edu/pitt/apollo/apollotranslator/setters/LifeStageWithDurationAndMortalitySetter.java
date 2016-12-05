package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.DevelopmentalStageEnum;
import edu.pitt.apollo.types.v4_0.Duration;
import edu.pitt.apollo.types.v4_0.LifeStageWithDurationAndMortality;
import edu.pitt.apollo.types.v4_0.MortalityFunction;
import edu.pitt.apollo.types.v4_0.Rate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 3:58:11 PM
 * Class: LifeStageWithDurationAndMortalitySetter
 */
public class LifeStageWithDurationAndMortalitySetter extends AbstractTypedSetter<LifeStageWithDurationAndMortality> {

	private static final String STAGE = "stage";
	private static final String DURATION = "duration";
	private static final String MORTALITY_RATE = "mortalityRate";
	private static final String MORTALITY_FUNCTION = "mortalityFunction";

	public LifeStageWithDurationAndMortalitySetter() {

	}

	public LifeStageWithDurationAndMortalitySetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setStage(DevelopmentalStageEnum stage) throws ApolloSetterException {
		return setValue(STAGE, stage.toString(), section);
	}

	private List<SetterReturnObject> setDuration(Duration duration) throws ApolloSetterException {
		DurationSetter setter = new DurationSetter(apolloTranslationEngine, type + "." + DURATION, section);
		return setter.set(duration);
	}

	private List<SetterReturnObject> setMortalityRate(Rate mortalityRate) throws ApolloSetterException {
		RateSetter rateSetter = new RateSetter(apolloTranslationEngine, type + "." + MORTALITY_RATE, section);
		return rateSetter.set(mortalityRate);
	}

	private List<SetterReturnObject> setMortalityFunction(MortalityFunction function) throws ApolloSetterException {
		MortalityFunctionSetter setter = new MortalityFunctionSetter(apolloTranslationEngine, type + "." + MORTALITY_FUNCTION, section);
		return setter.set(function);
	}

	@Override
	public List<SetterReturnObject> set(LifeStageWithDurationAndMortality t) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setStage(t.getStage()));
		list.addAll(setDuration(t.getDuration()));
		if (t.getMortalityRate() != null) {
			list.addAll(setMortalityRate(t.getMortalityRate()));
		} else if (t.getMortalityFunction() != null) {
			list.addAll(setMortalityFunction(t.getMortalityFunction()));
		}

		return list;
	}

}

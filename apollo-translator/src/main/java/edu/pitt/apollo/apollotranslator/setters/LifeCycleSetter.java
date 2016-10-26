package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.LifeCycle;
import edu.pitt.apollo.types.v4_0.LifeStageWithDurationAndMortality;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 3, 2014
 * Time: 3:54:00 PM
 * Class: LifeCycleSetter
 */
public class LifeCycleSetter extends AbstractTypedSetter<LifeCycle> {

	//public static final String SECTION = "INDIVIDUAL LIFE CYCLES";
	private static final String LIFE_STAGES_WITH_DURATIONS_AND_MORTALITIES = "lifeStagesWithDurationsAndMortalities";
	
	public LifeCycleSetter() {
		
	}
	
	public LifeCycleSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
		super(prefix, section, apolloTranslationEngine);
	}
	
	private List<SetterReturnObject> setLifeStagesWithDurationsAndMortalities(List<LifeStageWithDurationAndMortality> stages) throws ApolloSetterException {
		ListSetter setter = new ListSetter(LifeStageWithDurationAndMortalitySetter.class, LifeStageWithDurationAndMortality.class,
				stages, apolloTranslationEngine, section, type + "." + LIFE_STAGES_WITH_DURATIONS_AND_MORTALITIES);

		return setter.set();
	}
	
	@Override
	public List<SetterReturnObject> set(LifeCycle t) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setLifeStagesWithDurationsAndMortalities(t.getLifeStagesWithDurationsAndMortalities()));
		
		return list;
	}

}

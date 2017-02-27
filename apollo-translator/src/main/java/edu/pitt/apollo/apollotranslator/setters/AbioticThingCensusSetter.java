package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.AbioticThingCensus;
import edu.pitt.apollo.types.v4_0_1.AbioticThingEnum;
import edu.pitt.apollo.types.v4_0_1.OvipositionSiteCensus;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Sep 4, 2014
 Time: 5:31:17 PM
 Class: AbioticThingCensusSetter
 */
public class AbioticThingCensusSetter extends AbstractTypedSetter<AbioticThingCensus> {

	private static final String ABIOTIC_ECOSYSTEM_ELEMENT = "abioticEcosystemElement";

	public AbioticThingCensusSetter() {

	}

	public AbioticThingCensusSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setAbioticEcosystemElement(AbioticThingEnum value) throws ApolloSetterException {
		return setValue(ABIOTIC_ECOSYSTEM_ELEMENT, value.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(AbioticThingCensus t) throws ApolloSetterException {
		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setAbioticEcosystemElement(t.getAbioticThing()));

		if (t instanceof OvipositionSiteCensus) {
			OvipositionSiteCensusSetter setter = new OvipositionSiteCensusSetter(apolloTranslationEngine, type, section);
			list.addAll(setter.set((OvipositionSiteCensus) t));
		}
		
		return list;
	}

}

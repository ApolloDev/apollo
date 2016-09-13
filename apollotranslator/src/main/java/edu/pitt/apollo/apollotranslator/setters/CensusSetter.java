package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.AbioticThingCensus;
import edu.pitt.apollo.types.v4_0.Census;
import edu.pitt.apollo.types.v4_0.PopulationInfectionAndImmunityCensus;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 4, 2014
 * Time: 5:31:59 PM
 * Class: CensusSetter
 */
public class CensusSetter extends AbstractTypedSetter<Census> {

	public static final String DATE_FIELD = "referenceDate";
	public static final String DESCRIPTION_FIELD = "description";

	public CensusSetter() {

	}

	public CensusSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
		super(type, section, apolloTranslationEngine);
	}

	private List<SetterReturnObject> setDescription(String description) throws ApolloSetterException {
		return setValue(DESCRIPTION_FIELD, description, section);
	}

	private List<SetterReturnObject> setDate(XMLGregorianCalendar date) throws ApolloSetterException {
		return setValue(DATE_FIELD, date.toString(), section);
	}

	@Override
	public List<SetterReturnObject> set(Census t) throws ApolloSetterException {

		List<SetterReturnObject> list = new ArrayList<SetterReturnObject>();
		list.addAll(setDescription(t.getDescription()));
		if (t.getReferenceDate() != null) {
			list.addAll(setDate(t.getReferenceDate()));
		}

		if (t instanceof PopulationInfectionAndImmunityCensus) {
			PopulationInfectionAndImmunityCensusSetter setter = new PopulationInfectionAndImmunityCensusSetter(apolloTranslationEngine, type, section);
			list.addAll(setter.set((PopulationInfectionAndImmunityCensus) t));
		} else if (t instanceof AbioticThingCensus) {
			AbioticThingCensusSetter setter = new AbioticThingCensusSetter(apolloTranslationEngine, type, section);
			list.addAll(setter.set((AbioticThingCensus) t));
		}

		return list;
	}

}

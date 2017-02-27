package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.TimeDefinition;

import java.util.ArrayList;
import java.util.List;

public class TimeDefinitionSetter extends AbstractSetter {


	public static final String SIMULATOR_TIME_FIELD = "simulatorTime";
	public static final String DATE_FIELD = "dateTime";
	private String type, section;

	public TimeDefinitionSetter(String type, String section,
			ApolloTranslationEngine apolloTranslationEngine) {
		super(apolloTranslationEngine);
		this.type = type;
                this.section = section;
	}

	public List<SetterReturnObject> setTimeDefinition(TimeDefinition td) throws ApolloSetterException {
		List<SetterReturnObject> results = new ArrayList<SetterReturnObject>();

		results.addAll(setValue(type + "." + SIMULATOR_TIME_FIELD,
				Integer.toString(td.getSimulatorTime()), section));

		if (td.getDateTime() != null) {
			results.addAll(setValue(type + "." + DATE_FIELD, td
					.getDateTime().toString(), section));

		}
		return results;

	}

}

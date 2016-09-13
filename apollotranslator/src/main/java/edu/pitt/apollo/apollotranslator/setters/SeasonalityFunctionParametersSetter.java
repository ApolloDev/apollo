package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0.SeasonalityFunctionParameters;

public class SeasonalityFunctionParametersSetter extends
		AbstractTypedSetter<SeasonalityFunctionParameters> {

//	private static final String SECTION = "SEASONALITY_FUNCTION_PARAMETERS";
	public static final String MAXIMUM_SEASONAL_LATITUDE_FIELD = "maximumSeasonalLatitude";
	public static final String MINIMUM_SEASONAL_LATITUDE_FIELD = "minimumSeasonalLatitude";
	public static final String SEASONAL_TEMPORAL_OFFSET_FIELD = "seasonalTemporalOffset";

	public static final String[] fields = { MAXIMUM_SEASONAL_LATITUDE_FIELD,
			MINIMUM_SEASONAL_LATITUDE_FIELD, SEASONAL_TEMPORAL_OFFSET_FIELD };

	public SeasonalityFunctionParametersSetter(
			ApolloTranslationEngine apolloTranslationEngine, String type,
			String section) {
		super(type, section, apolloTranslationEngine);
	}

//	public SeasonalityFunctionParametersSetter(
//			ApolloTranslationEngine apolloTranslationEngine) {
//		super("seasonalityFunctionParameters", SECTION, apolloTranslationEngine);
//		// TODO Auto-generated constructor stub
//	}
//
//	public SeasonalityFunctionParametersSetter(String type,
//			ApolloTranslationEngine apolloTranslationEngine) {
//		super(type, SECTION, apolloTranslationEngine);
//	}

	public List<SetterReturnObject> setMaximumSeasonalLatitude(String value)
			throws ApolloSetterException {
		return setValue(MAXIMUM_SEASONAL_LATITUDE_FIELD, value, section);
	}

	public List<SetterReturnObject> setMinimumSeasonalLatitude(String value)
			throws ApolloSetterException {
		return setValue(MINIMUM_SEASONAL_LATITUDE_FIELD, value, section);

	}

	public List<SetterReturnObject> setSeasonalTemporalOffset(String value)
			throws ApolloSetterException {
		return setValue(SEASONAL_TEMPORAL_OFFSET_FIELD, value, section);

	}

	public List<SetterReturnObject> setTransmissionCoefficient()
			throws ApolloSetterException {
		return setValue("", AbstractSetter.GENERIC_IS_NOT_NULL_LABEL, section);
	}

	@Override
	public List<SetterReturnObject> set(SeasonalityFunctionParameters sfp)
			throws ApolloSetterException {
		List<SetterReturnObject> sroList = new ArrayList<SetterReturnObject>();
		sroList.addAll(setTransmissionCoefficient());

		sroList.addAll(setMinimumSeasonalLatitude(String.valueOf(sfp
				.getMinimumSeasonalLatitude())));
		sroList.addAll(setMaximumSeasonalLatitude(String.valueOf(sfp
				.getMaximumSeasonalLatitude())));
		sroList.addAll(setSeasonalTemporalOffset(String.valueOf(sfp
				.getSeasonalTemporalOffset())));

		return sroList;

	}
}

package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_2.LocationDefinition;

import java.util.Arrays;
import java.util.List;

public class LocationDefinitionSetter extends AbstractTypedSetter<LocationDefinition> {

    public static final String LOCATIONS_INCLUDED_FIELD = "locationsIncluded";
    public static final String LOCATIONS_EXCLUDED_FIELD = "locationsExcluded";

    public LocationDefinitionSetter(String type, String section,
            ApolloTranslationEngine apolloTranslationEngine) {
        super(type, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setIncluded(List<String> locations) throws ApolloSetterException {

        return setValue(LOCATIONS_INCLUDED_FIELD,
                Arrays.toString(locations.toArray(new String[locations.size()])), section);
    }

    private List<SetterReturnObject> setExcluded(List<String> locations) throws ApolloSetterException {
        return setValue(LOCATIONS_EXCLUDED_FIELD,
                Arrays.toString(locations.toArray(new String[locations.size()])), section);
    }

    @Override
    public List<SetterReturnObject> set(LocationDefinition t) throws ApolloSetterException {
        results.addAll(setIncluded(t.getLocationsIncluded()));
        if (t.getLocationsExcluded() != null && t.getLocationsExcluded().size() > 0) {
            results.addAll(setExcluded(t.getLocationsExcluded()));
        }
        return results;

    }
}

package edu.pitt.apollo.apollolocationservicesdk;

import java.util.HashMap;

/**
 * Created by mas400 on 8/23/16.
 */
public class ApolloLocationServiceAsFileImplementation extends AbstractApolloLocationServiceAsFile{
    public ApolloLocationServiceAsFileImplementation() {
        codeToEntryMap = new HashMap<>();
        nameToEntryMap = new HashMap<>();
        locationTypeIdToName = new HashMap<>();
        letterToEntryMap = new HashMap<>();
    }
}

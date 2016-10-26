package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0.Population;

import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class PopulationsSelectorProcessor implements SelectorProcessor {
    @Override
    public Integer processSelector(RunSimulationMessage runSimulationMessage, TranslationInstructionSelector selector, List<Integer> arrayIndiciesForAccess, Integer optionalIndex) throws ApolloTranslatorException {
        Integer index = null;
        List<Population> populations = runSimulationMessage.getInfectiousDiseaseScenario().getPopulations();
        TranslationInstructionSelector.SelectorNameValuePair pair = selector.getPair();
        String name = pair.getName();
        String value = pair.getValue();

        if (!name.equalsIgnoreCase("taxonId")) {
            throw new ApolloTranslatorException("Population selector only supports the field taxonId. The specified field was " + name);
        }

        for (int i = 0; i < populations.size(); i++) {
            String hostTaxonId = populations.get(i).getTaxonId();
            if (hostTaxonId.equals(value)) {
                index = i;
                return index;
            }
        }

        return index;
    }
}

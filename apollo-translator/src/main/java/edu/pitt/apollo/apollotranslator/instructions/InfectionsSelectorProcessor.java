package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_2.Infection;

import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class InfectionsSelectorProcessor implements SelectorProcessor {
    @Override
    public Integer processSelector(RunSimulationMessage runSimulationMessage, TranslationInstructionSelector selector, List<Integer> arrayIndiciesForAccess, Integer optionalIndex) throws ApolloTranslatorException {
        Integer index = null;
        List<Infection> infections = runSimulationMessage.getInfectiousDiseaseScenario().getInfections();
        TranslationInstructionSelector.SelectorNameValuePair pair = selector.getPair();
        String name = pair.getName();
        String value = pair.getValue();

        if (!name.equalsIgnoreCase("hostTaxonId")) {
            throw new ApolloTranslatorException("Infection selector only supports the field hostTaxonId. The specified field was " + name);
        }

        for (int i = 0; i < infections.size(); i++) {
            String hostTaxonId = infections.get(i).getHost();
            if (hostTaxonId.equals(value)) {
                index = i;
            }
        }

        return index;
    }
}

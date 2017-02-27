package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_1.DiseaseOutcomeWithProbability;

import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class DiseaseOutcomeSelectorProcessor implements SelectorProcessor {
    @Override
    public Integer processSelector(RunSimulationMessage runSimulationMessage, TranslationInstructionSelector selector, List<Integer> arrayIndiciesForAccess, Integer optionalIndex) throws ApolloTranslatorException {

        Integer index = null;
        List<DiseaseOutcomeWithProbability> outcomes = runSimulationMessage.getInfectiousDiseaseScenario()
                .getInfections().get(0).getInfectiousDiseases().get(0).getDiseaseOutcomesWithProbabilities();
        TranslationInstructionSelector.SelectorNameValuePair pair = selector.getPair();
        String name = pair.getName();
        String value = pair.getValue();

        if (!name.equalsIgnoreCase("diseaseOutcome")) {
            throw new ApolloTranslatorException("Disease outcome selector only supports the field diseaseOutcome. The specified field was " + name);
        }

        for (int i = 0; i < outcomes.size(); i++) {

            DiseaseOutcomeWithProbability doap = outcomes.get(i);
            if (doap.getDiseaseOutcome().value().equals(value)) {
                index = i;
                return index;
            }
        }

        return index;
    }
}

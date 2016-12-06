package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_1.LifeStageWithDurationAndMortality;
import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class LifeStagesWithDurationAndMortalitiesSelectorProcessor implements SelectorProcessor {
    @Override
    public Integer processSelector(RunSimulationMessage runSimulationMessage, TranslationInstructionSelector selector, List<Integer> arrayIndiciesForAccess, Integer optionalIndex) throws ApolloTranslatorException {
        Integer index = null;
        List<LifeStageWithDurationAndMortality> stages = runSimulationMessage
                .getInfectiousDiseaseScenario().getPopulations().get(arrayIndiciesForAccess.get(0))
                .getIndividualBiologyAndBehavior().getLifeCycle().getLifeStagesWithDurationsAndMortalities();
        TranslationInstructionSelector.SelectorNameValuePair pair = selector.getPair();
        String name = pair.getName();
        String value = pair.getValue();

        if (!name.equalsIgnoreCase("stage")) {
            throw new ApolloTranslatorException("processLifeStagesWithDurationsAndMortalities selector only supports the field parameterName. The specified field was " + name);
        }

        for (int i = 0; i < stages.size(); i++) {

            LifeStageWithDurationAndMortality stage = stages.get(i);
            if (stage.getStage().value().equals(value)) {
                index = i;
            }
        }

        return index;
    }
}

package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0.NonApolloParameter;

import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class NonApolloParametersSelectorProcessor implements SelectorProcessor {
    @Override
    public Integer processSelector(RunSimulationMessage runSimulationMessage, TranslationInstructionSelector selector, List<Integer> arrayIndiciesForAccess, Integer optionalIndex) throws ApolloTranslatorException {
        Integer index = null;
        List<NonApolloParameter> nonApolloParams = runSimulationMessage.getInfectiousDiseaseScenario().getNonApolloParameters();
        TranslationInstructionSelector.SelectorNameValuePair pair = selector.getPair();
        String name = pair.getName();
        String value = pair.getValue();

        if (!name.equalsIgnoreCase("parameterName")) {
            throw new ApolloTranslatorException("NonApolloParameter selector only supports the field parameterName. The specified field was " + name);
        }

        for (int i = 0; i < nonApolloParams.size(); i++) {

            NonApolloParameter param = nonApolloParams.get(i);
            if (param.getParameterName().equals(value)) {
                index = i;
                return index;
            }
        }

        return index;
    }
}

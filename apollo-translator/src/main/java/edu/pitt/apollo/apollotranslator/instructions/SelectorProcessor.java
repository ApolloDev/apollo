package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;

import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public interface SelectorProcessor {

    Integer processSelector(RunSimulationMessage runSimulationMessage,
                            TranslationInstructionSelector selector,
                            List<Integer> arrayIndiciesForAccess,
                            Integer optionalIndex) throws ApolloTranslatorException;

}

package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_2.*;

import java.util.List;

/**
 * Created by nem41 on 8/10/15.
 */
public class ControlMeasureSelectorProcessor implements SelectorProcessor {
    @Override
    public Integer processSelector(RunSimulationMessage runSimulationMessage,
                                   TranslationInstructionSelector selector,
                                   List<Integer> arrayIndiciesForAccess, Integer optionalIndex) throws ApolloTranslatorException {
        List<InfectiousDiseaseControlMeasure> controlMeasuresList
                = runSimulationMessage.getInfectiousDiseaseScenario().getInfectiousDiseaseControlStrategies();
        if (controlMeasuresList == null || controlMeasuresList.isEmpty()) {
            return null;
        }

        String type = selector.getType();
        String description = selector.getDescription();
        String name = null, value = null;
        TranslationInstructionSelector.SelectorNameValuePair pair = selector.getPair();
        if (pair != null) {
            name = pair.getName();
            value = pair.getValue();
        }

        if (name != null
                && !name.equalsIgnoreCase("treatmentType")) {
            throw new ApolloTranslatorException("Control strategy selector only supports the selector name "
                    + "'treatmentType'. The specified field was '" + name + "'");
        }

        // Integer indexToMatch;
//        if (index.equals(MULTIPLE_INDEX_IDENTIFIER)) {
//            indexToMatch = null;
//        } else {
        // try to parse int
//            try {
//                indexToMatch = Integer.parseInt(index);
//            } catch (NumberFormatException ex) {
//                throw new ApolloTranslatorException("NumberFormatException: " + ex.getMessage());
//            }
//        }

        Integer indexInControlMeasureArray = null;
        int indexOfControlMeasureMatchingSelector = 0;
        for (int i = 0; i < controlMeasuresList.size(); i++) {
            InfectiousDiseaseControlMeasure controlMeasure = controlMeasuresList.get(i);

            // check type
            if (type != null) {
                if (!controlMeasure.getClass().getSimpleName().equalsIgnoreCase(type)) {
                    // this control measure doesn't match type, so continue
                    continue;
                }
            }

            // check treatment
            if (name != null) {
                if (name.equalsIgnoreCase("treatmentType")) {
                    // first check type for individual treatment control strategy
                    if (!controlMeasure.getClass().getSimpleName().equalsIgnoreCase("individualTreatmentControlMeasure")) {
                        continue;
                    }

                    // cast it
                    Treatment treatment = ((IndividualTreatmentControlMeasure) controlMeasure).getIndividualTreatment();
                    if (value.equalsIgnoreCase("vaccination")) {
                        if (!(treatment instanceof Vaccination)) {
                            continue;
                        }
                    } else if (value.equalsIgnoreCase("antiviralTreatment")) {
                        if (!(treatment instanceof AntiviralTreatment)) {
                            continue;
                        }
                    } else if (value.equalsIgnoreCase("drugTreatment")) {
                        if (!(treatment instanceof DrugTreatment)) {
                            continue;
                        }
                    }
                }
            }

            // check description
            if (description != null) {
                if (!controlMeasure.getDescription().equals(description)) {
                    // this control measure doesn't match the description, so continue
                    continue;
                }
            }

            // if here, then the control measure matches everything
            //      if (indexToMatch != null) {
            // if (indexToMatch == indexOfControlMeasureMatchingSelector) {
            indexInControlMeasureArray = i;
            return indexInControlMeasureArray;
            // }
            //    } else {
            //        indicesInControlMeasureArray.add(i);
            //    }

            // indexOfControlMeasureMatchingSelector++;
        }

        return indexInControlMeasureArray;
    }
}

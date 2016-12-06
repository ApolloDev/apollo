package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.SelectorResult;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstruction;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstructionSelector;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 15, 2013
 * Time: 5:07:43 PM
 * Class: TranslationInstructionsProcessor
 * IDE: NetBeans 6.9.1
 */
public class TranslationInstructionsProcessor {

    private enum SupportedFieldTypes {

        FACILITY_ENUM("FacilityEnum");
        private final String value;

        SupportedFieldTypes(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public String getValue() {
            return value;
        }

        public static SupportedFieldTypes getEnum(String value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
            for (SupportedFieldTypes v : values()) {
                if (value.equalsIgnoreCase(v.getValue())) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    private static final Pattern CONTROL_STRATEGIES_PATTERN = Pattern.compile("infectiousDiseaseScenario\\.infectiousDiseaseControlStrategies");
    private static final Pattern LIFE_STAGES_PATTERN = Pattern.compile("infectiousDiseaseScenario\\.populations\\[([0-9]+)\\]\\.individualBiologyAndBehavior\\.lifeCycle\\.lifeStagesWithDurationsAndMortalities");
    private static final Pattern DISEASE_OUTCOMES_PATTERN = Pattern.compile("infectiousDiseaseScenario\\.infections\\[0\\]\\.infectiousDiseases\\[0\\]\\.diseaseOutcomesWithProbabilities");
    private static final Pattern INFECTIONS_PATTERN = Pattern.compile("infectiousDiseaseScenario\\.infections");
    private static final Pattern NON_APOLLO_PARAMETERS_PATTERN = Pattern.compile("infectiousDiseaseScenario\\.nonApolloParameters");
    private static final Pattern POPULATIONS_PATTERN = Pattern.compile("infectiousDiseaseScenario\\.populations");

    private static final String TYPE = "type";
    private static final String DESCRIPTION = "description";
    private static final String MULTIPLE_INDEX_IDENTIFIER = "n";
    private static final String NATIVE_PARAM_INDEX_IDENTIFIER = "**" + MULTIPLE_INDEX_IDENTIFIER;
    private static Map<Pattern, SelectorProcessor> patternsAndSelectorProcessors;
    private final RunSimulationMessage runSimulationMessage;
    private final List<? extends TranslationInstruction> translationInstructions;

    static {
        // the order of this map is important since we want to try to match with the longest pattern first
        // e.g. infectiousDiseaseScenario.infections[0].infectiousDiseases before infectiousDiseaseScenario.infections
        patternsAndSelectorProcessors = new LinkedHashMap<>();
        patternsAndSelectorProcessors.put(CONTROL_STRATEGIES_PATTERN, new ControlMeasureSelectorProcessor());
        patternsAndSelectorProcessors.put(DISEASE_OUTCOMES_PATTERN, new DiseaseOutcomeSelectorProcessor());
        patternsAndSelectorProcessors.put(INFECTIONS_PATTERN, new InfectionsSelectorProcessor());
        patternsAndSelectorProcessors.put(LIFE_STAGES_PATTERN, new LifeStagesWithDurationAndMortalitiesSelectorProcessor());
        patternsAndSelectorProcessors.put(POPULATIONS_PATTERN, new PopulationsSelectorProcessor());
        patternsAndSelectorProcessors.put(NON_APOLLO_PARAMETERS_PATTERN, new NonApolloParametersSelectorProcessor());
    }

    public TranslationInstructionsProcessor(List<? extends TranslationInstruction> translationInstructions, RunSimulationMessage simulatorConfiguration) {
        this.translationInstructions = translationInstructions;
        this.runSimulationMessage = simulatorConfiguration;
    }

    public List<TranslationInstruction> processTranslationInstructions() throws FileNotFoundException, ApolloTranslatorException {

        List<TranslationInstruction> newTranslationInstructions = new ArrayList<>();
        for (TranslationInstruction instruction : translationInstructions) {
            TranslationInstruction newInstruction = processTranslationInstruction(instruction);
            // if new translation instruction is null, no apollo objects were found for that selector
            if (newInstruction != null) {
                newTranslationInstructions.add(instruction);
            }
        }

        return newTranslationInstructions;
    }

    private TranslationInstruction processTranslationInstruction(TranslationInstruction instruction) throws ApolloTranslatorException {

        String apolloParameter = instruction.getApolloParameter();
        String nativeParameter = instruction.getNativeParameterIdentifier();
        Map<String, String> jsFunctionArgsMap = instruction.getJsFunctionArgumentMap();

        SelectorResult selectorResult = SelectorUtility.getSelectorResult(apolloParameter);

        if (selectorResult != null) {
            String objectOrFieldType = selectorResult.getObjectOrFieldType();
            Map<String, String> selectorOptions = selectorResult.getSelectorOptions();
            String selectorIndex = selectorResult.getIndex();
            String optionalIndexString = selectorResult.getOptionalIndex();
            String selectorAndIndexString = selectorResult.getSelectorAndIndexString();

            TranslationInstructionSelector selector = createInstructionSelector(selectorOptions);

            Integer indexInApolloArray = null;
            try {

                for (Pattern pattern : patternsAndSelectorProcessors.keySet()) {

                    Matcher matcher = pattern.matcher(objectOrFieldType);
                    Integer optionalIndex = null;
                    if (optionalIndexString != null) {
                        optionalIndex = Integer.parseInt(optionalIndexString);
                    }

                    if (matcher.matches()) {
                        List<Integer> indiciesFromMatcher = getIndicesList(matcher);

                        SelectorProcessor selectorProcessor = patternsAndSelectorProcessors.get(pattern);
                        indexInApolloArray = selectorProcessor.processSelector(runSimulationMessage, selector, indiciesFromMatcher, optionalIndex);
                        break;
                    }

                }

            } catch (ApolloTranslatorException ex) {
                throw new ApolloTranslatorException("Exception attempting to use selectors for apollo parameter"
                        + " " + apolloParameter + " and native parameter " + nativeParameter + ": " + ex.getMessage());
            }

            if (indexInApolloArray != null) {

                String newApolloParameter = apolloParameter.replace(selectorAndIndexString, "[" + Integer.toString(indexInApolloArray) + "]");
                // String newNativeParameter = nativeParameter == null ? null : nativeParameter.replace(NATIVE_PARAM_INDEX_IDENTIFIER, Integer.toString(index));
                Map<String, String> newJsFunctionArgsMap = new HashMap<String, String>();
                if (jsFunctionArgsMap != null) {
                    for (String argument : jsFunctionArgsMap.keySet()) {
                        String value = jsFunctionArgsMap.get(argument);
                        value = value.replace(selectorAndIndexString, "[" + Integer.toString(indexInApolloArray) + "]");
                        newJsFunctionArgsMap.put(argument, value);
                    }
                }

                instruction.setApolloParameter(newApolloParameter);
                // instruction.setNativeParameterIdentifier(newNativeParameter);
                instruction.setJsFunctionArgumentMappings(newJsFunctionArgsMap);
            } else {
                // nothing to process
                return null;
            }

            // process further instructions
            return processTranslationInstruction(instruction);
        } else {
            return instruction;
        }

    }

    List<Integer> getIndicesList(Matcher matcher) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < matcher.groupCount(); i++) {
            Integer num = Integer.parseInt(matcher.group(i + 1));
            list.add(num);
        }


        return list;
    }


//    private List<TranslationInstruction> processTranslationInstructionOld(TranslationInstruction instruction) throws ApolloTranslatorException {
//
//        List<TranslationInstruction> newInstructions = new ArrayList<TranslationInstruction>();
//
//        String apolloParameter = instruction.getApolloParameter();
//        String nativeParameter = instruction.getNativeParameterIdentifier();
//        Map<String, String> jsFunctionArgsMap = instruction.getJsFunctionArgumentMap();
//
//        SelectorResult selectorResults = SelectorUtility.getSelectorResult(apolloParameter);
//
//        if (selectorResults.size() > 0) {
//
//            String completeTranslationInstructionString = null;
//            for (SelectorResult selectorResult : selectorResults) {
//
//                String objectOrFieldType = selectorResult.getObjectOrFieldType();
//                Map<String, String> selectorOptions = selectorResult.getSelectorOptions();
//                String selectorIndex = selectorResult.getIndex();
//                String optionalIndex = selectorResult.getOptionalIndex();
//                String selectorAndIndexString = selectorResult.getSelectorAndIndexString();
//
//                if (completeTranslationInstructionString == null) {
//                    completeTranslationInstructionString = objectOrFieldType;
//                }
//
//                // create the selector object
//                TranslationInstructionSelector selector = createInstructionSelector(selectorOptions);
//
//                Integer indexInApolloArray = null;
//                //  List<Integer> indicesInApolloObjectArray = null;
//                try {
//
//                    // use regex to match
//
//                    if (objectOrFieldType.equalsIgnoreCase("infectiousDiseaseScenario.infectiousDiseaseControlStrategies")) {
//                        indicesInApolloObjectArray = processControlMeasureSelector(selector, optionalIndex);
//                    } else if (objectOrFieldType.equalsIgnoreCase("infectiousDiseaseScenario.infections[0].infectiousDiseases[0].diseaseOutcomesWithProbabilities")) {
//                        indicesInApolloObjectArray = processDiseaseOutcomeSelector(selector);
//                    } else if (objectOrFieldType.equalsIgnoreCase("infectiousDiseaseScenario.infections")) {
//                        indicesInApolloObjectArray = processInfectionSelector(selector);
//                    } else if (objectOrFieldType.equalsIgnoreCase("infectiousDiseaseScenario.nonApolloParameters")) {
//                        indicesInApolloObjectArray = processNonApolloParameterSelector(selector);
//                    } else if (objectOrFieldType.equals("infectiousDiseaseScenario.populations")) {
//                        indicesInApolloObjectArray = processPopulationSelector(selector);
////				} else if (objectOrFieldType.equalsIgnoreCase("infectiousDiseaseScenario.behaviors")) {
////					indicesInApolloObjectArray = processIndividualBehaviorSelecter(selector, optionalIndex);
//                    } else if (objectOrFieldType.equalsIgnoreCase("infectiousDiseaseScenario.lifeCycles[0].lifeStagesWithDurationsAndMortalities")) {
//                        indicesInApolloObjectArray = processLifeStagesWithDurationsAndMortalitiesSelector(selector);
////				} else if (objectOrFieldType.equalsIgnoreCase("infectiousDiseaseScenario.populationInfectionAndImmunityCensuses")) {
////					indicesInApolloObjectArray = processPopulationInfectionAndImmunityCensusSelector(selector);
//                    } else if ((selectorOptions == null || selectorOptions.isEmpty()) && optionalIndex != null) {
//                        // this means only an index was used
//                        indicesInApolloObjectArray = new ArrayList<Integer>();
//                        indicesInApolloObjectArray.add(Integer.parseInt(optionalIndex));
//                    }
//                } catch (ApolloTranslatorException ex) {
//                    throw new ApolloTranslatorException("Exception attempting to use selectors for apollo parameter"
//                            + " " + apolloParameter + " and native parameter " + nativeParameter + ": " + ex.getMessage());
//                }
//
//                if (indexInApolloArray != null) {
//                    //  for (Integer index : indicesInApolloObjectArray) {
//                    // now replace in the appropriate strings and create new translation instruction(s)
////                    String selectorAndIndexString = apolloParamMatcher.group(2);
//
//                    String newApolloParameter = apolloParameter.replace(selectorAndIndexString, "[" + Integer.toString(indexInApolloArray) + "]");
//                    String newNativeParameter = nativeParameter == null ? null : nativeParameter.replace(NATIVE_PARAM_INDEX_IDENTIFIER, Integer.toString(index));
//                    Map<String, String> newJsFunctionArgsMap = new HashMap<String, String>();
//                    if (jsFunctionArgsMap != null) {
//                        for (String argument : jsFunctionArgsMap.keySet()) {
//                            String value = jsFunctionArgsMap.get(argument);
//                            value = value.replace(selectorAndIndexString, "[" + Integer.toString(indexInApolloArray) + "]");
//                            newJsFunctionArgsMap.put(argument, value);
//                        }
//                    }
//
//                    instruction.setApolloParameter(newApolloParameter);
//                    instruction.setNativeParameterIdentifier(newNativeParameter);
//                    instruction.setJsFunctionArgumentMappings(newJsFunctionArgsMap);
//
//                    newInstructions.add(instruction);
//                    //   }
//                } else {
//                    newInstructions.add(instruction);
//                }
//            }
//        }
//
//        return newInstructions;
//    }

    private TranslationInstructionSelector createInstructionSelector(Map<String, String> selectorOptions) {
        TranslationInstructionSelector selector = new TranslationInstructionSelector();
        for (String option : selectorOptions.keySet()) {
            // the control measure must match all options
//            Matcher typeMatcher = TYPE_PATTERN.matcher(option);
            if (option.equals(TYPE)) {
                String type = selectorOptions.get(option);
                selector.setType(type);
            } else if (option.equals(DESCRIPTION)) {
                String description = selectorOptions.get(option);
                selector.setDescription(description);
            } else {
                // unrecognized option, use a pair
                String value = selectorOptions.get(option);
                selector.setPair(option, value);
            }
        }

        return selector;
    }

//    private Integer processDiseaseOutcomeSelector(TranslationInstructionSelector selector) throws ApolloTranslatorException {
//
//        Integer index = null;
//        List<DiseaseOutcomeWithProbability> outcomes = runSimulationMessage.getInfectiousDiseaseScenario()
//                .getInfections().get(0).getInfectiousDiseases().get(0).getDiseaseOutcomesWithProbabilities();
//        SelectorNameValuePair pair = selector.getPair();
//        String name = pair.getName();
//        String value = pair.getValue();
//
//        if (!name.equalsIgnoreCase("diseaseOutcome")) {
//            throw new ApolloTranslatorException("Disease outcome selector only supports the field diseaseOutcome. The specified field was " + name);
//        }
//
//        for (int i = 0; i < outcomes.size(); i++) {
//
//            DiseaseOutcomeWithProbability doap = outcomes.get(i);
//            if (doap.getDiseaseOutcome().value().equals(value)) {
//                 index = i;
//                return index;
//            }
//        }
//
//        return index;
//    }

//    private Integer processInfectionSelector(TranslationInstructionSelector selector) throws ApolloTranslatorException {
//
//        Integer index = null;
//        List<Infection> infections = runSimulationMessage.getInfectiousDiseaseScenario().getInfections();
//        SelectorNameValuePair pair = selector.getPair();
//        String name = pair.getName();
//        String value = pair.getValue();
//
//        if (!name.equalsIgnoreCase("hostTaxonId")) {
//            throw new ApolloTranslatorException("Infection selector only supports the field hostTaxonId. The specified field was " + name);
//        }
//
//        for (int i = 0; i < infections.size(); i++) {
//            String hostTaxonId = infections.get(i).getHost();
//            if (hostTaxonId.equals(value)) {
//                index = i;
//            }
//        }
//
//        return index;
//    }

//    private Integer processPopulationSelector(TranslationInstructionSelector selector) throws ApolloTranslatorException {
//
//        Integer index = null;
//        List<Population> populations = runSimulationMessage.getInfectiousDiseaseScenario().getPopulations();
//        SelectorNameValuePair pair = selector.getPair();
//        String name = pair.getName();
//        String value = pair.getValue();
//
//        if (!name.equalsIgnoreCase("populationSpecies")) {
//            throw new ApolloTranslatorException("PopulationInfectionAndImmunityCensus selector only supports the field populationSpecies. The specified field was " + name);
//        }
//
//        for (int i = 0; i < populations.size(); i++) {
//            String hostTaxonId = populations.get(i).getTaxonId();
//            if (hostTaxonId.equals(value)) {
//                index = i;
//                return index;
//            }
//        }
//
//        return index;
//    }

//    private List<Integer> processLifeStagesWithDurationsAndMortalitiesSelector(TranslationInstructionSelector selector) throws ApolloTranslatorException {
//
//        List<Integer> indicies = new ArrayList<Integer>();
//        List<LifeStageWithDurationAndMortality> stages = runSimulationMessage.getInfectiousDiseaseScenario().getLifeCycles()
//                .get(0).getLifeStagesWithDurationsAndMortalities();
//        SelectorNameValuePair pair = selector.getPair();
//        String name = pair.getName();
//        String value = pair.getValue();
//
//        if (!name.equalsIgnoreCase("stage")) {
//            throw new ApolloTranslatorException("processLifeStagesWithDurationsAndMortalities selector only supports the field parameterName. The specified field was " + name);
//        }
//
//        for (int i = 0; i < stages.size(); i++) {
//
//            LifeStageWithDurationAndMortality stage = stages.get(i);
//            if (stage.getStage().value().equals(value)) {
//                indicies.add(i);
//            }
//        }
//
//        return indicies;
//    }

//    private Integer processNonApolloParameterSelector(TranslationInstructionSelector selector) throws ApolloTranslatorException {
//
//        Integer index = null;
//        List<NonApolloParameter> nonApolloParams = runSimulationMessage.getInfectiousDiseaseScenario().getNonApolloParameters();
//        SelectorNameValuePair pair = selector.getPair();
//        String name = pair.getName();
//        String value = pair.getValue();
//
//        if (!name.equalsIgnoreCase("parameterName")) {
//            throw new ApolloTranslatorException("NonApolloParameter selector only supports the field parameterName. The specified field was " + name);
//        }
//
//        for (int i = 0; i < nonApolloParams.size(); i++) {
//
//            NonApolloParameter param = nonApolloParams.get(i);
//            if (param.getParameterName().equals(value)) {
//                index = i;
//                return index;
//            }
//        }
//
//        return index;
//    }

//	private List<Integer> processIndividualBehaviorSelecter(TranslationInstructionSelector selector, String index) throws ApolloTranslatorException {
//
//		List<Behavior> behaviorsList = runSimulationMessage.getInfectiousDiseaseScenario().getBehaviors();
//		if (behaviorsList == null || behaviorsList.isEmpty()) {
//			return null;
//		}
//
//		String type = selector.getType();
//		String name = null, value = null;
//		SelectorNameValuePair pair = selector.getPair();
//		if (pair != null) {
//			name = pair.getName();
//			value = pair.getValue();
//		}
//
//		if (name != null && !name.equalsIgnoreCase("type")) {
//			throw new ApolloTranslatorException("IndividualBehavior selector only supports the selector name 'type'. The specified field was '" + name + "'");
//		}
//
//		Integer indexToMatch;
//		if (index.equals(MULTIPLE_INDEX_IDENTIFIER)) {
//			indexToMatch = null;
//		} else {
//			// try to parse int
//			try {
//				indexToMatch = Integer.parseInt(index);
//			} catch (NumberFormatException ex) {
//				throw new ApolloTranslatorException("NumberFormatException: " + ex.getMessage());
//			}
//		}
//
//		List<Integer> indicesInBehaviorArray = new ArrayList<Integer>();
//		int indexOfIndividualBehaviorMatchingSelector = 0;
//
//		for (int i = 0; i < behaviorsList.size(); i++) {
//			Behavior behavior = behaviorsList.get(i);
//
//			if (type.equalsIgnoreCase("humanBehavior")) {
//				if (!(behavior instanceof HumanBehavior)) {
//					continue;
//				}
//			} else if (type.equalsIgnoreCase("mosquitoBehavior")) {
//				if (!(behavior instanceof MosquitoBehavior)) {
//					continue;
//				}
//			}
//
//			// if here, then the control measure matches everything
//			if (indexToMatch != null) {
//				if (indexToMatch == indexOfIndividualBehaviorMatchingSelector) {
//					indicesInBehaviorArray.add(i);
//					return indicesInBehaviorArray;
//				}
//			} else {
//				indicesInBehaviorArray.add(i);
//			}
//
//			indexOfIndividualBehaviorMatchingSelector++;
//		}
//
//		return indicesInBehaviorArray;
//	}

//    private Integer processControlMeasureSelector(TranslationInstructionSelector selector, String index) throws ApolloTranslatorException {
//
//        List<InfectiousDiseaseControlMeasure> controlMeasuresList = runSimulationMessage.getInfectiousDiseaseScenario().getInfectiousDiseaseControlStrategies();
//        if (controlMeasuresList == null || controlMeasuresList.isEmpty()) {
//            return null;
//        }
//
//        String type = selector.getType();
//        String description = selector.getDescription();
//        String name = null, value = null;
//        SelectorNameValuePair pair = selector.getPair();
//        if (pair != null) {
//            name = pair.getName();
//            value = pair.getValue();
//        }
//
//        if (name != null
//                && !name.equalsIgnoreCase("treatmentType")) {
//            throw new ApolloTranslatorException("Control strategy selector only supports the selector name "
//                    + "'treatmentType'. The specified field was '" + name + "'");
//        }
//
//       // Integer indexToMatch;
////        if (index.equals(MULTIPLE_INDEX_IDENTIFIER)) {
////            indexToMatch = null;
////        } else {
//            // try to parse int
////            try {
////                indexToMatch = Integer.parseInt(index);
////            } catch (NumberFormatException ex) {
////                throw new ApolloTranslatorException("NumberFormatException: " + ex.getMessage());
////            }
////        }
//
//        Integer indexInControlMeasureArray = null;
//        int indexOfControlMeasureMatchingSelector = 0;
//        for (int i = 0; i < controlMeasuresList.size(); i++) {
//            InfectiousDiseaseControlMeasure controlMeasure = controlMeasuresList.get(i);
//
//            // check type
//            if (type != null) {
//                if (!controlMeasure.getClass().getSimpleName().equalsIgnoreCase(type)) {
//                    // this control measure doesn't match type, so continue
//                    continue;
//                }
//            }
//
//            // check treatment
//            if (name != null) {
//                if (name.equalsIgnoreCase("treatmentType")) {
//                    // first check type for individual treatment control strategy
//                    if (!controlMeasure.getClass().getSimpleName().equalsIgnoreCase("individualTreatmentControlMeasure")) {
//                        continue;
//                    }
//
//                    // cast it
//                    Treatment treatment = ((IndividualTreatmentControlMeasure) controlMeasure).getIndividualTreatment();
//                    if (value.equalsIgnoreCase("vaccination")) {
//                        if (!(treatment instanceof Vaccination)) {
//                            continue;
//                        }
//                    } else if (value.equalsIgnoreCase("antiviralTreatment")) {
//                        if (!(treatment instanceof AntiviralTreatment)) {
//                            continue;
//                        }
//                    } else if (value.equalsIgnoreCase("drugTreatment")) {
//                        if (!(treatment instanceof DrugTreatment)) {
//                            continue;
//                        }
//                    }
//                }
//            }
//
//            // check description
//            if (description != null) {
//                if (!controlMeasure.getDescription().equals(description)) {
//                    // this control measure doesn't match the description, so continue
//                    continue;
//                }
//            }
//
//            // if here, then the control measure matches everything
//      //      if (indexToMatch != null) {
//               // if (indexToMatch == indexOfControlMeasureMatchingSelector) {
//                    indexInControlMeasureArray = i;
//                    return indexInControlMeasureArray;
//               // }
//        //    } else {
//        //        indicesInControlMeasureArray.add(i);
//        //    }
//
//           // indexOfControlMeasureMatchingSelector++;
//        }
//
//        return indexInControlMeasureArray;
//
//    }
//    private List<Integer> processImperialReproductionNumberForPlaceSelector(TranslationInstructionSelector selector, String index) throws ApolloTranslatorException {
//
//        //the spec says there is guaranteed to be at least one infection and one infection acquisition...remove this check?!
//        List<InfectionAcquisition> acquisition = runSimulationMessage.getInfectiousDiseaseScenario().getInfections().get(0).getInfectionAcquisition();
//        if (acquisition == null || acquisition.isEmpty()) {
//            return null;
//        }
//
//        ImperialReproductionNumber imperialReproNum = acquisition.get(0).getImperialReproductionNumber();
//        if (imperialReproNum == null) {
//            return null;
//        }
//
//        List<ImperialReproductionNumberForPlace> imperialReproNumsForPlace = imperialReproNum.getImperialReproductionNumberForPlace();
//
//        if (imperialReproNumsForPlace != null && imperialReproNumsForPlace.size() > 0) {
//
//            List<Integer> indicesInObjectArray = new ArrayList<Integer>();
//
//            // try to parse int before checking pair
//            if (index != null) {
//                try {
//                    int objectIndex = Integer.parseInt(index);
//                    indicesInObjectArray.add(objectIndex);
//                    return indicesInObjectArray;
//                } catch (NumberFormatException ex) {
//                    throw new ApolloTranslatorException("NumberFormatException: " + ex.getMessage());
//                }
//            }
//
//            SelectorNameValuePair pair = selector.getPair();
//            if (pair == null) {
//                return null;
//            }
//
//            String name = pair.getName();
//            String value = pair.getValue();
//
//            int indexOfObjectMatchingSelector = 0;
//            imperialReproNumLoop:
//            for (int i = 0; i < imperialReproNumsForPlace.size(); i++) {
//                ImperialReproductionNumberForPlace imperialReproNumForPlace = imperialReproNumsForPlace.get(i);
//
//
//                // check type
//                boolean matched = false;
//                if (name != null && value != null) {
////                    Field[] fields = imperialReproNumForPlace.getClass().getDeclaredFields();
////                    for (Field field : fields) {
//                    try {
//                        Field field = imperialReproNumForPlace.getClass().getDeclaredField(name);
//                        field.setAccessible(true);
//
////                        if (field.getName().equals(name)) {
//
//                        String fieldValue = getApolloFieldValue(name, field.get(imperialReproNumForPlace));
//                        if (fieldValue == null) {
//                            continue imperialReproNumLoop;
//                        }
//
//                        if (!fieldValue.equals(value)) {
//                            // this field matched the name but the value was not a match, so continue
//                            continue imperialReproNumLoop;
//                        } else {
//                            matched = true;
//                        }
//                    } catch (IllegalAccessException ex) {
//                        throw new ApolloTranslatorException("IllegalAccessException: " + ex.getMessage());
//                    } catch (IllegalArgumentException ex) {
//                        throw new ApolloTranslatorException("IllegalArgumentException: " + ex.getMessage());
//                    } catch (NoSuchFieldException ex) {
//                        throw new ApolloTranslatorException("NoSuchFieldException: " + ex.getMessage());
//                    } catch (SecurityException ex) {
//                        throw new ApolloTranslatorException("SecurityException: " + ex.getMessage());
//                    }
////                        }
////                    }
//                }
//
//                if (!matched) {
//                    // this object wasn't a match
//                    continue;
//                }
//
//                // if here, then the object matches everything
//                indicesInObjectArray.add(i);
//
//
//                indexOfObjectMatchingSelector++;
//            }
//
//            return indicesInObjectArray;
//        } else {
//            return null;
//        }
//    }
//    private String getApolloFieldValue(String fieldName, Object fieldValue) {
//
//        SupportedFieldTypes fieldType = SupportedFieldTypes.getEnum(fieldName);
//
//        if (fieldType.equals(SupportedFieldTypes.FACILITY_ENUM)) {
//
//            FacilityEnum placeType = (FacilityEnum) fieldValue;
//            return placeType.toString();
//        }
//
//        return null;
//    }
}

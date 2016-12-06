package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.exception.ApolloParserException;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.file.AbstractTranslationInstructionsFileLoader;
import edu.pitt.apollo.apollotranslator.file.JsFileLoader;
import edu.pitt.apollo.apollotranslator.file.PlainTextTranslationInstructionsFileLoader;
import edu.pitt.apollo.apollotranslator.file.XMLTranslationInstructionsFileLoader;
import edu.pitt.apollo.apollotranslator.instructions.TranslationInstructionsProcessor;
import edu.pitt.apollo.apollotranslator.types.js.JsContainer;
import edu.pitt.apollo.apollotranslator.types.js.JsFunction;
import edu.pitt.apollo.apollotranslator.types.translator.*;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import org.mozilla.javascript.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Aug 9, 2013 Time:
 * 1:27:15 PM Class: ApolloTranslator IDE: NetBeans 6.9.1
 */
public class ApolloTranslationEngine {

    private static final String XPATH_OBJECTS_FIELD = "xPathObjectArray";
    private static final String NAMESPACES_FIELD = "namespaces";
    private static final String NAMESPACES_ID_FIELD = "id";
    private static final String NAMESPACES_NAMESPACE_FIELD = "namespace";
    private static final String XPATH_OBJECT_XPATH_FIELD = "xpath";
    private static final String XPATH_OBJECT_VALUE_FIELD = "value";
    private static final String XPATH_OBJECT_VALUES_FIELD = "values";
    private static final String COMMENT_FIELD = "comment";
    private static final String PRINT_FIELD = "print";
    private static final String VALUE_FIELD = "value";
    private static final String WARNINGS_FIELD = "warnings";
    private static final String EXCEPTION_FIELD = "exception";
    private static final boolean LOG_NO_PRINT_FIELD_SET = false;
    private String translationInstructionsFilePath;
    private String javaScriptFilePath;
    private List<? extends TranslationInstruction> nativeTermTranslationInstructions = null;
    private JsContainer jsContainer;
    private Context context;
    private ScriptableObject scope;
    private SimulatorConfigurationParser simulatorConfigurationParser;
    private ApolloParameterMap apolloParameterMap = new ApolloParameterMap();
    private static final String ANY_VALID_ARRAY_INDEX = "\\[([1-9][0-9]*|\\d)\\]";
    private TranslationMode translationMode;
    Pattern[] patterns = {
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX + ".controlMeasureCompliance"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".individualTreatment.vaccinationEfficacies"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".individualTreatment.vaccinationEfficacies"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureResponseDelay.value"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureStartTime.triggers"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureStartTime.triggers" + ANY_VALID_ARRAY_INDEX + ".reactiveControlMeasureThreshold"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureStartTime.trigger.ascertainmentFraction"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureStartTime.trigger.ascertainmentDelay"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".supplySchedule"),
            //            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
            //                    + ".supplySchedule"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".administrationCapacity"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureReactiveEndPointFraction"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".targetPopulationsAndPrioritizations"),
            //            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
            //                    + ANY_VALID_ARRAY_INDEX + ".controlStrategyResponseDelay"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX
                    + ".individualTreatment.antiviralTreatmentEfficacy.averageAntiviralTreatmentEfficacy"),
            //            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
            //                    + ANY_VALID_ARRAY_INDEX
            //                    + ".controlMeasureTargetPopulationsAndPrioritization"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX
            //                    + ".antiviralTreatmentAdministrationCapacity"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX
            //                    + ".antiviralTreatmentSupplySchedule"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX + ".controlMeasureResponseDelay"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureStartTime.trigger.reactiveControlMeasureThreshold"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX
            //                    + ".reactiveStartTime.trigger.ascertainmentFraction"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX
            //                    + ".reactiveStartTime.trigger.ascertainmentDelay"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX + ".schoolClosureTargetFacilities"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX + ".schoolClosureDuration"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX + ""),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX + ""),
            Pattern.compile("controlMeasure" + ANY_VALID_ARRAY_INDEX
                    + ".controlMeasureCompliance"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX + ".controlMeasureCompliance"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX
            //                    + ".controlMeasureReactiveEndPointFraction"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX + ".controlMeasureCompliance"),
            Pattern.compile("infectiousDiseaseScenario.infections[0].infectiousDiseases"),
            //            Pattern.compile("populationInitialization.populationStates.populationStates.description.diseaseStates"),
            Pattern.compile("infectiousDiseaseScenario.populationInfectionAndImmunityCensuses"),
            Pattern.compile("infectiousDiseaseScenario.populationInfectionAndImmunityCensuses" + ANY_VALID_ARRAY_INDEX
                    + ".censusData.censusDataCells"),
            Pattern.compile("infectiousDiseaseScenario.populationInfectionAndImmunityCensuses" + ANY_VALID_ARRAY_INDEX
                    + ".censusData.location"),
            //            Pattern.compile("controlMeasure" + ANY_VALID_ARRAY_INDEX
            //                    + ""),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".individualTreatment.numDosesInTreatmentCourse"),
            //            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
            //                    + ".startTime.startTimeRelativeToScenarioDate"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".individualTreatment.vaccinationEfficacies"
                    + ANY_VALID_ARRAY_INDEX + ".averageVaccinationEfficacy"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".individualTreatment.vaccinationEfficacies"
                    + ANY_VALID_ARRAY_INDEX + ".vaccinationEfficaciesByTimeSinceMostRecentDose.numberOfDosesAdministered"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".individualTreatment.vaccinationEfficacies"
                    + ANY_VALID_ARRAY_INDEX + ".vaccinationEfficaciesConditionedOnTimeSinceMostRecentDose.vaccinationEfficacyConditionedOnTimeSinceMostRecentDose"),
        /*            Pattern.compile("controlMeasure" + ANY_VALID_ARRAY_INDEX
        + ".vaccination.vaccinationEfficacies"
        + ANY_VALID_ARRAY_INDEX + ".averageVaccinationEfficacy"),
        Pattern.compile("controlMeasure" + ANY_VALID_ARRAY_INDEX
        + ".vaccination.vaccinationEfficacies"
        + ANY_VALID_ARRAY_INDEX + ".averageVaccinationEfficacy"),*/
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".targetPopulationsAndPrioritizations.controlMeasureNamedPrioritizationScheme"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies" + ANY_VALID_ARRAY_INDEX
                    + ".targetPopulationsAndPrioritizations.controlMeasureTargetPopulationsAndPrioritization"),
            Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
                    + ANY_VALID_ARRAY_INDEX + ".controlMeasureStopTime"),
//        Pattern.compile("infectiousDiseaseScenario.infectiousDiseaseControlStrategies"
//        + ANY_VALID_ARRAY_INDEX + ".controlStrategyStartTime.startTimeRelativeToScenarioDate"),
            //            Pattern.compile("controlMeasure"
            //                    + ANY_VALID_ARRAY_INDEX
            //                    + ".antiviralTreatment.numDosesInTreatmentCourse"),
            Pattern.compile("authentication.requesterPassword")};
//            Pattern.compile("controlMeasure"
//                    + ANY_VALID_ARRAY_INDEX + ".fixedStartTime.startTime"),};

    public ApolloTranslationEngine(
            RunSimulationMessage simulatorConfiguration, TranslationMode translationMode,
            String translationInstructionsFilePath, String javaScriptFilePath,
            boolean useLogging) throws FileNotFoundException, IOException,
            ApolloTranslatorException {
        this.translationInstructionsFilePath = translationInstructionsFilePath;
        this.javaScriptFilePath = javaScriptFilePath;
        this.translationMode = translationMode;
        ApolloLogger.initialize(useLogging);

        // create the context and scope for Rhino
        context = Context.enter();
        context.setOptimizationLevel(-1);

        scope = context.initStandardObjects();
        addDefaultCustomMethodsToScope(scope);

        this.simulatorConfigurationParser = new SimulatorConfigurationParser(
                simulatorConfiguration);

        loadTranslationInstructionsFile();

        TranslationInstructionsProcessor translationInstructionsrocessor = new TranslationInstructionsProcessor(nativeTermTranslationInstructions, simulatorConfiguration);
        nativeTermTranslationInstructions = translationInstructionsrocessor.processTranslationInstructions();

//        for (TranslationInstruction instruction : nativeTermTranslationInstructions) {
//            System.out.println(instruction.getApolloParameter());
//        }

        loadJavaScriptFile();
        loadApolloParameterMap(); // this should be done after processing the instruction selectors
    }

    private void loadTranslationInstructionsFile()
            throws FileNotFoundException, IOException,
            ApolloTranslatorException {

        // this will read the translation instructions file and store everything
        // in the list
        AbstractTranslationInstructionsFileLoader translationInstructionsFileLoader;
        if (translationMode.equals(TranslationMode.PLAIN_TEXT)) {
            translationInstructionsFileLoader = new PlainTextTranslationInstructionsFileLoader(translationInstructionsFilePath);
        } else if (translationMode.equals(TranslationMode.XML)) {
            translationInstructionsFileLoader = new XMLTranslationInstructionsFileLoader(translationInstructionsFilePath);
        } else {
            throw new ApolloTranslatorException("The translation mode " + translationMode.name()
                    + " did not match any of the supported types.");
        }

        nativeTermTranslationInstructions = translationInstructionsFileLoader.loadInstructions();
    }

    private void loadApolloParameterMap() {

        for (TranslationInstruction ti : nativeTermTranslationInstructions) {
            apolloParameterMap.put(ti.getApolloParameter(), ti);
        }
    }

    public ApolloParameterMap getApolloParameterMap() {
        return apolloParameterMap;
    }

    //    public void loadTranslationInstructionsFilePath(
//            String translationInstructionsFilePath)
//            throws FileNotFoundException, IOException,
//            ApolloTranslatorException {
//        this.translationInstructionsFilePath = translationInstructionsFilePath;
//        loadTranslationInstructionsFile();
//    }
    private void loadJavaScriptFile() throws FileNotFoundException,
            ApolloTranslatorException {
        // this will load the JavaScript file and store all function names and
        // arguments
        jsContainer = JsFileLoader.loadJavaScriptFunctionsFromFile(javaScriptFilePath);
    }

    public void loadJavaScriptFilePath(String javaScriptFilePath)
            throws FileNotFoundException, ApolloTranslatorException {
        this.javaScriptFilePath = javaScriptFilePath;
        loadJavaScriptFile();
    }

    public TranslationReturnObject translateToNativeParameterWithoutJavaScript(TranslationInstruction instruction) throws ApolloTranslatorException {
        String nativeParamIdentifier = instruction.getNativeParameterIdentifier();
        String globalComment = instruction.getGlobalComment();

        TranslationReturnObject tro;
        if (instruction instanceof PlainTextTranslationInstruction) {
            tro = new PlainTextTranslationReturnObject();
            ((PlainTextTranslationReturnObject) tro).setDelimiter(((PlainTextTranslationInstruction) instruction).getNativeParameterDelimiter());
            ((PlainTextTranslationReturnObject) tro).setNativeTerm(nativeParamIdentifier);
        } else if (instruction instanceof XMLTranslationInstruction) {
            tro = new XMLTranslationReturnObject();
            ((XMLTranslationReturnObject) tro).setXmlNativeValueType(((XMLTranslationInstruction) instruction).getXmlNativeValueType());
        } else {
            throw new ApolloTranslatorException("Unrecognized translation instruction type");
        }

        tro.setCommentFromJavascriptFunction(globalComment);

        return tro;
    }

    public TranslationReturnObject translateToNativeParameterUsingJavaScript(
            TranslationInstruction instruction)
            throws ApolloTranslatorException, IllegalAccessException,
            ClassNotFoundException {
        // instruction contains the native param name, function name, and
        // argument map
        // javaScript is the entire JavaScript string loaded from the file
        // jsFunctionList is a list of JsFunnction objects loaded from the
        // JavaScript
        // context and scope are used for Rhino
        // scp is the parser to retrieve objects from the simulator
        // configuration

        String functionName = instruction.getJsFunctionName();
        String nativeParamIdentifier = instruction.getNativeParameterIdentifier();
        String globalComment = instruction.getGlobalComment();
        JsFunction jsFunction = jsContainer.getFunctionList().getJsFunctionByName(functionName);
        Map<String, String> functionArgumentMap = instruction.getJsFunctionArgumentMap();

        // check that function name is set
        if (functionName == null || functionName.trim().equals("")) {
            throw new ApolloTranslatorException(
                    "No JavaScript function was specified for native term \""
                            + nativeParamIdentifier + "\"");
        }

        // check that the referenced function exists
        if (jsFunction == null) {
            String message = "The function \"" + functionName
                    + "\" with arguments \"";
            for (String argument : functionArgumentMap.keySet()) {
                message += argument + ", ";
            }

            message += "\" for native term \"" + nativeParamIdentifier
                    + "\" could not be found in the JavaScript file.";
            throw new ApolloTranslatorException(message);
        }

        Object[] functionArguments = {};
        // check if there are any arguments to process
        if (jsFunction.getNumArguments() > 0) {

            // there are function arguments, so there should be argument
            // mappings
            if (functionArgumentMap == null || functionArgumentMap.isEmpty()) {
                throw new ApolloTranslatorException(
                        "An empty or missing function argument map was given for the function \""
                                + functionName + "\" for native term \""
                                + nativeParamIdentifier + "\"");
            }

            functionArguments = new Object[jsFunction.getNumArguments()];
            List<String> functionArgumentNames = jsFunction.getArgumentNames();
            // now for each argument, get the associated object from the
            // simulator configuration
            int argumentIndex = 0;
            for (String argument : functionArgumentNames) {

                String apolloIdentifier = functionArgumentMap.get(argument);
                if (apolloIdentifier == null) {
                    throw new ApolloTranslatorException(
                            "There was no value or Apollo identifier path provided for the argument \""
                                    + argument + "\" of the function \""
                                    + functionName
                                    + "\" for the native term \""
                                    + nativeParamIdentifier + "\"");
                }

                Object apolloObject = null;
                // fix for issue 48
                if (apolloIdentifier.startsWith("\"")
                        && apolloIdentifier.endsWith("\"")) {
                    apolloObject = apolloIdentifier.substring(1,
                            apolloIdentifier.length() - 1);
                } else {
                    try {
                        apolloObject = simulatorConfigurationParser.getApolloObjectFromLabel(apolloIdentifier);
                    } catch (ApolloParserException ex) {
                        throw new ApolloTranslatorException(
                                "ApolloParserException translating native term \""
                                        + nativeParamIdentifier + "\": "
                                        + ex.getMessage());
                    }
                    if (apolloObject == null) {

                        // we print this here since we have access to the apollo
                        // identifier
                        ApolloLogger.log(Level.FINE,
                                "The translation file specified that the simulator configuration field \""
                                        + apolloIdentifier
                                        + "\" should be used to translate to the \""
                                        + nativeParamIdentifier
                                        + "\" native parameter."
                                        + "  However, the \""
                                        + apolloIdentifier
                                        + "\" field is not set in the simulator configuration.  The native parameter \""
                                        + nativeParamIdentifier
                                        + "\" will not be translated.");

                        return null;
                    }
                }
                functionArguments[argumentIndex] = Context.javaToJS(
                        apolloObject, scope);
                argumentIndex++;
            }
        }

        context.evaluateString(scope, jsContainer.getJavaScript(), "<cmd>", 1,
                null);
        Object functionObj = scope.get(functionName, scope);
        if (!(functionObj instanceof Function)) {
            throw new ApolloTranslatorException(
                    "The object returned from JavaScript string matching name "
                            + functionName + " was not a function");
        }
        Function function = (Function) functionObj;
        Object result;
        try {
            result = function.call(context, scope, scope, functionArguments);
        } catch (JavaScriptException ex) {
            throw new ApolloTranslatorException(
                    "There was an exception thrown from the JavaScript: "
                            + ex.getMessage());
        }

        // check if the result is undefined (no return value from the function)
        if (result.equals(Context.getUndefinedValue())) {
            throw new ApolloTranslatorException(
                    "No value was returned from the function \""
                            + functionName
                            + "\" for the parameter"
                            + " \""
                            + nativeParamIdentifier
                            + "\". All JavaScript functions referenced in the translation file must return a value.");
        }

        String valueFromJavaScript = "";
        String globalAndJavaScriptCommentString = "";
        List<String> stringWarnings = null;

        if (globalComment != null) {
            globalComment = globalComment.replace("\\n", "\n");
            globalAndJavaScriptCommentString = globalComment + "\n";
        }

        TranslationReturnObject tro;
        if (instruction instanceof PlainTextTranslationInstruction) {
            tro = new PlainTextTranslationReturnObject();
        } else if (instruction instanceof XMLTranslationInstruction) {
            tro = new XMLTranslationReturnObject();
        } else {
            throw new ApolloTranslatorException("Unrecognized translation instruction type");
        }
        if (result instanceof NativeObject) {
            NativeObject no = (NativeObject) result;

            if (!no.containsKey(PRINT_FIELD) || (Boolean) no.get(PRINT_FIELD)) {
                if (!no.containsKey(PRINT_FIELD)) {
                    if (LOG_NO_PRINT_FIELD_SET) {
                        ApolloLogger.log(Level.WARNING,
                                "The JavaScript object for native term "
                                        + nativeParamIdentifier
                                        + " did not contain "
                                        + "a print field. The value and/or comment will still be printed.");
                    }
                }

                if (instruction instanceof PlainTextTranslationInstruction) {
                    if (!no.containsKey(VALUE_FIELD)
                            && !no.containsKey(COMMENT_FIELD)
                            && !no.containsKey(WARNINGS_FIELD)) {
                        throw new ApolloTranslatorException(
                                "The JavaScript object indicated it should print for native term "
                                        + nativeParamIdentifier
                                        + ", but there was no value or comment set");
                    }

                    if (no.containsKey(VALUE_FIELD)) {
                        String value = (no.get(VALUE_FIELD) == null) ? null : no.get(VALUE_FIELD).toString();
                        if (value == null) {
                            // since the value was set this means the set value
                            // was null
                            throw new ApolloTranslatorException(
                                    "The value to print for term "
                                            + nativeParamIdentifier + " was set to null");
                        } else {
                            valueFromJavaScript += value;
                        }
                    }
                } else if (instruction instanceof XMLTranslationInstruction) {

                    XMLTranslationInstruction.XMLNativeValueType type = ((XMLTranslationInstruction) instruction).getXmlNativeValueType();
                    tro = new XMLTranslationReturnObject();

                    Map<String, XMLXPathValue> map = new HashMap<>();

                    // need to loop over return object and set xpaths
                    if (no.containsKey(XPATH_OBJECTS_FIELD)) {
                        NativeArray objects = (NativeArray) no.get(XPATH_OBJECTS_FIELD);
                        for (int i = 0; i < (int) objects.getLength(); i++) {
                            NativeObject nativeObject = (NativeObject) objects.get(i);
                            if (nativeObject.containsKey(XPATH_OBJECT_XPATH_FIELD) && nativeObject.containsKey(XPATH_OBJECT_VALUE_FIELD)) {

                                String xpath = nativeObject.get(XPATH_OBJECT_XPATH_FIELD).toString();

                                if (type.equals(XMLTranslationInstruction.XMLNativeValueType.ATTRIBUTE)) {
                                    XMLAttributeValuePair attributeValuePair = new XMLAttributeValuePair();
                                    attributeValuePair.setAttribute(instruction.getNativeParameterIdentifier());
                                    attributeValuePair.setValue(nativeObject.get(XPATH_OBJECT_VALUE_FIELD).toString());
                                    map.put(xpath, attributeValuePair);
                                } else if (type.equals(XMLTranslationInstruction.XMLNativeValueType.SINGLE_VALUE)
                                        || type.equals(XMLTranslationInstruction.XMLNativeValueType.XML)
										|| type.equals(XMLTranslationInstruction.XMLNativeValueType.XML_REQUIRING_XPATH)) {
                                    XMLStringValue xmlStringValue = new XMLStringValue();
                                    xmlStringValue.setValue(nativeObject.get(XPATH_OBJECT_VALUE_FIELD).toString());
                                    map.put(xpath, xmlStringValue);
                                } else if (type.equals(XMLTranslationInstruction.XMLNativeValueType.MULTIPLE_VALUES)) {
                                    XMLMultipleStringValues xmlMultipleStringValues = new XMLMultipleStringValues();
                                    List<String> values = (List<String>) nativeObject.get(XPATH_OBJECT_VALUES_FIELD);
                                    xmlMultipleStringValues.setElementName(instruction.getNativeParameterIdentifier());
                                    xmlMultipleStringValues.setValues(values);
                                    map.put(xpath, xmlMultipleStringValues);
                                }

                            } else {
                                throw new ApolloTranslatorException("The JavaScript object did not contain an xpath"
                                + " field or a value field");
                            }
                        }

                        if (no.containsKey(NAMESPACES_FIELD)) {
                            Map<String, String> namespaceMap = new HashMap<>();
                            NativeArray namespaceObjects = (NativeArray) no.get(NAMESPACES_FIELD);
                            for (int i = 0; i < (int) namespaceObjects.getLength(); i++) {
                                NativeObject namespaceObject = (NativeObject) namespaceObjects.get(i);
                                if (namespaceObject.containsKey(NAMESPACES_ID_FIELD) && namespaceObject.containsKey(NAMESPACES_NAMESPACE_FIELD)) {
                                    String id = namespaceObject.get(NAMESPACES_ID_FIELD).toString();
                                    String namespace = namespaceObject.get(NAMESPACES_NAMESPACE_FIELD).toString();
                                    namespaceMap.put(id, namespace);

                                } else {
                                    throw new ApolloTranslatorException("The JavaScript object had a namespace list defined but a namespace object"
                                            + " was missing the 'id' or 'namespace' fields");
                                }
                            }

                            ((XMLTranslationReturnObject) tro).setNamespaces(namespaceMap);
                        }

                        ((XMLTranslationReturnObject) tro).setXpathsAndValues(map);
                    } else {
                        throw new ApolloTranslatorException("The instruction is of type XMLTranslationInstruction"
                        + " but the JavaScript object did not contain an " + XPATH_OBJECTS_FIELD + " field");
                    }

                    ((XMLTranslationReturnObject) tro).setXmlNativeValueType(((XMLTranslationInstruction) instruction).getXmlNativeValueType());
                }

                if (no.containsKey(COMMENT_FIELD)) {
                    String jsComment = (no.get(COMMENT_FIELD) == null) ? null
                            : no.get(COMMENT_FIELD).toString();
                    if (jsComment == null) {
                        // print a warning but this isn't a fatal error
                        ApolloLogger.log(Level.WARNING,
                                "The comment for native term "
                                        + nativeParamIdentifier + " was set to null");
                    } else {
                        globalAndJavaScriptCommentString += jsComment + "\n";
                    }
                }

                if (no.containsKey(WARNINGS_FIELD)) {

                    List<String> jsWarnings = (no.get(WARNINGS_FIELD) == null) ? null
                            : (List<String>) no.get(WARNINGS_FIELD);
                    if (jsWarnings == null) {
                        // print a warning but this isn't a fatal error
                        ApolloLogger.log(Level.WARNING,
                                "The warning for native term "
                                        + nativeParamIdentifier + " was set to null");
                    } else {
                        stringWarnings = jsWarnings;
                    }
                }


                tro.setCommentFromJavascriptFunction(globalAndJavaScriptCommentString);
                tro.setWarningsFromJavascriptFunction(stringWarnings);


            } else {
                ApolloLogger.log(Level.FINE,
                        "The JavaScript indicated that the term "
                                + nativeParamIdentifier + " should not be printed");
                return null;
            }

        } else {
            valueFromJavaScript = Context.toString(result);
        }

        if (instruction instanceof PlainTextTranslationInstruction) {
            tro = new PlainTextTranslationReturnObject();
            ((PlainTextTranslationReturnObject) tro).setDelimiter(((PlainTextTranslationInstruction) instruction).getNativeParameterDelimiter());
            ((PlainTextTranslationReturnObject) tro).setValueFromJavascriptFunction(valueFromJavaScript);
            ((PlainTextTranslationReturnObject) tro).setNativeTerm(nativeParamIdentifier);
        }

        return tro;
    }

    private static void addDefaultCustomMethodsToScope(Scriptable scope) {
        @SuppressWarnings("rawtypes")
        Class[] parameters = new Class[]{String.class
        };
        Method methodSignature;


        try {
            methodSignature = JavaScriptAccessableMethods.class.getMethod(
                    "readFile", parameters);

            scope.put(
                    "readFile", scope, new FunctionObject("readFile",
                            methodSignature, scope));

            methodSignature = JavaScriptAccessableMethods.class.getMethod(
                    "printline", parameters);

            scope.put(
                    "printline", scope, new FunctionObject("printline",
                            methodSignature, scope));

            methodSignature = JavaScriptAccessableMethods.class.getMethod(
                    "parseApolloObjectFromXml", String.class, String.class, String.class);

            scope.put(
                    "parseApolloObjectFromXml", scope, new FunctionObject("parseApolloObjectFromXml",
                            methodSignature, scope));

        } catch (SecurityException e) {

            e.printStackTrace();
        } catch (NoSuchMethodException e) {

            e.printStackTrace();
        }

    }

    public String getJavaScriptFilePath() {
        return javaScriptFilePath;
    }

    public String getTranslationInstructionsFilePath() {
        return translationInstructionsFilePath;
    }

    public void setLoggingState(boolean useLogging) {
        ApolloLogger.useLogging(useLogging);
    }

    //	public static void main(String[] args) throws FileNotFoundException,
//			IOException, ApolloTranslatorException, IllegalAccessException,
//			ClassNotFoundException, ScriptException {
//
//		// ApolloTranslator at = new ApolloTranslator(
//		// "./src/test/resources/flute/flute_translation.csv",
//		// "./src/test/resources/flute/flute_javascript.js", true);
//
//		SimulatorConfiguration sc = SimulatorConfigurationFactory
//				.getDefaultModelRunRequestObjType(DefVaccCm.VACC_CM,
//						DefAvCm.AV_CM, DefScCm.ALL_SC_CM_FIXED);
//
//		// at.translate(sc, ".");
//	}
    public List<String> ensureAllInstructionsUsed() {
        List<String> unusedInstructions = new ArrayList<String>();
        toNextInstruction:
        for (TranslationInstruction ti : nativeTermTranslationInstructions) {
            if (!ti.isTranslated()) {
                for (Pattern p : patterns) {
                    if (p.matcher(ti.getApolloParameter()).matches()) {
                        continue toNextInstruction;
                    }
                }
                unusedInstructions.add(ti.getApolloParameter());
            }
        }
        return unusedInstructions;

    }
}

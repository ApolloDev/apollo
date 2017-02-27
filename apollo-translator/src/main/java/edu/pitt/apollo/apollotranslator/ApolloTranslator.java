package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.file.NativeFileCreator;
import edu.pitt.apollo.apollotranslator.file.NativeFileCreatorFactory;
import edu.pitt.apollo.apollotranslator.file.VerboseFileCreator;
import edu.pitt.apollo.apollotranslator.file.VerboseFileCreatorFactory;
import edu.pitt.apollo.apollotranslator.types.translator.*;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApolloTranslator {

    RunSimulationMessage simulatorConfiguration;
    String translationInstructionsFilePath;
    String javaScriptFilePath;
    private List<SetterReturnObject> setterReturnObjects; // this is used for printing the native files
    private Map<String, List<SetterReturnObject>> apolloLabelSetterReturnObjectsMap; // this is used for printing the verbose file
    private String commentToken;
    private TranslationMode translationMode;
    private TranslatorInput translatorInput;

    public ApolloTranslator(TranslatorInput translatorInput) {
        super();
        this.simulatorConfiguration = translatorInput.getRunSimulationMessage();
        this.translationMode = translatorInput.getTranslationMode();
        this.translationInstructionsFilePath = translatorInput.getTranslationInstructionsFilePath();
        this.javaScriptFilePath = translatorInput.getJavaScriptFilePath();
        this.commentToken = translatorInput.getCommentToken();
        this.translatorInput = translatorInput;
    }

    public Map<String, ByteArrayOutputStream> translate() throws IOException,
            ApolloTranslatorException {
        ApolloTranslationEngine ate = new ApolloTranslationEngine(
                simulatorConfiguration, translationMode, translationInstructionsFilePath,
                javaScriptFilePath, true);

        ManualSimulatorConfigurationParser parser = new ManualSimulatorConfigurationParser(
                simulatorConfiguration, ate);
        try {
            List<String> unusedInstructions = parser.parse();
            if (unusedInstructions.size() > 0) {
                String unusedInstructionsStr = "";
                for (String apolloParams : unusedInstructions) {
                    // Pattern.compile("vaccinationControlMeasure" +
                    // ANY_VALID_ARRAY_INDEX +".controlMeasureResponseDelay"),
                    unusedInstructionsStr += apolloParams + "\n";
                }
                throw new ApolloTranslatorException(
                        "The following apollo_parameters that were specified in the translation file are invalid and have been ignored (check spelling?):\n"
                                + unusedInstructionsStr);

            }
        } catch (ApolloSetterException ex) {
            throw new ApolloTranslatorException(ex.getMessage());
        }

        setterReturnObjects = parser.getSetterReturnObjects();
        apolloLabelSetterReturnObjectsMap = parser.getApolloLabelSetterReturnObjectsMap();

        NativeFileCreatorInput nativeFileCreatorInput;
        if (translationMode.equals(TranslationMode.XML)) {
            XMLNativeFileCreatorInput input = new XMLNativeFileCreatorInput();

            if (translatorInput.getBaseConfigurationFileContent() != null) {
                // base configuration currently only supported for XML files
                input.setBaseXMLText(translatorInput.getBaseConfigurationFileContent());
            }
            nativeFileCreatorInput = input;

        } else if (translationMode.equals(TranslationMode.PLAIN_TEXT)) {
            PlainTextNativeFileCreatorInput input = new PlainTextNativeFileCreatorInput();
            input.setCommentToken(commentToken);
            nativeFileCreatorInput = input;
        } else {
            throw new ApolloTranslatorException("Unrecognized translation mode");
        }


        return generateAllFiles(nativeFileCreatorInput);
    }

    private Map<String, ByteArrayOutputStream> generateAllFiles(NativeFileCreatorInput nativeFileCreatorInput) throws FileNotFoundException, ApolloTranslatorException, IOException {

        Map<String, ByteArrayOutputStream> baosMap = new HashMap<String, ByteArrayOutputStream>();

        String simulatorName = simulatorConfiguration.getSoftwareIdentification().getSoftwareName();
        String simulatorVersion = simulatorConfiguration.getSoftwareIdentification().getSoftwareVersion();
        VerboseFileCreator verboseWriter = VerboseFileCreatorFactory.getVerboseFileWriter(translationMode, simulatorName,
                simulatorVersion, commentToken, apolloLabelSetterReturnObjectsMap);
        if (verboseWriter != null) {
            baosMap.put(VerboseFileCreator.VERBOSE_FILE_NAME, verboseWriter.createVerboseFile());
        }

//        NativeFileCreatorInput input;
//        if (translationMode.equals(TranslationMode.PLAIN_TEXT)) {
//            PlainTextNativeFileCreatorInput ptInput = new PlainTextNativeFileCreatorInput();
//            ptInput.setCommentToken(commentToken);
//            input = ptInput;
//        } else if (translationMode.equals(TranslationMode.XML)) {
//            XMLNativeFileCreatorInput xmlInput = new XMLNativeFileCreatorInput();
//            xmlInput.setBaseXMLText(new String(Files.readAllBytes(Paths.get("/Users/nem41/Documents/java_projects/apollo_projects/apollo-translator/test.xml"))));
//            input = xmlInput;
//        } else {
//            throw new ApolloTranslatorException("Unrecognized translation mode");
//        }

        // input.setSetterReturnObjects(setterReturnObjects);

        NativeFileCreator nativeWriter = NativeFileCreatorFactory.getNativeFileWriter(translationMode, nativeFileCreatorInput, setterReturnObjects);
        baosMap.putAll(nativeWriter.createNativeFiles());

        return baosMap;
    }

    public List<SetterReturnObject> getSetterReturnObjects() {
        return setterReturnObjects;
    }

    public Map<String, List<SetterReturnObject>> getApolloLabelSetterReturnObjectsMap() {

        return apolloLabelSetterReturnObjectsMap;
    }

}

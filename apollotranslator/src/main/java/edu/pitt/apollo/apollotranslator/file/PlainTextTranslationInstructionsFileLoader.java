package edu.pitt.apollo.apollotranslator.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.PlainTextTranslationInstruction;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Aug 12, 2013 Time:
 * 5:52:30 PM Class: PlainTextTranslationInstructionsFileLoader IDE: NetBeans 6.9.1
 */
public class PlainTextTranslationInstructionsFileLoader extends AbstractTranslationInstructionsFileLoader<PlainTextTranslationInstruction> {

    private static final String NATIVE_PARAM_DELIMITER_COLUMN_NAME = "native_parameter_delimiter";
    private static final int NATIVE_PARAM_DELIMITER_COLUMN_IDX = 2;
    private final String[] COLUMN_NAMES = {APOLLO_PARAM_COLUMN_NAME,
        NATIVE_PARAM_IDENTIFIER_COLUMN_NAME,
        NATIVE_PARAM_DELIMITER_COLUMN_NAME, JAVASCRIPT_FUNCTION_COLUMN_NAME,
        FUNCTION_ARG_MAPPINGS_COLUMN_NAME, CONFIG_FILE_NAME_COLUMN_NAME,
        GLOBAL_COMMENT_COLUMN_NAME, CONFIG_FILE_COMMENT_COLUMN_NAME,
        HTML_FILE_COMMENT_COLUMN_NAME,
        HTML_FILE_WARNING_COLUMN_NAME,
        HTML_FILE_WARNING_LEVEL_COLUMN_NAME,
         
    };
    private final int[] COLUMN_IDX;

    public PlainTextTranslationInstructionsFileLoader(String translationInstructionsFile) {

        super(translationInstructionsFile, PlainTextTranslationInstruction.class);

        // these columns are used by all translation output types but the indices may be different for different types
        apolloParamColumnIndex = 0;
        nativeParamIdentifierColumnIndex = 1;
        javaScriptFunctionColumnIndex = 3;
        functionArgMappingsColumnIndex = 4;
        configFileNameColumnIndex = 5;
        globalCommentColumnIndex = 6;
        configFileCommentColumnIndex = 7;
        htmlFileCommentColumnIndex = 8;
        htmlFileExplanationColumnIndex = 9;
        htmlFileExplanationLevelColumnIndex = 10;

        COLUMN_IDX = new int[]{apolloParamColumnIndex,
            nativeParamIdentifierColumnIndex,
            NATIVE_PARAM_DELIMITER_COLUMN_IDX,
            javaScriptFunctionColumnIndex, functionArgMappingsColumnIndex,
            configFileNameColumnIndex, globalCommentColumnIndex,
            configFileCommentColumnIndex, htmlFileCommentColumnIndex, 
            htmlFileExplanationColumnIndex, htmlFileExplanationLevelColumnIndex,
            
        };
    }

    @Override
    public List<PlainTextTranslationInstruction> loadInstructions() throws ApolloTranslatorException, IOException {

        CSVReader reader = loadInstructionReader(COLUMN_NAMES, COLUMN_IDX);

        int lineNumber = 1;
        String[] line;
        List<PlainTextTranslationInstruction> instructionList = new ArrayList<PlainTextTranslationInstruction>();
        while ((line = reader.readNext()) != null) {

            // first get the translation instruction with all base properties
            PlainTextTranslationInstruction instruction = new PlainTextTranslationInstruction(createTranslationInstructionFromLine(line, lineNumber));
            // then set the plain text specific properties
            instruction.setNativeParameterDelimiter(line[NATIVE_PARAM_DELIMITER_COLUMN_IDX]);

            instructionList.add(instruction);
            lineNumber++;
        }

        return instructionList;
    }
//    public static List<PlainTextTranslationInstruction> loadInstructions(
//            String translationInstructionsFile)
//            throws FileNotFoundException, IOException,
//            ApolloTranslatorException {
//
//        CSVReader reader;
////		try {
//        reader = new CSVReader(new FileReader(
//                translationInstructionsFile), ',', '"');
////		} catch (FileNotFoundException ex) {
////			throw new FileNotFoundException("File "
////					+ translationInstructionsFilePath
////					+ " could not be opened for reading: " + ex.getMessage());
////		}
//
//        String[] line;
//        try {
//            if ((line = reader.readNext()) == null) {
//                throw new ApolloTranslatorException("The translation file text "
//                        + translationInstructionsFile
//                        + " appears to be empty");
//            } else {
//                for (int i = 0; i < COLUMN_NAMES.length; i++) {
//                    if (!line[COLUMN_IDX[i]].trim().equalsIgnoreCase(
//                            COLUMN_NAMES[i])) {
//                        throw new ApolloTranslatorException("Column \"" + i
//                                + "\" in the translation file text "
//                                + translationInstructionsFile
//                                + " does not have the required heading of "
//                                + COLUMN_NAMES[i]);
//                    }
//                }
//
//            }
//
//            int lineNumber = 1;
//            List<PlainTextTranslationInstruction> instructionList = new ArrayList<PlainTextTranslationInstruction>();
//            while ((line = reader.readNext()) != null) {
//
//                if (line.length < 5) {
//                    throw new ApolloTranslatorException("Line " + lineNumber
//                            + " in the translation instructions file text "
//                            + translationInstructionsFile
//                            + " does not contain at least 5 columns");
//                }
//
//                PlainTextTranslationInstruction instruction = new PlainTextTranslationInstruction();
//                instruction.setNativeParameterIdentifier(line[NATIVE_PARAM_IDENTIFIER_COLUMN_IDX]);
//                instruction.setNativeParameterDelimiter(line[NATIVE_PARAMETER_DELIMITER_COLUMN_IDX]);
//                instruction.setJsFunctionName(line[JAVASCRIPT_FUNCTION_COLUMN_IDX]);
//                instruction.setHtmlFileWarning(line[HTML_FILE_WARNING_IDX]);
//
//                String cssWarningClass = line[HTML_FILE_WARNING_LEVEL_COLUMN_IDX];
//                if (cssWarningClass.isEmpty()) {
//                    instruction.setCssWarningClass(HtmlFileBuilder.CssWarningClasses.NONE);
//                } else {
//                    instruction.setCssWarningClass(HtmlFileBuilder.CssWarningClasses.getEnum(cssWarningClass));
//                }
//
//                instruction.setApolloParameter(line[APOLLO_PARAM_COLUMN_IDX]);
//
//                if (!line[FUNCTION_ARG_MAPPINGS_COLUMN_IDX].trim().equals("")) {
//                    String[] jsArgumentMappings = line[FUNCTION_ARG_MAPPINGS_COLUMN_IDX].split(",");
//
//                    for (String jsArgumentMapping : jsArgumentMappings) {
//                        String[] mappingArray = jsArgumentMapping.split(PARAMETER_ASSIGNMENT_CHARACTER);
//                        if (mappingArray.length != 2) {
//                            throw new ApolloTranslatorException(
//                                    "Line "
//                                    + lineNumber
//                                    + " in the translation instructions file text "
//                                    + translationInstructionsFile
//                                    + " contains a misconfigured JavaScript function argument mapping");
//                        }
//
//                        instruction.setJsFunctionArgumentMapping(
//                                mappingArray[0], mappingArray[1]);
//                    }
//                }
//
//                String configFileName = line[CONFIG_FILE_NAME_COLUMN_IDX];
//                instruction.setConfigurationFileName(configFileName);
//
//                String globalComment = line[GLOBAL_COMMENT_COLUMN_IDX];
//                if (!globalComment.trim().equals("")) {
//                    instruction.setGlobalComment(globalComment);
//                }
//
//                String htmlFileComment = line[HTML_FILE_COMMENT_COLUMN_IDX];
//                if (!globalComment.trim().equals("")) {
//                    instruction.setHtmlFileComment(htmlFileComment);
//                }
//
//                String configFileComment = line[CONFIG_FILE_COMMENT_COLUMN_IDX];
//                if (!configFileComment.trim().equals("")) {
//                    instruction.setConfigurationFileComment(configFileComment);
//                }
//
//
//
//
//                instructionList.add(instruction);
//                lineNumber++;
//            }
//
//            return instructionList;
//        } catch (IOException ex) {
//            throw new IOException(
//                    "IOException attempting to read line from the translation instructions file text "
//                    + translationInstructionsFile + ": "
//                    + ex.getMessage());
//        }
//    }
}

package edu.pitt.apollo.apollotranslator.file;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.file.VerboseFileCreator.CssWarningClasses;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstruction;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2013
 * Time: 2:48:19 PM
 * Class: AbstractTranslationInstructionsFileLoader
 * IDE: NetBeans 6.9.1
 */
public abstract class AbstractTranslationInstructionsFileLoader<T extends TranslationInstruction> {

    int apolloParamColumnIndex;
    public static final String APOLLO_PARAM_COLUMN_NAME = "apollo_parameter";
    int nativeParamIdentifierColumnIndex;
    public static final String NATIVE_PARAM_IDENTIFIER_COLUMN_NAME = "native_parameter";
    int javaScriptFunctionColumnIndex;
    public static final String JAVASCRIPT_FUNCTION_COLUMN_NAME = "javascript_function_name";
    int functionArgMappingsColumnIndex;
    public static final String FUNCTION_ARG_MAPPINGS_COLUMN_NAME = "function_arg_mappings";
    int configFileNameColumnIndex;
    public static final String CONFIG_FILE_NAME_COLUMN_NAME = "config_file_name";
    int globalCommentColumnIndex;
    public static final String GLOBAL_COMMENT_COLUMN_NAME = "global_comment";
    int configFileCommentColumnIndex;
    public static final String CONFIG_FILE_COMMENT_COLUMN_NAME = "config_file_comment";
    int htmlFileCommentColumnIndex;
    public static final String HTML_FILE_COMMENT_COLUMN_NAME = "html_file_comment";
    int htmlFileExplanationColumnIndex;
    public static final String HTML_FILE_WARNING_COLUMN_NAME = "html_file_warning";
    int htmlFileExplanationLevelColumnIndex;
    public static final String HTML_FILE_WARNING_LEVEL_COLUMN_NAME = "html_file_warning_level";
    static final String PARAMETER_ASSIGNMENT_CHARACTER = ":";
    private String translationInstructionsFile;
    private Class<T> translationInstructionClass;

    public AbstractTranslationInstructionsFileLoader(String translationInstructionsFile, Class<T> translationInstructionClass) {
        this.translationInstructionsFile = translationInstructionsFile;
        this.translationInstructionClass = translationInstructionClass;
    }

    protected CSVReader loadInstructionReader(String[] columnNames, int[] columnIdx) throws ApolloTranslatorException, IOException {

        CSVReader reader;
        reader = new CSVReader(new FileReader(translationInstructionsFile), ',', '"');

        String[] line;
        try {
            if ((line = reader.readNext()) == null) {
                throw new ApolloTranslatorException("The translation file text "
                        + translationInstructionsFile
                        + "appears to be empty");
            } else {
                for (int i = 0; i < columnNames.length; i++) {
                    if (!line[columnIdx[i]].trim().equalsIgnoreCase(
                            columnNames[i])) {
                        throw new ApolloTranslatorException("Column \"" + i
                                + "\" in the translation file text "
                                + translationInstructionsFile
                                + " does not have the required heading of "
                                + columnNames[i]);
                    }
                }

            }
        } catch (IOException ex) {
            throw new IOException(
                    "IOException attempting to read line from the translation instructions file text "
                    + translationInstructionsFile + ": "
                    + ex.getMessage());
        }

        return reader;
    }

    protected TranslationInstruction createTranslationInstructionFromLine(String[] line, int lineNumber) throws ApolloTranslatorException {

        if (line.length < 5) {
            throw new ApolloTranslatorException("Line " + lineNumber
                    + " in the translation instructions file text "
                    + translationInstructionsFile
                    + " does not contain at least 5 columns");
        }

        TranslationInstruction instruction = null;
        try {
            instruction = translationInstructionClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ApolloTranslatorException("Could not create instance of translation instruction");
        }
        instruction.setNativeParameterIdentifier(line[nativeParamIdentifierColumnIndex]);
        instruction.setJsFunctionName(line[javaScriptFunctionColumnIndex]);
        instruction.setHtmlFileWarning(line[htmlFileExplanationColumnIndex]);

        String cssWarningClass = line[htmlFileExplanationLevelColumnIndex];
        if (cssWarningClass.isEmpty()) {
            instruction.setCssWarningClass(CssWarningClasses.NONE);
        } else {
            instruction.setCssWarningClass(CssWarningClasses.getEnum(cssWarningClass));
        }

        instruction.setApolloParameter(line[apolloParamColumnIndex]);

        if (!line[functionArgMappingsColumnIndex].trim().equals("")) {
            String[] jsArgumentMappings = line[functionArgMappingsColumnIndex].split(",");

            for (String jsArgumentMapping : jsArgumentMappings) {
                String[] mappingArray = jsArgumentMapping.split(PARAMETER_ASSIGNMENT_CHARACTER);
                if (mappingArray.length != 2) {
                    throw new ApolloTranslatorException(
                            "Line "
                            + lineNumber
                            + " in the translation instructions file text "
                            + translationInstructionsFile
                            + " contains a misconfigured JavaScript function argument mapping");
                }

                instruction.setJsFunctionArgumentMapping(
                        mappingArray[0], mappingArray[1]);
            }
        }

        String configFileName = line[configFileNameColumnIndex];
        instruction.setConfigurationFileName(configFileName);

        String globalComment = line[globalCommentColumnIndex];
        if (!globalComment.trim().equals("")) {
            instruction.setGlobalComment(globalComment);
        }

        String htmlFileComment = line[htmlFileCommentColumnIndex];
        if (!globalComment.trim().equals("")) {
            instruction.setHtmlFileComment(htmlFileComment);
        }

        String configFileComment = line[configFileCommentColumnIndex];
        if (!configFileComment.trim().equals("")) {
            instruction.setConfigurationFileComment(configFileComment);
        }

        return instruction;
    }

    public abstract List<? extends TranslationInstruction> loadInstructions() throws ApolloTranslatorException, IOException;
}

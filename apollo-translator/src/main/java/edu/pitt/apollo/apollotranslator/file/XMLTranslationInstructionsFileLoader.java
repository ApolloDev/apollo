package edu.pitt.apollo.apollotranslator.file;

import au.com.bytecode.opencsv.CSVReader;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.XMLTranslationInstruction;
import edu.pitt.apollo.apollotranslator.types.translator.XMLTranslationInstruction.XMLNativeValueType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2013
 * Time: 12:18:32 PM
 * Class: XMLTranslationInstructionsFileLoader
 * IDE: NetBeans 6.9.1
 */
public class XMLTranslationInstructionsFileLoader extends AbstractTranslationInstructionsFileLoader<XMLTranslationInstruction> {

    private static final String XML_NATIVE_VALUE_TYPE_COLUMN_NAME = "xml_native_value_type";
    private static final int XML_NATIVE_VALUE_TYPE_COLUMN_IDX = 2;
    private static final String XPATH_COLUMN_NAME = "xpath";
    private static final int XPATH_COLUMN_IDX = 3;
    private final String[] COLUMN_NAMES = {APOLLO_PARAM_COLUMN_NAME,
        NATIVE_PARAM_IDENTIFIER_COLUMN_NAME, XML_NATIVE_VALUE_TYPE_COLUMN_NAME,
            XPATH_COLUMN_NAME, JAVASCRIPT_FUNCTION_COLUMN_NAME,
        FUNCTION_ARG_MAPPINGS_COLUMN_NAME, CONFIG_FILE_NAME_COLUMN_NAME,
        GLOBAL_COMMENT_COLUMN_NAME, CONFIG_FILE_COMMENT_COLUMN_NAME,
        HTML_FILE_COMMENT_COLUMN_NAME,
        HTML_FILE_WARNING_COLUMN_NAME,
        HTML_FILE_WARNING_LEVEL_COLUMN_NAME,};
    private final int[] COLUMN_IDX;

    public XMLTranslationInstructionsFileLoader(String translationInstructionsFile) {

        super(translationInstructionsFile, XMLTranslationInstruction.class);

        // these columns are used by all translation output types but the indices may be different for different types
        apolloParamColumnIndex = 0;
        nativeParamIdentifierColumnIndex = 1;
        javaScriptFunctionColumnIndex = 4;
        functionArgMappingsColumnIndex = 5;
        configFileNameColumnIndex = 6;
        globalCommentColumnIndex = 7;
        configFileCommentColumnIndex = 8;
        htmlFileCommentColumnIndex = 9;
        htmlFileExplanationColumnIndex = 10;
        htmlFileExplanationLevelColumnIndex = 11;

        COLUMN_IDX = new int[]{apolloParamColumnIndex,
            nativeParamIdentifierColumnIndex,
            XML_NATIVE_VALUE_TYPE_COLUMN_IDX, XPATH_COLUMN_IDX,
            javaScriptFunctionColumnIndex, functionArgMappingsColumnIndex,
            configFileNameColumnIndex, globalCommentColumnIndex,
            configFileCommentColumnIndex, htmlFileCommentColumnIndex,
            htmlFileExplanationColumnIndex, htmlFileExplanationLevelColumnIndex,};
    }

    @Override
    public List<XMLTranslationInstruction> loadInstructions() throws ApolloTranslatorException, IOException {

        CSVReader reader = loadInstructionReader(COLUMN_NAMES, COLUMN_IDX);

        int lineNumber = 1;
        String[] line;
        List<XMLTranslationInstruction> instructionList = new ArrayList<XMLTranslationInstruction>();
        while ((line = reader.readNext()) != null) {

            // first get the translation instruction with all base properties
            XMLTranslationInstruction instruction = new XMLTranslationInstruction(createTranslationInstructionFromLine(line, lineNumber));
            // then set the xml specific properties
            instruction.setXmlNativeValueType(XMLNativeValueType.valueOf(line[XML_NATIVE_VALUE_TYPE_COLUMN_IDX]));
            instruction.setxPathString(line[XPATH_COLUMN_IDX]);

            // add quotes so it is not treated as an apollo object path
            instruction.setJsFunctionArgumentMapping("xPathString", "\"" + line[XPATH_COLUMN_IDX] + "\"");

            instructionList.add(instruction);
            lineNumber++;
        }

        return instructionList;
    }
}

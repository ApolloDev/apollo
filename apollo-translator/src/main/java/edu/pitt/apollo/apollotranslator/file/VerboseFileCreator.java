package edu.pitt.apollo.apollotranslator.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import java.io.ByteArrayOutputStream;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 29, 2013
 * Time: 11:26:49 AM
 * Class: VerboseFileCreator
 * IDE: NetBeans 6.9.1
 */
public abstract class VerboseFileCreator {

    public enum CssWarningClasses {

        NONE("none"),
        NO_APOLLO_PARAMETER_CLASS_NAME("no-apollo-param"),
        NO_1_TO_1_MAPPING_CLASS_NAME("no-one-to-one-mapping"),
        NO_NATIVE_PARAM_CLASS_NAME("no-native-param");
        private String value;

        CssWarningClasses(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public String getValue() {
            return value;
        }

        public static CssWarningClasses getEnum(String value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
            for (CssWarningClasses v : values()) {
                if (value.equalsIgnoreCase(v.getValue())) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }

        public static String getDescription(CssWarningClasses cssClass, int num, String simulatorId) throws ApolloTranslatorException {

            String result = "";
            switch (cssClass) {
                case NO_1_TO_1_MAPPING_CLASS_NAME:
                    result = "Apollo parameters did not have a one-to-one mapping to a native parameter.";
                    break;
                case NO_APOLLO_PARAMETER_CLASS_NAME:
                    result = "# There were " + num + " " + simulatorId + " statements that could not be expressed in the " + APOLLO_VERSION
                            + " simulator configuration";
                    break;
                case NO_NATIVE_PARAM_CLASS_NAME:
                    result = "# There were " + num + " " + APOLLO_VERSION + " parameters that could not be expressed in the " + simulatorId
                            + " configuration";
                    break;
                default:
                    throw new ApolloTranslatorException("Unsupported CssWarningClasses value");

            }

            return result;
        }
    }

    public enum CssKeyValueClasses {

        NONE("none"),
        NATIVE_PARAM("native-param");
        private String value;

        CssKeyValueClasses(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public String getValue() {
            return value;
        }

        public static CssKeyValueClasses getEnum(String value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
            for (CssKeyValueClasses v : values()) {
                if (value.equalsIgnoreCase(v.getValue())) {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }
    }
    public static final String VERBOSE_FILE_NAME = "verbose.html"; // this is the file name that will be stored in the Apollo database. 
    //It is the same for all simulators.
    private static final String EXPLANATION_LABEL = "Explanation: ";
    private static final String TRANSLATION_REPLACE_INDICATOR = "TRANSLATION_TEXT";
    private static final String SIMULATOR_ID_REPLACE_INDICATOR = "SIMULATOR_ID";
    private static final String TRANSLATION_REPORT_REPLACE_INDICATOR = "TRANSLATION_REPORT";
    private static final String SIMULATOR_WORD_SEPERATOR = "_";
    private static final String SIMULATOR_VERSION_INDICATIOR = "ver.";
    public static final String UNUSED_SECTION = "Ignored Parameters";
    private final String SIMULATOR_NAME;
    private final String SIMULATOR_NAME_ABBREVIATION;
    private final String SIMULATOR_VERSION;
    private final String LINE_PREFIX;
//    private final String FILE_PATH;
    private StringBuilder stBuilder;
    private Map<String, StringBuilder> sectionMap = new LinkedHashMap<String, StringBuilder>();
    private String htmlText;
    protected static final String APOLLO_VERSION = "Apollo 3.0.2";
    protected static final CssWarningClasses GLOBAL_COMMENT_CLASS = CssWarningClasses.NO_1_TO_1_MAPPING_CLASS_NAME;
    protected static final CssWarningClasses HTML_COMMENT_CLASS = CssWarningClasses.NO_1_TO_1_MAPPING_CLASS_NAME;
    protected static final String NEW_LINE = "\r\n";
    protected static final String HTML_NEW_LINE = "<br>";
    protected static final String HTML_LESS_THAN = "&lt;";
    protected static final String HTML_GREATER_THAN = "&gt;";
    protected static final String HTML_DOUBLE_QUOTE = "&quot;";
    protected static final String HTML_SPACE = "&nbsp;";
    protected static final String CSS_INDENT_CLASS = "indent";
    protected Set<String> printedApolloParamAndValStrings;

    public VerboseFileCreator(String simulatorName, String simulatorVersion,
                              String linePrefix) throws FileNotFoundException,
            ApolloTranslatorException {

        this.SIMULATOR_NAME = simulatorName;
        this.SIMULATOR_VERSION = SIMULATOR_VERSION_INDICATIOR + " " + simulatorVersion;
        this.SIMULATOR_NAME_ABBREVIATION = setSimulatorNameAbbreviation();
        this.LINE_PREFIX = linePrefix;
//        this.FILE_PATH = filePath;
        this.htmlText = readHtmlTemplateFile();
        this.stBuilder = new StringBuilder();

        printedApolloParamAndValStrings = new HashSet<String>();
    }

    private String setSimulatorNameAbbreviation() {
        // if the simulator ID contains more than one word, we want to abbreviate it

        if (SIMULATOR_NAME.contains(SIMULATOR_WORD_SEPERATOR)) {
            String[] simIdWords = SIMULATOR_NAME.split(SIMULATOR_WORD_SEPERATOR);
            String simIdAbbrev = "";
            for (String word : simIdWords) {
                simIdAbbrev += word.toUpperCase().charAt(0);
            }

            return simIdAbbrev;
        } else {
            return SIMULATOR_NAME;
        }

    }

    private String readHtmlTemplateFile() throws FileNotFoundException,
            ApolloTranslatorException {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("translation_output_template.html");
        Scanner scanner = new Scanner(is);

        StringBuilder stBuild = new StringBuilder();
        while (scanner.hasNextLine()) {
            stBuild.append(scanner.nextLine()).append(NEW_LINE);
        }

        scanner.close();
        String htmlTemplateFileText = stBuild.toString();

        return htmlTemplateFileText;
    }

    private StringBuilder getStringBuilder(String section) {

        StringBuilder sectionBuilder = sectionMap.get(section);
        if (sectionBuilder == null) {
            sectionBuilder = new StringBuilder();
            sectionMap.put(section, sectionBuilder);
        }

        return sectionBuilder;
    }

    private VerboseFileCreator addText(String text, StringBuilder stringBuilder) {
        stringBuilder.append(text);
        return this;
    }

    protected VerboseFileCreator addText(String text) {

        return addText(text, stBuilder);
    }

    protected VerboseFileCreator addText(String text, String section) {

        return addText(text, getStringBuilder(section));
    }

    private VerboseFileCreator addCommentText(String text, StringBuilder stringBuilder) {
        stringBuilder.append(LINE_PREFIX).append(" ").append(text);
        return this;
    }

    protected VerboseFileCreator addCommentText(String text) {

        return addCommentText(text, stBuilder);
    }

    protected VerboseFileCreator addCommentText(String text, String section) {

        return addCommentText(text, getStringBuilder(section));
    }

    private VerboseFileCreator addExplanationLine(String text, CssWarningClasses cssWarningClass, StringBuilder stringBuilder) {

        if (text == null || text.trim().isEmpty() || cssWarningClass == null) {
            return this;
        }

        stringBuilder.append("<span class=\"warning ").append(cssWarningClass.getValue()).append("\">").append(LINE_PREFIX).append(HTML_SPACE + EXPLANATION_LABEL).append(text).append("</span>").append(HTML_NEW_LINE);
        return this;
    }

    protected VerboseFileCreator addExplanationLine(String text, CssWarningClasses cssWarningClass) {

        return addExplanationLine(text, cssWarningClass, stBuilder);
    }

    protected VerboseFileCreator addExplanationLine(String text, String section, CssWarningClasses cssWarningClass) {

        return addExplanationLine(text, cssWarningClass, getStringBuilder(section));
    }

    protected void prepareHtmlTextForPrinting() {

        String translationText;
        if (sectionMap.size() > 0) {
            translationText = "";
            Iterator<String> it = sectionMap.keySet().iterator();
            while (it.hasNext()) {
                String sectionName = it.next();
                String sectionContents = sectionMap.get(sectionName).toString();
                translationText += LINE_PREFIX + "     " + sectionName + " SECTION" + HTML_NEW_LINE;
                translationText += sectionContents + HTML_NEW_LINE;
            }
        } else {
            translationText = stBuilder.toString();
        }
        htmlText = htmlText.replace(TRANSLATION_REPLACE_INDICATOR,
                translationText);

        if (SIMULATOR_NAME == null ? SIMULATOR_NAME_ABBREVIATION != null : !SIMULATOR_NAME.equals(SIMULATOR_NAME_ABBREVIATION)) {
            htmlText = htmlText.replaceFirst(SIMULATOR_ID_REPLACE_INDICATOR, SIMULATOR_NAME + " (" + SIMULATOR_NAME_ABBREVIATION + ") " + SIMULATOR_VERSION);
        } else {
            htmlText = htmlText.replaceFirst(SIMULATOR_ID_REPLACE_INDICATOR, SIMULATOR_NAME + " " + SIMULATOR_VERSION);
        }
        htmlText = htmlText.replaceAll(SIMULATOR_ID_REPLACE_INDICATOR,
                SIMULATOR_NAME_ABBREVIATION + " " + SIMULATOR_VERSION);
    }

    protected ByteArrayOutputStream printHtmlText() throws FileNotFoundException {

        byte[] textBytes = htmlText.getBytes();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(textBytes.length);
        baos.write(textBytes, 0, textBytes.length);
        return baos;
    }

    public abstract ByteArrayOutputStream createVerboseFile() throws FileNotFoundException, ApolloTranslatorException, IOException;
}

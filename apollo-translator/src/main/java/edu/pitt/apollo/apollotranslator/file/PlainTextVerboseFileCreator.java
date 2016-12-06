package edu.pitt.apollo.apollotranslator.file;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.setters.AbstractSetter;
import edu.pitt.apollo.apollotranslator.types.translator.PlainTextTranslationReturnObject;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 18, 2013
 * Time: 11:26:46 AM
 * Class: PlainTextVerboseFileCreator
 * IDE: NetBeans 6.9.1
 */
public class PlainTextVerboseFileCreator extends VerboseFileCreator {

    private static final String NATIVE_PARAM_SPAN_START = "<span class=\"" + CssKeyValueClasses.NATIVE_PARAM + "\">";
    private static final String SPAN_END = "</span>";
    private Map<String, List<SetterReturnObject>> apolloLabelSetterReturnObjectMap;

    public PlainTextVerboseFileCreator(String simulatorName, String simulatorVersion,
                                       String linePrefix, Map<String, List<SetterReturnObject>> apolloLabelSetterReturnObjectMap) throws FileNotFoundException, ApolloTranslatorException {
        super(simulatorName, simulatorVersion, linePrefix);
        this.apolloLabelSetterReturnObjectMap = apolloLabelSetterReturnObjectMap;
    }

    private void addTextForSetterReturnObject(SetterReturnObject setterReturnObject, boolean printExtraNewLine) {
        String apolloParamAndValAsString = setterReturnObject.getApolloParamAndValAsString();
        String globalComment = setterReturnObject.getGlobalComment();
        String htmlFileComment = setterReturnObject.getHtmlFileComment();
        String sectionName = setterReturnObject.getSection();
        String htmlFileWarning = setterReturnObject.getHtmlFileWarning();
        CssWarningClasses cssWarningClass = setterReturnObject.getCssWarningClass();

        PlainTextTranslationReturnObject tro = (PlainTextTranslationReturnObject) setterReturnObject.getTranslationReturnObject();

        // first, print the apolloParamAndValString for the SRO
        // e.g. #infectiousDisease.reproductionNumber = 1.3
        if (apolloParamAndValAsString != null
                && !apolloParamAndValAsString.trim().isEmpty()
                && !printedApolloParamAndValStrings.contains(apolloParamAndValAsString)) {
            addCommentText(apolloParamAndValAsString + HTML_NEW_LINE, sectionName);
            printedApolloParamAndValStrings.add(apolloParamAndValAsString);

            List<SetterReturnObject> subObjects = setterReturnObject.getSubApolloParameters();
            // This will print all of the Apollo list object properties inbetween
            // the list param itself and the native param(s) it is mapped to
            // This should never need an extra new line
            if (subObjects != null && subObjects.size() > 0) {
                for (SetterReturnObject object : subObjects) {
                    addTextForSetterReturnObject(object, false);
                }
            }
        }

        if (globalComment
                != null && !globalComment.trim().isEmpty()) {
            addExplanationLine(globalComment, sectionName, GLOBAL_COMMENT_CLASS);
        }

        if (htmlFileComment
                != null && !htmlFileComment.trim().isEmpty()) {
            addExplanationLine(htmlFileComment, sectionName, HTML_COMMENT_CLASS);
        }
        // If there are any HTML file warnings, display them AFTER the
        // apolloParamAndValAsStr AND mappingComments

        if (htmlFileWarning
                != null && !htmlFileWarning.trim().isEmpty()) {
            addExplanationLine(htmlFileWarning, sectionName, cssWarningClass);
        }

        if (tro != null) {
            // now lets print the warnings that were returned by the
            // javascript...
            List<String> nativeWarnings = tro.getWarningsFromJavascriptFunction();
            if (nativeWarnings != null) {
                for (String nativeWarning : nativeWarnings) {
                    if (nativeWarning != null
                            && !nativeWarning.trim().isEmpty()) {

                        CssWarningClasses cssClass = CssWarningClasses.NONE;
                        if (!nativeWarning.startsWith("#")) {
                            String[] array = nativeWarning.split("#");
                            cssClass = CssWarningClasses.getEnum(array[0].trim());

                            nativeWarning = array[1];
                        }

                        addExplanationLine(nativeWarning, sectionName, cssClass);
                    }
                }
            }
            // now lets print the comments that were returned by the
            // javascript...

            String nativeComment = tro.getCommentFromJavascriptFunction();
            if (nativeComment != null && !nativeComment.trim().isEmpty()) {
                super.addCommentText(nativeComment + HTML_NEW_LINE, sectionName);
            }

            // now we print the value returned by the javascript
            if (!tro.getValueFromJavascriptFunction().isEmpty()) {
                // only print the parameter if there was a value returned by the javascript function

                String parameterString = tro.getNativeTerm() + tro.getDelimiter()
                        + tro.getValueFromJavascriptFunction();

                if (parameterString != null && !parameterString.trim().isEmpty()) {
                    // if the parameter string contains any new lines, we should
                    // replace them with <br>
                    parameterString = parameterString.replaceAll("(\r\n|\n)",
                            "<br>");
                    addText(NATIVE_PARAM_SPAN_START + parameterString + SPAN_END + HTML_NEW_LINE, sectionName);
                    if (printExtraNewLine) {
                        addText(HTML_NEW_LINE, sectionName);
                    }
                }
            }
        }
    }

    private void addLinesForApolloLabel(List<SetterReturnObject> setterReturnObjects) {
        // this should be fixed in the future
        // make some object to hold the apollo param, param and val string, and the section
        // then use those objects as keys in the map in the simulator configuration parser
        String apolloParamAndValAsString = setterReturnObjects.get(0).getApolloParamAndValAsString();
        String sectionName = setterReturnObjects.get(0).getSection();

//        if (apolloParamAndValAsString != null
//                && !apolloParamAndValAsString.trim().isEmpty()) {
//            addCommentText(apolloParamAndValAsString + HTML_NEW_LINE, sectionName);
//            printedApolloParamAndValStrings.add(apolloParamAndValAsString);
//        }

        for (int i = 0; i < setterReturnObjects.size(); i++) {
            SetterReturnObject setterReturnObject = setterReturnObjects.get(i);
            boolean printExtraNewLine = false;
            if (i == setterReturnObjects.size() - 1 || sectionName.equals(AbstractSetter.UNSPECIFIED_PARAM_SECTION)) {
                // print an extra new line for the last native term
                printExtraNewLine = true;
            }

            addTextForSetterReturnObject(setterReturnObject, printExtraNewLine);
        }
    }

    @Override
    public ByteArrayOutputStream createVerboseFile() throws FileNotFoundException, ApolloTranslatorException, IOException {
        for (String apolloParam : apolloLabelSetterReturnObjectMap.keySet()) {
            addLinesForApolloLabel(apolloLabelSetterReturnObjectMap.get(apolloParam));
        }

        prepareHtmlTextForPrinting();
        return printHtmlText();
    }
}

package edu.pitt.apollo.apollotranslator.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.pitt.apollo.apollotranslator.RecursiveSroCrawler;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.setters.AbstractSetter;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.apollotranslator.types.translator.TranslatorAttribute;
import edu.pitt.apollo.apollotranslator.types.translator.TranslatorElement;
import org.jdom2.Attribute;
import org.jdom2.Element;

import java.io.ByteArrayOutputStream;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 29, 2013
 * Time: 12:46:02 PM
 * Class: XMLVerboseFileCreator
 * IDE: NetBeans 6.9.1
 */
public class XMLVerboseFileCreator extends VerboseFileCreator {

    private static final String INDENTED_DIV_START = "<div class=\"" + CSS_INDENT_CLASS + "\">";
    private static final String DIV_END = "</div>";
    private static final String NATIVE_XML_SPAN_START = "<span class=\"" + CssKeyValueClasses.NATIVE_PARAM + "\">";
    private static final String SPAN_END = "</span>";
    private List<SetterReturnObject> setterReturnObjects;

    public XMLVerboseFileCreator(String simulatorName, String simulatorVersion,
                                 String linePrefix, Map<String, List<SetterReturnObject>> apolloLabelSetterReturnObjectMap) throws FileNotFoundException, ApolloTranslatorException {
        super(simulatorName, simulatorVersion, linePrefix);

        RecursiveSroCrawler crawler = new RecursiveSroCrawler();

        for (int i = 0; i < apolloLabelSetterReturnObjectMap.size(); i++) {
            List<SetterReturnObject> list = apolloLabelSetterReturnObjectMap.get(i);
            for (int j = 0; j < list.size(); j++) {
                crawler.addToList(list.get(j));
            }
        }

        this.setterReturnObjects = crawler.getSroList();
    }

    private void addTextForSetterReturnObject(SetterReturnObject setterReturnObject, boolean checkIfPrinted) {

        String apolloParamAndVal = setterReturnObject.getApolloParamAndValAsString();

        // for apollo param and val strings for elements, we want to always print
        // but for unused apollo params we only want to print once
        boolean lineAlreadyPrinted = printedApolloParamAndValStrings.contains(apolloParamAndVal);

        if (apolloParamAndVal != null) {
            if (checkIfPrinted && !lineAlreadyPrinted
                    || !checkIfPrinted) {

                if (setterReturnObject.getApolloParamAndValAsString().contains(AbstractSetter.LIST_VALUES_LABEL)
                        && checkAllSubApolloParametersPrinted(setterReturnObject)) {
                    // don't print the object list label (i.e. "list objects described below") when all sub apollo objects
                    // have been printed elsewhere

                    return;
                }

                addCommentText(apolloParamAndVal).addText(HTML_NEW_LINE);
                printedApolloParamAndValStrings.add(apolloParamAndVal);
            }

            List<SetterReturnObject> listSetterReturnObjects = setterReturnObject.getSubApolloParameters();
            if (listSetterReturnObjects != null && listSetterReturnObjects.size() > 0) {
                for (SetterReturnObject subSetterReturnObject : listSetterReturnObjects) {
                    addTextForSetterReturnObject(subSetterReturnObject, checkIfPrinted);
                }
            }

            if (checkIfPrinted && !lineAlreadyPrinted
                    || !checkIfPrinted) {
                // check this again but we don't need to add the apollo param and val again

                String htmlWarning = setterReturnObject.getHtmlFileWarning();
                CssWarningClasses cssWarningClass = setterReturnObject.getCssWarningClass();
                addExplanationLine(htmlWarning, cssWarningClass);
            }
        }
    }

    private boolean checkAllSubApolloParametersPrinted(SetterReturnObject setterReturnObject) {

        List<SetterReturnObject> subObjects = setterReturnObject.getSubApolloParameters();
        if (subObjects != null && subObjects.size() > 0) {

            for (SetterReturnObject subObject : subObjects) {
                if (!printedApolloParamAndValStrings.contains(subObject.getApolloParamAndValAsString())) {
                    return false;
                }

                boolean check = checkAllSubApolloParametersPrinted(subObject);
                if (!check) {
                    return false;
                }
            }

        }

        return true;
    }

    private void addTextForElement(Element element, boolean indentElement) {

        // process the element text and attributes
        String elementName = element.getName();
        String elementText = element.getText();
        List<Attribute> attributes = element.getAttributes();

        if (indentElement) {
            addText(INDENTED_DIV_START);
        }

        if (element instanceof TranslatorElement) {
            TranslatorElement translatorElement = (TranslatorElement) element;
            if (translatorElement.getSetterReturnObjects() != null
                    && translatorElement.getSetterReturnObjects().size() > 0) {
                for (SetterReturnObject setterReturnObject : translatorElement.getSetterReturnObjects()) {
                    addTextForSetterReturnObject(setterReturnObject, false);
                }
            }
        }

        addText(NATIVE_XML_SPAN_START).addText(HTML_LESS_THAN).addText(elementName).addText(SPAN_END);

        if (attributes != null && attributes.size() > 0) {

            for (Attribute attribute : attributes) {
                addText(INDENTED_DIV_START);

                if (attribute instanceof TranslatorAttribute) {
                    TranslatorAttribute translatorAttribute = (TranslatorAttribute) attribute;
                    if (translatorAttribute.getSetterReturnObject() != null) {
                        addTextForSetterReturnObject(translatorAttribute.getSetterReturnObject(), false);
                    }
                }

                addText(NATIVE_XML_SPAN_START);
                addText(attribute.getName()).addText("=").addText(HTML_DOUBLE_QUOTE).addText(attribute.getValue()).addText(HTML_DOUBLE_QUOTE);
                addText(SPAN_END).addText(DIV_END);
            }
        }

        addText(NATIVE_XML_SPAN_START).addText(HTML_GREATER_THAN).addText(SPAN_END);
        if (elementText != null && !elementText.isEmpty()) {
            // note that this is mutually exclusive with child elements existing
            addText(NATIVE_XML_SPAN_START);
            addText(elementText).addText(HTML_NEW_LINE).addText(HTML_LESS_THAN).addText("/").addText(elementName).addText(HTML_GREATER_THAN);
            addText(SPAN_END);
            if (indentElement) {
                addText(DIV_END);
            }
        }

        if (elementText == null || elementText.isEmpty()) {
            // for now only recurse if there is no element text
            List<Element> childElements = element.getChildren();
            if (childElements != null && childElements.size() > 0) {
                for (Element childElement : childElements) {
                    addTextForElement(childElement, true);
                }
            }

            addText(NATIVE_XML_SPAN_START).addText(HTML_LESS_THAN).addText("/").addText(elementName).addText(HTML_GREATER_THAN);
            addText(SPAN_END);
            if (indentElement) {
                addText(DIV_END);
            }
        }
    }

    private void addTextForUnusedSetterReturnObjects(List<SetterReturnObject> setterReturnObjectList) {

        addText(HTML_NEW_LINE);
        addText(HTML_NEW_LINE + "===== NON-TRANSLATED SCENARIO SETTINGS =====" + HTML_NEW_LINE);
        for (SetterReturnObject setterReturnObject : setterReturnObjectList) {

            String apolloParamAndValString = setterReturnObject.getApolloParamAndValAsString();

            if (!printedApolloParamAndValStrings.contains(apolloParamAndValString)) {
                addTextForSetterReturnObject(setterReturnObject, true);
            }
        }
    }

    @Override
    public ByteArrayOutputStream createVerboseFile() throws FileNotFoundException, ApolloTranslatorException, IOException {

        // for now assume that we only have one file and it is built
        Map<String, XMLFileBuilder> xmlFileBuilderMap = XMLMultiFileBuilder.buildMultipleXMLFiles(setterReturnObjects, "");
        XMLFileBuilder xmlFileBuilder = null;
        for (String string : xmlFileBuilderMap.keySet()) {
            xmlFileBuilder = xmlFileBuilderMap.get(string);
        }

        Element rootElement = xmlFileBuilder.getDocument().getRootElement();
        addTextForElement(rootElement, false);
        addTextForUnusedSetterReturnObjects(setterReturnObjects);

        prepareHtmlTextForPrinting();
        printHtmlText();
        
        return null; // NOT USING XML FOR NOW
    }
}

package edu.pitt.apollo.apollotranslator.file;

import java.io.IOException;
import java.util.Arrays;

import edu.pitt.apollo.apollotranslator.XMLUtility;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.apollotranslator.types.translator.TranslatorAttribute;
import edu.pitt.apollo.apollotranslator.types.translator.TranslatorElement;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 23, 2013
 * Time: 2:58:19 PM
 * Class: XMLFileBuilder
 * IDE: NetBeans 6.9.1
 */
public class XMLFileBuilder extends AbstractMarkupFileBuilder {

    private static final String XML_ELEMENT_CLASS = "edu.pitt.apollo.apollotranslator.types.translator.TranslatorElement";
    private static final String XML_ATTRIBUTE_CLASS = "edu.pitt.apollo.apollotranslator.types.translator.TranslatorAttribute";
    private Document document;
    private Element rootNode;
    private XMLUtility xmlUtility;

    public XMLFileBuilder(String rootElement) {
        super(rootElement);

        rootNode = new Element(rootElement);
        document = new Document(rootNode);
        document.setRootElement(rootNode);

        xmlUtility = new XMLUtility(XML_ELEMENT_CLASS, XML_ATTRIBUTE_CLASS);
    }

    @Override
    public void setAttribute(String elementPath, String attributeName, String attributeValue,
            SetterReturnObject elementSetterReturnObject, SetterReturnObject attrbiuteSetterReturnObject) throws ApolloTranslatorException {
        
        String[] elements = elementPath.split(XMLUtility.ELEMENT_SEPERATOR);
        TranslatorElement matchingElement = (TranslatorElement) xmlUtility.searchXMLForElement(rootNode, 0, elements, true);

        if (matchingElement != null) {
            TranslatorAttribute attribute = new TranslatorAttribute(attributeName, attributeValue);
            attribute.setSetterReturnObject(attrbiuteSetterReturnObject);
            matchingElement.setAttribute(attribute);
        } else {
            throw new ApolloTranslatorException("Could not add attribute for elelement list " + Arrays.toString(elements));
        }
    }

    @Override
    public void setComment(String elementPath, String comment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setText(String elementPath, String text, SetterReturnObject elementSetterReturnObject) throws IOException, ApolloTranslatorException {
        String[] elements = elementPath.split(XMLUtility.ELEMENT_SEPERATOR);
        // note that individual tokens in the elements list may contain selectors

        // this will add the text to the element, creating any additional elements as neccesary
        TranslatorElement element = (TranslatorElement) xmlUtility.addElementText(rootNode, elements, text);
        element.setSetterReturnObject(elementSetterReturnObject);
    }

    @Override
    public void setChild(String elementPath, String xmlString, SetterReturnObject elementSetterReturnObject) throws IOException, ApolloTranslatorException {
        String[] elements = elementPath.split(XMLUtility.ELEMENT_SEPERATOR);
        // note that individual tokens in the elements list may contain selectors

        // this will add the text to the element, creating any additional elements as neccesary
        try {
            TranslatorElement element = (TranslatorElement) xmlUtility.addElementChilds(rootNode, elements, xmlString);
            element.setSetterReturnObject(elementSetterReturnObject);
        } catch (JDOMException ex) {
            throw new ApolloTranslatorException("JDOMException attempting to write xml string \"" + xmlString
                    + "\". Exception was: " + ex.getMessage());
        }
    }

    @Override
    public void setElement(String elementPath, SetterReturnObject elementSetterReturnObject) throws IOException, ApolloTranslatorException {
        String[] elements = elementPath.split(XMLUtility.ELEMENT_SEPERATOR);

        TranslatorElement element = (TranslatorElement) xmlUtility.addElement(rootNode, elements);
        element.setSetterReturnObject(elementSetterReturnObject);
    }

    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }
}

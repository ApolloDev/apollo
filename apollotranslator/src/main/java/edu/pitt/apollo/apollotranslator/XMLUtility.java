package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.instructions.SelectorUtility;
import edu.pitt.apollo.apollotranslator.types.translator.SelectorResult;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 23, 2013
 * Time: 3:09:34 PM
 * Class: XMLUtility
 * IDE: NetBeans 6.9.1
 */
public class XMLUtility {

    public static final String ELEMENT_SEPERATOR = "\\.";
    private final String XML_ELEMENT_CLASS;
    private final String XML_ATTRIBUTE_CLASS;

    public XMLUtility(String xmlElementClass, String xmlAttributeClass) {
        this.XML_ELEMENT_CLASS = xmlElementClass;
        this.XML_ATTRIBUTE_CLASS = xmlAttributeClass;
    }

    private Element createNewElement(String name) throws ApolloTranslatorException {
        try {
            Constructor conctructor = Class.forName(XML_ELEMENT_CLASS).getConstructor(String.class);
            Element element = (Element) conctructor.newInstance(name);

            return element;
        } catch (ClassNotFoundException ex) {
            throw new ApolloTranslatorException("ClassNotFoundException attempting to create XML element from class "
                    + XML_ELEMENT_CLASS + ": " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new ApolloTranslatorException("IllegalAccessException attempting to create XML element from class "
                    + XML_ELEMENT_CLASS + ": " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ApolloTranslatorException("IllegalArgumentException attempting to create XML element from class "
                    + XML_ELEMENT_CLASS + ": " + ex.getMessage());
        } catch (InstantiationException ex) {
            throw new ApolloTranslatorException("InstantiationException attempting to create XML element from class "
                    + XML_ELEMENT_CLASS + ": " + ex.getMessage());
        } catch (InvocationTargetException ex) {
            throw new ApolloTranslatorException("InvocationTargetException attempting to create XML element from class "
                    + XML_ELEMENT_CLASS + ": " + ex.getMessage());
        } catch (NoSuchMethodException ex) {
            throw new ApolloTranslatorException("NoSuchMethodException attempting to create XML element from class "
                    + XML_ELEMENT_CLASS + ": " + ex.getMessage());
        } catch (SecurityException ex) {
            throw new ApolloTranslatorException("SecurityExcepion attempting to create XML element from class "
                    + XML_ELEMENT_CLASS + ": " + ex.getMessage());
        }
    }
    
//    private Attribute createNewAttribute(String attrName, String attrValue) throws ApolloTranslatorException {
//               try {
//            Constructor conctructor = Class.forName(XML_ATTRIBUTE_CLASS).getConstructor(String.class, String.class);
//            Attribute attribute = (Attribute) conctructor.newInstance(attrName, attrValue);
//
//            return attribute;
//        } catch (ClassNotFoundException ex) {
//            throw new ApolloTranslatorException("ClassNotFoundException attempting to create XML attribute from class "
//                    + XML_ATTRIBUTE_CLASS + ": " + ex.getMessage());
//        } catch (IllegalAccessException ex) {
//            throw new ApolloTranslatorException("IllegalAccessException attempting to create XML attribute from class "
//                    + XML_ATTRIBUTE_CLASS + ": " + ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            throw new ApolloTranslatorException("IllegalArgumentException attempting to create XML attribute from class "
//                    + XML_ATTRIBUTE_CLASS + ": " + ex.getMessage());
//        } catch (InstantiationException ex) {
//            throw new ApolloTranslatorException("InstantiationException attempting to create XML attribute from class "
//                    + XML_ATTRIBUTE_CLASS + ": " + ex.getMessage());
//        } catch (InvocationTargetException ex) {
//            throw new ApolloTranslatorException("InvocationTargetException attempting to create XML attribute from class "
//                    + XML_ATTRIBUTE_CLASS + ": " + ex.getMessage());
//        } catch (NoSuchMethodException ex) {
//            throw new ApolloTranslatorException("NoSuchMethodException attempting to create XML attribute from class "
//                    + XML_ATTRIBUTE_CLASS + ": " + ex.getMessage());
//        } catch (SecurityException ex) {
//            throw new ApolloTranslatorException("SecurityExcepion attempting to create XML attribute from class "
//                    + XML_ATTRIBUTE_CLASS + ": " + ex.getMessage());
//        }
//    }

    public Element addElementText(Element startingNode, String[] tokenList, String text) throws ApolloTranslatorException {

        Element matchingElement = searchXMLForElement(startingNode, 0, tokenList, true);

        if (matchingElement != null) {
            matchingElement.setText(text);
        } else {
            throw new ApolloTranslatorException("Could not add text for elelement list " + Arrays.toString(tokenList));
        }

        return matchingElement;
    }

//    public Element addElementAttribute(Element startingNode, String[] tokenList, String attribute, String value) throws ApolloTranslatorException {
//
//        Element matchingElement = searchXMLForElement(startingNode, 0, tokenList, true);
//
//        if (matchingElement != null) {
//            matchingElement.setAttribute(new Attribute(attribute, value));
//        } else {
//            throw new ApolloTranslatorException("Could not add attribute for elelement list " + Arrays.toString(tokenList));
//        }
//
//        return matchingElement;
//    }

    public Element addElementChilds(Element startingNode, String[] tokenList, String xmlString) throws JDOMException,
            IOException, ApolloTranslatorException {

        // add a temporary root to the XML string so that we can get the original XML
        // as children of the temporary root
        xmlString = "<root>" + xmlString + "</root>";

        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new StringReader(xmlString));
        Element docRoot = doc.getRootElement();
        List children = docRoot.getChildren();
        List<Element> newChildren = new ArrayList<Element>();

        while (children.size() > 0) {
            Element child = (Element) children.get(0);
            child.detach(); // this removes the child from the children list
            newChildren.add(child);
        }

        Element matchingElement = searchXMLForElement(startingNode, 0, tokenList, true);

        if (matchingElement != null) {
            matchingElement.setContent(newChildren);
        } else {
            throw new ApolloTranslatorException("Could not add child elements for element list " + Arrays.toString(tokenList));
        }

        return matchingElement;
    }

    public Element addElement(Element startingNode, String[] tokenList) throws ApolloTranslatorException {

        return searchXMLForElement(startingNode, 0, tokenList, true);
    }

    public Element searchXMLForElement(Element currentNode, int currentTokenIndex, String[] tokenList,
            boolean create) throws ApolloTranslatorException {
        // this will search over the nodes and create elements as neccesary
        String currentToken = tokenList[currentTokenIndex];
        int newTokenIndex = currentTokenIndex + 1;

        Element matchingElement;
        // check if element has a selector
        SelectorResult selectorResult = SelectorUtility.getSelectorResult(currentToken);
        if (selectorResult != null) { // then a selector was used

            String elementName = selectorResult.getObjectOrFieldType();
            Map<String, String> options = selectorResult.getSelectorOptions();

            List elements = currentNode.getChildren(elementName);

            if (elements == null || elements.isEmpty()) {
                // need to create the element with all selector options
                if (create) {
                    matchingElement = createNewElement(elementName);
                    for (String option : options.keySet()) {
                        matchingElement.setAttribute(new Attribute(option, options.get(option)));
                    }
                    currentNode.addContent(matchingElement);
                } else {
                    return null;
                }
            } else {
                List<Element> matchedElements = new ArrayList<Element>();
                // use the options to find the element
                elementLoop:
                for (int i = 0; i < elements.size(); i++) {
                    Element element = (Element) elements.get(i);
                    for (String option : options.keySet()) {
                        String optionValue = element.getAttributeValue(option);
                        if (optionValue == null) {
                            // this element is not a match
                            continue elementLoop;
                        } else {
                            if (!optionValue.equals(options.get(option))) {
                                continue elementLoop;
                            }
                        }
                    }

                    // if here then the element was a match
                    matchedElements.add(element);
                }

                if (matchedElements.isEmpty()) {
                    // need to create the element with all selector options
                    if (create) {
                        matchingElement = createNewElement(elementName);
                        for (String option : options.keySet()) {
                            matchingElement.setAttribute(new Attribute(option, options.get(option)));
                        }
                        currentNode.addContent(matchingElement);
                    } else {
                        return null;
                    }
                } else if (matchedElements.size() > 1) {
                    throw new ApolloTranslatorException("A selector was used for element " + elementName + " but more than one element"
                            + " matched the selector");
                } else {
                    // only one element was found
                    matchingElement = matchedElements.get(0);
                }
            }

        } else {
            // no selector was used
            List elements = currentNode.getChildren(currentToken);
            if (elements == null || elements.isEmpty()) {

                // this means we need to create the element
                if (create) {
                    matchingElement = createNewElement(currentToken);
                    currentNode.addContent(matchingElement);
                } else {
                    return null;
                }
            } else {
                if (elements.size() > 1) {
                    throw new ApolloTranslatorException("The element " + currentToken + " was specified without a selector and more than"
                            + " one of the elements was found using the element path");
                }

                matchingElement = (Element) elements.get(0);
            }
        }

        if (currentTokenIndex == tokenList.length - 1) {
            // this is the final element we want
            return matchingElement;
        } else {
            // need to keep going
            return searchXMLForElement(matchingElement, newTokenIndex, tokenList, create);
        }
    }
}

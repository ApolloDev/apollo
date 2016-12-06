package edu.pitt.apollo.apollotranslator.file;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 29, 2013
 * Time: 3:14:15 PM
 * Class: XMLMultiFileBuilder
 * IDE: NetBeans 6.9.1
 */
public class XMLMultiFileBuilder {

    public static Map<String, XMLFileBuilder> buildMultipleXMLFiles(List<SetterReturnObject> setterReturnObjects,
            String outputDirectory) throws ApolloTranslatorException, IOException {
//        Map<String, XMLFileBuilder> fileXmlFileBuilderMap = new HashMap<String, XMLFileBuilder>();
//        for (SetterReturnObject sro : setterReturnObjects) {
//
//            XMLTranslationReturnObject tro = (XMLTranslationReturnObject) sro.getTranslationReturnObject();
//
//            if (tro == null) {
//                // note that for XML if the value from the JavaScript function is empty
//                // we still want to print the native term. This allows for element attributes
//                // which look like <element attr=""/>
//
//                // we also want to print the element if NO native value is present
//                // This allows for printing elements like <element/>
//                continue;
//            }
//
//            if (tro.getXmlRootNodeName() == null || tro.getXmlRootNodeName().isEmpty()) {
//                // the root element name is required for all instructions
//                throw new ApolloTranslatorException("The xmlRootNodeName for native parameter " + tro.getNativeTerm()
//                        + " is empty or null");
//            }
//
//            if (sro.getConfigurationFile().length() == 0) {
//                // throw new ApolloTranslatorException(
//                // PlainTextTranslationInstructionsFileLoader.CONFIG_FILE_NAME_COLUMN_NAME
//                // + " not specified for native_parameter: "
//                // + sro.getTranslationReturnObject().getNativeTerm());
//                ApolloLogger.log(Level.INFO,
//                        AbstractTranslationInstructionsFileLoader.CONFIG_FILE_NAME_COLUMN_NAME
//                        + " not specified for native_parameter: "
//                        + sro.getTranslationReturnObject().getNativeTerm());
//                continue;
//            }
//
//            String elementPath = tro.getNativeTerm();
//            XMLNativeValueType xmlNativeValueType = tro.getXmlNativeValueType();
//            String xmlNativeValue = tro.getXmlNativeValue();
//            String rootNodeName = tro.getXmlRootNodeName();
//
//            String filePath = outputDirectory + File.separator
//                    + sro.getConfigurationFile();
//            XMLFileBuilder xmlFileBuilder = fileXmlFileBuilderMap.get(filePath);
//            if (xmlFileBuilder == null) {
//                xmlFileBuilder = new XMLFileBuilder(rootNodeName);
//                fileXmlFileBuilderMap.put(filePath, xmlFileBuilder);
//            } else {
//                if (!xmlFileBuilder.getRootElement().equals(rootNodeName)) {
//                    // this means that two instructions for the same output file
//                    // have different root nodes, which is not allowed
//
//                    throw new ApolloTranslatorException("The root element name " + rootNodeName
//                            + " for native term " + elementPath + " has a different root node name than "
//                            + " " + xmlFileBuilder.getRootElement() + ", which was previously set for the same"
//                            + " output file " + sro.getConfigurationFile());
//                }
//            }
//
//            if (xmlNativeValueType.equals(XMLNativeValueType.ATTRIBUTE)) {
//
//                // get the attribute and value from the nativeValue
//                String[] attributeValuePair = xmlNativeValue.split("=");
//                String attribute, value;
//                if (attributeValuePair.length != 2) {
//                    if (attributeValuePair.length > 0) {
//                        attribute = attributeValuePair[0];
//                        if (!attribute.trim().isEmpty() && xmlNativeValue.startsWith(attribute)) {
//                            value = "";
//                        } else {
//                            throw new ApolloTranslatorException("The XML native value type was " + XMLNativeValueType.ATTRIBUTE
//                                    + " but the value " + xmlNativeValue + " does not have the form attribute=value");
//                        }
//                    } else {
//                        throw new ApolloTranslatorException("The XML native value type was " + XMLNativeValueType.ATTRIBUTE
//                                + " but the value " + xmlNativeValue + " does not have the form attribute=value");
//                    }
//                } else {
//                    attribute = attributeValuePair[0];
//                    value = attributeValuePair[1];
//                }
//
//                xmlFileBuilder.setAttribute(elementPath, attribute, value, sro, sro);
//            } else if (xmlNativeValueType.equals(XMLNativeValueType.TEXT)) {
//                // don't need to process xmlNativeValue
//                xmlFileBuilder.setText(elementPath, xmlNativeValue, sro);
//            } else if (xmlNativeValueType.equals(XMLNativeValueType.CHILD)) {
//                // don't need to process xmlNativeValue
//                xmlFileBuilder.setChild(elementPath, xmlNativeValue, sro);
//            } else if (xmlNativeValueType.equals(XMLNativeValueType.NONE)) {
//                xmlFileBuilder.setElement(elementPath, sro);
//            } else {
//                throw new ApolloTranslatorException("unsupported xml native attribute type");
//            }
//        }
//
//        return fileXmlFileBuilderMap;
        return null;
    }
}

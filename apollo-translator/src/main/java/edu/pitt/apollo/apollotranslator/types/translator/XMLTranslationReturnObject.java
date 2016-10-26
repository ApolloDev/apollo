package edu.pitt.apollo.apollotranslator.types.translator;

import edu.pitt.apollo.apollotranslator.types.translator.XMLTranslationInstruction.XMLNativeValueType;

import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 23, 2013
 * Time: 4:03:15 PM
 * Class: XMLTranslationReturnObject
 * IDE: NetBeans 6.9.1
 */
public class XMLTranslationReturnObject extends TranslationReturnObject {

    private XMLNativeValueType xmlNativeValueType;
    private Map<String, XMLXPathValue> xpathsAndValues;
    private Map<String, String> namespaces;

    public XMLTranslationReturnObject() {
    }

    public Map<String, XMLXPathValue> getXpathsAndValues() {
        return xpathsAndValues;
    }

    public void setXpathsAndValues(Map<String, XMLXPathValue> xpathsAndValues) {
        this.xpathsAndValues = xpathsAndValues;
    }

    /**
     * @return the xmlNativeValueType
     */
    public XMLNativeValueType getXmlNativeValueType() {
        return xmlNativeValueType;
    }

    /**
     * @param xmlNativeValueType the xmlNativeValueType to set
     */
    public void setXmlNativeValueType(XMLNativeValueType xmlNativeValueType) {
        this.xmlNativeValueType = xmlNativeValueType;
    }

    public Map<String, String> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(Map<String, String> namespaces) {
        this.namespaces = namespaces;
    }
}

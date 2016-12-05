package edu.pitt.apollo.apollotranslator.types.translator;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2013
 * Time: 4:27:11 PM
 * Class: XMLTranslationInstruction
 * IDE: NetBeans 6.9.1
 */
public class XMLTranslationInstruction extends TranslationInstruction {

    public enum XMLNativeValueType {

        ATTRIBUTE, SINGLE_VALUE, MULTIPLE_VALUES, XML, XML_REQUIRING_XPATH
    }
    private XMLNativeValueType xmlNativeValueType;
    private String xPathString;

    public XMLTranslationInstruction() {
    }

    public XMLTranslationInstruction(TranslationInstruction instruction) {
        // this creates an XMLTranslationInstruction from a TranslationInstruction
        super(instruction);
    }

    public String getxPathString() {
        return xPathString;
    }

    public void setxPathString(String xPathString) {
        this.xPathString = xPathString;
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
}

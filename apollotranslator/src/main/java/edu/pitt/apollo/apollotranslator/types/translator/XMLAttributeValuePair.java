package edu.pitt.apollo.apollotranslator.types.translator;

/**
 * Created by nem41 on 7/27/15.
 */
public class XMLAttributeValuePair extends XMLXPathValue {

    private String attribute;
    private String value;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package edu.pitt.apollo.apollotranslator.types.translator;

import java.util.List;

/**
 * Created by nem41 on 7/27/15.
 */
public class XMLMultipleStringValues extends XMLXPathValue {

    private String elementName;
    private List<String> values;

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

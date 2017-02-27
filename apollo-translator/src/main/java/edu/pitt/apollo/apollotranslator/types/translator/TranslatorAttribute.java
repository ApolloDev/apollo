package edu.pitt.apollo.apollotranslator.types.translator;

import org.jdom2.Attribute;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 29, 2013
 * Time: 2:15:22 PM
 * Class: TranslatorAttribute
 * IDE: NetBeans 6.9.1
 */
public class TranslatorAttribute extends Attribute {
    
    private SetterReturnObject setterReturnObject;
    
    public TranslatorAttribute(String attribute, String value) {
        super(attribute, value);
    }

    /**
     * @return the setterReturnObject
     */
    public SetterReturnObject getSetterReturnObject() {
        return setterReturnObject;
    }

    /**
     * @param setterReturnObject the setterReturnObject to set
     */
    public void setSetterReturnObject(SetterReturnObject setterReturnObject) {
        this.setterReturnObject = setterReturnObject;
    }
    
}

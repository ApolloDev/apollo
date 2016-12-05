package edu.pitt.apollo.apollotranslator.types.translator;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 29, 2013
 * Time: 12:57:40 PM
 * Class: TranslatorElement
 * IDE: NetBeans 6.9.1
 */
public class TranslatorElement extends Element {

//    public TranslatorElement(Element element) {
//        this.setName(element.getName());
//        this.setNamespace(element.getNamespace());
//        this.setAttributes(element.getAttributes());
//        this.setContent(element.getContent());
//        this.additionalNamespaces = element.getAdditionalNamespaces();
//    }
    
    public TranslatorElement(String name) {
        super(name);
    }
    
    private List<SetterReturnObject> setterReturnObjectList;

    /**
     * @return the setterReturnObject
     */
    public List<SetterReturnObject> getSetterReturnObjects() {
        return setterReturnObjectList;
    }

    /**
     * @param setterReturnObject the setterReturnObject to set
     */
    public void setSetterReturnObject(SetterReturnObject setterReturnObject) {
        if (setterReturnObjectList == null) {
            setterReturnObjectList = new ArrayList<SetterReturnObject>();
        }
        setterReturnObjectList.add(setterReturnObject);
    }
    
}

package edu.pitt.apollo.apollotranslator.file;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

import java.io.IOException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 23, 2013
 * Time: 2:54:10 PM
 * Class: AbstractMarkupFileBuilder
 * IDE: NetBeans 6.9.1
 */
public abstract class AbstractMarkupFileBuilder {

    protected String rootElement;

    public AbstractMarkupFileBuilder(String rootElement) {
        this.rootElement = rootElement;
    }

    public String getRootElement() {
        return rootElement;
    }

    public abstract void setText(String elementPath, String text, SetterReturnObject elementSetterReturnObject) throws IOException, ApolloTranslatorException;

    public abstract void setAttribute(String elementPath, String attributeName, String attributeValue,
            SetterReturnObject elementSetterReturnObject, SetterReturnObject attributeSetterReturnObject) throws ApolloTranslatorException;

    public abstract void setChild(String elementPath, String xmlString, SetterReturnObject elementSetterReturnObject) throws IOException, ApolloTranslatorException;

    public abstract void setElement(String elementPath, SetterReturnObject elementSetterReturnObject) throws IOException, ApolloTranslatorException;

    public abstract void setComment(String elementPath, String comment);
}

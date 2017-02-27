package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 3, 2013
 * Time: 3:31:17 PM
 * Class: DoubleSetter
 * IDE: NetBeans 6.9.1
 */
public class DoubleSetter extends AbstractTypedSetter<Double> {

    public DoubleSetter() {
        
    }
    
    public DoubleSetter(ApolloTranslationEngine apolloTranslationEngine, String type, String section) {
        super(type, section, apolloTranslationEngine);
    }

    @Override
    public List<SetterReturnObject> set(Double dbl) throws ApolloSetterException {
        return setValue("", dbl.toString(), section);
    }
}

package edu.pitt.apollo.apollotranslator.setters;

import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.GenderEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 3, 2013
 * Time: 2:19:35 PM
 * Class: GenderCategorySetter
 * IDE: NetBeans 6.9.1
 */
public class GenderCategorySetter extends AbstractTypedSetter<GenderEnum> {

    public GenderCategorySetter() {
        
    }
    
    public GenderCategorySetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    @Override
    public List<SetterReturnObject> set(GenderEnum gender) throws ApolloSetterException {
        return setValue("", gender.value(), section);
    }
}

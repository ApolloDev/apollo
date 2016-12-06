package edu.pitt.apollo.apollotranslator.setters;

import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.types.v4_0_1.InfectionStateEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 3, 2013
 * Time: 2:38:33 PM
 * Class: InfectionStateSetter
 * IDE: NetBeans 6.9.1
 */
public class InfectionStateSetter extends AbstractTypedSetter<InfectionStateEnum> {

    public InfectionStateSetter() {
        
    }
    
    public InfectionStateSetter(ApolloTranslationEngine apolloTranslationEngine, String prefix, String section) {
        super(prefix, section, apolloTranslationEngine);
    }

    @Override
    public List<SetterReturnObject> set(InfectionStateEnum infectionState) throws ApolloSetterException {
        return setValue("", infectionState.value(), section);
    }
}

package edu.pitt.apollo.apollotranslator.setters;

import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;

/**
 * Created by JDL50 on 1/14/14.
 */


public class AuthenticationSetter extends AbstractTypedSetter<Authentication> {

    /*

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String requesterId;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String requesterPassword;
     */


    public static final String ID_FIELD = "requesterId";
    public static final String PW_FIELD = "requesterPassword";


    public AuthenticationSetter(String prefix,
                                String section,
                                ApolloTranslationEngine apolloTranslationEngine) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setRequesterId(String value)
            throws ApolloSetterException {
        return setValue(ID_FIELD, value, section);
    }

    private List<SetterReturnObject> setRequesterPassword(String value)
            throws ApolloSetterException {
        return setValue(PW_FIELD, value, section);
    }

    @Override
    public List<SetterReturnObject> set(Authentication authentication) throws ApolloSetterException {
        results.addAll(setRequesterId(authentication.getRequesterId()));
        results.addAll(setRequesterPassword(authentication.getRequesterPassword()));
        return results;
    }
}


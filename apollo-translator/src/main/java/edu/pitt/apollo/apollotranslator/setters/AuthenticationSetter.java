package edu.pitt.apollo.apollotranslator.setters;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.AuthorizationTypeEnum;

import java.util.List;

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


    public static final String ID_FIELD = "payload";
    public static final String PW_FIELD = "authorizationType";


    public AuthenticationSetter(String prefix,
                                String section,
                                ApolloTranslationEngine apolloTranslationEngine) {
        super(prefix, section, apolloTranslationEngine);
    }

    private List<SetterReturnObject> setPayload(String value)
            throws ApolloSetterException {
        return setValue(ID_FIELD, value, section);
    }

    private List<SetterReturnObject> setAuthorizationType(AuthorizationTypeEnum value)
            throws ApolloSetterException {
        return setValue(PW_FIELD, value.value(), section);
    }

    @Override
    public List<SetterReturnObject> set(Authentication authentication) throws ApolloSetterException {
        results.addAll(setPayload(authentication.getPayload()));
        results.addAll(setAuthorizationType(authentication.getAuthorizationType()));
        return results;
    }
}


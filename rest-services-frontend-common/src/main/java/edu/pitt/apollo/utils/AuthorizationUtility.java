package edu.pitt.apollo.utils;

import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.AuthorizationTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nem41 on 10/4/16.
 */
public class AuthorizationUtility {

    private static final String JWT_TOKEN_PROPERTY = "JWT";
    private static final String SSO_TOKEN_PROPERTY = "UserIdToken";

    public static Authentication createAuthenticationFromAuthorizationHeader(String authorizationHeader) throws UnsupportedAuthorizationTypeException {

        Map<String, String> authorizationPropertyMap = new HashMap<>();
        String[] authorizationProps = authorizationHeader.split(",");
        for (String propString : authorizationProps) {
            String[] keyValue = propString.split("=");
            if (keyValue.length < 2) {
                continue;
            }
            authorizationPropertyMap.put(keyValue[0], keyValue[1]);
        }

        Authentication authentication = new Authentication();
        if (authorizationPropertyMap.containsKey(JWT_TOKEN_PROPERTY)) {
            authentication.setAuthorizationType(AuthorizationTypeEnum.JWT);
            authentication.setPayload(authorizationPropertyMap.get(JWT_TOKEN_PROPERTY));
        } else if (authorizationPropertyMap.containsKey(SSO_TOKEN_PROPERTY)) {
            authentication.setAuthorizationType(AuthorizationTypeEnum.SSO);
            authentication.setPayload(authorizationPropertyMap.get(SSO_TOKEN_PROPERTY));
        } else {
            throw new UnsupportedAuthorizationTypeException();
        }

        return authentication;
    }

}

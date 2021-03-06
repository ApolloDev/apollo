package edu.pitt.apollo.utilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.AuthorizationTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nem41 on 10/4/16.
 */
public class AuthorizationUtility {

    private static final String JWT_TOKEN_PROPERTY = "JWT";
    private static final String SSO_TOKEN_PROPERTY = "UserIdToken";
    private static final String USER_ID_PROPERTY = "UserId";
    private static final String JSON_PROPERTY = "JSON";

    public static Authentication createAuthenticationFromAuthorizationHeader(String authorizationHeader) throws UnsupportedAuthorizationTypeException {

        Map<String, String> authorizationPropertyMap = new HashMap<>();
        String key = authorizationHeader.substring(0, authorizationHeader.indexOf("="));
        String keyValue = authorizationHeader.substring(authorizationHeader.indexOf("=") + 1);
        authorizationPropertyMap.put(key, keyValue);


        Authentication authentication = new Authentication();
        if (authorizationPropertyMap.containsKey(JWT_TOKEN_PROPERTY)) {
            authentication.setAuthorizationType(AuthorizationTypeEnum.JWT);
            authentication.setPayload(authorizationPropertyMap.get(JWT_TOKEN_PROPERTY));
        } else if (authorizationPropertyMap.containsKey(SSO_TOKEN_PROPERTY)) {
            authentication.setAuthorizationType(AuthorizationTypeEnum.SSO);
            authentication.setPayload(authorizationPropertyMap.get(SSO_TOKEN_PROPERTY));
        } else if (authorizationPropertyMap.containsKey(USER_ID_PROPERTY)) {
            authentication.setAuthorizationType(null);
            authentication.setPayload(authorizationPropertyMap.get(USER_ID_PROPERTY));
        } else if (authorizationPropertyMap.containsKey(JSON_PROPERTY)) {
            authentication.setAuthorizationType(AuthorizationTypeEnum.JSON);
            authentication.setPayload(authorizationPropertyMap.get(JSON_PROPERTY));
        } else {
            throw new UnsupportedAuthorizationTypeException();
        }

        return authentication;
    }

    public static JsonObject getJsonFromAuthentication(Authentication authentication) throws UnsupportedAuthorizationTypeException {
        if (!authentication.getAuthorizationType().equals(AuthorizationTypeEnum.JSON)) {
            throw new UnsupportedAuthorizationTypeException();
        }

        JsonParser parser = new JsonParser();
        return parser.parse(authentication.getPayload()).getAsJsonObject();
    }

}

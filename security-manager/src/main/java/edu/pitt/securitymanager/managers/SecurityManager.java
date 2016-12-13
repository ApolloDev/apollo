package edu.pitt.securitymanager.managers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.AuthorizationTypeEnum;
import edu.pitt.securitymanager.exception.ApolloSecurityException;
import edu.pitt.securitymanager.types.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by nem41 on 12/7/16.
 */
public abstract class SecurityManager {
    private static final String CLIENT_ID_PROPERTY = "client_id";
    private static String CLIENT_ID;
    private static String CLIENT_SECRET;
    private static final String CLIENT_SECRET_PROPERTY = "client_secret";
    private static final String APP_METADATA_KEY = "appMetadata";
    private static final String HUB_TOKEN_PROPERTY = "hub_token";
    protected static final String HUB_TOKEN;
    private static final String AUTH0_URL_PROPERTY = "auth0_url";
    protected static final String AUTH0_URL;
    private static final String HUB_URL_PROPERTY = "hub_url";
    protected static final String HUB_URL;
    private static final String USER_ID_USER_PROFILE_KEY = "sub";
    private static final String ROLES_USER_PROFILE_KEY = "roles";
    private final Map<String, String> userDelegatedTokenMap = new HashMap<>();
    private final Map<String, UserProfile> userProfileMap = new HashMap<>();

    static {
        Properties securityProperties = new Properties();
        try {
            securityProperties.load(new FileInputStream(ApolloServiceConstants.APOLLO_DIR + "base_security.properties"));
            HUB_URL = securityProperties.getProperty(HUB_URL_PROPERTY);
            AUTH0_URL = securityProperties.getProperty(AUTH0_URL_PROPERTY);
            HUB_TOKEN = securityProperties.getProperty(HUB_TOKEN_PROPERTY);
        } catch (IOException ex) {
            throw new ExceptionInInitializerError("IOException: " + ex.getMessage());
        }
    }

    protected SecurityManager(String securityPropertiesFile) throws ApolloSecurityException {
        loadSecurityProperties(securityPropertiesFile);
    }

    public Authentication getUsernameAuthentication(Authentication authentication) throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        UserProfile userProfileData = getUserProfileFromClaims(claims);
        Authentication newAuthentication = new Authentication();
        newAuthentication.setAuthorizationType(null);
        newAuthentication.setPayload(userProfileData.getUserId());
        return newAuthentication;
    }

    public void setAuthenticationJSONPayload(Authentication authentication, String payload) {
        authentication.setAuthorizationType(AuthorizationTypeEnum.JSON);
        authentication.setPayload(payload);
    }

    protected UserProfile getUserProfileFromAuthentication(Authentication authentication) throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        return getUserProfileFromClaims(claims);
    }

    protected Jws<Claims> getClaims(Authentication authentication) throws ApolloSecurityException {
        switch (authentication.getAuthorizationType()) {
            case JWT:
                try {
                    Jws<Claims> claims = Jwts.parser().setSigningKey(CLIENT_SECRET).parseClaimsJws(authentication.getPayload());
                    return claims;
                } catch (Exception ex) {
                    throw new ApolloSecurityException("Exception verifying JWT: " + ex.getMessage());
                }
            case SSO:
                try {
                    String token = getDelegatedToken(authentication.getPayload(), CLIENT_ID);
                    Jws<Claims> claims = Jwts.parser().setSigningKey(CLIENT_ID).parseClaimsJws(token);
                    return claims;
                } catch (Exception ex) {
                    throw new ApolloSecurityException("Exception verifying SSO: " + ex.getMessage());
                }
            default:
                throw new ApolloSecurityException("Unrecognized authorization type");
        }
    }

    protected JsonObject getUserProfileMetadata(String userId) throws ApolloSecurityException {

        try {
            URL obj = new URL(HUB_URL + URLEncoder.encode(userId));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");
            con.addRequestProperty("Accept", "application/" + "json");
            con.addRequestProperty("Authorization", "Bearer " + HUB_TOKEN);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result

            JsonParser parser = new JsonParser();
            JsonObject userProfile = parser.parse(response.toString()).getAsJsonObject();

            JsonObject metadata = userProfile.get(APP_METADATA_KEY).getAsJsonObject();

            return metadata;

        } catch (IOException ex) {
            throw new ApolloSecurityException("IOException: " + ex.getMessage());
        }
    }

    protected String getUserNameFromClaims(Jws<Claims> claims) {
        return (String) claims.getBody().get(USER_ID_USER_PROFILE_KEY);
    }

    protected UserProfile getUserProfileFromClaims(Jws<Claims> claims) throws ApolloSecurityException {

        String userId = getUserNameFromClaims(claims);

        if (userProfileMap.containsKey(userId)) {
            return userProfileMap.get(userId);
        }

        JsonObject metadata = getUserProfileMetadata(userId);
        if (metadata.get(ROLES_USER_PROFILE_KEY) == null) {
            throw new ApolloSecurityException("User profile does not contain any roles");
        }
        JsonArray rolesJsonObject = metadata.get(ROLES_USER_PROFILE_KEY).getAsJsonArray();
        Set<String> roles = new HashSet<>();
        for (int i = 0; i < rolesJsonObject.size(); i++) {
            String role = rolesJsonObject.get(i).getAsString();
            roles.add(role);
        }

        UserProfile userProfile = getUserProfileDataFromRoles(roles, userId);
        userProfileMap.put(userId, userProfile);

        return userProfile;
    }

    protected boolean userProfileHasRole(UserProfile userProfile, String role) {
        if (userProfile.getRoles() != null) {
            return userProfile.getRoles().contains(role);
        }

        return false;
    }

    protected boolean claimsHasRole(Jws<Claims> claims, String role) {
        String roles = (String) claims.getBody().get(ROLES_USER_PROFILE_KEY);

        return false;
    }

    protected abstract String createAuthenticationJSON(UserProfile userProfile);

    protected abstract String createAuthenticationJSON(Jws<Claims> claims);

    private UserProfile getUserProfileDataFromRoles(Set<String> roles, String userId) throws ApolloSecurityException {
        UserProfile userProfile = new UserProfile();
        userProfile.setRoles(roles);
        userProfile.setUserId(userId);
        return userProfile;
    }

    private void loadSecurityProperties(String securityPropertiesFile) throws ApolloSecurityException {
        Properties securityProperties = new Properties();
        try {
            securityProperties.load(new FileInputStream(ApolloServiceConstants.APOLLO_DIR + securityPropertiesFile));
            CLIENT_SECRET = securityProperties.getProperty(CLIENT_SECRET_PROPERTY);
            CLIENT_ID = securityProperties.getProperty(CLIENT_ID_PROPERTY);
        } catch (IOException ex) {
            throw new ApolloSecurityException("IOException: " + ex.getMessage());
        }
    }

    private String getDelegatedToken(String userIdToken, String clientId) throws ApolloSecurityException {

        if (userDelegatedTokenMap != null && userDelegatedTokenMap.containsKey(userIdToken)) {
            return userDelegatedTokenMap.get(userIdToken);
        }

        try {
            URL obj = new URL(AUTH0_URL + "delegation");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();


            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("client_id", clientId);
            jsonObject.addProperty("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
            jsonObject.addProperty("id_token", userIdToken);
            jsonObject.addProperty("target", clientId);
            jsonObject.addProperty("scope", "openid");
            jsonObject.addProperty("api_type", "app");

            String content = gson.toJson(jsonObject);


            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.addRequestProperty("Content-Type", "application/" + "json");
            con.setRequestProperty("Content-Length", Integer.toString(content.length()));
            con.getOutputStream().write(content.getBytes(StandardCharsets.UTF_8));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String responseAsString = response.toString();
            JsonParser parser = new JsonParser();
            JsonObject newData = parser.parse(responseAsString).getAsJsonObject();
            String newToken = newData.get("id_token").getAsString();

            if (userDelegatedTokenMap != null) {
                userDelegatedTokenMap.put(userIdToken, newToken);
            }
            return newToken;
        } catch (IOException ex) {
            throw new ApolloSecurityException("IOException: " + ex.getMessage());
        }
    }
}

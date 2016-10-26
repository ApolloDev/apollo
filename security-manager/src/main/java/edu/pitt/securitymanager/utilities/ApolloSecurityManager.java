package edu.pitt.securitymanager.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ServiceRecord;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.securitymanager.exception.ApolloSecurityException;
import edu.pitt.securitymanager.exception.UserNotAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.postgresql.util.Base64;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by nem41 on 10/4/16.
 */
public class ApolloSecurityManager {

    private static final String APOLLO_CLIENT_ID_PROPERTY = "apollo_client_id";
    private static final String APOLLO_CLIENT_ID;
    private static final String APOLLO_CLIENT_SECRET;
    private static final String APOLLO_CLIENT_SECRET_PROPERTY = "apollo_client_secret";
    private static final String HUB_URL_PROPERTY = "hub_url";
    private static final String HUB_URL;
    private static final String AUTH0_URL_PROPERTY = "auth0_url";
    private static final String AUTH0_URL;
    private static final String USER_ID_KEY = "sub";
    private static final String APP_METADATA_KEY = "appMetadata";
    private static final String ALLOWED_SERVICES_KEY = "allowedServices";
    private static final String SOFTWARE_DEVELOPER_KEY = "d";
    private static final String SOFTWARE_NAME_KEY = "n";
    private static final String SOFTWARE_VERSION_KEY = "v";
    private static final String IS_SERVICE_KEY = "isService";
    private static final Map<String, String> userDelegatedTokenMap = new HashMap<>();
    private static final Map<String, UserProfileData> userProfileMap = new HashMap<>();

    static {

        Properties securityProperties = new Properties();
        try {
            securityProperties.load(new FileInputStream(ApolloServiceConstants.APOLLO_DIR + "apollo_security.properties"));
            APOLLO_CLIENT_SECRET = securityProperties.getProperty(APOLLO_CLIENT_SECRET_PROPERTY);
            HUB_URL = securityProperties.getProperty(HUB_URL_PROPERTY);
            AUTH0_URL = securityProperties.getProperty(AUTH0_URL_PROPERTY);
            APOLLO_CLIENT_ID = securityProperties.getProperty(APOLLO_CLIENT_ID_PROPERTY);
        } catch (IOException ex) {
            throw new ExceptionInInitializerError("IOException: " + ex.getMessage());
        }

    }

    private static class UserProfileData {
        public List<SoftwareIdentification> allowedSoftware;
        public String userId;
    }

    public static void authorizeUserForRunData(Authentication authentication, BigInteger runId) throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        UserProfileData userProfile = getUserProfileFromClaims(claims);
        checkOwnershipOfRun(userProfile, runId);
        setAuthenticationUserName(authentication, userProfile.userId);
    }

    public static String authorizeUserForSpecifiedSoftware(Authentication authentication, SoftwareIdentification softwareId)
            throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        UserProfileData userProfile = getUserProfileFromClaims(claims);

        boolean foundSoftware = false;
        for (SoftwareIdentification userSoftwareIdentification : userProfile.allowedSoftware) {
            if (softwareEqual(userSoftwareIdentification, softwareId)) {
                foundSoftware = true;
                break;
            }
        }

        if (!foundSoftware) {
            throw new UserNotAuthorizedException("The user is not authorized to run the specified software");
        }

        setAuthenticationUserName(authentication, userProfile.userId);
        return userProfile.userId;
    }

    public static void authorizeService(Authentication authentication) throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        if (!isService(claims)) {
            throw new ApolloSecurityException("Only services are allowed to execute this action");
        } else {
            setAuthenticationUserName(authentication, getUserNameFromClaims(claims));
        }

    }

    public static void filterSoftwareListForServiceOrUser(Authentication authentication,
                                                          List<ServiceRecord> serviceRecords) throws ApolloSecurityException {

        Jws<Claims> claims = getClaims(authentication);
        if (!isService(claims)) {

            UserProfileData userProfile = getUserProfileFromClaims(claims);

            List<SoftwareIdentification> userSoftwareList = userProfile.allowedSoftware;
            for (Iterator<ServiceRecord> iterator = serviceRecords.iterator(); iterator.hasNext(); ) {
                boolean userAllowedToRun = false;
                SoftwareIdentification software = iterator.next().getSoftwareIdentification();
                for (SoftwareIdentification userSoftware : userSoftwareList) {
                    if (softwareEqual(software, userSoftware)) {
                        userAllowedToRun = true;
                        break;
                    }
                }
                if (!userAllowedToRun) {
                    iterator.remove();
                }
            }
        }
    }

    public static void authorizeServiceOrUserForRunData(Authentication authentication, BigInteger runId) throws ApolloSecurityException {

        Jws<Claims> claims = getClaims(authentication);
        if (!isService(claims)) {
            UserProfileData userProfile = getUserProfileFromClaims(claims);
            // check ownership
            checkOwnershipOfRun(userProfile, runId);
            setAuthenticationUserName(authentication, userProfile.userId);
        } else {
            setAuthenticationUserName(authentication, getUserNameFromClaims(claims));
        }
    }

    public static Authentication getUsernameAuthentication(Authentication authentication) throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        UserProfileData userProfileData = getUserProfileFromClaims(claims);
        Authentication newAuthentication = new Authentication();
        newAuthentication.setAuthorizationType(null);
        newAuthentication.setPayload(userProfileData.userId);
        return newAuthentication;
    }

    private static void checkOwnershipOfRun(UserProfileData userProfileData, BigInteger runId) throws ApolloSecurityException {

        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {

            String userForRun = dbUtils.getUserForRun(runId);
            if (!userForRun.equals(userProfileData.userId)) {
                throw new UserNotAuthorizedException("This run does not belong to the specified user");
            }

        } catch (ApolloDatabaseException ex) {
            throw new ApolloSecurityException("ApolloDatabaseException: " + ex.getMessage());
        }
    }

    private static Jws<Claims> getClaims(Authentication authentication) throws ApolloSecurityException {
        switch (authentication.getAuthorizationType()) {
            case JWT:
                try {
                    Jws<Claims> claims = Jwts.parser().setSigningKey(Base64.encodeBytes(APOLLO_CLIENT_SECRET.getBytes())).parseClaimsJws(authentication.getPayload());
                    return claims;
                } catch (Exception ex) {
                    throw new ApolloSecurityException("Exception verifying JWT: " + ex.getMessage());
                }
            case SSO:
                try {
                    String token = getDelegatedToken(authentication.getPayload());
                    Jws<Claims> claims = Jwts.parser().setSigningKey(APOLLO_CLIENT_SECRET).parseClaimsJws(token);
                    return claims;
                } catch (Exception ex) {
                    throw new ApolloSecurityException("Exception verifying JWT: " + ex.getMessage());
                }
            default:
                throw new ApolloSecurityException("Unrecognized authorization type");
        }
    }

    private static boolean isService(Jws<Claims> claims) {
        Boolean isService = false;
        if (claims.getBody().get(IS_SERVICE_KEY) != null) {
            isService = Boolean.parseBoolean((String) claims.getBody().get(IS_SERVICE_KEY));
        }
        return isService;
    }

    private static String getUserNameFromClaims(Jws<Claims> claims) {
        return (String) claims.getBody().get(USER_ID_KEY);
    }

    private static UserProfileData getUserProfileFromClaims(Jws<Claims> claims) throws ApolloSecurityException {

        String userId = getUserNameFromClaims(claims);

        return getUserProfile(userId);
    }

    private static String getDelegatedToken(String userIdToken) throws ApolloSecurityException {

        if (userDelegatedTokenMap.containsKey(userIdToken)) {
            return userDelegatedTokenMap.get(userIdToken);
        }

        try {
            URL obj = new URL(AUTH0_URL + "delegation");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();


            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("client_id", APOLLO_CLIENT_ID);
            jsonObject.addProperty("grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer");
            jsonObject.addProperty("id_token", userIdToken);
            jsonObject.addProperty("target", APOLLO_CLIENT_ID);
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

            userDelegatedTokenMap.put(userIdToken, newToken);
            return newToken;
        } catch (IOException ex) {
            throw new ApolloSecurityException("IOException: " + ex.getMessage());
        }

    }

    private static UserProfileData getUserProfile(String userId) throws ApolloSecurityException {

        if (userProfileMap.containsKey(userId)) {
            return userProfileMap.get(userId);
        }

        try {
            URL obj = new URL(HUB_URL + URLEncoder.encode(userId));
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

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

            JsonArray allowedServices = metadata.get(ALLOWED_SERVICES_KEY).getAsJsonArray();

            List<SoftwareIdentification> softwareIdentificationList = new ArrayList<>();
            for (int i = 0; i < allowedServices.size(); i++) {
                JsonObject service = allowedServices.get(i).getAsJsonObject();
                SoftwareIdentification softwareIdentification = new SoftwareIdentification();
                softwareIdentification.setSoftwareName(service.get(SOFTWARE_NAME_KEY).getAsString());
                softwareIdentification.setSoftwareDeveloper(service.get(SOFTWARE_DEVELOPER_KEY).getAsString());
                softwareIdentification.setSoftwareVersion(service.get(SOFTWARE_VERSION_KEY).getAsString());

                softwareIdentificationList.add(softwareIdentification);
            }

            UserProfileData userProfileData = new UserProfileData();
            userProfileData.allowedSoftware = softwareIdentificationList;
            userProfileData.userId = userId;

            userProfileMap.put(userId, userProfileData);

            return userProfileData;

        } catch (IOException ex) {
            throw new ApolloSecurityException("IOException: " + ex.getMessage());
        }
    }

    private static boolean softwareEqual(SoftwareIdentification softwareIdentification1,
                                         SoftwareIdentification softwareIdentification2) {
        return (softwareIdentification1.getSoftwareName().equals(softwareIdentification2.getSoftwareName())
                && softwareIdentification1.getSoftwareDeveloper().equals(softwareIdentification2.getSoftwareDeveloper())
                && softwareIdentification1.getSoftwareVersion().equals(softwareIdentification2.getSoftwareVersion()));

    }

    private static void setAuthenticationUserName(Authentication authentication, String userName) {
        authentication.setAuthorizationType(null);
        authentication.setPayload(userName);
    }
}

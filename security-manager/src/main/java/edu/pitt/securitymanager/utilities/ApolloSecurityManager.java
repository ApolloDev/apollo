package edu.pitt.securitymanager.utilities;

import com.auth0.jwt.JWTVerifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v4_0.ServiceRecord;
import edu.pitt.securitymanager.exception.ApolloSecurityException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.securitymanager.exception.UserNotAuthorizedException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by nem41 on 10/4/16.
 */
public class ApolloSecurityManager {

    private static final String APOLLO_CLIENT_SECRET;
    private static final String APOLLO_CLIENT_SECRET_PROPERTY = "apollo_client_secret";
    private static final String HUB_URL_PROPERTY = "hub_url";
    private static final String HUB_URL;
    private static final String USER_ID_KEY = "sub";
    private static final String APP_METADATA_KEY = "appMetadata";
    private static final String ALLOWED_SERVICES_KEY = "allowedServices";
    private static final String SOFTWARE_DEVELOPER_KEY = "d";
    private static final String SOFTWARE_NAME_KEY = "n";
    private static final String SOFTWARE_VERSION_KEY = "v";

    static {

        Properties securityProperties = new Properties();
        try {
            securityProperties.load(new FileInputStream(ApolloServiceConstants.APOLLO_DIR + "apollo_security.properties"));
            APOLLO_CLIENT_SECRET = securityProperties.getProperty(APOLLO_CLIENT_SECRET_PROPERTY);
            HUB_URL = securityProperties.getProperty(HUB_URL_PROPERTY);
        } catch (IOException ex) {
            throw new ExceptionInInitializerError("IOException: " + ex.getMessage());
        }

    }

    private static class UserProfileData {
        public List<SoftwareIdentification> allowedSoftware;
        public String userId;
        public boolean isService;
    }

    public static void authorizeUserForRunData(Authentication authentication, BigInteger runId) throws ApolloSecurityException {
        UserProfileData userProfile = getUserProfileFromAuthentication(authentication);
        checkOwnershipOfRun(userProfile, runId);
    }

    public static void authorizeUserForSpecifiedSoftware(Authentication authentication, SoftwareIdentification softwareId)
            throws ApolloSecurityException {
        UserProfileData userProfile = getUserProfileFromAuthentication(authentication);

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
    }

    public static void authorizeService(Authentication authentication) throws ApolloSecurityException {
        UserProfileData userProfileData = getUserProfileFromAuthentication(authentication);
        if (!userProfileData.isService) {
            throw new ApolloSecurityException("Only services are allowed to execute this action");
        }
    }

    public static void filterSoftwareListForServiceOrUser(Authentication authentication,
                                                          List<ServiceRecord> serviceRecords) throws ApolloSecurityException {
        UserProfileData userProfile = getUserProfileFromAuthentication(authentication);
        if (!userProfile.isService) {

            List<SoftwareIdentification> userSoftwareList = userProfile.allowedSoftware;
            for (Iterator<ServiceRecord> iterator = serviceRecords.iterator(); iterator.hasNext();) {
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
        UserProfileData userProfile = getUserProfileFromAuthentication(authentication);
        if (!userProfile.isService) {
            // check ownership
            checkOwnershipOfRun(userProfile, runId);
        }
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

    private static UserProfileData getUserProfileFromAuthentication(Authentication authentication) throws ApolloSecurityException {

        String userId = null;
        switch (authentication.getAuthorizationType()) {

            case JWT:
                try {
                    final JWTVerifier verifier = new JWTVerifier(APOLLO_CLIENT_SECRET);
                    final Map<String, Object> jwtData = verifier.verify(authentication.getPayload());

                    userId = (String) jwtData.get(USER_ID_KEY);

                } catch (Exception e) {
                    // Invalid Token
                    e.printStackTrace();
                }
                break;
            case SSO:
                break;
        }

        return getUserProfile(userId);
    }

    private static UserProfileData getUserProfile(String userId) throws ApolloSecurityException {

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
            userProfileData.isService = false;
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
}

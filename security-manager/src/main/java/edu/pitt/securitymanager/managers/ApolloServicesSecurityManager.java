package edu.pitt.securitymanager.managers;

import com.google.gson.JsonObject;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRecord;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.securitymanager.exception.ApolloSecurityException;
import edu.pitt.securitymanager.exception.UserNotAuthorizedException;
import edu.pitt.securitymanager.types.AuthenticationAndUserId;
import edu.pitt.securitymanager.types.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by nem41 on 10/4/16.
 */
public class ApolloServicesSecurityManager extends SecurityManager {

    private static final String GENERAL_APOLLO_SOFTWARE_ROLE = "ISG_USER";
    private static final String IS_SERVICE_KEY = "isService";
    private static final String SECURITY_FILE = "apollo_security.properties";

    public ApolloServicesSecurityManager() throws ApolloSecurityException {
        super(SECURITY_FILE);
    }

    public Authentication authorizeUserForRunData(Authentication authentication, BigInteger runId) throws ApolloSecurityException {
        UserProfile userProfile = getUserProfileFromAuthentication(authentication);
        checkOwnershipOfRun(userProfile, runId);
        return createAuthenticationWithJSON(userProfile);
    }

    public AuthenticationAndUserId authorizeUserForSpecifiedSoftware(Authentication authentication, SoftwareIdentification softwareId)
            throws ApolloSecurityException {
        // TODO update apollo authorization to use roles
        UserProfile userProfile = getUserProfileFromAuthentication(authentication);
        if (!userProfileHasRole(userProfile, GENERAL_APOLLO_SOFTWARE_ROLE)) {
            throw new UserNotAuthorizedException("The user is not authorized to access the library");
        }

        AuthenticationAndUserId authenticationAndUserId = new AuthenticationAndUserId();
        authenticationAndUserId.setUserId(userProfile.getUserId());
        authenticationAndUserId.setAuthentication(createAuthenticationWithJSON(userProfile));

        return authenticationAndUserId;
    }

    public Authentication authorizeService(Authentication authentication) throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        if (!isService(claims)) {
            throw new ApolloSecurityException("Only services are allowed to execute this action");
        } else {
            return createAuthenticationWithJSON(claims);
        }

    }

    public void filterSoftwareListForServiceOrUser(Authentication authentication,
                                                          List<ServiceRecord> serviceRecords) throws ApolloSecurityException {
        Jws<Claims> claims = getClaims(authentication);
        if (!isService(claims)) {

            // right now verified users are allowed to run all software
        }
    }

    public Authentication authorizeServiceOrUserForRunData(Authentication authentication, BigInteger runId) throws ApolloSecurityException {

        Jws<Claims> claims = getClaims(authentication);
        if (!isService(claims)) {
            UserProfile userProfile = getUserProfileFromClaims(claims);
            // check ownership
            checkOwnershipOfRun(userProfile, runId);
            return createAuthenticationWithJSON(userProfile);
        } else {
            return createAuthenticationWithJSON(claims);
        }
    }

    private static void checkOwnershipOfRun(UserProfile userProfileData, BigInteger runId) throws ApolloSecurityException {

        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {

            String userForRun = dbUtils.getUserForRun(runId);
            if (!userForRun.equals(userProfileData.getUserId())) {
                throw new UserNotAuthorizedException("This run does not belong to the specified user");
            }

        } catch (ApolloDatabaseException ex) {
            throw new ApolloSecurityException("ApolloDatabaseException: " + ex.getMessage());
        }
    }

    private static boolean isService(Jws<Claims> claims) {
        Boolean isService = false;
        if (claims.getBody().get(IS_SERVICE_KEY) != null) {
            isService = Boolean.parseBoolean((String) claims.getBody().get(IS_SERVICE_KEY));
        }
        return isService;
    }

    private static boolean softwareEqual(SoftwareIdentification softwareIdentification1,
                                         SoftwareIdentification softwareIdentification2) {
        return (softwareIdentification1.getSoftwareName().equals(softwareIdentification2.getSoftwareName())
                && softwareIdentification1.getSoftwareDeveloper().equals(softwareIdentification2.getSoftwareDeveloper())
                && softwareIdentification1.getSoftwareVersion().equals(softwareIdentification2.getSoftwareVersion()));

    }

    public static void main(String[] args) throws ApolloSecurityException {
//        Authentication authentication = new Authentication();
//        authentication.setAuthorizationType(AuthorizationTypeEnum.SSO);
//        authentication.setPayload("");
//        Authentication auth2 = new ApolloServicesSecurityManager().getUsernameAuthentication(authentication);
//        System.out.println("done");
    }

    @Override
    protected JsonObject createAuthenticationJSONObject(UserProfile userProfile) {
        JsonObject object = new JsonObject();
        object.addProperty(USERNAME_KEY, userProfile.getUserId());
        return object;
    }

    @Override
    protected JsonObject createAuthenticationJSONObject(Jws<Claims> claims) {
        JsonObject object = new JsonObject();
        object.addProperty(USERNAME_KEY, claims.getBody().get(USER_ID_USER_PROFILE_KEY).toString());
        return object;
    }
}

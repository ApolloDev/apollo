package edu.pitt.securitymanager.managers;

import com.google.gson.JsonObject;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.AuthorizationTypeEnum;
import edu.pitt.securitymanager.exception.ApolloSecurityException;
import edu.pitt.securitymanager.exception.UserNotAuthorizedException;
import edu.pitt.securitymanager.types.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Created by nem41 on 12/7/16.
 */
public class LibrarySecurityManager extends SecurityManager {

    private static final String LIBRARY_READ_ROLE = "ISG_USER";
    private static final String LIBRARY_CACHE_ROLE = "LIBRARY_CACHE";
    private static final String LIBRARY_ROLE_KEY = "libraryRoles";
    private static final String SECURITY_FILE = "apollo_security.properties";

    public LibrarySecurityManager() throws ApolloSecurityException {
        super(SECURITY_FILE);
    }

    public Authentication authorizeUserToAccessLibrary(Authentication authentication) throws ApolloSecurityException {

        if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.JWT)) {
            Jws<Claims> claims = getClaims(authentication);

            if (!claimsHasRole(claims, LIBRARY_READ_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to access the library");
            }

            return createAuthenticationWithJSON(claims);
        } else if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.SSO)) {
            UserProfile userProfile = getUserProfileFromAuthentication(authentication);
            if (!userProfileHasRole(userProfile, LIBRARY_READ_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to access the library");
            }

            return createAuthenticationWithJSON(userProfile);
        } else {
            throw new ApolloSecurityException("Unsupported authorization type");
        }
    }

    public Authentication authorizeUserToGetCachedData(Authentication authentication) throws ApolloSecurityException {
        if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.JWT)) {
            Jws<Claims> claims = getClaims(authentication);
            if (!claimsHasRole(claims, LIBRARY_CACHE_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to get cached data from the library");
            }

            return createAuthenticationWithJSON(claims);
        } else if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.SSO)) {

            UserProfile userProfile = getUserProfileFromAuthentication(authentication);
            if (!userProfileHasRole(userProfile, LIBRARY_CACHE_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to get cached data from the library");
            }

            return createAuthenticationWithJSON(userProfile);
        } else {
            throw new ApolloSecurityException("Unsupported authorization type");
        }
    }

    @Override
    protected JsonObject createAuthenticationJSONObject(UserProfile userProfile) {
        JsonObject object = new JsonObject();
        object.addProperty(LIBRARY_ROLE_KEY, userProfile.getRoles().toString());
        object.addProperty(USERNAME_KEY, userProfile.getUserId());
        return object;
    }

    @Override
    protected JsonObject createAuthenticationJSONObject(Jws<Claims> claims) {
        JsonObject object = new JsonObject();
        object.addProperty(LIBRARY_ROLE_KEY, claims.getBody().get(ROLES_KEY).toString());
        object.addProperty(USERNAME_KEY, claims.getBody().get(USER_ID_USER_PROFILE_KEY).toString());
        return object;
    }
}

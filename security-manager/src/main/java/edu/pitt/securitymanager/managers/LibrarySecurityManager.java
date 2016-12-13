package edu.pitt.securitymanager.managers;

import com.google.gson.Gson;
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

    private static final String LIBRARY_USER_ROLE = "LIBRARY_USER";
    private static final String LIBRARY_CURATOR_ROLE = "LIBRARY_CURATOR";
    private static final String SECURITY_FILE = "apollo_security.properties";

    public LibrarySecurityManager() throws ApolloSecurityException {
        super(SECURITY_FILE);
    }

    public String authorizeUserToReadLibrary(Authentication authentication) throws ApolloSecurityException {

        if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.JWT)) {
            Jws<Claims> claims = getClaims(authentication);

            if (!claimsHasRole(claims, LIBRARY_USER_ROLE) && !claimsHasRole(claims, LIBRARY_CURATOR_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to view the library");
            }

            return getUserNameFromClaims(claims);
        } else if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.SSO)) {
            UserProfile userProfile = getUserProfileFromAuthentication(authentication);
            if (!userProfileHasRole(userProfile, LIBRARY_USER_ROLE)
                    && !userProfileHasRole(userProfile, LIBRARY_CURATOR_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to view the library");
            }

            return userProfile.getUserId();
        } else {
            throw new ApolloSecurityException("Unsupported authorization type");
        }
    }

    public String authorizeUserToEditLibrary(Authentication authentication) throws ApolloSecurityException {
        if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.JWT)) {
            Jws<Claims> claims = getClaims(authentication);
            if (!claimsHasRole(claims, LIBRARY_CURATOR_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to view the library");
            }

            return getUserNameFromClaims(claims);
        } else if (authentication.getAuthorizationType().equals(AuthorizationTypeEnum.SSO)) {

            UserProfile userProfile = getUserProfileFromAuthentication(authentication);
            if (!userProfileHasRole(userProfile, LIBRARY_CURATOR_ROLE)) {
                throw new UserNotAuthorizedException("The user is not authorized to curate the library");
            }

            return userProfile.getUserId();
        } else {
            throw new ApolloSecurityException("Unsupported authorization type");
        }
    }

    @Override
    protected String createAuthenticationJSON(UserProfile userProfile) {
        Gson 
    }

    @Override
    protected String createAuthenticationJSON(Jws<Claims> claims) {
        return null;
    }
}

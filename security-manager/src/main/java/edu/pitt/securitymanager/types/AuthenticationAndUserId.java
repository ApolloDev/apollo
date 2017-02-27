package edu.pitt.securitymanager.types;

import edu.pitt.apollo.services_common.v4_0_1.Authentication;

/**
 * Created by nem41 on 12/15/16.
 */
public class AuthenticationAndUserId {

    private Authentication authentication;
    private String userId;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

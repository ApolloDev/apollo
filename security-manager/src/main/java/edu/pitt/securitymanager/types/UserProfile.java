package edu.pitt.securitymanager.types;

import java.util.Set;

/**
 * Created by nem41 on 12/7/16.
 */
public class UserProfile {
    private String userId;
    private Set<String> roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

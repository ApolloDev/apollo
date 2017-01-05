package edu.pitt.apollo.libraryservice.methods;

import com.google.gson.JsonObject;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.utilities.AuthorizationUtility;

import java.util.Map;

/**
 * Created by nem41 on 12/12/16.
 */
public class BaseLibraryMethod {

     final Map<String, Integer> roles;
     int highestRole = 1;
     String userName;

    public BaseLibraryMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        this.roles = roles;
        setLibraryProperties(authentication);
    }

    private void setLibraryProperties(Authentication authentication) throws LibraryServiceException {
        try {
            JsonObject jsonObject = AuthorizationUtility.getJsonFromAuthentication(authentication);

            String rolesString = jsonObject.get("libraryRoles").getAsString();
            if (rolesString != null) {
                String[] rolesArray = rolesString.split(",");
                for (String role : rolesArray) {
                    if (roles.containsKey(role)) {
                        if (highestRole < roles.get(role)) {
                            highestRole = roles.get(role);
                        }
                    }
                }
            } else {
                throw new LibraryServiceException("The authentication token JSON did not contain any roles");
            }

            userName = jsonObject.get("userName").getAsString();
        } catch (UnsupportedAuthorizationTypeException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }
}

package edu.pitt.apollo.libraryservice.methods;

import com.google.gson.JsonObject;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.utilities.AuthorizationUtility;

/**
 * Created by nem41 on 12/12/16.
 */
public class BaseLibraryMethod {

     int role;
     String userName;

    public BaseLibraryMethod(Authentication authentication) throws LibraryServiceException {
        setLibraryProperties(authentication);
    }

    private void setLibraryProperties(Authentication authentication) throws LibraryServiceException {
        try {
            JsonObject jsonObject = AuthorizationUtility.getJsonFromAuthentication(authentication);
            role = jsonObject.get("role").getAsInt();
            userName = jsonObject.get("userName").getAsString();
        } catch (UnsupportedAuthorizationTypeException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }
}

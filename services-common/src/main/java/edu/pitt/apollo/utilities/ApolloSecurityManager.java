package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Created by nem41 on 10/4/16.
 */
public class ApolloSecurityManager {

    public static void authorizeUserForRunData(Authentication authentication, BigInteger runId) throws UserNotAuthorizedException {

    }

    public static void authorizeUserForSpecifiedSoftware(Authentication authentication, SoftwareIdentification softwareId)
            throws UserNotAuthorizedException {

    }

    public static void authorizeService(Authentication authentication) throws UserNotAuthorizedException {

    }

    public static void authorizeServiceOrUserForRunData(Authentication authentication, BigInteger runId) throws UserNotAuthorizedException {

    }

}

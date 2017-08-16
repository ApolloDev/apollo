package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_2.SetReleaseVersionResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 10:49:57 AM
 * Class: SetReleaseVersionMethod
 */
public class SetReleaseVersionMethod extends BaseLibraryMethod {

    public SetReleaseVersionMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public SetReleaseVersionResult setReleaseVersion(LibraryDbUtils dbUtils, int urn, int version, String comment) throws LibraryServiceException {


        SetReleaseVersionResult result = new SetReleaseVersionResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            dbUtils.setReleaseVersion(urn, version, highestRole, userName, comment);
            status.setStatus(MethodCallStatusEnum.COMPLETED);

        } catch (ApolloDatabaseException | UserNotAuthorizedException | NoLibraryItemException | ApolloDatabaseExplicitException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }

}

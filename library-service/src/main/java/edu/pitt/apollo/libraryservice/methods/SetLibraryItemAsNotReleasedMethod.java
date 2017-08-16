package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_2.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 3, 2014
 * Time: 4:38:01 PM
 * Class: SetLibraryItemAsNotReleasedMethod
 */
public class SetLibraryItemAsNotReleasedMethod extends BaseLibraryMethod {

    public SetLibraryItemAsNotReleasedMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(LibraryDbUtils dbUtils, int urn) throws LibraryServiceException {

        SetLibraryItemAsNotReleasedResult result = new SetLibraryItemAsNotReleasedResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            int previousReleaseVersion = dbUtils.setLibraryItemAsNotReleased(urn, highestRole);
            result.setVersion(previousReleaseVersion);
            status.setStatus(MethodCallStatusEnum.COMPLETED);

        } catch (ApolloDatabaseException | UserNotAuthorizedException | NoLibraryItemException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }
}

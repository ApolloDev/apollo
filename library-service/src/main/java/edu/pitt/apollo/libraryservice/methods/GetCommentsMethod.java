package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetCommentsResult;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 11:27:41 AM
 * Class: GetCommentsMethod
 */
public class GetCommentsMethod extends BaseLibraryMethod {

    public GetCommentsMethod(Authentication authentication) throws LibraryServiceException {
        super(authentication);
    }

    public GetCommentsResult getComments(LibraryDbUtils dbUtils, int urn, int version) throws LibraryServiceException {


        GetCommentsResult result;
        MethodCallStatus status = new MethodCallStatus();

        try {
            result = dbUtils.getComments(urn, version, role);
            status.setStatus(MethodCallStatusEnum.COMPLETED);

        } catch (ApolloDatabaseException | UserNotAuthorizedException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
        result.setStatus(status);

        return result;
    }
}

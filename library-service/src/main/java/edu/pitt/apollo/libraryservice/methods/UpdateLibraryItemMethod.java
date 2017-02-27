package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_1.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v4_0_1.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 10:40:39 AM
 * Class: UpdateLibraryItemMethod
 */
public class UpdateLibraryItemMethod extends BaseLibraryMethod{

    public UpdateLibraryItemMethod(Authentication authentication) throws LibraryServiceException {
        super(authentication);
    }

    public UpdateLibraryItemContainerResult updateLibraryItem(LibraryDbUtils dbUtils,
                                                                     int urn, LibraryItemContainer item, String comment) throws LibraryServiceException {


        UpdateLibraryItemContainerResult result = new UpdateLibraryItemContainerResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            int version = dbUtils.updateLibraryItem(urn, role, item, userName, comment);
            result.setVersion(version);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException | UserNotAuthorizedException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }

}

package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v4_0.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v4_0.UpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v4_0.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 10:40:39 AM
 * Class: UpdateLibraryItemMethod
 */
public class UpdateLibraryItemMethod {

    public static UpdateLibraryItemContainerResult updateLibraryItem(LibraryDbUtils dbUtils,
                                                                     int urn, LibraryItemContainer item, String comment, Authentication authentication) {


        UpdateLibraryItemContainerResult result = new UpdateLibraryItemContainerResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.COMMITTER);
            if (userAuthorized) {
                int version = dbUtils.updateLibraryItem(urn, item, authentication, comment);
                result.setVersion(version);
                status.setStatus(MethodCallStatusEnum.COMPLETED);
            } else {
                status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
                status.setMessage("You are not authorized to update items in the library.");
            }

        } catch (ApolloDatabaseException ex) {
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage(ex.getMessage());
        }

        return result;
    }

}

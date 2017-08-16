package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_2.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v4_0_2.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 10:40:39 AM
 * Class: UpdateLibraryItemMethod
 */
public class UpdateLibraryItemMethod extends BaseLibraryMethod{

    public UpdateLibraryItemMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public UpdateLibraryItemContainerResult updateLibraryItem(LibraryDbUtils dbUtils,
                                                                     int urn, LibraryItemContainer item, String comment) throws LibraryServiceException {


        UpdateLibraryItemContainerResult result = new UpdateLibraryItemContainerResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            int version = dbUtils.updateLibraryItem(urn, highestRole, item, userName, comment);
            result.setVersion(version);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException | UserNotAuthorizedException | NoLibraryItemException | ApolloDatabaseExplicitException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }

}

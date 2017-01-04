package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_1.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_1.LibraryItemContainer;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:41:53 PM
 * Class: AddLibraryItemMethod
 */
public class AddLibraryItemMethod extends BaseLibraryMethod {

    public AddLibraryItemMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public AddLibraryItemContainerResult addLibraryItem(LibraryDbUtils dbUtils, LibraryItemContainer libraryItemContainer, String comment) throws LibraryServiceException {

        AddLibraryItemContainerResult result = new AddLibraryItemContainerResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            result = dbUtils.addLibraryItem(libraryItemContainer, userName, comment, highestRole);
            result.setStatus(status);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        } catch (UserNotAuthorizedException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }
}

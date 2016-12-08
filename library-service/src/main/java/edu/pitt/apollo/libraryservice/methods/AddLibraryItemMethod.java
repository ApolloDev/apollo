package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v4_0_1.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_1.LibraryItemContainer;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:41:53 PM
 * Class: AddLibraryItemMethod
 */
public class AddLibraryItemMethod {

    public static AddLibraryItemContainerResult addLibraryItem(LibraryDbUtils dbUtils, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) {

        AddLibraryItemContainerResult result = new AddLibraryItemContainerResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            result = dbUtils.addLibraryItem(libraryItemContainer, authentication.getPayload(), comment);
            result.setStatus(status);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException ex) {
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage(ex.getMessage());
        }

        return result;
    }
}

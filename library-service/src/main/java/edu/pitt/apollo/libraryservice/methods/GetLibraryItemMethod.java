package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_2.LibraryItemContainer;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;
import java.io.IOException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:46:59 PM
 * Class: GetLibraryItemMethod
 */
public class GetLibraryItemMethod {

	public static GetLibraryItemContainerResult getLibraryItemMethod(LibraryDbUtils dbUtils,  int urn, Integer version, Authentication authentication) {

		GetLibraryItemContainerResult result = null;
		MethodCallStatus status = new MethodCallStatus();

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {
                result = dbUtils.getLibraryItemContainer(urn, version);
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
                result = new GetLibraryItemContainerResult();
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to retrieve items from the library.");
			}
		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

        result.setStatus(status);
        return result;
	}

}

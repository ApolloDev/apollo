package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v3_0_0.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_0.LibraryItemContainer;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:41:53 PM
 * Class: AddLibraryItemMethod
 */
public class AddLibraryItemMethod {

	public static AddLibraryItemContainerResult addLibraryItem(LibraryDbUtils dbUtils,
			AddLibraryItemContainerMessage message) {

		Authentication authentication = message.getAuthentication();
		String comment = message.getComment();
		LibraryItemContainer item = message.getLibraryItemContainer();

		AddLibraryItemContainerResult result = new AddLibraryItemContainerResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.COMMITTER);
			if (userAuthorized) {
				result = dbUtils.addLibraryItem(item, authentication, comment);
				result.setStatus(status);

				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to add items to the library.");
			}

		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

		return result;
	}
}

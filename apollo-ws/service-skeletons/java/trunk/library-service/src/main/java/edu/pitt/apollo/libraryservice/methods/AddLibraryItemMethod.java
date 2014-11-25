package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v2_1_0.LibraryItemContainer;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:41:53 PM
 * Class: AddLibraryItemMethod
 */
public class AddLibraryItemMethod {

	public static AddOrUpdateLibraryItemContainerResult addLibraryItem(LibraryDbUtils dbUtils,
			AddOrUpdateLibraryItemContainerMessage message) {

		Authentication authentication = message.getAuthentication();
		String uri = message.getUri();
		String comment = message.getComment();
		LibraryItemContainer item = message.getLibraryItemContainer();
		
		AddOrUpdateLibraryItemContainerResult result = new AddOrUpdateLibraryItemContainerResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);
		
		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.COMMITTER);
			if (userAuthorized) {
				int version = dbUtils.addLibraryItem(uri, item, authentication, comment);
				result.setVersion(version);
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

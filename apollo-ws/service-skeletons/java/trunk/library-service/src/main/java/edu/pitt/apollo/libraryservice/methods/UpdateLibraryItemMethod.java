package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v3_0_0.AddOrUpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.AddOrUpdateLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_0.LibraryItemContainer;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 10:40:39 AM
 * Class: UpdateLibraryItemMethod
 */
public class UpdateLibraryItemMethod {

	public static AddOrUpdateLibraryItemContainerResult updateLibraryItem(LibraryDbUtils dbUtils,
			AddOrUpdateLibraryItemContainerMessage message) {
		
		Authentication authentication = message.getAuthentication();
		String urn = message.getUrn();
		String comment = message.getComment();
		LibraryItemContainer item = message.getLibraryItemContainer();

		AddOrUpdateLibraryItemContainerResult result = new AddOrUpdateLibraryItemContainerResult();
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

package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_0.LibraryItemContainer;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
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

	public static GetLibraryItemContainerResult getLibraryItemMethod(LibraryDbUtils dbUtils, GetLibraryItemContainerMessage message) {

		Authentication authentication = message.getAuthentication();
		String urn = message.getUrn();
		Integer version = message.getVersion();

		GetLibraryItemContainerResult result = new GetLibraryItemContainerResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {

				LibraryItemContainer container;
				if (version == null) {
					// get release version
					container = dbUtils.getReleaseLibraryItemContainer(urn);
				} else {
					container = dbUtils.getLibraryItemContainer(urn, version);
				}
				result.setLibraryItemContainer(container);
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to retrieve items from the library.");
			}
		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

		return result;
	}

}

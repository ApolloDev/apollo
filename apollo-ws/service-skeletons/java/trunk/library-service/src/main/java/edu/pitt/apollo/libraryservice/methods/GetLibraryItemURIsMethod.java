package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsResult;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatusEnum;

public class GetLibraryItemURIsMethod {

	public static GetLibraryItemURIsResult getLibraryItemURIs(LibraryDbUtils dbUtils,
			GetLibraryItemURIsMessage getLibraryItemURIsMessage) {

		Authentication authentication = getLibraryItemURIsMessage.getAuthentication();
		String itemType = getLibraryItemURIsMessage.getItemType();

		//execute query
		GetLibraryItemURIsResult result = new GetLibraryItemURIsResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {
				result.getURIs().addAll(dbUtils.getURNs(itemType));
				status.setMessage("");
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to get item URIs from the library.");
			}
		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

		return result;
	}

}

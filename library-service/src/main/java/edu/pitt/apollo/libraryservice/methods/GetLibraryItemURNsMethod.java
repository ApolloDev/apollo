package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v4_0.GetLibraryItemURNsMessage;
import edu.pitt.apollo.library_service_types.v4_0.GetLibraryItemURNsResult;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;

public class GetLibraryItemURNsMethod {

	public static GetLibraryItemURNsResult getLibraryItemURIs(LibraryDbUtils dbUtils,
															  String itemType, Authentication authentication) {



		//execute query
		GetLibraryItemURNsResult result = new GetLibraryItemURNsResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {
				result.getURNs().addAll(dbUtils.getURNs(itemType));
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

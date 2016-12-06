package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.database.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetReleaseVersionResult;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Aug 13, 2014 Time: 3:46:59 PM Class: GetLibraryItemMethod
 */
public class GetApprovedRevisionOfLibraryItemMethod {

	public static GetReleaseVersionResult getLibraryItemMethod(LibraryDbUtils dbUtils, int urn, Authentication authentication) {

		GetReleaseVersionResult result = null;
		MethodCallStatus status = new MethodCallStatus();

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {
				Integer revision = dbUtils.getReleaseVersion(urn);
				result = new GetReleaseVersionResult();
				if (revision != null) {
					result.setVersion(revision);
					result.setHasReleasedVersion(true);
					status.setStatus(MethodCallStatusEnum.COMPLETED);
				} else {
					status.setStatus(MethodCallStatusEnum.NOT_FOUND);
					status.setMessage("No approved revision found");
					result.setHasReleasedVersion(false);
				}
			} else {
				result = new GetReleaseVersionResult();
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

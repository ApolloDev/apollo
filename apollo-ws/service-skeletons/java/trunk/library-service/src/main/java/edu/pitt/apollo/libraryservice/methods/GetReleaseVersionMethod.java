package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionResult;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 11:05:58 AM
 * Class: GetReleaseVersionMethod
 */
public class GetReleaseVersionMethod {

	public static GetReleaseVersionResult getReleaseVersion(LibraryDbUtils dbUtils, GetReleaseVersionMessage message) {

		Authentication authentication = message.getAuthentication();
		String uri = message.getUri();

		GetReleaseVersionResult result = new GetReleaseVersionResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {
				int version = dbUtils.getPublicVersion(uri);
				result.setVersion(version);
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to get release versions.");
			}

		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

		return result;
	}
}

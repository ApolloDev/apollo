package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v3_0_2.SetLibraryItemAsNotReleasedMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 3, 2014
 * Time: 4:38:01 PM
 * Class: SetLibraryItemAsNotReleasedMethod
 */
public class SetLibraryItemAsNotReleasedMethod {

	public static SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(LibraryDbUtils dbUtils, SetLibraryItemAsNotReleasedMessage message) {
		Authentication authentication = message.getAuthentication();
		int urn = message.getUrn();

		SetLibraryItemAsNotReleasedResult result = new SetLibraryItemAsNotReleasedResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.COMMITTER);
			if (userAuthorized) {
				int previousReleaseVersion = dbUtils.setLibraryItemAsNotReleased(urn);
				result.setVersion(previousReleaseVersion);
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to set items as not released.");
			}

		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

		return result;
	}
}

package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v4_0.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v4_0.AddReviewerCommentResult;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 11:21:20 AM
 * Class: AddReviewerCommentMethod
 */
public class AddReviewerCommentMethod {

	public static AddReviewerCommentResult addReviewerComment(LibraryDbUtils dbUtils, int urn, int version, String comment, Authentication authentication) {

		AddReviewerCommentResult result = new AddReviewerCommentResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.REVIEWER);
			if (userAuthorized) {
				dbUtils.addReviewerComment(urn, version, comment, authentication);
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to add reviewer comments.");
			}

		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

		return result;
	}

}

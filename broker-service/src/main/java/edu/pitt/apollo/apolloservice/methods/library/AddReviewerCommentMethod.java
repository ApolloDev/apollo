package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_1_0.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v3_1_0.AddReviewerCommentResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:47:12 PM
 * Class: AddReviewerCommentMethod
 */
public class AddReviewerCommentMethod extends LibraryMethod {

	public static AddReviewerCommentResult addReviewerComment(AddReviewerCommentMessage addReviewerCommentToLibraryItemMessage) {
		return getLibraryServicePort().addReviewerCommentToLibraryItem(addReviewerCommentToLibraryItemMessage);
	}
	
}

package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_0_0.GetCommentsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetCommentsResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:38:01 PM
 * Class: GetCommentsForLibraryItemMethod
 */
public class GetCommentsForLibraryItemMethod extends LibraryMethod {

	public static GetCommentsResult getComments(GetCommentsMessage getCommentsForLibraryItemMessage) {
		return getLibraryServicePort().getCommentsForLibraryItem(getCommentsForLibraryItemMessage);
	}
	
}

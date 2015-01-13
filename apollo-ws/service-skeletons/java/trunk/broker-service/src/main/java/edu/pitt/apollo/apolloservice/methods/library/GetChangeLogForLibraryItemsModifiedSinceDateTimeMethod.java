package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_0_0.GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:49:48 PM
 * Class: GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod
 */
public class GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod extends LibraryMethod {

	public static GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLog(GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage getChangeLogForLibraryItemsModifiedSinceDateTimeMessage) {
		return getLibraryServicePort().getChangeLogForLibraryItemsModifiedSinceDateTime(getChangeLogForLibraryItemsModifiedSinceDateTimeMessage);
	}

}

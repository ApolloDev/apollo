package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v4_0.GetRevisionsResult;
import edu.pitt.apollo.library_service_types.v4_0.GetVersionsMessage;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:56:58 PM
 * Class: GetVersionNumbersForLibraryItemMethod
 */
public class GetVersionNumbersForLibraryItemMethod extends LibraryMethod {

	public static GetRevisionsResult getVersions(GetVersionsMessage getVersionNumbersForLibraryItemMessage) {
		return getLibraryServicePort().getVersionNumbersForLibraryItem(getVersionNumbersForLibraryItemMessage);
	}
	
}

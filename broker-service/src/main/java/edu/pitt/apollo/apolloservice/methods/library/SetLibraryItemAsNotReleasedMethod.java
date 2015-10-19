package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_0_2.SetLibraryItemAsNotReleasedMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.SetLibraryItemAsNotReleasedResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:42:33 PM
 * Class: SetLibraryItemAsNotReleasedMethod
 */
public class SetLibraryItemAsNotReleasedMethod extends LibraryMethod {

	public static SetLibraryItemAsNotReleasedResult setLibraryItemAsnotReleased(SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage) {
		return getLibraryServicePort().setLibraryItemAsNotReleased(setLibraryItemAsNotReleasedMessage);
	}
	
}

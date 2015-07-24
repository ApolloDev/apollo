package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_0_2.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.SetReleaseVersionResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:54:34 PM
 * Class: SetReleaseVersionForLibraryItemMethod
 */
public class SetReleaseVersionForLibraryItemMethod extends LibraryMethod {

	public static SetReleaseVersionResult setReleaseVersion(SetReleaseVersionMessage setReleaseVersionForLibraryItemMessage) {
		return getLibraryServicePort().setReleaseVersionForLibraryItem(setReleaseVersionForLibraryItemMessage);
	}
	
}

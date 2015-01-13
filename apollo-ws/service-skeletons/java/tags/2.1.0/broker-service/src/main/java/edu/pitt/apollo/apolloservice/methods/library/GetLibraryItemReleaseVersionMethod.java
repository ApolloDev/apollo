package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionResult;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Dec 12, 2014
 Time: 4:59:25 PM
 Class: GetLibraryItemReleaseVersionMethod
 */
public class GetLibraryItemReleaseVersionMethod extends LibraryMethod {

	public static GetReleaseVersionResult getReleaseVersion(GetReleaseVersionMessage getLibraryItemReleaseVersionMessage) {
		return getLibraryServicePort().getLibraryItemReleaseVersion(getLibraryItemReleaseVersionMessage);
	}
	
}

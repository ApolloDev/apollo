package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemURNsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemURNsResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:44:07 PM
 * Class: GetLibraryItemURNsMethod
 */
public class GetLibraryItemURNsMethod extends LibraryMethod {

	public static GetLibraryItemURNsResult getURNs(GetLibraryItemURNsMessage getLibraryItemURNsMessage) {
		return getLibraryServicePort().getLibraryItemURNs(getLibraryItemURNsMessage);
	}
	
}

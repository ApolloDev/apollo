package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_1_0.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_1_0.GetLibraryItemContainerResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:51:06 PM
 * Class: GetLibraryItemContainerMethod
 */
public class GetLibraryItemContainerMethod extends LibraryMethod {

	public static GetLibraryItemContainerResult getLibraryItemContainer(GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
		return getLibraryServicePort().getLibraryItemContainer(getLibraryItemContainerMessage);
	}
	
}

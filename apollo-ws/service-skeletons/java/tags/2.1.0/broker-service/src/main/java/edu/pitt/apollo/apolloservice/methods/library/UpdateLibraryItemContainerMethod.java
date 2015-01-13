package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:52:41 PM
 * Class: UpdateLibraryItemContainerMethod
 */
public class UpdateLibraryItemContainerMethod extends LibraryMethod {

	public static AddOrUpdateLibraryItemContainerResult updateLibraryItemContainer(AddOrUpdateLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage) {
		return getLibraryServicePort().updateLibraryItemContainer(addOrUpdateLibraryItemContainerMessage);
	}
	
}

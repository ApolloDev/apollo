package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_0_0.AddOrUpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.AddOrUpdateLibraryItemContainerResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:55:59 PM
 * Class: AddLibraryItemContainerMethod
 */
public class AddLibraryItemContainerMethod extends LibraryMethod {

	public static AddOrUpdateLibraryItemContainerResult addLibraryItemContainer(AddOrUpdateLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage) {
		return getLibraryServicePort().addLibraryItemContainer(addOrUpdateLibraryItemContainerMessage);
	}
	
}

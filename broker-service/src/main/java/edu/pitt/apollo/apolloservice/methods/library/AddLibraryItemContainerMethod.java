package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v4_0.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v4_0.AddLibraryItemContainerResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:55:59 PM
 * Class: AddLibraryItemContainerMethod
 */
public class AddLibraryItemContainerMethod extends LibraryMethod {

	public static AddLibraryItemContainerResult addLibraryItemContainer(AddLibraryItemContainerMessage addOrUpdateLibraryItemContainerMessage) {
		return getLibraryServicePort().addLibraryItemContainer(addOrUpdateLibraryItemContainerMessage);
	}
	
}

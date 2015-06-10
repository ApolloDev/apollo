package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_0_0.UpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.UpdateLibraryItemContainerResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:52:41 PM
 * Class: UpdateLibraryItemContainerMethod
 */
public class UpdateLibraryItemContainerMethod extends LibraryMethod {

	public static UpdateLibraryItemContainerResult updateLibraryItemContainer(UpdateLibraryItemContainerMessage updateLibraryItemContainerMessage) {
		return getLibraryServicePort().updateLibraryItemContainer(updateLibraryItemContainerMessage);
	}
	
}

package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v4_0.ModifyGroupOwnershipMessage;
import edu.pitt.apollo.library_service_types.v4_0.ModifyGroupOwnershipResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:45:45 PM
 * Class: RemoveGroupAccessToLibraryItemMethod
 */
public class RemoveGroupAccessToLibraryItemMethod extends LibraryMethod {

	public static ModifyGroupOwnershipResult removeGroupAccess(ModifyGroupOwnershipMessage removeGroupAccessToLibraryItemMessage) {
		return getLibraryServicePort().removeGroupAccessToLibraryItem(removeGroupAccessToLibraryItemMessage);
	}
}

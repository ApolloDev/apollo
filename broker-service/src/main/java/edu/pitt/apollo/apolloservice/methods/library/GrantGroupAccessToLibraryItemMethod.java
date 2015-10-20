package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.library_service_types.v3_1_0.ModifyGroupOwnershipMessage;
import edu.pitt.apollo.library_service_types.v3_1_0.ModifyGroupOwnershipResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 12, 2014
 * Time: 4:48:20 PM
 * Class: GrantGroupAccessToLibraryItemMethod
 */
public class GrantGroupAccessToLibraryItemMethod extends LibraryMethod {

	public static ModifyGroupOwnershipResult grantGroupAccess(ModifyGroupOwnershipMessage grantGroupAccessToLibraryItemMessage) {
		return getLibraryServicePort().grantGroupAccessToLibraryItem(grantGroupAccessToLibraryItemMessage);
	}
	
}

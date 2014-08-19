package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.Db4oDatabaseAccessor;
import edu.pitt.apollo.types.v2_0_2.Authentication;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:45:11 PM
 * Class: RemoveLibraryItemMethod
 */
public class RemoveLibraryItemMethod {

	public static MethodCallStatus removeLibraryItem(Db4oDatabaseAccessor db4oAccessor,
			Authentication authentication,
			String uuid) {

		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.COMPLETED);

		db4oAccessor.removeApolloLibraryItem(uuid);

		status.setMessage("Deleted object.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);

		return status;
	}
}

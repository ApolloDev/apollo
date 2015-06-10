package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v3_0_0.QueryMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.QueryResult;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 2:12:51 PM
 * Class: QueryLibraryMethod
 */
public class QueryLibraryMethod {

	public static QueryResult queryLibrary(LibraryDbUtils dbUtils, LibraryDbUtils readOnlyDbUtils, QueryMessage message) {

		Authentication authentication = message.getAuthentication();
		String query = message.getQuery();

		QueryResult result = new QueryResult();
		MethodCallStatus status = new MethodCallStatus();

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {
				result = readOnlyDbUtils.queryObjects(query);
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to use queries.");
			}

		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}
		
		result.setStatus(status);
		return result;
	}

}

package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryUserRoleTypeEnum;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v4_0.ChangeLogEntry;
import edu.pitt.apollo.library_service_types.v4_0.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Dec 3, 2014
 Time: 3:15:58 PM
 Class: GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod
 */
public class GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod {

	public static GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(LibraryDbUtils dbUtils,
																														  XMLGregorianCalendar dateTime, Authentication authentication) {

		
		GetChangeLogForLibraryItemsModifiedSinceDateTimeResult result = new GetChangeLogForLibraryItemsModifiedSinceDateTimeResult();
		MethodCallStatus status = new MethodCallStatus();
		result.setStatus(status);

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, LibraryUserRoleTypeEnum.READONLY);
			if (userAuthorized) {
				
				List<ChangeLogEntry> changeLog = dbUtils.getChangeLogForLibraryItemsModifiedSinceDateTime(dateTime);
				result.getChangeLogEntries().addAll(changeLog);
				
				status.setStatus(MethodCallStatusEnum.COMPLETED);
			} else {
				status.setStatus(MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				status.setMessage("You are not authorized to retrieve a change log from the library.");
			}
		} catch (ApolloDatabaseException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage(ex.getMessage());
		}

		return result;
		
	}
	
}

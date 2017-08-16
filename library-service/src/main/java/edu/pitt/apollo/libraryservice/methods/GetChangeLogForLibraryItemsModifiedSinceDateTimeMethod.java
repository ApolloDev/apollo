package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_2.ChangeLogEntry;
import edu.pitt.apollo.library_service_types.v4_0_2.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 3, 2014
 * Time: 3:15:58 PM
 * Class: GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod
 */
public class GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod extends BaseLibraryMethod {

    public GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(LibraryDbUtils dbUtils,
                                                                                                                   XMLGregorianCalendar dateTime) throws LibraryServiceException {

        GetChangeLogForLibraryItemsModifiedSinceDateTimeResult result = new GetChangeLogForLibraryItemsModifiedSinceDateTimeResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            List<ChangeLogEntry> changeLog = dbUtils.getChangeLogForLibraryItemsModifiedSinceDateTime(dateTime, highestRole);
            result.getChangeLogEntries().addAll(changeLog);

            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;

    }

}

package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.Db4oDatabaseAccessor;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemUuidsResult;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:48:32 PM
 * Class: GetUuidsForLibraryItemsCreatedSinceDateTimeMethod
 */
public class GetUuidsForLibraryItemsCreatedSinceDateTimeMethod {

	public static GetLibraryItemUuidsResult getUuidsForLibraryItemsCreatedSinceDateTime(Db4oDatabaseAccessor db4oAccessor, XMLGregorianCalendar creationDateTime) {
		GetLibraryItemUuidsResult result = new GetLibraryItemUuidsResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setMethodCallStatus(status);

		List<String> resultList = db4oAccessor.getUuidsCreatedSinceDateTime(creationDateTime);
		result.getUuids().addAll(resultList);
		return result;
	}

}

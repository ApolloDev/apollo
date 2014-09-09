package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.Db4oDatabaseAccessor;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemUuidsResult;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:50:05 PM
 * Class: GetUuidsForLibraryItemsGivenTypeMethod
 */
public class GetUuidsForLibraryItemsGivenTypeMethod {

	public static GetLibraryItemUuidsResult getUuidsForLibraryItemsGivenType(Db4oDatabaseAccessor db4oAccessor, String type) {
		GetLibraryItemUuidsResult result = new GetLibraryItemUuidsResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Okay.");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setMethodCallStatus(status);

		List<String> resultList = db4oAccessor.getUuidsGivenType(type);
		result.getUuids().addAll(resultList);
		return result;
	}
	
}

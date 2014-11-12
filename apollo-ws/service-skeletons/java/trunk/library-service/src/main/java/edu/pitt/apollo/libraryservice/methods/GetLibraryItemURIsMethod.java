package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsResult;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v2_1_0.MethodCallStatusEnum;

public class GetLibraryItemURIsMethod {

	public static GetLibraryItemURIsResult getLibraryItemURIs(
			GetLibraryItemURIsMessage getLibraryItemURIsMessage) {
		// query = "select all URIs from the db";
		String itemType = getLibraryItemURIsMessage.getItemType();
		if (itemType != null) {
			// query = " where item_type = "+itemType;
		}
		//execute query
		GetLibraryItemURIsResult result = new GetLibraryItemURIsResult();
		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("");
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		result.setStatus(status);
		
		//for each result...
		result.getURIs().add("//result");
		return result;
	}

}

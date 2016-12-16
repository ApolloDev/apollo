package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemContainersResult;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/11/15.
 */
public class GetLibraryItemContainersMethod extends BaseLibraryServiceAccessorMethod {

	public GetLibraryItemContainersMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, GetLibraryItemContainersResult.class, authorizationHeader);
	}

	public String getLibraryItems(String className, Boolean includeUnreleasedItems) {

        GetLibraryItemContainersResult result;
        if (includeUnreleasedItems == null) {
            includeUnreleasedItems = false;
        }
		try {
			result = impl.getLibraryItemContainers(className, includeUnreleasedItems, authentication);
		} catch (LibraryServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getName() + ": " + ex.getMessage());
			try {
				return serializer.serializeObject(responseBuilder.getResponse());
			} catch (SerializationException e1) {
				return "Error: " + e1.getClass().getName() + ": " + e1.getMessage();
			}
		}

		return getResponseAsString(result);

	}

}

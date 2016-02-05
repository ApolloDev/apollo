package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v4_0.GetRevisionsResult;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/13/15.
 */
public class GetVersionNumbersForLibraryItemMethod extends BaseLibraryServiceAccessorMethod {

	public GetVersionNumbersForLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat, GetRevisionsResult.class);
	}

	public String getVersionNumbersForLibraryItem(int urn) {
		try {
			return getResponseAsString(impl.getAllRevisionsOfLibraryItem(urn, authentication));
		} catch (LibraryServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getName() + ": " + ex.getMessage());
			try {
				return serializer.serializeObject(responseBuilder.getResponse());
			} catch (SerializationException e1) {
				return "Error: " + e1.getClass().getName() + ": " + e1.getMessage();
			}
		}
	}
}

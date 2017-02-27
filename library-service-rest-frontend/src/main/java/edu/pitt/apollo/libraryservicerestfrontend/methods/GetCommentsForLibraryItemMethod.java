package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetCommentsResult;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/13/15.
 */
public class GetCommentsForLibraryItemMethod extends BaseLibraryServiceAccessorMethod {

	public GetCommentsForLibraryItemMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, GetCommentsResult.class, authorizationHeader);
	}

	public String getCommentsForLibraryItem(int urn, int version) {
		GetCommentsResult result;
		try {
			result = impl.getCommentsForLibraryItem(urn, version, authentication);
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

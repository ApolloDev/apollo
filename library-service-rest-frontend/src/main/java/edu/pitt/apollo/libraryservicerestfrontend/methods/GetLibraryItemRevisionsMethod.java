package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.LibraryServiceException;

import edu.pitt.apollo.library_service_types.v4_0_1.GetReleaseVersionResult;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/12/15.
 */
public class GetLibraryItemRevisionsMethod extends BaseLibraryServiceAccessorMethod {

	public GetLibraryItemRevisionsMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat, GetReleaseVersionResult.class);
	}

	public String getLibraryItemReleaseVersion(int urn) {

		Object result;
		try {
			result = impl.getAllRevisionsOfLibraryItem(urn, authentication);
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

package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v4_0.*;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utils.ResponseDeserializer;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/12/15.
 */
public class UpdateLibraryItemMethod extends BaseLibraryServiceAccessorMethod {

	public UpdateLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat, UpdateLibraryItemContainerResult.class);
	}

	public String updateLibraryItem(String messageBody, String comment, int urn) {
		try {

			LibraryItemContainer container = (LibraryItemContainer) ResponseDeserializer.deserialize(messageBody);

			Object result = impl.reviseLibraryItem(urn, container, comment, authentication);
			return getResponseAsString(result);
		} catch (DeserializationException | UnsupportedSerializationFormatException | LibraryServiceException e) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName() + ": " + e.getMessage());
			try {
				return serializer.serializeObject(responseBuilder.getResponse());
			} catch (SerializationException e1) {
				return "Error: " + e1.getClass().getName() + ": " + e1.getMessage();
			}
		}
	}

}

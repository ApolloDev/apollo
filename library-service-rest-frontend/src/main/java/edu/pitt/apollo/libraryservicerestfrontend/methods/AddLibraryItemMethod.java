package edu.pitt.apollo.libraryservicerestfrontend.methods;


import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_2.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_2.LibraryItemContainer;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.apollo.utils.ResponseDeserializer;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/7/15.
 */
public class AddLibraryItemMethod extends BaseLibraryServiceAccessorMethod {

	public AddLibraryItemMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, AddLibraryItemContainerResult.class, authorizationHeader);
	}

	public String addLibraryItem(String messageBody, String comment) {
		try {
			LibraryItemContainer container = (LibraryItemContainer) ResponseDeserializer.deserialize(messageBody);

			Object result = impl.addLibraryItem(container, comment, authentication);
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

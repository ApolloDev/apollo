package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_1.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v4_0_1.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v4_0_1.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.apollo.utils.ResponseDeserializer;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/12/15.
 */
public class UpdateLibraryItemMethod extends BaseBrokerServiceAccessorMethod {

	public UpdateLibraryItemMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String updateLibraryItem(String messageBody, String comment, int urn) throws SerializationException {
		try {

			LibraryItemContainer container = (LibraryItemContainer) ResponseDeserializer.deserialize(messageBody);

			UpdateLibraryItemContainerResult result = impl.reviseLibraryItem(urn, container, comment, authentication);
			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
			serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
			serializationInformation.setClassName(UpdateLibraryItemContainerResult.class.getSimpleName());
			serializationInformation.setFormat(SerializationFormat.XML);

			String serializedObject = serializer.serializeObject(result);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

			return serializer.serializeObject(responseBuilder.getResponse());
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

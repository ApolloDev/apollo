package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_2.AddReviewerCommentResult;
import edu.pitt.apollo.services_common.v4_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/13/15.
 */
public class AddReviewerCommentMethod extends BaseBrokerServiceAccessorMethod {

	public AddReviewerCommentMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorizationHeader);
	}

	public String addReviewerComment(int urn, int version, String comment) throws SerializationException {
		AddReviewerCommentResult result;
		try {
			result = impl.addReviewerCommentToLibraryItem(urn, version, comment, authentication);
			
			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
            serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
            serializationInformation.setClassName(AddReviewerCommentResult.class.getSimpleName());
            serializationInformation.setFormat(SerializationFormat.XML);

            String serializedObject = serializer.serializeObject(result);

            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
                    .setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

			
		} catch (LibraryServiceException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getName() + ": " + ex.getMessage());
			try {
				return serializer.serializeObject(responseBuilder.getResponse());
			} catch (SerializationException e1) {
				return "Error: " + e1.getClass().getName() + ": " + e1.getMessage();
			}
		}
		return serializer.serializeObject(responseBuilder.getResponse());
	}
}

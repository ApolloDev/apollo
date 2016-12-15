package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_1.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v4_0_1.Request;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.XMLDeserializer;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/12/15.
 */
public class QueryMethod extends BaseLibraryServiceAccessorMethod {

    public QueryMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
        super(serializationFormat, UpdateLibraryItemContainerResult.class, authorizationHeader);
    }

    public String query(String messageBody) {
        try {
            Request requestMessageObject = new XMLDeserializer().getObjectFromMessage(messageBody, Request.class);
            Object result = impl.query(requestMessageObject.getRequestBody(), authentication);
            return getResponseAsString(result);
        } catch (DeserializationException e) {
            responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName() + ": " + e.getMessage());
            try {
                return serializer.serializeObject(responseBuilder.getResponse());
            } catch (SerializationException e1) {
                return "Error: " + e1.getClass().getName() + ": " + e1.getMessage();
            }
        }
    }

}

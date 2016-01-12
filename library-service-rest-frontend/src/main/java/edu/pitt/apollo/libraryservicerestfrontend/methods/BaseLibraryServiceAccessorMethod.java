package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.LibraryServiceImpl;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utils.ResponseMessageBuilder;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/7/15.
 */
public class BaseLibraryServiceAccessorMethod {

    protected final Authentication authentication;
    protected final Serializer serializer;
    protected final LibraryServiceImpl impl;
    protected final ResponseMessageBuilder responseBuilder;
    protected final Class responseClass;
    protected final ObjectSerializationInformation serializationInformation;

    public BaseLibraryServiceAccessorMethod(String username, String password, SerializationFormat serializationFormat, Class responseClass) throws UnsupportedSerializationFormatException {

        authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);

        responseBuilder = new ResponseMessageBuilder();

        serializer = SerializerFactory.getSerializer(serializationFormat);

        impl = new LibraryServiceImpl();
        this.responseClass = responseClass;

        serializationInformation = new ObjectSerializationInformation();
        serializationInformation.setClassNameSpace(Serializer.LIBRARY_SERVICE_NAMESPACE);
        serializationInformation.setClassName(responseClass.getClass().getSimpleName());
        serializationInformation.setFormat(SerializationFormat.XML);

    }

    public String getResponseAsString(Object result) {
        try {
            String serializedObject = serializer.serializeObject(result);
            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE).setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);

        } catch ( SerializationException ex) {
            responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        try {
            return serializer.serializeObject(responseBuilder.getResponse());
        } catch (SerializationException e) {
            return "Error: " + e.getMessage();
        }
    }
}

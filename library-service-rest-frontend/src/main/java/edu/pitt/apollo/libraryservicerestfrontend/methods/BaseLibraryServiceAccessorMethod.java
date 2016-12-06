package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.LibraryServiceImpl;


import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;

import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.apollo.utils.ResponseMessageBuilder;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.SerializerFactory;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

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
        //TODO:Update to latest authentication scheme
        /*authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);*/


        responseBuilder = new ResponseMessageBuilder();



        switch (serializationFormat) {
            case JSON:
                serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.JSON, Arrays.asList(ApolloClassList.classList));
                break;
            case XML:
                serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.XML, Arrays.asList(ApolloClassList.classList));
                break;
            default:
                serializer = null;
        }


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

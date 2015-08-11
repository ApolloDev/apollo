package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.BrokerServiceImpl;
import edu.pitt.apollo.LibraryServiceImpl;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utils.ResponseMessageBuilder;

/**
 * Created by jdl50 on 8/7/15.
 */
public class BaseLibraryServiceAccessorMethod {

    protected final Authentication authentication;
    protected final Serializer serializer;
    protected final LibraryServiceImpl impl;
    protected final ResponseMessageBuilder responseBuilder;

    public BaseLibraryServiceAccessorMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {

        authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);

        responseBuilder = new ResponseMessageBuilder();

        serializer = SerializerFactory.getSerializer(serializationFormat);

        impl = new LibraryServiceImpl();

    }

}

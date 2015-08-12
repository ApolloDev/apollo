package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerResult;
import edu.pitt.apollo.service.apolloservice.v3_0_2.AddLibraryItemContainer;
import edu.pitt.apollo.service.apolloservice.v3_0_2.AddLibraryItemContainerResponse;
import edu.pitt.apollo.services_common.v3_0_2.*;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.XMLDeserializer;
import edu.pitt.apollo.utils.ResponseDeserializer;
import edu.pitt.apollo.utils.ResponseMessageBuilder;
import org.springframework.http.HttpStatus;
import sun.security.krb5.internal.crypto.Des;

/**
 * Created by jdl50 on 8/7/15.
 */
public class AddLibraryItemMethod extends BaseLibraryServiceAccessorMethod {

    public AddLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, AddLibraryItemContainerResult.class);
    }

    public String addLibraryItem(String messageBody) {
        try {
            Object result = impl.addLibraryItemContainer((AddLibraryItemContainerMessage) ResponseDeserializer.deserialize(messageBody));
            return getResponseAsString(result);
        } catch (DeserializationException | UnsupportedSerializationFormatException e) {
            return "Error: " + e.getMessage();
        }
    }
}

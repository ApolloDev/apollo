package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_2.LibraryItemContainer;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import edu.pitt.apollo.utils.ResponseDeserializer;

/**
 * Created by jdl50 on 8/7/15.
 */
public class AddLibraryItemMethod extends BaseLibraryServiceAccessorMethod {

    public AddLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, AddLibraryItemContainerResult.class);
    }

    public String addLibraryItem(String messageBody, String comment) {
        try {
            AddLibraryItemContainerMessage message = new AddLibraryItemContainerMessage();
            LibraryItemContainer container = (LibraryItemContainer) ResponseDeserializer.deserialize(messageBody);

            message.setAuthentication(authentication);
            message.setLibraryItemContainer(container);
            message.setComment(comment);

            Object result = impl.addLibraryItemContainer(message);
            return getResponseAsString(result);
        } catch (DeserializationException | UnsupportedSerializationFormatException e) {
            return "Error: " + e.getMessage();
        }
    }
}

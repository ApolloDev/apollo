package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_2.UpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v3_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utils.ResponseDeserializer;
import edu.pitt.apollo.utils.ResponseMessageBuilder;
import org.springframework.http.HttpStatus;

/**
 * Created by jdl50 on 8/12/15.
 */
public class UpdateLibraryItemMethod extends BaseLibraryServiceAccessorMethod {
    public UpdateLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, UpdateLibraryItemContainerResult.class);
    }

    public String updateLibraryItem(String messageBody) {
        try {
            UpdateLibraryItemContainerMessage updateLibraryItemContainerMessage = (UpdateLibraryItemContainerMessage) ResponseDeserializer.deserialize(messageBody);
            Object result = impl.updateLibraryItemContainer(updateLibraryItemContainerMessage);
            return getResponseAsString(result);
        } catch (DeserializationException | UnsupportedSerializationFormatException e) {
            return "Error: " + e.getMessage();
        }
    }

}

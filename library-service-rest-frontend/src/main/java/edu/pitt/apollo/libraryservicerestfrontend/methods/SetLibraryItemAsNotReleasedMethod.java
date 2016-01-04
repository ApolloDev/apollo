package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v4_0.SetLibraryItemAsNotReleasedMessage;
import edu.pitt.apollo.library_service_types.v4_0.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;

/**
 * Created by jdl50 on 8/12/15.
 */
public class SetLibraryItemAsNotReleasedMethod extends BaseLibraryServiceAccessorMethod {
    public SetLibraryItemAsNotReleasedMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, SetLibraryItemAsNotReleasedResult.class);
    }

    public String hideLibraryItem(int urn) {
        SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage = new SetLibraryItemAsNotReleasedMessage();
        setLibraryItemAsNotReleasedMessage.setAuthentication(authentication);
        setLibraryItemAsNotReleasedMessage.setUrn(urn);
        return getResponseAsString(impl.setLibraryItemAsNotReleased(setLibraryItemAsNotReleasedMessage));
    }
}

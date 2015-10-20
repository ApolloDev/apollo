package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_1_0.GetRevisionsResult;
import edu.pitt.apollo.library_service_types.v3_1_0.GetVersionsMessage;
import edu.pitt.apollo.services_common.v3_1_0.SerializationFormat;

/**
 * Created by jdl50 on 8/13/15.
 */
public class GetVersionNumbersForLibraryItemMethod extends BaseLibraryServiceAccessorMethod {


    public GetVersionNumbersForLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, GetRevisionsResult.class);
    }

    public String getVersionNumbersForLibraryItem(int urn) {
        GetVersionsMessage getVersionsMessage = new GetVersionsMessage();
        getVersionsMessage.setAuthentication(authentication);
        getVersionsMessage.setUrn(urn);
        return getResponseAsString(impl.getVersionNumbersForLibraryItem(getVersionsMessage));
    }
}

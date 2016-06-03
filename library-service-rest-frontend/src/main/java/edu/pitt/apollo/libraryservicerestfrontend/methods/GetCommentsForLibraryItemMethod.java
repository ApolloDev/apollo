package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_1_0.GetCommentsMessage;
import edu.pitt.apollo.library_service_types.v3_1_0.GetCommentsResult;
import edu.pitt.apollo.service.libraryservice.v3_1_0.GetCommentsForLibraryItem;
import edu.pitt.apollo.services_common.v3_1_0.SerializationFormat;

/**
 * Created by jdl50 on 8/13/15.
 */
public class GetCommentsForLibraryItemMethod extends BaseLibraryServiceAccessorMethod {

    public GetCommentsForLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, GetCommentsResult.class);
    }

    public String getCommentsForLibraryItem(int urn, int version) {
        GetCommentsMessage getCommentsMessage = new GetCommentsMessage();
        getCommentsMessage.setAuthentication(authentication);
        getCommentsMessage.setUrn(urn);
        getCommentsMessage.setVersion(version);
        GetCommentsResult result = impl.getCommentsForLibraryItem(getCommentsMessage);
        return getResponseAsString(result);
    }
}

package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v4_0.GetLibraryItemURNsMessage;
import edu.pitt.apollo.library_service_types.v4_0.GetLibraryItemURNsResult;
import edu.pitt.apollo.service.libraryservice.v4_0.GetLibraryItemURNs;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;

/**
 * Created by jdl50 on 8/13/15.
 */
public class GetLibraryItemUrnsMethod extends  BaseLibraryServiceAccessorMethod {

    public GetLibraryItemUrnsMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, GetLibraryItemURNsResult.class);
    }

    public String getLibraryItemUrns(String itemType) {
        GetLibraryItemURNsMessage getLibraryItemURNs = new GetLibraryItemURNsMessage();
        getLibraryItemURNs.setAuthentication(authentication);
        getLibraryItemURNs.setItemType(itemType);
        return getResponseAsString(impl.getLibraryItemURNs(getLibraryItemURNs));
    }
}

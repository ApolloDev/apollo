package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v4_0.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v4_0.GetLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;

/**
 * Created by jdl50 on 8/11/15.
 */
public class GetLibraryItemMethod extends BaseLibraryServiceAccessorMethod {
    public GetLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, GetLibraryItemContainerResult.class);
    }

    public String getLibraryItem(int urn, Integer version) {

        GetLibraryItemContainerMessage getLibraryItemContainerMessage = new GetLibraryItemContainerMessage();
        getLibraryItemContainerMessage.setAuthentication(authentication);
        getLibraryItemContainerMessage.setUrn(urn);
        getLibraryItemContainerMessage.setVersion(version);
        Object result = impl.getLibraryItemContainer(getLibraryItemContainerMessage);


        return getResponseAsString(result);


    }


}

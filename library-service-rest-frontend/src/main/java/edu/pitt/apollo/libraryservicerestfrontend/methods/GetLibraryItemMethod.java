package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.GetLibraryItemContainerResult;
import edu.pitt.apollo.service.apolloservice.v3_0_2.GetLibraryItemContainer;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareIdentification;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utils.ResponseMessageBuilder;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;

/**
 * Created by jdl50 on 8/11/15.
 */
public class GetLibraryItemMethod extends BaseLibraryServiceAccessorMethod {
    public GetLibraryItemMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, GetLibraryItemContainerResult.class);
    }

    public String getLibraryItem(int urn, int version) {

        GetLibraryItemContainerMessage getLibraryItemContainerMessage = new GetLibraryItemContainerMessage();
        getLibraryItemContainerMessage.setAuthentication(authentication);
        getLibraryItemContainerMessage.setUrn(urn);
        getLibraryItemContainerMessage.setVersion(version);
        Object result = impl.getLibraryItemContainer(getLibraryItemContainerMessage);


        return getResponseAsString(result);


    }


}

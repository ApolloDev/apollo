package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_0_2.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.SetReleaseVersionResult;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;

/**
 * Created by jdl50 on 8/12/15.
 */
public class SetLibraryItemReleaseVersionMethod extends BaseLibraryServiceAccessorMethod {
    public SetLibraryItemReleaseVersionMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, SetReleaseVersionResult.class);
    }

    public String setLibraryItemReleaseVersion(int urn, int version, String comment) {
        SetReleaseVersionMessage setReleaseVersionMessage = new SetReleaseVersionMessage();
        setReleaseVersionMessage.setAuthentication(authentication);
        setReleaseVersionMessage.setUrn(urn);
        setReleaseVersionMessage.setVersion(version);
        setReleaseVersionMessage.setComment(comment);

        return getResponseAsString(impl.setReleaseVersionForLibraryItem(setReleaseVersionMessage));
    }
}

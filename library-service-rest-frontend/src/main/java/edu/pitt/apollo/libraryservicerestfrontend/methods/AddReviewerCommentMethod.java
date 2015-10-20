package edu.pitt.apollo.libraryservicerestfrontend.methods;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_1_0.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v3_1_0.AddReviewerCommentResult;
import edu.pitt.apollo.services_common.v3_1_0.SerializationFormat;

/**
 * Created by jdl50 on 8/13/15.
 */
public class AddReviewerCommentMethod extends BaseLibraryServiceAccessorMethod {
    public AddReviewerCommentMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
        super(username, password, serializationFormat, AddReviewerCommentResult.class);
    }

    public String addReviewerComment(int urn, int version, String comment) {
        AddReviewerCommentMessage addReviewerCommentMessage = new AddReviewerCommentMessage();
        addReviewerCommentMessage.setAuthentication(authentication);
        addReviewerCommentMessage.setUrn(urn);
        addReviewerCommentMessage.setVersion(version);
        addReviewerCommentMessage.setComment(comment);
        Object result = impl.addReviewerCommentToLibraryItem(addReviewerCommentMessage);
        return getResponseAsString(result);
    }
}

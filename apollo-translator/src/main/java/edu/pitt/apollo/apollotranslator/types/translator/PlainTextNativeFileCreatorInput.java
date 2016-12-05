package edu.pitt.apollo.apollotranslator.types.translator;


/**
 * Created by nem41 on 7/24/15.
 */
public class PlainTextNativeFileCreatorInput extends NativeFileCreatorInput {

    String commentToken;

    public String getCommentToken() {
        return commentToken;
    }

    public void setCommentToken(String commentToken) {
        this.commentToken = commentToken;
    }
}

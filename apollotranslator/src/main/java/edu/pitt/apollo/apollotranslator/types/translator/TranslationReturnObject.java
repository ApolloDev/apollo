package edu.pitt.apollo.apollotranslator.types.translator;

import java.util.List;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Sep 19, 2013 Time:
 * 4:54:49 PM Class: TranslationReturnObject IDE: NetBeans 6.9.1
 */
public abstract class TranslationReturnObject {

    protected String commentFromJavascriptFunction;
    protected List<String> warningsFromJavascriptFunction;

    /**
     * @return the comment
     */
    public String getCommentFromJavascriptFunction() {
        if (commentFromJavascriptFunction == null) {
            return "";
        }
        return commentFromJavascriptFunction;
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setCommentFromJavascriptFunction(String comment) {
        if (!"".equals(comment)) {
            this.commentFromJavascriptFunction = comment;
        }
    }

    /**
     * @return the warning
     */
    public List<String> getWarningsFromJavascriptFunction() {
        return warningsFromJavascriptFunction;
    }

    /**
     * @param warnings
     *            the warning to set
     */
    public void setWarningsFromJavascriptFunction(List<String> warnings) {
        this.warningsFromJavascriptFunction = warnings;
    }
}

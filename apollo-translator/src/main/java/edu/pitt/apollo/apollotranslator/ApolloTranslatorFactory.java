package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslatorInput;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 28, 2014
 * Time: 4:52:31 PM
 * Class: ApolloTranslatorFactory
 * IDE: NetBeans 6.9.1
 */
public class ApolloTranslatorFactory {

    public static final String DEFAULT_COMMENT_TOKEN = "#";

    public static ApolloTranslator getTranslator(TranslatorInput translatorInput) {

        translatorInput.setCommentToken(DEFAULT_COMMENT_TOKEN);
        return new ApolloTranslator(translatorInput);
    }
}

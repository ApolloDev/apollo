package edu.pitt.apollo.apollotranslator.instructions;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.SelectorResult;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2013
 * Time: 5:46:04 PM
 * Class: SelectorUtility
 * IDE: NetBeans 6.9.1
 */
public class SelectorUtility {

    private static final Pattern ATTRIBUTE_VALUE_PATTERN = Pattern.compile("(.+?)=(.+)");
    private static final Pattern SELECTOR_AND_INDEX_PATTERN = Pattern.compile("(.*?)(\\[([a-zA-Z]+?=.+?)\\](?:\\[(.*?)\\])?)(.*)");
    private static final String SELECTOR_OPTIONS_DELIMITER = ";";

    public static SelectorResult getSelectorResult(String string) throws ApolloTranslatorException {

        Matcher matcher = SELECTOR_AND_INDEX_PATTERN.matcher(string);

        if (matcher.find()) {
            SelectorResult result = new SelectorResult();

            result.setObjectOrFieldType(matcher.group(1));
            result.setSelectorAndIndexString(matcher.group(2));

            String selectorContents = matcher.group(3);
            result.setOptionalIndex(matcher.group(4));

            Map<String, String> optionsMap = new HashMap<String, String>();
            result.setSelectorOptions(optionsMap);

            String[] options = selectorContents.split(SELECTOR_OPTIONS_DELIMITER);
            for (String option : options) {
                if (!option.isEmpty()) {

                    Matcher attributeValuePairMatcher = ATTRIBUTE_VALUE_PATTERN.matcher(option);
                    if (!attributeValuePairMatcher.find()) {
                        throw new ApolloTranslatorException("The string " + string + " was specified as a selector but"
                                + " has an incorrect attribute=value statement");
                    }

                    String attribute = attributeValuePairMatcher.group(1);
                    String value = attributeValuePairMatcher.group(2);
                    optionsMap.put(attribute, value);
                }
            }
            return result;
        } else {
            return null;
        }
    }
}

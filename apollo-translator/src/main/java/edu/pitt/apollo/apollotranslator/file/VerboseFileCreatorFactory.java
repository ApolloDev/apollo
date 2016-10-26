package edu.pitt.apollo.apollotranslator.file;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Created by nem41 on 7/24/15.
 */
public class VerboseFileCreatorFactory {

    public static VerboseFileCreator getVerboseFileWriter(TranslationMode translationMode,
                                                         String simulatorName, String simulatorVersion,
                                                         String linePrefix, Map<String,
            List<SetterReturnObject>> apolloLabelSetterReturnObjectMap) throws FileNotFoundException, ApolloTranslatorException {

        switch (translationMode) {

            case PLAIN_TEXT:
                return new PlainTextVerboseFileCreator(simulatorName, simulatorVersion, linePrefix, apolloLabelSetterReturnObjectMap);
            case XML:
                return null; // XML verbose not supported right now
            default:
                return null;
        }

    }

}

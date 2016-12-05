package edu.pitt.apollo.apollotranslator.file;

import edu.pitt.apollo.apollotranslator.types.translator.*;

import java.util.List;

/**
 * Created by nem41 on 7/24/15.
 */
public class NativeFileCreatorFactory {

    public static NativeFileCreator getNativeFileWriter(TranslationMode translationMode,
                                                       NativeFileCreatorInput nativeFileCreatorInput,
                                                       List<SetterReturnObject> setterReturnObjects) {

        switch (translationMode) {

            case PLAIN_TEXT:
                return new PlainTextNativeFileCreator((PlainTextNativeFileCreatorInput) nativeFileCreatorInput, setterReturnObjects);
            case XML:
                return new XMLNativeFileCreator((XMLNativeFileCreatorInput) nativeFileCreatorInput, setterReturnObjects);
            default:
                return null;

        }

    }

}

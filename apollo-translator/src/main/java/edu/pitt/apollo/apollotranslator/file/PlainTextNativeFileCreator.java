package edu.pitt.apollo.apollotranslator.file;

import edu.pitt.apollo.apollotranslator.ApolloLogger;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.PlainTextNativeFileCreatorInput;
import edu.pitt.apollo.apollotranslator.types.translator.PlainTextTranslationReturnObject;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 23, 2013
 * Time: 5:37:54 PM
 * Class: PlainTextNativeFileCreator
 * IDE: NetBeans 6.9.1
 */
public class PlainTextNativeFileCreator extends NativeFileCreator {

    private final String commentToken;

    public PlainTextNativeFileCreator(PlainTextNativeFileCreatorInput plainTextNativeFileWriterInput, List<SetterReturnObject> setterReturnObjects) {
        super(plainTextNativeFileWriterInput, setterReturnObjects);
        this.commentToken = plainTextNativeFileWriterInput.getCommentToken();
    }

    @Override
    public Map<String, ByteArrayOutputStream> createNativeFiles() throws ApolloTranslatorException, IOException {
        Map<String, ByteArrayOutputStream> map = new HashMap<String, ByteArrayOutputStream>();
        for (SetterReturnObject sro : setterReturnObjects) {

//            String filePath = outputDirectory + File.separator
//                    + sro.getConfigurationFile();

            String fileName = sro.getConfigurationFile();
            PlainTextTranslationReturnObject tro = (PlainTextTranslationReturnObject) sro.getTranslationReturnObject();

            if (tro == null || tro.getValueFromJavascriptFunction() == null
                    || tro.getValueFromJavascriptFunction().isEmpty()) {
                // if the value from the JavaScript function is empty we don't want
                // to print the native term
                continue;
            }

            if (sro.getConfigurationFile().length() == 0) {
                // throw new ApolloTranslatorException(
                // PlainTextTranslationInstructionsFileLoader.CONFIG_FILE_NAME_COLUMN_NAME
                // + " not specified for native_parameter: "
                // + sro.getTranslationReturnObject().getNativeTerm());
                ApolloLogger.log(Level.INFO,
                        AbstractTranslationInstructionsFileLoader.CONFIG_FILE_NAME_COLUMN_NAME
                        + " not specified for native_parameter: "
                        + tro.getNativeTerm());
                continue;
            }
            // end fix for Issue #49

//            File file = new File(filePath);
//            FileWriter ps = map.get(file);
            ByteArrayOutputStream baos = map.get(fileName);
            if (baos == null) {
//                try {
                baos = new ByteArrayOutputStream();
                map.put(fileName, baos);
//                } catch (FileNotFoundException ex) {
//                    throw new FileNotFoundException("Could not open file "
//                            + filePath + " for writing");
//                }
            }

            String jsCommentString = tro.getCommentFromJavascriptFunction();
            if (jsCommentString != null && !jsCommentString.isEmpty()) {
                jsCommentString = commentToken + jsCommentString + "\n";
            } else {
                jsCommentString = "";
            }
            String configCommentString = sro.getConfigurationFileComment();
            if (configCommentString != null && !configCommentString.isEmpty()) {
                configCommentString = commentToken + configCommentString
                        + "\n";
            } else {
                configCommentString = "";
            }
            String globalCommentString = sro.getGlobalComment();
            if (globalCommentString != null && !globalCommentString.isEmpty()) {
                globalCommentString = commentToken + globalCommentString
                        + "\n";
            } else {
                globalCommentString = "";
            }

            String commentString = globalCommentString + configCommentString
                    + jsCommentString;
            String line = commentString + tro.getNativeTerm() + tro.getDelimiter()
                    + tro.getValueFromJavascriptFunction() + "\n";
            byte[] lineBytes = line.getBytes();
            baos.write(lineBytes, 0, lineBytes.length);

        }
//        Iterator<File> it = map.keySet().iterator();
//        while (it.hasNext()) {
//            map.get(it.next()).close();
//        }
        
        return map;
    }
}

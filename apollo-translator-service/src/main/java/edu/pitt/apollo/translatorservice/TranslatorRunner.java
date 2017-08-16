package edu.pitt.apollo.translatorservice;

import edu.pitt.apollo.apollotranslator.ApolloTranslator;
import edu.pitt.apollo.apollotranslator.ApolloTranslatorFactory;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.types.translator.TranslatorInput;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.Md5UtilsException;
import edu.pitt.apollo.filestore_service_types.v4_0_2.FileIdentification;
import edu.pitt.apollo.services_common.v4_0_2.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_2.ContentDataTypeEnum;
import edu.pitt.apollo.translatorservice.exception.TranslatorServiceException;
import edu.pitt.apollo.translatorservice.utility.TranslatorServiceUtils;
import org.codehaus.plexus.util.ExceptionUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 27, 2015
 * Time: 1:31:18 PM
 * Class: TranslatorRunner
 */
public class TranslatorRunner {

    final BigInteger runId;
    private final TranslatorInput translatorInput;

    public TranslatorRunner(BigInteger runId, TranslatorInput translatorInput) {
        super();
        this.runId = runId;
        this.translatorInput = translatorInput;
    }

    public void runTranslator() throws TranslatorServiceException, Md5UtilsException {
        ApolloTranslator translator = ApolloTranslatorFactory.getTranslator(translatorInput);

        try {

            // if the previous run had an error, try to re-run
            Map<String, ByteArrayOutputStream> configFiles = translator.translate();

            Iterator<String> configFilesIt = configFiles.keySet().iterator();
            while (configFilesIt.hasNext()) {
                String dataContentLabel = configFilesIt.next();
                ByteArrayOutputStream byteArrayOutputStream = configFiles.get(dataContentLabel);
                String dataContent = byteArrayOutputStream.toString();

//                int dataContentKey = dbUtils.addTextDataContent(dataContent);
//                int runDataDescriptionId = dbUtils.getRunDataDescriptionId(
//                        ContentDataFormatEnum.TEXT, dataContentLabel, ContentDataTypeEnum.CONFIGURATION_FILE,
//                        TranslatorServiceUtils.getTranslatorSoftwareIdentification(),
//                        translatorInput.getRunSimulationMessage().getSoftwareIdentification());
//                // int runDataId = the following line returns the runDataId, but
//                // it's not used at this point.
//                dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);
//                dbUtils.close();

				FileIdentification fileIdentification = new FileIdentification();
				fileIdentification.setFormat(ContentDataFormatEnum.TEXT);
				fileIdentification.setType(ContentDataTypeEnum.CONFIGURATION_FILE);
				fileIdentification.setLabel(dataContentLabel);

                  TranslatorServiceUtils.uploadTextFileContent(dataContent, runId, fileIdentification);
            }
        } catch (FilestoreException | IOException | ApolloTranslatorException ex) {
            throw getTranslatorServiceException(ex);
        }
    }

    public TranslatorServiceException getTranslatorServiceException(Exception ex) {
        String errorMessage;
        errorMessage = ex.getClass().getName() + " running translator: " + ex.getMessage();
        String stackTrace = ExceptionUtils.getStackTrace(ex);
        errorMessage += " - " + stackTrace;
        return new TranslatorServiceException(errorMessage);

    }

}

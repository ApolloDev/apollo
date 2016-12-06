package edu.pitt.apollo.translatorservice.thread;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.translatorservice.exception.TranslatorServiceException;
import edu.pitt.apollo.translatorservice.types.TranslatorResources;
import edu.pitt.apollo.translatorservice.utility.TranslatorServiceUtils;
import static edu.pitt.apollo.translatorservice.utility.TranslatorServiceUtils.ERROR_PREFIX;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 27, 2015 Time:
 * 2:20:54 PM Class: TranslationThread
 */
public abstract class TranslationThread extends Thread {

    protected static final Logger logger = LoggerFactory.getLogger(TranslationThread.class);
    protected String simulatorTranslationInstructionsFilename;
    protected String simulatorJavascriptMethodsFilename;
    protected TranslationMode translationMode;
    protected Integer urnOfBaseConfigurationInLibrary;
    protected String fileLocationOfBaseConfiguration;
    protected final BigInteger runId;
    protected int numThreads;
    protected AtomicBoolean runFailed = new AtomicBoolean(false);

    public TranslationThread(BigInteger runId) {
        this.runId = runId;
    }

    private void setTranslatorResourceFilePaths() throws TranslatorServiceException {
        SoftwareIdentification softwareId = getSoftwareIdForRun();
        String simulatorName = softwareId.getSoftwareName().toLowerCase();
        TranslatorResources translatorResources = TranslatorServiceUtils.getTranslatorResourceMap().get(simulatorName);
        simulatorTranslationInstructionsFilename = translatorResources.getTranslationInstructionsPath();
        simulatorJavascriptMethodsFilename = translatorResources.getJavaScriptPath();
        translationMode = translatorResources.getTranslationMode();
        urnOfBaseConfigurationInLibrary = translatorResources.getUrnOfBaseConfigurationInLibrary();
        fileLocationOfBaseConfiguration = translatorResources.getFileLocationOfBaseConfiguration();
		numThreads = TranslatorServiceUtils.getNumThreads();
    }

    private SoftwareIdentification getSoftwareIdForRun() throws TranslatorServiceException {
        SoftwareIdentification softwareId;
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils();
            softwareId = dbUtils.getSoftwareIdentificationForRun(runId);
            dbUtils.close();
            return softwareId;
        } catch (ApolloDatabaseKeyNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new TranslatorServiceException("Could not get software id for run id " + runId);
        } catch (ApolloDatabaseException ex) {
            logger.error(ex.getMessage());
            throw new TranslatorServiceException("Could not get software id for run id " + runId);
        }
    }

    protected abstract void runTranslation() throws TranslatorServiceException;

    @Override
    public void run() {
        try {
            setTranslatorResourceFilePaths();
            runTranslation();
            if (!isRunFailed()) {
                TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.TRANSLATION_COMPLETED, "Translation complete!");
            }
        } catch (TranslatorServiceException ex) {
            TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.FAILED, ex.getMessage());
        }
    }

    public synchronized void setRunFailed(String message) {
        if (!isRunFailed()) {
            runFailed.set(true);
            TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.FAILED, message);
        }
    }

    public boolean isRunFailed() {
        return runFailed.get();
    }

}

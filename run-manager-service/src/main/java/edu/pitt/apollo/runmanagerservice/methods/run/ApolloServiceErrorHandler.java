package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessImpl;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.utilities.ErrorUtils;

import static edu.pitt.apollo.utilities.ErrorUtils.checkFileExists;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;
import static edu.pitt.apollo.utilities.ErrorUtils.readErrorFromFile;
import static edu.pitt.apollo.utilities.ErrorUtils.writeErrorToFile;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 10:46:37 AM
 * Class: ApolloServiceErrorHandler
 * IDE: NetBeans 6.9.1
 */
public class ApolloServiceErrorHandler extends ErrorUtils {

    static Logger logger = LoggerFactory.getLogger(ApolloServiceErrorHandler.class);
    public static final BigInteger JOB_ID_FOR_FATAL_ERROR = new BigInteger("-1");
    public static final String RUN_ERROR_PREFIX = "ApolloServiceError";
    private static final String ERROR_FILENAME = "run_errors.txt";
    private static final String ERROR_FILE_DIR = "errors";


    public static void reportError(String message, BigInteger runId, Authentication authentication) {
        try {
            writeErrorToDataService(message, runId, authentication);
        } catch (DataServiceException e) {
            logger.debug("Error writing error: {} to database for runId {}, error was: {}", message, runId, e.getMessage());
            logger.debug("Attempting to write error to disk.");
            try {
                writeErrorToErrorFile(message, runId);
            } catch (IOException e1) {
                logger.debug("Error writing error: {} to dick for runId {}, error was: {}", message, runId, e1.getMessage());
                e1.printStackTrace();
            }

        }
    }


    public static String getErrorFilename() {
        return ApolloServiceConstants.APOLLO_DIR + ERROR_FILENAME;
    }

    public static File getErrorFile(long runId) {
        return new File(ApolloServiceConstants.APOLLO_DIR + ERROR_FILE_DIR + File.separator + "error-"
                + Long.valueOf(runId) + ".txt");
    }

    public static long getErrorRunId() {
        return -System.currentTimeMillis();
    }

    public static long writeErrorWithErrorId(String message) throws IOException {
        long id = getErrorRunId();
        ErrorUtils.writeErrorToFile(message, getErrorFile(id));
        return id;
    }

    public static boolean checkErrorFileExists(long runId) {
        return checkFileExists(getErrorFile(runId));
    }

    public static String readErrorFromErrorFile(long runId) {
        return readErrorFromFile(getErrorFile(runId));
    }


    public static void writeErrorToErrorFile(String message, BigInteger runId) throws IOException {
        writeErrorToFile(message, getErrorFile(runId.longValue()));
    }

    public static void writeErrorToDataService(String message, BigInteger runId, Authentication authentication) throws DataServiceException {
        DatastoreAccessImpl dataServiceDao = new DatastoreAccessImpl();

        MethodCallStatus status = new MethodCallStatus();
        status.setStatus(MethodCallStatusEnum.FAILED);
        status.setMessage(message);

        try {
            dataServiceDao.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, message, authentication);
        } catch (RunManagementException ex) {
            try {
                writeErrorToErrorFile("RunManagementException writing status for run " + runId + ": " + ex.getMessage(), runId);
            } catch (IOException ex2) {
                logger.error("Error writing error file!: " + ex2.getMessage());
            }
        }
    }
}

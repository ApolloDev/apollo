package edu.pitt.apollo.dataservice.error;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.utilities.ErrorUtils;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    public static void reportError(String message, BigInteger runId) {
        try {
            writeErrorToDatabase(message, runId);
        } catch (ApolloDatabaseException e) {
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

    //    public static String getErrorRunId() {
//        return "-" + RUN_ERROR_PREFIX
//                + Long.toString(System.currentTimeMillis());
//    }
    public static long getErrorRunId() {
        return -System.currentTimeMillis();
    }

    //    public static void writeErrorToErrorFile(String message, int runID) throws IOException {
//        ErrorUtils.writeErrorToFile(message, getErrorFile(runID));
//    }
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

    public static void writeErrorToDatabase(String message, BigInteger runId) throws ApolloDatabaseException {
        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {
            MethodCallStatus status = new MethodCallStatus();
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage(message);

            try {
                dbUtils.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, message);
            } catch (ApolloDatabaseException ex) {
                try {
                    writeErrorToErrorFile("ApolloDatabaseException writing status for run " + runId + ": " + ex.getMessage(), runId);
                } catch (IOException ex2) {
                    logger.error("Error writing error file!: " + ex2.getMessage());
                }
            }
        }

    }

//    public synchronized static String getError(String runId) {
//        File errorFile = new File(getErrorFilename());
//        if (errorFile.exists()) {
//            try {
//                BufferedReader br = new BufferedReader(
//                        new FileReader(errorFile));
//                String line = "";
//                while ((line = br.readLine()) != null) {
//                    String err[] = line.split("\t");
//                    if (runId.equals(err[0])) {
//                        br.close();
//                        return err[1];
//                    }
//                }
//                br.close();
//                return "Can't find error for given run id: " + runId;
//            } catch (IOException e) {
//                return "Error reading error file: " + e.getMessage();
//            }
//        } else {
//            return "Error file doesn't exist!";
//        }
//    }
//    public synchronized static void reportError(String runId, String error) {
//        File errorFile = new File(getErrorFilename());
//        FileWriter fw;
//        try {
//            fw = new FileWriter(errorFile, true);
//            fw.write(runId + "\t" + error + "\n");
//            fw.close();
//        } catch (IOException e) {
//            // eat the error for now
//        }
//    }
}

package edu.pitt.apollo.apolloservice.error;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.ErrorUtils;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 10:46:37 AM
 * Class: ApolloServiceErrorHandler
 * IDE: NetBeans 6.9.1
 */
public class ApolloServiceErrorHandler extends ErrorUtils {

    public static final BigInteger JOB_ID_FOR_FATAL_ERROR = new BigInteger("-1");
    public static final String RUN_ERROR_PREFIX = "ApolloServiceError";
    private static final String ERROR_FILENAME = "run_errors.txt";
    private static final String ERROR_FILE_DIR = "errors";

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

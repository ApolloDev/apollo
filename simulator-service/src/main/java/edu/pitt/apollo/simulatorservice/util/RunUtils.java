package edu.pitt.apollo.simulatorservice.util;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import java.math.BigInteger;
import org.apache.commons.codec.digest.DigestUtils;

public class RunUtils {

	public static void updateStatus(BigInteger runId, MethodCallStatusEnum statusEnum, String message) {
		try {
			ApolloDbUtils dbUtils = new ApolloDbUtils();
			dbUtils.updateStatusOfRun(runId, statusEnum, message);
		} catch (ApolloDatabaseException ex) {
			ex.printStackTrace();
		}
	}

//    public static synchronized String createRunDir(String directory)
//            throws IOException {
//
//        File dir = new File(directory);
//        dir.mkdirs();
//
//        return dir.getAbsolutePath();
//    }
//    public static synchronized String setStarted(String directory) throws IOException {
//
//        createRunDir(directory);
//
//        //should be md5 hash of run id
//        File started = new File(directory + File.separator + "started.txt");
//        started.createNewFile();
//
//        return directory;
//    }
//    public static synchronized void setStatusFile(String directory, MethodCallStatusEnum status)
//            throws IOException {
//
//        File finished = new File(directory + File.separator + status.toString().toLowerCase() + ".txt");
//        finished.createNewFile();
//    }
//
//    public static boolean statusFileExists(String directory, MethodCallStatusEnum status) {
//        return new File(directory + File.separator + status.toString().toLowerCase() + ".txt").exists();
//    }
//    public static synchronized void setFinished(String directory)
//            throws IOException {
//
//        File finished = new File(directory + File.separator + "finished.txt");
//        finished.createNewFile();
//    }
//    public static synchronized void setError(String directory, String msg)
//            throws IOException {
//
//        File error = new File(directory + File.separator + "error.txt");
//        error.createNewFile();
//
//        FileWriter fw = new FileWriter(error);
//        fw.write(msg);
//        fw.close();
//    }
//    public static synchronized String getError(String directory) throws IOException {
//
//        File error = new File(directory + File.separator + "error.txt");
//
//        BufferedReader br = new BufferedReader(new FileReader(error));
//        String errorMsg = br.readLine();
//        br.close();
//        return errorMsg;
//
//    }
//
//    public static String getResultsString(String directory) throws FileNotFoundException {
//
//        File file = new File(directory + File.separator + "results.txt");
//        Scanner scanner = new Scanner(file);
//        scanner.nextLine(); // first line is the header
//        String results = scanner.nextLine();
//
//        scanner.close();
//        return results;
//    }
//    public static MethodCallStatus getStatus(String directory, int runId)
//            throws IOException {
//
//        MethodCallStatus status = new MethodCallStatus();
//        File error = new File(directory + File.separator + "error.txt");
////        File finished = new File(directory + File.separator + "finished.txt");
////        File started = new File(directory + File.separator + "started.txt");
//
//        if (error.exists()) {
//            status.setMessage(RunUtils.getError(directory));
//            status.setStatus(MethodCallStatusEnum.FAILED);
//            return status;
//        } else if (statusFileExists(directory, MethodCallStatusEnum.COMPLETED)) {
//            status.setMessage("Run is complete.");
//            status.setStatus(MethodCallStatusEnum.COMPLETED);
//            return status;
//        } else if (statusFileExists(directory, MethodCallStatusEnum.LOG_FILES_WRITTEN)) {
//            status.setMessage("Log files written");
//            status.setStatus(MethodCallStatusEnum.LOG_FILES_WRITTEN);
//            return status;
//        } else if (statusFileExists(directory, MethodCallStatusEnum.RUNNING)) {
//            status.setMessage("Running...");
//            status.setStatus(MethodCallStatusEnum.RUNNING);
//            return status;
//        } else if (SimulatorServiceQueue.isRunQueued(runId)) {
//            status.setMessage("Run is queued");
//            status.setStatus(MethodCallStatusEnum.QUEUED);
//            return status;
//        } else {
//            status.setMessage("Unknown run");
//            status.setStatus(MethodCallStatusEnum.FAILED);
//            return status;
//        }
//    }
	public static String getMd5HashFromString(String string) {

		return DigestUtils.md5Hex(string);
	}
}

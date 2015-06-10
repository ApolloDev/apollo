package edu.pitt.apollo.timeseriesvisualizer.utilities;

import org.apache.commons.codec.digest.DigestUtils;

public class RunUtils {

//    public static synchronized String createRunDir(String directory)
//            throws IOException {
//
//        File dir = new File(directory);
//
//        if (dir.exists()) {
//            FileUtils.cleanDirectory(dir);
//        } else {
//            dir.mkdirs();
//        }
//
//        return dir.getAbsolutePath();
//    }
//
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
//
//    public static synchronized void setFinished(String directory)
//            throws IOException {
//
//        File finished = new File(directory + File.separator + "finished.txt");
//        finished.createNewFile();
//    }
//
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
//
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
//
//    public static MethodCallStatus getStatus(String directory)
//            throws IOException {
//
//        MethodCallStatus status = new MethodCallStatus();
//        File error = new File(directory + File.separator + "error.txt");
//        File finished = new File(directory + File.separator + "finished.txt");
//        File started = new File(directory + File.separator + "started.txt");
//
//        if (error.exists()) {
//            status.setMessage(RunUtils.getError(directory));
//            status.setStatus(MethodCallStatusEnum.FAILED);
//            return status;
//        } else if (finished.exists()) {
//            status.setMessage("Run is complete.");
//            status.setStatus(MethodCallStatusEnum.COMPLETED);
//            return status;
//        } else if (started.exists()) {
//            status.setMessage("Running...");
//            status.setStatus(MethodCallStatusEnum.RUNNING);
//            return status;
//        } else {
//            status.setMessage("Unknown run");
//            status.setStatus(MethodCallStatusEnum.UNKNOWN_RUNID);
//            return status;
//        }
//    }

    public static String getMd5HashFromString(String string) {

        return DigestUtils.md5Hex(string);
    }
}

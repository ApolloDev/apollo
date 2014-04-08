package edu.pitt.apollo.seir.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

import edu.pitt.apollo.SeirSimulatorServiceImpl;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;


public class RunUtils {

//    public static String WORK_DIR = "/Users/jdl50/apollo/work/";
//    public static String WORK_DIR = "/home/jdl/Public/1.3.1/";

//    public static synchronized String getNextId() throws IOException {
//
//        // this method is now only called in a static initializer
//        // add 1000 to the IDs to lessen the chance of using the same IDs twice
//        File dir = new File(WORK_DIR);
//        dir.mkdirs();
//        File f = new File(WORK_DIR + "id.txt");
//        if (!f.exists()) {
//            f.createNewFile();
//            FileWriter fw = new FileWriter(f);
//            fw.write("1001");
//            fw.close();
//            return "1001";
//        } else {
//            BufferedReader br = new BufferedReader(new FileReader(f));
//            String line = br.readLine();
//            int nextId = Integer.valueOf(line);
//            nextId++;
//            nextId += 1000;
//            br.close();
//
//            FileWriter fw = new FileWriter(f);
//            fw.write(String.valueOf(nextId));
//            fw.close();
//
//            return String.valueOf(nextId);
//        }
//
//    }

//    public static synchronized int writeCurrentId(int id) throws IOException {
//
//        File dir = new File(WORK_DIR);
//        dir.mkdirs();
//        File f = new File(WORK_DIR + "id.txt");
//        if (!f.exists()) {
//            f.createNewFile();
//        }
//        FileWriter fw = new FileWriter(f);
//        fw.write(String.valueOf(id));
//        fw.close();
//
//        return id;
//    }

    public static synchronized String createRunDir(String directory)
            throws IOException {

        File dir = new File(directory);
        dir.mkdirs();

        return dir.getAbsolutePath();
    }

    public static synchronized String setStarted(String directory) throws IOException {

        createRunDir(directory);

        //should be md5 hash of run id
        File started = new File(directory + File.separator + "started.txt");
        started.createNewFile();

        return directory;
    }

//    public static synchronized String getWorkDir(String runId) {
//
//        return WORK_DIR + runId + File.separator;
//    }

    public static synchronized void setFinished(String directory)
            throws IOException {

        File finished = new File(directory + File.separator + "finished.txt");
        finished.createNewFile();
    }

    public static synchronized void setError(String directory, String msg)
            throws IOException {

        File error = new File(directory + File.separator + "error.txt");
        error.createNewFile();

        FileWriter fw = new FileWriter(error);
        fw.write(msg);
        fw.close();
    }

    public static synchronized String getError(String directory) throws IOException {

        File error = new File(directory+ File.separator + "error.txt");

        BufferedReader br = new BufferedReader(new FileReader(error));
        String errorMsg = br.readLine();
        br.close();
        return errorMsg;

    }

    public static String getResultsString(String directory) throws FileNotFoundException {

        File file = new File(directory + File.separator + "results.txt");
        Scanner scanner = new Scanner(file);
        scanner.nextLine(); // first line is the header
        String results = scanner.nextLine();

        scanner.close();
        return results;
    }

    public static MethodCallStatus getStatus(String directory, int runId)
            throws IOException {
        
        MethodCallStatus status = new MethodCallStatus();
        File error = new File(directory + File.separator + "error.txt");
        File finished = new File(directory + File.separator + "finished.txt");
        File started = new File(directory + File.separator + "started.txt");

        if (error.exists()) {
            status.setMessage(RunUtils.getError(directory));
            status.setStatus(MethodCallStatusEnum.FAILED);
            return status;
        } else if (finished.exists()) {
            status.setMessage("Run is complete.");
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            return status;
        } else if (started.exists()) {
            status.setMessage("Running...");
            status.setStatus(MethodCallStatusEnum.RUNNING);
            return status;
        } else if (SeirSimulatorServiceImpl.isRunQueued(runId)) {
            status.setMessage("Run is queued");
            status.setStatus(MethodCallStatusEnum.QUEUED);
            return status;
        } else {
            status.setMessage("Unknown run");
            status.setStatus(MethodCallStatusEnum.FAILED);
            return status;
        }
    }

//    public static String getMd5HashFromBytes(byte[] bytes) {
//
//        try {
//            MessageDigest md = null;
//
//            md = MessageDigest.getInstance("MD5");
//
//            md.update(bytes);
//            byte[] digest = md.digest();
//            StringBuilder sb = new StringBuilder();
//
//            for (byte b : digest) {
//                sb.append(Integer.toHexString((int) (b & 0xff)));
//            }
//
//            String md5Hash = sb.toString();
//            return md5Hash;
//
//        } catch (NoSuchAlgorithmException ex) {
//            return null;
//        }
//    }

    public static String getMd5HashFromString(String string) {

        return DigestUtils.md5Hex(string);
    }
}

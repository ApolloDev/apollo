package edu.pitt.apollo.seir.utils;

import edu.pitt.apollo.SeirSimulatorServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class RunUtils {

//    public static String WORK_DIR = "/Users/jdl50/apollo/work/";
    public static String WORK_DIR = "/home/jdl/Public/";

    public static synchronized String getNextId() throws IOException {

        // this method is now only called in a static initializer
        // add 1000 to the IDs to lessen the chance of using the same IDs twice
        File dir = new File(WORK_DIR);
        dir.mkdirs();
        File f = new File(WORK_DIR + "id.txt");
        if (!f.exists()) {
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            fw.write("1001");
            fw.close();
            return "1001";
        } else {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            int nextId = Integer.valueOf(line);
            nextId++;
            nextId += 1000;
            br.close();

            FileWriter fw = new FileWriter(f);
            fw.write(String.valueOf(nextId));
            fw.close();

            return String.valueOf(nextId);
        }

    }

    public static synchronized int writeCurrentId(int id) throws IOException {

        File dir = new File(WORK_DIR);
        dir.mkdirs();
        File f = new File(WORK_DIR + "id.txt");
        if (!f.exists()) {
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(f);
        fw.write(String.valueOf(id));
        fw.close();

        return id;
    }

    public static synchronized String createRunDir(String runId)
            throws IOException {

        File dir = new File(WORK_DIR + runId);
        dir.mkdirs();

        return dir.getAbsolutePath();
    }

    public static synchronized String setStarted(String runId) throws IOException {

        String directory = createRunDir(runId);

        //should be md5 hash of run id
        File started = new File(WORK_DIR + runId + File.separator + "started.txt");
        started.createNewFile();

        return directory;
    }

    public static synchronized String getWorkDir(String runId) {

        return WORK_DIR + runId + File.separator;
    }

    public static synchronized void setFinished(String runId)
            throws IOException {

        File finished = new File(WORK_DIR + runId + File.separator + "finished.txt");
        finished.createNewFile();
    }

    public static synchronized void setError(String runId, String msg)
            throws IOException {

        File error = new File(WORK_DIR + runId + File.separator + "error.txt");
        error.createNewFile();

        FileWriter fw = new FileWriter(error);
        fw.write(msg);
        fw.close();
    }

    public static synchronized String getError(String runId) throws IOException {

        File error = new File(WORK_DIR + runId + File.separator + "error.txt");

        BufferedReader br = new BufferedReader(new FileReader(error));
        String errorMsg = br.readLine();
        br.close();
        return errorMsg;

    }

    public static String getResultsString(String runId) throws FileNotFoundException {

        String runIdHash = RunUtils.getMd5HashFromBytes(runId.getBytes());

        File file = new File(WORK_DIR + runIdHash + File.separator + "results.txt");
        Scanner scanner = new Scanner(file);
        scanner.nextLine(); // first line is the header
        String results = scanner.nextLine();

        scanner.close();
        return results;
    }

    public static RunStatus getStatus(String runId)
            throws IOException {
        String dirName = getMd5HashFromBytes(runId.getBytes());
        if (dirName == null) {
            System.err.println("Directory name from run ID hash was null");
            return null;
        }

        String runIdHash = RunUtils.getMd5HashFromBytes(runId.getBytes());

        RunStatus rs = new RunStatus();
        File error = new File(WORK_DIR + dirName + File.separator + "error.txt");
        File finished = new File(WORK_DIR + dirName + File.separator + "finished.txt");
        File started = new File(WORK_DIR + dirName + File.separator + "started.txt");

        if (error.exists()) {
            rs.setMessage(RunUtils.getError(dirName));
            rs.setStatus(RunStatusEnum.FAILED);
            return rs;
        } else if (finished.exists()) {
            rs.setMessage("Run is complete.");
            rs.setStatus(RunStatusEnum.COMPLETED);
            return rs;
        } else if (started.exists()) {
            rs.setMessage("Running...");
            rs.setStatus(RunStatusEnum.RUNNING);
            return rs;
        } else if (SeirSimulatorServiceImpl.isRunQueued(runIdHash)) {
            rs.setMessage("Run is queued");
            rs.setStatus(RunStatusEnum.QUEUED);
            return rs;
        } else {
            rs.setMessage("Unknown run");
            rs.setStatus(RunStatusEnum.FAILED);
            return rs;
        }
    }

    public static String getMd5HashFromBytes(byte[] bytes) {

        try {
            MessageDigest md = null;

            md = MessageDigest.getInstance("MD5");

            md.update(bytes);
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte b : digest) {
                sb.append(Integer.toHexString((int) (b & 0xff)));
            }

            String md5Hash = sb.toString();
            return md5Hash;

        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }

    public static void main(String args[]) throws IOException {
        System.out.println(RunUtils.getNextId());
        System.out.println(RunUtils.getNextId());
        System.out.println(RunUtils.getNextId());

        RunUtils.setStarted("tester");
        System.out.println(RunUtils.getStatus("tester").getStatus());

        RunUtils.setFinished("tester");
        System.out.println(RunUtils.getStatus("tester").getStatus());

        RunUtils.setError("tester", "not really an error, just a test.");
        System.out.println(RunUtils.getStatus("tester").getStatus());
    }
}

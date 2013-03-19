package edu.pitt.apollo.seir.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;

public class RunUtils {

	public static String WORK_DIR = "/Users/jdl50/apollo/work/";

	public static synchronized String getNextId() throws IOException {

		File dir = new File(WORK_DIR);
		dir.mkdirs();
		File f = new File(WORK_DIR + "id.txt");
		if (!f.exists()) {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write("1");
			fw.close();
			return "1";
		} else {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			int nextId = Integer.valueOf(line);
			nextId++;
			br.close();

			FileWriter fw = new FileWriter(f);
			fw.write(String.valueOf(nextId));
			fw.close();

			return String.valueOf(nextId);
		}

	}

	public static synchronized void createRunDir(String runId)
			throws IOException {
		File dir = new File(WORK_DIR + runId);
		dir.mkdirs();

	}

	public static synchronized void setStarted(String runId) throws IOException {
		createRunDir(runId);
		
		//should be md5 hash of run id
		File started = new File(WORK_DIR + runId + "/started.txt");
		started.createNewFile();
	}

	public static synchronized String getWorkDir(String runId) {

		return WORK_DIR + runId + File.separator;
	}

	public static synchronized void setFinished(String runId)
			throws IOException {
		File finished = new File(WORK_DIR + runId + "/finished.txt");
		finished.createNewFile();
	}

	public static synchronized void setError(String runId, String msg)
			throws IOException {
		File error = new File(WORK_DIR + runId + "/error.txt");
		error.createNewFile();

		FileWriter fw = new FileWriter(error);
		fw.write(msg);
		fw.close();
	}

	public static synchronized String getError(String runId) throws IOException {
		File error = new File(WORK_DIR + runId + "/error.txt");

		BufferedReader br = new BufferedReader(new FileReader(error));
		String errorMsg = br.readLine();
		br.close();
		return errorMsg;

	}

	public static synchronized RunStatus getStatus(String runId)
			throws IOException {
		RunStatus rs = new RunStatus();
		File error = new File(WORK_DIR + runId + "/error.txt");
		File finished = new File(WORK_DIR + runId + "/finished.txt");
		File started = new File(WORK_DIR + runId + "/started.txt");

		if (error.exists()) {
			rs.setMessage(RunUtils.getError(runId));
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
		} else {
			rs.setMessage("Unknown run");
			rs.setStatus(RunStatusEnum.FAILED);
			return rs;
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

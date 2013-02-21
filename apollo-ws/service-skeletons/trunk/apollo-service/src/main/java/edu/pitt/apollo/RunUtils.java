package edu.pitt.apollo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import edu.pitt.apollo.types.SimulatorIdentification;

public class RunUtils {
	public static String getErrorRunId() {
		return ApolloServiceImpl.getRunErrorPrefix()
				+ Long.toString(System.currentTimeMillis());
	}
	
	public synchronized static String getError(String runId) {
		File errorFile = new File(ApolloServiceImpl.getErrorFilename());
		if (errorFile.exists()) {
			try {
				BufferedReader br = new BufferedReader(
						new FileReader(errorFile));
				String line = "";
				while ((line = br.readLine()) != null) {
					String err[] = line.split("\t");
					if (runId.equals(err[0])) {
						br.close();
						return err[1];
					}
				}
				br.close();
				return "Can't find error for given run id: " + runId;
			} catch (IOException e) {
				return "Error reading error file: " + e.getMessage();
			}
		} else {
			return "Error file doesn't exist!";
		}
	}

	public synchronized static void reportError(String runId, String error) {
		File errorFile = new File(ApolloServiceImpl.getErrorFilename());
		FileWriter fw;
		try {
			fw = new FileWriter(errorFile, true);
			fw.write(runId + "\t" + error + "\n");
			fw.close();
		} catch (IOException e) {
			// eat the error for now
		}

	}
}

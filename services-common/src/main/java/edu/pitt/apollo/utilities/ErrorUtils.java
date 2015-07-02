package edu.pitt.apollo.utilities;

import edu.pitt.apollo.FileLocks;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.pitt.apollo.FileLocks.FileLockAction;

/**
 * A helper class which allows thread-safe file operations suitable for using
 * these files to indicate the error state of a process.
 * 
 * For example, during a 'run' operation of a service, the service calls
 * "writeErrorToFile()" to create a file named 'error1.txt', which indicates
 * that run "1" encountered an error. The next time 'run' is executed for run
 * "1", the service will delete the error file via a call to clearErrorFile(),
 * and try to clear the error by re-running the service.
 * 
 * During the 'getRunStatus' operation of a service, the "checkFileExists()"
 * method will be called to see if the errorFile exists. If so, the run
 * encountered an error.
 * 
 * An error can occur when file access is not synchronized. For example, if
 * "getRunStatus()" if called at the same time "run()" is called, the run()
 * method will try to delete the errorFile while the "getRunStatus()" method
 * will read the no-yet-deleted file and report that the run is in error. *
 * 
 * @author jdl50
 * 
 */
public class ErrorUtils {

	private static Map<File, FileLocks> filesInUse = new HashMap<File, FileLocks>();

	private static void getFileLock(File errorFile, FileLockAction action) {
		FileLocks fileLocks = filesInUse.get(errorFile);
		if (fileLocks == null) {
			fileLocks = new FileLocks();
			filesInUse.put(errorFile, fileLocks);
		}

		switch (action) {
		case LOCK:
			fileLocks.lock();
			break;
		case UNLOCK:
			fileLocks.unlock();
			break;
		}
	}

	public static boolean checkFileExists(File errorFile) {
		getFileLock(errorFile, FileLockAction.LOCK);
		try {
			return errorFile.exists();

		} finally {
			getFileLock(errorFile, FileLockAction.UNLOCK);
		}
	}

	/**
	 * 
	 * @param errorFile
	 * @return "True" if an error file was cleared, "False" if there was no error file
	 */
	public static boolean clearErrorFile(File errorFile) {
		getFileLock(errorFile, FileLockAction.LOCK);
		try {
			if (errorFile.exists()) {
				errorFile.delete();
				return true;
			} else {
				return false;
			}
		} finally {
			getFileLock(errorFile, FileLockAction.UNLOCK);
		}

	}

	public static String readErrorFromFile(File errorFile) {
		getFileLock(errorFile, FileLockAction.LOCK);
		try {
			if (errorFile.exists()) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(errorFile));
					try {
						String line = "";
						String contents = "";
						while ((line = br.readLine()) != null) {
							contents += line;
						}
						return contents;
					} finally {
						br.close();
					}
				} catch (IOException e) {
					return "Error reading error file: " + e.getMessage();
				}
			} else {
				return "Error file doesn't exist!";
			}
		} finally {
			getFileLock(errorFile, FileLockAction.UNLOCK);
		}
	}

	public static String readErrorFromFile(String runId, String errorFilePrefix, String directory) {
		File errorFile = new File(directory + "/" + errorFilePrefix + runId + ".txt");
		return readErrorFromFile(errorFile);
	}

	public static void writeErrorToFile(String error, File errorFile) throws IOException {
		File parentDirectory = errorFile.getParentFile();
		if (parentDirectory != null && !parentDirectory.exists()) {
			parentDirectory.mkdirs();
		}
		getFileLock(errorFile, FileLockAction.LOCK);
		try {
			FileWriter fw;
			fw = new FileWriter(errorFile, false);
			fw.write(error + "\n");
			fw.close();
		} finally {
			getFileLock(errorFile, FileLockAction.UNLOCK);
		}
	}

	public static void writeErrorToFile(String error, String runId, String errorFilePrefix, String directory) throws IOException {
		File errorFile = new File(directory + "/" + errorFilePrefix + runId + ".txt");
		writeErrorToFile(error, errorFile);

	}
}

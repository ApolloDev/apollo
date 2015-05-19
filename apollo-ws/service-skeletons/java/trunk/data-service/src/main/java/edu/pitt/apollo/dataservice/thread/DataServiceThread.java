package edu.pitt.apollo.dataservice.thread;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.ApolloServiceThread;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 6, 2015
 * Time: 4:51:03 PM
 * Class: DataServiceThread
 */
public abstract class DataServiceThread extends ApolloServiceThread {

	protected final ApolloDbUtils dbUtils;
	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(DataServiceThread.class);
	private ZipOutputStream zos;

	public DataServiceThread(BigInteger runId, ApolloServiceQueue queue, ApolloDbUtils dbUtils) {
		super(runId, queue);
		this.dbUtils = dbUtils;
	}

//	protected final void zipOutputFiles(String outputDirectory, String zipFileName) {
//
//		File directoryToZip = new File(outputDirectory);
//		List<File> filesList = new ArrayList<File>();
//
//		try {
//			getAllFiles(directoryToZip, filesList);
//			writeZipFile(directoryToZip, filesList, outputDirectory + File.separator + zipFileName);
//		} catch (IOException ex) {
//			logger.error("IOExcpetion zipping files: " + ex.getMessage());
//			updateStatus(MethodCallStatusEnum.FAILED, "An error occurred zipping the files.");
//		}
//
//	}

//	private void getAllFiles(File dir, List<File> fileList) {
//		File[] files = dir.listFiles();
//		for (File file : files) {
//			fileList.add(file);
//			if (file.isDirectory()) {
//				getAllFiles(file, fileList);
//			}
//		}
//	}

	protected void initializeZipFile(String zipFilePath) throws IOException {

		File outputFile = new File(zipFilePath);
		outputFile.getParentFile().mkdirs();
		FileOutputStream fos = new FileOutputStream(outputFile);
		zos = new ZipOutputStream(fos);
	}
	
	protected void finalizeZipFile() throws IOException {
		if (zos != null) {
			zos.close();
		}
	}

	protected void addToZip(String pathOfFileInZip, byte[] bytes) throws FileNotFoundException,
			IOException {

//		FileInputStream fis = new FileInputStream(file);
//		byte[] contentBytes = content.getBytes();
//		Scanner scanner = new Scanner

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		ZipEntry zipEntry = new ZipEntry(pathOfFileInZip);
		zos.putNextEntry(zipEntry);

//		byte[] bytes = new byte[1024];
//		int length;
//		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, bytes.length);
//		}

		zos.closeEntry();
//		fis.close();
	}

	protected final void updateStatus(MethodCallStatusEnum statusEnum, String message) {
		try {
			dbUtils.updateStatusOfRun(runId, statusEnum, message);
		} catch (ApolloDatabaseException ex) {
			try {
				dbUtils.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, "ApolloDatabaseException updating status for run ID "
						+ runId + ": " + ex.getMessage());
			} catch (ApolloDatabaseException ex1) {
				logger.error("Could not update the run status for run ID " + runId + ": " + ex1.getMessage());
			}
		}
	}

}

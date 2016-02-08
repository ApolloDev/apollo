package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.ApolloServiceThread;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorFactory;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 6, 2015 Time: 4:51:03 PM Class: DatabaseAccessThread
 */
public abstract class DatastoreAccessThread extends ApolloServiceThread {

	protected final org.slf4j.Logger logger = LoggerFactory.getLogger(DatastoreAccessThread.class);
	private ZipOutputStream zos;
	protected Authentication authentication;

	public DatastoreAccessThread(BigInteger runId, ApolloServiceQueue queue, Authentication authentication) {
		super(runId, queue);
		this.authentication = authentication;
	}

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

		// we want the zipEntry's path to be a relative path that is relative
		// to the directory being zipped, so chop off the rest of the path
		ZipEntry zipEntry = new ZipEntry(pathOfFileInZip);
		zos.putNextEntry(zipEntry);
		zos.write(bytes, 0, bytes.length);
		zos.closeEntry();
	}

	protected final void updateStatus(MethodCallStatusEnum statusEnum, String message) {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor(authentication);
			dba.updateStatusOfRun(runId, statusEnum, message, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException | DataServiceException ex) {
			logger.error("Could not update the run status for run ID " + runId + ": " + ex.getMessage());
		}
	}
}

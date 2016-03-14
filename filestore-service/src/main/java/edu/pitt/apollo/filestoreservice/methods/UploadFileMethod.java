package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.filestoreservice.threads.FileDownloadMonitor;
import edu.pitt.apollo.filestoreservice.threads.FileDownloadThread;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFile;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jdl50 on 2/10/16.
 */
//TODO: class needs to be synchronized
public class UploadFileMethod extends FileStoreCoreMethod {

	static final Logger logger = LoggerFactory.getLogger(UploadFileMethod.class);

	public UploadFileMethod(String rootDirectory, String webRoot, BigInteger runId, String salt) {
		super(rootDirectory, webRoot, runId, salt);
	}

	private DirectoryContentFileEntry addFileToDirectoryContentFile(FileIdentification fileIdentification) throws FilestoreException {
		try {
			Integer fileExistResult = checkIfFileExists(fileIdentification);
			if (fileExistResult == DIRECTORY_CONTENT_FILE_DOES_NOT_EXIST) {
				writeDirectoryContentFile(new DirectoryContentFile());
			}

			fileExistResult = checkIfFileExists(fileIdentification);
			if (fileExistResult == FILE_DOES_NOT_EXIST) {

				DirectoryContentFile directoryContentFile = readDirectoryContentFile();
				int maxFileNumber = 0;
				if (directoryContentFile.getFiles() != null) {
					for (DirectoryContentFileEntry entry : directoryContentFile.getFiles()) {
						int fileNumber = entry.getUniqueFileNumber();
						maxFileNumber = Math.max(maxFileNumber, fileNumber);
					}
				}
				int newUniqueFileNumber = maxFileNumber + 1;
				DirectoryContentFileEntry newEntry = new DirectoryContentFileEntry(newUniqueFileNumber, fileIdentification);
				directoryContentFile.getFiles().add(newEntry);
				writeDirectoryContentFile(directoryContentFile);
				return newEntry;
			} else {
				throw new FilestoreException("File already exists!");
			}
		} catch (IOException ex) {
			throw new FilestoreException("IOException: " + ex.getMessage());
		}

	}

	private URL downloadFile(DirectoryContentFileEntry directoryContentFileEntry, URL urlToFile) throws IOException {
		File temporaryFileDownloadLocation = getLocalFileTemporaryName(directoryContentFileEntry);
		File finalDownloadFileLocation = getLocalFile(directoryContentFileEntry);
		File fileDownloadLocationDirectory = new File(temporaryFileDownloadLocation.getParent());
		fileDownloadLocationDirectory.mkdirs();
		FileDownloadThread fileDownloadThread = new FileDownloadThread(urlToFile, temporaryFileDownloadLocation, finalDownloadFileLocation);
		fileDownloadThread.start();
		FileDownloadMonitor fileDownloadMonitor = new FileDownloadMonitor(temporaryFileDownloadLocation);
		fileDownloadMonitor.start();
		return getWebserverUrl(directoryContentFileEntry);
	}

	public URL uploadFile(URL urlToFile, FileIdentification fileIdentification) throws FilestoreException {
		DirectoryContentFileEntry directoryContentFileEntry = addFileToDirectoryContentFile(fileIdentification);
		//TODO: have to launch a new thread
		try {
			return downloadFile(directoryContentFileEntry, urlToFile);
		} catch (IOException ex) {
			throw new FilestoreException("IOException: " + ex.getMessage());
		}
	}

}

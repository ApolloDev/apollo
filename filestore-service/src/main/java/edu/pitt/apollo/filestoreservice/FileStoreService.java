package edu.pitt.apollo.filestoreservice;

import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.db.LibraryDbUtils;
import edu.pitt.apollo.db.LibraryReadOnlyDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservice.methods.*;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import edu.pitt.apollo.interfaces.FilestoreServiceInterface;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileStoreService implements FilestoreServiceInterface {

	static final Logger logger = LoggerFactory.getLogger(FileStoreService.class);
	public static String FILE_STORE_PROPERTIES_FILE = "filestore.properties";
	public static String PROPERTY_FILE_STORE_ROOT_DIRECTORY = "root_directory";
	public static String PROPERTY_FILE_STORE_WEB_ROOT = "web_root";
	public static String PROPERTY_FILE_STORE_SALT = "salt";
	public static String DIRECTORY_CONTENT_FILENAME = "directory_content.json";
	private static Properties properties = new Properties();
	private static String FILE_STORE_ROOT_DIRECTORY = null;
	private static final String DEFAULT_FILESTORE_PROPERTIES_FILENAME = "filestore.properties";
	String rootDirectory, webRoot, salt;

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + apolloDir);
		} else {
			logger.warn(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " environment variable not found!");
		}

		String propertiesFileName = apolloDir + FILE_STORE_PROPERTIES_FILE;
		File propertiesFile = new File(propertiesFileName);
		if (propertiesFile.exists()) {
			try {
				properties.load(new FileReader(new File(propertiesFileName)));
				if (properties.containsKey(PROPERTY_FILE_STORE_ROOT_DIRECTORY)) {
					FILE_STORE_ROOT_DIRECTORY = properties.getProperty(PROPERTY_FILE_STORE_ROOT_DIRECTORY);
				}

				logger.info("Successfully loaded Filestore properties file \"" + propertiesFileName + "\".");
			} catch (IOException ex) {
				logger.error("Unable to load properties file: " + propertiesFileName + ". Error was: (" + ex.getClass() + ") " + ex.getMessage());
			}
		} else {
			try {
				properties.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAULT_FILESTORE_PROPERTIES_FILENAME)));
				logger.info("Successfully loaded default Filestore properties file \"" + DEFAULT_FILESTORE_PROPERTIES_FILENAME + "\".");
			} catch (IOException ex) {
				logger.error("Unable to load default properties file: " + propertiesFileName + ". Error was: (" + ex.getClass() + ") " + ex.getMessage());
			}
		}

	}

	public FileStoreService() {
		rootDirectory = properties.getProperty(PROPERTY_FILE_STORE_ROOT_DIRECTORY);
		webRoot = properties.getProperty(PROPERTY_FILE_STORE_WEB_ROOT);
		salt = properties.getProperty(PROPERTY_FILE_STORE_SALT);
	}

	@Override
	public void uploadFile(BigInteger runId, String urlToFile, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		UploadFileMethod uploadFileMethod = new UploadFileMethod(rootDirectory, webRoot, runId, salt, authentication);
		try {
			URL url = new URL(urlToFile);
			uploadFileMethod.uploadFile(url, filename, fileFormat, fileType);
		} catch (MalformedURLException ex) {
			throw new FilestoreException("MalformedUrlException: " + ex.getMessage());
		}
	}

	@Override
	public String getUrlOfFile(BigInteger runId, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		GetUrlOfFileMethod getUrlOfFileMethod = new GetUrlOfFileMethod(rootDirectory, webRoot, runId, salt, authentication);
		try {
			return getUrlOfFileMethod.getUrlOfFile(filename, fileFormat, fileType).toExternalForm();
		} catch (IOException ex) {
			throw new FilestoreException("IOException: " + ex.getMessage());
		}
	}

	@Override
	public MethodCallStatus getStatusOfFileUpload(BigInteger runId, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
		GetStatusOfFileUploadMethod getStatusOfFileUploadMethod = new GetStatusOfFileUploadMethod(rootDirectory, webRoot, runId, salt, authentication);
		try {
			return getStatusOfFileUploadMethod.getStatus(filename, fileFormat, fileType);
		} catch (IOException ex) {
			throw new FilestoreException("IOException: " + ex.getMessage());
		}
	}

	@Override
	public List<FileIdentification> listFilesForRun(BigInteger runId, Authentication authentication) throws FilestoreException {
		ListFilesForRunMethod listFilesForRunMethod = new ListFilesForRunMethod(rootDirectory, webRoot, runId, salt, authentication);
		List<FileIdentification> fileIdentificationList = new ArrayList<FileIdentification>();
		try {
			List<DirectoryContentFileEntry> filesForRun = listFilesForRunMethod.listFilesForRun();
			for (DirectoryContentFileEntry entry : filesForRun) {
				fileIdentificationList.add(entry.getFileIdentification());
			}
			return fileIdentificationList;
		} catch (IOException ex) {
			throw new FilestoreException("IOException: " + ex.getMessage());
		}
	}

	public void deleteFilesForRun(BigInteger runId) {
		DeleteFilesForRun deleteFilesForRun = new DeleteFilesForRun(rootDirectory, webRoot, runId, salt, null);
		deleteFilesForRun.delete();

	}
}

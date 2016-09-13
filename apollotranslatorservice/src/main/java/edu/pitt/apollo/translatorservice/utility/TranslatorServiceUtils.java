package edu.pitt.apollo.translatorservice.utility;

import au.com.bytecode.opencsv.CSVReader;
import edu.pitt.apollo.ApolloServiceConstants;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.connector.FilestoreServiceConnector;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.restfilestoreserviceconnector.RestFilestoreServiceConnector;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;
import edu.pitt.apollo.translatorservice.exception.TranslatorServiceException;
import edu.pitt.apollo.translatorservice.types.TranslatorResources;
import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.FileStoreServiceUtility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Sep 11, 2013 Time: 12:30:12 PM Class: TranslatorServiceUtils IDE: NetBeans 6.9.1
 */
public class TranslatorServiceUtils {

	private static final Logger logger = LoggerFactory.getLogger(TranslatorServiceUtils.class);
	public static final String ERROR_PREFIX = "Translator v2.1.0 error: ";
	private static final String DIRECTORY_CONTENTS_FILE_NAME = "list.txt";
	private static final String SIMULATOR_NAME_COLUMN = "simulator_name";
	private static final String TRANSLATION_INSTRUCTIONS_PATH_COLUMN = "translation_instructions_path";
	private static final String JAVASCRIPT_PATH_COLUMN = "javascript_path";
	private static final String TRANSLATION_MODE_COLUMN = "translation_mode";
	private static final String BASE_CONFIGURATION_URN_COLUMN = "base_configuration_urn";
	protected static final String SOFTWARE_NAME = "Translator";
	protected static final String SOFTWARE_VERSION = "1.0";
	protected static final String SOFTWARE_DEVELOPER = "UPitt";
	private static final Map<String, TranslatorResources> simNameTranslatorResourceMap;
	private static final String TRANSLATOR_RESOURCE_FILES_TABLE;
	private static final String SERVICE_PROPERTIES_FILE = "translatorservice.properties";
	private static final String TRANSLATION_PATHS_FILE_PARAM = "translation_paths_file";
	private static final String NUM_THREADS_PARAM = "num_threads";
	protected static final Integer TRANSLATOR_SOFTWARE_ID_KEY;
	protected static final int NUM_THREADS;
	private static final String FILESTORE_SERVICE_URL_PROPERTY = "filestore_service_url";
	private static final String LOCAL_FILE_DIR_PROPERTY = "local_file_dir";
	private static final String BASE_FILE_URL_PROPERTY = "base_file_url";
	private static final String AUTH_USERNAME_PROPERTY = "authentication_username";
	private static final String AUTH_PASSWORD_PROPERTY = "authentication_password";
	public static final String LOCAL_FILE_DIR;
	public static final String BASE_FILE_URL;
	private static final Authentication authentication;
	private static final FilestoreServiceConnector filestoreServiceConnector;

	static {
		Properties properties = null;
		try {
			properties = loadPropertiesFile();
		} catch (IOException ex) {
			throw new ExceptionInInitializerError("IOException loading properties file: " + ex.getMessage());

		}

		authentication = new Authentication();
		authentication.setRequesterId(properties.getProperty(AUTH_USERNAME_PROPERTY));
		authentication.setRequesterPassword(properties.getProperty(AUTH_PASSWORD_PROPERTY));
		
		filestoreServiceConnector = new RestFilestoreServiceConnector(properties.getProperty(FILESTORE_SERVICE_URL_PROPERTY));
		LOCAL_FILE_DIR = properties.getProperty(LOCAL_FILE_DIR_PROPERTY);
		BASE_FILE_URL = properties.getProperty(BASE_FILE_URL_PROPERTY);

		NUM_THREADS = Integer.parseInt(properties.getProperty(NUM_THREADS_PARAM));

		TRANSLATOR_RESOURCE_FILES_TABLE = properties.getProperty(TRANSLATION_PATHS_FILE_PARAM);
		if (TRANSLATOR_RESOURCE_FILES_TABLE == null) {
			String message = ERROR_PREFIX + "No \"" + TRANSLATION_PATHS_FILE_PARAM
					+ "\" property was specified in " + " \"" + SERVICE_PROPERTIES_FILE + "\"";
			logger.error(message);
			throw new ExceptionInInitializerError(message);
		}

		try {
			simNameTranslatorResourceMap = TranslatorServiceUtils
					.loadTranslationResources(TRANSLATOR_RESOURCE_FILES_TABLE);
		} catch (IOException ex) {
			String message = ERROR_PREFIX + "IOException loading translation paths file: " + ex.getMessage();
			logger.error(message);
			throw new ExceptionInInitializerError(message);

		} catch (TranslatorServiceException ex) {
			String message = ERROR_PREFIX + "TranslatorException loading translation paths file: "
					+ ex.getMessage();
			logger.error(message);
			throw new ExceptionInInitializerError(message);

		}

		try {
			ApolloDbUtils dbUtils = new ApolloDbUtils();

			Integer tempTranslatorSoftwareId = null;
			Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
			for (Integer id : softwareIdMap.keySet()) {
				SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
				if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
					tempTranslatorSoftwareId = id;
				}
			}

			dbUtils.close();

			TRANSLATOR_SOFTWARE_ID_KEY = tempTranslatorSoftwareId;
		} catch (ApolloDatabaseException ex) {
			throw new ExceptionInInitializerError("ApolloDatabaseException attempting to load the translator software ID: "
					+ ex.getMessage());
		}
		if (TRANSLATOR_SOFTWARE_ID_KEY == null) {
			throw new ExceptionInInitializerError("Could not find the translator in the list of registered services");
		}
	}

	private static Properties loadPropertiesFile() throws IOException {

		String apolloDir = TranslatorServiceUtils.getApolloWorkDir();
		File f = new File(apolloDir + SERVICE_PROPERTIES_FILE);
		if (f.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
				Properties properties = new Properties();
				properties.load(fis);
				return properties;
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
		} else {
			logger.error("Translator v3.0.0 error: {} file not found!", apolloDir + SERVICE_PROPERTIES_FILE);
			return null;
		}

	}

	public static FilestoreServiceConnector getFilestoreServiceConnector() {
		return filestoreServiceConnector;
	}
	
	public static Map<String, TranslatorResources> getTranslatorResourceMap() {
		return simNameTranslatorResourceMap;
	}
	
	public static Authentication getAuthentication() {
		return authentication;
	}

	public static int getNumThreads() {
		return NUM_THREADS;
	}

	public static SoftwareIdentification getTranslatorSoftwareIdentification() {
		SoftwareIdentification softwareIdentification = new SoftwareIdentification();
		softwareIdentification.setSoftwareDeveloper(SOFTWARE_DEVELOPER);
		softwareIdentification.setSoftwareName(SOFTWARE_NAME);
		softwareIdentification.setSoftwareVersion(SOFTWARE_VERSION);
		softwareIdentification.setSoftwareType(ApolloSoftwareTypeEnum.TRANSLATOR);

		return softwareIdentification;

	}

	public static String getApolloWorkDir() {

		return ApolloServiceConstants.APOLLO_DIR;
	}

	public static synchronized int getNextRunIdFromFile(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write("1");
			fw.close();
			return 1;
		} else {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			int nextId = Integer.valueOf(line);
			nextId++;
			br.close();

			FileWriter fw = new FileWriter(f);
			fw.write(String.valueOf(nextId));
			fw.close();

			return nextId;
		}

	}

	public static boolean createDirectory(String directory) {

		File file = new File(directory);
		if (file.exists()) {
			return true;
		}

		return file.mkdirs();
	}

	public static void createFolderContentsList(String directory) throws FileNotFoundException {

		File dir = new File(directory);
		if (!dir.exists()) {
			throw new FileNotFoundException("The directory \"" + directory + "\" does not exist."
					+ " The directory contents file cannot be created.");
		}

		StringBuilder stBuild = new StringBuilder();
		for (String file : dir.list()) {
			stBuild.append(file).append("\n");
		}

		String file = dir + File.separator + DIRECTORY_CONTENTS_FILE_NAME;
		try {
			PrintStream ps = new PrintStream(new File(file));
			ps.print(stBuild.toString());
			ps.close();
		} catch (FileNotFoundException ex) {
			throw new FileNotFoundException("The file \"" + file + "\" could not be created for writing.");
		}
	}

	public static Map<String, TranslatorResources> loadTranslationResources(
			String translationPathsFile) throws FileNotFoundException, IOException,
			TranslatorServiceException {

		Map<String, TranslatorResources> simNameTranslatorInputMap = new HashMap<String, TranslatorResources>();

		CSVReader reader;
		try {
			try {
				reader = new CSVReader(new FileReader(translationPathsFile), ',', '"');
			} catch (FileNotFoundException ex) {
				throw new FileNotFoundException("File " + translationPathsFile
						+ " could not be opened for reading: " + ex.getMessage());
			}

			String[] line;
			try {
				if ((line = reader.readNext()) == null) {
					throw new TranslatorServiceException("The translation paths file appears to be empty");
				}
				if (!line[0].trim().equalsIgnoreCase(SIMULATOR_NAME_COLUMN)) {
					throw new TranslatorServiceException("The first column in the translation paths file \""
							+ translationPathsFile + "\" does not have the required heading of \""
							+ SIMULATOR_NAME_COLUMN + "\"");
				}
				if (!line[1].trim().equalsIgnoreCase(TRANSLATION_INSTRUCTIONS_PATH_COLUMN)) {
					throw new TranslatorServiceException("The second column in the translation paths file \""
							+ translationPathsFile + "\" does not have the required heading of \""
							+ TRANSLATION_INSTRUCTIONS_PATH_COLUMN + "\"");
				}
				if (!line[2].trim().equalsIgnoreCase(JAVASCRIPT_PATH_COLUMN)) {
					throw new TranslatorServiceException("The third column in the translation paths file \""
							+ translationPathsFile + "\" does not have the required heading of \""
							+ JAVASCRIPT_PATH_COLUMN + "\"");
				}
				if (!line[3].trim().equalsIgnoreCase(TRANSLATION_MODE_COLUMN)) {
					throw new TranslatorServiceException("The third column in the translation paths file \""
							+ translationPathsFile + "\" does not have the required heading of \""
							+ TRANSLATION_MODE_COLUMN + "\"");
				}
				if (!line[4].trim().equalsIgnoreCase(BASE_CONFIGURATION_URN_COLUMN)) {
					throw new TranslatorServiceException("The third column in the translation paths file \""
							+ translationPathsFile + "\" does not have the required heading of \""
							+ BASE_CONFIGURATION_URN_COLUMN + "\"");
				}

				int lineNumber = 1;
				while ((line = reader.readNext()) != null) {

					if (line.length < 4) {
						if (reader != null) {
							reader.close();
						}
						throw new TranslatorServiceException("Line " + lineNumber
								+ " in the translation instructions file " + translationPathsFile
								+ " does not contain at least 3 columns");
					}

					TranslatorResources input = new TranslatorResources();
					input.setTranslationInstructionsPath(line[1]);
					input.setJavaScriptPath(line[2]);
					input.setTranslationMode(TranslationMode.valueOf(line[3]));
					if (line[4] != null && !line[4].trim().equals("")) {
						try {
							int urn = Integer.parseInt(line[4]);
							input.setUrnOfBaseConfigurationInLibrary(urn);
						} catch (NumberFormatException ex) {
							// assume it's a file path
							input.setFileLocationOfBaseConfiguration(line[4]);
						}
					}

					simNameTranslatorInputMap.put(line[0].toLowerCase(), input);

					lineNumber++;
				}
				if (reader != null) {
					reader.close();
				}

				return simNameTranslatorInputMap;
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (IOException ex) {
			throw new IOException(
					"IOException attempting to read line from the translation instructions file "
					+ translationPathsFile + ": " + ex.getMessage());
		}
	}

	public static void updateStatusForRun(BigInteger runId, MethodCallStatusEnum status, String message) {
		try {
			ApolloDbUtils apolloDbUtils = new ApolloDbUtils();
			apolloDbUtils.updateStatusOfRun(runId, status, message);
			apolloDbUtils.close();
		} catch (ApolloDatabaseException ex) {
			logger.error("Could not update status of run " + runId + ": " + ex.getMessage());
		}
	}

	public static void uploadTextFileContent(String content, BigInteger runId,
			FileIdentification fileIdentification) throws FilestoreException {

		FileStoreServiceUtility.uploadTextFileContentSynchronous(content, runId, fileIdentification,
				authentication, LOCAL_FILE_DIR + File.separator + runId, BASE_FILE_URL + "/" + runId, filestoreServiceConnector);
	}

}

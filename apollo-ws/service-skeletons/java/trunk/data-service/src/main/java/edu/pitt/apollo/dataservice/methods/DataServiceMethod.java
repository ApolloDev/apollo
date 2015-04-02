package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 16, 2015
 * Time: 3:54:13 PM
 * Class: DataServiceMethod
 */
public abstract class DataServiceMethod {

	protected static final ApolloDbUtils dbUtils;
	protected final BigInteger runId;
	protected static final SoftwareIdentification dataServiceSoftwareId;
	protected static final int DATA_SERVICE_SOFTWARE_KEY;
	protected static final Logger logger = LoggerFactory.getLogger(DataServiceMethod.class);
	protected final ApolloServiceQueue queue;
	private static final String APOLLO_DIR;
	private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
	private static final String DATA_SERVICE_PROPERTIES_NAME = "data_service.properties";
	private static final String OUTPUT_DIRECTORY_KEY = "output_directory";
	private static final String OUTPUT_FILE_NAME_KEY = "output_file_name";
	protected static final String OUTPUT_DIRECTORY;
	protected static final String OUTPUT_FILE_NAME;
	private static final String ZIP_FILE_NAME_KEY = "zip_file_name";
	protected static final String ZIP_FILE_NAME;

	static {
		dataServiceSoftwareId = new SoftwareIdentification();
		dataServiceSoftwareId.setSoftwareDeveloper("UPitt");
		dataServiceSoftwareId.setSoftwareName("Data Service");
		dataServiceSoftwareId.setSoftwareVersion("1.0");
		dataServiceSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.DATA);

		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		System.out.println("apolloDir: " + apolloDir);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			APOLLO_DIR = apolloDir;
			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);

			FileInputStream fis = null;
			try {
				fis = new FileInputStream(APOLLO_DIR + DATA_SERVICE_PROPERTIES_NAME);
			} catch (FileNotFoundException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Can not find file \""
						+ DATA_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			Properties properties = new Properties();
			try {
				properties.load(fis);
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Unable to read file \""
						+ DATA_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			try {
				fis.close();
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Unable to close file \""
						+ DATA_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			String outputDir = properties.getProperty(OUTPUT_DIRECTORY_KEY);
			if (!outputDir.endsWith(File.separator)) {
				outputDir = outputDir + File.separator;
			}
			OUTPUT_DIRECTORY = outputDir;
			OUTPUT_FILE_NAME = properties.getProperty(OUTPUT_FILE_NAME_KEY);

			ZIP_FILE_NAME = properties.getProperty(ZIP_FILE_NAME_KEY);

			try {
				//dbUtils = new ApolloDbUtils(new File(APOLLO_DIR + DATABASE_PROPERTIES_FILENAME));
				dbUtils = new ApolloDbUtils();

				try {
					DATA_SERVICE_SOFTWARE_KEY = dbUtils.getSoftwareIdentificationKey(dataServiceSoftwareId);
				} catch (ApolloDatabaseException ex) {
					logger.error(ex.getMessage());
					throw new ExceptionInInitializerError("ApolloDatabaseException getting the key for the data service software ID");
				}
			} catch (ApolloDatabaseException ex) {
				throw new ExceptionInInitializerError("ApolloDatabaseException creating ApolloDbUtils: " + ex.getMessage());
			}
//			} catch (IOException ex) {
//				throw new ExceptionInInitializerError("Error creating ApolloDbUtils when initializing the data service: "
//						+ ex.getMessage());
//			}
		} else {
			throw new ExceptionInInitializerError("No Apollo Work Dir evironment variable found when initializing data service!");
		}
	}

	protected DataServiceMethod(ApolloServiceQueue queue, BigInteger runId) {
		this.queue = queue;
		this.runId = runId;
	}

	public abstract void downloadFiles();

}

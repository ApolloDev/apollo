package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunMessage;
import edu.pitt.apollo.utilities.JsonUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:21:19 PM Class: RunMethodForDataService
 */
public class RunMethodForDataService extends AbstractRunMethod {

	private static final String BASE_URL;
	private static final String DATA_SERVICE_PROPERTIES_NAME = "data_service.properties";
	private static final String BASE_URL_KEY = "base_url";
	private static final String ZIP_FILE_NAME_KEY = "zip_file_name";
	private static final String ZIP_FILE_NAME;
	private static final String FILE_PREFIX = "run_%d_";
	private static final BigInteger NO_SIMULATION_GROUP_ID = null;

	static {
		FileInputStream fis;
		final String APOLLO_DIR = ApolloServiceConstants.APOLLO_DIR;
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

		BASE_URL = properties.getProperty(BASE_URL_KEY);
		ZIP_FILE_NAME = properties.getProperty(ZIP_FILE_NAME_KEY);
	}

	private List<URLForFileAndRunId> urlsForFilesAndRunIds;

	public RunMethodForDataService(BigInteger runId, Authentication authentication) throws JsonUtilsException, DataServiceException {
		super(runId, authentication, "run_dataservice_message.json");
	}

//	private void getOutputFileURLs(GetOutputFilesURLsMessage message, BigInteger runId) throws DataServiceException {
//		urlsForFilesAndRunIds = new ArrayList<>();
//		for (RunIdAndFiles runIdAndFiles : message.getRunIdsAndFiles()) {
//			// create a url for each file in the list
//			BigInteger run = runIdAndFiles.getRunId();
//			List<String> files = runIdAndFiles.getFiles();
//
//			List<BigInteger> runIdsAssociatedWithRun = dataServiceDao.getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
//			for (BigInteger singleRun : runIdsAssociatedWithRun) {
//				for (String file : files) {
//					String urlForFile = BASE_URL + runId + "/" + FILE_PREFIX + file;
//					urlForFile = String.format(urlForFile, singleRun);
//
//					URLForFileAndRunId urlForFileAndRunId = new URLForFileAndRunId();
//					urlForFileAndRunId.setRunId(singleRun);
//					urlForFileAndRunId.setUrl(urlForFile);
//					urlForFileAndRunId.setFile(file);
//					urlsForFilesAndRunIds.add(urlForFileAndRunId);
//				}
//			}
//		}
//	}
	private String getZipFileURL(BigInteger runId) {
		return BASE_URL + runId + "/" + String.format(ZIP_FILE_NAME, runId);
	}

	@Override
	protected RunMessage convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException {
		JsonUtils jsonUtils = new JsonUtils();
		RunMessage object;

//		try {
//			object = (GetAllOutputFilesURLAsZipMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, GetAllOutputFilesURLAsZipMessage.class);
//		} catch (JsonUtilsException ex) {
//			// try next message type
//			try {
		object = (DataRetrievalRequestMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, DataRetrievalRequestMessage.class);
//			} catch (JsonUtilsException ex1) {
//				// try next message type
//				object = (GetOutputFilesURLsMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, GetOutputFilesURLsMessage.class);
//			}
//		}

		return object;
	}

//	@Override
//	protected Object getObjectToReturn(BigInteger runId) throws RunManagerServiceException {
////		if (runMessage instanceof GetOutputFilesURLsMessage) {
////			try {
////				getOutputFileURLs((GetOutputFilesURLsMessage) runMessage, runId);
////			} catch (DataServiceException ex) {
////				throw new RunManagerServiceException("There was an exception using the data service: " + ex.getMessage());
////			}
////			GetOutputFilesURLsResult filesResult = new GetOutputFilesURLsResult();
////			filesResult.setMethodCallStatus(getDefaultSuccessfulMethodCallStatus());
////			filesResult.setRequestIdentification(runId);
////			filesResult.getUrlsForRunIdsAndFiles().addAll(urlsForFilesAndRunIds);
////			return filesResult;
////		} else if (runMessage instanceof DataRetrievalRequestMessage) {
////			String zipURL = getZipFileURL(runId);
////			GetOutputFilesURLAsZipResult filesResult = new GetOutputFilesURLAsZipResult();
////			filesResult.setMethodCallStatus(getDefaultSuccessfulMethodCallStatus());
////			filesResult.setRequestIdentification(runId);
////			filesResult.setUrl(zipURL);
////			return filesResult;
////		} else 
//		if (runMessage instanceof DataRetrievalRequestMessage) {
//			RunResult runResult = new RunResult();
//			runResult.setRunId(runId);
//			runResult.setMethodCallStatus(getDefaultSuccessfulMethodCallStatus());
//			return runResult;
//		} else {
//			throw new UnrecognizedMessageTypeException("The run message for the data service was of an unrecognized type");
//		}
//	}

	@Override
	protected MethodCallStatus getDefaultSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.CALLED_DATA_SERVICE);
		status.setMessage("The run has been submitted to the data service");
		return status;
	}

}

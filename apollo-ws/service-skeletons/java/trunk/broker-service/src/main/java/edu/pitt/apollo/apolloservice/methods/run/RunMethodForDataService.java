package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningDataService;
import edu.pitt.apollo.apolloservice.types.ReturnObjectForRun;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsResult;
import edu.pitt.apollo.data_service_types.v3_0_0.RunIdAndFiles;
import edu.pitt.apollo.data_service_types.v3_0_0.URLForFileAndRunId;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 22, 2015
 * Time: 12:21:19 PM
 * Class: RunMethodForDataService
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
    private GetOutputFilesURLsMessage getOutputFilesURLsMessage;
    private GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage;
    private GetAllOutputFilesURLAsZipMessage getAllOutputFilesURLAsZipMessage;

    public RunMethodForDataService(Authentication authentication, GetOutputFilesURLsMessage message) {
        super(authentication, DatabaseAccessorForRunningDataService.getDataServiceSoftwareId(), NO_SIMULATION_GROUP_ID, message);
        this.getOutputFilesURLsMessage = message;
    }

    public RunMethodForDataService(Authentication authentication, GetOutputFilesURLAsZipMessage message) {
        super(authentication, DatabaseAccessorForRunningDataService.getDataServiceSoftwareId(), NO_SIMULATION_GROUP_ID, message);
        this.getOutputFilesURLAsZipMessage = message;
    }

    public RunMethodForDataService(Authentication authentication, GetAllOutputFilesURLAsZipMessage message) {
        super(authentication, DatabaseAccessorForRunningDataService.getDataServiceSoftwareId(), NO_SIMULATION_GROUP_ID, message);
        this.getAllOutputFilesURLAsZipMessage = message;
    }

    private void getOutputFileURLs(GetOutputFilesURLsMessage message, BigInteger runId) throws ApolloDatabaseException {
        urlsForFilesAndRunIds = new ArrayList<>();
        for (RunIdAndFiles runIdAndFiles : message.getRunIdsAndFiles()) {
            // create a url for each file in the list
            BigInteger run = runIdAndFiles.getRunId();
            List<String> files = runIdAndFiles.getFiles();

            List<BigInteger> runIdsAssociatedWithRun = dao.getRunIdsAssociatedWithRun(run);
            for (BigInteger singleRun : runIdsAssociatedWithRun) {
                for (String file : files) {
                    String urlForFile = BASE_URL + runId + "/" + FILE_PREFIX + file;
                    urlForFile = String.format(urlForFile, singleRun);

                    URLForFileAndRunId urlForFileAndRunId = new URLForFileAndRunId();
                    urlForFileAndRunId.setRunId(singleRun);
                    urlForFileAndRunId.setUrl(urlForFile);
                    urlForFileAndRunId.setFile(file);
                    urlsForFilesAndRunIds.add(urlForFileAndRunId);
                }
            }
        }
    }

    private String getZipFileURL(BigInteger runId) {
        return BASE_URL + runId + "/" + String.format(ZIP_FILE_NAME, runId);
    }

    @Override
    public ReturnObjectForRun getReturnObjectForRun(RunResult runResult) {
        ReturnObjectForRun returnObj = new ReturnObjectForRun();

        if (getOutputFilesURLsMessage != null) {
            try {
                getOutputFileURLs(getOutputFilesURLsMessage, runResult.getRunId());
            } catch (ApolloDatabaseException ex) {
                MethodCallStatus status = new MethodCallStatus();
                status.setStatus(MethodCallStatusEnum.FAILED);
                status.setMessage("There was an exception getting the run IDs associated with the run ID " + runResult.getRunId());
                returnObj.setStatus(status);
                return returnObj;
            }
            GetOutputFilesURLsResult filesResult = new GetOutputFilesURLsResult();
            filesResult.setMethodCallStatus(runResult.getMethodCallStatus());
            filesResult.setRequestIdentification(runResult.getRunId());
            filesResult.getUrlsForRunIdsAndFiles().addAll(urlsForFilesAndRunIds);
            returnObj.setStatus(runResult.getMethodCallStatus());
            returnObj.setObjectToReturnFromBroker(filesResult);
        } else if (getOutputFilesURLAsZipMessage != null) {
            String zipURL = getZipFileURL(runResult.getRunId());
            GetOutputFilesURLAsZipResult filesResult = new GetOutputFilesURLAsZipResult();
            filesResult.setMethodCallStatus(runResult.getMethodCallStatus());
            filesResult.setRequestIdentification(runResult.getRunId());
            filesResult.setUrl(zipURL);
            returnObj.setStatus(runResult.getMethodCallStatus());
            returnObj.setObjectToReturnFromBroker(filesResult);
        } else if (getAllOutputFilesURLAsZipMessage != null) {
            String zipURL = getZipFileURL(runResult.getRunId());
            GetAllOutputFilesURLAsZipResult filesResult = new GetAllOutputFilesURLAsZipResult();
            filesResult.setMethodCallStatus(runResult.getMethodCallStatus());
            filesResult.setRequestIdentification(runResult.getRunId());
            filesResult.setUrl(zipURL);
            returnObj.setStatus(runResult.getMethodCallStatus());
            returnObj.setObjectToReturnFromBroker(filesResult);
        }

        return returnObj;
    }

}

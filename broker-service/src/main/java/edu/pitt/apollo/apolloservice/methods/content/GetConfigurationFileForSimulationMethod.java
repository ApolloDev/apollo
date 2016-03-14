package edu.pitt.apollo.apolloservice.methods.content;

import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.simulator_service_types.v4_0.GetConfigurationFileForSimulationResult;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.IOUtils;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 9, 2014 Time: 1:52:25 PM Class: GetConfigurationFileForSimulationMethod IDE: NetBeans 6.9.1
 */
public class GetConfigurationFileForSimulationMethod extends BaseFilestoreServiceAccessorMethod {

	private static final String CONFIG_FILE_NAME = "config.txt";
	private static final String VERBOSE_CONFIG_FILE_NAME = "verbose.html";

	public GetConfigurationFileForSimulationMethod(String dataServiceUrl) {
		super(dataServiceUrl);
	}

	public GetConfigurationFileForSimulationResult getConfigurationFile(BigInteger runId, Authentication authentication) {

		GetConfigurationFileForSimulationResult result = new GetConfigurationFileForSimulationResult();
		MethodCallStatus status;

		try {
			FileIdentification configFileId = null, verboseFileId = null;
			List<FileIdentification> fileList = filestoreServiceConnector.listFilesForRun(runId, authentication);
			for (FileIdentification fileIdentification : fileList) {
				switch (fileIdentification.getLabel()) {
					case CONFIG_FILE_NAME:
						configFileId = fileIdentification;
						break;
					case VERBOSE_CONFIG_FILE_NAME:
						verboseFileId = fileIdentification;
						break;
				}
			}

			if (configFileId != null) {
				String url = filestoreServiceConnector.getUrlOfFile(runId, configFileId.getLabel(), configFileId.getFormat(),
						configFileId.getType(), authentication);
				result.setConfigurationFile(getContent(url));
			}
			if (verboseFileId != null) {
				String url = filestoreServiceConnector.getUrlOfFile(runId, verboseFileId.getLabel(), verboseFileId.getFormat(),
						verboseFileId.getType(), authentication);
				result.setConfigurationFileInHtmlFormat(getContent(url));
			}
		} catch (FilestoreException | IOException ex) {
			status = getFailedMethodCallStatus("Exception calling the filestore service to get configuration files: " + ex.getMessage());
			result.setMethodCallStatus(status);
			return result;
		}

		status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		status.setMessage("The configuration files were succesfully obtained");
		result.setMethodCallStatus(status);

		return result;
	}

	private static String getContent(String url) throws IOException {
		InputStream in = new URL(url).openStream();

		try {
			return IOUtils.toString(in);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
}

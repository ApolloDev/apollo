package edu.pitt.apollo.apolloservice.methods.content;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetConfigurationFileForSimulationResult;

import java.math.BigInteger;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:52:25 PM
 * Class: GetConfigurationFileForSimulationMethod
 * IDE: NetBeans 6.9.1
 */
public class GetConfigurationFileForSimulationMethod extends BaseDataServiceAccessorMethod {

	private static final String CONFIG_FILE_NAME = "config.txt";
	private static final String VERBOSE_CONFIG_FILE_NAME = "verbose.html";
	
	public GetConfigurationFileForSimulationMethod(String dataServiceUrl) {
		super(dataServiceUrl);
	}
	
	public GetConfigurationFileForSimulationResult getConfigurationFile(BigInteger runId, Authentication authentication) {
		
		GetConfigurationFileForSimulationResult result = new GetConfigurationFileForSimulationResult();
		MethodCallStatus status;
		
		try {
		Map<BigInteger, FileAndURLDescription> fileMap = connector.getListOfFilesForRunId(runId, authentication);
		BigInteger configFileId = null, verboseFileId = null;
		for (BigInteger fileId : fileMap.keySet()) {
			FileAndURLDescription description = fileMap.get(fileId);
			switch (description.getName()) {
				case CONFIG_FILE_NAME:
					configFileId = fileId;
					break;
				case VERBOSE_CONFIG_FILE_NAME:
					verboseFileId = fileId;
					break;
			}
		}
		
		if (configFileId != null) {
			String content = connector.getContentForContentId(configFileId, authentication);
			result.setConfigurationFile(content);
		}
		if (verboseFileId != null) {
			String content = connector.getContentForContentId(verboseFileId, authentication);
			result.setConfigurationFileInHtmlFormat(content);
		}
		} catch (DataServiceException ex) {
			status = getFailedMethodCallStatus("Exception calling the data service to get configuration files: " + ex.getMessage());
			result.setMethodCallStatus(status);
			return result;
		}
		
		status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.COMPLETED);
		status.setMessage("The configuration files were succesfully obtained");
		result.setMethodCallStatus(status);
		
		return result;
	}
}

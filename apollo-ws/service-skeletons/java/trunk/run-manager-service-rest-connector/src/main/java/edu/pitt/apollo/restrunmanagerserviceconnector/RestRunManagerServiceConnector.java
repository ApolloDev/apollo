package edu.pitt.apollo.restrunmanagerserviceconnector;

import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.math.BigInteger;
import java.util.List;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nem41
 */
public class RestRunManagerServiceConnector extends RunManagerServiceConnector {

	RestTemplate template = new RestTemplate();

	public RestRunManagerServiceConnector(String url) {
		super(url);
	}

	private <T> T checkResponseAndGetObject(Response response, Class<T> clazz) throws RunManagementException {
		try {
			RestServiceUtils.checkResponseCode(response);
			return RestServiceUtils.getObjectFromResponseBody(response, clazz);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	private <T> List<T> checkResponseAndGetObjects(Response response, Class<T> clazz) throws RunManagementException {
		try {
			RestServiceUtils.checkResponseCode(response);
			return RestServiceUtils.getObjectsFromResponseBody(response, clazz);
		} catch (RestServiceException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		String uri = serviceUrl + "/ws/run/" + runId + "/rungroup?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		Response response = template.getForObject(uri, Response.class);
		return checkResponseAndGetObjects(response, BigInteger.class);
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {

		String uri = serviceUrl + "/ws/run/" + runId + "/softwareIdentification?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		Response response = template.getForObject(uri, Response.class);
		return checkResponseAndGetObject(response, SoftwareIdentification.class);
	}

	@Override
	public BigInteger insertRun(RunMessage message) throws RunManagementException {
		//String uri = serviceUrl+ "/ws/runs?" + RestServiceUtils.getUsernameAndPasswordQueryParams(null)
		return null;
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {

		String uri = serviceUrl + "/ws/run/" + runId + "/status?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
		Response response = template.getForObject(uri, Response.class);
		return checkResponseAndGetObject(response, MethodCallStatus.class);
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws SimulatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws SimulatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}

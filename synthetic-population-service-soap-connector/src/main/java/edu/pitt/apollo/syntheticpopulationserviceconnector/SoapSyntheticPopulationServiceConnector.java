package edu.pitt.apollo.syntheticpopulationserviceconnector;

import edu.pitt.apollo.connector.SyntheticPopulationServiceConnector;
import edu.pitt.apollo.exception.SyntheticPopulationServiceException;
import edu.pitt.apollo.service.syntheticpopulationservice.v4_0.KillRunRequest;
import edu.pitt.apollo.service.syntheticpopulationservice.v4_0.KillRunResponse;
import edu.pitt.apollo.service.syntheticpopulationservice.v4_0.SyntheticPopulationServiceEI;
import edu.pitt.apollo.service.syntheticpopulationservice.v4_0.SyntheticPopulationServiceV40;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.synthetic_population_service_types.v4_0.RunSyntheticPopulationGenerationMessage;
import java.math.BigInteger;
import java.net.URL;

/**
 *
 * @author nem41
 */
public class SoapSyntheticPopulationServiceConnector extends SyntheticPopulationServiceConnector {

	private SyntheticPopulationServiceEI port;

	public SoapSyntheticPopulationServiceConnector(String url) throws SyntheticPopulationServiceException {
		super(url);
		initialize();
	}

	private void initialize() throws SyntheticPopulationServiceException {
		try {
			port = new SyntheticPopulationServiceV40(new URL(serviceUrl)).getSyntheticPopulationServiceEndpoint();
		} catch (Exception ex) {
			throw new SyntheticPopulationServiceException("Exception getting synthetic population service endpoint: " + ex.getMessage());
		}
	}

	@Override
	public void killRun(BigInteger runId) throws SyntheticPopulationServiceException {
		KillRunRequest killrunRequest = new KillRunRequest();
		killrunRequest.setRunId(runId);
		KillRunResponse response = port.killRun(killrunRequest);
		if (response.getRunStatus().getRunStatus().equals(MethodCallStatusEnum.FAILED)) {
			throw new SyntheticPopulationServiceException("The kill run request failed: " + response.getRunStatus().getErrorMessage());
		}
	}

	@Override
	public BigInteger runSyntheticPopulationGeneration(RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
		return port.runSyntheticPopulationGeneration(runSyntheticPopulationGenerationMessage);
	}

}

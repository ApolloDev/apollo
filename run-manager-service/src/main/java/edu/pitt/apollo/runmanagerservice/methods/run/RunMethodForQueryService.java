package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.query_service_types.v4_0_1.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_1.RunMessage;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.JsonUtils;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:17:43 PM Class: RunMethodForSimulationAndVisualization
 */
public class RunMethodForQueryService extends AbstractRunMethod {

	private Class runMessageClass = null;

	public RunMethodForQueryService(BigInteger stagedRunId, Authentication authentication) throws JsonUtilsException, DatastoreException, FilestoreException {
		super(stagedRunId, authentication, "run_message.json");
	}

	@Override
	protected String getRunMessageJson(String runMessageFilename) throws DatastoreException, FilestoreException {
		String json = dataServiceDao.getRunMessageAssociatedWithRunIdAsJsonOrNull(runId, authentication, "run_message.json");

		if (json.contains("\"type\" : \"RunSimulatorOutputQueryMessage\"")) {
			runMessageClass = RunSimulatorOutputQueryMessage.class;
		} else {
			throw new DatastoreException("Unsupported run message type when getting run message json");
		}

		return json;
	}

	@Override
	protected RunMessage convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException {
		JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));
		return (RunSimulatorOutputQueryMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, runMessageClass);
	}

	@Override
	protected MethodCallStatus getDefaultSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.CALLED_QUERY_SERVICE);
		status.setMessage("The run request has been sent to the query service");
		return status;
	}
}

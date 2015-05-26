package edu.pitt.apollo;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationResult;
import edu.pitt.apollo.dataservice.methods.run.GetRunInformationMethod;
import edu.pitt.apollo.dataservice.methods.run.SetSimulationGroupIdsForRunIdMethod;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 5/21/15.
 */
public class RestDataServiceImpl extends DataServiceImpl {

    public GetRunInformationResult getRunInformation(GetRunInformationMessage message) {
        GetRunInformationResult result = GetRunInformationMethod.getRunInformation(message);
        return result;
    }

    public MethodCallStatus addSimulationGroupIdsForRunId(List<BigInteger> groupIds, BigInteger runId) {
        MethodCallStatus mcs = SetSimulationGroupIdsForRunIdMethod.setSimulationGroupIdsForRun(groupIds, runId);
        return mcs;
    }
}

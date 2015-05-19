package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.GetStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetStatusOfRunResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/15/15.
 */
public class GetStatusOfRunMethod {
    public static GetStatusOfRunResult getStatusOfRunAndGetResult(GetStatusOfRunMessage message) {
        GetStatusOfRunResult result = new GetStatusOfRunResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils();

            MethodCallStatus runStatus = dbUtils.getStatusOfLastServiceToBeCalledForRun(message.getRunId());
            result.setStatusMessage(runStatus.getMessage());
            result.setStatusEnum(runStatus.getStatus());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setMethodCallStatus(mcs);
            return result;
        }catch(ApolloDatabaseException ade) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(ade.getMessage());
            result.setMethodCallStatus(mcs);
            return result;
        }
    }
}

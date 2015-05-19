package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.UpdateStatusOfRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.UpdateStatusOfRunResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/11/15.
 */
public class UpdateStatusOfRunMethod {

    public static UpdateStatusOfRunResult updateStatusOfRunAndGetResult(UpdateStatusOfRunMessage message) {
        UpdateStatusOfRunResult result = new UpdateStatusOfRunResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils();

            dbUtils.updateStatusOfRun(message.getRunId(),message.getStatusEnum(),message.getStatusMessage());
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

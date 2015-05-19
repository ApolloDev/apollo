package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.RemoveRunDataMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RemoveRunDataResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/18/15.
 */
public class RemoveRunDataMethod {

    public static RemoveRunDataResult removeRunDataAndGetResult(RemoveRunDataMessage message) {
        MethodCallStatus mcs = new MethodCallStatus();
        RemoveRunDataResult result = new RemoveRunDataResult();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils()){
            dbUtils.removeRunData(message.getRunId());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseException e) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        }
        return result;
    }
}

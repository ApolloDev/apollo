package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.RemoveRunDataMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RemoveRunDataResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
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
            DatabaseAccessor dbAccessor = new DatabaseAccessor(message.getAuthentication(),dbUtils);
            dbAccessor.removeRunData(message.getRunId(),message.getAuthentication());
//            dbUtils.removeRunData(message.getRunId());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseException ade) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(ade.getMessage());
            result.setMethodCallStatus(mcs);
        } catch (DataServiceException dse) {
            dse.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(dse.getMessage());
            result.setMethodCallStatus(mcs);
        }
        return result;
    }
}

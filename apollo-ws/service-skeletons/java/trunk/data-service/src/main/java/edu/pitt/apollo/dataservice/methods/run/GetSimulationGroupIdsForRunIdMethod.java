package edu.pitt.apollo.dataservice.methods.run;


import edu.pitt.apollo.data_service_types.v3_0_0.GetRunIdsAssociatedWithSimulationGroupResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by dcs27 on 5/27/15.
 */
public class GetSimulationGroupIdsForRunIdMethod {
    public static GetRunIdsAssociatedWithSimulationGroupResult getSimulationGroupIdsForRun(BigInteger runId,Authentication authentication) {
        GetRunIdsAssociatedWithSimulationGroupResult result = new GetRunIdsAssociatedWithSimulationGroupResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(authentication, dbUtils);
            List<BigInteger> runIdsAssociatedWithSimulationGroup = dbAccessor.getRunIdsAssociatedWithSimulationGroupForRun(runId,authentication);
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setMethodCallStatus(mcs);
            for(BigInteger id : runIdsAssociatedWithSimulationGroup) {
                result.getAssociatedRunIds().add(id);
            }
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        }catch (DataServiceException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        }
        return result;
    }
}

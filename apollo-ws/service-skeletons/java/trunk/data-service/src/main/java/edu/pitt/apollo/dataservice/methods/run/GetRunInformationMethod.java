package edu.pitt.apollo.dataservice.methods.run;

import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by dcs27 on 5/21/15.
 */
public class GetRunInformationMethod {
    public static GetRunInformationResult getRunInformation(GetRunInformationMessage message) {
        GetRunInformationResult result = new GetRunInformationResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            List<Integer> listOfGroupIds =  dbUtils.getSimulationGroupIdsForRun(message.getRunId().intValue());
            for(Integer id : listOfGroupIds)
            {
                result.getAssociatedGroupIds().add(BigInteger.valueOf(id));
            }

            SoftwareIdentification si = dbUtils.getSoftwareIdentificationForRun(message.getRunId());
            result.setServiceType(si.getSoftwareType().value());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        }
        return result;
    }
}

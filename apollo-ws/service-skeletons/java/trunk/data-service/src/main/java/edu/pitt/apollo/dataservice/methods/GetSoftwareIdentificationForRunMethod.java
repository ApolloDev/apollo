package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationForRunResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * Created by dcs27 on 5/6/15.
 */
public class GetSoftwareIdentificationForRunMethod{



    public static GetSoftwareIdentificationForRunResult buildResultMessage(GetSoftwareIdentificationForRunMessage message) throws ApolloDatabaseException
    {
        ApolloDbUtils dbUtils = new ApolloDbUtils();
        SoftwareIdentification si;
        try {
           si = dbUtils.getSoftwareIdentificationForRun(message.getRunId());
            GetSoftwareIdentificationForRunResult result = new GetSoftwareIdentificationForRunResult();

            MethodCallStatus mcs = new MethodCallStatus();
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);

            result.setMethodCallStatus(mcs);

            result.setSoftwareIdentification(si);

            return result;
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            throw new ApolloDatabaseException(e.getMessage());
        }
    }
}

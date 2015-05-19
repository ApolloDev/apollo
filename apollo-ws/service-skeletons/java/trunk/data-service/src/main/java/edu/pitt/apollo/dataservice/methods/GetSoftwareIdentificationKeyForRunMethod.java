package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
//import edu.pitt.apollo.service.dataservice.v3_0_0.GetSoftwareIdentificationKeyForRun;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/7/15.
 */
public class GetSoftwareIdentificationKeyForRunMethod {

    public static GetSoftwareIdentificationKeyForRunResult getSoftwareIdentificationKeyForRun(GetSoftwareIdentificationKeyForRunMessage message)
    {

        GetSoftwareIdentificationKeyForRunResult result = new GetSoftwareIdentificationKeyForRunResult();
        MethodCallStatus mcs = new MethodCallStatus();
        BigInteger runId = message.getRunId();
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils();

            int softwareIdentificationKey = dbUtils.getSoftwareIdentificationKeyForRun(runId);


            mcs.setStatus(MethodCallStatusEnum.COMPLETED);

            result.setMethodCallStatus(mcs);

            result.setSoftwareIdentificationKey(BigInteger.valueOf(softwareIdentificationKey));

            return result;
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
            return result;
        }
    }
}

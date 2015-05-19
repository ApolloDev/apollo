package edu.pitt.apollo.dataservice.methods;


import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationKeyFromSoftwareIdentificationResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/7/15.
 */
public class GetSoftwareIdentificationKeyFromSoftwareIdentificationMethod {

    public static GetSoftwareIdentificationKeyFromSoftwareIdentificationResult getSoftwareIdentificationKeyFromSoftwareIdentification(GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage message)
    {
        GetSoftwareIdentificationKeyFromSoftwareIdentificationResult result = new GetSoftwareIdentificationKeyFromSoftwareIdentificationResult();
        MethodCallStatus mcs = new MethodCallStatus();
        SoftwareIdentification si = message.getSoftwareIdentification();
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils();
            int softwareIdentificationKey = dbUtils.getSoftwareIdentificationKey(si);
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

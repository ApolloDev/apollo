package edu.pitt.apollo.dataservice.methods.softwaremethods;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.GetListOfRegisteredSoftwareResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;

import java.util.Map;

/**
 * Created by dcs27 on 5/19/15.
 */
public class GetListOfRegisteredSoftwareMethod {
    public static GetListOfRegisteredSoftwareResult getListOfRegisteredSoftware() {
        GetListOfRegisteredSoftwareResult result = new GetListOfRegisteredSoftwareResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            Map<Integer,ServiceRegistrationRecord> softwareIdToRegistrationRecord = dbUtils.getRegisteredSoftware();

            for(Integer id : softwareIdToRegistrationRecord.keySet()) {
                result.getResgisteredSoftware().add(softwareIdToRegistrationRecord.get(id));
            }
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

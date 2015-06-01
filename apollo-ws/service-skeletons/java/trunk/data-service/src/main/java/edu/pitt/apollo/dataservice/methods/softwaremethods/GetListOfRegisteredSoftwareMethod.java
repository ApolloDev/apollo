package edu.pitt.apollo.dataservice.methods.softwaremethods;

import edu.pitt.apollo.data_service_types.v3_0_0.GetListOfRegisteredSoftwareResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;

import java.util.Map;

/**
 * Created by dcs27 on 5/19/15.
 */
public class GetListOfRegisteredSoftwareMethod {
    public static GetListOfRegisteredSoftwareResult getListOfRegisteredSoftware(Authentication authentication) {
        GetListOfRegisteredSoftwareResult result = new GetListOfRegisteredSoftwareResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(authentication,dbUtils);
            Map<Integer,ServiceRegistrationRecord> softwareIdToRegistrationRecord = dbAccessor.getListOfRegisteredSoftwareRecords(authentication);
//            Map<Integer,ServiceRegistrationRecord> softwareIdToRegistrationRecord = dbUtils.getRegisteredSoftware();

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
        } catch (DataServiceException ase) {
            ase.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(ase.getMessage());
            result.setMethodCallStatus(mcs);
        }
        return result;
    }
}

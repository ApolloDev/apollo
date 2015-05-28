package edu.pitt.apollo.dataservice.methods.run;

import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationForRunMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetSoftwareIdentificationForRunResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * Created by dcs27 on 5/28/15.
 */
public class GetLastServiceToBeCalledForRun {

    public static GetSoftwareIdentificationForRunResult getLastServiceToBeCalledForRun(GetSoftwareIdentificationForRunMessage message) {
        GetSoftwareIdentificationForRunResult result = new GetSoftwareIdentificationForRunResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(message.getAuthentication(),dbUtils);
            SoftwareIdentification si = dbAccessor.getLastServiceToBeCalledForRun(message.getRunId(),message.getAuthentication());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setMethodCallStatus(mcs);
            result.setSoftwareIdentification(si);
        } catch (ApolloDatabaseException ade) {
            ade.printStackTrace();
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

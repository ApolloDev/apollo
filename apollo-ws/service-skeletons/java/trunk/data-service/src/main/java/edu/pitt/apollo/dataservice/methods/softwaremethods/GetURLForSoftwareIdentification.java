package edu.pitt.apollo.dataservice.methods.softwaremethods;

import edu.pitt.apollo.data_service_types.v3_0_0.GetURLForSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLForSoftwareIdentificationResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * Created by dcs27 on 5/28/15.
 */
public class GetURLForSoftwareIdentification {
    public static GetURLForSoftwareIdentificationResult getURLForSoftwareIdentification(GetURLForSoftwareIdentificationMessage message)
    {
        GetURLForSoftwareIdentificationResult result = new GetURLForSoftwareIdentificationResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(message.getAuthentication(),dbUtils);

            String wsdlURL = dbAccessor.getURLForSoftwareIdentificationWithSoftwareNameAndVersion(message.getSoftwareName(),message.getSoftwareVersion(),message.getAuthentication());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setWsdlURL(wsdlURL);
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        } catch (DataServiceException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        }
        return result;
    }
}

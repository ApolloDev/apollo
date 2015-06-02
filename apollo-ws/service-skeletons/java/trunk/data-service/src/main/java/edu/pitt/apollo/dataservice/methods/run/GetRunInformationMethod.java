package edu.pitt.apollo.dataservice.methods.run;

import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RunInformation;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by dcs27 on 5/21/15.
 */
public class GetRunInformationMethod {
    public static RunInformation getRunInformation(GetRunInformationMessage message) {
		
		// NEEDS FIXED
		
//        RunInformation result = new RunInformation();
//
//        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
//        {
//            List<Integer> listOfGroupIds =  dbUtils.getSimulationGroupIdsForRun(message.getRunId().intValue());
//            for(Integer id : listOfGroupIds)
//            {
//                result.getAssociatedGroupIds().add(BigInteger.valueOf(id));
//            }
//
//            SoftwareIdentification si = dbUtils.getSoftwareIdentificationForRun(message.getRunId());
//            result.setSoftwareIdentification(si);
//        } catch (ApolloDatabaseException e) {
//            e.printStackTrace();
//            mcs.setStatus(MethodCallStatusEnum.FAILED);
//            mcs.setMessage(e.getMessage());
//            result.setMethodCallStatus(mcs);
//        }
//        return result;
		
		return null;
    }
}

package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.AssociateContentWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateContentWithRunIdResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/8/15.
 */
public class AssociateContentWithRunIdMethod {



    public static AssociateContentWithRunIdResult associateContentWithRunIdResult(AssociateContentWithRunIdMessage message) {
        MethodCallStatus mcs = new MethodCallStatus();
        AssociateContentWithRunIdResult result = new AssociateContentWithRunIdResult();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils()) {


//            int dataContentKey = dbUtils.associateContentWithRunId(message.getRunId(), message.getDataContentKey().intValue(), message.getRunDataDescriptionId().intValue());

            mcs.setStatus(MethodCallStatusEnum.COMPLETED);

            result.setMethodCallStatus(mcs);

            return result;
        } catch (ApolloDatabaseKeyNotFoundException e) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
            return result;
        } catch (ApolloDatabaseException e) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
            return result;
        }

   }

}

package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.AddTextDataContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddTextDataContentResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/8/15.
 */
public class AddTextDataContentMethod {



    public static AddTextDataContentResult addTextDataContent(AddTextDataContentMessage message)
    {
        MethodCallStatus mcs = new MethodCallStatus();
        AddTextDataContentResult result = new AddTextDataContentResult();

        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils();

            int dataContentKey = dbUtils.addTextDataContent(message.getUrl());


            mcs.setStatus(MethodCallStatusEnum.COMPLETED);

            result.setMethodCallStatus(mcs);

            result.setDataContentKey(BigInteger.valueOf(dataContentKey));
            return result;
        }catch(ApolloDatabaseException ex)
        {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(ex.getMessage());
            result.setMethodCallStatus(mcs);
            return result;
        }

    }
}

package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.GetRunDataDescriptionIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunDataDescriptionIdResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataFormatEnum;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataType;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/11/15.
 */
public class GetRunDataDescriptionIdMethod {



    public static GetRunDataDescriptionIdResult buildRunDataDescriptionIdResultMessage(GetRunDataDescriptionIdMessage message)
    {
        GetRunDataDescriptionIdResult result = new GetRunDataDescriptionIdResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try{
            ApolloDbUtils dbUtils = new ApolloDbUtils();

            DbContentDataFormatEnum formatEnum = DbContentDataFormatEnum.valueOf(message.getDataFormat().value());
            DbContentDataType contentTypeEnum = DbContentDataType.valueOf(message.getDataType().value());
            int runDescriptionId = dbUtils.getRunDataDescriptionId(formatEnum, message.getDataLabel(), contentTypeEnum, message.getDataSourceSoftwareIdKey().intValue(), message.getDataDestinationSoftwareIdKey().intValue());

            mcs.setStatus(MethodCallStatusEnum.COMPLETED);

            result.setMethodCallStatus(mcs);
            result.setRunDescriptionId(BigInteger.valueOf(runDescriptionId));

        }catch(ApolloDatabaseException ade) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(ade.getMessage());
            result.setMethodCallStatus(mcs);
        }

        return result;
    }
}

package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateFileWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateFileWithRunIdResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataFormatEnum;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataType;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;
import java.sql.SQLException;

/**
 * Created by dcs27 on 5/20/15.
 */
public class AssociateFileWithRunIdMethod {
    public static AssociateFileWithRunIdResult associateFileWithRunId(AssociateFileWithRunIdMessage message) {
        AssociateFileWithRunIdResult result = new AssociateFileWithRunIdResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(message.getAuthentication(),dbUtils);
            ContentDataTypeEnum dataTypeEnum = ContentDataTypeEnum.valueOf(message.getContentType().toString());
            dbAccessor.associateContentWithRunIdWithSoftareNameAndVersionParameters(message.getRunId(),message.getFileTextContent(),message.getSourceSoftwareName(),
                    message.getSourceSoftwareVersion(),message.getDestinationSoftwareName(),message.getDestinationSoftwareVersion(),
                    message.getContentLabel(), ContentDataFormatEnum.TEXT,dataTypeEnum,message.getAuthentication());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
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

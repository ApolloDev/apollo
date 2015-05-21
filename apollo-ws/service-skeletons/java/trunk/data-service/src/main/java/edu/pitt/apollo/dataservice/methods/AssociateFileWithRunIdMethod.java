package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateFileWithRunIdMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateFileWithRunIdResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataFormatEnum;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataType;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/20/15.
 */
public class AssociateFileWithRunIdMethod {
    public static AssociateFileWithRunIdResult associateFileWithRunId(AssociateFileWithRunIdMessage message) {
        AssociateFileWithRunIdResult result = new AssociateFileWithRunIdResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            int contentId = dbUtils.addTextDataContent(message.getFileTextContent());
            int sourceSoftwareId = dbUtils.getSoftwareIdentificationKeyFromNameAndVersion(message.getSourceSoftwareName(), message.getSourceSoftwareVersion());
            int destinationSoftwareId = dbUtils.getSoftwareIdentificationKeyFromNameAndVersion(message.getDestinationSoftwareName(), message.getDestinationSoftwareVersion());
            DbContentDataType dataTypeEnum = DbContentDataType.valueOf(message.getContentType().toString());
            int runDataDescriptionId = dbUtils.getRunDataDescriptionId(DbContentDataFormatEnum.TEXT,message.getContentLabel(),dataTypeEnum,sourceSoftwareId,destinationSoftwareId);
            dbUtils.associateContentWithRunId(message.getRunId(),contentId,runDataDescriptionId);
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        } catch (Md5UtilsException ex) {
            ex.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(ex.getMessage());
            result.setMethodCallStatus(mcs);
        }
        return result;
    }


}

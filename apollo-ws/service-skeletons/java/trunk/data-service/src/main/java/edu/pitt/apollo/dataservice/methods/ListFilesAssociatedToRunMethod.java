package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.ContentIdAndLabel;
import edu.pitt.apollo.data_service_types.v3_0_0.ListFilesMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.ListFilesResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by dcs27 on 5/18/15.
 */
public class ListFilesAssociatedToRunMethod {
    public static ListFilesResult listFilesAssocaitedToRun(ListFilesMessage message) {
        ListFilesResult result = new ListFilesResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils()) {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(message.getAuthentication(),dbUtils);
            HashMap<BigInteger,String> contentIdToLabelMap = dbAccessor.getListOfFilesForRunId(message.getRunId(),message.getAuthentication());

            for(BigInteger id : contentIdToLabelMap.keySet())
            {
                ContentIdAndLabel contentIdAndLabel = new ContentIdAndLabel();
                contentIdAndLabel.setContentId(id);
                contentIdAndLabel.setLabel(contentIdToLabelMap.get(id));
                result.getContentIdAndLabels().add(contentIdAndLabel);
            }
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

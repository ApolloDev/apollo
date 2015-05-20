package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.ContentIdAndLabel;
import edu.pitt.apollo.data_service_types.v3_0_0.ListURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.ListURLsResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * Created by dcs27 on 5/20/15.
 */
public class ListURLsAssociatedToRunMethod {
    public static ListURLsResult listURLsAssociatedToRun(ListURLsMessage message) {
        ListURLsResult result = new ListURLsResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils()) {
            HashMap<BigInteger,String> contentIdToLabelMap = dbUtils.getListOfURLsForRunId(message.getRunId());

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
        }
        return result;
    }
}

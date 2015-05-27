package edu.pitt.apollo.dataservice.methods.url;

import edu.pitt.apollo.data_service_types.v3_0_0.GetURLContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLContentResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/20/15.
 */
public class GetURLContentMethod {
    public static GetURLContentResult getUrl(GetURLContentMessage message) {
        GetURLContentResult result = new GetURLContentResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(message.getAuthentication(),dbUtils);
            String url = dbAccessor.getURLForURLId(message.getUrlId(), message.getAuthentication());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setURL(url);
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

package edu.pitt.apollo.dataservice.methods.user;

import edu.pitt.apollo.data_service_types.v3_0_0.DeleteUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.DeleteUserResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/18/15.
 */
public class DeleteUserMethod {
    public static DeleteUserResult deleteUser(DeleteUserMessage message) {
        DeleteUserResult result = new DeleteUserResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            dbUtils.deleteUser(message.getUserId(),message.getUserPassword());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            mcs.setMessage("User successfully deleted.");
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseKeyNotFoundException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
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

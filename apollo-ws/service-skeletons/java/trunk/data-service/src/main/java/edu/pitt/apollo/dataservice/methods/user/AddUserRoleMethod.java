package edu.pitt.apollo.dataservice.methods.user;

import edu.pitt.apollo.data_service_types.v3_0_0.AddUserRoleMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddUserRoleResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/18/15.
 */
public class AddUserRoleMethod {
    public static AddUserRoleResult addUserRole(AddUserRoleMessage message) {
        AddUserRoleResult result = new AddUserRoleResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            dbUtils.addUserRole(message.getUserId(),message.getUserPassword(),message.getSoftwareIdentification(),message.isCanRunSoftware(),message.isCanRequestPrivileged());
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

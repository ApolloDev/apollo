package edu.pitt.apollo.dataservice.methods.user;

import edu.pitt.apollo.data_service_types.v3_0_0.AddRoleMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddRoleResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/18/15.
 */
public class AddRoleMethod {
    public static AddRoleResult addRole(AddRoleMessage message) {
        AddRoleResult result = new AddRoleResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            dbUtils.addRole(message.getSoftwareIdentification(),message.isCanRun(),message.isCanViewCache());
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

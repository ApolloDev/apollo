package edu.pitt.apollo.dataservice.methods.user;

import edu.pitt.apollo.data_service_types.v3_0_0.AddUserMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddUserResult;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseRecordAlreadyExistsException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/18/15.
 */
public class AddUserMethod {
    public static AddUserResult addUser(AddUserMessage message) {
        AddUserResult result = new AddUserResult();
        MethodCallStatus mcs = new MethodCallStatus();
        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            dbUtils.addUser(message.getUserId(),message.getUserPassword(),message.getUserEmail());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            mcs.setMessage("User added successfully.");
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseUserPasswordException e) {
            e.printStackTrace();
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseRecordAlreadyExistsException e) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        } catch (ApolloDatabaseException e) {
            mcs.setStatus(MethodCallStatusEnum.FAILED);
            mcs.setMessage(e.getMessage());
            result.setMethodCallStatus(mcs);
        }

        return result;
    }
}

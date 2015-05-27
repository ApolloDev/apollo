package edu.pitt.apollo.dataservice.methods.file;

import edu.pitt.apollo.data_service_types.v3_0_0.GetFileContentMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetFileContentResult;
import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

/**
 * Created by dcs27 on 5/20/15.
 */
public class GetFileContentMethod {
    public static GetFileContentResult getFileContent(GetFileContentMessage message) {
        GetFileContentResult result = new GetFileContentResult();
        MethodCallStatus mcs = new MethodCallStatus();

        try(ApolloDbUtils dbUtils = new ApolloDbUtils())
        {
            DatabaseAccessor dbAccessor = new DatabaseAccessor(message.getAuthentication(),dbUtils);
            String fileContent =dbAccessor.getFileContentForFileId(message.getFileId(), message.getAuthentication());
            mcs.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setFileContents(fileContent);
            result.setMethodCallStatus(mcs);
        } catch (DataServiceException e) {
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

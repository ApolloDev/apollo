package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetCacheDataResult;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

/**
 * Created by nem41 on 12/12/16.
 */
public class GetCacheDataMethod extends BaseLibraryMethod {


    public GetCacheDataMethod(Authentication authentication) throws LibraryServiceException {
        super(authentication);
    }

    public GetCacheDataResult getCacheData(LibraryDbUtils dbUtils) throws LibraryServiceException {

        try {
            GetCacheDataResult result = dbUtils.getCacheData(role);
            MethodCallStatus status = new MethodCallStatus();
            result.setStatus(status);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            return result;
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

}

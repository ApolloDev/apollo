package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetCacheDataResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.Map;

/**
 * Created by nem41 on 12/12/16.
 */
public class GetCacheDataMethod extends BaseLibraryMethod {


    public GetCacheDataMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetCacheDataResult getCacheData(LibraryDbUtils dbUtils) throws LibraryServiceException {

        try {
            GetCacheDataResult result = dbUtils.getCacheData();
            MethodCallStatus status = new MethodCallStatus();
            result.setStatus(status);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            return result;
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }

}

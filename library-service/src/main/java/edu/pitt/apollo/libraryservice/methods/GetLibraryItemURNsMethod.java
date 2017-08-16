package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetLibraryItemURNsResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.Map;

public class GetLibraryItemURNsMethod extends BaseLibraryMethod {

    public GetLibraryItemURNsMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetLibraryItemURNsResult getLibraryItemURIs(LibraryDbUtils dbUtils,
                                                              String itemType) throws LibraryServiceException {

        //execute query
        GetLibraryItemURNsResult result = new GetLibraryItemURNsResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            result.getURNs().addAll(dbUtils.getURNs(itemType));
            status.setMessage("");
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }

}

package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemDisplayNamesAndURNsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.LibraryItemDisplayNameAndURN;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by nem41 on 12/12/16.
 */
public class GetLibraryItemDisplayNamesAndURNsMethod extends BaseLibraryMethod {
    public GetLibraryItemDisplayNamesAndURNsMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetLibraryItemDisplayNamesAndURNsResult getLibraryItemDisplayNamesAndURNs(LibraryDbUtils libraryDbUtils, String className, boolean includeUnreleasedItems) throws LibraryServiceException {

        GetLibraryItemDisplayNamesAndURNsResult result = new GetLibraryItemDisplayNamesAndURNsResult();
        MethodCallStatus status = new MethodCallStatus();

        try {
            List<LibraryItemDisplayNameAndURN> libraryItemContainerList = libraryDbUtils.getLibraryItemDisplayNamesAndURNs(className, includeUnreleasedItems, highestRole);
            result.getLibraryItems().addAll(libraryItemContainerList);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setStatus(status);
            return result;
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }
}

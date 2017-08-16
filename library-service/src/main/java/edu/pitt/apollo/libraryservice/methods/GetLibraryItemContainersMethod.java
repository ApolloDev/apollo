package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetLibraryItemContainersResult;
import edu.pitt.apollo.library_service_types.v4_0_2.LibraryItemContainer;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by nem41 on 12/12/16.
 */
public class GetLibraryItemContainersMethod extends BaseLibraryMethod {
    public GetLibraryItemContainersMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetLibraryItemContainersResult getLibraryItemContainers(LibraryDbUtils libraryDbUtils, String className, boolean includeUnreleasedItems) throws LibraryServiceException {

        GetLibraryItemContainersResult result = new GetLibraryItemContainersResult();
        MethodCallStatus status = new MethodCallStatus();

        try {
            List<LibraryItemContainer> libraryItemContainerList = libraryDbUtils.getLibraryItemContainers(className, includeUnreleasedItems, highestRole);
            result.getLibraryItems().addAll(libraryItemContainerList);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setStatus(status);
            return result;
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }
}

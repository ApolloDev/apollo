package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetMembersOfCollectionResult;
import edu.pitt.apollo.library_service_types.v4_0_1.LibraryItemContainerAndURN;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

import java.util.List;

/**
 * Created by nem41 on 12/12/16.
 */
public class GetMembersOfCollectionMethod extends BaseLibraryMethod {
    public GetMembersOfCollectionMethod(Authentication authentication) throws LibraryServiceException {
        super(authentication);
    }

    public GetMembersOfCollectionResult getMembersOfCollectionResult(LibraryDbUtils libraryDbUtils, int urn, int version, boolean includeUnreleasedItems) throws LibraryServiceException {

        GetMembersOfCollectionResult result = new GetMembersOfCollectionResult();
        MethodCallStatus status = new MethodCallStatus();

        try {
            List<LibraryItemContainerAndURN> collections = libraryDbUtils.getMembersOfCollection(urn, version, includeUnreleasedItems, role);

            result.getMembers().addAll(collections);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setStatus(status);
            return result;
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }
}

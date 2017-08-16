package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetMembersOfCollectionResult;
import edu.pitt.apollo.library_service_types.v4_0_2.LibraryItemDisplayNameAndURN;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by nem41 on 12/12/16.
 */
public class GetMembersOfCollectionMethod extends BaseLibraryMethod {
    public GetMembersOfCollectionMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetMembersOfCollectionResult getMembersOfCollectionResult(LibraryDbUtils libraryDbUtils, int urn, int version, boolean includeUnreleasedItems) throws LibraryServiceException {

        GetMembersOfCollectionResult result = new GetMembersOfCollectionResult();
        MethodCallStatus status = new MethodCallStatus();

        try {
            List<LibraryItemDisplayNameAndURN> collections = libraryDbUtils.getMembersOfCollection(urn, version, includeUnreleasedItems, highestRole);

            result.getMembers().addAll(collections);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setStatus(status);
            return result;
        } catch (ApolloDatabaseException | NoLibraryItemException | UserNotAuthorizedException | ApolloDatabaseExplicitException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }
    }
}

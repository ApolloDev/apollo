package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetLibraryItemContainerResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:46:59 PM
 * Class: GetLibraryItemMethod
 */
public class GetLibraryItemMethod extends BaseLibraryMethod {

    public GetLibraryItemMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetLibraryItemContainerResult getLibraryItemMethod(LibraryDbUtils dbUtils, int urn, Integer version) throws LibraryServiceException {

        GetLibraryItemContainerResult result;
        MethodCallStatus status = new MethodCallStatus();

        try {
            result = dbUtils.getLibraryItemContainer(urn, version, highestRole);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException | UserNotAuthorizedException | NoLibraryItemException | ApolloDatabaseExplicitException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        result.setStatus(status);
        return result;
    }

}

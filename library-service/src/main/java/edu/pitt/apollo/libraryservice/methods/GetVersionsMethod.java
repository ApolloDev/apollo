package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseExplicitException;
import edu.pitt.apollo.db.exceptions.library.NoLibraryItemException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetRevisionsResult;
import edu.pitt.apollo.library_service_types.v4_0_2.RevisionAndComments;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 12:14:11 PM
 * Class: GetVersionsMethod
 */
public class GetVersionsMethod extends BaseLibraryMethod {

    public GetVersionsMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetRevisionsResult getVersions(LibraryDbUtils dbUtils, int urn) throws LibraryServiceException {


        GetRevisionsResult result = new GetRevisionsResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            List<RevisionAndComments> versions = dbUtils.getRevisionsAndComments(urn, highestRole);
            result.getRevisionsAndComments().addAll(versions);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException | NoLibraryItemException | UserNotAuthorizedException | ApolloDatabaseExplicitException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }
}

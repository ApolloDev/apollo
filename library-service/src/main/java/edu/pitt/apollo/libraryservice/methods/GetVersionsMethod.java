package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_1.GetRevisionsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.RevisionAndComments;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

import java.util.List;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 12:14:11 PM
 * Class: GetVersionsMethod
 */
public class GetVersionsMethod extends BaseLibraryMethod {

    public GetVersionsMethod(Authentication authentication) throws LibraryServiceException {
        super(authentication);
    }

    public GetRevisionsResult getVersions(LibraryDbUtils dbUtils, int urn) throws LibraryServiceException {


        GetRevisionsResult result = new GetRevisionsResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            List<RevisionAndComments> versions = dbUtils.getRevisionsAndComments(urn, role);
            result.getRevisionsAndComments().addAll(versions);
            status.setStatus(MethodCallStatusEnum.COMPLETED);
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        return result;
    }
}

package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.library_service_types.v4_0_2.GetReleaseVersionResult;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.util.Map;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Aug 13, 2014 Time: 3:46:59 PM Class: GetLibraryItemMethod
 */
public class GetApprovedRevisionOfLibraryItemMethod extends BaseLibraryMethod {

    public GetApprovedRevisionOfLibraryItemMethod(Authentication authentication, Map<String, Integer> roles) throws LibraryServiceException {
        super(authentication, roles);
    }

    public GetReleaseVersionResult getLibraryItemMethod(LibraryDbUtils dbUtils, int urn) throws LibraryServiceException {

        GetReleaseVersionResult result;
        MethodCallStatus status = new MethodCallStatus();

        try {
            Integer revision = dbUtils.getReleaseVersion(urn);
            result = new GetReleaseVersionResult();
            if (revision != null) {
                result.setVersion(revision);
                result.setHasReleasedVersion(true);
                status.setStatus(MethodCallStatusEnum.COMPLETED);
            } else {
                status.setStatus(MethodCallStatusEnum.NOT_FOUND);
                status.setMessage("No approved revision found");
                result.setHasReleasedVersion(false);
            }
        } catch (ApolloDatabaseException ex) {
            throw new LibraryServiceException(ex.getMessage());
        }

        result.setStatus(status);
        return result;
    }

}

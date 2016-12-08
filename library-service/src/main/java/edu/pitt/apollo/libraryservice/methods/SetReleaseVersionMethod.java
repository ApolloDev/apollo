package edu.pitt.apollo.libraryservice.methods;

import edu.pitt.apollo.database.LibraryDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.library_service_types.v4_0_1.SetReleaseVersionResult;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Nov 7, 2014
 * Time: 10:49:57 AM
 * Class: SetReleaseVersionMethod
 */
public class SetReleaseVersionMethod {

    public static SetReleaseVersionResult setReleaseVersion(LibraryDbUtils dbUtils, int urn, int version, String comment, Authentication authentication) {


        SetReleaseVersionResult result = new SetReleaseVersionResult();
        MethodCallStatus status = new MethodCallStatus();
        result.setStatus(status);

        try {
            dbUtils.setReleaseVersion(urn, version, authentication.getPayload(), comment);
            status.setStatus(MethodCallStatusEnum.COMPLETED);

        } catch (ApolloDatabaseException ex) {
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage(ex.getMessage());
        }

        return result;
    }

}

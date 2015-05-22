package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import java.math.BigInteger;

/**
 * Created by jdl50 on 5/21/15.
 */
public abstract class DataServiceAccessorForRunningSimulations extends DataServiceAccessor {

    @Override
    public String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId) throws DataServiceException {
        return null;
    }


}

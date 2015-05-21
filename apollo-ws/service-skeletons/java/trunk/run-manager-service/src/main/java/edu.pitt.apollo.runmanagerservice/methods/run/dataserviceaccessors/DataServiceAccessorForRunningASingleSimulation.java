package edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors;

import edu.pitt.apollo.Md5UtilsException;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessorForRunningASingleSimulation extends DataServiceAccessorForRunningSimulations {
    @Override
    public BigInteger[] insertRunIntoDatabase(BigInteger memberOfSimulationGroupIdOrNull) throws DataserviceException, Md5UtilsException {
        return new BigInteger[0];
    }
}

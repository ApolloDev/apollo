package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessImpl;
import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Created by nem41 on 9/2/15.
 */
public class RunMethodFactory {

    public static AbstractRunMethod getRunMethod(BigInteger runId, Authentication authentication) throws DataServiceException, JsonUtilsException {

        DatastoreAccessImpl dataServiceAccessor = new DatastoreAccessImpl();
        SoftwareIdentification runSoftwareId = dataServiceAccessor.getSoftwareIdentificationForRun(runId, authentication);
        ApolloSoftwareTypeEnum type;
        if (runSoftwareId != null) {
           type = runSoftwareId.getSoftwareType();
        } else {
            type = ApolloSoftwareTypeEnum.SIMULATOR; // assume simulator type for transmission experiment, which don't list a single
            // software ID for the experiment
        }

        AbstractRunMethod runMethod;
        switch (type) {

            case SIMULATOR:
                runMethod = new RunMethodForSimulation(runId ,authentication);
                break;
            case VISUALIZER:
                runMethod = new RunMethodForVisualization(runId ,authentication);
                break;
            case DATA:
                runMethod = new RunMethodForDatastore(runId, authentication);
                break;
            default:
                throw new RunManagementException("The requested software to run is of an unsupported type");
        }

        return runMethod;
    }

}

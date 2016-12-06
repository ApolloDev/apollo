package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.types.v4_0_1.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;

import java.math.BigInteger;import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Created by nem41 on 9/2/15.
 */
public class RunMethodFactory {

    public static AbstractRunMethod getRunMethod(BigInteger runId, Authentication authentication) throws RunManagementException, JsonUtilsException, DatastoreException, FilestoreException {

        DatastoreAccessor dataServiceAccessor = new DatastoreAccessor();
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
			case QUERY_SERVICE:
				runMethod = new RunMethodForQueryService(runId, authentication);
                break;
            default:
                throw new RunManagementException("The requested software to run is of an unsupported type");
        }

        return runMethod;
    }

}

package edu.pitt.apollo.dataservice.methods.database;

//delete one of these!!!!

import java.math.BigInteger;

import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.RunIdAndCollisionId;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;

public class DatabaseAccessorForRunningMultipleSimulations extends
        DatabaseAccessorForRunningSimulations {

    public DatabaseAccessorForRunningMultipleSimulations(
            RunSimulationsMessage runSimulationsMessage,
            Authentication authentication,
            ApolloDbUtils dbUtils) throws ApolloDatabaseException {
        super(authentication, runSimulationsMessage
                .getSimulatorIdentification(), dbUtils, RunSimulationsMessage.class);
        this.runMessage = runSimulationsMessage;
    }

    @Override
    public BigInteger[] insertRunIntoDatabase(
            BigInteger memberOfSimulationGroupIdOrNull)
            throws ApolloDatabaseException, Md5UtilsException {
        RunIdAndCollisionId runIdAndHighestMD5CollisionIdForRun = dbUtils.getRunIdAndHighestMD5CollisionIdForRun(runMessage);

        boolean needToAddRun = true;
        int collisionId = 1;
        if ((runIdAndHighestMD5CollisionIdForRun.getCollisionId() > 0)){
            //just assume they are the same for now...it's not like MD5 will collide, but we should verify
            needToAddRun = false;
        }
        BigInteger[] runIdSimulationGroupId;
        if (needToAddRun) {

             runIdSimulationGroupId = dbUtils.addSimulationRun(
                    runMessage,
                    memberOfSimulationGroupIdOrNull, collisionId,
                    ((RunSimulationsMessage) runMessage)
                            .getSimulatorIdentification(),
                    TranslatorServiceRecordContainer
                            .getTranslatorSoftwareIdentification(), authentication);
        } else {
            runIdSimulationGroupId = new BigInteger[2];
            runIdSimulationGroupId[0] = runIdAndHighestMD5CollisionIdForRun.getRunId();
            runIdSimulationGroupId[1] = dbUtils.getSimulationGroupIdForRun(runIdAndHighestMD5CollisionIdForRun.getRunId());
        }
        return runIdSimulationGroupId;
    }
}

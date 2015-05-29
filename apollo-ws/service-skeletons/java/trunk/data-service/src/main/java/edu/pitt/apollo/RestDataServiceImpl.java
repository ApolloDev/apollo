package edu.pitt.apollo;


import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.dataservice.methods.GetAllOutputFilesURLAsZipMethod;
import edu.pitt.apollo.dataservice.methods.GetOutputFilesURLAsZipMethod;
import edu.pitt.apollo.dataservice.methods.GetOutputFilesURLsMethod;
import edu.pitt.apollo.dataservice.methods.run.*;
import edu.pitt.apollo.dataservice.methods.softwaremethods.GetListOfRegisteredSoftwareMethod;
import edu.pitt.apollo.dataservice.methods.softwaremethods.GetURLForSoftwareIdentification;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 5/21/15.
 */
public class RestDataServiceImpl extends DataServiceImpl {
    private static final ApolloServiceQueue serviceQueue;

    static {
        serviceQueue = new ApolloServiceQueue();
    }
    public GetRunInformationResult getRunInformation(GetRunInformationMessage message) {
        GetRunInformationResult result = GetRunInformationMethod.getRunInformation(message);
        return result;
    }

    public MethodCallStatus addRundIdsToSimulationGroupForRunId(List<BigInteger> groupIds, BigInteger runId, Authentication authentication) {
        MethodCallStatus mcs = SetSimulationGroupIdsForRunIdMethod.setSimulationGroupIdsForRun(groupIds, runId, authentication);
        return mcs;
    }

    public GetRunIdsAssociatedWithSimulationGroupResult getRunIdsAssociatedToSimulationGroupsForRunId(BigInteger runId, Authentication authentication) {
        GetRunIdsAssociatedWithSimulationGroupResult result = GetSimulationGroupIdsForRunIdMethod.getSimulationGroupIdsForRun(runId, authentication);
        return result;
    }

    public GetURLForSoftwareIdentificationResult getURLForSoftwareIdentification(GetURLForSoftwareIdentificationMessage message){
        GetURLForSoftwareIdentificationResult result = GetURLForSoftwareIdentification.getURLForSoftwareIdentification(message);
        return result;
    }
    public MethodCallStatus getAllOutputFilesURLAsZip(BigInteger runId,Authentication authentication) {
        GetAllOutputFilesURLAsZipMethod method = new GetAllOutputFilesURLAsZipMethod(serviceQueue, runId,authentication);
//        method.downloadFiles();
        return null;
    }
    public MethodCallStatus getOutputFilesURLs(BigInteger runId,Authentication authentication) {
        GetOutputFilesURLsMethod method = new GetOutputFilesURLsMethod(serviceQueue, runId,authentication);
        method.downloadFiles();
        return null;
    }

    public MethodCallStatus getOutputFilesURLAsZip(BigInteger runId,Authentication authentication) {
        GetOutputFilesURLAsZipMethod method = new GetOutputFilesURLAsZipMethod(serviceQueue, runId,authentication);
        method.downloadFiles();

        return null;
    }

    public GetSoftwareIdentificationForRunResult getLastServiceToBeCalledForRun(GetSoftwareIdentificationForRunMessage message)
    {
        GetSoftwareIdentificationForRunResult result = GetLastServiceToBeCalledForRun.getLastServiceToBeCalledForRun(message);
        return result;
    }

    public UpdateLastServiceToBeCalledForRunResult updateLastServiceToBeCalledForRunResult(UpdateLastServiceToBeCalledForRunMessage message)
    {
        UpdateLastServiceToBeCalledForRunResult result = UpdateLastServiceToBeCalledForRun.updateLastServiceToBeCalledForRun(message);
        return result;
    }

    public GetListOfRegisteredSoftwareResult getListOfRegisteredSoftware(Authentication authentication) {
        GetListOfRegisteredSoftwareResult result = GetListOfRegisteredSoftwareMethod.getListOfRegisteredSoftware(authentication);
        return result;
    }

}

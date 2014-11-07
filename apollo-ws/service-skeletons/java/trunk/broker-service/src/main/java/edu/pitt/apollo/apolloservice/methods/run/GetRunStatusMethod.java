package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDatabaseStatusNotFoundForRunIdException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.types.v2_1_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_1_0.SoftwareIdentification;
import java.math.BigInteger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:29:19 PM
 * Class: GetRunStatusMethod
 * IDE: NetBeans 6.9.1
 */
public class GetRunStatusMethod {

    private static MethodCallStatus getErrorMethodCallStatus(String message) {
        MethodCallStatus status = new MethodCallStatus();
        status.setStatus(MethodCallStatusEnum.FAILED);
        status.setMessage(message);
        return status;
    }

    public static MethodCallStatus getRunStatus(BigInteger runIdentification) {

        ApolloDbUtils dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
        // first check the apollo errors file
        if (ApolloServiceErrorHandler.checkErrorFileExists(runIdentification.intValue())) {

            MethodCallStatus status = new MethodCallStatus();
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage(ApolloServiceErrorHandler.readErrorFromErrorFile(runIdentification.intValue()));
            return status;
        }
        if (runIdentification.intValue() == -1) {
            return getErrorMethodCallStatus("Unable to write error file on server (disk full?).");
        }

        MethodCallStatus status = new MethodCallStatus();
        try {
            status = dbUtils.getStatusOfLastServiceToBeCalledForRun(runIdentification);
        } catch (ApolloDatabaseStatusNotFoundForRunIdException ex) {
            SoftwareIdentification softwareId;
            try {
                softwareId = dbUtils.getLastServiceToBeCalledForRun(runIdentification);
            } catch (ApolloDatabaseException ex1) {
                String message = ex1.getMessage();
                return getErrorMethodCallStatus(message);
            }
            if (softwareId.getSoftwareType() == ApolloSoftwareTypeEnum.SIMULATOR) {
                status.setStatus(MethodCallStatusEnum.CALLED_SIMULATOR);
                status.setMessage("The run was submitted to the simulator.");
            } else if (softwareId.getSoftwareType() == ApolloSoftwareTypeEnum.VISUALIZER) {
                status.setStatus(MethodCallStatusEnum.CALLED_VISUALIZER);
                status.setMessage("The run was submitted to the visualizer.");
            } else if (softwareId.getSoftwareType() == ApolloSoftwareTypeEnum.TRANSLATOR) {
                status.setStatus(MethodCallStatusEnum.CALLED_TRANSLATOR);
                status.setMessage("The run was submitted to the translator.");
            } else {
                return getErrorMethodCallStatus("Unrecognized software type");
            }
        } catch (ApolloDatabaseException ex) {
            return getErrorMethodCallStatus(ex.getMessage());
        }

        return status;
//
//        {
//            // long runIdAsLong =
//            // Long.parseLong(runAndSoftwareIdentification.getRunId());
//            if (ApolloServiceErrorHandler.checkErrorFileExists(runIdentification.intValue())) {
//
//                MethodCallStatus status = new MethodCallStatus();
//                status.setStatus(MethodCallStatusEnum.FAILED);
//                status.setMessage(ApolloServiceErrorHandler.readErrorFromErrorFile(runIdentification.intValue()));
//                return status;
//            }
//        }
//
//      
//        SoftwareIdentification softwareId;
//        try {
//            softwareId = dbUtils.getLastServiceToBeCalledForRun(runIdentification);
//        } catch (ApolloDatabaseException ex) {
//            String message = ex.getMessage();
//            return getErrorMethodCallStatus(message);
//        }
//
//        URL url;
//        try {
//            url = new URL(dbUtils.getUrlForSoftwareIdentification(softwareId));
//        } catch (SQLException ex) {
//            String message = "SQLException attempting to get URL for software identification for runId "
//                    + runIdentification + ": " + ex.getMessage();
//            return getErrorMethodCallStatus(message);
//        } catch (ApolloDatabaseKeyNotFoundException ex) {
//            String message = "Apollo database key not found attempting to get URL for software identification for runId "
//                    + runIdentification + ": " + ex.getMessage();
//            return getErrorMethodCallStatus(message);
//        } catch (ClassNotFoundException ex) {
//            String message = "ClassNotFoundException attempting to get URL for software identification for runId "
//                    + runIdentification + ": " + ex.getMessage();
//            return getErrorMethodCallStatus(message);
//        } catch (MalformedURLException ex) {
//            String message = "MalformedURLException attempting to get URL for software identification for runId "
//                    + runIdentification + ": " + ex.getMessage();
//            return getErrorMethodCallStatus(message);
//        }
//
//        MethodCallStatus status;
//        // get the webservice WSDL URL for supplied
//        if (softwareId.getSoftwareType() == ApolloSoftwareTypeEnum.SIMULATOR) {
//            SimulatorServiceEI port = new SimulatorServiceV201(url).getSimulatorServiceEndpoint();
//            status = port.getRunStatus(runIdentification);
//            if (status.getStatus().equals(MethodCallStatusEnum.UNKNOWN_RUNID)) {
//                
//                
//                status.setStatus(MethodCallStatusEnum.CALLED_SIMULATOR);
//                status.setMessage("The run was submitted to the simulator.");
//            }
//        } else if (softwareId.getSoftwareType() == ApolloSoftwareTypeEnum.VISUALIZER) {
//
//            VisualizerServiceEI port = new VisualizerServiceV201(url).getVisualizerServiceEndpoint();
//            status = port.getRunStatus(runIdentification);
//            if (status.getStatus().equals(MethodCallStatusEnum.UNKNOWN_RUNID)) {
//                status.setStatus(MethodCallStatusEnum.CALLED_VISUALIZER);
//                status.setMessage("The run was submitted to the visualizer.");
//            }
//        } else if (softwareId.getSoftwareType() == ApolloSoftwareTypeEnum.TRANSLATOR) {
//            TranslatorServiceEI port = new TranslatorServiceV201(url).getTranslatorServiceEndpoint();
//            status = port.getRunStatus(runIdentification);
//
//            if (status.getStatus().equals(MethodCallStatusEnum.UNKNOWN_RUNID)) {
//                status.setStatus(MethodCallStatusEnum.CALLED_TRANSLATOR);
//                status.setMessage("The run was submitted to the translator.");
//            }
//        } else {
//            return getErrorMethodCallStatus("Unrecognized software type");
//        }
//
//        return status;

    }
}

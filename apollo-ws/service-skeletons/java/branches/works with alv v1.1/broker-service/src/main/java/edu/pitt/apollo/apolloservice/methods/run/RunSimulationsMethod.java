package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.types.v2_1_0.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_1_0.RunSimulationsResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:39:59 PM
 * Class: RunSimulationsMethod
 * IDE: NetBeans 6.9.1
 */
public class RunSimulationsMethod {

    public static RunSimulationsResult runSimulations(RunSimulationsMessage runSimulationsMessage) {
 //       RunSimulationsResult bsr = new RunSimulationsResult();

        // this method needs to be updated
        
//        String runId;
//        try {
//            URL url = null;
//            try {
//                // get the webservice WSDL URL for supplied
//                // SimulatorIdentification
//                url = RegistrationUtils.getUrlForSoftwareIdentification(runSimulationsMessage.getSoftwareIdentification());
//                if (url == null) {
//                    runId = Long.toString(ApolloServiceErrorHandler.writeErrorWithErrorId("Service not registered."));
//                    bsr.setRunId(runId);
//                    return bsr;
//                }
//            } catch (IOException e) {
//                runId = RunUtils.getErrorRunId();
//                RunUtils.reportError(runId, "Error reading registry.");
//                bsr.setRunId(runId);
//                return bsr;
//            }
//            // run the simulator
//
//            SimulatorServiceEI port = new SimulatorServiceV202(url).getSimulatorServiceEndpoint();
//
//            // disable chunking for ZSI
//            Client client = ClientProxy.getClient(port);
//            HTTPConduit http = (HTTPConduit) client.getConduit();
//            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//            httpClientPolicy.setConnectionTimeout(36000);
//            httpClientPolicy.setAllowChunking(false);
//            http.setClient(httpClientPolicy);
//
//            return port.runSimulations(runSimulationsMessage);
//        } catch (Exception e) {
//            runId = RunUtils.getErrorRunId();
//            RunUtils.reportError(runId,
//                    "Problem with SimulatorService:" + e.getMessage());
//            bsr.setRunId(runId);
//            return bsr;
//        }
        
        return null;
    }
}

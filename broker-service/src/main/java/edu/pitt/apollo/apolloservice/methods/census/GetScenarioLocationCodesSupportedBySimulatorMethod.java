package edu.pitt.apollo.apolloservice.methods.census;

import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_2.GetScenarioLocationCodesSupportedBySimulatorResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 2:03:39 PM
 * Class: GetScenarioLocationCodesSupportedBySimulatorMethod
 * IDE: NetBeans 6.9.1
 */
public class GetScenarioLocationCodesSupportedBySimulatorMethod extends PopulationAndEnvironmentCensusMethod {

    public static GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator(
            SoftwareIdentification simulatorIdentification) {
        GetScenarioLocationCodesSupportedBySimulatorResult res = getScenarioLocationCodesSupportedBySimulator();
        MethodCallStatus status = new MethodCallStatus();
        status.setStatus(MethodCallStatusEnum.COMPLETED);
        status.setMessage("Returned " + res.getLocationCodes().size()
                + " items.");
        res.setMethodCallStatus(status);
        return res;

    }
}

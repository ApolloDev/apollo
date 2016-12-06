
package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v4_0_1.*;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author nem41
 */
public interface RunManagementInterface {

	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException;

	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException;

	public InsertRunResult insertRun(RunMessage message, Authentication authentication) throws RunManagementException;

	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException;

	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException;

	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException;

	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException;

	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException;

	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException;

}

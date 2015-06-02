
package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author nem41
 */
public interface RunManagementInterface {

	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws DataServiceException;

	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws DataServiceException;

	public BigInteger insertRun(Object message, Authentication authentication) throws DataServiceException;

	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws DataServiceException;

	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException;

	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws DataServiceException;

	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws DataServiceException;

	public void removeRunData(BigInteger runId, Authentication authentication) throws DataServiceException;

	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws DataServiceException;

}

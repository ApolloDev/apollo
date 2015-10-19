package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.types.v3_0_2.SoftwareIdentification;


/**
 *
 * @author nem41
 */
public interface UserManagementInterface {

	public void addRole(SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean allowPrivilegedRequest,
			String roleDescription, Authentication authentication) throws DataServiceException;

	public void deleteUser(String username, Authentication authentication) throws DataServiceException;

	public void addUserRole(String username, String userPasssword, SoftwareIdentification softwareIdentification,
			boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DataServiceException;

	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DataServiceException;

	public void authenticateUser(Authentication authentication) throws DataServiceException;

	public void authorizeUser(Authentication authentication,
			SoftwareIdentification softwareIdentification,
			boolean requestToRunSoftware) throws DataServiceException;
}

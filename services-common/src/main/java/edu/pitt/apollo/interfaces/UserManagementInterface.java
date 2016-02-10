package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;


/**
 *
 * @author nem41
 */
public interface UserManagementInterface {

	public void addRole(SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean allowPrivilegedRequest,
			String roleDescription, Authentication authentication) throws DatastoreException;

	public void deleteUser(String username, Authentication authentication) throws DatastoreException;

	public void addUserRole(String username, String userPasssword, SoftwareIdentification softwareIdentification,
			boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DatastoreException;

	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DatastoreException;

	public void authenticateUser(Authentication authentication) throws DatastoreException;

	public void authorizeUser(Authentication authentication,
			SoftwareIdentification softwareIdentification,
			boolean requestToRunSoftware) throws DatastoreException;
}

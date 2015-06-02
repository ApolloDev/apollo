
package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 *
 * @author nem41
 */
public interface UserManagementInterface {
	
	
	public void addUserRole(SoftwareIdentification softwareIdentification,
			boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DataServiceException;
	
	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DataServiceException;
}

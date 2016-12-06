
package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;

/**
 *
 * @author nem41
 */
public interface ContentManagementInterface {

	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException;
	
}

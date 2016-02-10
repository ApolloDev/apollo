
package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.FileAndURLDescription;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import java.math.BigInteger;
import java.util.Map;

/**
 *
 * @author nem41
 */
public interface ContentManagementInterface {
	
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware,
			SoftwareIdentification destinationSoftware, String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DatastoreException;

	public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DatastoreException;

	public Map<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DatastoreException;

	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DatastoreException;

	public String getContentForContentId(BigInteger urlId, Authentication authentication) throws DatastoreException;

	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException;
	
}

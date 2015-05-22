package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.runmanagerservice.exception.RunManagerServiceException;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessorForRunningDataServiceRequests extends DataServiceAccessor {

	private GetAllOutputFilesURLAsZipMessage getAllOutputFilesURLAsZipMessage;
	private GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage;
	private GetOutputFilesURLsMessage getOutputFilesURLsMessage;

	public DataServiceAccessorForRunningDataServiceRequests(GetAllOutputFilesURLAsZipMessage getAllOutputFilesURLAsZipMessage) {
		this.getAllOutputFilesURLAsZipMessage = getAllOutputFilesURLAsZipMessage;
	}

	public DataServiceAccessorForRunningDataServiceRequests(GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage) {
		this.getOutputFilesURLAsZipMessage = getOutputFilesURLAsZipMessage;
	}

	public DataServiceAccessorForRunningDataServiceRequests(GetOutputFilesURLsMessage getOutputFilesURLsMessage) {
		this.getOutputFilesURLsMessage = getOutputFilesURLsMessage;
	}

	@Override
	public BigInteger[] insertRunIntoDatabase(BigInteger memberOfSimulationGroupIdOrNull) throws DataServiceException, RunManagerServiceException {
		if (getAllOutputFilesURLAsZipMessage != null) {
			return connector.addDataServiceRun(getAllOutputFilesURLAsZipMessage);
		} else if (getOutputFilesURLAsZipMessage != null) {
			return connector.addDataServiceRun(getOutputFilesURLAsZipMessage);
		} else if (getOutputFilesURLsMessage != null) {
			return connector.addDataServiceRun(getOutputFilesURLsMessage);
		} else {
			throw new RunManagerServiceException("The message object for the data service run was null");
		}
	}
}

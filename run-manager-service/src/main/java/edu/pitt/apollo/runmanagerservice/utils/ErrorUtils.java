package edu.pitt.apollo.runmanagerservice.utils;

import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class ErrorUtils {

	static Logger logger = LoggerFactory.getLogger(ErrorUtils.class);

	public static synchronized void reportError(BigInteger runId, String message, Authentication authentication) {
		try {
			DatastoreAccessor dataServiceAccessor = new DatastoreAccessor();

			dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, message, authentication);
		} catch (RunManagementException e) {
			logger.error("Error updating ERROR status of run to '" + message + "'.  Error was:" + e.getMessage());
		}

	}
}

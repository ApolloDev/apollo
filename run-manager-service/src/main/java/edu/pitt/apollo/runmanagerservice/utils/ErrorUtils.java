package edu.pitt.apollo.runmanagerservice.utils;

import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessImpl;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import javafx.scene.chart.XYChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class ErrorUtils {
    static Logger logger = LoggerFactory.getLogger(ErrorUtils.class);
    public static synchronized void reportError(BigInteger runId,  String message, Authentication authentication) {
        DatastoreAccessImpl dataServiceAccessor = new DatastoreAccessImpl();
        try {
            dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, message, authentication);
        } catch (RunManagementException e) {
            logger.error("Error updating ERROR status of run to '" + message + "'.  Error was:" + e.getMessage());
        }

    }
}

package edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors;

import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessorFactory {
    static public DataServiceAccessor getDataServiceAccessor(Object message, Authentication authentication) throws UnrecognizedMessageTypeException {
        return null;
    }
}

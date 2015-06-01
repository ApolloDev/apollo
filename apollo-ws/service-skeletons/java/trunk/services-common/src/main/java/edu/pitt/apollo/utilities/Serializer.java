package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.SerializationException;

/**
 *
 * @author nem41
 */
public interface Serializer {

	public static final String APOLLO_NAMESPACE = "http://types.apollo.pitt.edu/v3_0_0";
	public static final String APOLLO_NAMESPACE_TNS_PREFIX = "tns";

	public String serializeObject(Object obj, String namespace, String prefix) throws SerializationException;

}

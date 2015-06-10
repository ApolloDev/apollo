package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.SerializationException;

/**
 *
 * @author nem41
 */
public interface SerializerInterface {

	public String serializeObject(Object obj) throws SerializationException;

}

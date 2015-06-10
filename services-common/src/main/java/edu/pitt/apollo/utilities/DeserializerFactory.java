
package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;

/**
 *
 * @author nem41
 */
public class DeserializerFactory {
	
	public static Deserializer getDeserializer(SerializationFormat format) throws UnsupportedSerializationFormatException {
		switch (format) {
			case XML:
				return new XMLDeserializer();
			default:
				throw new UnsupportedSerializationFormatException("The serialization format " + format + " is not currently supported");
		}
	}
	
}

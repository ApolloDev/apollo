package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;

/**
 *
 * @author nem41
 */
public class SerializerFactory {

	public static Serializer getSerializer(SerializationFormat format, String nameSpace, String prefix) throws UnsupportedSerializationFormatException {

		switch (format) {
			case XML:
				return new XMLSerializer(nameSpace, prefix);
			default:
				throw new UnsupportedSerializationFormatException("The serialization format " + format + " is not currently supported");
		}

	}

}

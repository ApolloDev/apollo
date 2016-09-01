package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;

/**
 * @author nem41
 */
public class SerializerFactory {

    public static Serializer getSerializer(SerializationFormat format) throws UnsupportedSerializationFormatException {

        switch (format) {
            case XML:
                return new XMLSerializer();
            case JSON:
                return new JsonSerializer();
            default:
                throw new UnsupportedSerializationFormatException("The serialization format " + format + " is not currently supported");
        }

    }

}

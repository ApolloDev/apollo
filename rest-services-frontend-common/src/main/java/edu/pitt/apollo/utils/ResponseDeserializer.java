package edu.pitt.apollo.utils;

import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v3_0_2.*;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.XMLDeserializer;

/**
 * Created by jdl50 on 8/7/15.
 */
public class ResponseDeserializer {

    public static Object deserialize(String text) throws DeserializationException, UnsupportedSerializationFormatException {
        Request requestMessageObject = new XMLDeserializer().getObjectFromMessage(text, Request.class);
        RequestMeta meta = requestMessageObject.getRequestMeta();
        ObjectSerializationInformation config = meta.getRequestBodySerializationInformation();

        SerializationFormat format = config.getFormat();
        Deserializer deserializer = DeserializerFactory.getDeserializer(format);

        String className = config.getClassName();
        String classNamespace = config.getClassNameSpace();

        return deserializer.getObjectFromMessage(requestMessageObject.getRequestBody(), className, classNamespace);
    }
}

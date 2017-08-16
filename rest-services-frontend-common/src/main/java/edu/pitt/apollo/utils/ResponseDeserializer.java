package edu.pitt.apollo.utils;


import edu.pitt.apollo.services_common.v4_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_2.Request;
import edu.pitt.apollo.services_common.v4_0_2.RequestMeta;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.isg.objectserializer.Deserializer;
import edu.pitt.isg.objectserializer.DeserializerFactory;
import edu.pitt.isg.objectserializer.XMLDeserializer;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;

/**
 * Created by jdl50 on 8/7/15.
 */
public class ResponseDeserializer {

    public static Object deserialize(String text) throws DeserializationException, UnsupportedSerializationFormatException {
        Request requestMessageObject = new XMLDeserializer().getObjectFromMessage(text, Request.class);
        RequestMeta meta = requestMessageObject.getRequestMeta();
        ObjectSerializationInformation config = meta.getRequestBodySerializationInformation();

        SerializationFormat format = config.getFormat();
        Deserializer deserializer = null;
        switch (format) {
            case JSON:
                deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.JSON);
                break;
            case XML:
                deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.XML);
                break;
        }


        String className = config.getClassName();
        String classNamespace = config.getClassNameSpace();

        return deserializer.getObjectFromMessage(requestMessageObject.getRequestBody(), className, classNamespace);
    }
}

package edu.pitt.apollo.utilities;

import edu.pitt.apollo.services_common.v4_0_2.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.isg.objectserializer.Serializer;

/**
 * Created by jdl50 on 12/5/16.
 */
public class SerializationUtils {

    protected final SerializationFormat serializationFormat;

    public SerializationUtils(SerializationFormat serializationFormat) {
        this.serializationFormat = serializationFormat;
    }


    public ObjectSerializationInformation getObjectSerializationInformation(Object object) {
        ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
        objectSerializationInformation.setClassName(object.getClass().getSimpleName());
        objectSerializationInformation.setClassNameSpace(Serializer.convertNamespaceFromJavaToXSD(object.getClass().getPackage().getName()));
        objectSerializationInformation.setFormat(serializationFormat);
        return objectSerializationInformation;
    }


}

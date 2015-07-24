package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.utilities.ApolloClassList;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jdl50 on 5/15/15.
 */
public class JsonUtils {

    static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static Map<Class, JAXBMarshaller> marshallerMap = new HashMap<Class, JAXBMarshaller>();
    

    public final Object getObjectFromJson(String json, Class clazz) throws JsonUtilsException {
        InputStream jsonInputStream = new ByteArrayInputStream(json.getBytes());
        return getObjectFromJson(jsonInputStream, clazz);
    }

    public final Object getObjectFromJson(InputStream jsonInputStream, Class clazz) throws JsonUtilsException {
        Map<String, Object> jaxbProperties = new HashMap<String, Object>(2);
        jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        jaxbProperties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
        JAXBContext jc;
        try {
            jc = (JAXBContext) JAXBContext.newInstance(ApolloClassList.classList, jaxbProperties);
            JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource ss = new StreamSource(jsonInputStream);
            return unmarshaller.unmarshal(ss, clazz).getValue();
        } catch (JAXBException ex) {
            throw new JsonUtilsException("JAXBException creating object from JSON: " + ex.getMessage());
        }
    }

    public synchronized final ByteArrayOutputStream getJsonBytes(Object obj) throws JAXBException {
        Class clazz = obj.getClass();
        JAXBMarshaller marshaller = null;
//        if (marshallerMap.containsKey(clazz)) {
//            marshaller = marshallerMap.get(clazz);
//        } else {
            Map<String, Object> jaxbProperties = new HashMap<String, Object>(2);
            jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
//		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
            JAXBContext jc = (JAXBContext) JAXBContext.newInstance(ApolloClassList.classList,
                    jaxbProperties);
            marshaller = jc.createMarshaller();
            marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshallerMap.put(clazz, marshaller);
//        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        marshaller.marshal(obj, baos);
        return baos;
    }

    public String getJSONString(Object obj) {
        try {

            return getJsonBytes(obj).toString();

        } catch (Exception e) {
            logger.error("Exception encoding {} to JSON.  Error message was: {} ", obj, e.getMessage());
            return null;
        }

    }
}

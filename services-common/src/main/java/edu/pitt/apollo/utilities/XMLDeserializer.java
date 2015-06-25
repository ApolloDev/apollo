package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.DeserializationException;
import javax.xml.bind.JAXB;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by dcs27 on 5/29/15.
 */
public class XMLDeserializer extends Deserializer {

	@Override
	public <T> T getObjectFromMessage(String xml, Class<T> classToParseTo) throws DeserializationException {
		InputStream xmlAsStream = new ByteArrayInputStream(xml.getBytes());
		T objectToReturn = JAXB.unmarshal(xmlAsStream, classToParseTo);
		return objectToReturn;
	}

	@Override
	public Object getObjectFromMessage(String xml, String className, String classNamespace) throws DeserializationException {
		
		String javaPackage = convertNamespaceFromXSDToJava(classNamespace);
		String classWithPackage = javaPackage + "." + className;
		
		try {
			Class<?> classToParseTo = Class.forName(classWithPackage);
			return getObjectFromMessage(xml, classToParseTo);
		} catch (ClassNotFoundException e) {
			throw new DeserializationException("Exception deserializing object. Specified class \"" + className + "\" could not be found");
		}
	}
}

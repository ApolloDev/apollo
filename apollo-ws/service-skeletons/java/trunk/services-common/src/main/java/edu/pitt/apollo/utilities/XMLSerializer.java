package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.SerializationException;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

/**
 *
 * @author nem41
 */
public class XMLSerializer implements Serializer {

	@Override
	public String serializeObject(Object obj, String namespace, String prefix) throws SerializationException {
		String xml = "";
		try {

			JAXBContext context = JAXBContext.newInstance(Object.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			final StringWriter stringWriter = new StringWriter();

			marshaller.marshal(new JAXBElement(
					new QName(namespace, obj.getClass().getSimpleName(), prefix), Object.class, obj), stringWriter);

			xml = stringWriter.toString();
		} catch (JAXBException e) {
			throw new SerializationException("JAXBException: " + e.getMessage());
		}
		return xml;
	}

}

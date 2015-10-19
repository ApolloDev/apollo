package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
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
public class XMLSerializer extends Serializer {

	public XMLSerializer() {
		super(SerializationFormat.XML);
	}

	@Override
	public String serializeObject(Object obj) throws SerializationException {
		
		String xml = "";
		try {
			
			JAXBContext context = JAXBContext.newInstance(ApolloClassList.classList);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			final StringWriter stringWriter = new StringWriter();

			String namespace = convertNamespaceFromJavaToXSD(obj.getClass().getPackage().getName());
			marshaller.marshal(new JAXBElement(
					new QName(namespace, obj.getClass().getSimpleName(), Serializer.APOLLO_NAMESPACE_TNS_PREFIX), obj.getClass(), obj), stringWriter);

			xml = stringWriter.toString();
		} catch (JAXBException e) {
			throw new SerializationException("JAXBException: " + e.getMessage());
		}
		return xml;
	}

}

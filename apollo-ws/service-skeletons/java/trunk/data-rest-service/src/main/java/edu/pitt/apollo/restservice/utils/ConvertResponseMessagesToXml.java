package edu.pitt.apollo.restservice.utils;

import edu.pitt.apollo.restservice.rest.responsemessage.*;
import edu.pitt.apollo.restservice.rest.responsemessage.ApolloXsdVersionResponseMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;

/**
 * Created by dcs27 on 5/6/15.
 */
public class ConvertResponseMessagesToXml {
    public static String convertAddLibraryResponseMessageToXmlJaxb(AddLibraryItemResponseMessage obj) {
        String xml = "";
        try {

            JAXBContext context = JAXBContext.newInstance(AddLibraryItemResponseMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("http://types.apollo.pitt.edu/v3_0_0", "AddLibraryItemResponseMessage", "tns"), AddLibraryItemResponseMessage.class, obj), stringWriter);

            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String convertApolloXsdVersionResponseMessageToXmlJaxb(ApolloXsdVersionResponseMessage obj) {
        String xml = "";
        try {

            JAXBContext context = JAXBContext.newInstance(ApolloXsdVersionResponseMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("http://types.apollo.pitt.edu/v3_0_0", "ApolloXsdVersionResponseMessage", "tns"), ApolloXsdVersionResponseMessage.class, obj), stringWriter);

            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String convertGetSoftwareIdentificationForRunMessageToXmlJaxb(GetSoftwareIdentificationForRunRestMessage obj) {
        String xml = "";
        try {

            JAXBContext context = JAXBContext.newInstance(GetSoftwareIdentificationForRunRestMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("local", "GetSoftwareIdentificationForRunRestMessage", "tns"), GetSoftwareIdentificationForRunRestMessage.class, obj), stringWriter);

            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String UpdateStatusOfRunRestMessage(UpdateStatusOfRunRestMessage returnMessage) {
        String xml = "";
        try {

            JAXBContext context = JAXBContext.newInstance(UpdateStatusOfRunRestMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("local", "UpdateStatusOfRunRestMessage", "tns"), UpdateStatusOfRunRestMessage.class, returnMessage), stringWriter);

            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String convertStatusResponseMessagetoXmlJaxb(StatusOnlyResponseMessage returnMessage) {
        String xml = "";
        try {

            JAXBContext context = JAXBContext.newInstance(StatusOnlyResponseMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("local", "StatusOnlyResponseMessage", "tns"), StatusOnlyResponseMessage.class, returnMessage), stringWriter);

            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String convertGetRunsStatusRestMessageToXmlJaxb(GetRunStatusRestMessage returnMessage) {
        String xml = "";
        try {

            JAXBContext context = JAXBContext.newInstance(GetRunStatusRestMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("local", "GetRunStatusRestMessage", "tns"), GetRunStatusRestMessage.class, returnMessage), stringWriter);

            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public static String convertGetIdentificationKeyRestMessageXmlJaxb(GetIdentificationKeyRestMessage returnMessage) {
        String xml = "";
        try {

            JAXBContext context = JAXBContext.newInstance(GetIdentificationKeyRestMessage.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("local", "GetIdentificationKeyRestMessage", "tns"), GetIdentificationKeyRestMessage.class, returnMessage), stringWriter);

            xml = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }
}

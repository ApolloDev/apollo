package edu.pitt.apollo.restservice.utils;

import edu.pitt.apollo.restservice.exceptions.ParsingFromXmlToObjectException;
import edu.pitt.apollo.restservice.types.AddRoleInformation;
import edu.pitt.apollo.restservice.types.UserAndRoleInformation;
import edu.pitt.apollo.restservice.types.UserInformation;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.types.v3_0_0.Epidemic;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseScenario;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

/**
 * Created by dcs27 on 5/5/15.
 */
public class ParseXmlToAndFromObject {

    /*--First called by controller--*/

    public static SoftwareIdentification getXmlFromEpidemic(String siXml)
    {
        SoftwareIdentification siConvertedFromXml = convertFromXmlToSoftwareIdentification(siXml);
        return siConvertedFromXml;
    }

    /*--End First called by controller--*/

    public static SoftwareIdentification convertFromXmlToSoftwareIdentification(String xml) {
        SoftwareIdentification si = null;
        InputStream siXmlInputStream = new ByteArrayInputStream(xml.getBytes());
        try {

    /*--Turn into Java--*/
            si = JAXB.unmarshal(siXmlInputStream, SoftwareIdentification.class);

        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }
        return si;
    }

    public static UserInformation convertFromXmlToUserInformation(String xml) throws ParsingFromXmlToObjectException
    {
        UserInformation userInformation;
        InputStream userInfoInputStream = new ByteArrayInputStream(xml.getBytes());

        try{
            userInformation = JAXB.unmarshal(userInfoInputStream,UserInformation.class);
            return userInformation;
        }
        catch(Exception e)
        {
            throw new ParsingFromXmlToObjectException(e.getMessage());
        }
    }

    public static String convertFromUserAndRoleInformationToXml(UserAndRoleInformation usr)
    {
        String xml ="";
        try {
            JAXBContext context = JAXBContext.newInstance(UserAndRoleInformation.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("local", "UserAndRoleInformation", "tns"), UserAndRoleInformation.class, usr), stringWriter);

            xml = stringWriter.toString();
        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xml;
    }
    public static UserAndRoleInformation convertFromXmlToUserAndRoleInformationXml(String xml) throws ParsingFromXmlToObjectException {
        UserAndRoleInformation userAndRoleInformation;
        InputStream userAndRoleInformationInputStream = new ByteArrayInputStream(xml.getBytes());
        try{
            userAndRoleInformation = JAXB.unmarshal(userAndRoleInformationInputStream, UserAndRoleInformation.class);
            return userAndRoleInformation;
        }
        catch(Exception e)
        {
            ParsingFromXmlToObjectException ex = new ParsingFromXmlToObjectException(e.getMessage());
            ex.setErrorMessage(e.getMessage());
            throw ex;
        }

    }
    public static AddRoleInformation convertFromXmlToRoleInformation(String xml) throws ParsingFromXmlToObjectException {
        AddRoleInformation roleInformation;
        InputStream roleInformationInputStream = new ByteArrayInputStream(xml.getBytes());
        try{
            roleInformation = JAXB.unmarshal(roleInformationInputStream,AddRoleInformation.class);
            return roleInformation;
        }
        catch(Exception e)
        {
            ParsingFromXmlToObjectException ex = new ParsingFromXmlToObjectException(e.getMessage());
            ex.setErrorMessage(e.getMessage());
            throw ex;
        }

    }
}

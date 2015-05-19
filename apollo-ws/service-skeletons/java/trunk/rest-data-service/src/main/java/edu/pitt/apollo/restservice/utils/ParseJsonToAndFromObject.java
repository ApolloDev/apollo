package edu.pitt.apollo.restservice.utils;

import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_0.Epidemic;
import edu.pitt.apollo.types.v3_0_0.InfectiousDiseaseScenario;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcs27 on 5/5/15.
 */
public class ParseJsonToAndFromObject {

    /*--First called by controller--*/
    public static String getJsonFromIds(InfectiousDiseaseScenario ids) {
        String convertEpidemicToJsonString = convertFromInfectiousDiseaseScenarioToJson(ids);
        return convertEpidemicToJsonString;
    }

    public static String getJsonFromEpidemic(Epidemic epidemic) {
        String convertEpidemicToJsonString = convertFromEpidemicToJson(epidemic);
        return convertEpidemicToJsonString;
    }
    /*--End First called by controller--*/

    public static String convertFromEpidemicToJson(Epidemic epidemic) {
        String jsonOfEpidemic = "";
        try {

            Map<String, Object> properties = new HashMap<String, Object>(2);
            properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
            properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

            JAXBContext jc = JAXBContext.newInstance(new Class[]{
                            Epidemic.class, ObjectFactory.class},
                    properties);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("http://types.apollo.pitt.edu/v3_0_0", "Epidemic", "tns"), Epidemic.class, epidemic), stringWriter);

            jsonOfEpidemic = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return jsonOfEpidemic;


    }


    public static Epidemic convertFromJsonToEpidemic(String json) {
        Epidemic epidemic = null;
        InputStream idsJsonInputStream = new ByteArrayInputStream(json.getBytes());
        try {

    /*--Turn into Java--*/
            // JAXBContext jc = JAXBContext.newInstance(InfectiousDiseaseScenario.class);
            //Unmarshaller unmarshaller = jc.createUnmarshaller();
            Map<String, Object> properties = new HashMap<String, Object>(2);
            properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
            properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

            JAXBContext jc = JAXBContext.newInstance(new Class[]{
                            Epidemic.class, ObjectFactory.class},
                    properties);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource jsonAsStreamSource = new StreamSource(new ByteArrayInputStream(
                    json.getBytes()));
            epidemic = (Epidemic) unmarshaller
                    .unmarshal(jsonAsStreamSource, Epidemic.class).getValue();


        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }
        return epidemic;
    }

    public static InfectiousDiseaseScenario convertFromJsonToIds(String json) {
        InfectiousDiseaseScenario ids = null;
        InputStream idsJsonInputStream = new ByteArrayInputStream(json.getBytes());
        try {

    /*--Turn into Java--*/
            // JAXBContext jc = JAXBContext.newInstance(InfectiousDiseaseScenario.class);
            //Unmarshaller unmarshaller = jc.createUnmarshaller();
            Map<String, Object> properties = new HashMap<String, Object>(2);
            properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
            properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

            JAXBContext jc = JAXBContext.newInstance(new Class[]{
                            InfectiousDiseaseScenario.class, ObjectFactory.class},
                    properties);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            StreamSource jsonAsStreamSource = new StreamSource(new ByteArrayInputStream(
                    json.getBytes()));
            ids = (InfectiousDiseaseScenario) unmarshaller
                    .unmarshal(jsonAsStreamSource, InfectiousDiseaseScenario.class).getValue();

//            StreamSource jsonAsStreamSource = new StreamSource( new StringReader(json));
//
//            ids = unmarshaller.unmarshal(jsonAsStreamSource,InfectiousDiseaseScenario.class).getValue();

//            System.out.println(ids.getLocation().getApolloLocationCode());

            // ids = JAXB.unmarshal(idsJsonInputStream, InfectiousDiseaseScenario.class);

        } catch (
                Exception e
                )

        {
            e.printStackTrace();
        }
        return ids;
    }


    public static String convertFromInfectiousDiseaseScenarioToJson(InfectiousDiseaseScenario ids) {
        String jsonOfIds = "";
        try {

            Map<String, Object> properties = new HashMap<String, Object>(2);
            properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
            properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

            JAXBContext jc = JAXBContext.newInstance(new Class[]{
                            InfectiousDiseaseScenario.class, ObjectFactory.class},
                    properties);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            final StringWriter stringWriter = new StringWriter();

            marshaller.marshal(new JAXBElement(
                    new QName("http://types.apollo.pitt.edu/v3_0_0", "InfectiousDiseaseScenario", "tns"), InfectiousDiseaseScenario.class, ids), stringWriter);

            jsonOfIds = stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return jsonOfIds;


    }
}

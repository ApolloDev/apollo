package edu.pitt.apollo.apollotranslator.file;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by nem41 on 7/21/15.
 */
public class XPathTest {

    public static void main(String[] args) throws IOException, JDOMException {

        String xmlString = "<ModelOptions>\n" +
                "      <option name=\"op1\" value=\"false\" />\n" +
                "      <option name=\"op2\" value=\"false\" />\n" +
                "      <option name=\"op3\" value=\"false\" />\n" +
                "      <option name=\"op4\" value=\"false\" />\n" +
                "      <option name=\"op5\" value=\"false\" />\n" +
                "    </ModelOptions>";


        SAXBuilder builder = new SAXBuilder();
        String exampleXML = xmlString;
        InputStream stream = new ByteArrayInputStream(exampleXML.getBytes("UTF-8"));
        Document anotherDocument = builder.build(stream);

        SAXBuilder jdomBuilder = new SAXBuilder();
        Document jdomDocument = jdomBuilder.build("/Users/nem41/Documents/java_projects/apollo_projects/apollo-translator/src/test/resources/openmalaria/initial_base_config.xml");

        XPathFactory xpf = XPathFactory.instance();
        XPathExpression<Element> expr = xpf.compile("/scenario/entomology/@scaledAnnualEIR", Filters.element());
        List<Element> elements = expr.evaluate(jdomDocument);
        System.out.println(elements.size());
        for (Element element : elements) {
//            element.setContent(anotherDocument.getRootElement().clone());
            element.setAttribute("scaledAnnualEIR", "500");
            //System.out.println(element.getValue());
        }

        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());
        xmlOutputter.output(jdomDocument, System.out);

    }

}

package edu.pitt.apollo.apollotranslator.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pitt.apollo.apollotranslator.ApolloLogger;
import edu.pitt.apollo.apollotranslator.types.translator.*;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import org.jdom2.*;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Oct 23, 2013 Time: 5:34:39 PM Class: XMLNativeFileCreator IDE: NetBeans 6.9.1
 */
public class XMLNativeFileCreator extends NativeFileCreator {

	private final String baseXMLFile;

	public XMLNativeFileCreator(XMLNativeFileCreatorInput xmlNativeFileWriterInput, List<SetterReturnObject> setterReturnObjects) {
		super(xmlNativeFileWriterInput, setterReturnObjects);
		this.baseXMLFile = xmlNativeFileWriterInput.getBaseXMLFile();
	}

	@Override
	public Map<String, ByteArrayOutputStream> createNativeFiles() throws ApolloTranslatorException, IOException {
		Map<String, ByteArrayOutputStream> map = new HashMap<>();
		SAXBuilder jdomBuilder = new SAXBuilder();
		Document jdomDocument;

		try {
			jdomDocument = jdomBuilder.build(new ByteArrayInputStream(baseXMLFile.getBytes(StandardCharsets.UTF_8)));
		} catch (JDOMException ex) {
			throw new ApolloTranslatorException("JDOMException reading base XML file: " + ex.getMessage());
		}

		String filePath = null;
		for (SetterReturnObject sro : setterReturnObjects) {

			XMLTranslationReturnObject tro = (XMLTranslationReturnObject) sro.getTranslationReturnObject();

			if (tro == null) {
                // note that for XML if the value from the JavaScript function is empty
				// we still want to print the native term. This allows for element attributes
				// which look like <element attr=""/>

                // we also want to print the element if NO native value is present
				// This allows for printing elements like <element/>
				continue;
			}

			if (sro.getConfigurationFile().length() == 0) {
				ApolloLogger.log(Level.INFO,
						AbstractTranslationInstructionsFileLoader.CONFIG_FILE_NAME_COLUMN_NAME
						+ " not specified");
				continue;
			}

			filePath = sro.getConfigurationFile();
			Map<String, XMLXPathValue> xpathsAndValues = tro.getXpathsAndValues();
			XMLTranslationInstruction.XMLNativeValueType xmlNativeValueType = tro.getXmlNativeValueType();

			List<Namespace> namespaceList = null;
			Map<String, String> namespaces = tro.getNamespaces();
			if (namespaces != null && namespaces.size() > 0) {
				namespaceList = new ArrayList<>();
				for (String id : namespaces.keySet()) {
					String namespace = namespaces.get(id);
					Namespace namespaceObj = Namespace.getNamespace(id, namespace);
					namespaceList.add(namespaceObj);
				}
			}

			for (String xpath : xpathsAndValues.keySet()) {

				XPathFactory xpf = XPathFactory.instance();
				XPathExpression<Element> expr;
				if (namespaceList != null) {
					expr = xpf.compile(xpath, Filters.element(), null, namespaceList);
				} else {
					expr = xpf.compile(xpath, Filters.element());
				}
				List<Element> elements = expr.evaluate(jdomDocument);

				if (xmlNativeValueType.equals(XMLTranslationInstruction.XMLNativeValueType.ATTRIBUTE)) {

					XMLAttributeValuePair valuePair = (XMLAttributeValuePair) xpathsAndValues.get(xpath);
					for (Element element : elements) {
						element.setAttribute(valuePair.getAttribute(), valuePair.getValue());
					}

				} else if (xmlNativeValueType.equals(XMLTranslationInstruction.XMLNativeValueType.SINGLE_VALUE)) {

					XMLStringValue value = (XMLStringValue) xpathsAndValues.get(xpath);
					for (Element element : elements) {
						element.setText(value.getValue());
					}

				} else if (xmlNativeValueType.equals(XMLTranslationInstruction.XMLNativeValueType.MULTIPLE_VALUES)) {
					XMLMultipleStringValues multipleValues = (XMLMultipleStringValues) xpathsAndValues.get(xpath);
					List<String> values = multipleValues.getValues();
					for (Element element : elements) {
						for (int i = 0; i < values.size(); i++) {
							Element newElement = new Element(multipleValues.getElementName());
							newElement.setText(values.get(i));
							if (i == 0) {
								element.setContent(newElement);
							} else {
								element.addContent(newElement);
							}
						}
					}
				} else if (xmlNativeValueType.equals(XMLTranslationInstruction.XMLNativeValueType.XML)) {

					XMLStringValue xmlStringValue = (XMLStringValue) xpathsAndValues.get(xpath);
					String xml = xmlStringValue.getValue();

					InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

					try {
						Document document = jdomBuilder.build(stream);
						// create copy of content
						List<Content> newContent = new ArrayList<>();
						List<Content> oldContent = document.getRootElement().getContent();
						for (int i = 0; i < oldContent.size(); i++) {
							newContent.add(oldContent.get(i).clone());
						}

						for (Element element : elements) {
							element.setContent(newContent);
						}
					} catch (JDOMException ex) {
						throw new ApolloTranslatorException("JDOMException reading XML from JavaScript: " + ex.getMessage());
					}

				} else if (xmlNativeValueType.equals(XMLTranslationInstruction.XMLNativeValueType.XML_REQUIRING_XPATH)) {

					XMLStringValue xmlStringValue = (XMLStringValue) xpathsAndValues.get(xpath);
					String xml = xmlStringValue.getValue();

					InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

					try {
						Document document = jdomBuilder.build(stream);

						List<Element> newElements = expr.evaluate(document);
						Element newElement = newElements.get(0).detach();

						// create copy of content
//						List<Content> newContent = new ArrayList<>();
//						List<Content> oldContent = document.getRootElement().getContent();
//						for (int i = 0; i < oldContent.size(); i++) {
//							newContent.add(oldContent.get(i).clone());
//						}

						for (Element element : elements) {
							Element parent = element.getParentElement();
							parent.removeContent(element);
							parent.addContent(newElement);
						}
					} catch (JDOMException ex) {
						throw new ApolloTranslatorException("JDOMException reading XML from JavaScript: " + ex.getMessage());
					}

				} else {
					throw new ApolloTranslatorException("unsupported xml native attribute type");
				}
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLOutputter xmlOutputter = new XMLOutputter();
		xmlOutputter.setFormat(Format.getPrettyFormat());
		xmlOutputter.output(jdomDocument, baos);
		map.put(filePath, baos);

		return map;
	}
}

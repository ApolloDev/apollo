package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.DeserializationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nem41
 */
public abstract class Deserializer {

	private static final Pattern xsdNAmespacePattern = Pattern.compile("http:\\/\\/(.*?)\\/(.*)\\/");

	public abstract <T> T getObjectFromMessage(String xml, Class<T> classToParseTo) throws DeserializationException;

	public abstract Object getObjectFromMessage(String xml, String className, String classNamespace) throws DeserializationException;

	protected String convertNamespaceFromXSDToJava(String xsdNamespace) throws DeserializationException {

		Matcher matcher = xsdNAmespacePattern.matcher(xsdNamespace);
		if (matcher.find()) {
			String packageName = matcher.group(1);
			String[] packageNameSplit = packageName.split(".");
			String javaNamespace = "";
			for (int i = packageNameSplit.length - 1; i >= 0; i--) {
				javaNamespace += packageNameSplit[i] + ".";
			}
			javaNamespace = javaNamespace.replace("-", "_");

			String version = matcher.group(2);
			javaNamespace += version;
			return javaNamespace;
		} else {
			throw new DeserializationException("Could not convert from XSD namespace to Java package. The namespace did not match the expected pattern.");
		}
	}

}

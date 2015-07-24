package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.service.libraryservice.v3_0_2.LibraryServiceEI;
import edu.pitt.apollo.service.libraryservice.v3_0_2.LibraryServiceV300;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 9, 2014 Time: 1:49:07 PM Class: LibraryMethod IDE: NetBeans 6.9.1
 */
public abstract class LibraryMethod {

	static Logger logger = LoggerFactory.getLogger(LibraryMethod.class);

//	private static final String LIBRARY_SERVICE_URL = "http://betaweb.rods.pitt.edu/library-service-war-3.0.0/services/libraryservice?wsdl"; // production
	private static final String LIBRARY_SERVICE_URL = "http://localhost:8080/library-service-war-3.0.0-SNAPSHOT/services/libraryservice?wsdl"; // testing

	protected static LibraryServiceEI getLibraryServicePort() {
		URL libraryServiceURL;
		try {
			libraryServiceURL = new URL(LIBRARY_SERVICE_URL);

			return new LibraryServiceV300(libraryServiceURL).getLibraryServiceEndpoint();

		} catch (MalformedURLException ex) {
			logger.error("MalformedURLException attempting to get Library port: " + ex.getMessage());
		}
		return null;
	}
}

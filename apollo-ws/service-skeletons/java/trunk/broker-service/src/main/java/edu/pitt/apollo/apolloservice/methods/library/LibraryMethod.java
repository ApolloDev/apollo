package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.service.libraryservice.v3_0_0.LibraryServiceEI;
import edu.pitt.apollo.service.libraryservice.v3_0_0.LibraryServiceV300;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:49:07 PM
 * Class: LibraryMethod
 * IDE: NetBeans 6.9.1
 */
public abstract class LibraryMethod {

    protected static LibraryServiceEI getLibraryServicePort() {
        URL libraryServiceURL;
        try {
            libraryServiceURL = new URL(
                    "http://localhost:8080/library-service-war-3.0.0-SNAPSHOT/services/libraryservice?wsdl");

            return new LibraryServiceV300(libraryServiceURL).getLibraryServiceEndpoint();

        } catch (MalformedURLException ex) {
            try {
                ApolloServiceErrorHandler.writeErrorWithErrorId("MalformedURLException attempting to get Library port: " 
                        + ex.getMessage());
            } catch (IOException e) {
                System.err.println("IOException writing error file: "
                        + e.getMessage());
            }
        }
        return null;
    }
}

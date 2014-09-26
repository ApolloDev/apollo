package edu.pitt.apollo.apolloservice.methods.library;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.service.libraryservice.v2_0_2.LibraryServiceEI;
import edu.pitt.apollo.service.libraryservice.v2_0_2.LibraryServiceV202;
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
                    "http://localhost:8080/libraryservice-2.0.2a.7-SNAPSHOT/services/libraryservice?wsdl");

            return new LibraryServiceV202(libraryServiceURL).getLibraryServiceEndpoint();

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

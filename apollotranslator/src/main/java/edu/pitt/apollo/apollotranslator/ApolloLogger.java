package edu.pitt.apollo.apollotranslator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 21, 2013
 * Time: 1:12:52 PM
 * Class: ApolloLogger
 * IDE: NetBeans 6.9.1
 */
public class ApolloLogger {

    private static final Logger LOGGER = Logger.getLogger(ApolloLogger.class.getName());
    private static boolean useLogging = false;

    private ApolloLogger() {
    }

    public static void initialize(boolean useLogging) {

        ApolloLogger.useLogging = useLogging;
    }

    public static void log(Level level, String message) {

        if (useLogging) {
            LOGGER.log(level, message);
        }
    }

    public static void useLogging(boolean useLogging) {

        ApolloLogger.useLogging = useLogging;
    }
}

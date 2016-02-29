package edu.pitt.apollo;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 7, 2014
 * Time: 1:14:20 PM
 * Class: ApolloServiceConstants
 * IDE: NetBeans 6.9.1
 */
public class ApolloServiceConstants {

    public static final String APOLLO_DIR;
    public static final int END_USER_APPLICATION_SOURCE_ID = 12;

    static final Logger logger = LoggerFactory.getLogger(ApolloServiceConstants.class);
    static {

        Map<String, String> env = System.getenv();
        String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
        if (apolloDir != null) {
            if (!apolloDir.endsWith(File.separator)) {
                apolloDir += File.separator;
            }
            APOLLO_DIR = apolloDir;
            logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:"
                    + APOLLO_DIR);
        } else {
            logger.error(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE
                    + " environment variable not found!");
            APOLLO_DIR = "";
        }
    }
	
	public static void main(String[] args) {
		
	}
}

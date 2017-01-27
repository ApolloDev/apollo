package edu.pitt.apollo.apollolocationservicesdk;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by mas400 on 7/13/16.
 */
public class WriteLocationCacheConfigProperties {
    public static void main(String[] args) {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("./src/main/resources/location_cache_config.properties");

            // set the properties value
            prop.setProperty("codeToLocationCache", "/Users/mas400/dev/apollo-work-dir/310/code_to_location_cache.ser");
            prop.setProperty("nameToLocationCache", "/Users/mas400/dev/apollo-work-dir/310/name_to_location_cache.ser");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

package edu.pitt.apollo.restservice.utils;

import edu.pitt.apollo.library_service_types.v3_0_0.LibraryItemContainer;
import edu.pitt.apollo.types.v3_0_0.*;



import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CodeResolver {

    static HashMap<String, String> locationCodeMap = new HashMap<String, String>();
    static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
    //static Properties configurationProperties = null;


    public static List<BigInteger> getListOfGroupIds(String listAsString)
    {

        String[] groupIdsSplit = listAsString.split(",");
        List<BigInteger> groupIdsAsList = new ArrayList<BigInteger>();
        for(String idAsString : groupIdsSplit)
        {
            groupIdsAsList.add(new BigInteger(idAsString));
        }
        return groupIdsAsList;
    }

    public static Properties getLibraryViewerConfigurationFile()
    {



        return loadConfigurationPropertiesFile();
    }
    public static Properties loadConfigurationPropertiesFile() {

         String env = System.getenv("APOLLO_300_WORK_DIR");
//        String env = System.getenv("HOME");

    	File configurationFile = new File(env + "/library_viewer_config.properties");
        InputStream input = null;
        Properties configurationProperties = new Properties();
        try {

            input = new FileInputStream(configurationFile);

            // load a properties file
            configurationProperties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return configurationProperties;
    }




}

package edu.pitt.apollo.flute.utils;

import edu.pitt.apollo.FluteSimulatorServiceException;
import edu.pitt.apollo.apollotranslator.ApolloTranslator;
import edu.pitt.apollo.types._07._03._2013.SimulatorConfiguration;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class FileUtils {

    private static String fluteServiceConfigDir;
    private static final String FLUTE_SERVICE_PROPERTIES_FILE = "flute_service.properties";
    public static final String FLUTE_CONFIGURATION_FILE_NAME = "flute-configuration.txt";
    public static final String FLUTE_VERBOSE_LOCAL_FILE_NAME = "flute-configuration-verbose.html";
    public static final String FLUTE_VERBOSE_TRANSLATED_FILE_NAME = "verbose-config.html";
    
    public static void downloadFile(URL sourcefile, String runIdHash, String destFileName)
            throws IOException {

       String location = getFluteConfigLocation(runIdHash) + destFileName;
        
        ReadableByteChannel rbc = Channels.newChannel(sourcefile.openStream());
        FileOutputStream fos = new FileOutputStream(location);
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        fos.flush();
        fos.close();
    }

    public static String getFluteConfigLocation(String runIdHash) {

        return RunUtils.WORK_DIR + File.separator + runIdHash + File.separator;
    }

    public static synchronized void createFluteConfigFile(SimulatorConfiguration config, String outputFileDir)
            throws FileNotFoundException, IOException, FluteSimulatorServiceException, ApolloTranslatorException,
            IllegalAccessException, ClassNotFoundException {

        // load the file names to use
        Properties properties = new Properties();
        properties.load(new FileInputStream(fluteServiceConfigDir + FLUTE_SERVICE_PROPERTIES_FILE));
        String javaScriptFilePath = properties.getProperty("translation_javascript_path");
        String translationInstructionsFilePath = properties.getProperty("translation_instructions_path");

        if (javaScriptFilePath == null) {
            throw new FluteSimulatorServiceException("Could not find translatio_javascript_path in FluTE"
                    + " properties file");
        }
        if (translationInstructionsFilePath == null) {
            throw new FluteSimulatorServiceException("Could not find translation_instructions_path in FluTE"
                    + " properties file");
        }

//        ApolloTranslator translator = new ApolloTranslator(translationInstructionsFilePath, javaScriptFilePath, false);
//        translator.translate(config, outputFileDir);

        ApolloTranslator translator = new ApolloTranslator(config, translationInstructionsFilePath, javaScriptFilePath, "#");
        translator.translate();
        translator.writeNativeFiles(translator.getSetterReturnObjects(), outputFileDir);
        translator.writeVerbose(translator.getSetterReturnObjects(), outputFileDir + File.separator + FLUTE_VERBOSE_LOCAL_FILE_NAME);
    }

    public static String loadConfigurationFile(String runIdHash, boolean verbose) throws FileNotFoundException {

        // only can return the verbose output for now
//        if (fluteConfigType.equalsIgnoreCase("verbose")) {
//        fileName = "verbose-html.html";
//        } else if (fluteConfigType.equalsIgnoreCase("nonverbose")) {
//        } else {
//            throw new FileNotFoundException("Unrecognized config file type");
//        }

        String configFileDirectory = getFluteConfigLocation(runIdHash);

        File fluteConfigFile;
        if (verbose) {
            fluteConfigFile = new File(configFileDirectory + FLUTE_VERBOSE_LOCAL_FILE_NAME);
        } else {
            fluteConfigFile = new File(configFileDirectory + FLUTE_CONFIGURATION_FILE_NAME);
        }

        Scanner scanner = new Scanner(fluteConfigFile);
        StringBuilder stBuild = new StringBuilder();
        while (scanner.hasNextLine()) {
            stBuild.append(scanner.nextLine()).append("\n");
        }

        return stBuild.toString();
    }

    public static String downloadFile(String url, String runIdHash) throws MalformedURLException, IOException {
        String location = getFluteConfigLocation(runIdHash) + FLUTE_CONFIGURATION_FILE_NAME;

        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(location);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        return location;
    }

    static {

        Map<String, String> env = System.getenv();
        fluteServiceConfigDir = env.get("FLUTE_SERVICE_CONFIG_DIR");
        if (fluteServiceConfigDir != null) {
            if (!fluteServiceConfigDir.endsWith(File.separator)) {
                fluteServiceConfigDir += File.separator;
            }
            System.out.println("FLUTE_SERVICE_CONFIG_DIR is now:" + fluteServiceConfigDir);
        } else {
            System.out.println("FLUTE_SERVICE_CONFIG_DIR environment variable not found!");
            fluteServiceConfigDir = "";
        }
    }
}

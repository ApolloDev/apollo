package edu.pitt.apollo.flute.utils;

import edu.pitt.apollo.TimLantBatchRunCreator.MikesStuff;
import edu.pitt.apollo.types.SimulatorConfiguration;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

public class FileUtils {

    public static void downloadFile(URL sourcefile, String destFileName)
            throws IOException {

        ReadableByteChannel rbc = Channels.newChannel(sourcefile.openStream());
        FileOutputStream fos = new FileOutputStream(destFileName);
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        fos.flush();
        fos.close();
    }

    public static synchronized void createFluteConfigFile(SimulatorConfiguration config, String outputDirectory) {

        MikesStuff.createFluteCfg(config, outputDirectory);
    }

    public static String loadConfigurationFile(String runIdHash) throws FileNotFoundException {

        String fileName;
        // only can return the verbose output for now
//        if (fluteConfigType.equalsIgnoreCase("verbose")) {
            fileName = "verbose-html.html";
//        } else if (fluteConfigType.equalsIgnoreCase("nonverbose")) {
//            fileName = "nonverbose-text.txt";
//        } else {
//            throw new FileNotFoundException("Unrecognized config file type");
//        }
        
        String configFilePath = RunUtils.WORK_DIR + File.separator + runIdHash + File.separator + fileName;
        File fluteConfigFile = new File(configFilePath);

        Scanner scanner = new Scanner(fluteConfigFile);
        StringBuilder stBuild = new StringBuilder();
        while (scanner.hasNextLine()) {
            stBuild.append(scanner.nextLine()).append("\n");
        }

        return stBuild.toString();
    }
}

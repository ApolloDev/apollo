package edu.pitt.apollo.outputtranslatorservice;

import edu.pitt.apollo.services_common.v4_0.Authentication;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.*;
/**
 * Created by mas400 on 2/24/16.
 */
public class TranslateOutput {
    public static void main(String [] args) {
        translateOutput(BigInteger.ONE, "test", new Authentication());
    }
    public static void translateOutput(BigInteger runId, String baseOutputURL, Authentication authentication) {
        /*1) set status of RUN to TRANSLATING_OUTPUT
          2) download file from url
          3) run python program to translate*/
        String s = null;
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();

        try {
            //download file
            URL url = new URL(baseOutputURL);
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream("output.hdf5");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            File dir = new File(path);
            String pythonScriptPath = path+"/output-translator-service/src/main/java/edu/pitt/apollo/outputtranslatorservice/FRED_output_translator.py";
            String[]callAndArgs={"python3", pythonScriptPath, "-i", path+"/output-translator-service/src/main/java/edu/pitt/apollo/outputtranslatorservice/output.allegheny.county_age.race.gender.location.hdf5"};

            Process p = Runtime.getRuntime().exec(callAndArgs, null, dir);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

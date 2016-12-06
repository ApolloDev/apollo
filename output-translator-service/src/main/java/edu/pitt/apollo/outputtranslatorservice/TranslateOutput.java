package edu.pitt.apollo.outputtranslatorservice;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.outputtranslatorservice.exception.OutputTranslatorException;
import edu.pitt.apollo.restrunmanagerserviceconnector.RestRunManagerServiceConnector;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.utilities.Md5Utils;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mas400 on 2/24/16.
 */
public class TranslateOutput {

	private static Logger logger = LoggerFactory.getLogger(TranslateOutput.class);
	private static RunManagerServiceConnector connector = new RestRunManagerServiceConnector("");
	private static final String OUTPUT_TRANSLATOR_PROPERTIES_FILE_NAME = "output_translator.properties";
	private static final String PYTHON_SCRIPT_FILE_PROP = "python_script_file_path";
	private static final String TEMP_DIRECTORY_PROP = "temp_dir";
	private static final String PYTHON_RUN_COMMAND_PROP = "python_run_cmd";
	private static final String pythonScriptFilePath;
	private static final String tempDirectory;
	private static final String pythonRunCommand;

	static {
		String apolloWorkDir = ApolloServiceConstants.APOLLO_DIR;
		Properties outputTranslatorProperties = new Properties();
		try {
			outputTranslatorProperties.load(new FileInputStream(apolloWorkDir + OUTPUT_TRANSLATOR_PROPERTIES_FILE_NAME));
		} catch (IOException ex) {
			logger.error("IOException loading output translator properties: " + ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
		pythonScriptFilePath = outputTranslatorProperties.getProperty(PYTHON_SCRIPT_FILE_PROP);
		tempDirectory = outputTranslatorProperties.getProperty(TEMP_DIRECTORY_PROP);
		pythonRunCommand = outputTranslatorProperties.getProperty(PYTHON_RUN_COMMAND_PROP);
	}

	public static void main(String[] args) {
		translateOutput(BigInteger.ONE, "http://localhost:8080/fred_out.hdf5", new Authentication());
	}

	private static void updateStatus(MethodCallStatusEnum statusEnum, String status, BigInteger runId, Authentication authentication) throws OutputTranslatorException {
		try {
			connector.updateStatusOfRun(runId, MethodCallStatusEnum.TRANSLATING_OUTPUT, "The output is being translated", authentication);
		} catch (RunManagementException ex) {
			try {
				connector.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED,
						"Could not update status for run " + runId, authentication);
			} catch (RunManagementException ex1) {
				throw new OutputTranslatorException(ex1.getMessage());
			}
		}
	}

	public static void translateOutput(BigInteger runId, String baseOutputURL, Authentication authentication) {
		/*1) set status of RUN to TRANSLATING_OUTPUT
          2) download file from url
          3) run python program to translate*/

//		try {
		// 1) update status
//			updateStatus(MethodCallStatusEnum.TRANSLATING_OUTPUT, "The run output is being translated", runId, authentication);
		try {
			// 2) download file
			String s = null;
			URL url = new URL(baseOutputURL);

			// create temp file name
			String tempFileName = runId + "_" + new Md5Utils().getMd5FromString(runId + baseOutputURL);
			String inputFile = tempDirectory + File.separator + tempFileName + ".h5";
			String outputFile = tempDirectory + File.separator + tempFileName + "_output.h5";
			String csvTemp = tempDirectory + File.separator + tempFileName + "_temp.csv";

			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream(inputFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();

			// 3) run python script
//				System.out.println(pythonRunCommand);
			String[] callAndArgs = {"./script.sh", inputFile, outputFile, csvTemp};
//				String[] env = {"LC_ALL=en_US.UTF-8", "LANG=en_US.UTF-8"}; // needed for pandas on some installations

			Process p = Runtime.getRuntime().exec(callAndArgs);

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			System.out.println("Here is the standard output of the command:\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}
			// read any errors from the attempted command
//				System.out.println("Here is the standard error of the command (if any):\n");
			String errors = "";
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);

				errors += s + "\n";
			}

//				if (!errors.equals("")) {
//					System.out.println("Errors:");
//					System.out.println(errors);
//					updateStatus(MethodCallStatusEnum.FAILED, "The python script failed during output translation: " + errors, runId, authentication);
//					return;
//				}
			// 4) upload translated output to file service
		} catch (IOException e) {
			e.printStackTrace();
		}
//		} catch (OutputTranslatorException ex) {
//			logger.error(ex.getMessage());
//		}

	}
}

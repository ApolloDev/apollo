package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.file.VerboseFileCreator.CssKeyValueClasses;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.apollotranslator.types.translator.TranslatorInput;
import edu.pitt.apollo.examples.exception.ConfigurationExampleException;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder.DefReproductionNumber;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum;
import junit.framework.TestCase;
import org.mozilla.javascript.RhinoException;

import java.io.*;
import java.util.*;

public abstract class AbstractSimulatorTest extends TestCase {

	protected Properties p = null;
	private ApolloTranslator at = null;
	protected Scanner scanner = null;
	protected String htmlFileName;
	protected String outputDirectory;
	protected String translationFile;
	protected String javaScriptFile;
	protected String configFile;
    protected final TranslationMode translationMode;
	public static final String NOT_IMPLEMENTED = "TEST METHOD NOT IMPLEMENTED!";
	private static final String OVERRIDE_MESSAGE = "This method must be overridden for the test to succesfully pass";
	protected DefReproductionNumber reproductionNumberType = DefReproductionNumber.R0;
	protected final RunSimulationMessageFactory.RunSimulationMessageDiseaseType runSimulationMessageDiseaseType;

	protected AbstractSimulatorTest(RunSimulationMessageFactory.RunSimulationMessageDiseaseType type, TranslationMode translationMode) {
		this.runSimulationMessageDiseaseType = type;
        this.translationMode = translationMode;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.reproductionNumberType = DefReproductionNumber.R0;
		createOutputDir(outputDirectory);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		at = null;
	}

    protected TranslatorInput getTranslatorInput(RunSimulationMessage runSimulationMessage) {

        TranslatorInput translatorInput = new TranslatorInput();
        translatorInput.setTranslationMode(TranslationMode.PLAIN_TEXT);
        translatorInput.setJavaScriptFilePath(javaScriptFile);
        translatorInput.setRunSimulationMessage(runSimulationMessage);
        translatorInput.setTranslationInstructionsFilePath(translationFile);
        return translatorInput;
    }

	protected final void createOutputDir(String outputDir) throws Exception {
		File f = new File(outputDir);
		if (!f.exists()) {
			f.mkdirs();
		}
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			if (files != null) {
				for (File d : files) {
					if (d.isFile()) {
						d.delete();
					}
				}
			}
		}

	}

	private void setupTranslator(RunSimulationMessage sc) {
		at = ApolloTranslatorFactory.getTranslator(getTranslatorInput(sc));
		try {
			writeFiles(at.translate(), outputDirectory);
			loadConfigurationFile();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	protected void loadConfigurationFile() throws IOException {
		p = new Properties();
		// load the config file into a properties map
		p.load(new FileInputStream(configFile));
	}

	protected final String getProperty(String key) {
		return p.getProperty(key);
	}

	protected void refreshScanner() {
		try {
			scanner = new Scanner(new File(htmlFileName));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail(e.getMessage());

		}
	}

	protected void writeFiles(Map<String, ByteArrayOutputStream> map, String outputDirectory) {

		for (String fileName : map.keySet()) {
			String fileLocation = outputDirectory + File.separator + fileName;
			OutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(fileLocation);
				ByteArrayOutputStream stream = map.get(fileName);
				stream.writeTo(outputStream);
			} catch (FileNotFoundException ex) {
				fail(ex.getMessage());
			} catch (IOException ex) {
				fail(ex.getMessage());
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException ex) {
						fail(ex.getMessage());
					}
				}
			}
		}

	}

	protected void translateNewSimulatorConfiguration(Set<ControlMeasureTypeEnum> controlMeasureTypes) {
		RunSimulationMessage sc;
		try {
			sc = getRunSimulationMessage(controlMeasureTypes);
		} catch (ConfigurationExampleException ex) {
			fail(ex.getMessage());
			return;
		}

		try {
			setupTranslator(sc);

			checkControlMeasuresDontExist(controlMeasureTypes);

		} catch (RhinoException re) {
			String message = "Error at line " + re.lineNumber()
					+ ",  column number: " + re.columnNumber()
					+ "\r\nReported error was: " + re.getMessage();
			System.out.println(message);
			fail(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			fail(e.getMessage());

		}

	}

	public void testNoControlMeasures() {

		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.noneOf(ControlMeasureTypeEnum.class);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		// check that the base parameters were written correctly
		checkBaseParameters();
	}
	public void runVaccControlMeasureTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.VACC_CM, ControlMeasureTypeEnum.NO_AV_CM,
                ControlMeasureTypeEnum.NO_SC_CM, ControlMeasureTypeEnum.NO_VI_CM, ControlMeasureTypeEnum.NO_VQ_CM,
                ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		// check that the base parameters were written correctly
		checkBaseParameters();
		// now fully check that all of the vaccine parameters were written
		checkVaccControlMeasure();

	}

	public void runAvControlMeasureTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.NO_VACC_CM, ControlMeasureTypeEnum.AV_CM,
				ControlMeasureTypeEnum.NO_SC_CM, ControlMeasureTypeEnum.NO_VI_CM, ControlMeasureTypeEnum.NO_VQ_CM,
				ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		// check that the base parameters were written correctly
		checkBaseParameters();
		// now fully check that all of the vaccine parameters were written
		checkAvControlMeasure();
	}

	public void runVaccAndAvControlStratgiesTest() {

		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.VACC_CM, ControlMeasureTypeEnum.AV_CM);
		translateNewSimulatorConfiguration(controlMeasureTypes);
		// check that the base parameters were written correctly
		checkBaseParameters();
		// now fully check that all of the vaccine parameters were written
		checkAvControlMeasure();
		checkVaccControlMeasure();
	}

	public void runSchoolClosureControlMeasure_AllSchools_FixedStartTimeTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.NO_VACC_CM,
				ControlMeasureTypeEnum.NO_AV_CM, ControlMeasureTypeEnum.ALL_SC_CM_FIXED, ControlMeasureTypeEnum.NO_VI_CM,
				ControlMeasureTypeEnum.NO_VQ_CM, ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkSchoolClosureControlMeasure(true, true);
	}

	public void runSchoolClosureControlMeasure_AllSchools_ReactiveStartTimeTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.NO_VACC_CM,
				ControlMeasureTypeEnum.NO_AV_CM, ControlMeasureTypeEnum.ALL_SC_CM_REACTIVE, ControlMeasureTypeEnum.NO_VI_CM,
				ControlMeasureTypeEnum.NO_VQ_CM, ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);
		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkSchoolClosureControlMeasure(true, false);
	}

	public void runSchoolClosureControlMeasure_IndividualSchools_FixedStartTimeTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.NO_VACC_CM,
				ControlMeasureTypeEnum.NO_AV_CM, ControlMeasureTypeEnum.INDIVIDUAL_SC_CM_FIXED,
				ControlMeasureTypeEnum.NO_VI_CM,
				ControlMeasureTypeEnum.NO_VQ_CM,
				ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkSchoolClosureControlMeasure(false, true);
	}

	public void runSchoolClosureControlMeasure_IndividualSchools_ReactiveStartTimeTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.NO_VACC_CM,
				ControlMeasureTypeEnum.NO_AV_CM, ControlMeasureTypeEnum.INDIVIDUAL_SC_CM_REACTIVE,
				ControlMeasureTypeEnum.NO_VI_CM,
				ControlMeasureTypeEnum.NO_VQ_CM,
				ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkSchoolClosureControlMeasure(false, false);
	}

	public void runVaccAvSchoolClosureFixedStartTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.VACC_CM,
				ControlMeasureTypeEnum.AV_CM, ControlMeasureTypeEnum.ALL_SC_CM_FIXED);
		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkAvControlMeasure();
		checkVaccControlMeasure();
		checkSchoolClosureControlMeasure(true, true);
	}

	public void runVaccAvSchoolClosureReactiveStartTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.VACC_CM,
				ControlMeasureTypeEnum.AV_CM, ControlMeasureTypeEnum.ALL_SC_CM_REACTIVE);
		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkAvControlMeasure();
		checkVaccControlMeasure();
		checkSchoolClosureControlMeasure(true, false);
	}

	public void runVaccAvAllScFixedVoluntaryLibralComboTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.VACC_CM, ControlMeasureTypeEnum.AV_CM,
				ControlMeasureTypeEnum.ALL_SC_CM_FIXED, ControlMeasureTypeEnum.VI_CM,
				ControlMeasureTypeEnum.VQ_CM, ControlMeasureTypeEnum.LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkAvControlMeasure();
		checkVaccControlMeasure();
		checkSchoolClosureControlMeasure(true, true);
		checkNamedControlMeasuresMeasures();
	}

	public void runVaccAvAllScReactiveVoluntaryLibralComboTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.VACC_CM, ControlMeasureTypeEnum.AV_CM,
				ControlMeasureTypeEnum.ALL_SC_CM_REACTIVE, ControlMeasureTypeEnum.VI_CM,
				ControlMeasureTypeEnum.VQ_CM, ControlMeasureTypeEnum.LSL_CM);
		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkAvControlMeasure();
		checkVaccControlMeasure();
		checkSchoolClosureControlMeasure(true, false);
		checkNamedControlMeasuresMeasures();
	}

	public void runDrugCmTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.NO_VACC_CM, ControlMeasureTypeEnum.NO_AV_CM,
				ControlMeasureTypeEnum.NO_SC_CM, ControlMeasureTypeEnum.NO_VI_CM,
				ControlMeasureTypeEnum.NO_VQ_CM, ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkDrugControlMeasure();
	}

	public void runWolbachiaControlMeasureTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.WOLBACHIA);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkWolbachiaControlMeasure();
	}

	public void runIndoorResidualSprayingControlMeasure() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.INDOOR_RESIDUAL_SPRAYING);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkIndoorResidualSprayingControlMeasure();
	}

    public void runInsecticideTreatedNetControlMeasure() {
        EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.INSECTICIDE_TREATED_NET);

        translateNewSimulatorConfiguration(controlMeasureTypes);
        checkBaseParameters();
        checkInsecticideTreatedNetControlMeasure();
    }

	public void runLarvicideControlMeasure() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.LARVICIDE);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkLarvicideControlMeasure();
	}

	public void runContainerReductionControlMeasure() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.CONTAINER_REDUCTION);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkContainerReductionControlMeasure();
	}

	public void runWolbachiaIRSLarvicideAndContainerReductionControlStrategiesTest() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.WOLBACHIA, ControlMeasureTypeEnum.INDOOR_RESIDUAL_SPRAYING,
				ControlMeasureTypeEnum.LARVICIDE, ControlMeasureTypeEnum.CONTAINER_REDUCTION);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		checkWolbachiaControlMeasure();
		checkIndoorResidualSprayingControlMeasure();
		checkLarvicideControlMeasure();
		checkContainerReductionControlMeasure();
	}

	public void checkNoDuplicateLinesInFile() {
		refreshScanner();
		Set<String> linesInFile = new HashSet<String>();
		// if it's an apollo param and value comment, make sure it doesn't exist
		// anywhere else
		// in the file.

		String lastLine = "<INITIAL STATE RANDOM STUFF 234570238453>";
		// if it's WARNING comment, make sure the line above the WARNING wasn't
		// the same WARNING
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			if (!line.isEmpty()) {
				if (line.startsWith("<span class=\"ApolloParamAndValAsString\"")) {
					assertFalse("Duplicate ApolloParamAndValAsString line detected:" + line,
							linesInFile.contains(line));

				} else if (line.startsWith("<span class=\"warning-")) {
					assertFalse("Duplicate CONSECUTIVE comments detected: "
							+ line, lastLine.equalsIgnoreCase(line));

				} else if (line.startsWith("<span class=\"" + CssKeyValueClasses.NATIVE_PARAM + "\">")) {
					assertFalse("Duplicate native param detected:" + line,
							linesInFile.contains(line));
				}
				lastLine = line;
				linesInFile.add(line);

			}
		}

	}

	protected abstract void checkBaseParameters();

	protected abstract void checkControlMeasuresDontExist(Set<ControlMeasureTypeEnum> controlMeasureTypes);

	protected void checkAvControlMeasure() {
		fail(OVERRIDE_MESSAGE);
	}

	protected void checkVaccControlMeasure() {
		fail(OVERRIDE_MESSAGE);
	}

	protected void checkDrugControlMeasure() {
		fail(OVERRIDE_MESSAGE);
	}

	protected void checkSchoolClosureControlMeasure(
			boolean allSchools, boolean fixedStartTime) {
		fail(OVERRIDE_MESSAGE);
	}

	protected void checkNamedControlMeasuresMeasures() {
		fail(OVERRIDE_MESSAGE);
	}

	protected void checkWolbachiaControlMeasure() {
		fail(OVERRIDE_MESSAGE);
	}

	protected void checkIndoorResidualSprayingControlMeasure() {
        fail(OVERRIDE_MESSAGE);
    }

    protected void checkInsecticideTreatedNetControlMeasure() {
        fail(OVERRIDE_MESSAGE);
    }

	protected void checkLarvicideControlMeasure() {
		fail(OVERRIDE_MESSAGE);
	}

	protected void checkContainerReductionControlMeasure() {
		fail(OVERRIDE_MESSAGE);
	}

	protected final RunSimulationMessage getRunSimulationMessage(Set<ControlMeasureTypeEnum> controlMeasureTypes) throws ConfigurationExampleException {
		return RunSimulationMessageFactory.getRunSimulationMessage(runSimulationMessageDiseaseType, controlMeasureTypes);
	}

}

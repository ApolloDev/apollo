package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.apollotranslator.types.translator.TranslatorInput;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumSet;
import java.util.Set;

/**
 * Created by nem41 on 7/23/15.
 */
public class TestOpenMalaria extends AbstractSimulatorTest {

	private static final String RES_DIR = "./src/test/resources/openmalaria";
	private static final String OUTPUT_DIR = RES_DIR + File.separator + "output";
	private static final String HTML_FILE_NAME = OUTPUT_DIR + File.separator
			+ "verbose.html";
	private static final String TRANSLATION_FILE = RES_DIR + File.separator
			+ "openmalaria_translation_4.0.csv";
	private static final String JAVASCRIPT_FILE = RES_DIR + File.separator
			+ "openmalaria_javascript_4.0.js";
	private static final String CONFIG_FILE = OUTPUT_DIR + File.separator + "config.xml";
	private static final String VALID_BASE_CONFIG_FILE = RES_DIR + File.separator + "valid_base_config.xml";
	private static final String VALID_IRS_CONFIG_FILE = RES_DIR + File.separator + "valid_irs_config.xml";
	private static final String VALID_ITN_CONFIG_FILE = RES_DIR + File.separator + "valid_itn_config.xml";
	private static final String VALID_ALL_CM_CONFIG_FILE = RES_DIR + File.separator + "valid_all_cm_config.xml";
	private static final String INITIAL_BASE_CONFIG_FILE = RES_DIR + File.separator + "initial_base_config.xml";

	public TestOpenMalaria() {
		super(RunSimulationMessageFactory.RunSimulationMessageDiseaseType.MALARIA, TranslationMode.XML,
                HTML_FILE_NAME, OUTPUT_DIR, TRANSLATION_FILE, JAVASCRIPT_FILE, CONFIG_FILE);
	}

	@Override
	protected TranslatorInput getTranslatorInput(RunSimulationMessage runSimulationMessage) {

		TranslatorInput translatorInput = new TranslatorInput();
		translatorInput.setTranslationMode(TranslationMode.XML);
		translatorInput.setJavaScriptFilePath(javaScriptFile);
		translatorInput.setRunSimulationMessage(runSimulationMessage);
		translatorInput.setTranslationInstructionsFilePath(translationFile);

		try {
			translatorInput.setBaseConfigurationFileContent(new String(Files.readAllBytes(Paths.get(INITIAL_BASE_CONFIG_FILE))));
		} catch (IOException ex) {
			fail(ex.getMessage());
		}

		return translatorInput;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void checkBaseParameters() {

		checkFile(CONFIG_FILE, VALID_BASE_CONFIG_FILE);

	}

	@Override
	protected void checkControlMeasuresDontExist(Set<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes) {

	}

	@Override
	protected void checkIndoorResidualSprayingControlMeasure() {
		checkFile(CONFIG_FILE, VALID_IRS_CONFIG_FILE);
	}

	@Override
	protected void checkInsecticideTreatedNetControlMeasure() {
		checkFile(CONFIG_FILE, VALID_ITN_CONFIG_FILE);
	}

	protected void checkAllControlMeasure() {
		checkFile(CONFIG_FILE, VALID_ALL_CM_CONFIG_FILE);
	}

	@Override
	public void runIndoorResidualSprayingControlMeasure() {
		EnumSet<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum.INDOOR_RESIDUAL_SPRAYING);

		// don't want to check base parameters since the entire file will be compared
		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkIndoorResidualSprayingControlMeasure();
	}

	@Override
	public void runInsecticideTreatedNetControlMeasure() {
		EnumSet<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum.INSECTICIDE_TREATED_NET);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkInsecticideTreatedNetControlMeasure();
	}

	public void runAllControlMeasures() {
		EnumSet<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum.INSECTICIDE_TREATED_NET,
				AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum.INDOOR_RESIDUAL_SPRAYING);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkAllControlMeasure();
	}

	private void checkFile(String outputFile, String baseFile) {
		try {
			// read in valid file and compare directly with output
			String validContents = new String(Files.readAllBytes(Paths.get(baseFile)));
			String outputContents = new String(Files.readAllBytes(Paths.get(outputFile)));

			assertEquals(outputContents, validContents);
		} catch (IOException ex) {
			fail(ex.getMessage());
		}
	}

	public void testIRSControlMeasure() {
		runIndoorResidualSprayingControlMeasure();
	}

	public void testITNControlMeasure() {
		runInsecticideTreatedNetControlMeasure();
	}

	public void testAllControlMeasures() {
		runAllControlMeasures();
	}

}

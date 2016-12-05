package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;

import java.io.File;
import java.util.Set;

public class TestAnthrax extends AbstractSimulatorTest {

    public static final String RES_DIR = "./src/test/resources/anthrax";
    public static final String OUTPUT_DIR = RES_DIR + File.separator + "output";
    public static final String HTML_FILE_NAME = OUTPUT_DIR + File.separator
            + "verbose.html";
    public static final String TRANSLATION_FILE = RES_DIR + File.separator
            + "anthrax_translation_4.0.csv";
    public static final String JAVASCRIPT_FILE = RES_DIR + File.separator
            + "anthrax_javascript_4.0.js";
    public static final String COMMENT_PREFIX = "#";
    public static final String CONFIG_FILE = OUTPUT_DIR + File.separator + "config.txt";

    public TestAnthrax() {
        super(RunSimulationMessageFactory.RunSimulationMessageDiseaseType.ANTHRAX, TranslationMode.PLAIN_TEXT);
    }

    @Override
    protected void setUp() throws Exception {
        htmlFileName = HTML_FILE_NAME;
        outputDirectory = OUTPUT_DIR;
        configFile = CONFIG_FILE;
        translationFile = TRANSLATION_FILE;
        javaScriptFile = JAVASCRIPT_FILE;
        super.setUp();

    }

    @Override
    protected void checkBaseParameters() {
        assertEquals("3600", getProperty("run_duration"));
        assertEquals("9313830.51695", getProperty("unexposed_compartment_size"));
        assertEquals("52824.094900000004", getProperty("asymptomatic_compartment_size"));
        assertEquals("4.54", getProperty("asymptomatic_to_prodromal_mu"));
        assertEquals("0.19", getProperty("asymptomatic_to_prodromal_sigma"));
        assertEquals("4.58", getProperty("prodromal_to_fulminant_mu"));
        assertEquals("0.44", getProperty("prodromal_to_fulminant_sigma"));
        assertEquals("3.58", getProperty("fulminant_to_dead_mu"));
        assertEquals("0.36", getProperty("fulminant_to_dead_sigma"));
        checkNoDuplicateLinesInFile();
        // checkAtLeastOneCommentExists();

    }

    @Override
    protected void checkControlMeasuresDontExist(Set<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes) {

		/*		if (scCm == DefScCm.NO_SC_CM) {
         }*/
    }

    public void checkAtLeastOneCommentExists() {
        refreshScanner();
        assertTrue(scanner.findWithinHorizon(
                "One to many mapping: infectiousDisease\\.infectiousPeriod maps to days_infectious\\[0\\], days_symptomatic\\[0\\]",
                0) != null);
    }

    @Override
    protected void checkDrugControlMeasure() {
        StringBuilder strBuild = new StringBuilder();
        double val = 3000;
        for (int i = 0; i < 150; i++) {
            for (int j = 0; j < 24; j++) {
                int valToAppend = (int) Math.floor(val / 24);
                strBuild.append(",").append(valToAppend);
            }
            val = val + 1;
        }
        strBuild = strBuild.deleteCharAt(0);

        assertEquals("0.9", getProperty("prophylaxis_treatment_efficacy"));
        assertEquals("0", getProperty("begin_treatment_interval"));
        assertEquals("0.55", getProperty("prodromal_treatment_efficacy"));
        assertEquals("0.55", getProperty("fulminant_treatment_efficacy"));
        assertEquals(strBuild.toString(), getProperty("prophylaxis_schedule"));
    }

    public void testDrugControlMeasure() {
        runDrugCmTest();
    }
}

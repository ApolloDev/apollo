package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;

import java.io.File;
import java.util.Set;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 17, 2014
 * Time: 2:12:34 PM
 * Class: TestEbola
 */
public class TestEbola extends AbstractSimulatorTest {

	private static final String RES_DIR = "./src/test/resources/ebola";
	private static final String OUTPUT_DIR = RES_DIR + File.separator + "output";
	private static final String HTML_FILE_NAME = OUTPUT_DIR + File.separator
			+ "verbose.html";
	private static final String TRANSLATION_FILE = RES_DIR + File.separator
			+ "ebola_translation_4.0.csv";
	private static final String JAVASCRIPT_FILE = RES_DIR + File.separator
			+ "ebola_javascript_4.0.js";
	private static final String CONFIG_FILE = OUTPUT_DIR + File.separator + "config.txt";

	public TestEbola() {
		// influenza is similar enough we can use it for now
		super(RunSimulationMessageFactory.RunSimulationMessageDiseaseType.EBOLA, TranslationMode.PLAIN_TEXT,
                HTML_FILE_NAME, OUTPUT_DIR, TRANSLATION_FILE, JAVASCRIPT_FILE, CONFIG_FILE);
	}

	@Override
	protected void checkBaseParameters() {
		assertEquals("6030932.50000", getProperty("initialSusceptible"));
		assertEquals("0.00000", getProperty("initialExposed"));
		assertEquals("63483.50000", getProperty("initialInfectiousDying"));
		assertEquals("253934.00000", getProperty("initialInfectiousSurviving"));
		assertEquals("0.00000", getProperty("initialRecovered"));
		assertEquals("0.67000", getProperty("symptomaticFraction"));
		assertEquals("0.2", getProperty("caseFatalityRate"));
		assertEquals("6", getProperty("infectiousPeriod"));
		assertEquals("2", getProperty("latentPeriod"));
		assertEquals("1.3", getProperty("R0"));
		assertEquals("150", getProperty("runLength"));
		checkNoDuplicateLinesInFile();	}

	@Override
	protected void checkControlMeasuresDontExist(Set<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}

package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder;
import edu.pitt.apollo.examples.runsimulationmessages.InfluenzaRunSimulationMessageBuilder;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 2, 2014
 * Time: 2:21:48 PM
 * Class: TestClara
 */
public class TestClara extends AbstractSimulatorTest {

	private static final String RES_DIR = "./src/test/resources/clara";
	private static final String OUTPUT_DIR = RES_DIR + File.separator + "output";
	private static final String HTML_FILE_NAME = OUTPUT_DIR + File.separator
			+ "verbose.html";
	private static final String TRANSLATION_FILE = RES_DIR + File.separator
			+ "clara_translation_4.0.csv";
	private static final String JAVASCRIPT_FILE = RES_DIR + File.separator
			+ "clara_javascript_4.0.js";
	private static final String CONFIG_FILE = OUTPUT_DIR + File.separator + "config.txt";

	public TestClara() {
		super(RunSimulationMessageFactory.RunSimulationMessageDiseaseType.DENGUE, TranslationMode.PLAIN_TEXT,
                HTML_FILE_NAME, OUTPUT_DIR, TRANSLATION_FILE, JAVASCRIPT_FILE, CONFIG_FILE);
	}

	@Override
	protected void setUp() throws Exception {
	    super.setUp();
		reproductionNumberType = InfluenzaRunSimulationMessageBuilder.DefReproductionNumber.TRANSMISSION_PROBABILITY;
	}

	@Override
	protected void checkBaseParameters() {
		assertEquals("0.9", getProperty("set V-to-H-Transmissibility"));
		assertEquals("0.9", getProperty("set V-to-H-Transmissibility"));
		assertEquals("25", getProperty("set symptomatic-ratio"));
		assertEquals("7.5", getProperty("set Person-Migration-Speed"));
		assertEquals("35", getProperty("set Person-Building-Affinity"));
		assertEquals("90", getProperty("set Bite-Probability"));
		assertEquals("5", getProperty("set Bite-Radius"));
		assertEquals("8", getProperty("set Time-Between-Bites"));
		assertEquals("4", getProperty("set Mosquito-Migration-Speed"));
		assertEquals("90", getProperty("set Mosquito-House-Affinity"));
		assertEquals("1", getProperty("set Mating-Radius"));
		assertEquals("10", getProperty("set Mating-Probability"));
		assertEquals("5", getProperty("set Straight-Travel-After-Ovi"));
		assertEquals("3", getProperty("set Gonotrophic-Cycle-Length"));
		assertEquals("24", getProperty("set Eggs-Per-Brood"));
		assertEquals("10", getProperty("set Min-Eggs-Per-Oviposit"));
		assertEquals("0", getProperty("set Age-Dep-Fecundity-Reduction"));
		assertEquals("4", getProperty("set Egg-Hatch-Time"));
		assertEquals("1", getProperty("set Egg-Mortality-Rate"));
		assertEquals("14", getProperty("set Eclose-Time"));
		assertEquals("1", getProperty("set Omega"));
		assertEquals("2.5", getProperty("set Sigma"));
		assertEquals("21", getProperty("set Max-Mosquito-Age"));
		assertEquals("11", getProperty("set Mosquito-Mortality-Rate-Min"));
		assertEquals("11", getProperty("set Mosquito-Mortality-Rate-Max"));
		assertEquals("200", getProperty("set Breteau-Index"));
		assertEquals("33.75", getProperty("set K-Per-House"));
		assertEquals("true", getProperty("set Heterogenous-K?"));
		assertEquals("0.5", getProperty("set Outdoor-Ovisite-Number-Ratio"));
		assertEquals("2", getProperty("set Outdoor-Ovisite-K-Ratio-Max"));
		assertEquals("71", getProperty("set Infection-Day-Start"));
		assertEquals("\"[2 0 0 100 0 10 1]\"", getProperty("set infection-seed-matrix"));
		assertEquals("0.5", getProperty("set Initial-Mosquito-Infection-Rate"));
		assertEquals("0", getProperty("set Initial-Person-Immunity-Rate"));
		assertEquals("5", getProperty("set Backfill-Eggs-Day"));
		assertEquals("6", getProperty("set Initial-Mosquito-To-Human-Ratio"));
		assertEquals("50", getProperty("set Male-Mosquito-Ratio"));
		assertEquals("\"inputs/Cairns\"", getProperty("set input-file-root"));
		assertEquals("true", getProperty("set write-data?"));
		assertEquals("112", getProperty("set Max-Days"));
		assertEquals("126", getProperty("set peak-day"));
		assertEquals("true", getProperty("set log-mosquito-infections?"));
		assertEquals("true", getProperty("set Propagate-Infection?"));

		checkNoDuplicateLinesInFile();
	}

	@Override
	protected void checkControlMeasuresDontExist(Set<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes) {
	}

	@Override
	protected void checkWolbachiaControlMeasure() {
		assertEquals("0", getProperty("set Wolbachia-Release-Start-Day"));
		assertEquals("0", getProperty("set Wolbachia-Release-End-Day"));
		assertEquals("4", getProperty("set Wolbachia-Sites-Every-Nth"));
		assertEquals("14", getProperty("set Wolbachia-Seed-Adults-per-house"));
		assertEquals("false", getProperty("set Clear-Wolbachia-On-Migration?"));
		assertEquals("\"roads\"", getProperty("set Wolbachia-Release-On"));
		assertEquals("7", getProperty("set Wolbachia-Release-Frequency"));
		assertEquals("0", getProperty("set Wolb-Affect-Egg-Mortality-Rate"));
		assertEquals("0", getProperty("set Wolb-Affect-Larva-Mortality-Rate"));
		assertEquals("15", getProperty("set Wolb-Affect-Adult-Mortality-Rate"));
		assertEquals("15", getProperty("set Wolb-Affect-Fecundity"));
		assertEquals("0", getProperty("set Wolb-Affect-Mating-Probability"));
		assertEquals("0", getProperty("set Wolb-Affect-Leakage-Rate"));
		assertEquals("0", getProperty("set Wolb-Pre-Release-Adult-Suppression"));
	}

	@Override
	protected void checkIndoorResidualSprayingControlMeasure() {
		assertEquals("113", getProperty("set control-init-day"));
		assertEquals("true", getProperty("set is-vector-control?"));
		assertEquals("80", getProperty("set control-percent-mosquito"));
		assertEquals("100", getProperty("set cover-radius"));
	}

	@Override
	protected void checkLarvicideControlMeasure() {
		assertEquals("113", getProperty("set larvicide-init-day"));
		assertEquals("true", getProperty("set is-larvicide-control?"));
	}

	@Override
	protected void checkContainerReductionControlMeasure() {
		assertEquals("true", getProperty("set is-reduction-control?"));
		assertEquals("113", getProperty("set reduction-init-day"));
		assertEquals("60", getProperty("set control-percent-eggs"));
		assertEquals("60", getProperty("set control-percent-larvae"));
	}

	@Override
	protected void loadConfigurationFile() throws IOException {
		p = new Properties();

		File config = new File(configFile);
		Scanner scanner = new Scanner(config);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			int firstSpace = line.indexOf(' ');
			int secondSpace = line.indexOf(' ', firstSpace + 1);
			if (!line.trim().equals("")) {
				String param = line.substring(0, secondSpace);
				String value = line.substring(secondSpace + 1, line.length());
				p.put(param, value);
			}
		}

		scanner.close();
	}

	public void testWolbachiaControlMeasure() {
		runWolbachiaControlMeasureTest();
	}

	public void testIndoorResidualSprayingControlMeasure() {
		runIndoorResidualSprayingControlMeasure();
	}

	public void testLarvicideControlMeasure() {
		runLarvicideControlMeasure();
	}
	
	public void testContainerRecductionControlMeasure() {
		runContainerReductionControlMeasure();
	}
	public void testWolbachiaIRSLarvicideAndContainerReductionControlStrategies() {
		runWolbachiaIRSLarvicideAndContainerReductionControlStrategiesTest();
	}
}

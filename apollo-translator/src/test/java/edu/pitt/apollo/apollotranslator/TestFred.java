package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

public class TestFred extends AbstractSimulatorTest {

	private static final String RES_DIR = "./src/test/resources/fred";
	private static final String OUTPUT_DIR = RES_DIR + File.separator + "output";
	private static final String HTML_FILE_NAME = OUTPUT_DIR + File.separator
			+ "verbose.html";
	private static final String TRANSLATION_FILE = RES_DIR + File.separator
			+ "fred_translation_4.0.csv";
	private static final String JAVASCRIPT_FILE = RES_DIR + File.separator
			+ "fred_javascript_4.0.js";
	private static final String VACC_SCHED_FILE = OUTPUT_DIR + File.separator
			+ "fred-vaccination-schedule_0.txt";
	private static final String POP_FILE = OUTPUT_DIR + File.separator
			+ "fred_initial_population_0.txt";
	private static final String CONFIG_FILE = OUTPUT_DIR + File.separator + "config.txt";

	public TestFred() {
		super(RunSimulationMessageFactory.RunSimulationMessageDiseaseType.INFLUENZA, TranslationMode.PLAIN_TEXT);
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
		assertEquals("150", getProperty("days"));
		assertEquals("0.67", getProperty("symp[0]"));
		assertEquals("1.3", getProperty("R0"));
		assertEquals("06037", getProperty("fips"));
		assertEquals("6 0 0.30929497095034175 0.7491442676127461 0.9640089638824945 0.998050129265157 1", getProperty("days_latent[0]"));
		assertEquals("15 0 0 0.008153855622262108 0.07381920072913184 0.220047221101578 0.4211390211778252 0.627841045157024 0.7956630524662738 0.9053593334281886 0.9634273293840823 0.9883249445233538 0.9969492758839874 0.9993530330124397 0.9998895372569839 1",
				getProperty("days_infectious[0]"));
		assertEquals("15 0 0 0.008153855622262108 0.07381920072913184 0.220047221101578 0.4211390211778252 0.627841045157024 0.7956630524662738 0.9053593334281886 0.9634273293840823 0.9883249445233538 0.9969492758839874 0.9993530330124397 0.9998895372569839 1",
				getProperty("days_symptomatic[0]"));
		assertEquals("1 0.04", getProperty("residual_immunity_values[0]"));
		assertEquals("exposed:0.47;infectious:0.53",
				getProperty("advanced_seeding"));
		assertEquals("2009-09-08", getProperty("start_date"));
		checkNoDuplicateLinesInFile();

		try {
			checkPopFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
		}
		// checkAtLeastOneCommentExists();

	}

	@Override
	protected void checkControlMeasuresDontExist(Set<ControlMeasureTypeEnum> controlMeasureTypes) {
		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.NO_VACC_CM)) {
			assertNull(getProperty("enable_vaccination"));
			assertNull(getProperty("enable_behaviors"));
			assertNull(getProperty("number_of_vaccines"));
			assertNull(getProperty("accept_vaccine_strategy_distribution"));
			assertNull(getProperty("vaccine_number_of_doses[0]"));
			assertNull(getProperty("vaccine_total_avail[0]"));
			assertNull(getProperty("vaccine_additional_per_day[0]"));
			assertNull(getProperty("vaccine_starting_day[0]"));

			assertNull(getProperty("vaccine_next_dosage_day[0][0]"));
			assertNull(getProperty("vaccine_dose_efficacy_ages[0][0]"));
			assertNull(getProperty("vaccine_dose_efficacy_values[0][0]"));
			assertNull(getProperty("vaccine_dose_efficacy_delay_ages[0][0]"));
			assertNull(getProperty("vaccine_dose_efficacy_delay_values[0][0]"));
			assertNull(getProperty("vaccination_capacity_file"));

		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.NO_AV_CM)) {
			assertNull(getProperty("enable_antivirals"));
			assertNull(getProperty("av_initial_stock[0]"));
		}

		/*		if (scCm == DefScCm.NO_SC_CM) {
		 }*/
	}

	@Override
	protected void checkAvControlMeasure() {
		assertEquals("1", getProperty("enable_antivirals"));
        assertEquals("1", getProperty("number_antivirals"));
		assertEquals("0", getProperty("av_disease[0]"));
		assertEquals("0", getProperty("av_prophylaxis[0]"));
		assertEquals("0", getProperty("av_start_day[0]"));
		assertEquals("461175", getProperty("av_total_avail[0]"));
		assertEquals("3074.5", getProperty("av_additional_per_day[0]"));
		assertEquals("0", getProperty("av_initial_stock[0]"));
		assertEquals("1", getProperty("av_course_length[0]"));
		assertEquals("0.0", getProperty("av_reduce_susceptibility[0]"));
		assertEquals("0.7", getProperty("av_reduce_infectivity[0]"));
		assertEquals("0.7", getProperty("av_reduce_symptomatic_period[0]"));
		assertEquals("0.2", getProperty("av_prob_symptomatics[0]"));
	}

	@Override
	protected void checkVaccControlMeasure() {
		assertEquals("1", getProperty("enable_vaccination"));
		assertEquals("1", getProperty("enable_behaviors"));
		assertEquals("1", getProperty("number_of_vaccines"));
		assertNotNull(getProperty("accept_vaccine_strategy_distribution"));
		assertEquals("1", getProperty("vaccine_number_of_doses[0]"));
		assertEquals("461175", getProperty("vaccine_total_avail[0]"));
		assertEquals("461175", getProperty("vaccine_additional_per_day[0]"));
		assertEquals("0", getProperty("vaccine_starting_day[0]"));
		assertEquals("0", getProperty("vaccine_next_dosage_day[0][0]"));
		assertEquals("2 0 100", getProperty("vaccine_dose_efficacy_ages[0][0]"));
		assertEquals("1 0.7", getProperty("vaccine_dose_efficacy_values[0][0]"));
		assertEquals("2 0 100",
				getProperty("vaccine_dose_efficacy_delay_ages[0][0]"));
		assertEquals("1 14",
				getProperty("vaccine_dose_efficacy_delay_values[0][0]"));
		assertEquals("fred-vaccination-schedule_0.txt",
				getProperty("vaccination_capacity_file"));
		assertEquals("7 0.10000 0.9 0 0 0 0 0", getProperty("accept_vaccine_strategy_distribution"));

		try {
			checkVaccScheduleFile();
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void checkSchoolClosureControlMeasure(boolean allSchools,
			boolean fixedStartTime) {
		if (allSchools) {
			assertEquals("global", getProperty("school_closure_policy"));
		} else {
			assertEquals("reactive", getProperty("school_closure_policy"));
		}

		assertEquals("56", getProperty("school_closure_period"));
        assertEquals("2", getProperty("school_closure_delay"));
		if (fixedStartTime) {
			assertEquals("0", getProperty("school_closure_day"));
		} else {
			assertEquals("0.01", getProperty("school_closure_threshold"));
		}
	}

	private void checkPopFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(POP_FILE));
		String line = br.readLine();
		assertEquals("#line_format", line);
		line = br.readLine();
		assertEquals("0 0 101941", line);
		br.close();
	}

	protected void checkVaccScheduleFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(VACC_SCHED_FILE));
		try {
			for (int i = 0; i < 150; i++) {
				String[] cols = br.readLine().split(" ");
				//this accomidates the pre-emptive vaccination stuff..so the schedule could look like 5 -500, 4 -400, ... 0 3000, 1 3001...
//				if (i < 5) {
//					assertEquals(i - 5, Integer.parseInt(cols[0]));
//					assertEquals(Math.abs(i - 5) * 100, Integer.parseInt(cols[1]));
//				} else {
					assertEquals(i, Integer.parseInt(cols[0]));
					assertEquals(3000 + i, Integer.parseInt(cols[1]));
//				}
			}
		} finally {
			br.close();
		}
	}

	public void checkAtLeastOneCommentExists() {
		refreshScanner();
		assertTrue(scanner.findWithinHorizon(
				"One to many mapping: infectiousDisease\\.infectiousPeriod maps to days_infectious\\[0\\], days_symptomatic\\[0\\]",
				0) != null);
	}

	public void testPopFile() {
		EnumSet<ControlMeasureTypeEnum> controlMeasureTypes = EnumSet.of(ControlMeasureTypeEnum.NO_VACC_CM,
				ControlMeasureTypeEnum.NO_AV_CM, ControlMeasureTypeEnum.NO_SC_CM,
				ControlMeasureTypeEnum.NO_VI_CM,
				ControlMeasureTypeEnum.NO_VQ_CM,
				ControlMeasureTypeEnum.NO_LSL_CM, ControlMeasureTypeEnum.NO_DRUG_CM);

		translateNewSimulatorConfiguration(controlMeasureTypes);
		checkBaseParameters();
		try {
			checkPopFile();
		} catch (IOException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

	}

	public void testVaccControlMeasure() {
		runVaccControlMeasureTest();
	}

	public void testAvControlMeasure() {
		runAvControlMeasureTest();
	}

	public void testVaccAndAvControlStrategies() {
		runVaccAndAvControlStratgiesTest();
	}

	public void testSchoolClosureAllSchoolsFixedStartTime() {
		runSchoolClosureControlMeasure_AllSchools_FixedStartTimeTest();
	}

	public void testSchoolClosureAllSchoolsReactiveStartTime() {
		runSchoolClosureControlMeasure_AllSchools_ReactiveStartTimeTest();
	}

	public void testSchoolClosureControlMeasure_IndividualSchools_FixedStartTime() {
		runSchoolClosureControlMeasure_IndividualSchools_FixedStartTimeTest();
	}

	public void testSchoolClosureControlMeasure_IndividualSchools_ReactiveStartTime() {
		runSchoolClosureControlMeasure_IndividualSchools_ReactiveStartTimeTest();
	}

	public void testVaccAvAllScFixed() {
		runVaccAvSchoolClosureFixedStartTest();
	}

	public void testVaccAvAllScReactive() {
		runVaccAvSchoolClosureReactiveStartTest();
	}
}

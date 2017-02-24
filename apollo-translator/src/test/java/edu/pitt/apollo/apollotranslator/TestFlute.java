package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;

import java.io.File;
import java.util.Set;

public class TestFlute extends AbstractSimulatorTest {

	private static final String RES_DIR = "./src/test/resources/flute/";
	private static final String OUTPUT_DIR = RES_DIR + File.separator + "output";
	private static final String HTML_FILE_NAME = OUTPUT_DIR + File.separator
			+ "verbose.html";
	private static final String TRANSLATION_FILE = RES_DIR + File.separator
			+ "flute_translation_4.0.csv";
	private static final String JAVASCRIPT_FILE = RES_DIR + File.separator
			+ "flute_javascript_4.0.js";
	private static final String CONFIG_FILE = OUTPUT_DIR + File.separator
			+ "config.txt";

	public TestFlute() {
		super(RunSimulationMessageFactory.RunSimulationMessageDiseaseType.INFLUENZA, TranslationMode.PLAIN_TEXT,
                HTML_FILE_NAME, OUTPUT_DIR, TRANSLATION_FILE, JAVASCRIPT_FILE, CONFIG_FILE);
	}

	@Override
	protected void checkBaseParameters() {
		assertEquals("1", getProperty("seed"));
		assertEquals("apollo_demo", getProperty("label"));
		assertEquals("1", getProperty("logfile"));
		assertEquals("usa", getProperty("datafile"));
		assertEquals("6", getProperty("onlystate"));
		assertEquals("37", getProperty("onlycounty"));
		assertEquals("150", getProperty("runlength"));
		assertEquals("0.01141", getProperty("seedinfectedfrac"));
		assertEquals("1.3", getProperty("R0"));
		assertEquals("150", getProperty("runlength"));
		
		checkNoDuplicateLinesInFile();
	}

	@Override
	protected void checkControlMeasuresDontExist(Set<AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum> controlMeasureTypes) {
        checkBaseParameters();
	}

	@Override
	protected void checkAvControlMeasure() {
		assertEquals("0.7", getProperty("AVEi"));
		assertEquals("0.3", getProperty("AVEs"));
		assertEquals("0.6", getProperty("AVEp"));
		assertEquals("3000", getProperty("antiviraldoses"));
		assertEquals("3075", getProperty("antiviraldosesdaily"));
		assertTrue(
				"expected value of 0 or 2, but value was "
				+ getProperty("responsedelay"),
				getProperty("responsedelay").equals("0")
				|| getProperty("responsedelay").equals("2"));
	}

	@Override
	protected void checkVaccControlMeasure() {
		assertEquals("0.7", getProperty("vaccinationfraction"));
		assertEquals(
				"0 0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
				getProperty("vaccinebuildup"));
		assertEquals("1.0", getProperty("preexistingimmunitylevel"));
		assertEquals(
				"0 3001 3002 3003 3004 3005 3006 3007 3008 3009 3010 3011 3012 3013 3014 3015 3016 3017 3018 3019 3020 3021 3022 3023 3024 3025 3026 3027 3028 3029 3030 3031 3032 3033 3034 3035 3036 3037 3038 3039 3040 3041 3042 3043 3044 3045 3046 3047 3048 3049 3050 3051 3052 3053 3054 3055 3056 3057 3058 3059 3060 3061 3062 3063 3064 3065 3066 3067 3068 3069 3070 3071 3072 3073 3074 3075 3076 3077 3078 3079 3080 3081 3082 3083 3084 3085 3086 3087 3088 3089 3090 3091 3092 3093 3094 3095 3096 3097 3098 3099 3100 3101 3102 3103 3104 3105 3106 3107 3108 3109 3110 3111 3112 3113 3114 3115 3116 3117 3118 3119 3120 3121 3122 3123 3124 3125 3126 3127 3128 3129 3130 3131 3132 3133 3134 3135 3136 3137 3138 3139 3140 3141 3142 3143 3144 3145 3146 3147 3148 3149 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0",
				getProperty("vaccineproduction"));
		assertEquals("0 0.7 0.4 0.6 1.0 0.1 0.1 0.1 0.1 0.1 1",
				getProperty("vaccinedata"));
		assertEquals("0 3000", getProperty("vaccinedoses"));
		assertEquals("50000", getProperty("vaccinedosesdaily"));
		assertEquals("1 1 1 0 0 0 0 0 1 1 1 2 0", getProperty("vaccinepriorities"));
		assertEquals("0.089 0.212 0.212 0.212 0", getProperty("highriskfraction"));
		assertEquals("0 0 0.02771 0.02069 0", getProperty("pregnantfraction"));
		assertEquals("0.07", getProperty("essentialfraction"));
		// this delay seems to be funky! It changes depening on what the other
		// control measures are...
//		assertTrue(
//				"expected value of 0 or 2, but value was "
//						+ getProperty("responsedelay"),
//				getProperty("responsedelay").equals("0")
//						|| getProperty("responsedelay").equals("2"));
	}

	@Override
	protected void checkSchoolClosureControlMeasure(boolean allSchools,
			boolean fixedStartTime) {
		if (allSchools) {
			assertEquals("all", getProperty("schoolclosurepolicy"));
			assertEquals("56", getProperty("schoolclosuredays"));
			assertEquals("2", getProperty("responsedelay"));
		} else {
			assertNull(getProperty("schoolclosurepolicy"));
		}
	}

	@Override
	protected void checkNamedControlMeasuresMeasures() {
		assertEquals("0.65", getProperty("isolation"));
		assertEquals("0.75", getProperty("quarantine"));
        assertEquals("0.85", getProperty("liberalleave"));
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

	public void testAllSchoolClosureFixedStartTime() {
		runSchoolClosureControlMeasure_AllSchools_FixedStartTimeTest();
	}

	public void testAllSchoolClosureReactiveStartTime() {
		runSchoolClosureControlMeasure_AllSchools_ReactiveStartTimeTest();
	}

	public void testVaccAvAllScFixedVoluntaryLibralCombo() {
		runVaccAvAllScFixedVoluntaryLibralComboTest();
	}

	public void testVaccAvAllScReactiveVoluntaryLibralCombo() {
		runVaccAvAllScReactiveVoluntaryLibralComboTest();
	}
}

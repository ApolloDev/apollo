package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;
import edu.pitt.apollo.examples.runsimulationmessages.RunSimulationMessageFactory;
import edu.pitt.apollo.examples.runsimulationmessages.AbstractRunSimulationMessageBuilder.ControlMeasureTypeEnum;

import java.io.File;
import java.util.Set;

public class TestSeir extends AbstractSimulatorTest {

	private static final String RES_DIR = "./src/test/resources/seir";
	private static final String OUTPUT_DIR = RES_DIR + File.separator + "output";
	private static final String HTML_FILE_NAME = OUTPUT_DIR + File.separator
			+ "verbose.html";
	private static final String TRANSLATION_FILE = RES_DIR + File.separator
			+ "seir_translation_4.0.csv";
	private static final String JAVASCRIPT_FILE = RES_DIR + File.separator
			+ "seir_javascript_4.0.js";
	private static final String CONFIG_FILE = OUTPUT_DIR + File.separator + "config.txt";

	public TestSeir() {
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
		assertEquals("150", getProperty("run_length"));
		assertEquals("0.33", getProperty("asymptomatic_fraction"));
		assertEquals("1.3", getProperty("r0"));
		assertEquals("2", getProperty("latent_period"));
		assertEquals("6", getProperty("infectious_period"));
		assertEquals("9313830.51695,52824.094900000004,59206.188149999994,392744.2",
				getProperty("init_SEIR_counts"));
		checkNoDuplicateLinesInFile();
		// checkAtLeastOneCommentExists();

	}

	@Override
	protected void checkControlMeasuresDontExist(Set<ControlMeasureTypeEnum> controlMeasureTypes) {
		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.NO_VACC_CM)) {
			assertNull(getProperty("vaccination_efficacy"));
			assertNull(getProperty("vaccination_efficacy_delay"));
			assertNull(getProperty("vaccination_compliance"));
			assertNull(getProperty("vaccination_supply_schedule"));
			assertNull(getProperty("vaccination_admin_schedule"));
		}

		if (controlMeasureTypes.contains(ControlMeasureTypeEnum.NO_AV_CM)) {
			assertNull(getProperty("antiviral_treatment_efficacy"));
			assertNull(getProperty("antiviral_treatment_compliance"));
			assertNull(getProperty("antiviral_supply_schedule"));
			assertNull(getProperty("antiviral_admin_schedule"));
		}

		/*		if (scCm == DefScCm.NO_SC_CM) {
		 }*/
	}

	@Override
	protected void checkAvControlMeasure() {
		assertEquals("0.7", getProperty("antiviral_treatment_efficacy"));
		assertEquals("0.2", getProperty("antiviral_treatment_compliance"));
		assertEquals("3000,3001,3002,3003,3004,3005,3006,3007,3008,3009,"
				+ "3010,3011,3012,3013,3014,3015,3016,3017,3018,3019,3020,"
				+ "3021,3022,3023,3024,3025,3026,3027,3028,3029,3030,3031,"
				+ "3032,3033,3034,3035,3036,3037,3038,3039,3040,3041,3042,"
				+ "3043,3044,3045,3046,3047,3048,3049,3050,3051,3052,3053,"
				+ "3054,3055,3056,3057,3058,3059,3060,3061,3062,3063,3064,"
				+ "3065,3066,3067,3068,3069,3070,3071,3072,3073,3074,3075,"
				+ "3076,3077,3078,3079,3080,3081,3082,3083,3084,3085,3086,"
				+ "3087,3088,3089,3090,3091,3092,3093,3094,3095,3096,3097,"
				+ "3098,3099,3100,3101,3102,3103,3104,3105,3106,3107,3108,"
				+ "3109,3110,3111,3112,3113,3114,3115,3116,3117,3118,3119,"
				+ "3120,3121,3122,3123,3124,3125,3126,3127,3128,3129,3130,"
				+ "3131,3132,3133,3134,3135,3136,3137,3138,3139,3140,3141,"
				+ "3142,3143,3144,3145,3146,3147,3148,3149", getProperty("antiviral_supply_schedule"));
		assertEquals("3000,3001,3002,3003,3004,3005,3006,3007,3008,3009,3010,"
				+ "3011,3012,3013,3014,3015,3016,3017,3018,3019,3020,3021,"
				+ "3022,3023,3024,3025,3026,3027,3028,3029,3030,3031,3032,"
				+ "3033,3034,3035,3036,3037,3038,3039,3040,3041,3042,3043,"
				+ "3044,3045,3046,3047,3048,3049,3050,3051,3052,3053,3054,"
				+ "3055,3056,3057,3058,3059,3060,3061,3062,3063,3064,3065,"
				+ "3066,3067,3068,3069,3070,3071,3072,3073,3074,3075,3076,"
				+ "3077,3078,3079,3080,3081,3082,3083,3084,3085,3086,3087,"
				+ "3088,3089,3090,3091,3092,3093,3094,3095,3096,3097,3098,"
				+ "3099,3100,3101,3102,3103,3104,3105,3106,3107,3108,3109,"
				+ "3110,3111,3112,3113,3114,3115,3116,3117,3118,3119,3120,"
				+ "3121,3122,3123,3124,3125,3126,3127,3128,3129,3130,3131,"
				+ "3132,3133,3134,3135,3136,3137,3138,3139,3140,3141,3142,"
				+ "3143,3144,3145,3146,3147,3148,3149", getProperty("antiviral_admin_schedule"));
	}

	@Override
	protected void checkVaccControlMeasure() {
		assertEquals("0.7", getProperty("vaccination_efficacy"));
		assertEquals("14", getProperty("vaccination_efficacy_delay"));
		assertEquals("0.9", getProperty("vaccination_compliance"));
		assertEquals("3000,3001,3002,3003,3004,3005,3006,3007,3008,3009,3010,3011,"
				+ "3012,3013,3014,3015,3016,3017,3018,3019,3020,3021,3022,3023,3024,"
				+ "3025,3026,3027,3028,3029,3030,3031,3032,3033,3034,3035,3036,3037,"
				+ "3038,3039,3040,3041,3042,3043,3044,3045,3046,3047,3048,3049,3050,"
				+ "3051,3052,3053,3054,3055,3056,3057,3058,3059,3060,3061,3062,3063,"
				+ "3064,3065,3066,3067,3068,3069,3070,3071,3072,3073,3074,3075,3076,"
				+ "3077,3078,3079,3080,3081,3082,3083,3084,3085,3086,3087,3088,3089,"
				+ "3090,3091,3092,3093,3094,3095,3096,3097,3098,3099,3100,3101,3102,"
				+ "3103,3104,3105,3106,3107,3108,3109,3110,3111,3112,3113,3114,3115,"
				+ "3116,3117,3118,3119,3120,3121,3122,3123,3124,3125,3126,3127,3128,"
				+ "3129,3130,3131,3132,3133,3134,3135,3136,3137,3138,3139,3140,3141,"
				+ "3142,3143,3144,3145,3146,3147,3148,3149", getProperty("vaccination_supply_schedule"));
		assertEquals("50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,50000,"
                + "50000,50000,50000,50000,50000,50000,50000,50000", getProperty("vaccination_admin_schedule"));
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
}

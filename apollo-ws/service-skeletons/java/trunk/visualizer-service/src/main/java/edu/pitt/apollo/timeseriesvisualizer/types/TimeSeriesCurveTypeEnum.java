package edu.pitt.apollo.timeseriesvisualizer.types;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Feb 27, 2014
 Time: 3:35:57 PM
 Class: TimeSeriesCurveTypeEnum
 IDE: NetBeans 6.9.1
 */
public enum TimeSeriesCurveTypeEnum {

	SUSCEPTIBLE("susceptible"),
	LATENT("latent"),
	INFECTIOUS("infectious"),
	RECOVERED("recovered"),
	NEWLY_LATENT("newly latent"),
	NEWLY_DECEASED("newly deceased"),
	PROPHYLACTICS_GIVEN("prophylactics given"),
	ASYMPTOMATIC("asymptomatic"),
	SYMPTOMATIC("symptomatic"),
	FULMINANT("fulminant"),
	DEAD("dead"),
	SUSCEPTIBLE_MOSQUITO("mosquito susceptible"),
	LATENT_MOSQUITO("mosquito latent"),
	INFECTIOUS_MOSQUITO("mosquito infectious"),
	NEWLY_LATENT_MOSQUITO("mosquito newly latent");

	public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_INFECTION_STATES_CHART = {SUSCEPTIBLE, LATENT, INFECTIOUS, RECOVERED,
		SUSCEPTIBLE_MOSQUITO, LATENT_MOSQUITO, INFECTIOUS_MOSQUITO};
	public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_INCIDENCE_CHART = {NEWLY_LATENT, NEWLY_LATENT_MOSQUITO};
	public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_NEWLY_DECEASED_CHART = {NEWLY_DECEASED};
	public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_TREATMENT_COUNTS_CHART = {PROPHYLACTICS_GIVEN};
	public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_DISEASE_STATES_CHART = {ASYMPTOMATIC, SYMPTOMATIC, FULMINANT, DEAD};

	private final String value;

	private TimeSeriesCurveTypeEnum(String s) {
		value = s;
	}

	public String getValue() {
		return value;
	}
}

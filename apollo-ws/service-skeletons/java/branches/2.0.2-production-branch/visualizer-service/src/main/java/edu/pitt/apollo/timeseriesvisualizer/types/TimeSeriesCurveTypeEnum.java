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
    EXPOSED("exposed"),
    INFECTIOUS("infectious"),
    RECOVERED("recovered"),
    NEWLY_EXPOSED("newly exposed"),
	NEWLY_DECEASED("newly deceased"),
	PROPHYLACTICS_GIVEN("prophylactics given");
    
    public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_PREVALENCE_CHART = {SUSCEPTIBLE, EXPOSED, INFECTIOUS, RECOVERED};
    public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_RATES_CHART = {NEWLY_EXPOSED, NEWLY_DECEASED};
	public static final TimeSeriesCurveTypeEnum[] CURVE_TYPES_FOR_TREATMENT_COUNTS_CHART = {PROPHYLACTICS_GIVEN};
	
    private final String value;

    private TimeSeriesCurveTypeEnum(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}

package edu.pitt.apollo.timeseriesvisualizer.types;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 27, 2014
 * Time: 3:35:57 PM
 * Class: InfectionStateEnum
 * IDE: NetBeans 6.9.1
 */
public enum InfectionStateEnum {

    SUSCEPTIBLE("susceptible"),
    EXPOSED("exposed"),
    INFECTIOUS("infectious"),
    RECOVERED("recovered"),
    NEWLY_EXPOSED("newly exposed");
    
    public static final InfectionStateEnum[] PREVALENCE_INFECTION_STATES = {SUSCEPTIBLE, EXPOSED, INFECTIOUS, RECOVERED};
    public static final InfectionStateEnum[] INCIDENCE_INFECTION_STATES = {NEWLY_EXPOSED};
    
    private String value;

    private InfectionStateEnum(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}

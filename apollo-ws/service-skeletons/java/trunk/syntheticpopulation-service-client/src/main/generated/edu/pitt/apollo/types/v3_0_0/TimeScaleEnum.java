
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimeScaleEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TimeScaleEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="simulatorTimeScale"/>
 *     &lt;enumeration value="decsisionTimeScale"/>
 *     &lt;enumeration value="controlStrategyTimeScale"/>
 *     &lt;enumeration value="individualTreatmentTimeScale"/>
 *     &lt;enumeration value="epidemicTimeScale"/>
 *     &lt;enumeration value="gregorianCalendar"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TimeScaleEnum")
@XmlEnum
public enum TimeScaleEnum {

    @XmlEnumValue("simulatorTimeScale")
    SIMULATOR_TIME_SCALE("simulatorTimeScale"),
    @XmlEnumValue("decsisionTimeScale")
    DECSISION_TIME_SCALE("decsisionTimeScale"),
    @XmlEnumValue("controlStrategyTimeScale")
    CONTROL_STRATEGY_TIME_SCALE("controlStrategyTimeScale"),
    @XmlEnumValue("individualTreatmentTimeScale")
    INDIVIDUAL_TREATMENT_TIME_SCALE("individualTreatmentTimeScale"),
    @XmlEnumValue("epidemicTimeScale")
    EPIDEMIC_TIME_SCALE("epidemicTimeScale"),
    @XmlEnumValue("gregorianCalendar")
    GREGORIAN_CALENDAR("gregorianCalendar");
    private final String value;

    TimeScaleEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TimeScaleEnum fromValue(String v) {
        for (TimeScaleEnum c: TimeScaleEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

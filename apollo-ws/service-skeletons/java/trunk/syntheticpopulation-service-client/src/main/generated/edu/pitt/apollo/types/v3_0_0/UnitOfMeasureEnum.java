
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitOfMeasureEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UnitOfMeasureEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="dollars"/>
 *     &lt;enumeration value="euros"/>
 *     &lt;enumeration value="weight_in_pounds"/>
 *     &lt;enumeration value="weight_in_kilograms"/>
 *     &lt;enumeration value="seconds"/>
 *     &lt;enumeration value="minutes"/>
 *     &lt;enumeration value="hours"/>
 *     &lt;enumeration value="days"/>
 *     &lt;enumeration value="weeks"/>
 *     &lt;enumeration value="months"/>
 *     &lt;enumeration value="years"/>
 *     &lt;enumeration value="percent"/>
 *     &lt;enumeration value="cases"/>
 *     &lt;enumeration value="meters"/>
 *     &lt;enumeration value="inches"/>
 *     &lt;enumeration value="miles"/>
 *     &lt;enumeration value="daily_dose"/>
 *     &lt;enumeration value="percent_of_population_vaccinated"/>
 *     &lt;enumeration value="visits"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UnitOfMeasureEnum")
@XmlEnum
public enum UnitOfMeasureEnum {

    @XmlEnumValue("dollars")
    DOLLARS("dollars"),
    @XmlEnumValue("euros")
    EUROS("euros"),
    @XmlEnumValue("weight_in_pounds")
    WEIGHT_IN_POUNDS("weight_in_pounds"),
    @XmlEnumValue("weight_in_kilograms")
    WEIGHT_IN_KILOGRAMS("weight_in_kilograms"),
    @XmlEnumValue("seconds")
    SECONDS("seconds"),
    @XmlEnumValue("minutes")
    MINUTES("minutes"),
    @XmlEnumValue("hours")
    HOURS("hours"),
    @XmlEnumValue("days")
    DAYS("days"),
    @XmlEnumValue("weeks")
    WEEKS("weeks"),
    @XmlEnumValue("months")
    MONTHS("months"),
    @XmlEnumValue("years")
    YEARS("years"),
    @XmlEnumValue("percent")
    PERCENT("percent"),
    @XmlEnumValue("cases")
    CASES("cases"),
    @XmlEnumValue("meters")
    METERS("meters"),
    @XmlEnumValue("inches")
    INCHES("inches"),
    @XmlEnumValue("miles")
    MILES("miles"),
    @XmlEnumValue("daily_dose")
    DAILY_DOSE("daily_dose"),
    @XmlEnumValue("percent_of_population_vaccinated")
    PERCENT_OF_POPULATION_VACCINATED("percent_of_population_vaccinated"),
    @XmlEnumValue("visits")
    VISITS("visits");
    private final String value;

    UnitOfMeasureEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UnitOfMeasureEnum fromValue(String v) {
        for (UnitOfMeasureEnum c: UnitOfMeasureEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

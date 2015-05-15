
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiseaseOutcomeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DiseaseOutcomeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="asymptomatic"/>
 *     &lt;enumeration value="symptomatic"/>
 *     &lt;enumeration value="severely symptomatic"/>
 *     &lt;enumeration value="fulminant"/>
 *     &lt;enumeration value="hospitalization"/>
 *     &lt;enumeration value="death"/>
 *     &lt;enumeration value="recovery"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DiseaseOutcomeEnum")
@XmlEnum
public enum DiseaseOutcomeEnum {

    @XmlEnumValue("asymptomatic")
    ASYMPTOMATIC("asymptomatic"),
    @XmlEnumValue("symptomatic")
    SYMPTOMATIC("symptomatic"),
    @XmlEnumValue("severely symptomatic")
    SEVERELY_SYMPTOMATIC("severely symptomatic"),
    @XmlEnumValue("fulminant")
    FULMINANT("fulminant"),
    @XmlEnumValue("hospitalization")
    HOSPITALIZATION("hospitalization"),
    @XmlEnumValue("death")
    DEATH("death"),
    @XmlEnumValue("recovery")
    RECOVERY("recovery");
    private final String value;

    DiseaseOutcomeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DiseaseOutcomeEnum fromValue(String v) {
        for (DiseaseOutcomeEnum c: DiseaseOutcomeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

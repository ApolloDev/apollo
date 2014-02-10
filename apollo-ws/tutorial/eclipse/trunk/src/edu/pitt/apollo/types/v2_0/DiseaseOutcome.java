
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiseaseOutcome.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DiseaseOutcome">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="asymptomatic"/>
 *     &lt;enumeration value="symptomatic"/>
 *     &lt;enumeration value="severely symptomatic"/>
 *     &lt;enumeration value="hospitalization"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DiseaseOutcome")
@XmlEnum
public enum DiseaseOutcome {

    @XmlEnumValue("asymptomatic")
    ASYMPTOMATIC("asymptomatic"),
    @XmlEnumValue("symptomatic")
    SYMPTOMATIC("symptomatic"),
    @XmlEnumValue("severely symptomatic")
    SEVERELY_SYMPTOMATIC("severely symptomatic"),
    @XmlEnumValue("hospitalization")
    HOSPITALIZATION("hospitalization");
    private final String value;

    DiseaseOutcome(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DiseaseOutcome fromValue(String v) {
        for (DiseaseOutcome c: DiseaseOutcome.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

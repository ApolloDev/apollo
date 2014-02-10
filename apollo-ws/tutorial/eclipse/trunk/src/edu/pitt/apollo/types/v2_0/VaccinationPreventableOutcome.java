
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VaccinationPreventableOutcome.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VaccinationPreventableOutcome">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="infection"/>
 *     &lt;enumeration value="infectiousness"/>
 *     &lt;enumeration value="sickness requiring medical attention"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VaccinationPreventableOutcome")
@XmlEnum
public enum VaccinationPreventableOutcome {

    @XmlEnumValue("infection")
    INFECTION("infection"),
    @XmlEnumValue("infectiousness")
    INFECTIOUSNESS("infectiousness"),
    @XmlEnumValue("sickness requiring medical attention")
    SICKNESS_REQUIRING_MEDICAL_ATTENTION("sickness requiring medical attention");
    private final String value;

    VaccinationPreventableOutcome(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VaccinationPreventableOutcome fromValue(String v) {
        for (VaccinationPreventableOutcome c: VaccinationPreventableOutcome.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TreatmentPreventableOutcomeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TreatmentPreventableOutcomeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="infection"/>
 *     &lt;enumeration value="infectiousness"/>
 *     &lt;enumeration value="sickness requiring medical attention"/>
 *     &lt;enumeration value="sickness requiring intensive care"/>
 *     &lt;enumeration value="death"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TreatmentPreventableOutcomeEnum")
@XmlEnum
public enum TreatmentPreventableOutcomeEnum {

    @XmlEnumValue("infection")
    INFECTION("infection"),
    @XmlEnumValue("infectiousness")
    INFECTIOUSNESS("infectiousness"),
    @XmlEnumValue("sickness requiring medical attention")
    SICKNESS_REQUIRING_MEDICAL_ATTENTION("sickness requiring medical attention"),
    @XmlEnumValue("sickness requiring intensive care")
    SICKNESS_REQUIRING_INTENSIVE_CARE("sickness requiring intensive care"),
    @XmlEnumValue("death")
    DEATH("death");
    private final String value;

    TreatmentPreventableOutcomeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TreatmentPreventableOutcomeEnum fromValue(String v) {
        for (TreatmentPreventableOutcomeEnum c: TreatmentPreventableOutcomeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

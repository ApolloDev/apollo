
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndividualTreatmentEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IndividualTreatmentEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="vaccination"/>
 *     &lt;enumeration value="antiviral_treatment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IndividualTreatmentEnum")
@XmlEnum
public enum IndividualTreatmentEnum {

    @XmlEnumValue("vaccination")
    VACCINATION("vaccination"),
    @XmlEnumValue("antiviral_treatment")
    ANTIVIRAL_TREATMENT("antiviral_treatment");
    private final String value;

    IndividualTreatmentEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IndividualTreatmentEnum fromValue(String v) {
        for (IndividualTreatmentEnum c: IndividualTreatmentEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TreatmentStateEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TreatmentStateEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="treated"/>
 *     &lt;enumeration value="not_treated"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TreatmentStateEnum")
@XmlEnum
public enum TreatmentStateEnum {

    @XmlEnumValue("treated")
    TREATED("treated"),
    @XmlEnumValue("not_treated")
    NOT_TREATED("not_treated");
    private final String value;

    TreatmentStateEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TreatmentStateEnum fromValue(String v) {
        for (TreatmentStateEnum c: TreatmentStateEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

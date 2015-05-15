
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OccupationEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OccupationEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="storekeeper"/>
 *     &lt;enumeration value="merchant"/>
 *     &lt;enumeration value="nurse"/>
 *     &lt;enumeration value="hospitalMessenger"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OccupationEnum")
@XmlEnum
public enum OccupationEnum {

    @XmlEnumValue("storekeeper")
    STOREKEEPER("storekeeper"),
    @XmlEnumValue("merchant")
    MERCHANT("merchant"),
    @XmlEnumValue("nurse")
    NURSE("nurse"),
    @XmlEnumValue("hospitalMessenger")
    HOSPITAL_MESSENGER("hospitalMessenger");
    private final String value;

    OccupationEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OccupationEnum fromValue(String v) {
        for (OccupationEnum c: OccupationEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacilityEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FacilityEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="preschool"/>
 *     &lt;enumeration value="primarySchool"/>
 *     &lt;enumeration value="secondarySchool"/>
 *     &lt;enumeration value="workplace"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FacilityEnum")
@XmlEnum
public enum FacilityEnum {

    @XmlEnumValue("preschool")
    PRESCHOOL("preschool"),
    @XmlEnumValue("primarySchool")
    PRIMARY_SCHOOL("primarySchool"),
    @XmlEnumValue("secondarySchool")
    SECONDARY_SCHOOL("secondarySchool"),
    @XmlEnumValue("workplace")
    WORKPLACE("workplace");
    private final String value;

    FacilityEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FacilityEnum fromValue(String v) {
        for (FacilityEnum c: FacilityEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

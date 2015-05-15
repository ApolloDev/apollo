
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TargetPopulationEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TargetPopulationEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="all"/>
 *     &lt;enumeration value="members_of_the_same_household"/>
 *     &lt;enumeration value="newly_sick"/>
 *     &lt;enumeration value="household_member_of_infant"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TargetPopulationEnum")
@XmlEnum
public enum TargetPopulationEnum {

    @XmlEnumValue("all")
    ALL("all"),
    @XmlEnumValue("members_of_the_same_household")
    MEMBERS_OF_THE_SAME_HOUSEHOLD("members_of_the_same_household"),
    @XmlEnumValue("newly_sick")
    NEWLY_SICK("newly_sick"),
    @XmlEnumValue("household_member_of_infant")
    HOUSEHOLD_MEMBER_OF_INFANT("household_member_of_infant");
    private final String value;

    TargetPopulationEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TargetPopulationEnum fromValue(String v) {
        for (TargetPopulationEnum c: TargetPopulationEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

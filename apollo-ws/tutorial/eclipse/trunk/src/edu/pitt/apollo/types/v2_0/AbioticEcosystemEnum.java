
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AbioticEcosystemEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AbioticEcosystemEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="preschool"/>
 *     &lt;enumeration value="primarySchool"/>
 *     &lt;enumeration value="secondarySchool"/>
 *     &lt;enumeration value="workplace"/>
 *     &lt;enumeration value="pigFarm"/>
 *     &lt;enumeration value="chickenFarm"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AbioticEcosystemEnum")
@XmlEnum
public enum AbioticEcosystemEnum {

    @XmlEnumValue("preschool")
    PRESCHOOL("preschool"),
    @XmlEnumValue("primarySchool")
    PRIMARY_SCHOOL("primarySchool"),
    @XmlEnumValue("secondarySchool")
    SECONDARY_SCHOOL("secondarySchool"),
    @XmlEnumValue("workplace")
    WORKPLACE("workplace"),
    @XmlEnumValue("pigFarm")
    PIG_FARM("pigFarm"),
    @XmlEnumValue("chickenFarm")
    CHICKEN_FARM("chickenFarm");
    private final String value;

    AbioticEcosystemEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AbioticEcosystemEnum fromValue(String v) {
        for (AbioticEcosystemEnum c: AbioticEcosystemEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

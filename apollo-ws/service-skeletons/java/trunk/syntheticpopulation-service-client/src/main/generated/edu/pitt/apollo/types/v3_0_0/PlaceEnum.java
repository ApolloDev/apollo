
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlaceEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PlaceEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="preschool"/>
 *     &lt;enumeration value="primarySchool"/>
 *     &lt;enumeration value="secondarySchool"/>
 *     &lt;enumeration value="allSchools"/>
 *     &lt;enumeration value="allSchoolsOfOneLevelInOneTract"/>
 *     &lt;enumeration value="workplace"/>
 *     &lt;enumeration value="nzaraCottonFactory"/>
 *     &lt;enumeration value="home"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PlaceEnum")
@XmlEnum
public enum PlaceEnum {

    @XmlEnumValue("preschool")
    PRESCHOOL("preschool"),
    @XmlEnumValue("primarySchool")
    PRIMARY_SCHOOL("primarySchool"),
    @XmlEnumValue("secondarySchool")
    SECONDARY_SCHOOL("secondarySchool"),
    @XmlEnumValue("allSchools")
    ALL_SCHOOLS("allSchools"),
    @XmlEnumValue("allSchoolsOfOneLevelInOneTract")
    ALL_SCHOOLS_OF_ONE_LEVEL_IN_ONE_TRACT("allSchoolsOfOneLevelInOneTract"),
    @XmlEnumValue("workplace")
    WORKPLACE("workplace"),
    @XmlEnumValue("nzaraCottonFactory")
    NZARA_COTTON_FACTORY("nzaraCottonFactory"),
    @XmlEnumValue("home")
    HOME("home");
    private final String value;

    PlaceEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PlaceEnum fromValue(String v) {
        for (PlaceEnum c: PlaceEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

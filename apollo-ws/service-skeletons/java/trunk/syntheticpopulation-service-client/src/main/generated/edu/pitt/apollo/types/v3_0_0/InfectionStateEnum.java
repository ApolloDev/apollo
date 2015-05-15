
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfectionStateEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InfectionStateEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="susceptible"/>
 *     &lt;enumeration value="greaterThanOrEqualToId50Exposure"/>
 *     &lt;enumeration value="latent"/>
 *     &lt;enumeration value="infectious"/>
 *     &lt;enumeration value="recovered"/>
 *     &lt;enumeration value="immuneAndActsLikeImmune"/>
 *     &lt;enumeration value="immuneButActsLikeNotImmune"/>
 *     &lt;enumeration value="newly_sick"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "InfectionStateEnum")
@XmlEnum
public enum InfectionStateEnum {

    @XmlEnumValue("susceptible")
    SUSCEPTIBLE("susceptible"),
    @XmlEnumValue("greaterThanOrEqualToId50Exposure")
    GREATER_THAN_OR_EQUAL_TO_ID_50_EXPOSURE("greaterThanOrEqualToId50Exposure"),
    @XmlEnumValue("latent")
    LATENT("latent"),
    @XmlEnumValue("infectious")
    INFECTIOUS("infectious"),
    @XmlEnumValue("recovered")
    RECOVERED("recovered"),
    @XmlEnumValue("immuneAndActsLikeImmune")
    IMMUNE_AND_ACTS_LIKE_IMMUNE("immuneAndActsLikeImmune"),
    @XmlEnumValue("immuneButActsLikeNotImmune")
    IMMUNE_BUT_ACTS_LIKE_NOT_IMMUNE("immuneButActsLikeNotImmune"),
    @XmlEnumValue("newly_sick")
    NEWLY_SICK("newly_sick");
    private final String value;

    InfectionStateEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InfectionStateEnum fromValue(String v) {
        for (InfectionStateEnum c: InfectionStateEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactDefinitionEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContactDefinitionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="primaryContact"/>
 *     &lt;enumeration value="secondaryContact"/>
 *     &lt;enumeration value="infectedMosquitoBitesSusceptibleHuman"/>
 *     &lt;enumeration value="susceptibleMosquitoBitesInfectiousHuman"/>
 *     &lt;enumeration value="parenteralInjection"/>
 *     &lt;enumeration value="physicalContact"/>
 *     &lt;enumeration value="contactWithBodyFluids"/>
 *     &lt;enumeration value="assistedChildbirthOfInfectedMother"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContactDefinitionEnum")
@XmlEnum
public enum ContactDefinitionEnum {

    @XmlEnumValue("primaryContact")
    PRIMARY_CONTACT("primaryContact"),
    @XmlEnumValue("secondaryContact")
    SECONDARY_CONTACT("secondaryContact"),
    @XmlEnumValue("infectedMosquitoBitesSusceptibleHuman")
    INFECTED_MOSQUITO_BITES_SUSCEPTIBLE_HUMAN("infectedMosquitoBitesSusceptibleHuman"),
    @XmlEnumValue("susceptibleMosquitoBitesInfectiousHuman")
    SUSCEPTIBLE_MOSQUITO_BITES_INFECTIOUS_HUMAN("susceptibleMosquitoBitesInfectiousHuman"),
    @XmlEnumValue("parenteralInjection")
    PARENTERAL_INJECTION("parenteralInjection"),
    @XmlEnumValue("physicalContact")
    PHYSICAL_CONTACT("physicalContact"),
    @XmlEnumValue("contactWithBodyFluids")
    CONTACT_WITH_BODY_FLUIDS("contactWithBodyFluids"),
    @XmlEnumValue("assistedChildbirthOfInfectedMother")
    ASSISTED_CHILDBIRTH_OF_INFECTED_MOTHER("assistedChildbirthOfInfectedMother");
    private final String value;

    ContactDefinitionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContactDefinitionEnum fromValue(String v) {
        for (ContactDefinitionEnum c: ContactDefinitionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

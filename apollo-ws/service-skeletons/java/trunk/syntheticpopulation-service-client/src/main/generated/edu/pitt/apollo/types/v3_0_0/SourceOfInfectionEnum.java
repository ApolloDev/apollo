
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SourceOfInfectionEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SourceOfInfectionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="syringe"/>
 *     &lt;enumeration value="infectiousHost"/>
 *     &lt;enumeration value="eitherSyringeOrInfectiousHost"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SourceOfInfectionEnum")
@XmlEnum
public enum SourceOfInfectionEnum {

    @XmlEnumValue("syringe")
    SYRINGE("syringe"),
    @XmlEnumValue("infectiousHost")
    INFECTIOUS_HOST("infectiousHost"),
    @XmlEnumValue("eitherSyringeOrInfectiousHost")
    EITHER_SYRINGE_OR_INFECTIOUS_HOST("eitherSyringeOrInfectiousHost");
    private final String value;

    SourceOfInfectionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SourceOfInfectionEnum fromValue(String v) {
        for (SourceOfInfectionEnum c: SourceOfInfectionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

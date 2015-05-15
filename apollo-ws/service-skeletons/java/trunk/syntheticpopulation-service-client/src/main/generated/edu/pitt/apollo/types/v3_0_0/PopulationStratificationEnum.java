
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PopulationStratificationEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PopulationStratificationEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="essential_workforce"/>
 *     &lt;enumeration value="pregnant"/>
 *     &lt;enumeration value="high_risk"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PopulationStratificationEnum")
@XmlEnum
public enum PopulationStratificationEnum {

    @XmlEnumValue("essential_workforce")
    ESSENTIAL_WORKFORCE("essential_workforce"),
    @XmlEnumValue("pregnant")
    PREGNANT("pregnant"),
    @XmlEnumValue("high_risk")
    HIGH_RISK("high_risk");
    private final String value;

    PopulationStratificationEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PopulationStratificationEnum fromValue(String v) {
        for (PopulationStratificationEnum c: PopulationStratificationEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

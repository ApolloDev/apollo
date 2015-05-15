
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NamedPrioritizationSchemeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="NamedPrioritizationSchemeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="treat_sick_only"/>
 *     &lt;enumeration value="ACIP"/>
 *     &lt;enumeration value="HHTAP"/>
 *     &lt;enumeration value="HHTAP100"/>
 *     &lt;enumeration value="none"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "NamedPrioritizationSchemeEnum")
@XmlEnum
public enum NamedPrioritizationSchemeEnum {

    @XmlEnumValue("treat_sick_only")
    TREAT_SICK_ONLY("treat_sick_only"),
    ACIP("ACIP"),
    HHTAP("HHTAP"),
    @XmlEnumValue("HHTAP100")
    HHTAP_100("HHTAP100"),
    @XmlEnumValue("none")
    NONE("none");
    private final String value;

    NamedPrioritizationSchemeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static NamedPrioritizationSchemeEnum fromValue(String v) {
        for (NamedPrioritizationSchemeEnum c: NamedPrioritizationSchemeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

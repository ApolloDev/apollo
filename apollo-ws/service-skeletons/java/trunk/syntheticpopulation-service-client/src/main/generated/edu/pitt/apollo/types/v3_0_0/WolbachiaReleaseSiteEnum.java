
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WolbachiaReleaseSiteEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="WolbachiaReleaseSiteEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="roads"/>
 *     &lt;enumeration value="ovi-sites"/>
 *     &lt;enumeration value="front-doors"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WolbachiaReleaseSiteEnum")
@XmlEnum
public enum WolbachiaReleaseSiteEnum {

    @XmlEnumValue("roads")
    ROADS("roads"),
    @XmlEnumValue("ovi-sites")
    OVI_SITES("ovi-sites"),
    @XmlEnumValue("front-doors")
    FRONT_DOORS("front-doors");
    private final String value;

    WolbachiaReleaseSiteEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WolbachiaReleaseSiteEnum fromValue(String v) {
        for (WolbachiaReleaseSiteEnum c: WolbachiaReleaseSiteEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

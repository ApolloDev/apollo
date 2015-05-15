
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitOfDistanceEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UnitOfDistanceEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="meters"/>
 *     &lt;enumeration value="inches"/>
 *     &lt;enumeration value="miles"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UnitOfDistanceEnum")
@XmlEnum
public enum UnitOfDistanceEnum {

    @XmlEnumValue("meters")
    METERS("meters"),
    @XmlEnumValue("inches")
    INCHES("inches"),
    @XmlEnumValue("miles")
    MILES("miles");
    private final String value;

    UnitOfDistanceEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UnitOfDistanceEnum fromValue(String v) {
        for (UnitOfDistanceEnum c: UnitOfDistanceEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

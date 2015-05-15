
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitOfTimeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UnitOfTimeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="year"/>
 *     &lt;enumeration value="month"/>
 *     &lt;enumeration value="day"/>
 *     &lt;enumeration value="hour"/>
 *     &lt;enumeration value="minute"/>
 *     &lt;enumeration value="second"/>
 *     &lt;enumeration value="millisecond"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UnitOfTimeEnum")
@XmlEnum
public enum UnitOfTimeEnum {

    @XmlEnumValue("year")
    YEAR("year"),
    @XmlEnumValue("month")
    MONTH("month"),
    @XmlEnumValue("day")
    DAY("day"),
    @XmlEnumValue("hour")
    HOUR("hour"),
    @XmlEnumValue("minute")
    MINUTE("minute"),
    @XmlEnumValue("second")
    SECOND("second"),
    @XmlEnumValue("millisecond")
    MILLISECOND("millisecond");
    private final String value;

    UnitOfTimeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UnitOfTimeEnum fromValue(String v) {
        for (UnitOfTimeEnum c: UnitOfTimeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

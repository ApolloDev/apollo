
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperatorEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperatorEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="less_than_or_equal"/>
 *     &lt;enumeration value="greater_than_or_equal"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperatorEnum")
@XmlEnum
public enum OperatorEnum {

    @XmlEnumValue("less_than_or_equal")
    LESS_THAN_OR_EQUAL("less_than_or_equal"),
    @XmlEnumValue("greater_than_or_equal")
    GREATER_THAN_OR_EQUAL("greater_than_or_equal");
    private final String value;

    OperatorEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OperatorEnum fromValue(String v) {
        for (OperatorEnum c: OperatorEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

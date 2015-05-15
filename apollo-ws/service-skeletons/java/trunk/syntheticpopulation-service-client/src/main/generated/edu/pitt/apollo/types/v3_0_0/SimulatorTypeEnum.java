
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SimulatorTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SimulatorTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="agent-based"/>
 *     &lt;enumeration value="compartmental"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SimulatorTypeEnum")
@XmlEnum
public enum SimulatorTypeEnum {

    @XmlEnumValue("agent-based")
    AGENT_BASED("agent-based"),
    @XmlEnumValue("compartmental")
    COMPARTMENTAL("compartmental");
    private final String value;

    SimulatorTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimulatorTypeEnum fromValue(String v) {
        for (SimulatorTypeEnum c: SimulatorTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

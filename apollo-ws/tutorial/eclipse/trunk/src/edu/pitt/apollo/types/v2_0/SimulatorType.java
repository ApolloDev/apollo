
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SimulatorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SimulatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="agent-based"/>
 *     &lt;enumeration value="compartmental"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SimulatorType")
@XmlEnum
public enum SimulatorType {

    @XmlEnumValue("agent-based")
    AGENT_BASED("agent-based"),
    @XmlEnumValue("compartmental")
    COMPARTMENTAL("compartmental");
    private final String value;

    SimulatorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SimulatorType fromValue(String v) {
        for (SimulatorType c: SimulatorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

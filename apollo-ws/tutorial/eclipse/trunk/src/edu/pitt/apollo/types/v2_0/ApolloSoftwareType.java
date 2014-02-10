
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApolloSoftwareType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApolloSoftwareType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="simulator"/>
 *     &lt;enumeration value="visualizer"/>
 *     &lt;enumeration value="syntheticPopulationGenerator"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ApolloSoftwareType")
@XmlEnum
public enum ApolloSoftwareType {

    @XmlEnumValue("simulator")
    SIMULATOR("simulator"),
    @XmlEnumValue("visualizer")
    VISUALIZER("visualizer"),
    @XmlEnumValue("syntheticPopulationGenerator")
    SYNTHETIC_POPULATION_GENERATOR("syntheticPopulationGenerator");
    private final String value;

    ApolloSoftwareType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApolloSoftwareType fromValue(String v) {
        for (ApolloSoftwareType c: ApolloSoftwareType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

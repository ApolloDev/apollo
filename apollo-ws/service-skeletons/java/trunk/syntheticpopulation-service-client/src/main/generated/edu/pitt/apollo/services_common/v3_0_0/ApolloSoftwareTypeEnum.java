
package edu.pitt.apollo.services_common.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApolloSoftwareTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApolloSoftwareTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="simulator"/>
 *     &lt;enumeration value="batchSimulator"/>
 *     &lt;enumeration value="visualizer"/>
 *     &lt;enumeration value="syntheticPopulationGenerator"/>
 *     &lt;enumeration value="translator"/>
 *     &lt;enumeration value="data"/>
 *     &lt;enumeration value="broker"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ApolloSoftwareTypeEnum")
@XmlEnum
public enum ApolloSoftwareTypeEnum {

    @XmlEnumValue("simulator")
    SIMULATOR("simulator"),
    @XmlEnumValue("batchSimulator")
    BATCH_SIMULATOR("batchSimulator"),
    @XmlEnumValue("visualizer")
    VISUALIZER("visualizer"),
    @XmlEnumValue("syntheticPopulationGenerator")
    SYNTHETIC_POPULATION_GENERATOR("syntheticPopulationGenerator"),
    @XmlEnumValue("translator")
    TRANSLATOR("translator"),
    @XmlEnumValue("data")
    DATA("data"),
    @XmlEnumValue("broker")
    BROKER("broker");
    private final String value;

    ApolloSoftwareTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApolloSoftwareTypeEnum fromValue(String v) {
        for (ApolloSoftwareTypeEnum c: ApolloSoftwareTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

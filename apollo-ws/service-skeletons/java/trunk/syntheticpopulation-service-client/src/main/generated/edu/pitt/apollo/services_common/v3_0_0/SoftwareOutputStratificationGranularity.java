
package edu.pitt.apollo.services_common.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoftwareOutputStratificationGranularity.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SoftwareOutputStratificationGranularity">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="byTopLevelLocationAndInfectionStateOverTime"/>
 *     &lt;enumeration value="allStratificationsAtTheFinestGranularity"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SoftwareOutputStratificationGranularity")
@XmlEnum
public enum SoftwareOutputStratificationGranularity {

    @XmlEnumValue("byTopLevelLocationAndInfectionStateOverTime")
    BY_TOP_LEVEL_LOCATION_AND_INFECTION_STATE_OVER_TIME("byTopLevelLocationAndInfectionStateOverTime"),
    @XmlEnumValue("allStratificationsAtTheFinestGranularity")
    ALL_STRATIFICATIONS_AT_THE_FINEST_GRANULARITY("allStratificationsAtTheFinestGranularity");
    private final String value;

    SoftwareOutputStratificationGranularity(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SoftwareOutputStratificationGranularity fromValue(String v) {
        for (SoftwareOutputStratificationGranularity c: SoftwareOutputStratificationGranularity.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

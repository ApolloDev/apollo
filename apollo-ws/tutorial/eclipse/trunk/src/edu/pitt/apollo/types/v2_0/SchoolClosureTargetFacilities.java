
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SchoolClosureTargetFacilities.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SchoolClosureTargetFacilities">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="all"/>
 *     &lt;enumeration value="individual"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SchoolClosureTargetFacilities")
@XmlEnum
public enum SchoolClosureTargetFacilities {

    @XmlEnumValue("all")
    ALL("all"),
    @XmlEnumValue("individual")
    INDIVIDUAL("individual");
    private final String value;

    SchoolClosureTargetFacilities(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SchoolClosureTargetFacilities fromValue(String v) {
        for (SchoolClosureTargetFacilities c: SchoolClosureTargetFacilities.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

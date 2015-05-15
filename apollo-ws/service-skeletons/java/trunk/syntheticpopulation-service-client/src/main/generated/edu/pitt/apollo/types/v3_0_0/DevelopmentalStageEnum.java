
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DevelopmentalStageEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DevelopmentalStageEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="egg"/>
 *     &lt;enumeration value="larval"/>
 *     &lt;enumeration value="pupal"/>
 *     &lt;enumeration value="larvalAndPupal"/>
 *     &lt;enumeration value="adultForm"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DevelopmentalStageEnum")
@XmlEnum
public enum DevelopmentalStageEnum {

    @XmlEnumValue("egg")
    EGG("egg"),
    @XmlEnumValue("larval")
    LARVAL("larval"),
    @XmlEnumValue("pupal")
    PUPAL("pupal"),
    @XmlEnumValue("larvalAndPupal")
    LARVAL_AND_PUPAL("larvalAndPupal"),
    @XmlEnumValue("adultForm")
    ADULT_FORM("adultForm");
    private final String value;

    DevelopmentalStageEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DevelopmentalStageEnum fromValue(String v) {
        for (DevelopmentalStageEnum c: DevelopmentalStageEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

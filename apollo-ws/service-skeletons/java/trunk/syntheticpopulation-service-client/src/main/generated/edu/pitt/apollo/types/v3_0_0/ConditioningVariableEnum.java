
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConditioningVariableEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConditioningVariableEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="biologicalSex"/>
 *     &lt;enumeration value="gender"/>
 *     &lt;enumeration value="ageRange"/>
 *     &lt;enumeration value="timeSpan"/>
 *     &lt;enumeration value="diseaseOutcome"/>
 *     &lt;enumeration value="sourceOfInfection"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConditioningVariableEnum")
@XmlEnum
public enum ConditioningVariableEnum {

    @XmlEnumValue("biologicalSex")
    BIOLOGICAL_SEX("biologicalSex"),
    @XmlEnumValue("gender")
    GENDER("gender"),
    @XmlEnumValue("ageRange")
    AGE_RANGE("ageRange"),
    @XmlEnumValue("timeSpan")
    TIME_SPAN("timeSpan"),
    @XmlEnumValue("diseaseOutcome")
    DISEASE_OUTCOME("diseaseOutcome"),
    @XmlEnumValue("sourceOfInfection")
    SOURCE_OF_INFECTION("sourceOfInfection");
    private final String value;

    ConditioningVariableEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConditioningVariableEnum fromValue(String v) {
        for (ConditioningVariableEnum c: ConditioningVariableEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntervalBoundaryDefinitionEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="IntervalBoundaryDefinitionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="timeOfFirstSymptom"/>
 *     &lt;enumeration value="timeOfRecovery"/>
 *     &lt;enumeration value="timeOfHospitalAdmission"/>
 *     &lt;enumeration value="timeOfHospitalDischarge"/>
 *     &lt;enumeration value="timeOfDeath"/>
 *     &lt;enumeration value="timeOfWhoNotification"/>
 *     &lt;enumeration value="timeOfCaseDetection"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "IntervalBoundaryDefinitionEnum")
@XmlEnum
public enum IntervalBoundaryDefinitionEnum {

    @XmlEnumValue("timeOfFirstSymptom")
    TIME_OF_FIRST_SYMPTOM("timeOfFirstSymptom"),
    @XmlEnumValue("timeOfRecovery")
    TIME_OF_RECOVERY("timeOfRecovery"),
    @XmlEnumValue("timeOfHospitalAdmission")
    TIME_OF_HOSPITAL_ADMISSION("timeOfHospitalAdmission"),
    @XmlEnumValue("timeOfHospitalDischarge")
    TIME_OF_HOSPITAL_DISCHARGE("timeOfHospitalDischarge"),
    @XmlEnumValue("timeOfDeath")
    TIME_OF_DEATH("timeOfDeath"),
    @XmlEnumValue("timeOfWhoNotification")
    TIME_OF_WHO_NOTIFICATION("timeOfWhoNotification"),
    @XmlEnumValue("timeOfCaseDetection")
    TIME_OF_CASE_DETECTION("timeOfCaseDetection");
    private final String value;

    IntervalBoundaryDefinitionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static IntervalBoundaryDefinitionEnum fromValue(String v) {
        for (IntervalBoundaryDefinitionEnum c: IntervalBoundaryDefinitionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

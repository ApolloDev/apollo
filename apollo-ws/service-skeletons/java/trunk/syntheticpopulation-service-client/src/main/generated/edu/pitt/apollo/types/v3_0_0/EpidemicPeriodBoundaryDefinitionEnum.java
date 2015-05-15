
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EpidemicPeriodBoundaryDefinitionEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EpidemicPeriodBoundaryDefinitionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="symptomOnsetFirstCase"/>
 *     &lt;enumeration value="dateOfFirstCaseFatality"/>
 *     &lt;enumeration value="firstLabCaseConfirmation"/>
 *     &lt;enumeration value="symptomOnsetLastCase"/>
 *     &lt;enumeration value="hospitalizationLastCase"/>
 *     &lt;enumeration value="deathOfLastCase"/>
 *     &lt;enumeration value="administrativeDeclaration"/>
 *     &lt;enumeration value="lastLabCaseConfirmation"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EpidemicPeriodBoundaryDefinitionEnum")
@XmlEnum
public enum EpidemicPeriodBoundaryDefinitionEnum {

    @XmlEnumValue("symptomOnsetFirstCase")
    SYMPTOM_ONSET_FIRST_CASE("symptomOnsetFirstCase"),
    @XmlEnumValue("dateOfFirstCaseFatality")
    DATE_OF_FIRST_CASE_FATALITY("dateOfFirstCaseFatality"),
    @XmlEnumValue("firstLabCaseConfirmation")
    FIRST_LAB_CASE_CONFIRMATION("firstLabCaseConfirmation"),
    @XmlEnumValue("symptomOnsetLastCase")
    SYMPTOM_ONSET_LAST_CASE("symptomOnsetLastCase"),
    @XmlEnumValue("hospitalizationLastCase")
    HOSPITALIZATION_LAST_CASE("hospitalizationLastCase"),
    @XmlEnumValue("deathOfLastCase")
    DEATH_OF_LAST_CASE("deathOfLastCase"),
    @XmlEnumValue("administrativeDeclaration")
    ADMINISTRATIVE_DECLARATION("administrativeDeclaration"),
    @XmlEnumValue("lastLabCaseConfirmation")
    LAST_LAB_CASE_CONFIRMATION("lastLabCaseConfirmation");
    private final String value;

    EpidemicPeriodBoundaryDefinitionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EpidemicPeriodBoundaryDefinitionEnum fromValue(String v) {
        for (EpidemicPeriodBoundaryDefinitionEnum c: EpidemicPeriodBoundaryDefinitionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApolloIndexableItemTypeEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ApolloIndexableItemTypeEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="InfectionType"/>
 *     &lt;enumeration value="InfectionAcquisitionType"/>
 *     &lt;enumeration value="InfectiousDiseaseType"/>
 *     &lt;enumeration value="InfectiousDiseaseControlStrategyType"/>
 *     &lt;enumeration value="InfectiousDiseaseScenarioType"/>
 *     &lt;enumeration value="ContaminationType"/>
 *     &lt;enumeration value="ContaminatedThingCensusType"/>
 *     &lt;enumeration value="PopulationAndEnvironmentCensusType"/>
 *     &lt;enumeration value="PopulationInfectionImmunityCensusType"/>
 *     &lt;enumeration value="PopulationTreatmentCensusType"/>
 *     &lt;enumeration value="VaccinationType"/>
 *     &lt;enumeration value="AntiviralTreatmentType"/>
 *     &lt;enumeration value="EpidemicType"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ApolloIndexableItemTypeEnum")
@XmlEnum
public enum ApolloIndexableItemTypeEnum {

    @XmlEnumValue("InfectionType")
    INFECTION_TYPE("InfectionType"),
    @XmlEnumValue("InfectionAcquisitionType")
    INFECTION_ACQUISITION_TYPE("InfectionAcquisitionType"),
    @XmlEnumValue("InfectiousDiseaseType")
    INFECTIOUS_DISEASE_TYPE("InfectiousDiseaseType"),
    @XmlEnumValue("InfectiousDiseaseControlStrategyType")
    INFECTIOUS_DISEASE_CONTROL_STRATEGY_TYPE("InfectiousDiseaseControlStrategyType"),
    @XmlEnumValue("InfectiousDiseaseScenarioType")
    INFECTIOUS_DISEASE_SCENARIO_TYPE("InfectiousDiseaseScenarioType"),
    @XmlEnumValue("ContaminationType")
    CONTAMINATION_TYPE("ContaminationType"),
    @XmlEnumValue("ContaminatedThingCensusType")
    CONTAMINATED_THING_CENSUS_TYPE("ContaminatedThingCensusType"),
    @XmlEnumValue("PopulationAndEnvironmentCensusType")
    POPULATION_AND_ENVIRONMENT_CENSUS_TYPE("PopulationAndEnvironmentCensusType"),
    @XmlEnumValue("PopulationInfectionImmunityCensusType")
    POPULATION_INFECTION_IMMUNITY_CENSUS_TYPE("PopulationInfectionImmunityCensusType"),
    @XmlEnumValue("PopulationTreatmentCensusType")
    POPULATION_TREATMENT_CENSUS_TYPE("PopulationTreatmentCensusType"),
    @XmlEnumValue("VaccinationType")
    VACCINATION_TYPE("VaccinationType"),
    @XmlEnumValue("AntiviralTreatmentType")
    ANTIVIRAL_TREATMENT_TYPE("AntiviralTreatmentType"),
    @XmlEnumValue("EpidemicType")
    EPIDEMIC_TYPE("EpidemicType");
    private final String value;

    ApolloIndexableItemTypeEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ApolloIndexableItemTypeEnum fromValue(String v) {
        for (ApolloIndexableItemTypeEnum c: ApolloIndexableItemTypeEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

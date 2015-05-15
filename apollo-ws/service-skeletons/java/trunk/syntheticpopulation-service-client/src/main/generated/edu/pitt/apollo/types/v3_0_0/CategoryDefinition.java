
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CategoryDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CategoryDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryDefinition")
@XmlSeeAlso({
    PlaceCategoryDefinition.class,
    RealTimeSpanCategoryDefinition.class,
    AgeRangeCategoryDefinition.class,
    TimeSpanCategoryDefinition.class,
    CaseDefinition.class,
    GenderCategoryDefinition.class,
    SourceOfInfectionCategoryDefinition.class,
    DiseaseOutcomeCategoryDefinition.class,
    RealDateSpanCategoryDefinition.class,
    RealTimePointCategoryDefinition.class
})
public class CategoryDefinition {


}

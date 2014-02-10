
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApolloIndexableItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApolloIndexableItem">
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
@XmlType(name = "ApolloIndexableItem")
@XmlSeeAlso({
    DecisionAnalysis.class,
    DecisionModel.class,
    InfectionAcquisition.class,
    Infection.class,
    InfectiousDiseaseScenario.class,
    InfectiousDisease.class,
    CensusData.class,
    Contamination.class,
    Census.class,
    InfectiousDiseaseControlStrategy.class,
    Treatment.class
})
public class ApolloIndexableItem {


}

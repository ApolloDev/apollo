
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for VectorControlStrategy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VectorControlStrategy">
 *   &lt;complexContent>
 *     &lt;extension base="{http://types.apollo.pitt.edu/v3_0_0/}InfectiousDiseaseControlStrategy">
 *       &lt;sequence>
 *         &lt;element name="vectorTaxonId" type="{http://types.apollo.pitt.edu/v3_0_0/}NcbiTaxonId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VectorControlStrategy", propOrder = {
    "vectorTaxonId"
})
@XmlSeeAlso({
    IndoorResidualSprayingVectorControlStrategy.class,
    LarvicideControlStrategy.class,
    ContainerReductionControlStrategy.class,
    WolbachiaControlStrategy.class
})
public class VectorControlStrategy
    extends InfectiousDiseaseControlStrategy
{

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String vectorTaxonId;

    /**
     * Gets the value of the vectorTaxonId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVectorTaxonId() {
        return vectorTaxonId;
    }

    /**
     * Sets the value of the vectorTaxonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVectorTaxonId(String value) {
        this.vectorTaxonId = value;
    }

}

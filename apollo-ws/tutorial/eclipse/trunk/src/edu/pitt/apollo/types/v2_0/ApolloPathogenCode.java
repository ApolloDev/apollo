
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ApolloPathogenCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApolloPathogenCode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ncbiTaxonId" type="{http://types.apollo.pitt.edu/v2_0/}NcbiTaxonId"/>
 *         &lt;element name="gisrnCladeName" type="{http://www.w3.org/2001/XMLSchema}token" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApolloPathogenCode", propOrder = {
    "ncbiTaxonId",
    "gisrnCladeName"
})
public class ApolloPathogenCode {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ncbiTaxonId;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String gisrnCladeName;

    /**
     * Gets the value of the ncbiTaxonId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNcbiTaxonId() {
        return ncbiTaxonId;
    }

    /**
     * Sets the value of the ncbiTaxonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNcbiTaxonId(String value) {
        this.ncbiTaxonId = value;
    }

    /**
     * Gets the value of the gisrnCladeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGisrnCladeName() {
        return gisrnCladeName;
    }

    /**
     * Sets the value of the gisrnCladeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGisrnCladeName(String value) {
        this.gisrnCladeName = value;
    }

}

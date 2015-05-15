
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Location complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Location">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="apolloLocationCode" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloLocationCode"/>
 *           &lt;element name="locationDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}LocationDefinition"/>
 *           &lt;element name="namedMultiGeometry" type="{http://types.apollo.pitt.edu/v3_0_0/}NamedMultiGeometry"/>
 *           &lt;element name="cartesianCircleLocationDefinition" type="{http://types.apollo.pitt.edu/v3_0_0/}CartesianCircleLocationDefinition"/>
 *         &lt;/choice>
 *         &lt;element name="textualDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Location", propOrder = {
    "apolloLocationCode",
    "locationDefinition",
    "namedMultiGeometry",
    "cartesianCircleLocationDefinition",
    "textualDescription"
})
public class Location {

    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String apolloLocationCode;
    protected LocationDefinition locationDefinition;
    protected NamedMultiGeometry namedMultiGeometry;
    protected CartesianCircleLocationDefinition cartesianCircleLocationDefinition;
    protected String textualDescription;

    /**
     * Gets the value of the apolloLocationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApolloLocationCode() {
        return apolloLocationCode;
    }

    /**
     * Sets the value of the apolloLocationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApolloLocationCode(String value) {
        this.apolloLocationCode = value;
    }

    /**
     * Gets the value of the locationDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link LocationDefinition }
     *     
     */
    public LocationDefinition getLocationDefinition() {
        return locationDefinition;
    }

    /**
     * Sets the value of the locationDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationDefinition }
     *     
     */
    public void setLocationDefinition(LocationDefinition value) {
        this.locationDefinition = value;
    }

    /**
     * Gets the value of the namedMultiGeometry property.
     * 
     * @return
     *     possible object is
     *     {@link NamedMultiGeometry }
     *     
     */
    public NamedMultiGeometry getNamedMultiGeometry() {
        return namedMultiGeometry;
    }

    /**
     * Sets the value of the namedMultiGeometry property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedMultiGeometry }
     *     
     */
    public void setNamedMultiGeometry(NamedMultiGeometry value) {
        this.namedMultiGeometry = value;
    }

    /**
     * Gets the value of the cartesianCircleLocationDefinition property.
     * 
     * @return
     *     possible object is
     *     {@link CartesianCircleLocationDefinition }
     *     
     */
    public CartesianCircleLocationDefinition getCartesianCircleLocationDefinition() {
        return cartesianCircleLocationDefinition;
    }

    /**
     * Sets the value of the cartesianCircleLocationDefinition property.
     * 
     * @param value
     *     allowed object is
     *     {@link CartesianCircleLocationDefinition }
     *     
     */
    public void setCartesianCircleLocationDefinition(CartesianCircleLocationDefinition value) {
        this.cartesianCircleLocationDefinition = value;
    }

    /**
     * Gets the value of the textualDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextualDescription() {
        return textualDescription;
    }

    /**
     * Sets the value of the textualDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextualDescription(String value) {
        this.textualDescription = value;
    }

}

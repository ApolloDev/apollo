
package edu.pitt.apollo.types.v3_0_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for NamedMultiGeometry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NamedMultiGeometry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="polygons" type="{http://types.apollo.pitt.edu/v3_0_0/}LocationPolygon" maxOccurs="unbounded"/>
 *         &lt;choice>
 *           &lt;element name="apolloLocationCode" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloLocationCode"/>
 *           &lt;element name="textualDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NamedMultiGeometry", propOrder = {
    "polygons",
    "apolloLocationCode",
    "textualDescription"
})
public class NamedMultiGeometry {

    @XmlElement(required = true)
    protected List<LocationPolygon> polygons;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String apolloLocationCode;
    protected String textualDescription;

    /**
     * Gets the value of the polygons property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the polygons property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPolygons().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocationPolygon }
     * 
     * 
     */
    public List<LocationPolygon> getPolygons() {
        if (polygons == null) {
            polygons = new ArrayList<LocationPolygon>();
        }
        return this.polygons;
    }

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


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
 * <p>Java class for LocationDefinition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationDefinition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="locationsIncluded" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloLocationCode" maxOccurs="unbounded"/>
 *         &lt;element name="locationsExcluded" type="{http://types.apollo.pitt.edu/v3_0_0/}ApolloLocationCode" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="multiGeometries" type="{http://types.apollo.pitt.edu/v3_0_0/}MultiGeometry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationDefinition", propOrder = {
    "locationsIncluded",
    "locationsExcluded",
    "multiGeometries"
})
public class LocationDefinition {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> locationsIncluded;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> locationsExcluded;
    protected List<MultiGeometry> multiGeometries;

    /**
     * Gets the value of the locationsIncluded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationsIncluded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationsIncluded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLocationsIncluded() {
        if (locationsIncluded == null) {
            locationsIncluded = new ArrayList<String>();
        }
        return this.locationsIncluded;
    }

    /**
     * Gets the value of the locationsExcluded property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationsExcluded property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationsExcluded().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getLocationsExcluded() {
        if (locationsExcluded == null) {
            locationsExcluded = new ArrayList<String>();
        }
        return this.locationsExcluded;
    }

    /**
     * Gets the value of the multiGeometries property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the multiGeometries property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMultiGeometries().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MultiGeometry }
     * 
     * 
     */
    public List<MultiGeometry> getMultiGeometries() {
        if (multiGeometries == null) {
            multiGeometries = new ArrayList<MultiGeometry>();
        }
        return this.multiGeometries;
    }

}


package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AaaDummyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AaaDummyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="elementIWantToScreenSnapWithoutArrows" type="{http://types.apollo.pitt.edu/v3_0_0/}TreatmentEfficacy"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AaaDummyType", propOrder = {
    "elementIWantToScreenSnapWithoutArrows"
})
public class AaaDummyType {

    @XmlElement(required = true)
    protected TreatmentEfficacy elementIWantToScreenSnapWithoutArrows;

    /**
     * Gets the value of the elementIWantToScreenSnapWithoutArrows property.
     * 
     * @return
     *     possible object is
     *     {@link TreatmentEfficacy }
     *     
     */
    public TreatmentEfficacy getElementIWantToScreenSnapWithoutArrows() {
        return elementIWantToScreenSnapWithoutArrows;
    }

    /**
     * Sets the value of the elementIWantToScreenSnapWithoutArrows property.
     * 
     * @param value
     *     allowed object is
     *     {@link TreatmentEfficacy }
     *     
     */
    public void setElementIWantToScreenSnapWithoutArrows(TreatmentEfficacy value) {
        this.elementIWantToScreenSnapWithoutArrows = value;
    }

}

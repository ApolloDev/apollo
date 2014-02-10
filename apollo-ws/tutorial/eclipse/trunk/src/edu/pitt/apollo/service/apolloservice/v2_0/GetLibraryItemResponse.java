
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.GetLibraryItemResult;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="getLibraryItemResult" type="{http://types.apollo.pitt.edu/v2_0/}GetLibraryItemResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getLibraryItemResult"
})
@XmlRootElement(name = "getLibraryItemResponse")
public class GetLibraryItemResponse {

    @XmlElement(required = true)
    protected GetLibraryItemResult getLibraryItemResult;

    /**
     * Gets the value of the getLibraryItemResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetLibraryItemResult }
     *     
     */
    public GetLibraryItemResult getGetLibraryItemResult() {
        return getLibraryItemResult;
    }

    /**
     * Sets the value of the getLibraryItemResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLibraryItemResult }
     *     
     */
    public void setGetLibraryItemResult(GetLibraryItemResult value) {
        this.getLibraryItemResult = value;
    }

}

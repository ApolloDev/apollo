
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.GetLibraryItemUuidsResult;


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
 *         &lt;element name="getLibraryItemsResult" type="{http://types.apollo.pitt.edu/v2_0/}GetLibraryItemUuidsResult"/>
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
    "getLibraryItemsResult"
})
@XmlRootElement(name = "getUuidsForLibraryItemsCreatedSinceDateTimeResponse")
public class GetUuidsForLibraryItemsCreatedSinceDateTimeResponse {

    @XmlElement(required = true)
    protected GetLibraryItemUuidsResult getLibraryItemsResult;

    /**
     * Gets the value of the getLibraryItemsResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetLibraryItemUuidsResult }
     *     
     */
    public GetLibraryItemUuidsResult getGetLibraryItemsResult() {
        return getLibraryItemsResult;
    }

    /**
     * Sets the value of the getLibraryItemsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetLibraryItemUuidsResult }
     *     
     */
    public void setGetLibraryItemsResult(GetLibraryItemUuidsResult value) {
        this.getLibraryItemsResult = value;
    }

}


package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetLibraryItemResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetLibraryItemResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="methodCallStatus" type="{http://types.apollo.pitt.edu/v2_0/}MethodCallStatus"/>
 *         &lt;element name="curatedLibraryItemContainer" type="{http://types.apollo.pitt.edu/v2_0/}CuratedLibraryItemContainer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetLibraryItemResult", propOrder = {
    "methodCallStatus",
    "curatedLibraryItemContainer"
})
public class GetLibraryItemResult {

    @XmlElement(required = true)
    protected MethodCallStatus methodCallStatus;
    @XmlElement(required = true)
    protected CuratedLibraryItemContainer curatedLibraryItemContainer;

    /**
     * Gets the value of the methodCallStatus property.
     * 
     * @return
     *     possible object is
     *     {@link MethodCallStatus }
     *     
     */
    public MethodCallStatus getMethodCallStatus() {
        return methodCallStatus;
    }

    /**
     * Sets the value of the methodCallStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link MethodCallStatus }
     *     
     */
    public void setMethodCallStatus(MethodCallStatus value) {
        this.methodCallStatus = value;
    }

    /**
     * Gets the value of the curatedLibraryItemContainer property.
     * 
     * @return
     *     possible object is
     *     {@link CuratedLibraryItemContainer }
     *     
     */
    public CuratedLibraryItemContainer getCuratedLibraryItemContainer() {
        return curatedLibraryItemContainer;
    }

    /**
     * Sets the value of the curatedLibraryItemContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CuratedLibraryItemContainer }
     *     
     */
    public void setCuratedLibraryItemContainer(CuratedLibraryItemContainer value) {
        this.curatedLibraryItemContainer = value;
    }

}

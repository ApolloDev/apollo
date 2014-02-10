
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CuratedLibraryItemContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CuratedLibraryItemContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="curatedLibraryItem" type="{http://types.apollo.pitt.edu/v2_0/}CatalogEntryForApolloLibraryItem"/>
 *         &lt;element name="apolloIndexableItem" type="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CuratedLibraryItemContainer", propOrder = {
    "curatedLibraryItem",
    "apolloIndexableItem"
})
public class CuratedLibraryItemContainer {

    @XmlElement(required = true)
    protected CatalogEntryForApolloLibraryItem curatedLibraryItem;
    @XmlElement(required = true)
    protected ApolloIndexableItem apolloIndexableItem;

    /**
     * Gets the value of the curatedLibraryItem property.
     * 
     * @return
     *     possible object is
     *     {@link CatalogEntryForApolloLibraryItem }
     *     
     */
    public CatalogEntryForApolloLibraryItem getCuratedLibraryItem() {
        return curatedLibraryItem;
    }

    /**
     * Sets the value of the curatedLibraryItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link CatalogEntryForApolloLibraryItem }
     *     
     */
    public void setCuratedLibraryItem(CatalogEntryForApolloLibraryItem value) {
        this.curatedLibraryItem = value;
    }

    /**
     * Gets the value of the apolloIndexableItem property.
     * 
     * @return
     *     possible object is
     *     {@link ApolloIndexableItem }
     *     
     */
    public ApolloIndexableItem getApolloIndexableItem() {
        return apolloIndexableItem;
    }

    /**
     * Sets the value of the apolloIndexableItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApolloIndexableItem }
     *     
     */
    public void setApolloIndexableItem(ApolloIndexableItem value) {
        this.apolloIndexableItem = value;
    }

}

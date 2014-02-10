
package edu.pitt.apollo.service.apolloservice.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import edu.pitt.apollo.types.v2_0.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0.Authentication;


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
 *         &lt;element name="authentication" type="{http://types.apollo.pitt.edu/v2_0/}Authentication"/>
 *         &lt;element name="apolloIndexableItem" type="{http://types.apollo.pitt.edu/v2_0/}ApolloIndexableItem"/>
 *         &lt;element name="itemDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="itemIndexingLabels" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded" minOccurs="0"/>
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
    "authentication",
    "apolloIndexableItem",
    "itemDescription",
    "itemSource",
    "itemType",
    "itemIndexingLabels"
})
@XmlRootElement(name = "addLibraryItem")
public class AddLibraryItem {

    @XmlElement(required = true)
    protected Authentication authentication;
    @XmlElement(required = true)
    protected ApolloIndexableItem apolloIndexableItem;
    @XmlElement(required = true)
    protected String itemDescription;
    @XmlElement(required = true)
    protected String itemSource;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String itemType;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> itemIndexingLabels;

    /**
     * Gets the value of the authentication property.
     * 
     * @return
     *     possible object is
     *     {@link Authentication }
     *     
     */
    public Authentication getAuthentication() {
        return authentication;
    }

    /**
     * Sets the value of the authentication property.
     * 
     * @param value
     *     allowed object is
     *     {@link Authentication }
     *     
     */
    public void setAuthentication(Authentication value) {
        this.authentication = value;
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

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the itemSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemSource() {
        return itemSource;
    }

    /**
     * Sets the value of the itemSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemSource(String value) {
        this.itemSource = value;
    }

    /**
     * Gets the value of the itemType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * Sets the value of the itemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemType(String value) {
        this.itemType = value;
    }

    /**
     * Gets the value of the itemIndexingLabels property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemIndexingLabels property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemIndexingLabels().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getItemIndexingLabels() {
        if (itemIndexingLabels == null) {
            itemIndexingLabels = new ArrayList<String>();
        }
        return this.itemIndexingLabels;
    }

}

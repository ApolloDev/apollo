
package edu.pitt.apollo.types.v2_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CatalogEntryForApolloLibraryItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CatalogEntryForApolloLibraryItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="itemDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="itemType" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="itemUuid" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="itemIndexingLabels" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="itemCreationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CatalogEntryForApolloLibraryItem", propOrder = {
    "itemDescription",
    "itemSource",
    "itemType",
    "itemUuid",
    "itemIndexingLabels",
    "itemCreationTime"
})
public class CatalogEntryForApolloLibraryItem {

    @XmlElement(required = true)
    protected String itemDescription;
    @XmlElement(required = true)
    protected String itemSource;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String itemType;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String itemUuid;
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> itemIndexingLabels;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar itemCreationTime;

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
     * Gets the value of the itemUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemUuid() {
        return itemUuid;
    }

    /**
     * Sets the value of the itemUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemUuid(String value) {
        this.itemUuid = value;
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

    /**
     * Gets the value of the itemCreationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getItemCreationTime() {
        return itemCreationTime;
    }

    /**
     * Sets the value of the itemCreationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setItemCreationTime(XMLGregorianCalendar value) {
        this.itemCreationTime = value;
    }

}

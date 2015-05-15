
package edu.pitt.apollo.services_common.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceRegistrationRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceRegistrationRecord">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services-common.apollo.pitt.edu/v3_0_0/}ServiceRecord">
 *       &lt;sequence>
 *         &lt;element name="authentication" type="{http://services-common.apollo.pitt.edu/v3_0_0/}Authentication"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceRegistrationRecord", propOrder = {
    "authentication"
})
public class ServiceRegistrationRecord
    extends ServiceRecord
{

    @XmlElement(required = true)
    protected Authentication authentication;

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

}

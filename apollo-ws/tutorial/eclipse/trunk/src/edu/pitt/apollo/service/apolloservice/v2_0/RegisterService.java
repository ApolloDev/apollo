
package edu.pitt.apollo.service.apolloservice.v2_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import edu.pitt.apollo.types.v2_0.ServiceRegistrationRecord;


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
 *         &lt;element name="serviceRegistrationRecord" type="{http://types.apollo.pitt.edu/v2_0/}ServiceRegistrationRecord"/>
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
    "serviceRegistrationRecord"
})
@XmlRootElement(name = "registerService")
public class RegisterService {

    @XmlElement(required = true)
    protected ServiceRegistrationRecord serviceRegistrationRecord;

    /**
     * Gets the value of the serviceRegistrationRecord property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceRegistrationRecord }
     *     
     */
    public ServiceRegistrationRecord getServiceRegistrationRecord() {
        return serviceRegistrationRecord;
    }

    /**
     * Sets the value of the serviceRegistrationRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceRegistrationRecord }
     *     
     */
    public void setServiceRegistrationRecord(ServiceRegistrationRecord value) {
        this.serviceRegistrationRecord = value;
    }

}

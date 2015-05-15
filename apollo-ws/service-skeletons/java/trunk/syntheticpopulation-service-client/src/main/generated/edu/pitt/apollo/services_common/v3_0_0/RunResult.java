
package edu.pitt.apollo.services_common.v3_0_0;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RunResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RunResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="runId" type="{http://services-common.apollo.pitt.edu/v3_0_0/}RunIdentification"/>
 *         &lt;element name="methodCallStatus" type="{http://services-common.apollo.pitt.edu/v3_0_0/}MethodCallStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RunResult", propOrder = {
    "runId",
    "methodCallStatus"
})
public class RunResult {

    @XmlElement(required = true)
    protected BigInteger runId;
    @XmlElement(required = true)
    protected MethodCallStatus methodCallStatus;

    /**
     * Gets the value of the runId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRunId() {
        return runId;
    }

    /**
     * Sets the value of the runId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRunId(BigInteger value) {
        this.runId = value;
    }

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

}


package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for SpatialKernelFunctionParameters complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpatialKernelFunctionParameters">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="a0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="a1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b0" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="b1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="c1" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cutoff" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpatialKernelFunctionParameters", propOrder = {
    "type",
    "a0",
    "a1",
    "b0",
    "b1",
    "c1",
    "cutoff"
})
public class SpatialKernelFunctionParameters {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String type;
    protected double a0;
    protected double a1;
    protected double b0;
    protected double b1;
    protected double c1;
    protected double cutoff;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the a0 property.
     * 
     */
    public double getA0() {
        return a0;
    }

    /**
     * Sets the value of the a0 property.
     * 
     */
    public void setA0(double value) {
        this.a0 = value;
    }

    /**
     * Gets the value of the a1 property.
     * 
     */
    public double getA1() {
        return a1;
    }

    /**
     * Sets the value of the a1 property.
     * 
     */
    public void setA1(double value) {
        this.a1 = value;
    }

    /**
     * Gets the value of the b0 property.
     * 
     */
    public double getB0() {
        return b0;
    }

    /**
     * Sets the value of the b0 property.
     * 
     */
    public void setB0(double value) {
        this.b0 = value;
    }

    /**
     * Gets the value of the b1 property.
     * 
     */
    public double getB1() {
        return b1;
    }

    /**
     * Sets the value of the b1 property.
     * 
     */
    public void setB1(double value) {
        this.b1 = value;
    }

    /**
     * Gets the value of the c1 property.
     * 
     */
    public double getC1() {
        return c1;
    }

    /**
     * Sets the value of the c1 property.
     * 
     */
    public void setC1(double value) {
        this.c1 = value;
    }

    /**
     * Gets the value of the cutoff property.
     * 
     */
    public double getCutoff() {
        return cutoff;
    }

    /**
     * Sets the value of the cutoff property.
     * 
     */
    public void setCutoff(double value) {
        this.cutoff = value;
    }

}

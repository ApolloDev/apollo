//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.05 at 06:45:13 PM EST 
//


package edu.pitt.rods.research.apollo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AgentBasedModelRunResponseObjType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgentBasedModelRunResponseObjType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="results">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Recognized">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="EpiCurve">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Susceptible" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
 *                                       &lt;element name="Exposed" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
 *                                       &lt;element name="Infectious" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
 *                                       &lt;element name="Recovered" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="IncidenceCurve" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
 *                             &lt;element name="NumberVaccinated" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Unrecognized" type="{http://research.rods.pitt.edu/apollo/}SupportedTypeCollectionType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgentBasedModelRunResponseObjType", propOrder = {
    "results"
})
public class AgentBasedModelRunResponseObjType {

    @XmlElement(required = true)
    protected AgentBasedModelRunResponseObjType.Results results;

    /**
     * Gets the value of the results property.
     * 
     * @return
     *     possible object is
     *     {@link AgentBasedModelRunResponseObjType.Results }
     *     
     */
    public AgentBasedModelRunResponseObjType.Results getResults() {
        return results;
    }

    /**
     * Sets the value of the results property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgentBasedModelRunResponseObjType.Results }
     *     
     */
    public void setResults(AgentBasedModelRunResponseObjType.Results value) {
        this.results = value;
    }


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
     *         &lt;element name="Recognized">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="EpiCurve">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Susceptible" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
     *                             &lt;element name="Exposed" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
     *                             &lt;element name="Infectious" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
     *                             &lt;element name="Recovered" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="IncidenceCurve" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
     *                   &lt;element name="NumberVaccinated" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Unrecognized" type="{http://research.rods.pitt.edu/apollo/}SupportedTypeCollectionType"/>
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
        "recognized",
        "unrecognized"
    })
    public static class Results {

        @XmlElement(name = "Recognized", required = true)
        protected AgentBasedModelRunResponseObjType.Results.Recognized recognized;
        @XmlElement(name = "Unrecognized", required = true)
        protected SupportedTypeCollectionType unrecognized;

        /**
         * Gets the value of the recognized property.
         * 
         * @return
         *     possible object is
         *     {@link AgentBasedModelRunResponseObjType.Results.Recognized }
         *     
         */
        public AgentBasedModelRunResponseObjType.Results.Recognized getRecognized() {
            return recognized;
        }

        /**
         * Sets the value of the recognized property.
         * 
         * @param value
         *     allowed object is
         *     {@link AgentBasedModelRunResponseObjType.Results.Recognized }
         *     
         */
        public void setRecognized(AgentBasedModelRunResponseObjType.Results.Recognized value) {
            this.recognized = value;
        }

        /**
         * Gets the value of the unrecognized property.
         * 
         * @return
         *     possible object is
         *     {@link SupportedTypeCollectionType }
         *     
         */
        public SupportedTypeCollectionType getUnrecognized() {
            return unrecognized;
        }

        /**
         * Sets the value of the unrecognized property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupportedTypeCollectionType }
         *     
         */
        public void setUnrecognized(SupportedTypeCollectionType value) {
            this.unrecognized = value;
        }


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
         *         &lt;element name="EpiCurve">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Susceptible" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
         *                   &lt;element name="Exposed" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
         *                   &lt;element name="Infectious" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
         *                   &lt;element name="Recovered" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="IncidenceCurve" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
         *         &lt;element name="NumberVaccinated" type="{http://www.w3.org/2001/XMLSchema}double"/>
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
            "epiCurve",
            "incidenceCurve",
            "numberVaccinated"
        })
        public static class Recognized {

            @XmlElement(name = "EpiCurve", required = true)
            protected AgentBasedModelRunResponseObjType.Results.Recognized.EpiCurve epiCurve;
            @XmlElement(name = "IncidenceCurve", required = true)
            protected UnnamedDoubleArrayType incidenceCurve;
            @XmlElement(name = "NumberVaccinated")
            protected double numberVaccinated;

            /**
             * Gets the value of the epiCurve property.
             * 
             * @return
             *     possible object is
             *     {@link AgentBasedModelRunResponseObjType.Results.Recognized.EpiCurve }
             *     
             */
            public AgentBasedModelRunResponseObjType.Results.Recognized.EpiCurve getEpiCurve() {
                return epiCurve;
            }

            /**
             * Sets the value of the epiCurve property.
             * 
             * @param value
             *     allowed object is
             *     {@link AgentBasedModelRunResponseObjType.Results.Recognized.EpiCurve }
             *     
             */
            public void setEpiCurve(AgentBasedModelRunResponseObjType.Results.Recognized.EpiCurve value) {
                this.epiCurve = value;
            }

            /**
             * Gets the value of the incidenceCurve property.
             * 
             * @return
             *     possible object is
             *     {@link UnnamedDoubleArrayType }
             *     
             */
            public UnnamedDoubleArrayType getIncidenceCurve() {
                return incidenceCurve;
            }

            /**
             * Sets the value of the incidenceCurve property.
             * 
             * @param value
             *     allowed object is
             *     {@link UnnamedDoubleArrayType }
             *     
             */
            public void setIncidenceCurve(UnnamedDoubleArrayType value) {
                this.incidenceCurve = value;
            }

            /**
             * Gets the value of the numberVaccinated property.
             * 
             */
            public double getNumberVaccinated() {
                return numberVaccinated;
            }

            /**
             * Sets the value of the numberVaccinated property.
             * 
             */
            public void setNumberVaccinated(double value) {
                this.numberVaccinated = value;
            }


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
             *         &lt;element name="Susceptible" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
             *         &lt;element name="Exposed" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
             *         &lt;element name="Infectious" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
             *         &lt;element name="Recovered" type="{http://research.rods.pitt.edu/apollo/}unnamed_double_array_type"/>
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
                "susceptible",
                "exposed",
                "infectious",
                "recovered"
            })
            public static class EpiCurve {

                @XmlElement(name = "Susceptible", required = true)
                protected UnnamedDoubleArrayType susceptible;
                @XmlElement(name = "Exposed", required = true)
                protected UnnamedDoubleArrayType exposed;
                @XmlElement(name = "Infectious", required = true)
                protected UnnamedDoubleArrayType infectious;
                @XmlElement(name = "Recovered", required = true)
                protected UnnamedDoubleArrayType recovered;

                /**
                 * Gets the value of the susceptible property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public UnnamedDoubleArrayType getSusceptible() {
                    return susceptible;
                }

                /**
                 * Sets the value of the susceptible property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public void setSusceptible(UnnamedDoubleArrayType value) {
                    this.susceptible = value;
                }

                /**
                 * Gets the value of the exposed property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public UnnamedDoubleArrayType getExposed() {
                    return exposed;
                }

                /**
                 * Sets the value of the exposed property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public void setExposed(UnnamedDoubleArrayType value) {
                    this.exposed = value;
                }

                /**
                 * Gets the value of the infectious property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public UnnamedDoubleArrayType getInfectious() {
                    return infectious;
                }

                /**
                 * Sets the value of the infectious property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public void setInfectious(UnnamedDoubleArrayType value) {
                    this.infectious = value;
                }

                /**
                 * Gets the value of the recovered property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public UnnamedDoubleArrayType getRecovered() {
                    return recovered;
                }

                /**
                 * Sets the value of the recovered property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link UnnamedDoubleArrayType }
                 *     
                 */
                public void setRecovered(UnnamedDoubleArrayType value) {
                    this.recovered = value;
                }

            }

        }

    }

}

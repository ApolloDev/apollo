
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CaseDefinitionEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CaseDefinitionEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="case"/>
 *     &lt;enumeration value="confirmedCase"/>
 *     &lt;enumeration value="probableCase"/>
 *     &lt;enumeration value="suspectedCase"/>
 *     &lt;enumeration value="neonatalCase"/>
 *     &lt;enumeration value="epidemiologicallyLinkedCase"/>
 *     &lt;enumeration value="fatalCase"/>
 *     &lt;enumeration value="alertCase"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CaseDefinitionEnum")
@XmlEnum
public enum CaseDefinitionEnum {

    @XmlEnumValue("case")
    CASE("case"),
    @XmlEnumValue("confirmedCase")
    CONFIRMED_CASE("confirmedCase"),
    @XmlEnumValue("probableCase")
    PROBABLE_CASE("probableCase"),
    @XmlEnumValue("suspectedCase")
    SUSPECTED_CASE("suspectedCase"),
    @XmlEnumValue("neonatalCase")
    NEONATAL_CASE("neonatalCase"),
    @XmlEnumValue("epidemiologicallyLinkedCase")
    EPIDEMIOLOGICALLY_LINKED_CASE("epidemiologicallyLinkedCase"),
    @XmlEnumValue("fatalCase")
    FATAL_CASE("fatalCase"),
    @XmlEnumValue("alertCase")
    ALERT_CASE("alertCase");
    private final String value;

    CaseDefinitionEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CaseDefinitionEnum fromValue(String v) {
        for (CaseDefinitionEnum c: CaseDefinitionEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

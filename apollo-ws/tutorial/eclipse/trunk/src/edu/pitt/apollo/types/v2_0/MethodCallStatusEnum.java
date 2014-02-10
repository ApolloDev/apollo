
package edu.pitt.apollo.types.v2_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MethodCallStatusEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MethodCallStatusEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="exiting"/>
 *     &lt;enumeration value="held"/>
 *     &lt;enumeration value="queued"/>
 *     &lt;enumeration value="running"/>
 *     &lt;enumeration value="moving"/>
 *     &lt;enumeration value="waiting"/>
 *     &lt;enumeration value="completed"/>
 *     &lt;enumeration value="failed"/>
 *     &lt;enumeration value="unauthorized"/>
 *     &lt;enumeration value="authentication_failure"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MethodCallStatusEnum")
@XmlEnum
public enum MethodCallStatusEnum {

    @XmlEnumValue("exiting")
    EXITING("exiting"),
    @XmlEnumValue("held")
    HELD("held"),
    @XmlEnumValue("queued")
    QUEUED("queued"),
    @XmlEnumValue("running")
    RUNNING("running"),
    @XmlEnumValue("moving")
    MOVING("moving"),
    @XmlEnumValue("waiting")
    WAITING("waiting"),
    @XmlEnumValue("completed")
    COMPLETED("completed"),
    @XmlEnumValue("failed")
    FAILED("failed"),
    @XmlEnumValue("unauthorized")
    UNAUTHORIZED("unauthorized"),
    @XmlEnumValue("authentication_failure")
    AUTHENTICATION_FAILURE("authentication_failure");
    private final String value;

    MethodCallStatusEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MethodCallStatusEnum fromValue(String v) {
        for (MethodCallStatusEnum c: MethodCallStatusEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

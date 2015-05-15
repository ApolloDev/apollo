
package edu.pitt.apollo.types.v3_0_0;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BehaviorEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BehaviorEnum">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="absentFromWork"/>
 *     &lt;enumeration value="absentFromPreSchool"/>
 *     &lt;enumeration value="absentFromPrimarySchool"/>
 *     &lt;enumeration value="absentFromSecondarySchool"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BehaviorEnum")
@XmlEnum
public enum BehaviorEnum {

    @XmlEnumValue("absentFromWork")
    ABSENT_FROM_WORK("absentFromWork"),
    @XmlEnumValue("absentFromPreSchool")
    ABSENT_FROM_PRE_SCHOOL("absentFromPreSchool"),
    @XmlEnumValue("absentFromPrimarySchool")
    ABSENT_FROM_PRIMARY_SCHOOL("absentFromPrimarySchool"),
    @XmlEnumValue("absentFromSecondarySchool")
    ABSENT_FROM_SECONDARY_SCHOOL("absentFromSecondarySchool");
    private final String value;

    BehaviorEnum(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BehaviorEnum fromValue(String v) {
        for (BehaviorEnum c: BehaviorEnum.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

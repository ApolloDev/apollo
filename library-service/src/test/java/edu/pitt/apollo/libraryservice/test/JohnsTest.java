package edu.pitt.apollo.libraryservice.test;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by jdl50 on 7/31/15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "JohnsTest"
)
@JsonTypeInfo(
        include = JsonTypeInfo.As.PROPERTY,
        use = JsonTypeInfo.Id.CLASS,
        property = "class"
)
public class JohnsTest {
        @XmlElement(
                required = true
        )
    String one, two, three = "four";
}

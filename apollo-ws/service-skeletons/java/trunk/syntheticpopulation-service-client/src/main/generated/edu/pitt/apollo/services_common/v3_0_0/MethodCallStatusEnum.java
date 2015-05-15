
package edu.pitt.apollo.services_common.v3_0_0;

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
 *     &lt;enumeration value="called_translator"/>
 *     &lt;enumeration value="called_visualizer"/>
 *     &lt;enumeration value="called_simulator"/>
 *     &lt;enumeration value="translating"/>
 *     &lt;enumeration value="translation_completed"/>
 *     &lt;enumeration value="initializing"/>
 *     &lt;enumeration value="log_files_written"/>
 *     &lt;enumeration value="loading_run_config_into_database"/>
 *     &lt;enumeration value="loaded_run_config_into_database"/>
 *     &lt;enumeration value="staging"/>
 *     &lt;enumeration value="running"/>
 *     &lt;enumeration value="moving"/>
 *     &lt;enumeration value="waiting"/>
 *     &lt;enumeration value="completed"/>
 *     &lt;enumeration value="failed"/>
 *     &lt;enumeration value="unauthorized"/>
 *     &lt;enumeration value="unknown_runid"/>
 *     &lt;enumeration value="run_terminated"/>
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
    @XmlEnumValue("called_translator")
    CALLED_TRANSLATOR("called_translator"),
    @XmlEnumValue("called_visualizer")
    CALLED_VISUALIZER("called_visualizer"),
    @XmlEnumValue("called_simulator")
    CALLED_SIMULATOR("called_simulator"),
    @XmlEnumValue("translating")
    TRANSLATING("translating"),
    @XmlEnumValue("translation_completed")
    TRANSLATION_COMPLETED("translation_completed"),
    @XmlEnumValue("initializing")
    INITIALIZING("initializing"),
    @XmlEnumValue("log_files_written")
    LOG_FILES_WRITTEN("log_files_written"),
    @XmlEnumValue("loading_run_config_into_database")
    LOADING_RUN_CONFIG_INTO_DATABASE("loading_run_config_into_database"),
    @XmlEnumValue("loaded_run_config_into_database")
    LOADED_RUN_CONFIG_INTO_DATABASE("loaded_run_config_into_database"),
    @XmlEnumValue("staging")
    STAGING("staging"),
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
    @XmlEnumValue("unknown_runid")
    UNKNOWN_RUNID("unknown_runid"),
    @XmlEnumValue("run_terminated")
    RUN_TERMINATED("run_terminated"),
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

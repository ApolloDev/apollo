package edu.pitt.apollo.apollotranslator.types.translator;

import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2013
 * Time: 5:48:08 PM
 * Class: SelectorResult
 * IDE: NetBeans 6.9.1
 */
public class SelectorResult {
    
    private String objectOrFieldType;
    private Map<String, String> selectorOptions;
    private String index;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    private String optionalIndex;
    private String selectorAndIndexString;

    /**
     * @return the objectOrFieldType
     */
    public String getObjectOrFieldType() {
        return objectOrFieldType;
    }

    /**
     * @param objectOrFieldType the objectOrFieldType to set
     */
    public void setObjectOrFieldType(String objectOrFieldType) {
        this.objectOrFieldType = objectOrFieldType;
    }

    /**
     * @return the optionalIndex
     */
    public String getOptionalIndex() {
        return optionalIndex;
    }

    /**
     * @param optionalIndex the optionalIndex to set
     */
    public void setOptionalIndex(String optionalIndex) {
        this.optionalIndex = optionalIndex;
    }

    /**
     * @return the selectorAndIndexString
     */
    public String getSelectorAndIndexString() {
        return selectorAndIndexString;
    }

    /**
     * @param selectorAndIndexString the selectorAndIndexString to set
     */
    public void setSelectorAndIndexString(String selectorAndIndexString) {
        this.selectorAndIndexString = selectorAndIndexString;
    }

    /**
     * @return the selectorOptions
     */
    public Map<String, String> getSelectorOptions() {
        return selectorOptions;
    }

    /**
     * @param selectorOptions the selectorOptions to set
     */
    public void setSelectorOptions(Map<String, String> selectorOptions) {
        this.selectorOptions = selectorOptions;
    }
    
}

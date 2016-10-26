package edu.pitt.apollo.apollotranslator.types.translator;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 15, 2013
 * Time: 6:08:53 PM
 * Class: TranslationInstructionSelector
 * IDE: NetBeans 6.9.1
 */
public class TranslationInstructionSelector {

    public class SelectorNameValuePair {

        private String name, value;

        public SelectorNameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }
        
        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    private String type = null;
    private String description = null;
    private SelectorNameValuePair pair = null;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    public SelectorNameValuePair getPair() {
        return pair;
    }
    
    public void setPair(String name, String value) {
        this.pair = new SelectorNameValuePair(name, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TranslationInstructionSelector other = (TranslationInstructionSelector) obj;
        if ((this.type == null) ? (other.type != null) : !this.type.equals(other.type)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 13 * hash + (this.description != null ? this.description.hashCode() : 0);
        return hash;
    }
}

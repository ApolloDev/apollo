package edu.pitt.apollo.apollotranslator.types.translator;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2013
 * Time: 1:52:16 PM
 * Class: PlainTextTranslationReturnObject
 * IDE: NetBeans 6.9.1
 */
public class PlainTextTranslationReturnObject extends TranslationReturnObject {

    private String delimiter;
    protected String nativeTerm;
    private String valueFromJavascriptFunction;

    public PlainTextTranslationReturnObject() {
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * @return the valueFromJavascriptFunction
     */
    public String getValueFromJavascriptFunction() {
        return valueFromJavascriptFunction;
    }

    /**
     * @param valueFromJavascriptFunction the valueFromJavascriptFunction to set
     */
    public void setValueFromJavascriptFunction(String valueFromJavascriptFunction) {
        this.valueFromJavascriptFunction = valueFromJavascriptFunction;
    }

    public String getNativeTerm() {
        return nativeTerm;
    }

    public void setNativeTerm(String nativeTerm) {
        this.nativeTerm = nativeTerm;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PlainTextTranslationReturnObject other = (PlainTextTranslationReturnObject) obj;
        if ((this.nativeTerm == null) ? (other.nativeTerm != null) : !this.nativeTerm.equals(other.nativeTerm)) {
            return false;
        }
        if ((this.delimiter == null) ? (other.delimiter != null) : !this.delimiter.equals(other.delimiter)) {
            return false;
        }
        if ((this.valueFromJavascriptFunction == null) ? (other.valueFromJavascriptFunction != null) : !this.valueFromJavascriptFunction.equals(other.valueFromJavascriptFunction)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.nativeTerm != null ? this.nativeTerm.hashCode() : 0);
        hash = 71 * hash + (this.delimiter != null ? this.delimiter.hashCode() : 0);
        hash = 71 * hash + (this.valueFromJavascriptFunction != null ? this.valueFromJavascriptFunction.hashCode() : 0);
        return hash;
    }
}

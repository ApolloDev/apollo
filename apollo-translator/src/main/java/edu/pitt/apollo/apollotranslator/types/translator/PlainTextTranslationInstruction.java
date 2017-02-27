package edu.pitt.apollo.apollotranslator.types.translator;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 22, 2013
 * Time: 12:29:56 PM
 * Class: PlainTextTranslationInstruction
 * IDE: NetBeans 6.9.1
 */
public class PlainTextTranslationInstruction extends TranslationInstruction {

    private String nativeParameterDelimiter;

    public PlainTextTranslationInstruction() {
        super();
    }

    public PlainTextTranslationInstruction(TranslationInstruction instruction) {
        // this makes a PlainTextTranslationInstruction from a TranslationInstruction
        super(instruction);
    }

    /**
     * @return the nativeParameterDelimiter
     */
    public String getNativeParameterDelimiter() {
        return nativeParameterDelimiter;
    }

    /**
     * @param nativeParameterDelimiter the nativeParameterDelimiter to set
     */
    public void setNativeParameterDelimiter(String nativeParameterDelimiter) {
        this.nativeParameterDelimiter = nativeParameterDelimiter;
    }
}

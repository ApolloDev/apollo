package edu.pitt.apollo.translatorservice.types;

import edu.pitt.apollo.apollotranslator.types.translator.TranslationMode;

/**
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 11, 2013
 * Time: 12:20:11 PM
 * Class: TranslatorInput
 * IDE: NetBeans 6.9.1
 */
public class TranslatorResources {

    private String translationInstructionsPath;
    private String javaScriptPath;
    private Integer urnOfBaseConfigurationInLibrary = null;
    private String fileLocationOfBaseConfiguration = null;
    private TranslationMode translationMode;

    public String getFileLocationOfBaseConfiguration() {
        return fileLocationOfBaseConfiguration;
    }

    public void setFileLocationOfBaseConfiguration(String fileLocationOfBaseConfiguration) {
        this.fileLocationOfBaseConfiguration = fileLocationOfBaseConfiguration;
    }

    public Integer getUrnOfBaseConfigurationInLibrary() {
        return urnOfBaseConfigurationInLibrary;
    }

    public void setUrnOfBaseConfigurationInLibrary(Integer urnOfBaseConfigurationInLibrary) {
        this.urnOfBaseConfigurationInLibrary = urnOfBaseConfigurationInLibrary;
    }

    public TranslationMode getTranslationMode() {
        return translationMode;
    }

    public void setTranslationMode(TranslationMode translationMode) {
        this.translationMode = translationMode;
    }

    /**
     * @return the translationInstructionsPath
     */
    public String getTranslationInstructionsPath() {
        return translationInstructionsPath;
    }

    /**
     * @param translationInstructionsPath the translationInstructionsPath to set
     */
    public void setTranslationInstructionsPath(String translationInstructionsPath) {
        this.translationInstructionsPath = translationInstructionsPath;
    }

    /**
     * @return the javaScriptPath
     */
    public String getJavaScriptPath() {
        return javaScriptPath;
    }

    /**
     * @param javaScriptPath the javaScriptPath to set
     */
    public void setJavaScriptPath(String javaScriptPath) {
        this.javaScriptPath = javaScriptPath;
    }
}

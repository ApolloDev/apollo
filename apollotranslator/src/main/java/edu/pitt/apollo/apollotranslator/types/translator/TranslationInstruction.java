package edu.pitt.apollo.apollotranslator.types.translator;

import java.util.HashMap;
import java.util.Map;

import edu.pitt.apollo.apollotranslator.file.VerboseFileCreator.CssWarningClasses;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Aug 12, 2013 Time:
 * 5:45:36 PM Class: TranslationInstruction IDE: NetBeans 6.9.1
 */
public abstract class TranslationInstruction {

//	public enum ErrorLevel {
//		NONE, NO_1_TO_1_MAPPING, INFORMATION_LOSS
//	};
    String apolloParameter;
    String nativeParameterIdentifier;
    String jsFunctionName;
    String configurationFileName;
    String globalComment = null;
    String configurationFileComment = null;
    String htmlFileComment = null;
    String htmlFileWarning;
    boolean translated = false;
    CssWarningClasses cssWarningClass;
    // this maps function arguments to apollo identifier paths
    Map<String, String> jsFunctionArgumentMap;

    public TranslationInstruction() {
    }

    public TranslationInstruction(TranslationInstruction instruction) {
        // this makes a PlainTextTranslationInstruction from a TranslationInstruction

        this.apolloParameter = instruction.getApolloParameter();
        this.nativeParameterIdentifier = instruction.getNativeParameterIdentifier();
        this.configurationFileComment = instruction.getConfigurationFileComment();
        this.configurationFileName = instruction.getConfigurationFileName();
        this.cssWarningClass = instruction.getCssWarningClass();
        this.globalComment = instruction.getGlobalComment();
        this.htmlFileComment = instruction.getHtmlFileComment();
        this.htmlFileWarning = instruction.getHtmlFileWarning();
        this.jsFunctionName = instruction.getJsFunctionName();
        this.jsFunctionArgumentMap = instruction.getJsFunctionArgumentMap();
        this.translated = instruction.isTranslated();
    }

    public boolean isTranslated() {
        return translated;
    }

    public void setTranslated(boolean translated) {
        this.translated = translated;
    }

    public String getApolloParameter() {
        return apolloParameter;
    }

    public void setApolloParameter(String apolloParameter) {
        this.apolloParameter = apolloParameter;
    }

    public String getHtmlFileComment() {
        return htmlFileComment;
    }

    public void setHtmlFileComment(String htmlFileComment) {
        this.htmlFileComment = htmlFileComment;
    }

    public String getGlobalComment() {
        return globalComment;
    }

    public void setGlobalComment(String globalComment) {
        this.globalComment = globalComment;
    }

    public String getHtmlFileWarning() {
        return htmlFileWarning;
    }

    public void setHtmlFileWarning(String htmlFileWarning) {
        this.htmlFileWarning = htmlFileWarning;
    }

    public void setCssWarningClass(CssWarningClasses cssWarningClass) {
        this.cssWarningClass = cssWarningClass;
    }

    public CssWarningClasses getCssWarningClass() {
        return cssWarningClass;
    }

//    public void setHtmlFileWarningErrorLevel(CssWarningClasses htmlFileWaringErrorLevel) {
//        this.cssWarningClass = htmlFileWaringErrorLevel;
//    }
    public String getNativeParameterIdentifier() {
        return nativeParameterIdentifier;
    }

    public void setNativeParameterIdentifier(String nativeParameterIdentifier) {
        this.nativeParameterIdentifier = nativeParameterIdentifier;
    }

    public Map<String, String> getJsFunctionArgumentMap() {
        return jsFunctionArgumentMap;
    }

    public void clearJsFunctionArgumentMap() {
        jsFunctionArgumentMap = null;
    }

    public void setJsFunctionArgumentMapping(String jsFunctionArg,
            String apolloIdentifierPath) {
        if (jsFunctionArgumentMap == null) {
            jsFunctionArgumentMap = new HashMap<String, String>();
        }

        jsFunctionArgumentMap.put(jsFunctionArg, apolloIdentifierPath);
    }

    public void setJsFunctionArgumentMappings(Map<String, String> jsFunctionArgs) {
        this.jsFunctionArgumentMap = jsFunctionArgs;
    }

    public String getConfigurationFileName() {
        return configurationFileName;
    }

    public void setConfigurationFileName(String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    public String getConfigurationFileComment() {
        return configurationFileComment;
    }

    public void setConfigurationFileComment(String configurationFileComment) {
        this.configurationFileComment = configurationFileComment;
    }

    public String getJsFunctionName() {
        return jsFunctionName;
    }

    public void setJsFunctionName(String jsFunctionName) {
        this.jsFunctionName = jsFunctionName;
    }
//    public TranslationInstruction copy() {
//
//        TranslationInstruction newInstruction = new TranslationInstruction();
//        newInstruction.setApolloParameter(this.getApolloParameter());
//        newInstruction.setConfigurationFileComment(this.getConfigurationFileComment());
//        newInstruction.setConfigurationFileName(this.getConfigurationFileName());
//        newInstruction.setCssWarningClass(this.getCssWarningClass());
//        newInstruction.setGlobalComment(this.getGlobalComment());
//        newInstruction.setHtmlFileComment(this.getHtmlFileComment());
//        newInstruction.setHtmlFileWarning(this.getHtmlFileWarning());
//        newInstruction.setJsFunctionArgumentMappings(this.getJsFunctionArgumentMap());
//        newInstruction.setJsFunctionName(this.getJsFunctionName());
//        newInstruction.setTranslated(this.isTranslated());
//
//        return newInstruction;
//    }
}

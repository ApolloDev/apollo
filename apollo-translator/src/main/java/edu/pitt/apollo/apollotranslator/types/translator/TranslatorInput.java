package edu.pitt.apollo.apollotranslator.types.translator;

import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nem41 on 7/28/15.
 */
public class TranslatorInput {

    private RunSimulationMessage runSimulationMessage;
    private TranslationMode translationMode;
    private String translationInstructionsFilePath;
    private String javaScriptFilePath;
    private String commentToken;
    private String baseConfigurationFileContent;
	private Map<Class, String> classToTemplateFileMap = null;

	public Map<Class, String> getClassToTemplateFileMap() {
		if (classToTemplateFileMap == null) {
			classToTemplateFileMap = new HashMap<>();
		}
		
		return classToTemplateFileMap;
	}
	
    public String getBaseConfigurationFileContent() {
        return baseConfigurationFileContent;
    }

    public void setBaseConfigurationFileContent(String baseConfigurationFileContent) {
        this.baseConfigurationFileContent = baseConfigurationFileContent;
    }

    public RunSimulationMessage getRunSimulationMessage() {
        return runSimulationMessage;
    }

    public void setRunSimulationMessage(RunSimulationMessage simulatorConfiguration) {
        this.runSimulationMessage = simulatorConfiguration;
    }

    public TranslationMode getTranslationMode() {
        return translationMode;
    }

    public void setTranslationMode(TranslationMode translationMode) {
        this.translationMode = translationMode;
    }

    public String getTranslationInstructionsFilePath() {
        return translationInstructionsFilePath;
    }

    public void setTranslationInstructionsFilePath(String translationInstructionsFilePath) {
        this.translationInstructionsFilePath = translationInstructionsFilePath;
    }

    public String getJavaScriptFilePath() {
        return javaScriptFilePath;
    }

    public void setJavaScriptFilePath(String javaScriptFilePath) {
        this.javaScriptFilePath = javaScriptFilePath;
    }

    public String getCommentToken() {
        return commentToken;
    }

    public void setCommentToken(String commentToken) {
        this.commentToken = commentToken;
    }
}

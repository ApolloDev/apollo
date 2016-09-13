package edu.pitt.apollo.apollotranslator.types.translator;

import java.util.List;

import edu.pitt.apollo.apollotranslator.file.VerboseFileCreator.CssWarningClasses;

public class SetterReturnObject {

    // The following list is used for Apollo list objects, such as control measures,
    // or lists of target priority populations. Each SetterReturnObject in the list will correspond
    // to an Apollo parameter in a specific object of the containing list. Similar labels from different
    // objects are denoted by appending an index [0], [1], etc. to the Apollo label. We use this list here
    // so that native params which map to the entire Apollo object list will be printed
    // after all of the Apollo labels of the objects contained in the list.
    private List<SetterReturnObject> subApolloParameters;
    private TranslationReturnObject translationReturnObject;

    public TranslationReturnObject getTranslationReturnObject() {
        return translationReturnObject;
    }

    public void setTranslationReturnObject(TranslationReturnObject translationReturnObject) {
        this.translationReturnObject = translationReturnObject;
    }
    
    private int numOneToManyMappings, numManyToOneMappings;
    private String htmlFileWarning;
    private String configurationFile, apolloParameter,
            htmlFileComment, globalComment, configurationFileComment,
            apolloParamAndValAsString, mappingComment;

    public String getMappingComment() {
        return mappingComment;
    }

    public void setMappingComment(String mappingComment) {
        this.mappingComment = mappingComment;
    }
    private String section;

    public String getApolloParameter() {
        return apolloParameter;
    }

    public void setApolloParameter(String apolloParameter) {
        this.apolloParameter = apolloParameter;
    }
    public CssWarningClasses cssWarningClass;

    public CssWarningClasses getCssWarningClass() {
        return cssWarningClass;
    }

    public void setCssWarningClass(CssWarningClasses errorLevel) {
        this.cssWarningClass = errorLevel;
    }

    public String getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
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

    public String getConfigurationFileComment() {
        return configurationFileComment;
    }

    public void setConfigurationFileComment(String configurationFileComment) {
        this.configurationFileComment = configurationFileComment;
    }

    public String getApolloParamAndValAsString() {
        return apolloParamAndValAsString;
    }

    public void setApolloParamAndValAsString(String apolloParamAndValAsString) {
        this.apolloParamAndValAsString = apolloParamAndValAsString;
    }

    public String getHtmlFileWarning() {
        return htmlFileWarning;
    }

    public void setHtmlFileWarning(String htmlFileWarning) {
        this.htmlFileWarning = htmlFileWarning;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section
     *            the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the numOneToManyMappings
     */
    public int getNumOneToManyMappings() {
        return numOneToManyMappings;
    }

    /**
     * @param numOneToManyMappings
     *            the numOneToManyMappings to set
     */
    public void setNumOneToManyMappings(int numOneToManyMappings) {
        this.numOneToManyMappings = numOneToManyMappings;
    }

    /**
     * @return the numManyToOneMappings
     */
    public int getNumManyToOneMappings() {
        return numManyToOneMappings;
    }

    /**
     * @param numManyToOneMappings
     *            the numManyToOneMappings to set
     */
    public void setNumManyToOneMappings(int numManyToOneMappings) {
        this.numManyToOneMappings = numManyToOneMappings;
    }

//    /**
//     * @return the translationReturnObjects
//     */
//    public TranslationReturnObject getTranslationReturnObject() {
//        return translationReturnObjects;
//    }
//
//    /**
//     * @param translationReturnObjects
//     *            the translationReturnObjects to set
//     */
//    public void setTranslationReturnObject(
//            TranslationReturnObject translationReturnObjects) {
//        this.translationReturnObjects = translationReturnObjects;
//    }
    /**
     * @return the subApolloParameters
     */
    public List<SetterReturnObject> getSubApolloParameters() {
        return subApolloParameters;
    }

    public void setSubApolloParameters(List<SetterReturnObject> apolloParamsContainedInListObjects) {
        this.subApolloParameters = apolloParamsContainedInListObjects;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SetterReturnObject other = (SetterReturnObject) obj;
        if (this.translationReturnObject == null || !this.translationReturnObject.equals(other.translationReturnObject)) {
            return false;
        }
        if (this.numOneToManyMappings != other.numOneToManyMappings) {
            return false;
        }
        if (this.numManyToOneMappings != other.numManyToOneMappings) {
            return false;
        }
        if ((this.configurationFile == null) ? (other.configurationFile != null) : !this.configurationFile.equals(other.configurationFile)) {
            return false;
        }
        if ((this.apolloParameter == null) ? (other.apolloParameter != null) : !this.apolloParameter.equals(other.apolloParameter)) {
            return false;
        }
        if ((this.apolloParamAndValAsString == null) ? (other.apolloParamAndValAsString != null) : !this.apolloParamAndValAsString.equals(other.apolloParamAndValAsString)) {
            return false;
        }
        if ((this.section == null) ? (other.section != null) : !this.section.equals(other.section)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.translationReturnObject != null ? this.translationReturnObject.hashCode() : 0);
        hash = 53 * hash + this.numOneToManyMappings;
        hash = 53 * hash + this.numManyToOneMappings;
        hash = 53 * hash + (this.configurationFile != null ? this.configurationFile.hashCode() : 0);
        hash = 53 * hash + (this.apolloParameter != null ? this.apolloParameter.hashCode() : 0);
        hash = 53 * hash + (this.apolloParamAndValAsString != null ? this.apolloParamAndValAsString.hashCode() : 0);
        hash = 53 * hash + (this.section != null ? this.section.hashCode() : 0);
        return hash;
    }

}

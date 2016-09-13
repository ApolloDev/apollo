package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.exception.ApolloTranslatorException;
import edu.pitt.apollo.apollotranslator.file.VerboseFileCreator.CssWarningClasses;
import edu.pitt.apollo.apollotranslator.types.translator.ApolloParameterMap;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationInstruction;
import edu.pitt.apollo.apollotranslator.types.translator.TranslationReturnObject;

public class AbstractSetter {

    public static final String UNSPECIFIED_PARAM_SECTION = "NON-SCENARIO PARAMETERS USED TO RUN SIMULATOR";
    public static final String GENERIC_IS_NOT_NULL_LABEL = "(non-Apollo default parameters)";
    public static final String PARAM_IS_NOT_SET_LABEL = "(not set)";
    public static final String LIST_VALUES_LABEL = "(list values described below)";
    public static final String PARAM_VALUES_DESCRIBED_BELOW_LABEL = "(list values described below)";
    ApolloTranslationEngine apolloTranslationEngine;

    public AbstractSetter() {
    }

    public ApolloTranslationEngine getApolloTranslationEngine() {
        return apolloTranslationEngine;
    }

    public void setApolloTranslationEngine(ApolloTranslationEngine apolloTranslationEngine) {
        this.apolloTranslationEngine = apolloTranslationEngine;
    }

    AbstractSetter(ApolloTranslationEngine apolloTranslationEngine) {
        this.apolloTranslationEngine = apolloTranslationEngine;
    }

    protected List<SetterReturnObject> setValue(String fieldName,
            String fieldValue, String section) throws ApolloSetterException {

        List<SetterReturnObject> setterReturnObjects = new ArrayList<SetterReturnObject>();
        if (apolloTranslationEngine == null)
            System.out.println("no");
        ApolloParameterMap apolloParameterMap = apolloTranslationEngine.getApolloParameterMap();

        
        List<TranslationInstruction> list = apolloParameterMap.get(fieldName);
        if (list != null && list.size() > 0) {

            String mappingString = "";

            int listSize = list.size();
            for (TranslationInstruction ti : list) {
                ti.setTranslated(true);
                SetterReturnObject sro = new SetterReturnObject();
                if (!section.equals(UNSPECIFIED_PARAM_SECTION)) {
                    sro.setApolloParameter(fieldName);
                    sro.setApolloParamAndValAsString(fieldName + " = "
                            + fieldValue);
                }
                sro.setNumOneToManyMappings(listSize);
                sro.setHtmlFileWarning(ti.getHtmlFileWarning());
                sro.setCssWarningClass(ti.getCssWarningClass());
                sro.setSection(section);
                sro.setMappingComment(mappingString);

                // we want to make sure the setter return object is added even
                // if the
                // translation return object is null
                setterReturnObjects.add(sro);

                // ti will never be null now
                // if (ti != null) {

                try {
                    if (!ti.getJsFunctionName().isEmpty()) {
                        TranslationReturnObject tro = apolloTranslationEngine.translateToNativeParameterUsingJavaScript(ti);
                        if (tro == null) { // this means the native term should
                            // not be printed
                            continue;
                        }
                        sro.setTranslationReturnObject(tro);
                        sro.setConfigurationFile(ti.getConfigurationFileName());
                    } else if (!ti.getNativeParameterIdentifier().isEmpty()) {
                        TranslationReturnObject tro = apolloTranslationEngine.translateToNativeParameterWithoutJavaScript(ti);
                        if (tro == null) { // this means the native term should
                            // not be printed
                            continue;
                        }
                        sro.setTranslationReturnObject(tro);
                        sro.setConfigurationFile(ti.getConfigurationFileName());
                    }
                } catch (ApolloTranslatorException ex) {
                    throw new ApolloSetterException(ex.getMessage());
                } catch (IllegalAccessException ex) {
                    throw new ApolloSetterException(ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    throw new ApolloSetterException(ex.getMessage());
                }
            }

        } else { // there is no translation instructions

            if (fieldValue == null) {
                fieldValue = "";
            }
            if (!section.equalsIgnoreCase(NonScenarioParameterSetter.SECTION)
                    && !fieldValue.equals(GENERIC_IS_NOT_NULL_LABEL)) {
                SetterReturnObject sro = new SetterReturnObject();
                sro.setApolloParameter(fieldName);
                sro.setApolloParamAndValAsString(fieldName + " = " + fieldValue);
                sro.setCssWarningClass(CssWarningClasses.NONE);
                sro.setSection(section);

                setterReturnObjects.add(sro);
            }
        }
        return setterReturnObjects;
    }
}

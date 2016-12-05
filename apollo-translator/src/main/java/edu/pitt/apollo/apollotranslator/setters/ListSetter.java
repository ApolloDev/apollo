package edu.pitt.apollo.apollotranslator.setters;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollotranslator.ApolloTranslationEngine;
import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Oct 15, 2013
 * Time: 3:02:41 PM
 * Class: ListSetter
 * IDE: NetBeans 6.9.1
 * 
 * This class allows a list of Apollo objects of the same base class (T) to be set using the Setter
 * class for that apolloObject class.
 * 
 */
public class ListSetter<T> {

    private Class clazz;
    private List<T> apolloObjectsFromSimulatorConfiguration;
    private final ApolloTranslationEngine apolloTranslationEngine;
    private final Class classToSet;
    private final String SECTION;
    private final String TYPE_PREFIX;

    public ListSetter(Class<AbstractTypedSetter> setterClass, Class classToSet,
            List<T> apolloObjectsFromSimulatorConfiguration, ApolloTranslationEngine apolloTranslationEngine,
            String SECTION, String VCM_TYPE_PREFIX) {
        this.apolloTranslationEngine = apolloTranslationEngine;
        this.clazz = setterClass;
        this.apolloObjectsFromSimulatorConfiguration = apolloObjectsFromSimulatorConfiguration;

        // the XXX is the class used to filter the list of Apollo objects to a specific class
        this.classToSet = classToSet;
        this.SECTION = SECTION;
        this.TYPE_PREFIX = VCM_TYPE_PREFIX;

    }

    public List<SetterReturnObject> set() throws ApolloSetterException {

        List<SetterReturnObject> results = new ArrayList<SetterReturnObject>();
        for (int index = 0; index < apolloObjectsFromSimulatorConfiguration.size(); index++) {

            T apolloObject = apolloObjectsFromSimulatorConfiguration.get(index);

//            if (classToSet.getSimpleName().equalsIgnoreCase("ControlMeasure")) {
//                if (!apolloObject.getClass().getSimpleName().equalsIgnoreCase("ControlMeasure")) {
//                    continue;
//                }
//            } else {
                if (!classToSet.isInstance(apolloObject)) {
                    continue;
                }
//            }

            AbstractTypedSetter setter;
            try {
                setter = (AbstractTypedSetter) clazz.newInstance();
                setter.setSection(SECTION);
                setter.setType(TYPE_PREFIX + "[" + index + "]");
                setter.setApolloTranslationEngine(apolloTranslationEngine);
                if (apolloTranslationEngine == null)
                    System.out.println("ok");
                results.addAll(setter.set(apolloObject));
//            } catch (ApolloSetterException ex) {
//                Logger.getLogger(ListSetter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
//                Logger.getLogger(ListSetter.class.getName()).log(Level.SEVERE, null, ex);
                throw new ApolloSetterException("InstatiationException: " + ex.getMessage());
            } catch (IllegalAccessException ex) {
//                Logger.getLogger(ListSetter.class.getName()).log(Level.SEVERE, null, ex);
                throw new ApolloSetterException("IllegalAccessException: " + ex.getMessage());
            }


        }

        return results;
    }
}

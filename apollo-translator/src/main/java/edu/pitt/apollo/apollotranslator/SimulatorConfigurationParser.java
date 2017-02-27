package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.exception.ApolloParserException;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimulatorConfigurationParser {

    private static final String APOLLO_OBJECT_CLASS_PREFIX = "edu.pitt.apollo.types._10._28._2013.";
    private RunSimulationMessage simulatorConfiguration = null;
    private List<String> missingApolloObjectIdentifierPaths = new ArrayList<String>(); // this list keeps track of which identifier paths
    // did not have objects set so that the warnings are only printed once for each path

    public SimulatorConfigurationParser(
    		RunSimulationMessage simulatorConfiguration) {
        super();
        this.simulatorConfiguration = simulatorConfiguration;
    }

    public Object getApolloObjectFromLabel(String apolloIdentifierPath)
            throws ApolloParserException, IllegalAccessException,
            ClassNotFoundException {

        // check for null values
        if (this.simulatorConfiguration == null) {
            throw new ApolloParserException(
                    "Simulator configuration was null");
        }
        if (apolloIdentifierPath == null) {
            throw new ApolloParserException("Apollo field path was null");
        }

        // this will be the name of the top most object to start the path at
        // or the identifier itself if it can be accessed without a path
        String highestLevelObjectName = apolloIdentifierPath.split("\\.")[0];
        if (highestLevelObjectName.equals(apolloIdentifierPath)) {
            // this indicates the identifer can be used without a path to get to
            // the desired object
            apolloIdentifierPath = null;
        }

        return getApolloObjectFromIdentifierPath(null, highestLevelObjectName,
                null, apolloIdentifierPath);
    }

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private Object getApolloObjectFromIdentifierPath(Object parentObject,
            String highestLevelObjectName, Field parentField,
            String apolloIdentifierPath) throws ApolloParserException,
            IllegalAccessException, ClassNotFoundException {
        Class clazz;
        if (parentField == null) {
            parentObject = this.simulatorConfiguration;
            clazz = parentObject.getClass();
        } else {
            clazz = parentField.getType();
        }
        Object result = null;
        if (clazz.toString().contains("edu")) {
            List<Field> fields = getAllFields(new LinkedList<Field>(), clazz);

            for (Field field : fields) {
                if (result == null) {
                    field.setAccessible(true);
                    if (clazz.getEnumConstants() == null) {
                        if (field.getName().equalsIgnoreCase(
                                highestLevelObjectName)) {
                            try {
                                result = field.get(parentObject);
                            } catch (IllegalAccessException ex) {
                                throw new IllegalAccessException(
                                        "Exception attempting to access field "
                                        + field.getName() + ": "
                                        + ex.getMessage());
                            }
                            if (result == null) {
                                // this means that the requested object could
                                // not be found
                                return null;
                            }
                            if (apolloIdentifierPath == null) {
                                // this means the top level object is what
                                // should be returned
                                return result;
                            } else {
                                // the identifier must be accessed using methods
                                return getApolloObjectUsingMethods(result,
                                        apolloIdentifierPath);
                            }

                        } else {

							if (parentObject == null) { // this means none of the fields in the object are set, so continue
								continue;
							}
                            result = getApolloObjectFromIdentifierPath(
                                    field.get(parentObject),
                                    highestLevelObjectName, field,
                                    apolloIdentifierPath);
                            if (result != null) {
                                return result;
                            }
                        }
                    }
                }
            }
        }
        return result;

    }

    private Object printApolloObjectFromIdentifierPath(Object instanceOfField, Field currentField, String parentPath) throws ApolloParserException,
            IllegalAccessException, ClassNotFoundException {
        Class clazz;
        if (currentField == null) {
        	instanceOfField = this.simulatorConfiguration;
            clazz = instanceOfField.getClass();
        } else {
            clazz = currentField.getType();
            if (!clazz.toString().contains("edu.pitt") && (instanceOfField != null)) {
            	
            	//String s = currentField.getGenericType().toString();
            	//s = s.substring(s.indexOf("<")+1, s.indexOf(">"));
            	
//            	if (s.equalsIgnoreCase("edu.pitt.apollo.types._10._28._2013.ControlMeasure")) {
            		clazz = instanceOfField.getClass();
            		if (clazz.toString().contains("VaccinationEfficacyForSimulatorConfiguration"))
            		System.out.println("!!!!!!!! USING:" + clazz);
//          	} else 
            	//System.out.println(s);
//            	clazz = Class.forName(s);
            
            }
            
        }

        Object result = null;
        if (clazz.toString().contains("edu") ) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (result == null) {
                    field.setAccessible(true);
                    if (clazz.getEnumConstants() == null) {

                        String fieldType = field.getType().getSimpleName();
                     
//                        if (fieldType.equals("String")
//                                || fieldType.equals("double")
//                                || fieldType.equals("BigInteger")
//                                || fieldType.equals("int")) {
//                        System.out.println(clazz.toString() + "." + field.getName());
                        System.out.println(parentPath + "." + field.getName());
//                        } else 
                        
                        if (fieldType.equals("List")) {
//                            System.out.println(clazz.toString() + "." + field.getName());
                            @SuppressWarnings("rawtypes")
                            
							List list = (List) field.get(instanceOfField);
                           // System.out.println(field.getGenericType());
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    Object instanceOfListEntry = list.get(i);
                                    //obj == we want to pass an instance of timedefinition
                                    result = printApolloObjectFromIdentifierPath(instanceOfListEntry, field, parentPath + "." + field.getName());
                                }
                            }
                        } else {
                        	 result = printApolloObjectFromIdentifierPath(
                                     field.get(instanceOfField), field, parentPath + "." + field.getName());
                        }

//                        } else {
//                            System.out.println("unrecognized type: " + fieldType);
//                        }

//                        System.out.println("field.get: " + field.get(parentObject));
                        
                        //field = simulatorIdentification
                        //instance = instance of SoftwareIdentification
//                        result = printApolloObjectFromIdentifierPath(
//                                field.get(instanceOfField), field, parentPath + "." + field.getName());
//                        if (result != null) {
//                            return result;
//                        }

                    }
                }
            }
        }
        return result;

    }

    private Object getApolloObjectUsingMethods(Object object,
            String apolloIdentifierPath) throws ApolloParserException,
            IllegalAccessException, ClassNotFoundException {
        // an example path is infectiousDisease.infectiousPeriod.timeStepValue

        Pattern pattern = Pattern.compile("\\[(.*)\\]");

        // split the path by periods
        String[] identifierPathArray = apolloIdentifierPath.split("\\.");

        // the first element is the object that was already found, so start at
        // index 1 to use the objects methods
        String currentIdentifierPath = identifierPathArray[0]; // this will be built in the loop
        // to be the path to the current obhect or method
        idPathLoop:
        for (int i = 1; i < identifierPathArray.length; i++) {

            String identifier = identifierPathArray[i];
            currentIdentifierPath += "." + identifier;

            Matcher matcher = pattern.matcher(identifier);
            if (matcher.matches()) {
                String group = matcher.group(1);
                if (group == null || group.trim().equals("")) {
                    throw new ApolloParserException(
                            "No object type or index was specified where one was expected");
                }

                List<Object> objectList = (List<Object>) object;

                // check if the group represents an integer
                try {
                    int index = Integer.parseInt(group);
                    object = objectList.get(index);
                    continue;
                } catch (NumberFormatException ex) {
                    // not an integer, but we don't need to do anything special
                    // here
                }

                // the group must be a string representing an object type
                String objectType = APOLLO_OBJECT_CLASS_PREFIX + group;
                // get the class represented by the string

                try {
                    boolean foundObject = false;
                    for (Object obj : objectList) {
                        if (Class.forName(objectType).isInstance(obj)) {
                            if (!foundObject) {
                                object = obj;
                                foundObject = true;
                            } else {
                                // this means an instance of the object in the list has already been found
                                // which is not allowed when using this shortcut method
                                throw new ApolloParserException("Two objects of the type \"" + objectType + "\" were found"
                                        + " in the object list when using the shortcut method. The shortcut method can only"
                                        + " be used when a single instance of the object type exists in the list.");
                            }
                        }
                    }

                    if (foundObject) {
                        continue idPathLoop; // continue the main loop
                    }
                } catch (ClassNotFoundException ex) {
                    throw new ClassNotFoundException("Class " + objectType
                            + " could not be found");
                }

                // if this point is reached this means that the object could not be found
                if (!missingApolloObjectIdentifierPaths.contains(currentIdentifierPath)) {
                    // we only print the warning if a warning for this objectType has not already been printed
                    ApolloLogger.log(Level.WARNING, "No object of type \"" + objectType + "\" could be found in the object list "
                            + "using the shortcut method in the Apollo identifier path \"" + currentIdentifierPath + "\". "
                            + "Any native terms using this identifer path will not be processed.");


                    missingApolloObjectIdentifierPaths.add(currentIdentifierPath);
                }

                return null;

            } else {
                String targetMethodName = identifier;
                List<Method> completeObjectMethodList = new ArrayList<Method>();

                Class<?> objectClass = object.getClass();
                Collections.addAll(completeObjectMethodList,
                        objectClass.getDeclaredMethods());
                // need to handle inherited methods here also
                Class parent = objectClass.getSuperclass();
                while (parent != null) {
                    Collections.addAll(completeObjectMethodList,
                            parent.getDeclaredMethods());
                    parent = parent.getSuperclass();
                }

                boolean foundMethod = false;
                for (Method method : completeObjectMethodList) {
                    String name = method.getName();
                    if (("get" + targetMethodName).equalsIgnoreCase(name) || // for
                            // object
                            // get
                            // methods
                            ("is" + targetMethodName).equalsIgnoreCase(name)) { // for
                        // boolean
                        // check
                        // methods
                        foundMethod = true;
                        try {
                            object = method.invoke(object);
                        } catch (IllegalAccessException ex) {
                            throw new IllegalAccessException(
                                    "Exception attempting to access method "
                                    + name + " on object: "
                                    + ex.getMessage());
                        } catch (InvocationTargetException ex) {
                            throw new ApolloParserException(
                                    "Exception running method "
                                    + name
                                    + ". Check to make sure this method does not throw any exceptions.");
                        }
                        break;
                    }
                }
                if (!foundMethod) {
                    String addtlMsg = "";
                    if (targetMethodName.contains("[")) {
                        addtlMsg += " It appears that you are trying to access an array index, please use the proper notation in the translation file, which is arrayName.[index]";
                    }
                    throw new ApolloParserException(
                            "Could not find accessor method \"get"
                            + targetMethodName.substring(0, 1).toUpperCase()
                            + targetMethodName.substring(1)
                            + "\" or \"is"
                            + targetMethodName.substring(0, 1).toUpperCase()
                            + targetMethodName.substring(1)
                            + " while trying to get the value for "
                            + targetMethodName
                            + ", which was specified in the translation file."
                            + addtlMsg);
                }
            }
        }

        // now the object should reflect the desired apollo field
        return object;
    }

//    public static void main(String[] args) throws ApolloParserException, IllegalAccessException, ClassNotFoundException {
//
//        SimulatorConfiguration sc = SimulatorConfigurationFactory.getDefaultModelRunRequestObjType(
//                SimulatorConfigurationFactory.DefVaccCm.VACC_CM,
//                SimulatorConfigurationFactory.DefAvCm.AV_CM,
//                SimulatorConfigurationFactory.DefScCm.ALL_SC_CM_FIXED);
//        SimulatorConfigurationParser sp = new SimulatorConfigurationParser(sc);
//        sp.printApolloObjectFromIdentifierPath(null, null, "");
//    }
}

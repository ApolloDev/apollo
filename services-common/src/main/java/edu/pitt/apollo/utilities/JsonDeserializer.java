/*
 * Copyright 2015 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.utilities;

import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.JsonUtilsException;

/**
 *
 * @author nem41
 */
public class JsonDeserializer extends Deserializer {

	@Override
	public <T> T getObjectFromMessage(String json, Class<T> classToParseTo) throws DeserializationException {
		JsonUtils jsonUtils = new JsonUtils();
		try {
			return (T) jsonUtils.getObjectFromJson(json, classToParseTo);
		} catch (JsonUtilsException ex) {
			throw new DeserializationException("JsonUtilsException: " + ex.getMessage());
		}
	}

	@Override
	public Object getObjectFromMessage(String json, String className, String classNamespace) throws DeserializationException {
		// get class from classname and classnamespace
		String javaPackage = convertNamespaceFromXSDToJava(classNamespace);
		String classWithPackage = javaPackage + "." + className;

		try {
			Class<?> classToParseTo = Class.forName(classWithPackage);
			return getObjectFromMessage(json, classToParseTo);
		} catch (ClassNotFoundException e) {
			throw new DeserializationException("Exception deserializing object. Specified class \"" + className + "\" could not be found");
		}
	}

}

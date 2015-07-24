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
package edu.pitt.apollo.restserviceconnectorcommon;

import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v3_0_2.Request;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 *
 * @author nem41
 */
public class RequestHttpMessageConverter implements HttpMessageConverter<Request> {

	@Override
	public boolean canRead(Class<?> type, MediaType mt) {
		return false;
	}

	@Override
	public boolean canWrite(Class<?> type, MediaType mt) {
		return (mt.equals(MediaType.APPLICATION_XML) || mt.equals(MediaType.APPLICATION_JSON))
				&& type.getName().equals(Request.class.getName());
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		List<MediaType> supportedTypes = new ArrayList<>();
		supportedTypes.add(MediaType.APPLICATION_XML);
		supportedTypes.add(MediaType.APPLICATION_JSON);
		return supportedTypes;
	}

	@Override
	public Request read(Class<? extends Request> type, HttpInputMessage him) throws IOException, HttpMessageNotReadableException {
		try {
			Deserializer deserializer = DeserializerFactory.getDeserializer(SerializationFormat.XML);
			return deserializer.getObjectFromMessage(him.getBody().toString(), Request.class);
		} catch (DeserializationException | UnsupportedSerializationFormatException ex) {
			throw new HttpMessageNotReadableException("The object could not be parsed from XML");
		}
	}

	@Override
	public void write(Request t, MediaType mt, HttpOutputMessage hom) throws IOException, HttpMessageNotWritableException {
		try {
			Serializer serializer;
			if (mt.equals(MediaType.APPLICATION_XML)) {
				serializer = SerializerFactory.getSerializer(SerializationFormat.XML);
			} else if (mt.equals(MediaType.APPLICATION_JSON)) {
				serializer = SerializerFactory.getSerializer(SerializationFormat.JSON);
			} else {
				throw new HttpMessageNotWritableException("Unsupported media type: " + mt);
			}
			String serializedObject = serializer.serializeObject(t);
			hom.getBody().write(serializedObject.getBytes());
		} catch (SerializationException | UnsupportedSerializationFormatException ex) {
			throw new HttpMessageNotWritableException("Could not serialize object: " + ex.getMessage());
		}
	}

}

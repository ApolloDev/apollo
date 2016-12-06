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


import edu.pitt.apollo.services_common.v4_0_1.Response;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.Deserializer;
import edu.pitt.isg.objectserializer.DeserializerFactory;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.SerializerFactory;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nem41
 */
public class ResponseHttpMessageConverter implements HttpMessageConverter<Response> {

	@Override
	public boolean canRead(Class<?> type, MediaType mt) {
		return true;
	}

	@Override
	public boolean canWrite(Class<?> type, MediaType mt) {
		return (mt.equals(MediaType.APPLICATION_XML) || mt.equals(MediaType.APPLICATION_JSON))
				&& type.getName().equals(Response.class.getName());
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		List<MediaType> supportedTypes = new ArrayList<>();
		supportedTypes.add(MediaType.APPLICATION_XML);
		supportedTypes.add(MediaType.APPLICATION_JSON);
		return supportedTypes;
	}

	@Override
	public Response read(Class<? extends Response> type, HttpInputMessage him) throws IOException, HttpMessageNotReadableException {
		try {
			Deserializer deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.XML);

			java.util.Scanner s = new java.util.Scanner(him.getBody()).useDelimiter("\\A");
			String objectString = s.hasNext() ? s.next() : "";
			
			return deserializer.getObjectFromMessage(objectString, Response.class);
		} catch (DeserializationException | UnsupportedSerializationFormatException ex) {
			throw new HttpMessageNotReadableException("The object could not be parsed from XML");
		}
	}

	@Override
	public void write(Response t, MediaType mt, HttpOutputMessage hom) throws IOException, HttpMessageNotWritableException {
		try {
			Serializer serializer;
			if (mt.equals(MediaType.APPLICATION_XML)) {
				serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.XML, Arrays.asList(ApolloClassList.classList));
			} else if (mt.equals(MediaType.APPLICATION_JSON)) {
				serializer = SerializerFactory.getSerializer(edu.pitt.isg.objectserializer.SerializationFormat.JSON, Arrays.asList(ApolloClassList.classList));
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

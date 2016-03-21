/*
 * Copyright 2016 nem41.
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
package edu.pitt.apollo.filestoreservicerestfrontend.methods;

import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;
import edu.pitt.apollo.utilities.Serializer;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class ListFilesForRunMethod extends BaseFileStoreMethod {

	public ListFilesForRunMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String listFilesForRun(BigInteger runId) throws SerializationException {
		try {
			List<FileIdentification> files = fileStoreService.listFilesForRun(runId, null);

			List<String> serializedObjects = new ArrayList<>();
			for (FileIdentification file : files) {
				String serializedObject = serializer.serializeObject(file);
				serializedObjects.add(serializedObject);
			}

            ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
            serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
            serializationInformation.setClassName(FileIdentification.class.getSimpleName());
            serializationInformation.setFormat(SerializationFormat.XML);

            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.addContentToBody(serializedObjects).setIsBodySerialized(true).setResponseBodySerializationInformation(serializationInformation);

		} catch (FilestoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}

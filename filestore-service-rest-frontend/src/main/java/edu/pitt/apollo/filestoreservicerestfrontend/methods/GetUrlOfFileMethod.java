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
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.filestoreservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class GetUrlOfFileMethod extends BaseFileStoreMethod {

	public GetUrlOfFileMethod(SerializationFormat serializationFormat, String authorization) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		super(serializationFormat, authorization);
	}

	public String getUrlOfFile(BigInteger runId, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType) throws SerializationException {
		try {
			String url = fileStoreService.getUrlOfFile(runId, filename, fileFormat, fileType, authentication);

			responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
					.addContentToBody(url).setIsBodySerialized(false);

		} catch (FilestoreException ex) {
			responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}
}

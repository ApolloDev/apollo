/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.restservice.methods;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.AssociateContentWithRunIdMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restservice.rest.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_0_0.Request;
import edu.pitt.apollo.services_common.v3_0_0.RequestMeta;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.XMLDeserializer;
import java.math.BigInteger;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class AssociateContentWithRunIdMethod {

	public static String associateContentWithRunId(String username, String password, BigInteger runId, String messageBody, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException, SerializationException {
		ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();
		try {
			Request requestMessageObject = new XMLDeserializer().getObjectFromMessage(messageBody, Request.class);
			RequestMeta meta = requestMessageObject.getRequestMeta();
			ObjectSerializationInformation config = meta.getRequestBodySerializationInformation();

			SerializationFormat format = config.getFormat();
			Deserializer deserializer = DeserializerFactory.getDeserializer(format);

			String className = config.getClassName();
			String classNamespace = config.getClassNameSpace();

			Object object = deserializer.getObjectFromMessage(messageBody, className, classNamespace);

			if (!(object instanceof AssociateContentWithRunIdMessage)) {
				responseBuilder.setStatus(HttpStatus.BAD_REQUEST, "The object in the message body was not an instance of AssociateContentWithRunIdMessage");
			} else {
				AssociateContentWithRunIdMessage message = (AssociateContentWithRunIdMessage) object;

				DataServiceImpl impl = new DataServiceImpl();

				String content = message.getFileContentOrUrl();
				SoftwareIdentification sourceSoftware = message.getSourceSoftware();
				SoftwareIdentification destinationSoftware = message.getDestinationSoftware();
				String contentLabel = message.getContentLabel();
				ContentDataFormatEnum contentFormat = message.getContentDataFormat();
				ContentDataTypeEnum contentType = message.getContentDataType();

				Authentication authentication = new Authentication();
				authentication.setRequesterId(username);
				authentication.setRequesterPassword(password);

				try {
					impl.associateContentWithRunId(runId, content, sourceSoftware, destinationSoftware,
							contentLabel, contentFormat, contentType, authentication);

					responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
				} catch (DataServiceException ex) {
					responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			}
		} catch (DeserializationException | UnsupportedSerializationFormatException ex) {
			responseBuilder.setStatus(HttpStatus.OK, ex.getMessage());
		}

		Serializer serializer = SerializerFactory.getSerializer(serializationFormat, Serializer.APOLLO_NAMESPACE, Serializer.APOLLO_NAMESPACE_TNS_PREFIX);
		return serializer.serializeObject(responseBuilder.getResponse());

	}

}

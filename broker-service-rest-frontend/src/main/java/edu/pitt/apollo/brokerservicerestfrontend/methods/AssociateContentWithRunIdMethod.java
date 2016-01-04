/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.apolloservice.methods.content.BaseDataServiceAccessorMethod;
import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.data_service_types.v4_0.AssociateContentWithRunIdMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v4_0.*;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.XMLDeserializer;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class AssociateContentWithRunIdMethod extends BaseBrokerServiceAccessorMethod {

	public AssociateContentWithRunIdMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String associateContentWithRunId(BigInteger runId, String messageBody) throws UnsupportedSerializationFormatException, SerializationException {
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

				String content = message.getFileContentOrUrl();
				SoftwareIdentification sourceSoftware = message.getSourceSoftware();
				SoftwareIdentification destinationSoftware = message.getDestinationSoftware();
				String contentLabel = message.getContentLabel();
				ContentDataFormatEnum contentFormat = message.getContentDataFormat();
				ContentDataTypeEnum contentType = message.getContentDataType();

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

		return serializer.serializeObject(responseBuilder.getResponse());

	}

}

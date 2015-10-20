/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.dataservicerestfrontend.methods;

import edu.pitt.apollo.data_service_types.v3_1_0.AddRoleMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.dataservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v3_1_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v3_1_0.Request;
import edu.pitt.apollo.services_common.v3_1_0.RequestMeta;
import edu.pitt.apollo.services_common.v3_1_0.SerializationFormat;
import edu.pitt.apollo.types.v3_1_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.XMLDeserializer;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class AddRoleMethod extends BaseDataServiceAccessorMethod {

	public AddRoleMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String addRole(String messageBody) throws UnsupportedSerializationFormatException, SerializationException {

		try {
			Request requestMessageObject = new XMLDeserializer().getObjectFromMessage(messageBody, Request.class);
			RequestMeta meta = requestMessageObject.getRequestMeta();
			ObjectSerializationInformation config = meta.getRequestBodySerializationInformation();

			SerializationFormat format = config.getFormat();
			Deserializer deserializer = DeserializerFactory.getDeserializer(format);

			String className = config.getClassName();
			String classNamespace = config.getClassNameSpace();

			Object object = deserializer.getObjectFromMessage(messageBody, className, classNamespace);

			if (!(object instanceof AddRoleMessage)) {
				responseBuilder.setStatus(HttpStatus.BAD_REQUEST, "The object in the message body was not an instance of AddRoleMessage");
			} else {
				AddRoleMessage message = (AddRoleMessage) object;

				SoftwareIdentification softwareId = message.getSoftwareIdentification();
				boolean canRunSoftware = message.isCanRun();
				boolean canRequestPrivileged = message.isCanRequestPrivileged();
				String roleDescription = message.getRoleDescription();

				try {
					impl.addRole(softwareId, canRunSoftware, canRequestPrivileged, roleDescription, authentication);

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

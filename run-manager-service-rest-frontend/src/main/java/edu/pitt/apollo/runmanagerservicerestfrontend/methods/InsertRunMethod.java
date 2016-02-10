/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.runmanagerservicerestfrontend.methods;

import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v4_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0.Request;
import edu.pitt.apollo.services_common.v4_0.RequestMeta;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.XMLDeserializer;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nem41
 */
public class InsertRunMethod extends BaseRunManagerServiceAccessorMethod {

	public InsertRunMethod(String username, String password, SerializationFormat serializationFormat) throws UnsupportedSerializationFormatException {
		super(username, password, serializationFormat);
	}

	public String insertRun(String messageBody) throws UnsupportedSerializationFormatException, SerializationException {

		try {
			Request requestMessageObject = new XMLDeserializer().getObjectFromMessage(messageBody, Request.class);
			RequestMeta meta = requestMessageObject.getRequestMeta();
			ObjectSerializationInformation config = meta.getRequestBodySerializationInformation();

			SerializationFormat format = config.getFormat();
			Deserializer deserializer = DeserializerFactory.getDeserializer(format);

			String className = config.getClassName();
			String classNamespace = config.getClassNameSpace();

			RunMessage object = (RunMessage) deserializer.getObjectFromMessage(requestMessageObject.getRequestBody(), className, classNamespace);

			if (!(object instanceof RunSimulationMessage) && !(object instanceof RunSimulationsMessage)
					&& !(object instanceof RunVisualizationMessage) && !(object instanceof DataRetrievalRequestMessage)
					&& !(object instanceof RunInfectiousDiseaseTransmissionExperimentMessage)) {
				responseBuilder.setStatus(HttpStatus.BAD_REQUEST, "The object in the message body was not an instance of a valid run message type. "
						+ "The valid types are: RunSimulationMessage, RunSimulationsMessage, RunVisualizationMessage, RunInfectiousDiseaseTransmissionExperimentMessage");
			} else {

				try {
					InsertRunResult insertRunResult = impl.insertRun(object);

					ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
					objectSerializationInformation.setClassNameSpace(Serializer.SERVICES_COMMON_NAMESPACE);
					objectSerializationInformation.setClassName(insertRunResult.getClass().getSimpleName());
					objectSerializationInformation.setFormat(SerializationFormat.XML);

					String serializedObject = serializer.serializeObject(insertRunResult);
					responseBuilder.setResponseBodySerializationInformation(objectSerializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);
					responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
				} catch (RunManagementException ex) {
					responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
				}
			}
		} catch (DeserializationException | UnsupportedSerializationFormatException ex) {
			responseBuilder.setStatus(HttpStatus.OK, ex.getMessage());
		}

		return serializer.serializeObject(responseBuilder.getResponse());
	}

}

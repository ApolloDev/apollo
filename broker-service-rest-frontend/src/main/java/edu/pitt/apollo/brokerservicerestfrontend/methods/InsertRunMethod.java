/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.apollo_service_types.v4_0_1.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage;
import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.data_service_types.v4_0_1.DataRetrievalRequestMessage;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.services_common.v4_0_1.*;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;
import edu.pitt.isg.objectserializer.Deserializer;
import edu.pitt.isg.objectserializer.DeserializerFactory;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.XMLDeserializer;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

/**
 * @author nem41
 */
public class InsertRunMethod extends BaseBrokerServiceAccessorMethod {

    public InsertRunMethod(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
        super(serializationFormat, authorizationHeader);
    }

    public String insertRun(String messageBody) throws UnsupportedSerializationFormatException, SerializationException {

        try {
            Request requestMessageObject = new XMLDeserializer().getObjectFromMessage(messageBody, Request.class);
            RequestMeta meta = requestMessageObject.getRequestMeta();
            ObjectSerializationInformation config = meta.getRequestBodySerializationInformation();

            SerializationFormat format = config.getFormat();
            Deserializer deserializer;
            switch (format) {
                case JSON:
                    deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.JSON);
                    break;
                case XML:
                    deserializer = DeserializerFactory.getDeserializer(edu.pitt.isg.objectserializer.SerializationFormat.XML);
                    break;
                default:
                    deserializer = null;
                    break;
            }

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
                    InsertRunResult insertRunResult = impl.insertRun(object, authentication);

                    ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
                    objectSerializationInformation.setClassNameSpace(Serializer.SERVICES_COMMON_NAMESPACE);
                    objectSerializationInformation.setClassName(insertRunResult.getClass().getSimpleName());
                    objectSerializationInformation.setFormat(SerializationFormat.XML);

                    String serializedObject = serializer.serializeObject(insertRunResult);
                    responseBuilder.setResponseBodySerializationInformation(objectSerializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);
                    responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE);
                } catch (DatastoreException ex) {
                    responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
                }
            }
        } catch (DeserializationException | UnsupportedSerializationFormatException | RunManagementException ex) {
            responseBuilder.setStatus(HttpStatus.OK, ex.getMessage());
        }

        return serializer.serializeObject(responseBuilder.getResponse());
    }

}

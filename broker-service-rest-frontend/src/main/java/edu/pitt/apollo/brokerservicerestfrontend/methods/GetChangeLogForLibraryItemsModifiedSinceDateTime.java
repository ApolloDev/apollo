package edu.pitt.apollo.brokerservicerestfrontend.methods;

import edu.pitt.apollo.brokerservicerestfrontend.utils.ResponseMessageBuilder;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.library_service_types.v4_0_1.AddReviewerCommentResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.services_common.v4_0_1.ObjectSerializationInformation;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.Serializer;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.http.HttpStatus;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jdl50 on 8/13/15.
 */
public class GetChangeLogForLibraryItemsModifiedSinceDateTime extends BaseBrokerServiceAccessorMethod {

    public GetChangeLogForLibraryItemsModifiedSinceDateTime(SerializationFormat serializationFormat, String authorizationHeader) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
        super(serializationFormat, authorizationHeader);
    }

    public String getChangeLogForLibraryItemsModifiedSinceDateTime(String dateTime) throws SerializationException {
        
		GetChangeLogForLibraryItemsModifiedSinceDateTimeResult result;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(dateTime);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);

            result = impl.getChangeLogForLibraryItemsModifiedSinceDateTime(xmlGregorianCalendar, authentication);
			
			ObjectSerializationInformation serializationInformation = new ObjectSerializationInformation();
            serializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
            serializationInformation.setClassName(GetChangeLogForLibraryItemsModifiedSinceDateTimeResult.class.getSimpleName());
            serializationInformation.setFormat(SerializationFormat.XML);

            String serializedObject = serializer.serializeObject(result);

            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
                    .setResponseBodySerializationInformation(serializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);
			
        } catch (ParseException | DatatypeConfigurationException | LibraryServiceException e) {
            responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName() + ": " + e.getMessage());
        }
        try {
            return serializer.serializeObject(responseBuilder.getResponse());
        } catch (SerializationException e) {
            return "Error: " + e.getClass().getName() + ": " + e.getMessage();
        }
    }
}

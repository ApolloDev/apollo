package edu.pitt.apollo.filestoreservicerestfrontend.controller;

import edu.pitt.apollo.filestoreservicerestfrontend.utils.ResponseMessageBuilder;
import com.wordnik.swagger.annotations.ApiParam;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.interfaces.FilestoreServiceInterface;
import edu.pitt.apollo.services_common.v4_0.*;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utilities.XMLDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mas400 on 3/9/16.
 */
@Controller
@RequestMapping("/files")
public class FileStoreController {
    protected final ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();

    static Logger logger = LoggerFactory.getLogger(FileStoreController.class);

    @RequestMapping(value = "/{runId}", method = RequestMethod.POST, headers = "Accept=text/html")
    public
    @ResponseBody
    void uploadFile(@PathVariable("runId") BigInteger runId, @RequestParam("urlToFile") String urlToFile,
                    @ApiParam(value = "File Identification", required = true) @RequestBody String messageBody) throws FilestoreException, DeserializationException {

        FileIdentification fileIdentification = new XMLDeserializer().getObjectFromMessage(messageBody, FileIdentification.class);

        FilestoreServiceInterface filestoreService = new FileStoreService();
        filestoreService.uploadFile(runId, urlToFile, fileIdentification, null);
    }

    @RequestMapping(value = "/{runId}/url", method = RequestMethod.GET, headers = "Accept=text/html")
    public String getUrlOfFile(@PathVariable("runId") BigInteger runId,
                               @RequestParam("filename") String filename, @RequestParam(required = false) ContentDataFormatEnum fileFormat,
                               @RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException {

        FilestoreServiceInterface filestoreService = new FileStoreService();

        //used for testing
//        FileIdentification fileIdentification = new FileIdentification();
//        fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
//        fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
//        fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");
//        String urlOfFile = filestoreService.getUrlOfFile(runId, fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType());
//        logger.info("File URL: " + urlOfFile);
        String urlOfFile = filestoreService.getUrlOfFile(runId, filename, fileFormat, fileType, null);
        return urlOfFile;
    }

    @RequestMapping(value = "/{runId}/status", method = RequestMethod.GET, headers = "Accept=text/html")
    public String getStatusOfFileUpload(@PathVariable("runId") BigInteger runId,
                                        @RequestParam(required = false) String filename, @RequestParam(required = false) ContentDataFormatEnum fileFormat,
                                        @RequestParam(required = false) ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
        FilestoreServiceInterface filestoreService = new FileStoreService();
        Serializer serializer = SerializerFactory.getSerializer(SerializationFormat.XML);

        try {
            //        //used for testing
            FileIdentification fileIdentification = new FileIdentification();
            fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
            fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
            fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");
            MethodCallStatus status = filestoreService.getStatusOfFileUpload(runId, fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType(), null);

//            MethodCallStatus status = filestoreService.getStatusOfFileUpload(runId, filename, fileFormat, fileType, null);

            ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
            objectSerializationInformation.setClassNameSpace(Serializer.APOLLO_NAMESPACE);
            objectSerializationInformation.setClassName(status.getClass().getSimpleName());
            objectSerializationInformation.setFormat(SerializationFormat.XML);

            String serializedObject = serializer.serializeObject(status);
            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
                    .setResponseBodySerializationInformation(objectSerializationInformation).addContentToBody(serializedObject).setIsBodySerialized(true);
        } catch (SerializationException ex) {
            responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        logger.info(serializer.serializeObject(responseBuilder.getResponse()));
        return serializer.serializeObject(responseBuilder.getResponse());
    }

    @RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=text/html")
    public String listFilesForRun(@PathVariable("runId") BigInteger runId) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
        Serializer serializer = SerializerFactory.getSerializer(SerializationFormat.XML);
        try {
            FilestoreServiceInterface filestoreService = new FileStoreService();
            List<FileIdentification> files = filestoreService.listFilesForRun(runId, null);

            List<String> serializedObjects = new ArrayList<>();
            for (FileIdentification file : files) {
                String serializedObject = serializer.serializeObject(file);
                serializedObjects.add(serializedObject);
            }
            responseBuilder.setStatus(HttpStatus.OK, ResponseMessageBuilder.DEFAULT_SUCCESS_MESSAGE)
                    .addContentToBody(serializedObjects).setIsBodySerialized(true);

        } catch (SerializationException ex) {
            responseBuilder.setStatus(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

//        logger.info("Files: " + serializer.serializeObject(responseBuilder.getResponse()));
        return serializer.serializeObject(responseBuilder.getResponse());
    }

    public static void main(String[] args) throws UnsupportedSerializationFormatException, SerializationException {
        FileIdentification fileIdentification = new FileIdentification();
        fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
        fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
        fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");

        System.out.println(SerializerFactory.getSerializer(SerializationFormat.XML).serializeObject(fileIdentification));
    }
}

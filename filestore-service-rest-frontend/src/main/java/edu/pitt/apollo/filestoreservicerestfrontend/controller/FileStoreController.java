package edu.pitt.apollo.filestoreservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiParam;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.interfaces.FilestoreServiceInterface;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.utilities.SerializerFactory;
import edu.pitt.apollo.utilities.XMLDeserializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mas400 on 3/9/16.
 */
@Controller
@RequestMapping("/files")
public class FileStoreController {

	static Logger logger = LoggerFactory.getLogger(FileStoreController.class);

	@RequestMapping(value = "/{runId}", method = RequestMethod.POST, headers = "Accept=text/html")
	public @ResponseBody
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
			@RequestParam(required = false) String filename, @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
			@RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException {
		FilestoreServiceInterface filestoreService = new FileStoreService();

//        //used for testing
//        FileIdentification fileIdentification = new FileIdentification();
//        fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
//        fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
//        fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");
//        String status = filestoreService.getStatusOfFileUpload(runId, fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType());
//        logger.info("File status: "+ status);
		String status = "";
//		= filestoreService.getStatusOfFileUpload(runId, filename, fileFormat, fileType, null);

		return status;
	}

	@RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=text/html")
	public List<FileIdentification> listFilesForRun(@PathVariable("runId") BigInteger runId) throws FilestoreException {
		FilestoreServiceInterface filestoreService = new FileStoreService();
		List<FileIdentification> files = filestoreService.listFilesForRun(runId, null);

		//used for testing
//        for(FileIdentification file : files) {
//            logger.info("File: " + file.getLabel());
//        }
		return files;
	}

	public static void main(String[] args) throws UnsupportedSerializationFormatException, SerializationException {
		FileIdentification fileIdentification = new FileIdentification();
		fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
		fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
		fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");

		System.out.println(SerializerFactory.getSerializer(SerializationFormat.XML).serializeObject(fileIdentification));
	}
}

package edu.pitt.apollo.filestoreservicerestfrontend.controller;

import edu.pitt.apollo.filestoreservicerestfrontend.utils.ResponseMessageBuilder;
import com.wordnik.swagger.annotations.ApiParam;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.GetStatusOfFileUploadMethod;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.GetUrlOfFileMethod;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.ListFilesForRunMethod;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.UploadFileMethod;
import edu.pitt.apollo.services_common.v4_0.*;
import edu.pitt.apollo.utilities.SerializerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

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
	public String uploadFile(@PathVariable("runId") BigInteger runId, @RequestParam("urlToFile") String urlToFile,
			@ApiParam(value = "File Identification", required = true) @RequestBody String messageBody) throws FilestoreException, DeserializationException, UnsupportedSerializationFormatException, SerializationException {

		return new UploadFileMethod(null, null, SerializationFormat.XML).uploadFile(runId, urlToFile, messageBody, null);
	}

	@RequestMapping(value = "/{runId}/url", method = RequestMethod.GET, headers = "Accept=text/html")
	public String getUrlOfFile(@PathVariable("runId") BigInteger runId,
			@RequestParam("filename") String filename, @RequestParam(required = false) ContentDataFormatEnum fileFormat,
			@RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {

		return new GetUrlOfFileMethod(null, null, SerializationFormat.XML).getUrlOfFile(runId, filename, fileFormat, fileType, null);
	}

	@RequestMapping(value = "/{runId}/status", method = RequestMethod.GET, headers = "Accept=text/html")
	public String getStatusOfFileUpload(@PathVariable("runId") BigInteger runId,
			@RequestParam(required = false) String filename, @RequestParam(required = false) ContentDataFormatEnum fileFormat,
			@RequestParam(required = false) ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new GetStatusOfFileUploadMethod(null, null, SerializationFormat.XML).getStatusOfFileUpload(runId, filename, fileFormat, fileType, null);
	}

	@RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=text/html")
	public String listFilesForRun(@PathVariable("runId") BigInteger runId) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new ListFilesForRunMethod(null, null, SerializationFormat.XML).listFilesForRun(runId);
	}

	public static void main(String[] args) throws UnsupportedSerializationFormatException, SerializationException {
		FileIdentification fileIdentification = new FileIdentification();
		fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
		fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
		fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");

		System.out.println(SerializerFactory.getSerializer(SerializationFormat.XML).serializeObject(fileIdentification));
	}
}

package edu.pitt.apollo.brokerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiParam;
import edu.pitt.apollo.brokerservicerestfrontend.methods.GetStatusOfFileUploadMethod;
import edu.pitt.apollo.brokerservicerestfrontend.methods.GetUrlOfFileMethod;
import edu.pitt.apollo.brokerservicerestfrontend.methods.ListFilesForRunMethod;
import edu.pitt.apollo.brokerservicerestfrontend.methods.UploadFileMethod;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/18/15. Purpose: This class contains the RESTful interfaces associated with individual file retrieval and manipulation.
 */
@Controller
@RequestMapping("/ws/files")
public class FilesController {

	@RequestMapping(value = "/{runId}", method = RequestMethod.POST, headers = "Accept=text/html")
	public @ResponseBody
	String uploadFile(@PathVariable("runId") BigInteger runId, @RequestParam("urlToFile") String urlToFile,
			@ApiParam(value = "File Identification", required = true) @RequestBody String messageBody) throws FilestoreException, DeserializationException, UnsupportedSerializationFormatException, SerializationException {

		return new UploadFileMethod("", "", SerializationFormat.XML).uploadFile(runId, urlToFile, messageBody);
	}

	@RequestMapping(value = "/{runId}/url", method = RequestMethod.GET, headers = "Accept=text/html")
	public String getUrlOfFile(@PathVariable("runId") BigInteger runId,
			@RequestParam("filename") String filename, @RequestParam(required = false) ContentDataFormatEnum fileFormat,
			@RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {

		return new GetUrlOfFileMethod("", "", SerializationFormat.XML).getUrlOfFile(runId, fileFormat, fileType, filename);

	}

	@RequestMapping(value = "/{runId}/status", method = RequestMethod.GET, headers = "Accept=text/html")
	public String getStatusOfFileUpload(@PathVariable("runId") BigInteger runId,
			@RequestParam(required = false) String filename, @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
			@RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new GetStatusOfFileUploadMethod("", "", SerializationFormat.XML).getStatus(runId, fileFormat, fileType, filename);
	}

	@RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=text/html")
	public String listFilesForRun(@PathVariable("runId") BigInteger runId) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new ListFilesForRunMethod("", "", SerializationFormat.XML).listFilesForRun(runId);
	}
}

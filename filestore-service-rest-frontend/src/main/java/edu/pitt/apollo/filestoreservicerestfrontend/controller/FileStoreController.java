package edu.pitt.apollo.filestoreservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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

import javax.ws.rs.GET;
import javax.ws.rs.POST;

/**
 * Created by mas400 on 3/9/16.
 */
@Controller
@RequestMapping("/ws/files")
public class FileStoreController {

	protected final ResponseMessageBuilder responseBuilder = new ResponseMessageBuilder();

	static Logger logger = LoggerFactory.getLogger(FileStoreController.class);

    @POST
    @ApiOperation(value = "Upload file.", notes = "Uploads a file for the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
	@RequestMapping(value = "/{runId}", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
    String uploadFile(@PathVariable("runId") BigInteger runId, @RequestParam("urlToFile") String urlToFile,
			@ApiParam(value = "File Identification", required = true) @RequestBody String messageBody) throws FilestoreException, DeserializationException, UnsupportedSerializationFormatException, SerializationException {

		return new UploadFileMethod(null, null, SerializationFormat.XML).uploadFile(runId, urlToFile, messageBody, null);
	}

    @GET
    @ApiOperation(value = "Get the URL for a file.", notes = "Returns the URL for a file.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
	@RequestMapping(value = "/{runId}/url", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
    String getUrlOfFile(@PathVariable("runId") BigInteger runId,
			@RequestParam("fileName") String filename, @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
			@RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {

		return new GetUrlOfFileMethod(null, null, SerializationFormat.XML).getUrlOfFile(runId, filename, fileFormat, fileType, null);
	}

    @GET
    @ApiOperation(value = "Get the status of a file upload.", notes = "Returns the status of a file upload.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
	@RequestMapping(value = "/{runId}/status", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
    String getStatusOfFileUpload(@PathVariable("runId") BigInteger runId,
			@RequestParam("fileName") String filename, @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
			@RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new GetStatusOfFileUploadMethod(null, null, SerializationFormat.XML).getStatusOfFileUpload(runId, filename, fileFormat, fileType, null);
	}

    @GET
    @ApiOperation(value = "Get the list of files for a run.", notes = "Returns a list of the files for a run.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
	@RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
    String listFilesForRun(@PathVariable("runId") BigInteger runId) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
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

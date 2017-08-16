package edu.pitt.apollo.filestoreservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.filestore_service_types.v4_0_2.FileIdentification;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.GetStatusOfFileUploadMethod;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.GetUrlOfFileMethod;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.ListFilesForRunMethod;
import edu.pitt.apollo.filestoreservicerestfrontend.methods.UploadFileMethod;
import edu.pitt.apollo.services_common.v4_0_2.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_2.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.isg.objectserializer.exceptions.DeserializationException;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;

/**
 * Created by mas400 on 3/9/16.
 */
@Controller
@RequestMapping("/ws/files")
public class FileStoreController {

	@POST
	@ApiOperation(value = "Upload file.", notes = "Uploads a file for the given run ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/{runId}", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String uploadFile(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "URL of the file to upload", required = true) @RequestParam("urlToFile") String urlToFile,
			@ApiParam(value = "Name of the file", required = true) @RequestParam("fileName") String filename,
			@ApiParam(value = "Format of the file", required = true) @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
			@ApiParam(value = "Type of the file", required = true) @RequestParam("fileType") ContentDataTypeEnum fileType,
                      @RequestHeader("Authorization") String authorization
	) throws FilestoreException, DeserializationException, UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new UploadFileMethod(SerializationFormat.XML, authorization).uploadFile(runId, urlToFile, filename,
				fileFormat, fileType);
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
			@RequestParam("fileType") ContentDataTypeEnum fileType, @RequestHeader("Authorization") String authorization) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		return new GetUrlOfFileMethod(SerializationFormat.XML, authorization).getUrlOfFile(runId, filename, fileFormat, fileType);
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
			@RequestParam("fileType") ContentDataTypeEnum fileType, @RequestHeader("Authorization") String authorization) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		return new GetStatusOfFileUploadMethod(SerializationFormat.XML, authorization).getStatusOfFileUpload(runId, filename, fileFormat, fileType);
	}

	@GET
	@ApiOperation(value = "Get the list of files for a run.", notes = "Returns a list of the files for a run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String listFilesForRun(@PathVariable("runId") BigInteger runId, @RequestHeader("Authorization") String authorization) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		return new ListFilesForRunMethod(SerializationFormat.XML, authorization).listFilesForRun(runId);
	}

	public static void main(String[] args) throws UnsupportedSerializationFormatException, SerializationException {
		FileIdentification fileIdentification = new FileIdentification();
		fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
		fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
		fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");

		//System.out.println(SerializerFactory.getSerializer(SerializationFormat.XML).serializeObject(fileIdentification));
	}
}

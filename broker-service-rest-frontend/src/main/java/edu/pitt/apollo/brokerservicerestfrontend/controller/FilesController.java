package edu.pitt.apollo.brokerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;

/**
 * Created by dcs27 on 5/18/15. Purpose: This class contains the RESTful interfaces associated with individual file retrieval and manipulation.
 */
@Controller
@RequestMapping("/ws/files")
public class FilesController {

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
			@ApiParam(value = "Type of the file", required = true) @RequestParam("fileType") ContentDataTypeEnum fileType
	) throws FilestoreException, DeserializationException, UnsupportedSerializationFormatException, SerializationException {

		return new UploadFileMethod(null, null, SerializationFormat.XML).uploadFile(runId, urlToFile, filename,
				fileFormat, fileType);
	}

	@GET
	@ApiOperation(value = "Get the URL for a file.", notes = "Returns the URL for a file.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/{runId}/url", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getUrlOfFileXML(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Name of the file", required = true) @RequestParam("fileName") String filename,
			@ApiParam(value = "Format of the file", required = true) @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
			@ApiParam(value = "Type of the file", required = true) @RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {

		return new GetUrlOfFileMethod(null, null, SerializationFormat.XML).getUrlOfFile(runId, fileFormat, fileType, filename);
	}

    @GET
    @ApiOperation(value = "Get the URL for a file.", notes = "Returns the URL for a file.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/{runId}/url", method = RequestMethod.GET, headers = "Accept=application/json")
    public @ResponseBody
    String getUrlOfFileJSON(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
                        @ApiParam(value = "Name of the file", required = true) @RequestParam("fileName") String filename,
                        @ApiParam(value = "Format of the file", required = true) @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
                        @ApiParam(value = "Type of the file", required = true) @RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {

        return new GetUrlOfFileMethod(null, null, SerializationFormat.JSON).getUrlOfFile(runId, fileFormat, fileType, filename);
    }

	@GET
	@ApiOperation(value = "Get the status of a file upload.", notes = "Returns the status of a file upload.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/{runId}/status", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getStatusOfFileUpload(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Name of the file", required = true) @RequestParam("fileName") String filename,
			@ApiParam(value = "Format of the file", required = true) @RequestParam("fileFormat") ContentDataFormatEnum fileFormat,
			@ApiParam(value = "Type of the file", required = true) @RequestParam("fileType") ContentDataTypeEnum fileType) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new GetStatusOfFileUploadMethod(null, null, SerializationFormat.XML).getStatus(runId, fileFormat, fileType, filename);
	}

	@GET
	@ApiOperation(value = "Get the list of files for a run.", notes = "Returns a list of the files for a run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String listFilesForRunXML(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new ListFilesForRunMethod(null, null, SerializationFormat.XML).listFilesForRun(runId);
	}

	@GET
	@ApiOperation(value = "Get the list of files for a run.", notes = "Returns a list of the files for a run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/{runId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	String listFilesForRunJSON(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId) throws FilestoreException, UnsupportedSerializationFormatException, SerializationException {
		return new ListFilesForRunMethod(null, null, SerializationFormat.JSON).listFilesForRun(runId);
	}
}

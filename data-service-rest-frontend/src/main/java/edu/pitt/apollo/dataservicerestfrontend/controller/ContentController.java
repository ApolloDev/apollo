package edu.pitt.apollo.dataservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.dataservicerestfrontend.methods.GetContentByIdMethod;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.math.BigInteger;

/**
 * Created by dcs27 on 5/18/15. Purpose: This class contains the RESTful interfaces associated with individual file retrieval and manipulation.
 */
@Controller
@RequestMapping("/ws")
public class ContentController {

	/*--Methods for the file resource of a run--*/
	@GET
	@ApiOperation(value = "Get file.", notes = "Returns the requested file.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/content/{contentId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getFileOfRunUsingRunAndFileId(@ApiParam(value = "File ID.", required = true) @PathVariable("contentId") BigInteger contentId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return new GetContentByIdMethod(username, password, SerializationFormat.XML).getContent(contentId);
	}

	/* @DELETE
	 @ApiOperation(value = "Remove file reference.", notes = "Removes the reference of a file from the given run ID.", response = String.class)
	 @ApiResponses(value = {
	 @ApiResponse(code = 200, message = "")
	 })
	 @RequestMapping(value = "/file/{fileId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	 public
	 @ResponseBody
	 String removeReferenceOfFileFromRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,@ApiParam(value = "File ID.", required = true) @PathVariable("fileId") BigInteger fileId) {
	 return null;
	 }*/
    //We cannot create anything at the file level (PUT), and we cannot edit a file at this level (POST).
}

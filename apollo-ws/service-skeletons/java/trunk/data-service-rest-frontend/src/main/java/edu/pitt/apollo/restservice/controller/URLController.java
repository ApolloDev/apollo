package edu.pitt.apollo.restservice.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restservice.methods.GetContentByIdMethod;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.math.BigInteger;

/**
 * Created by dcs27 on 5/18/15. * Purpose: This class contains the RESTful interfaces associated with individual URL retrieval and manipulation.
 */
@Controller
@RequestMapping("/ws")
public class URLController {
	/*--Methods for the url resource of a run--*/

	@GET
	@ApiOperation(value = "Get URL.", notes = "Returns the requested URL.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/url/{urlId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getURLOfRunUsingRunAndFileId(@ApiParam(value = "URL ID.", required = true) @PathVariable("urlId") BigInteger urlId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return new GetContentByIdMethod(username, password, SerializationFormat.XML).getContent(urlId);
	}


	/* @DELETE
	 @ApiOperation(value = "Remove URL reference.", notes = "Removes the reference of a URL from the given run ID.", response = String.class)
	 @ApiResponses(value = {
	 @ApiResponse(code = 200, message = "")
	 })
	 @RequestMapping(value = "/url/{urlId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	 public
	 @ResponseBody
	 String removeReferenceOfURLFromRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,@ApiParam(value = "URL ID.", required = true) @PathVariable("urlId") BigInteger urlId) {
	 return null;
	 }*/
    //We cannot create anything at the URL level (PUT), and we cannot edit a URL at this level (POST).
}

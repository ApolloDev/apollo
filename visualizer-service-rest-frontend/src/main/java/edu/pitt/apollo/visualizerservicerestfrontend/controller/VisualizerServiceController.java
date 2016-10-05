package edu.pitt.apollo.visualizerservicerestfrontend.controller;

import edu.pitt.apollo.visualizerservicerestfrontend.utils.StartRunMethod;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.visualizerservicerestfrontend.exception.UnsupportedRunActionException;
import edu.pitt.apollo.services_common.v4_0.RunActionEnum;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import javax.ws.rs.POST;

/**
 * Created by dcs27 on 5/15/15. Purpose: This class contains the RESTful interfaces associated with the runs collection.
 */
@Controller
@RequestMapping("/ws")
public class VisualizerServiceController {

	@POST
	@ApiOperation(value = "Perform action on the run.", notes = "Performs an action on the run (start or terminate).", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String run(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Action", required = true) @RequestParam("action") RunActionEnum action,
               @RequestHeader("Authorization") String authorization)
			throws UnsupportedSerializationFormatException, SerializationException,
			UnsupportedRunActionException, FilestoreException, RunManagementException {

		return new StartRunMethod(SerializationFormat.XML, authorization).startRun(runId);
	}
}

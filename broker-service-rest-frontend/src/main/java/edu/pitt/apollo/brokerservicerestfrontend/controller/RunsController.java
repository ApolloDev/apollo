package edu.pitt.apollo.brokerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.brokerservicerestfrontend.exception.UnsupportedRunActionException;
import edu.pitt.apollo.brokerservicerestfrontend.methods.*;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_1.RunActionEnum;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.apollo.types.v4_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;

/**
 * Created by dcs27 on 5/15/15. Purpose: This class contains the RESTful interfaces associated with the runs collection.
 */
@Controller
@RequestMapping("/ws")
public class RunsController {
	
	@GET
	@ApiOperation(value = "Get list of simulation group IDs for run", notes = "Returns a list of the simulation group IDs for a run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/rungroup", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getRunIdsInSimulationGroup(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                      @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new GetRunIdsInSimulationGroupForRunMethod(SerializationFormat.XML, authorization).getRunIdsInSimulationGroupForRun(runId);
	}

	@GET
	@ApiOperation(value = "Get software identification", notes = "Returns the software identification associated with the given run ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/softwareIdentification", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getSoftwareIdentificationForRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                           @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new GetSoftwareIdentificationForRunMethod(SerializationFormat.XML, authorization).getSoftwareIdentificationForRun(runId);
	}
	
	@POST
	@ApiOperation(value = "Insert and start run", notes = "Inserts the provided run configuration into the runs collection and then starts the run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/runs", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String postRunToRunsCollection(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Run message", required = true) @RequestBody String messageBody) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		return new InsertAndStartRunMethod(SerializationFormat.XML, authorization).insertAndStartRun(messageBody);
	}
	
	@POST
	@ApiOperation(value = "Set status", notes = "Sets the status of a given run using a MethodCallStatusEnum and a status message.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/status", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String updateStatusOfRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Method call status enum", required = true) @RequestParam("methodCallStatusEnum") MethodCallStatusEnum statusToUpdateTo,
			@ApiParam(value = "Status message", required = true) @RequestParam("statusMessage") String statusMessage,
                             @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new SetStatusOfRunMethod(SerializationFormat.XML, authorization).setStatusOfRun(runId, statusToUpdateTo, statusMessage);
	}
	
	@POST
	@ApiOperation(value = "Set last service to be called for run", notes = "Sets the provided software as the last service to be called for the given run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/lastServiceToBeCalled", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String updateLastServiceToBeCalledForRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Software name", required = true) @RequestParam("softwareName") String softwareName,
			@ApiParam(value = "Software version", required = true) @RequestParam("softwareVersion") String softwareVersion,
			@ApiParam(value = "Software developer", required = true) @RequestParam("softwareDeveloper") String softwareDeveloper,
			@ApiParam(value = "Apollo software type enum", required = true) @RequestParam("softwareTypeEnum") ApolloSoftwareTypeEnum softwareTypeEnum,
                                             @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new SetLastServiceToBeCalledForRunMethod(SerializationFormat.XML, authorization).setLastServiceToBeCalledForRunMethod(runId, softwareName,
                softwareVersion, softwareDeveloper, softwareTypeEnum);
	}
	
	@GET
	@ApiOperation(value = "Get last service to be called for run", notes = "Returns the software identification of the last service to be called for the given run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/lastServiceToBeCalled", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getLastServiceToBeCalledForRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
                                          @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new GetLastServiceToBeCalledForRunMethod(SerializationFormat.XML, authorization).getLastServiceToBeCalledForRun(runId);
	}
	
	@POST
	@ApiOperation(value = "Add run IDs to simulation group for run", notes = "Adds a list of run IDs to the simulation group for the given run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/rungroup", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String addRunIdsToSimulationGroupForRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Comma-separated list of run IDs to add to simulation group", required = true) @RequestParam("runIdsToAssociate") String runIdsToAssociate,
                                            @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new AddRunIdsToSimulationGroupForRun(SerializationFormat.XML, authorization).addRunIdsToSimulationGroupForRun(runId, runIdsToAssociate);
		
	}
	
	@DELETE
	@ApiOperation(value = "Delete run", notes = "Deletes all run data associated with the given run ID from the system.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	public @ResponseBody
	String deleteRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
                     @RequestHeader("Authorization") String authorization) throws SerializationException, UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		
		return new DeleteRunMethod(SerializationFormat.XML, authorization).deleteRun(runId);
	}
	
	@GET
	@ApiOperation(value = "Get status", notes = "Returns the MethodCallStatus associated with the given run", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/status", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getStatusOfRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {


		return new GetStatusOfRunMethod(SerializationFormat.XML, authorization).getStatusForRun(runId);
	}
	
	@POST
	@ApiOperation(value = "Perform action on the run", notes = "Performs an action on the run (currently only termination supported).", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String run(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Action (only TERMINATE supported)", required = true) @RequestParam("action") RunActionEnum action,
               @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedRunActionException, UnsupportedAuthorizationTypeException {
		
		switch (action) {
//			case START:
//				return new StartRunMethod(username, password, SerializationFormat.XML).startRun(runId);
			case TERMINATE:
				return new TerminateRunMethod(SerializationFormat.XML, authorization).terminateRun(runId);
			default:
				throw new UnsupportedRunActionException("The specified run action value \"" + action + "\" is not recognized");
		}
		
	}
}

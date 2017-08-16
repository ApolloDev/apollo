package edu.pitt.apollo.runmanagerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.runmanagerservicerestfrontend.exception.UnsupportedRunActionException;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.*;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_2.RunActionEnum;
import edu.pitt.apollo.services_common.v4_0_2.SerializationFormat;
import edu.pitt.apollo.types.v4_0_2.ApolloSoftwareTypeEnum;
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
	@ApiOperation(value = "Get simulation group IDs for run.", notes = "Returns the simulation group IDs for run using a comma separated input.", response = String.class)
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
	@ApiOperation(value = "Get software identification.", notes = "Returns the software identification for the given run ID.", response = String.class)
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
	@ApiOperation(value = "Insert run.", notes = "Inserts a given run into the runs collection.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/runs", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String postRunToRunsCollection(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Run message.", required = true) @RequestBody String messageBody) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		return new InsertRunMethod(SerializationFormat.XML, authorization).insertRun(messageBody);
	}
	
	@POST
	@ApiOperation(value = "Set status.", notes = "Sets the status of a given run ID using a MethodCallStatusEnum and status message.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/status", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String updateStatusOfRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Method call status enum", required = true) @RequestParam("methodCallStatusEnum") MethodCallStatusEnum statusToUpdateTo,
			@ApiParam(value = "Status message", required = true) @RequestParam("statusMessage") String statusMessage,
                             @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new SetStatusOfRunMethod(SerializationFormat.XML, authorization).setStatusOfRun(runId, statusToUpdateTo, statusMessage);
	}
	
	@POST
	@ApiOperation(value = "Set the last service to be called for the run.", notes = "Sets the software identification for the last service to be called for a given run ID.", response = String.class)
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
	@ApiOperation(value = "Get last service to be called.", notes = "Returns the software identification for the last service to be called for a given run ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/lastServiceToBeCalled", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getLastServiceToBeCalledForRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                          @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new GetLastServiceToBeCalledForRunMethod(SerializationFormat.XML, authorization).getLastServiceToBeCalledForRun(runId);
	}
	
	@POST
	@ApiOperation(value = "Set simulation group IDs for run.", notes = "Sets the simulation group IDs for run using a comma separated input.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/rungroup", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String addRunIdsToSimulationGroupForRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "List of run IDs to associate.", required = true) @RequestParam("runIdsToAssociate") String runIdsToAssociate,
                                            @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new AddRunIdsToSimulationGroupForRun(SerializationFormat.XML, authorization).addRunIdsToSimulationGroupForRun(runId, runIdsToAssociate);
		
	}
	
	@DELETE
	@ApiOperation(value = "Delete run.", notes = "Deletes run associated with the given run ID from the system. A user will need to have proper authentication to perform this task.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	public @ResponseBody
	String deleteRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                     @RequestHeader("Authorization") String authorization) throws SerializationException, UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
		
		return new DeleteRunMethod(SerializationFormat.XML, authorization).deleteRun(runId);
	}
	
	@GET
	@ApiOperation(value = "Get status.", notes = "Returns the method call status and message for the given run ID", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/status", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getStatusOfRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                          @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
		
		return new GetStatusOfRunMethod(SerializationFormat.XML, authorization).getStatusForRun(runId);
	}
	
	@POST
	@ApiOperation(value = "Perform action on the run.", notes = "Performs an action on the run (start or terminate).", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String run(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Action", required = true) @RequestParam("action") RunActionEnum action,
               @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedRunActionException, UnsupportedAuthorizationTypeException {
		
		switch (action) {
			case START:
				return new StartRunMethod(SerializationFormat.XML, authorization).startRun(runId);
			case TERMINATE:
				return new TerminateRunMethod(SerializationFormat.XML, authorization).terminateRun(runId);
			default:
				throw new UnsupportedRunActionException("The specified run action value \"" + action + "\" is not recognized");
		}
		
	}

	//We cannot create a new collection at the URL level (PUT), and we cannot DELETE the URLs collection (DELETE).

	/*--Method to get run information such as groups, types, etc--*/
	@GET
	@ApiOperation(value = "Get information for run.", notes = "Returns the service type and all simulation group IDs associated with the run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getRunInformation(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                             @RequestHeader("Authorization") String authorization) throws SerializationException, UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {

		return new GetInformationForRunMethod(SerializationFormat.XML, authorization).getInformationForRun(runId);
	}
	
}

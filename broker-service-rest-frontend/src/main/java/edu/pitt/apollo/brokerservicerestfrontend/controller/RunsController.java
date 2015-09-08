package edu.pitt.apollo.brokerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.brokerservicerestfrontend.methods.*;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.brokerservicerestfrontend.exception.UnsupportedRunActionException;
import edu.pitt.apollo.services_common.v3_0_2.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.math.BigInteger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;

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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new GetRunIdsInSimulationGroupForRunMethod(username, password, SerializationFormat.XML).getRunIdsInSimulationGroupForRun(runId);
	}

	@GET
	@ApiOperation(value = "Get software identification", notes = "Returns the software identification associated with the given run ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/softwareIdentification", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getSoftwareIdentificationForRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new GetSoftwareIdentificationForRunMethod(username, password, SerializationFormat.XML).getSoftwareIdentificationForRun(runId);
	}
	
	@POST
	@ApiOperation(value = "Insert and start run", notes = "Inserts the provided run configuration into the runs collection and then starts the run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/runs", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String postRunToRunsCollection(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Run message", required = true) @RequestBody String messageBody) throws UnsupportedSerializationFormatException, SerializationException {
		return new InsertAndStartRunMethod(username, password, SerializationFormat.XML).insertAndStartRun(messageBody);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new SetStatusOfRunMethod(username, password, SerializationFormat.XML).setStatusOfRun(runId, statusToUpdateTo, statusMessage);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new SetLastServiceToBeCalledForRunMethod(username, password, SerializationFormat.XML).setLastServiceToBeCalledForRunMethod(runId, softwareName,
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new GetLastServiceToBeCalledForRunMethod(username, password, SerializationFormat.XML).getLastServiceToBeCalledForRun(runId);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new AddRunIdsToSimulationGroupForRun(username, password, SerializationFormat.XML).addRunIdsToSimulationGroupForRun(runId, runIdsToAssociate);
		
	}
	
	@DELETE
	@ApiOperation(value = "Delete run", notes = "Deletes all run data associated with the given run ID from the system.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	public @ResponseBody
	String deleteRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws SerializationException, UnsupportedSerializationFormatException {
		
		return new DeleteRunMethod(username, password, SerializationFormat.XML).deleteRun(runId);
	}
	
	@GET
	@ApiOperation(value = "Get status", notes = "Returns the MethodCallStatus associated with the given run", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/status", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getStatusOfRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new GetStatusOfRunMethod(username, password, SerializationFormat.XML).getStatusForRun(runId);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedRunActionException {
		
		switch (action) {
//			case START:
//				return new StartRunMethod(username, password, SerializationFormat.XML).startRun(runId);
			case TERMINATE:
				return new TerminateRunMethod(username, password, SerializationFormat.XML).terminateRun(runId);
			default:
				throw new UnsupportedRunActionException("The specified run action value \"" + action + "\" is not recognized");
		}
		
	}

    @GET
    @ApiOperation(value = "List files.", notes = "Returns the list of files associated with the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/files", method = RequestMethod.GET, headers = "Accept=application/xml")
    public @ResponseBody
    String getListOfFilesForRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                  @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                  @ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

        return new GetListOfFilesForRunMethod(username, password, SerializationFormat.XML).getListOfFilesForRun(runId);
    }

    @POST
    @ApiOperation(value = "Associate file.", notes = "Associates a file with the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/files", method = RequestMethod.POST, headers = "Accept=application/xml")
    public @ResponseBody
    String associateFileWithRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                  @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                  @ApiParam(value = "Password", required = true) @RequestParam("password") String password,
                                  @ApiParam(value = "Request object", required = true) @RequestBody String requestBody) throws UnsupportedSerializationFormatException, SerializationException {

        return new AssociateContentWithRunIdMethod(username, password, SerializationFormat.XML).associateContentWithRunId(runId, password);
    }
	
}

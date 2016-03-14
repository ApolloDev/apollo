package edu.pitt.apollo.runmanagerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservicerestfrontend.exception.UnsupportedRunActionException;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.AddRunIdsToSimulationGroupForRun;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.DeleteRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.GetInformationForRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.GetLastServiceToBeCalledForRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.GetRunIdsInSimulationGroupForRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.GetSoftwareIdentificationForRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.GetStatusOfRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.InsertRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.RunDataServiceJobMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.StartRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.SetLastServiceToBeCalledForRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.SetStatusOfRunMethod;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.TerminateRunMethod;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunActionEnum;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;
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
	@ApiOperation(value = "Get simulation group IDs for run.", notes = "Returns the simulation group IDs for run using a comma separated input.", response = String.class)
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
	@ApiOperation(value = "Get software identification.", notes = "Returns the software identification for the given run ID.", response = String.class)
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
	@ApiOperation(value = "Insert run.", notes = "Inserts a given run into the runs collection.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/runs", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String postRunToRunsCollection(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Run message.", required = true) @RequestBody String messageBody) throws UnsupportedSerializationFormatException, SerializationException {
		return new InsertRunMethod(username, password, SerializationFormat.XML).insertRun(messageBody);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new SetStatusOfRunMethod(username, password, SerializationFormat.XML).setStatusOfRun(runId, statusToUpdateTo, statusMessage);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new SetLastServiceToBeCalledForRunMethod(username, password, SerializationFormat.XML).setLastServiceToBeCalledForRunMethod(runId, softwareName,
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new GetLastServiceToBeCalledForRunMethod(username, password, SerializationFormat.XML).getLastServiceToBeCalledForRun(runId);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new AddRunIdsToSimulationGroupForRun(username, password, SerializationFormat.XML).addRunIdsToSimulationGroupForRun(runId, runIdsToAssociate);
		
	}
	
	@DELETE
	@ApiOperation(value = "Delete run.", notes = "Deletes run associated with the given run ID from the system. A user will need to have proper authentication to perform this task.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	public @ResponseBody
	String deleteRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws SerializationException, UnsupportedSerializationFormatException {
		
		return new DeleteRunMethod(username, password, SerializationFormat.XML).deleteRun(runId);
	}
	
	@GET
	@ApiOperation(value = "Get status.", notes = "Returns the method call status and message for the given run ID", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/status", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getStatusOfRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {
		
		return new GetStatusOfRunMethod(username, password, SerializationFormat.XML).getStatusForRun(runId);
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
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedRunActionException {
		
		switch (action) {
			case START:
				return new StartRunMethod(username, password, SerializationFormat.XML).startRun(runId);
			case TERMINATE:
				return new TerminateRunMethod(username, password, SerializationFormat.XML).terminateRun(runId);
			default:
				throw new UnsupportedRunActionException("The specified run action value \"" + action + "\" is not recognized");
		}
		
	}

	//We cannot create a new collection at the URL level (PUT), and we cannot DELETE the URLs collection (DELETE).

	@POST
	@ApiOperation(value = "Get all output files url as zip.", notes = "Starts process to get all output files as zipped given a run ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/allOutputFilesURLAsZip/", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String getAllOutputFilesURLAsZip(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return new RunDataServiceJobMethod(username, password, SerializationFormat.XML).runDataServiceJob(runId);
	}

	/*--Method to get run information such as groups, types, etc--*/
	@GET
	@ApiOperation(value = "Get information for run.", notes = "Returns the service type and all simulation group IDs associated with the run.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getRunInformation(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws SerializationException, UnsupportedSerializationFormatException {

		return new GetInformationForRunMethod(username, password, SerializationFormat.XML).getInformationForRun(runId);
	}
	
}

package edu.pitt.apollo.restservice.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;

import edu.pitt.apollo.restservice.methods.AddRunIdsToSimulationGroupForRun;
import edu.pitt.apollo.restservice.methods.AssociateContentWithRunIdMethod;
import edu.pitt.apollo.restservice.methods.DeleteRunMethod;
import edu.pitt.apollo.restservice.methods.GetInformationForRunMethod;
import edu.pitt.apollo.restservice.methods.GetLastServiceToBeCalledForRunMethod;
import edu.pitt.apollo.restservice.methods.GetListOfFilesForRunMethod;
import edu.pitt.apollo.restservice.methods.GetListOfURLsForRunMethod;
import edu.pitt.apollo.restservice.methods.GetRunIdsInSimulationGroupForRunMethod;
import edu.pitt.apollo.restservice.methods.GetSoftwareIdentificationForRunMethod;
import edu.pitt.apollo.restservice.methods.GetStatusOfRunMethod;
import edu.pitt.apollo.restservice.methods.InsertRunMethod;
import edu.pitt.apollo.restservice.methods.RunDataServiceJobMethod;
import edu.pitt.apollo.restservice.methods.SetLastServiceToBeCalledForRunMethod;
import edu.pitt.apollo.restservice.methods.SetStatusOfRunMethod;
import edu.pitt.apollo.services_common.v3_0_0.*;
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

	/*--Methods for the RUNS collection--*/
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
	//Cannot delete the RUNS collection (DELETE), cannot create a collection at this level (PUT), and cannot get a list of all runs (GET, for now).

	/*--Methods to modify a run?--*/
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
	//We cannot run data through this method (POST), cannot reace new run data (PUT), and as of now we do not havea reason to get data from this level (GET).


	/*--Methods for Software Identification of a run--*/
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

//	@ApiIgnore
//	@RequestMapping(value = "/run/{runId}/softwareIdentification", method = RequestMethod.POST, headers = "Accept=application/xml")
//	public @ResponseBody
//	String postSoftwareIdentification(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
//			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
//			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) {
//
//		GetSoftwareIdentificationForRunRestMessage returnMessage = new GetSoftwareIdentificationForRunRestMessage();
//		GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
//		Meta meta = new Meta();
//		meta.setNumberOfReturnedResults(0);
//		meta.setStatus(MethodNotAllowedMessage.getStatus());
//		meta.setStatusMessage(MethodNotAllowedMessage.getMessage());
//		returnMessage.setMeta(meta);
//		return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
//	}
//	@ApiIgnore
//	@RequestMapping(value = "/run/{runId}/softwareIdentification", method = RequestMethod.PUT, headers = "Accept=application/xml")
//	public @ResponseBody
//	String putSoftwareIdentification(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
//			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
//			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) {
//
//		GetSoftwareIdentificationForRunRestMessage returnMessage = new GetSoftwareIdentificationForRunRestMessage();
//		GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
//		Meta meta = new Meta();
//		meta.setNumberOfReturnedResults(0);
//		meta.setStatus(MethodNotAllowedMessage.getStatus());
//		meta.setStatusMessage(MethodNotAllowedMessage.getMessage());
//		returnMessage.setMeta(meta);
//		return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
//	}
	//Cannot EDIT a software identificaiton for a run (POST), can't remove it (DELETE), and can't assign a new one (PUT).

	/*--Methods for Status of a run--*/
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

		return new SetStatusOfRunMethod(username, password, SerializationFormat.XML).setStatusOfRun(runId, statusToUpdateTo, username);
	}
	//Cannot create a new status for a run as it will always exist (PUT), and cannot delete a run status as a user (DELETE).


	/*--Methods for files collection of a run--*/
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

	//We cannot create a new collection at the files level (PUT), and we cannot DELETE the files collection (DELETE).

	/*--Methods for URLs collection of a run--*/
	@GET
	@ApiOperation(value = "List URLs", notes = "Returns the list of URLs associated with the given run ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/urls", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getListOfURLsForRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return new GetListOfURLsForRunMethod(username, password, SerializationFormat.XML).getListOfURLsForRunMethod(runId);
	}

	@POST
	@ApiOperation(value = "Associate URL.", notes = "Associates URL with the given run ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/run/{runId}/urls", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String associateURLWithRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "File text content, source/destination name and version, file label, and file type.", required = true) @RequestBody String associationData) throws UnsupportedSerializationFormatException, SerializationException {

		return new AssociateContentWithRunIdMethod(username, password, SerializationFormat.XML).associateContentWithRunId(runId, associationData);
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

	/*--Methods to get and post rungroup data--*/
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

	/*--Methods to get and post last service to run for run id--*/
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
}

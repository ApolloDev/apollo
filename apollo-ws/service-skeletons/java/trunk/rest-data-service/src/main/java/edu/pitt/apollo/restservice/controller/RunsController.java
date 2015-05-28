package edu.pitt.apollo.restservice.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.RestDataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.dataservice.methods.run.GetSimulationGroupIdsForRunIdMethod;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.restservice.exceptions.ParsingFromXmlToObjectException;
import edu.pitt.apollo.restservice.rest.responsemessage.*;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.MethodNotAllowedMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;
import edu.pitt.apollo.restservice.rest.utils.*;
import edu.pitt.apollo.restservice.types.AssociateContentWithRunIdRestMessage;
import edu.pitt.apollo.restservice.utils.CodeResolver;
import edu.pitt.apollo.restservice.utils.ConvertResponseMessagesToXml;
import edu.pitt.apollo.restservice.utils.ParseXmlToAndFromObject;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcs27 on 5/15/15.
 * Purpose: This class contains the RESTful interfaces associated with the runs collection.
 */

@Controller
@RequestMapping("/ws")
public class RunsController {

    /*--Methods for the RUNS collection--*/
    @POST
    @ApiOperation(value ="Insert run.", notes = "Inserts a given run into the runs collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/runs", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String postRunToRunsCollection() {
        return null;
    }
    //Cannot delete the RUNS collection (DELETE), cannot create a collection at this level (PUT), and cannot get a list of all runs (GET, for now).

    /*--Methods to modify a run?--*/
    @DELETE
    @ApiOperation(value = "Delete run.", notes = "Deletes run associated with the given run ID from the system. A user will need to have proper authentication to perform this task.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String deleteRunFromDatabase(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId) {
        DataServiceImpl impl = new DataServiceImpl();
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        RemoveRunDataMessage message = new RemoveRunDataMessage();
        message.setRunId(runId);
        RemoveRunDataResult result = impl.removeRunData(message);

        if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
        {
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
        }
        else
        {
            returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
        }

        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }
    //We cannot run data through this method (POST), cannot reace new run data (PUT), and as of now we do not havea reason to get data from this level (GET).


    /*--Methods for Software Identification of a run--*/
    @GET
    @ApiOperation(value = "Get software identification.", notes = "Returns the software identification for the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/softwareIdentification", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getSoftwareIdentificationForRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId) {
        DataServiceImpl impl = new DataServiceImpl();
        GetSoftwareIdentificationForRunRestMessage returnMessage = new GetSoftwareIdentificationForRunRestMessage();
        GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
        message.setRunId(runId);

        GetSoftwareIdentificationForRunResult result = impl.getSoftwareIdentificationForRun(message);
        if (result.getMethodCallStatus().getStatus() == MethodCallStatusEnum.FAILED) {
            returnMessage = BuildSoftwareIdentificationForRunMessage.buildFailedGetIdentificationKeyRestMessage(result.getMethodCallStatus().getMessage());
        } else {
            returnMessage = BuildSoftwareIdentificationForRunMessage.buildSuccessfulGetIdentificationKeyRestMessage(result.getSoftwareIdentification());
        }
        return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
    }

    @ApiIgnore
    @RequestMapping(value = "/run/{runId}/softwareIdentification", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String postSoftwareIdentification(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId) {

        GetSoftwareIdentificationForRunRestMessage returnMessage = new GetSoftwareIdentificationForRunRestMessage();
        GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(0);
        meta.setStatus(MethodNotAllowedMessage.getStatus());
        meta.setStatusMessage(MethodNotAllowedMessage.getMessage());
        returnMessage.setMeta(meta);
        return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
    }
    @ApiIgnore
    @RequestMapping(value = "/run/{runId}/softwareIdentification", method = RequestMethod.PUT, headers = "Accept=application/xml")
    public
    @ResponseBody
    String putSoftwareIdentification(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId) {

        GetSoftwareIdentificationForRunRestMessage returnMessage = new GetSoftwareIdentificationForRunRestMessage();
        GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(0);
        meta.setStatus(MethodNotAllowedMessage.getStatus());
        meta.setStatusMessage(MethodNotAllowedMessage.getMessage());
        returnMessage.setMeta(meta);
        return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
    }
    //Cannot EDIT a software identificaiton for a run (POST), can't remove it (DELETE), and can't assign a new one (PUT).

    /*--Methods for Status of a run--*/
    @GET
    @ApiOperation(value = "Get status.", notes = "Returns the method call status and message for the given run ID", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/status", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getStatusOfRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId) {

        GetRunStatusRestMessage returnMessage = new GetRunStatusRestMessage();

        GetStatusOfRunMessage message = new GetStatusOfRunMessage();
        message.setRunId(runId);

        DataServiceImpl impl = new DataServiceImpl();

        GetStatusOfRunResult result = impl.getStatusOfRun(message);

        if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
        {
            returnMessage = BuildGetRunStatusRestMessage.buildFailedGetIdentificationKeyRestMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            RunStatus statusOfRun = new RunStatus();
            statusOfRun.setMessage(result.getStatusMessage());
            statusOfRun.setStatus(result.getStatusEnum());
            statusOfRun.setRunId(runId);

            returnMessage = BuildGetRunStatusRestMessage.buildSuccessfulGetIdentificationKeyRestMessage(statusOfRun);

        }

       return ConvertResponseMessagesToXml.convertGetRunsStatusRestMessageToXmlJaxb(returnMessage);
    }

    @POST
    @ApiOperation(value = "Set status.", notes = "Sets the status of a given run ID using a MethodCallStatusEnum and status message.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/status", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String updateStatusOfRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                              @ApiParam(value="Method call status enum", required=true) @RequestParam("methodCallStatusEnum") MethodCallStatusEnum statusToUpdateTo,
                              @ApiParam(value="Status message", required=true) @RequestParam("statusMessage") String statusMessage) {
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        if(statusMessage.equalsIgnoreCase("") || statusMessage.trim().equalsIgnoreCase(""))
        {
            Meta meta = new Meta();
            meta.setStatus(400);
            meta.setStatusMessage("A valid status message is required.");
            meta.setNumberOfReturnedResults(0);
            returnMessage.setMeta(meta);

            return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
        }
        UpdateStatusOfRunMessage updateStatusOfRunMessage = new UpdateStatusOfRunMessage();
        updateStatusOfRunMessage.setRunId(runId);
        updateStatusOfRunMessage.setStatusMessage(statusMessage);
        updateStatusOfRunMessage.setStatusEnum(statusToUpdateTo);
        DataServiceImpl impl = new DataServiceImpl();
        UpdateStatusOfRunResult result = impl.updateStatusOfRun(updateStatusOfRunMessage);

        if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED)
        {
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
        }
        else
        {
            returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
        }

        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }
    //Cannot create a new status for a run as it will always exist (PUT), and cannot delete a run status as a user (DELETE).


    /*--Methods for files collection of a run--*/
    @GET
    @ApiOperation(value = "List files.", notes = "Returns the list of files associated with the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/files", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getListOfFilesForRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                  @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                  @ApiParam(value = "Password", required = true) @RequestParam("password") String password) {
        GetListOfContentAssociatedToRunRestMessage returnMessage = new GetListOfContentAssociatedToRunRestMessage();
        DataServiceImpl impl = new DataServiceImpl();
        ListFilesMessage message = new ListFilesMessage();
        Authentication authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);
        message.setRunId(runId);
        message.setAuthentication(authentication);

        ListFilesResult result = impl.listFilesAssociatedToRun(message);

        if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED){
            returnMessage = BuildGetListOfContentAssociatedToRunRestMessage.buildFailedGetListOfFilesAssociatedToRunRestMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            List<ContentIdAndDescription> list = result.getContentIdAndDescriptions();
            returnMessage = BuildGetListOfContentAssociatedToRunRestMessage.buildSuccessfulGetListOfFilesAssociatedToRunRestMessage(list);
        }
        return ConvertResponseMessagesToXml.convertGetListOfContentAssociatedToRunRestMessage(returnMessage);
    }

    @POST
    @ApiOperation(value = "Associate file.", notes = "Associates a file with the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/files", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String associateFileWithRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                  @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                  @ApiParam(value = "Password", required = true) @RequestParam("password") String password,
                                  @ApiParam(value = "File text content, source/destination name and version, file label, and file type.", required = true) @RequestBody String associationData) {
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        DataServiceImpl impl = new DataServiceImpl();
        try {
            AssociateContentWithRunIdRestMessage messageBodyContent = ParseXmlToAndFromObject.convertFromXmlToAssociateContentWithRunIdRestMessage(associationData);
            AssociateFileWithRunIdMessage message = new AssociateFileWithRunIdMessage();
            message.setContentLabel(messageBodyContent.getContentLabel());
            message.setContentType(DbContentDataType.valueOf(messageBodyContent.getContentType()));
            message.setDestinationSoftwareName(messageBodyContent.getDestinationSoftwareName());
            message.setDestinationSoftwareVersion(messageBodyContent.getDestinationSoftwareVersion());
            message.setSourceSoftwareName(messageBodyContent.getSourceSoftwareName());
            message.setSourceSoftwareVersion(messageBodyContent.getSourceSoftwareVersion());
            message.setFileTextContent(messageBodyContent.getFileContentOrUrl());
            message.setRunId(runId);
            Authentication authentication = new Authentication();
            authentication.setRequesterId(username);
            authentication.setRequesterPassword(password);
            message.setAuthentication(authentication);

            AssociateFileWithRunIdResult result = impl.associateFileWithRunId(message);

            if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED){
                returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
            }
            else{
                returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
            }
        } catch (ParsingFromXmlToObjectException e) {
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(e.getErrorMessage());
        }
        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }
    //We cannot create a new collection at the files level (PUT), and we cannot DELETE the files collection (DELETE).

    /*--Methods for URLs collection of a run--*/
    @GET
    @ApiOperation(value = "List URLs", notes = "Returns the list of URLs associated with the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/urls", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getListOfURLsForRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                 @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                 @ApiParam(value = "Password", required = true) @RequestParam("password") String password) {
        GetListOfContentAssociatedToRunRestMessage returnMessage = new GetListOfContentAssociatedToRunRestMessage();
        DataServiceImpl impl = new DataServiceImpl();
        Authentication authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);
        ListURLsMessage message = new ListURLsMessage();
        message.setRunId(runId);
        message.setAuthentication(authentication);
        ListURLsResult result = impl.listURLsAssociatedToRun(message);
        if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED){
            returnMessage = BuildGetListOfContentAssociatedToRunRestMessage.buildFailedGetListOfFilesAssociatedToRunRestMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            returnMessage = BuildGetListOfContentAssociatedToRunRestMessage.buildSuccessfulGetListOfFilesAssociatedToRunRestMessage(result.getContentIdAndDescriptions());
        }
        return ConvertResponseMessagesToXml.convertGetListOfContentAssociatedToRunRestMessage(returnMessage);

    }

    @POST
    @ApiOperation(value = "Associate URL.", notes = "Associates URL with the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/run/{runId}/urls", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String associateURLWithRunId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                 @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                 @ApiParam(value = "Password", required = true) @RequestParam("password") String password,
                                 @ApiParam(value = "File text content, source/destination name and version, file label, and file type.", required = true) @RequestBody String associationData) {

        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        DataServiceImpl impl = new DataServiceImpl();
        try {
            AssociateContentWithRunIdRestMessage messageBodyContent = ParseXmlToAndFromObject.convertFromXmlToAssociateContentWithRunIdRestMessage(associationData);
            AssociateURLWithRunIdMessage message = new AssociateURLWithRunIdMessage();
            message.setContentLabel(messageBodyContent.getContentLabel());
            message.setContentType(DbContentDataType.valueOf(messageBodyContent.getContentType()));
            message.setDestinationSoftwareName(messageBodyContent.getDestinationSoftwareName());
            message.setDestinationSoftwareVersion(messageBodyContent.getDestinationSoftwareVersion());
            message.setSourceSoftwareName(messageBodyContent.getSourceSoftwareName());
            message.setSourceSoftwareVersion(messageBodyContent.getSourceSoftwareVersion());
            message.setFileTextContent(messageBodyContent.getFileContentOrUrl());
            message.setRunId(runId);
            Authentication authentication = new Authentication();
            authentication.setRequesterId(username);
            authentication.setRequesterPassword(password);
            message.setAuthentication(authentication);

            AssociateURLWithRunIdResult result = impl.associateURLWithRunId(message);

            if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED){
                returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
            }
            else{
                returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
            }
        } catch (ParsingFromXmlToObjectException e) {
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(e.getErrorMessage());
        }
        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }
    //We cannot create a new collection at the URL level (PUT), and we cannot DELETE the URLs collection (DELETE).

    @POST
    @ApiOperation(value = "Get all output files url as zip.", notes = "Starts process to get all output files as zipped given a run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value="/run/{runId}/allOutputFilesURLAsZip/", method= RequestMethod.POST, headers="Accept=application/xml")
    public @ResponseBody String getAllOutputFilesURLAsZip(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId){
        BigInteger runIdAsBigInteger;
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        Meta meta = new Meta();

        meta.setNumberOfReturnedResults(0);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());

        DataServiceImpl impl = new DataServiceImpl();
        impl.getAllOutputFilesURLAsZip(runId);

        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);

    }

    /*--Method to get run information such as groups, types, etc--*/
    @GET
      @ApiOperation(value = "Get information for run.", notes = "Returns the service type and all simulation group IDs associated with the run.", response = String.class)
      @ApiResponses(value = {
              @ApiResponse(code = 200, message = "")
      })
      @RequestMapping(value="/run/{runId}", method= RequestMethod.GET, headers="Accept=application/xml")
      public @ResponseBody String getRunInformation(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId){
        RestDataServiceImpl impl = new RestDataServiceImpl();
        GetRunInformationRestMessage returnMessage = new GetRunInformationRestMessage();
        GetRunInformationMessage message = new GetRunInformationMessage();
        message.setRunId(runId);

        GetRunInformationResult result = impl.getRunInformation(message);

        if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED){
            returnMessage = BuildGetRunInformationRestMessage.buildFailedGetRunInformationRestMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            returnMessage = BuildGetRunInformationRestMessage.buildSuccessfulGetRunInformationRestMessage(result);
        }
        return ConvertResponseMessagesToXml.convertGetRunInformationRestMessageToXmlJaxb(returnMessage);

    }

    /*--Methods to get and post rungroup data--*/
    @GET
    @ApiOperation(value = "Get simulation group IDs for run.", notes = "Returns the simulation group IDs for run using a comma separated input.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value="/run/{runId}/rungroup", method= RequestMethod.GET, headers="Accept=application/xml")
    public @ResponseBody String getRunIdsInSimulationGroup(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                                           @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                                           @ApiParam(value = "Password", required = true) @RequestParam("password") String password){
        RestDataServiceImpl impl = new RestDataServiceImpl();
        SimulationGroupRestMessage returnMessage = new SimulationGroupRestMessage();

        GetRunIdsAssociatedWithSimulationGroupMessage message = new GetRunIdsAssociatedWithSimulationGroupMessage();
        Authentication authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);
        GetRunIdsAssociatedWithSimulationGroupResult result = impl.getRunIdsAssociatedToSimulationGroupsForRunId(runId,authentication);

        if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED){
            returnMessage = BuildSimulationGroupRestMessage.buildFailedSimulationGroupRestMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            returnMessage = BuildSimulationGroupRestMessage.buildSuccessfulSimulationGroupRestMessage(result);
        }
        return ConvertResponseMessagesToXml.convertGetRunIdsAssociatedWithSimulationGroupRestMessage(returnMessage);

    }

    @POST
    @ApiOperation(value = "Set simulation group IDs for run.", notes = "Sets the simulation group IDs for run using a comma separated input.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value="/run/{runId}/rungroup", method= RequestMethod.POST, headers="Accept=application/xml")
    public @ResponseBody String setRunIdsInGroup(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,
                                                    @ApiParam(value = "List of run IDs to associate.", required = true) @RequestParam("runIdsToAssociate") String runIdsToAssociate,
                                                 @ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                                @ApiParam(value = "Password", required = true) @RequestParam("password") String password){
        RestDataServiceImpl impl = new RestDataServiceImpl();
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();

        Authentication authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);
        List<BigInteger> groupIdsAsList = CodeResolver.getListOfGroupIds(runIdsToAssociate);

        MethodCallStatus result = impl.addRundIdsToSimulationGroupForRunId(groupIdsAsList, runId,authentication);

        if(result.getStatus()==MethodCallStatusEnum.FAILED){
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMessage());
        }
        else{
            returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
        }
        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);

    }


}

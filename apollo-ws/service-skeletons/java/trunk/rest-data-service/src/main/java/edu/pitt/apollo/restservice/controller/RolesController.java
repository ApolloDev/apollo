package edu.pitt.apollo.restservice.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.AddRoleMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.AddRoleResult;
import edu.pitt.apollo.restservice.exceptions.ParsingFromXmlToObjectException;
import edu.pitt.apollo.restservice.rest.responsemessage.StatusOnlyResponseMessage;
import edu.pitt.apollo.restservice.rest.utils.BuildStatusResponseMessage;
import edu.pitt.apollo.restservice.types.AddRoleInformation;
import edu.pitt.apollo.restservice.utils.ConvertResponseMessagesToXml;
import edu.pitt.apollo.restservice.utils.ParseXmlToAndFromObject;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;

/*
 * Created by dcs27 on 5/15/15.
 * Purpose: This class contains the RESTful interfaces associated with the roles collection.
 */

@Controller
@RequestMapping("/ws")
public class RolesController {
    /*--Methods for the Users collection--*/
   /* @GET
    @ApiOperation(value ="List roles.", notes = "Returns the list of all possible roles.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/roles", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getRoles() {
        return null;
    }*/
    @POST
    @ApiOperation(value ="Add role.", notes = "Adds the role to the roles collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/roles", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String addRolesToRolesCollection(@ApiParam(value = "Role information XML.", required = true) @RequestBody String roleInformationXml) {
        DataServiceImpl impl = new DataServiceImpl();
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        try{
            AddRoleInformation roleInformation = ParseXmlToAndFromObject.convertFromXmlToRoleInformation(roleInformationXml);
            AddRoleMessage message = new AddRoleMessage();
            message.setSoftwareIdentification(roleInformation.getSoftwareIdentification());
            message.setCanRun(roleInformation.isCanRun());
            message.setCanViewCache(roleInformation.isCanViewCache());
            AddRoleResult result = impl.addRole(message);

            if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED){
                returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
            }
            else{
                returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
            }
        } catch (ParsingFromXmlToObjectException e) {
            e.printStackTrace();
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(e.getErrorMessage());
        }

        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }
    //We cannot delete the roles collection (DELETE) and we cannot PUT (add a collection) to the users collection.


    /*--Methods for the Users collection--*/
  /*  @GET
    @ApiOperation(value ="Get role.", notes = "Returns the role associated with the given role id.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getRoleUsingRoleId(@ApiParam(value = "Role ID", required = true) @PathVariable("roleId") BigInteger roleId) {
        return null;
    }*/
  /*  @POST
    @ApiOperation(value ="Edit role.", notes = "Edit role's data with the given role ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String editRoleUsingRoleId(@ApiParam(value = "Role ID", required = true) @PathVariable("roleId") BigInteger roleId) {
        return null;
    }*/
    /*@DELETE
    @ApiOperation(value ="Delete role.", notes = "Deletes the given role ID from the roles collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String deleteTheRole(@ApiParam(value = "Role ID", required = true) @PathVariable("roleId") BigInteger roleId) {
        return null;
    }*/
    //We cannot PUT data at this level (PUT).

}

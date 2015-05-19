package edu.pitt.apollo.restservice.controller;

import com.wordnik.swagger.annotations.*;
import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.restservice.exceptions.ParsingFromXmlToObjectException;
import edu.pitt.apollo.restservice.rest.responsemessage.StatusOnlyResponseMessage;
import edu.pitt.apollo.restservice.rest.utils.BuildStatusResponseMessage;
import edu.pitt.apollo.restservice.types.UserAndRoleInformation;
import edu.pitt.apollo.restservice.types.UserInformation;
import edu.pitt.apollo.restservice.utils.ConvertResponseMessagesToXml;
import edu.pitt.apollo.restservice.utils.ParseXmlToAndFromObject;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;

/**
 * Created by dcs27 on 5/15/15.
 * Purpose: This class contains the RESTful interfaces associated with the users collection.
 */

@Controller
@RequestMapping("/ws")
public class UsersController {

    /*--Methods for the Users collection--*/
    @GET
    @ApiOperation(value ="List users.", notes = "Returns the list of all users in the users collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getListOfUsers() {
        return null;
    }
    @POST
//    @ApiImplicitParam(dataType = "string", name = "userInformationXml", paramType = "body", required = true)
    @ApiOperation(value ="Add user.", notes = "Adds the user to the users collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/users", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String addUserToUsersList(@ApiParam(value = "User information XML.", required = true) @RequestBody String userInformationXml) {

        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        DataServiceImpl impl = new DataServiceImpl();
        /*--Parse XML to UserInformationObject--*/
        try {
            UserInformation userInformation = ParseXmlToAndFromObject.convertFromXmlToUserInformation(userInformationXml);
            AddUserMessage message = new AddUserMessage();
            message.setUserId(userInformation.getUserId());
            message.setUserPassword(userInformation.getUserPassword());
            message.setUserEmail(userInformation.getUserEmail());
            AddUserResult result = impl.addUser(message);

            if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED)
            {
                returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
            }
            else
            {
                returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
            }
        } catch (ParsingFromXmlToObjectException e) {
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(e.getMessage());
        }
        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }
    //We cannot delete the users collection (DELETE) and we cannot PUT (add a collection) to the users collection.

    /*--Methods for the user of the users collection--*/
    @GET
    @ApiOperation(value ="Get user.", notes = "Returns data associated to the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getUserFromUsersCollection(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId) {
        return null;
    }
    @DELETE
    @ApiOperation(value ="Delete user.", notes = "Removes a user from the users collection with the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/users", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String deleteUserFromUseresCollection(@ApiParam(value = "User ID", required = true) @PathVariable("userId") String userId, @ApiParam(value = "User password", required = true) @RequestParam("userPassword") String userPassword) {
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        DataServiceImpl impl = new DataServiceImpl();
        DeleteUserMessage message = new DeleteUserMessage();
        message.setUserId(userId);
        message.setUserPassword(userPassword);
        DeleteUserResult result = impl.deleteUser(message);
        if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED) {
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
        }
        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }


    @POST
    @ApiOperation(value ="Edit user.", notes = "Edit data for a user with the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String editUserInUsersCollection(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId) {
        return null;
    }
    //Cannot PUT at this level.

    /*--Methods for the roles of a user of the users collection--*/
    @GET
    @ApiOperation(value ="Get roles for user.", notes = "Returns a list of roles for the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}/roles", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getRolesOfUser(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId) {
        return null;
    }
    @POST
    @ApiOperation(value ="Add role to user.", notes = "Add a role for the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}/roles", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String addRoleToUser(@ApiParam(value = "User ID", required = true) @PathVariable("userId") String userId,@ApiParam(value = "User and role information XML.", required = true) @RequestBody String userAndRoleInformationXml) {
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        DataServiceImpl impl = new DataServiceImpl();
        /*--Parse XML to UserInformationObject--*/
        try {
//            UserAndRoleInformation userAndRoleInformation = new UserAndRoleInformation();
//            SoftwareIdentification si = new SoftwareIdentification();
//            si.setSoftwareDeveloper("Pitt");
//            si.setSoftwareName("SEIR");
//            si.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);
//            si.setSoftwareVersion("2.1");
//            userAndRoleInformation.setCanRequestPrivileged(true);
//            userAndRoleInformation.setSoftwareIdentification(si);
//            userAndRoleInformation.setCanRunSoftware(true);
//            userAndRoleInformation.setUserPassword("asdf");
//
//            String xml = ParseXmlToAndFromObject.convertFromUserAndRoleInformationToXml(userAndRoleInformation);
//            System.out.println(xml);
            UserAndRoleInformation userAndRoleInformation = ParseXmlToAndFromObject.convertFromXmlToUserAndRoleInformationXml(userAndRoleInformationXml);
            AddUserRoleMessage message = new AddUserRoleMessage();
            message.setUserId(userId);
            message.setUserPassword(userAndRoleInformation.getUserPassword());
            message.setSoftwareIdentification(userAndRoleInformation.getSoftwareIdentification());
            message.setCanRunSoftware(userAndRoleInformation.isCanRunSoftware());
            message.setCanRequestPrivileged(userAndRoleInformation.isCanRequestPrivileged());
            AddUserRoleResult result = impl.addUserRole(message);
            if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED)
            {
                returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
            }
            else
            {
                returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
            }
        } catch (ParsingFromXmlToObjectException e) {
            returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(e.getMessage());
        }
        return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
    }
    //Cannot DELETE all roles of a user (DELETE), and CANNOT PUT a new collection or resource at this level (PUT).

    /*--Methods for individual role of a user--*/
    @DELETE
    @ApiOperation(value ="Remove role from user.", notes = "Removes a role from the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}/role/{roleId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getRolesOfUser(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId,@ApiParam(value = "Role ID", required = true) @PathVariable("roleId") BigInteger roleId) {
        return null;
    }


}

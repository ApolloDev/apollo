package edu.pitt.apollo.restservice.controller;

import com.wordnik.swagger.annotations.*;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.restservice.methods.AddRoleToUserMethod;
import edu.pitt.apollo.restservice.methods.AddUserMethod;
import edu.pitt.apollo.restservice.methods.DeleteUserMethod;
import edu.pitt.apollo.services_common.v3_0_0.SerializationFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;

/**
 * Created by dcs27 on 5/15/15.
 * Purpose: This class contains the RESTful interfaces associated with the users collection.
 */

@Controller
@RequestMapping("/ws")
public class UsersController {

    /*--Methods for the Users collection--*/
 /*   @GET
    @ApiOperation(value ="List users.", notes = "Returns the list of all users in the users collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/users", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getListOfUsers() {
        return null;
    }*/
    @POST
//    @ApiImplicitParam(dataType = "string", name = "userInformationXml", paramType = "body", required = true)
    @ApiOperation(value ="Add user.", notes = "Adds the user to the users collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/users", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String addUserToUsersList(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "User name to add", required = true) @RequestParam("userNameToAdd") String uernameToAdd,
			@ApiParam(value = "Password to add", required = true) @RequestParam("passwordToAdd") String passwordToAdd,
			@ApiParam(value = "User email", required = true) @RequestParam("userEmail") String userEmail) throws UnsupportedSerializationFormatException, SerializationException {

        return AddUserMethod.addUser(username, password, uernameToAdd, passwordToAdd, userEmail, SerializationFormat.XML);
    }
    //We cannot delete the users collection (DELETE) and we cannot PUT (add a collection) to the users collection.

    /*--Methods for the user of the users collection--*/
   /* @GET
    @ApiOperation(value ="Get user.", notes = "Returns data associated to the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getUserFromUsersCollection(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId) {
        return null;
    }*/
    @DELETE
    @ApiOperation(value ="Delete user.", notes = "Removes a user from the users collection with the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/users", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String deleteUserFromUseresCollection(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "User name to add", required = true) @RequestParam("userNameToAdd") String uernameToDelete) throws UnsupportedSerializationFormatException, SerializationException {
        
		return DeleteUserMethod.deleteUser(username, password, uernameToDelete, SerializationFormat.XML);
    }


  /*  @POST
    @ApiOperation(value ="Edit user.", notes = "Edit data for a user with the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String editUserInUsersCollection(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId) {
        return null;
    }*/
    //Cannot PUT at this level.

    /*--Methods for the roles of a user of the users collection--*/
   /* @GET
    @ApiOperation(value ="Get roles for user.", notes = "Returns a list of roles for the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}/roles", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getRolesOfUser(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId) {
        return null;
    }*/
    @POST
    @ApiOperation(value ="Add role to user.", notes = "Add a role for the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}/roles", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String addRoleToUser(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Request object", required = true) @RequestBody String requestBody) throws UnsupportedSerializationFormatException, SerializationException {
        
		return AddRoleToUserMethod.addRole(username, password, requestBody, SerializationFormat.XML);
    }
    //Cannot DELETE all roles of a user (DELETE), and CANNOT PUT a new collection or resource at this level (PUT).

    /*--Methods for individual role of a user--*/
 /*   @DELETE
    @ApiOperation(value ="Remove role from user.", notes = "Removes a role from the given user ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/user/{userId}/role/{roleId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getRolesOfUser(@ApiParam(value = "User ID", required = true) @PathVariable("userId") BigInteger userId,@ApiParam(value = "Role ID", required = true) @PathVariable("roleId") BigInteger roleId) {
        return null;
    }*/


}

package edu.pitt.apollo.dataservicerestfrontend.controller;

import com.wordnik.swagger.annotations.*;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.dataservicerestfrontend.methods.AddRoleToUserMethod;
import edu.pitt.apollo.dataservicerestfrontend.methods.AddUserMethod;
import edu.pitt.apollo.dataservicerestfrontend.methods.AuthenticateUserMethod;
import edu.pitt.apollo.dataservicerestfrontend.methods.AuthorizeUserMethod;
import edu.pitt.apollo.dataservicerestfrontend.methods.DeleteUserMethod;
import edu.pitt.apollo.dataservicerestfrontend.methods.GetSoftwareIdentificationForRunMethod;
import edu.pitt.apollo.services_common.v3_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_2.SerializationFormat;
import java.math.BigInteger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

/**
 * Created by dcs27 on 5/15/15. Purpose: This class contains the RESTful interfaces associated with the users collection.
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
	@GET
	@ApiOperation(value = "Authenticate user.", notes = "Authenticates the user.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/user/{userName}/authenticate", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String authenticateUser(@ApiParam(value = "User name.", required = true) @PathVariable("userName") String userName,
			@ApiParam(value = "Password for user to be authenticated", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return new AuthenticateUserMethod("", "", SerializationFormat.XML).authenticateUser(userName, password);
	}

	@GET
	@ApiOperation(value = "Authorize user.", notes = "Authorizes the user to run a given software.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/user/{userName}/authorize", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String authorizeUser(@ApiParam(value = "User name.", required = true) @PathVariable("userName") String userName,
			@ApiParam(value = "Password for user to be authorized", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Software name", required = true) @RequestParam("softwareName") String softwareName,
			@ApiParam(value = "Software version", required = true) @RequestParam("softwareVersion") String softwareVersion,
			@ApiParam(value = "Software developer", required = true) @RequestParam("softwareDeveloper") String softwareDeveloper,
			@ApiParam(value = "Apollo software type enum", required = true) @RequestParam("softwareTypeEnum") ApolloSoftwareTypeEnum softwareTypeEnum,
			@ApiParam(value = "Can the user run the software", required = true) @RequestParam("requestToRunSoftware") boolean requestToRunSoftware) throws UnsupportedSerializationFormatException, SerializationException {

		return new AuthorizeUserMethod("", "", SerializationFormat.XML).authorizeUser(userName, password,
				softwareName, softwareVersion, softwareDeveloper, softwareTypeEnum, requestToRunSoftware);
	}

	@POST
//    @ApiImplicitParam(dataType = "string", name = "userInformationXml", paramType = "body", required = true)
	@ApiOperation(value = "Add user.", notes = "Adds the user to the users collection.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/users", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String addUserToUsersList(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "User name to add", required = true) @RequestParam("userNameToAdd") String uernameToAdd,
			@ApiParam(value = "Password to add", required = true) @RequestParam("passwordToAdd") String passwordToAdd,
			@ApiParam(value = "User email", required = true) @RequestParam("userEmail") String userEmail) throws UnsupportedSerializationFormatException, SerializationException {

		return new AddUserMethod(username, password, SerializationFormat.XML).addUser(uernameToAdd, passwordToAdd, userEmail);
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
	@ApiOperation(value = "Delete user.", notes = "Removes a user with the given user ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/user/{userName}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	public @ResponseBody
	String deleteUserFromUseresCollection(@ApiParam(value = "Run ID.", required = true) @PathVariable("userName") String nameOfUserToDelete,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return new DeleteUserMethod(username, password, SerializationFormat.XML).deleteUser(nameOfUserToDelete);
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
	@ApiOperation(value = "Add role to user.", notes = "Add a role for the given user ID.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/user/{userId}/roles", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String addRoleToUser(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Request object", required = true) @RequestBody String requestBody) throws UnsupportedSerializationFormatException, SerializationException {

		return new AddRoleToUserMethod(username, password, SerializationFormat.XML).addRoleToUser(requestBody);
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

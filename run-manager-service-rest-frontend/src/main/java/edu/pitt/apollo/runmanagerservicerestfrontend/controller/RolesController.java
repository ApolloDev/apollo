package edu.pitt.apollo.runmanagerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservicerestfrontend.methods.AddRoleMethod;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;

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
	@ApiOperation(value = "Add role.", notes = "Adds the role to the roles collection.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/roles", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String addRolesToRolesCollection(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Request object", required = true) @RequestBody String requestBody) throws UnsupportedSerializationFormatException, SerializationException {

		return new AddRoleMethod(username, password, SerializationFormat.XML).addRole(requestBody);
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

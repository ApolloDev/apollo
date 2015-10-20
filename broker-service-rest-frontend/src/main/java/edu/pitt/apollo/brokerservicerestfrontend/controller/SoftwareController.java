package edu.pitt.apollo.brokerservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.brokerservicerestfrontend.methods.GetRegisteredSoftwareMethod;
import edu.pitt.apollo.brokerservicerestfrontend.methods.GetURLOfSoftwareMethod;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.types.v3_1_0.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v3_1_0.SerializationFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;


/*
 * Created by dcs27 on 5/15/15.
 * Purpose: This class contains the RESTful interfaces associated with the software collection.
 */
@Controller
@RequestMapping("/ws")
public class SoftwareController {
	/*--Methods for the Software collection--*/

	@GET
	@ApiOperation(value = "Get list of all registered software", notes = "Returns a list containing the IDs and names of all software registered with Apollo.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/software", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getListOfSoftwareFromCollection(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return new GetRegisteredSoftwareMethod(username, password, SerializationFormat.XML).getRegisteredSoftware();
	}

	@GET
	@ApiOperation(value = "Get URL of software", notes = "Returns the WSDL URL associated with the given software name and version.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/software/url", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody
	String getURLForSoftwareIdentification(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Software name", required = true) @RequestParam("softwareName") String softwareName,
			@ApiParam(value = "Software version", required = true) @RequestParam("softwareVersion") String softwareVersion,
			@ApiParam(value = "Software developer", required = true) @RequestParam("softwareDeveloper") String softwareDeveloper,
			@ApiParam(value = "Apollo software type enum", required = true) @RequestParam("softwareTypeEnum") ApolloSoftwareTypeEnum softwareTypeEnum) throws UnsupportedSerializationFormatException, SerializationException {

		return new GetURLOfSoftwareMethod(username, password, SerializationFormat.XML).getURLOfSoftwareMethod(softwareName, softwareVersion, softwareDeveloper, softwareTypeEnum);
	}
//    @ApiIgnore
//    @POST
//    @ApiOperation(value ="Add software identification.", notes = "Adds a new software identification to the software collection.", response = String.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "")
//    })
//    @RequestMapping(value = "/software", method = RequestMethod.POST, headers = "Accept=application/xml")
//    public
//    @ResponseBody
//    String addSoftwareIdentifcationToCollection() {
//        return null;
//    }
	//We cannot delete the software collection (DELETE), or put anything at this level (PUT).

	/*--Methods for individual software in the collectionb--*/
//    @GET
//    @ApiOperation(value ="Get software identification.", notes = "Returns the software identification for the give software ID.", response = String.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "")
//    })
//    @RequestMapping(value = "/software/{softwareId}", method = RequestMethod.GET, headers = "Accept=application/xml")
//    public
//    @ResponseBody
//    String getSoftwareIdentification(@ApiParam(value = "Software ID", required = true) @PathVariable("softwareId") BigInteger softwareId) {
//        return null;
//    }
//    @POST
//    @ApiOperation(value ="Edit software identification.", notes = "Edits the software identification for the give software ID.", response = String.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "")
//    })
//    @RequestMapping(value = "/software/{softwareId}", method = RequestMethod.POST, headers = "Accept=application/xml")
//    public
//    @ResponseBody
//    String editSoftwareIdentification(@ApiParam(value = "Software ID", required = true) @PathVariable("softwareId") BigInteger softwareId) {
//        return null;
//    }
	//We cannot DELETE software identification and we cannot PUT data at this level.
}

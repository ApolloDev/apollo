package edu.pitt.apollo.restservice.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;


/*
 * Created by dcs27 on 5/15/15.
 * Purpose: This class contains the RESTful interfaces associated with the software collection.
 */

@Controller
@RequestMapping("/ws")
public class SoftwareController {
    /*--Methods for the Software collection--*/
    @GET
    @ApiOperation(value ="List of all software in the collection.", notes = "Returns the list containing the software ID and name of all software in the collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/software", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getListOfSoftwareFromCollection() {
        return null;
    }
    @POST
    @ApiOperation(value ="Add software identification.", notes = "Adds a new software identification to the software collection.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/software", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String addSoftwareIdentifcationToCollection() {
        return null;
    }
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

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

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import java.math.BigInteger;

/**
 * Created by dcs27 on 5/18/15.
 * * Purpose: This class contains the RESTful interfaces associated with individual URL retreival and manipulation.
 */

@Controller
@RequestMapping("/ws")
public class URLController {
    /*--Methods for the url resource of a run--*/
    @GET
    @ApiOperation(value = "Get URL.", notes = "Returns the requested URL.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/url/{urlID}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getURLOfRunUsingRunAndFileId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,@ApiParam(value = "URL ID.", required = true) @PathVariable("urlId") BigInteger urlId) {
        return null;
    }

    @DELETE
    @ApiOperation(value = "Remove URL reference.", notes = "Removes the reference of a URL from the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/url/{urlId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String removeReferenceOfURLFromRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,@ApiParam(value = "URL ID.", required = true) @PathVariable("urlId") BigInteger urlId) {
        return null;
    }
    //We cannot create anything at the URL level (PUT), and we cannot edit a URL at this level (POST).

}

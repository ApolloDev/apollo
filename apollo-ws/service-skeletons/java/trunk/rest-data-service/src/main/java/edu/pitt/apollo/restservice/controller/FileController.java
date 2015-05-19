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
 * Purpose: This class contains the RESTful interfaces associated with individual file retreival and manipulation.
 */


@Controller
@RequestMapping("/ws")
public class FileController {
    /*--Methods for the file resource of a run--*/
    @GET
    @ApiOperation(value = "Get file.", notes = "Returns the requested file.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public
    @ResponseBody
    String getFileOfRunUsingRunAndFileId(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,@ApiParam(value = "File ID.", required = true) @PathVariable("fileId") BigInteger fileId) {
        return null;
    }

    @DELETE
    @ApiOperation(value = "Remove file reference.", notes = "Removes the reference of a file from the given run ID.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/file/{fileId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public
    @ResponseBody
    String removeReferenceOfFileFromRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,@ApiParam(value = "File ID.", required = true) @PathVariable("fileId") BigInteger fileId) {
        return null;
    }
    //We cannot create anything at the file level (PUT), and we cannot edit a file at this level (POST).

}

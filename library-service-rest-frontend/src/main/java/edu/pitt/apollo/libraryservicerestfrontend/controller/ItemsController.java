package edu.pitt.apollo.libraryservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.libraryservicerestfrontend.methods.*;
import edu.pitt.apollo.services_common.v4_0_1.SerializationFormat;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

/**
 * TODO: 2015-08-13: Still need to update the REST documentation. It's currently in a draft state. We also need to review the URIs for the service. Currently I am just focusing on getting the implementation completed.
 */
@Controller
@RequestMapping("/ws")
public class ItemsController {

    @GET
    @ApiOperation(value = "Retrieve an item from the library", notes = "Retrieves an item from the library. "
            + "If no revision parameter is specified, the latest approved revision will be returned.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String getLibraryItem(
            @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
            @ApiParam(value = "Revision", required = false) @RequestParam(value = "revision", required = false) Integer revision,
            @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new GetLibraryItemMethod(SerializationFormat.XML, authorization).getLibraryItem(urn, revision);
    }

    @GET
    @ApiOperation(value = "Retrieve the list of revisions and comments for an item", notes = "Retrieves a list of all revisions for an item along with their comments.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}/revisions", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String getAllRevisionsOfLibraryItem(
            @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
            @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new GetLibraryItemRevisionsMethod(SerializationFormat.XML, authorization).getLibraryItemReleaseVersion(urn);
    }

    @GET
    @ApiOperation(value = "Retrieve all reviewer comments for a given library item.", notes = "Retrieves a list of all reviewer comments for a given library item and revision.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}/revisions/{revision}/comments", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String getCommentsForLibraryItem(
            @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
            @ApiParam(value = "Revision", required = true) @PathVariable("revision") int revision,
            @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new GetCommentsForLibraryItemMethod(SerializationFormat.XML, authorization).getCommentsForLibraryItem(urn, revision);
    }

    @POST
    @ApiOperation(value = "Add a reviewer comment to a library item.", notes = "Adds a new comment to a given library item and revision.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}/revisions/{revision}/comments", method = RequestMethod.POST, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String addCommentToLibraryItem(
            @ApiParam(value = "Comment", required = true) @RequestParam("comment") String comment,
            @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
            @ApiParam(value = "Revision", required = true) @PathVariable("revision") int revision,
            @RequestHeader("Authorization") String authorization
    ) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new AddReviewerCommentMethod(SerializationFormat.XML, authorization).addReviewerComment(urn, revision, comment);
    }

    @GET
    @ApiOperation(value = "Retrieve a change log since the given time.", notes = "Retrieves a change log of the library since the given time. "
            + "The time must be the in following format 'yyyy-MM-dd HH:mm:ss' (e.g. 2015-08-30 17:21:43)", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/changelog", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String getChangeLog(
            @ApiParam(value = "Time", required = true) @RequestParam("dateTime") String dateTime,
            @RequestHeader("Authorization") String authorization
    ) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new GetChangeLogForLibraryItemsModifiedSinceDateTime(SerializationFormat.XML, authorization).getChangeLogForLibraryItemsModifiedSinceDateTime(dateTime);
    }

    @GET
    @ApiOperation(value = "Retrieve the approved revision of a library item.", notes = "Retrieves the approved revision of a library item if one exists. ",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}/revisions/approved", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String getApprovedRevisionOfLibraryItem(
            @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
            @RequestHeader("Authorization") String authorization
    ) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new GetApprovedRevisionOfLibraryItemMethod(SerializationFormat.XML, authorization).getLibraryItemReleaseVersion(urn);
    }

    @GET
    @ApiOperation(value = "Retrieve all library item urns for a given type.", notes = "I have no idea how of the syntax to specify the type.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String getLibraryItemUrns(
            @ApiParam(value = "Item Type", required = true) @RequestParam("itemType") String itemType,
            @RequestHeader("Authorization") String authorization
    ) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new GetLibraryItemUrnsMethod(SerializationFormat.XML, authorization).getLibraryItemUrns(itemType);
    }

    @POST
    @ApiOperation(value = "Mark a revision of a library item as approved.", notes = "Marks the specified revision of the given library"
            + " item as being approved.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}/revisions/{revision}/approve", method = RequestMethod.POST, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String setLibraryItemReleaseVersion(
            @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
            @ApiParam(value = "Revision", required = true) @PathVariable("revision") int revision,
            @ApiParam(value = "Comment", required = true) @PathVariable("comment") String comment,
            @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new SetLibraryItemReleaseVersionMethod(SerializationFormat.XML, authorization).setLibraryItemReleaseVersion(urn, revision, comment);
    }

    @POST
    @ApiOperation(value = "Set a library item as not having a public revision.", notes = "", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}/hide", method = RequestMethod.POST, headers = "Accept=applicaton/xml")
    public
    @ResponseBody
    String hideLibraryItem(
            @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
            @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, SerializationException, UnsupportedAuthorizationTypeException {
        return new SetLibraryItemAsNotReleasedMethod(SerializationFormat.XML, authorization).hideLibraryItem(urn);
    }

    @POST
    @ApiOperation(value = "Add an item to the library.", notes = "Adds a new item to the library.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String addLibraryItem(
                          @ApiParam(value = "Comment", required = true) @RequestParam("comment") String comment,
                          @ApiParam(value = "Library item container", required = true) @RequestBody String messageBody,
                          @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
        return new AddLibraryItemMethod(SerializationFormat.XML, authorization).addLibraryItem(messageBody, comment);
    }

    @POST
    @ApiOperation(value = "Update an existing library item.", notes = "Revises an existing library item.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/items/{urn}", method = RequestMethod.POST, headers = "Accept=application/xml")
    public
    @ResponseBody
    String reviseLibraryItem(
                             @ApiParam(value = "Comment", required = true) @RequestParam("comment") String comment,
                             @ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
                             @ApiParam(value = "Library item container", required = true) @RequestBody String messageBody,
                             @RequestHeader("Authorization") String authorization) throws UnsupportedSerializationFormatException, UnsupportedAuthorizationTypeException {
        return new UpdateLibraryItemMethod(SerializationFormat.XML, authorization).updateLibraryItem(messageBody, comment, urn);
    }
}

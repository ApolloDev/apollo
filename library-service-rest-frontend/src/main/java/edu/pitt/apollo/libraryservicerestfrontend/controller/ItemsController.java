package edu.pitt.apollo.libraryservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_0_2.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_2.CatalogEntry;
import edu.pitt.apollo.library_service_types.v3_0_2.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v3_0_2.TextContainer;
import edu.pitt.apollo.libraryservicerestfrontend.methods.AddLibraryItemMethod;
import edu.pitt.apollo.services_common.v3_0_2.*;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;
import java.util.Properties;

/**
 * Created by dcs27 on 5/18/15. Purpose: This class contains the RESTful interfaces associated with individual file retrieval and manipulation.
 */
@Controller
@RequestMapping("/ws")
public class ItemsController {

	/*--Methods for the file resource of a run--*/
	@GET
	@ApiOperation(value = "Get an item.", notes = "Returns the requested item.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
	public @ResponseBody
	String getFileOfRunUsingRunAndFileId(@ApiParam(value = "Item ID", required = true) @PathVariable("itemId") BigInteger itemId,
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password) throws UnsupportedSerializationFormatException, SerializationException {

		return "Hello";
	}

	@POST
	@ApiOperation(value = "Insert and start run.", notes = "Inserts a given run into the runs collection and starts the run.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String postRunToRunsCollection(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
								   @ApiParam(value = "Password", required = true) @RequestParam("password") String password,
								   @ApiParam(value = "Run message.", required = true) @RequestBody String messageBody) throws UnsupportedSerializationFormatException {
		return new AddLibraryItemMethod(username, password, SerializationFormat.XML).addLibraryItem(messageBody);
	}
	/* @DELETE
	 @ApiOperation(value = "Remove file reference.", notes = "Removes the reference of a file from the given run ID.", response = String.class)
	 @ApiResponses(value = {
	 @ApiResponse(code = 200, message = "")
	 })
	 @RequestMapping(value = "/file/{fileId}", method = RequestMethod.DELETE, headers = "Accept=application/xml")
	 public
	 @ResponseBody
	 String removeReferenceOfFileFromRun(@ApiParam(value = "Run ID.", required = true) @PathVariable("runId") BigInteger runId,@ApiParam(value = "File ID.", required = true) @PathVariable("fileId") BigInteger fileId) {
	 return null;
	 }*/
    //We cannot create anything at the file level (PUT), and we cannot edit a file at this level (POST).

	public static void main(String[] args) throws UnsupportedSerializationFormatException, SerializationException {
		Authentication authentication = new Authentication();
		authentication.setRequesterId(args[1]);
		authentication.setRequesterPassword(args[2]);

		AddLibraryItemContainerMessage alicm =  new AddLibraryItemContainerMessage();
		alicm.setAuthentication(authentication);
		alicm.setComment("Hello this is a comment");

		LibraryItemContainer lic = new LibraryItemContainer();
		CatalogEntry ce = new CatalogEntry();
		ce.setItemDescription("Item description here!");
		ce.setJavaClassName(TextContainer.class.getName());

		TextContainer tc = new TextContainer();
		tc.setText("Hello this is the container text!");
		lic.setLibraryItem(tc);
		lic.setCatalogEntry(ce);

		alicm.setLibraryItemContainer(lic);

		Serializer serializer = SerializerFactory.getSerializer(SerializationFormat.XML);
		String serializedString = serializer.serializeObject(alicm);
		System.out.println(serializedString);

		Request request = new Request();
		request.setRequestBody(serializedString);
		RequestMeta requestMeta = new RequestMeta();
		requestMeta.setIsBodySerialized(true);
		ObjectSerializationInformation objectSerializationInformation = new ObjectSerializationInformation();
		objectSerializationInformation.setClassName(AddLibraryItemContainerMessage.class.getSimpleName());
		objectSerializationInformation.setClassNameSpace(Serializer.LIBRARY_SERVICE_NAMESPACE);
		objectSerializationInformation.setFormat(SerializationFormat.XML);
		requestMeta.setRequestBodySerializationInformation(objectSerializationInformation);
		request.setRequestMeta(requestMeta);

		ItemsController ic = new ItemsController();
		try {

			ic.postRunToRunsCollection(args[1], args[2], serializer.serializeObject(request));
		} catch (UnsupportedSerializationFormatException e) {
			e.printStackTrace();
		}
	}
}

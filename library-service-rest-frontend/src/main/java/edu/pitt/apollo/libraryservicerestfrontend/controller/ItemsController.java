package edu.pitt.apollo.libraryservicerestfrontend.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.library_service_types.v3_0_2.*;
import edu.pitt.apollo.libraryservicerestfrontend.methods.*;
import edu.pitt.apollo.services_common.v3_0_2.*;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.Serializer;
import edu.pitt.apollo.utilities.SerializerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;
import java.util.Properties;

@Controller
@RequestMapping("/ws")
public class ItemsController {

	/*--Methods for the file resource of a run--*/
	@GET
	@ApiOperation(value = "Get an item.", notes = "Returns the requested item.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/{urn}/versions/{version}", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
	public @ResponseBody
	String getLibraryItem(
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
			@ApiParam(value = "Item Version", required = false) @PathVariable("version") int version) throws UnsupportedSerializationFormatException, SerializationException {
		return new GetLibraryItemMethod(username, password, SerializationFormat.XML).getLibraryItem(urn, version);
	}


	/*--Methods for the file resource of a run--*/
	@GET
	@ApiOperation(value = "Get an item.", notes = "Returns the requested item.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/{urn}/versions/public", method = RequestMethod.GET, headers = "Accept=applicaton/xml")
	public @ResponseBody
	String getLibraryItemReleaseVersion(
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn) throws UnsupportedSerializationFormatException, SerializationException {
		return new GetLibraryItemReleaseVersionMethod(username, password, SerializationFormat.XML).getLibraryItemReleaseVersion(urn);
	}

	@POST
	@ApiOperation(value = "Get an item.", notes = "Returns the requested item.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/{urn}/versions/{version}/public", method = RequestMethod.POST, headers = "Accept=applicaton/xml")
	public @ResponseBody
	String setLibraryItemReleaseVersion(
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn,
			@ApiParam(value = "Item Version", required = true) @PathVariable("version") int version,
			@ApiParam(value = "Item comment", required = true) @PathVariable("comment") String comment) throws UnsupportedSerializationFormatException, SerializationException {
		return new SetLibraryItemReleaseVersionMethod(username, password, SerializationFormat.XML).setLibraryItemReleaseVersion(urn, version, comment);
	}

	@POST
	@ApiOperation(value = "Get an item.", notes = "Returns the requested item.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/{urn}/versions/{version}/public/", method = RequestMethod.DELETE, headers = "Accept=applicaton/xml")
	public @ResponseBody
	String setLibraryItemAsNotReleased(
			@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "Password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "Item URN", required = true) @PathVariable("urn") int urn) throws UnsupportedSerializationFormatException, SerializationException {
		return new SetLibraryItemAsNotReleasedMethod(username, password, SerializationFormat.XML).setLibraryItemAsNotReleased(urn);
	}


	@POST
	@ApiOperation(value = "Insert and start run.", notes = "Inserts a given run into the runs collection and starts the run.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/{urn}", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String addLibraryItem(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
								   @ApiParam(value = "Password", required = true) @RequestParam("password") String password,
								   @ApiParam(value = "Run message.", required = true) @RequestBody String messageBody) throws UnsupportedSerializationFormatException {
		return new AddLibraryItemMethod(username, password, SerializationFormat.XML).addLibraryItem(messageBody);
	}

	@POST
	@ApiOperation(value = "Insert and start run.", notes = "Inserts a given run into the runs collection and starts the run.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value = "/items/", method = RequestMethod.POST, headers = "Accept=application/xml")
	public @ResponseBody
	String updateLibraryItem(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
								   @ApiParam(value = "Password", required = true) @RequestParam("password") String password,
								   @ApiParam(value = "Run message.", required = true) @RequestBody String messageBody) throws UnsupportedSerializationFormatException {
		return new UpdateLibraryItemMethod(username, password, SerializationFormat.XML).updateLibraryItem(messageBody);
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


		UpdateLibraryItemContainerMessage ulicm = new UpdateLibraryItemContainerMessage();
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
		ulicm.setLibraryItemContainer(lic);
		ulicm.setUrn(5);
		ulicm.setAuthentication(authentication);
		ulicm.setComment("Test comment!!!!");

		Serializer serializer = SerializerFactory.getSerializer(SerializationFormat.XML);
		String serializedString = serializer.serializeObject(alicm);
		//System.out.println(serializedString);

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
			//ic.postRunToRunsCollection(args[1], args[2], serializer.serializeObject(request));
			String item = ic.getLibraryItem(args[1], args[2], 5, 1);
			Deserializer deserializer = DeserializerFactory.getDeserializer(SerializationFormat.XML);
			Response response = (Response) deserializer.getObjectFromMessage(item, Response.class);
			GetLibraryItemContainerResult mylic = (GetLibraryItemContainerResult) deserializer.getObjectFromMessage(response.getResponseBody().get(0), GetLibraryItemContainerResult.class);
			TextContainer mytc = (TextContainer) mylic.getLibraryItemContainer().getLibraryItem();
			System.out.println(mytc.getText());
			ic.updateLibraryItem(args[1], args[2], serializer.serializeObject(request));
		} catch (UnsupportedSerializationFormatException e) {
			e.printStackTrace();
		} catch (DeserializationException e) {
			e.printStackTrace();
		}


	}
}

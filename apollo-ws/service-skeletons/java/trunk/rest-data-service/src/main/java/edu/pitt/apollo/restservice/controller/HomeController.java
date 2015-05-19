package edu.pitt.apollo.restservice.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.restservice.exceptions.SchemaDefinitionNotFoundException;
import edu.pitt.apollo.restservice.exceptions.XsdNotFoundException;
import edu.pitt.apollo.restservice.rest.responsemessage.*;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.*;
import edu.pitt.apollo.restservice.rest.utils.BuildIdentificationKeyResponseMessage;
import edu.pitt.apollo.restservice.rest.utils.BuildStatusResponseMessage;
import edu.pitt.apollo.restservice.utils.*;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.io.File;
import java.io.FilenameFilter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApiIgnore
@Controller
public class HomeController {
	static boolean isIds = false;

//	@GET
//	@ApiOperation(value = "Get software identification for run.", notes = "Returns the software identification for the given run ID.", response = String.class)
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "")
//	})
//	@RequestMapping(value="/ws/getSoftwareIdentificationForRun/{runId}", method= RequestMethod.GET, headers="Accept=application/xml")
//	public @ResponseBody String getSoftwareIdentificationKeyForRun(@ApiParam(value = "Run ID", required = true) @PathVariable("runId") String runId)
//	{
//		String xmlResponse ="";
//		BigInteger runIdAsBigInt = new BigInteger(runId);
//
//		GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
//		message.setRunId(runIdAsBigInt);
//
//		DataServiceImpl impl = new DataServiceImpl();
//
//		GetSoftwareIdentificationForRunResult result = impl.getSoftwareIdentificationForRun(message);
//		GetSoftwareIdentificationForRunRestMessage returnMessage = new GetSoftwareIdentificationForRunRestMessage();
//		Meta meta = new Meta();
//		if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED)
//		{
//			ApolloDatabaseExceptionMessage apolloExceptionMessage = new ApolloDatabaseExceptionMessage();
//			meta.setStatus(apolloExceptionMessage.getStatus());
//			meta.setStatusMessage(result.getMethodCallStatus().getMessage());
//			returnMessage.setMeta(meta);
//		}
//		else
//		{
//			RequestSuccessfulMessage responseSuccessfulMessage = new RequestSuccessfulMessage();
//			meta.setStatus(responseSuccessfulMessage.getStatus());
//			meta.setStatusMessage(responseSuccessfulMessage.getMessage());
//			returnMessage.setMeta(meta);
//			returnMessage.setResource(result.getSoftwareIdentification());
//		}
//
//		return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
//	}

	@POST
	@ApiOperation(value = "Get software identification for run.", notes = "Returns the software identification for the given run ID.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/getSoftwareIdentificationForRun/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String getSoftwareIdentificationForRun(String runId)
	{
		String xmlResponse ="";
		GetSoftwareIdentificationForRunRestMessage returnMessage = new GetSoftwareIdentificationForRunRestMessage();
		Meta meta = new Meta();
		BigInteger runIdAsBigInt;
		try {
			runIdAsBigInt = new BigInteger(runId);
		}
		catch(Exception ex){
			meta.setStatus(400);
			meta.setStatusMessage("A valid runId is required.");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
		}
		GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
		message.setRunId(runIdAsBigInt);

		DataServiceImpl impl = new DataServiceImpl();

		GetSoftwareIdentificationForRunResult result = impl.getSoftwareIdentificationForRun(message);

		if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED)
		{
			ApolloDatabaseExceptionMessage apolloExceptionMessage = new ApolloDatabaseExceptionMessage();
			meta.setStatus(apolloExceptionMessage.getStatus());
			meta.setStatusMessage(result.getMethodCallStatus().getMessage());
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);
		}
		else
		{
			RequestSuccessfulMessage responseSuccessfulMessage = new RequestSuccessfulMessage();
			meta.setStatus(responseSuccessfulMessage.getStatus());
			meta.setStatusMessage(responseSuccessfulMessage.getMessage());
			meta.setNumberOfReturnedResults(1);
			returnMessage.setMeta(meta);
			returnMessage.setResource(result.getSoftwareIdentification());
		}

		return ConvertResponseMessagesToXml.convertGetSoftwareIdentificationForRunMessageToXmlJaxb(returnMessage);
	}

	@POST
	@ApiOperation(value = "Update run status for run.", notes = "Updates the run status and run message for the given run ID.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/updateStatusOfRun/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String updateRunStatus(String runId,String methodCallStatusEnum, String statusMessage)
	{
		//
		String xmlResponse ="";
		StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();

		BigInteger runIdAsBigInt;
		MethodCallStatusEnum statusToUpdateTo;
		try {
			runIdAsBigInt = new BigInteger(runId);
			statusToUpdateTo = MethodCallStatusEnum.valueOf(methodCallStatusEnum);
		}
		catch(Exception ex){
			Meta meta = new Meta();
			meta.setStatus(400);
			meta.setStatusMessage("A valid runId and methodCallStatusEnum are required");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
		}



		UpdateStatusOfRunMessage updateStatusOfRunMessage = new UpdateStatusOfRunMessage();
		updateStatusOfRunMessage.setRunId(runIdAsBigInt);
		updateStatusOfRunMessage.setStatusMessage(statusMessage);
		updateStatusOfRunMessage.setStatusEnum(statusToUpdateTo);

		DataServiceImpl impl = new DataServiceImpl();

		UpdateStatusOfRunResult result = impl.updateStatusOfRun(updateStatusOfRunMessage);


//		Meta meta = new Meta();
		if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED)
		{
			returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
			/*ApolloDatabaseExceptionMessage apolloExceptionMessage = new ApolloDatabaseExceptionMessage();
			meta.setStatus(apolloExceptionMessage.getStatus());
			meta.setStatusMessage(result.getMethodCallStatus().getMessage());
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);*/

		}
		else
		{
			returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();

			/*RequestSuccessfulMessage responseSuccessfulMessage = new RequestSuccessfulMessage();
			meta.setStatus(responseSuccessfulMessage.getStatus());
			meta.setStatusMessage(responseSuccessfulMessage.getMessage());
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);*/

		}

		return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
	}

	@POST
	@ApiOperation(value = "Get software identification key from software identification", notes = "Returns the software identification key of the given softawre identification.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/getSoftwareIdentificationKeyFromSoftwareIdentification/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String getSoftwareIdentificationKeyFromSoftwareIdentification(String softwareIdentification)
	{
		//
		String xmlResponse ="";
		GetIdentificationKeyRestMessage returnMessage = new GetIdentificationKeyRestMessage();
		Meta meta = new Meta();
		try {
			XmlValidationTools.validateXMLSchemaXml(softwareIdentification);
		} catch (SchemaDefinitionNotFoundException e) {

			SchemaDefinitionNotFound sdnf = new SchemaDefinitionNotFound();
			meta.setNumberOfReturnedResults(0);
			meta.setStatus(sdnf.getStatus());
			meta.setStatusMessage(sdnf.getMessage());
			returnMessage.setMeta(meta);


		} catch (XsdNotFoundException e) {
			XsdNotFound xndf = new XsdNotFound();
			meta.setNumberOfReturnedResults(0);
			meta.setStatus(xndf.getStatus());
			meta.setStatusMessage(xndf.getMessage());
			returnMessage.setMeta(meta);
		}

		SoftwareIdentification si = ParseXmlToAndFromObject.convertFromXmlToSoftwareIdentification(softwareIdentification);
		DataServiceImpl impl = new DataServiceImpl();

		GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage message = new GetSoftwareIdentificationKeyFromSoftwareIdentificationMessage();
		message.setSoftwareIdentification(si);

		GetSoftwareIdentificationKeyFromSoftwareIdentificationResult result = impl.getSoftwareIdentificationKeyFromSoftwareIdentification(message);
		if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildFailedGetIdentificationKeyRestMessage(result.getMethodCallStatus().getMessage());
			/*ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
			meta.setNumberOfReturnedResults(0);
			meta.setStatusMessage(result.getMethodCallStatus().getMessage());
			meta.setStatus(adem.getStatus());
			returnMessage.setMeta(meta);*/
		}
		else
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildSuccessfulGetIdentificationKeyRestMessage(result.getSoftwareIdentificationKey());
			/*RequestSuccessfulMessage RequestSuccessfulMessage = new RequestSuccessfulMessage();
			meta.setNumberOfReturnedResults(1);
			meta.setStatus(RequestSuccessfulMessage.getStatus());
			meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
			returnMessage.setMeta(meta);
			BigInteger r = result.getSoftwareIdentificationKey();
			returnMessage.setResource(result.getSoftwareIdentificationKey());*/
		}

		return ConvertResponseMessagesToXml.convertGetIdentificationKeyRestMessageXmlJaxb(returnMessage);
//		return null;
	}


	@POST
	@ApiOperation(value = "Get software identification key for a run.", notes = "Returns the software identification key for the given run ID.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/getSoftwareIdentificationKeyForRun/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String getSoftwareIdentificationKeyForRun(String runId)
	{
		String xmlResponse ="";
		GetIdentificationKeyRestMessage returnMessage = new GetIdentificationKeyRestMessage();
		BigInteger runIdAsBigInt;
		try {
			runIdAsBigInt = new BigInteger(runId);

		}
		catch(Exception ex){
			Meta meta = new Meta();
			meta.setStatus(400);
			meta.setStatusMessage("A valid runId is required");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertGetIdentificationKeyRestMessageXmlJaxb(returnMessage);
		}
		DataServiceImpl impl = new DataServiceImpl();
//		GetIdentificationKeyRestMessage returnMessage = new GetIdentificationKeyRestMessage();

		GetSoftwareIdentificationKeyForRunMessage message = new GetSoftwareIdentificationKeyForRunMessage();
		message.setRunId(runIdAsBigInt);
		GetSoftwareIdentificationKeyForRunResult result = impl.getSoftwareIdentificationKeyForRun(message);


		if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildFailedGetIdentificationKeyRestMessage(result.getMethodCallStatus().getMessage());
//			ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
//			meta.setNumberOfReturnedResults(0);
//			meta.setStatusMessage(result.getMethodCallStatus().getMessage());
//			meta.setStatus(adem.getStatus());
//			returnMessage.setMeta(meta);
		}
		else
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildSuccessfulGetIdentificationKeyRestMessage(result.getSoftwareIdentificationKey());
//			meta.setStatus(RequestSuccessfulMessage.getStatus());
//			meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
//			meta.setNumberOfReturnedResults(1);
//			returnMessage.setResource(result.getSoftwareIdentificationKey());
		}


		return ConvertResponseMessagesToXml.convertGetIdentificationKeyRestMessageXmlJaxb(returnMessage);
	}


	@POST
	@ApiOperation(value = "Get run data description ID", notes = "Returns a data description ID given a data format, data label (name), data content type, data source software key, and a data destination software key. ", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/getRunDataDescriptionId/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String getRunDataDescriptionId(String dataFormat, String dataLabel, String dataContentType, String sourceSoftwareId, String destinationSoftwareId)
	{
		BigInteger sourceSoftwareIdAsBigInt;
		BigInteger destinationSoftwareIdAsBigInt;
		DbContentDataFormatEnum dbContentDataFormatEnum;
		DbContentDataType dbContentDataType;
		DataServiceImpl impl = new DataServiceImpl();
		GetIdentificationKeyRestMessage returnMessage= new GetIdentificationKeyRestMessage();

		Meta meta = new Meta();

		try {
			sourceSoftwareIdAsBigInt = new BigInteger(sourceSoftwareId);
			destinationSoftwareIdAsBigInt = new BigInteger(destinationSoftwareId);
			dbContentDataFormatEnum = DbContentDataFormatEnum.valueOf(dataFormat.toUpperCase());
			dbContentDataType = DbContentDataType.valueOf(dataContentType.toUpperCase());
		}
		catch(Exception ex){

			meta.setStatus(400);
			meta.setStatusMessage("A valid runId is required");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertGetIdentificationKeyRestMessageXmlJaxb(returnMessage);
		}
//		Meta meta = new Meta();

		GetRunDataDescriptionIdMessage message = new GetRunDataDescriptionIdMessage();
		message.setDataDestinationSoftwareIdKey(destinationSoftwareIdAsBigInt);
		message.setDataSourceSoftwareIdKey(sourceSoftwareIdAsBigInt);
		message.setDataLabel(dataLabel);
		message.setDataType(dbContentDataType);
		message.setDataFormat(dbContentDataFormatEnum);

		GetRunDataDescriptionIdResult result = impl.getRunDataDescriptionId(message);

		if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED)
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildFailedGetIdentificationKeyRestMessage(result.getMethodCallStatus().getMessage());
		}
		else
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildSuccessfulGetIdentificationKeyRestMessage(result.getRunDescriptionId());
		}

		return ConvertResponseMessagesToXml.convertGetIdentificationKeyRestMessageXmlJaxb(returnMessage);
	}

	@POST
	@ApiOperation(value = "Add text data content", notes = "Adds data content to the supplied url and returns an identification key. ", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/addTextDataContentMethod/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String addTextDataContentMethod(String url)
	{
		GetIdentificationKeyRestMessage returnMessage = new GetIdentificationKeyRestMessage();
		if(url==null)
		{
				Meta meta = new Meta();
				meta.setStatus(400);
				meta.setStatusMessage("A valid runId is required");
				meta.setNumberOfReturnedResults(0);
				returnMessage.setMeta(meta);

				return ConvertResponseMessagesToXml.convertGetIdentificationKeyRestMessageXmlJaxb(returnMessage);

		}

		DataServiceImpl impl = new DataServiceImpl();
		AddTextDataContentMessage message = new AddTextDataContentMessage();
		message.setUrl(url);
		AddTextDataContentResult result = impl.addTextDataContent(message);

		if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildFailedGetIdentificationKeyRestMessage(result.getMethodCallStatus().getMessage());
		}
		else
		{
			returnMessage = BuildIdentificationKeyResponseMessage.buildSuccessfulGetIdentificationKeyRestMessage(result.getDataContentKey());
		}


		return ConvertResponseMessagesToXml.convertGetIdentificationKeyRestMessageXmlJaxb(returnMessage);
	}

	@POST
	@ApiOperation(value = "Associate content with run ID", notes = "Associates content of a data content key and run data descriptiono key to a given run ID.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/associateContentWithRunIdMethod/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String associateContentWithRunIdMethod(String runId, String dataContentKey, String runDataDescriptionKey){
		BigInteger runIdAsBigInt;
		BigInteger dataContentKeyAsBigInt;
		BigInteger runDataDescriptionKeyAsBigInt;

		StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
		try {
			runIdAsBigInt = new BigInteger(runId);
			dataContentKeyAsBigInt = new BigInteger(dataContentKey);
			runDataDescriptionKeyAsBigInt = new BigInteger(runDataDescriptionKey);
		}
		catch(Exception ex){
			Meta meta = new Meta();
			meta.setStatus(400);
			meta.setStatusMessage("A valid runId, dataContentKey, and runDataDescriptionKey are required");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
		}


		DataServiceImpl impl = new DataServiceImpl();

		AssociateContentWithRunIdMessage message = new AssociateContentWithRunIdMessage();
		message.setRunDataDescriptionId(runDataDescriptionKeyAsBigInt);
		message.setDataContentKey(dataContentKeyAsBigInt);
		message.setRunId(runIdAsBigInt);

		AssociateContentWithRunIdResult result = impl.associateContentWithRunId(message);

		if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
		{
			returnMessage = BuildStatusResponseMessage.buildFailedStatusResponseMessage(result.getMethodCallStatus().getMessage());
		}
		else
		{
			returnMessage = BuildStatusResponseMessage.buildSuccessfulStatusResponseMessage();
		}


		return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
	}

	@POST
	@ApiOperation(value = "Get all output files url as zip.", notes = "Starts process to get all output files as zipped given a run ID.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/getAllOutputFilesURLAsZip/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String getAllOutputFilesURLAsZip(String runId){
		BigInteger runIdAsBigInteger;
		StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
		Meta meta = new Meta();

		try {
			runIdAsBigInteger = new BigInteger(runId);
		}
		catch(Exception ex){

			meta.setStatus(400);
			meta.setStatusMessage("A valid runId is required");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
		}

		meta.setNumberOfReturnedResults(0);
		meta.setStatus(RequestSuccessfulMessage.getStatus());
		meta.setStatusMessage(RequestSuccessfulMessage.getMessage());

		DataServiceImpl impl = new DataServiceImpl();
		impl.getAllOutputFilesURLAsZip(runIdAsBigInteger);

		return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);

	}

	@POST
	@ApiOperation(value = "Get output files url as zip.", notes = "Starts process to get output files as zipped given a run ID.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/getOutputFilesURLAsZip/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String getOutputFilesURLAsZip(String runId){
		BigInteger runIdAsBigInteger;
		StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
		Meta meta = new Meta();

		try {
			runIdAsBigInteger = new BigInteger(runId);
		}
		catch(Exception ex){

			meta.setStatus(400);
			meta.setStatusMessage("A valid runId is required");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
		}

		meta.setNumberOfReturnedResults(0);
		meta.setStatus(RequestSuccessfulMessage.getStatus());
		meta.setStatusMessage(RequestSuccessfulMessage.getMessage());

		DataServiceImpl impl = new DataServiceImpl();
		impl.getOutputFilesURLAsZip(runIdAsBigInteger);

		return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
		//getAllOutputFilesURLAsZip
	}

	@POST
	@ApiOperation(value = "Get output files url.", notes = "Starts process to get output files url given a run ID.", response = String.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "")
	})
	@RequestMapping(value="/ws/getOutputFilesURLs/", method= RequestMethod.POST, headers="Accept=application/xml")
	public @ResponseBody String getOutputFilesURLs(String runId){
		BigInteger runIdAsBigInteger;
		StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
		Meta meta = new Meta();

		try {
			runIdAsBigInteger = new BigInteger(runId);
		}
		catch(Exception ex){

			meta.setStatus(400);
			meta.setStatusMessage("A valid runId is required");
			meta.setNumberOfReturnedResults(0);
			returnMessage.setMeta(meta);

			return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);
		}

		meta.setNumberOfReturnedResults(0);
		meta.setStatus(RequestSuccessfulMessage.getStatus());
		meta.setStatusMessage(RequestSuccessfulMessage.getMessage());

		DataServiceImpl impl = new DataServiceImpl();
		impl.getOutputFilesURLs(runIdAsBigInteger);

		return ConvertResponseMessagesToXml.convertStatusResponseMessagetoXmlJaxb(returnMessage);

	}





	/*--Get versions of the XSD--*/
	@RequestMapping(value="/apolloXsdVersions", method= RequestMethod.GET, headers="Accept=application/xml")
	public @ResponseBody String getApolloXsdVersions(String infectiousDiseaseScenario)
	{
		String xmlResponse = "";


		List<String> versionNames = getFileList();

		RequestSuccessfulMessage successful = new RequestSuccessfulMessage();
		Meta meta = new Meta();
		meta.setStatus(successful.getStatus());
		meta.setStatusMessage(successful.getMessage());
		meta.setNumberOfReturnedResults(versionNames.size());

		ApolloXsdVersionResponseMessage xsdResponse = new ApolloXsdVersionResponseMessage();

		xsdResponse.setMeta(meta);
		xsdResponse.setXsdVersions(versionNames);


		return ConvertResponseMessagesToXml.convertApolloXsdVersionResponseMessageToXmlJaxb(xsdResponse);
	}



	private static List<String> getFileList() {
		//Map<String,File> filenameToFileMap = new HashMap<String,File>();
		ClassLoader cLoader = HomeController.class.getClassLoader();

		List<String> versionsList = new ArrayList<String>();
		File file = new File(cLoader.getResource("./apollo-xsd").getPath());
		if (file.exists()) {
			File[] files = file.listFiles(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".xsd");
				}
			});

			for(File f : files)
			{
				String name = f.getName();
				String[] nameSplitOnUnderScore = name.split("_");
				String[] versionSplitOnPeriod = nameSplitOnUnderScore[1].split(".xsd");
				versionsList.add(versionSplitOnPeriod[0].replace(".","_"));
			}
			return versionsList;
		} else {
			System.out.println(file.getAbsolutePath() + " folder not exists");
			return null;
		}
	}


}

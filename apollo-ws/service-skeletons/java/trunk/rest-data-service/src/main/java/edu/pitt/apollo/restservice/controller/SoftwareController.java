package edu.pitt.apollo.restservice.controller;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.RestDataServiceImpl;
import edu.pitt.apollo.data_service_types.v3_0_0.GetListOfRegisteredSoftwareResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLForSoftwareIdentificationMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetURLForSoftwareIdentificationResult;
import edu.pitt.apollo.restservice.rest.responsemessage.GetListOfRegisteredSoftwareRestMessage;
import edu.pitt.apollo.restservice.rest.responsemessage.GetURLForSoftwareIdentificationRestMessage;
import edu.pitt.apollo.restservice.rest.utils.BuildGetListOfRegisteredSoftwareRestMessage;
import edu.pitt.apollo.restservice.rest.utils.softwarerestmessages.BuildGetURLForSoftwareIdentificationRestMessage;
import edu.pitt.apollo.restservice.utils.ConvertResponseMessagesToXml;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.math.BigInteger;
import java.util.List;


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
    String getListOfSoftwareFromCollection(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                           @ApiParam(value = "Password", required = true) @RequestParam("password") String password) {
        RestDataServiceImpl impl = new RestDataServiceImpl();
        GetListOfRegisteredSoftwareRestMessage returnMessage = new GetListOfRegisteredSoftwareRestMessage();
        Authentication authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);
        GetListOfRegisteredSoftwareResult result = impl.getListOfRegisteredSoftware(authentication);

        if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED) {
            returnMessage =  BuildGetListOfRegisteredSoftwareRestMessage.buildFailedGetIdentificationKeyRestMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            returnMessage =  BuildGetListOfRegisteredSoftwareRestMessage.buildSuccessfulGetIdentificationKeyRestMessage(result.getResgisteredSoftware());
        }
        return ConvertResponseMessagesToXml.convertGetListOfRegisteredSoftwareRestMessage(returnMessage);
    }


    @GET
    @ApiOperation(value ="Get URL of software.", notes = "Returns the WSDL URL of the given software name and version.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "")
    })
    @RequestMapping(value = "/software/url", method = RequestMethod.GET, headers = "Accept=application/xml")
    public @ResponseBody String getURLForSoftwareIdentification(@ApiParam(value = "Username", required = true) @RequestParam("username") String username,
                                           @ApiParam(value = "Password", required = true) @RequestParam("password") String password,
                                           @ApiParam(value="Software name", required = true) @RequestParam("softwareName") String softwareName,
                                           @ApiParam(value="Software version", required = true) @RequestParam("softwareVersion") String softwareVersion) {
        RestDataServiceImpl impl = new RestDataServiceImpl();
        GetURLForSoftwareIdentificationRestMessage returnMessage = new GetURLForSoftwareIdentificationRestMessage();
        GetURLForSoftwareIdentificationMessage message = new GetURLForSoftwareIdentificationMessage();
        Authentication authentication = new Authentication();
        authentication.setRequesterId(username);
        authentication.setRequesterPassword(password);
        message.setAuthentication(authentication);
        message.setSoftwareName(softwareName);
        message.setSoftwareVersion(softwareVersion);
        GetURLForSoftwareIdentificationResult result = impl.getURLForSoftwareIdentification(message);

        if(result.getMethodCallStatus().getStatus()== MethodCallStatusEnum.FAILED) {
            returnMessage =  BuildGetURLForSoftwareIdentificationRestMessage.buildFailedGetURLForSoftwareIdentificationMessage(result.getMethodCallStatus().getMessage());
        }
        else{
            returnMessage =  BuildGetURLForSoftwareIdentificationRestMessage.buildSuccessfulGetURLForSoftwareIdentificationMessage(result.getWsdlURL());
        }
        String xml = ConvertResponseMessagesToXml.convertGetURLForSoftwareIdentificationRestMessageToXmlJaxB(returnMessage);
        return xml;

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

package edu.pitt.apollo.outputtranslator.controller;

import com.thoughtworks.xstream.XStream;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import edu.pitt.isg.objectserializer.exceptions.SerializationException;
import edu.pitt.isg.objectserializer.exceptions.UnsupportedSerializationFormatException;
import edu.pitt.apollo.outputtranslator.exception.UnsupportedRunActionException;
import edu.pitt.apollo.outputtranslator.types.rest.CollectionResource;
import edu.pitt.apollo.outputtranslator.types.rest.CollectionResponse;
import edu.pitt.apollo.outputtranslator.types.rest.Meta;
import edu.pitt.apollo.services_common.v4_0_1.RunActionEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.xml.ws.Response;
import java.math.BigInteger;
import java.util.ArrayList;


/**
 * Created by mas400 on 2/5/16.
 */

@Controller
@RequestMapping("/ws")
public class OutputTranslatorController {

    @RequestMapping(value = "/output/{runID}", method = RequestMethod.POST)
    public
    @ResponseBody
    String getOutput(@PathVariable BigInteger runID, @QueryParam("url") String url) {
        String result = "Url = " + url + ", run ID = " + runID;
        CollectionResponse collectionResponse = new CollectionResponse();
        Meta meta = new Meta();
        collectionResponse.setMeta(meta);
        collectionResponse.getMeta().setStatusMessage(result);
        collectionResponse.getMeta().setNumberOfReturnedResults(0);
        collectionResponse.setcollectionItems(new ArrayList<CollectionResource>());

        XStream xstream = new XStream();
        String collectionXML = xstream.toXML(collectionResponse);
        return collectionXML;
    }
}

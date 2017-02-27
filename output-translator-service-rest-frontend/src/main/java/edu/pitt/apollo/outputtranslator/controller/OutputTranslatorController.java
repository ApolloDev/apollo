package edu.pitt.apollo.outputtranslator.controller;

import com.thoughtworks.xstream.XStream;
import edu.pitt.apollo.outputtranslator.types.rest.CollectionResource;
import edu.pitt.apollo.outputtranslator.types.rest.CollectionResponse;
import edu.pitt.apollo.outputtranslator.types.rest.Meta;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.QueryParam;
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

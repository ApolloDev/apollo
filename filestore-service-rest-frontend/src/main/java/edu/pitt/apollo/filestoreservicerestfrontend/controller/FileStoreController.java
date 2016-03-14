package edu.pitt.apollo.filestoreservicerestfrontend.controller;

import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.interfaces.FilestoreServiceInterface;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by mas400 on 3/9/16.
 */
@Controller
@RequestMapping("/files")
public class FileStoreController {
    static Logger logger = LoggerFactory.getLogger(FileStoreController.class);

    @RequestMapping(value = "/{runId}", method = RequestMethod.POST, headers = "Accept=text/html")
    public
    @ResponseBody
    void uploadFile(@PathVariable("runId") BigInteger runId, @RequestParam String urlToFile,
                    @RequestParam FileIdentification fileIdentification) throws Exception {


        FilestoreServiceInterface filestoreService = new FileStoreService();
        filestoreService.uploadFile(runId, urlToFile, fileIdentification);
    }


    @RequestMapping(value = "/{runId}/url", method = RequestMethod.GET, headers = "Accept=text/html")
    public String getUrlOfFile(@PathVariable ("runId") BigInteger runId, @RequestParam(required = false) String filename, @RequestParam(required = false) ContentDataFormatEnum fileFormat,
                               @RequestParam(required = false) ContentDataTypeEnum fileType) throws Exception {

        FilestoreServiceInterface filestoreService = new FileStoreService();

        //used for testing
//        FileIdentification fileIdentification = new FileIdentification();
//        fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
//        fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
//        fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");
//        String urlOfFile = filestoreService.getUrlOfFile(runId, fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType());
//        logger.info("File URL: " + urlOfFile);

        String urlOfFile = filestoreService.getUrlOfFile(runId, filename, fileFormat, fileType);
        return urlOfFile;
    }

    @RequestMapping(value ="/{runId}/status", method = RequestMethod.GET, headers = "Accept=text/html")
    public String getStatusOfFileUpload(@PathVariable("runId") BigInteger runId, @RequestParam(required = false) String filename, @RequestParam(required = false) ContentDataFormatEnum fileFormat,
                                        @RequestParam(required = false) ContentDataTypeEnum fileType) throws IOException {
        FilestoreServiceInterface filestoreService = new FileStoreService();

//        //used for testing
//        FileIdentification fileIdentification = new FileIdentification();
//        fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
//        fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
//        fileIdentification.setLabel("output.allegheny.county.age.race.gender.location");
//        String status = filestoreService.getStatusOfFileUpload(runId, fileIdentification.getLabel(), fileIdentification.getFormat(), fileIdentification.getType());
//        logger.info("File status: "+ status);

        String status = filestoreService.getStatusOfFileUpload(runId, filename, fileFormat, fileType);

        return status;
    }

    @RequestMapping(value ="/{runId}", method = RequestMethod.GET, headers = "Accept=text/html")
    public List<FileIdentification> listFilesForRun(@PathVariable("runId") BigInteger runId) throws IOException {
        FilestoreServiceInterface filestoreService = new FileStoreService();
        List<FileIdentification> files = filestoreService.listFilesForRun(runId);

        //used for testing
//        for(FileIdentification file : files) {
//            logger.info("File: " + file.getLabel());
//        }

        return files;
    }
}

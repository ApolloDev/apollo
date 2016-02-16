package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by jdl50 on 2/9/16.
 */
public interface FilestoreServiceInterface {

    public void uploadFile(BigInteger runId, String urlToFile,
                        FileIdentification fileIdentification) throws Exception;

    public String getUrlOfFile(BigInteger runId, String filename, ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType) throws IOException;


    public String getStatusOfFileUpload(BigInteger runId, String filename, ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType) throws IOException;


    public List<FileIdentification> listFilesForRun(BigInteger runId) throws IOException;
}

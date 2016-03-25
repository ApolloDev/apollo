package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 2/9/16.
 */
public interface FilestoreServiceInterface {

    public void uploadFile(BigInteger runId, String urlToFile, String filename,
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException;

    public String getUrlOfFile(BigInteger runId, String filename, ContentDataFormatEnum fileFormat, 
			ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException;


    public MethodCallStatus getStatusOfFileUpload(BigInteger runId, String filename, 
			ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException;


    public List<FileIdentification> listFilesForRun(BigInteger runId, Authentication authentication) throws FilestoreException;
}

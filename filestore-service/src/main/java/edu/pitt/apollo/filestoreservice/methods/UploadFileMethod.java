package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0_1.FileIdentification;
import edu.pitt.apollo.filestoreservice.threads.UploadFileThread;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.URL;

/**
 * Created by jdl50 on 2/10/16.
 */
//TODO: class needs to be synchronized
public class UploadFileMethod extends FileStoreCoreMethod {

    static final Logger logger = LoggerFactory.getLogger(UploadFileMethod.class);

    public UploadFileMethod(String rootDirectory, String webRoot, BigInteger runId, String salt, Authentication authentication) {
        super(rootDirectory, webRoot, runId, salt, authentication);
    }

    public URL uploadFile(URL urlToFile, String filename, ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType) throws FilestoreException {
        try {
			FileIdentification fileIdentification = new FileIdentification();
			fileIdentification.setLabel(filename);
			fileIdentification.setFormat(fileFormat);
			fileIdentification.setType(fileType);
			
            UploadFileThread uploadFileThread = new UploadFileThread(rootDirectory, webRoot, runId, salt, urlToFile, fileIdentification, authentication);
            URL publicUrlofFile = uploadFileThread.getPublicUrlOfFile();
            new Thread(uploadFileThread).start();
            return publicUrlofFile;
        } catch (Exception ex) {
            throw new FilestoreException("Exception: " + ex.getMessage());
        }
    }

}

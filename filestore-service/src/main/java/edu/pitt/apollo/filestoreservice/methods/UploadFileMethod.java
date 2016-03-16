package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.filestoreservice.threads.FileDownloadMonitor;
import edu.pitt.apollo.filestoreservice.threads.FileDownloadThread;
import edu.pitt.apollo.filestoreservice.threads.UploadFileThread;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFile;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
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

    public URL uploadFile(URL urlToFile, FileIdentification fileIdentification) throws FilestoreException {
        try {
            UploadFileThread uploadFileThread = new UploadFileThread(rootDirectory, webRoot, runId, salt, urlToFile, fileIdentification, authentication);
            URL publicUrlofFile = uploadFileThread.getPublicUrlOfFile();
            new Thread(uploadFileThread).start();
            return publicUrlofFile;
        } catch (Exception ex) {
            throw new FilestoreException("Exception: " + ex.getMessage());
        }
    }

}

package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.filestoreservice.types.DirectoryContentFile;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

/**
 * Created by jdl50 on 2/10/16.
 */
public class GetUrlOfFileMethod extends FileStoreCoreMethod {

    public GetUrlOfFileMethod(String rootDirectory, String webRoot, BigInteger runId, String salt, Authentication authentication) {
        super(rootDirectory, webRoot, runId, salt, authentication);

    }

    public URL getUrlOfFile(String filename, ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType) throws IOException {
        DirectoryContentFile directoryContentFile = readDirectoryContentFile();
        for (DirectoryContentFileEntry directoryContentFileEntry : directoryContentFile.getFiles()) {
            if (directoryContentFileEntry.getFileIdentification().getFormat() == fileFormat &&
                    directoryContentFileEntry.getFileIdentification().getType() == fileType &&
                    directoryContentFileEntry.getFileIdentification().getLabel().equals(filename)) {

                return getWebserverUrl(directoryContentFileEntry);
            }
        }
        return null;
    }
}

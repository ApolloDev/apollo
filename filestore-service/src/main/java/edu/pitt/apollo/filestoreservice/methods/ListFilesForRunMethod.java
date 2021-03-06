package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 2/10/16.
 */
public class ListFilesForRunMethod extends FileStoreCoreMethod {

    public ListFilesForRunMethod(String rootDirectory, String webRoot, BigInteger runId, String salt, Authentication authentication) {
        super(rootDirectory, webRoot, runId, salt, authentication);
    }

    public List<DirectoryContentFileEntry> listFilesForRun() throws IOException {
        return readDirectoryContentFile().getFiles();
    }
}

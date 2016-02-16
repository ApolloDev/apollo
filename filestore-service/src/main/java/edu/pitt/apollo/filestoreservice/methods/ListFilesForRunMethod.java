package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.filestoreservice.types.DirectoryContentFile;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 2/10/16.
 */
public class ListFilesForRunMethod extends FileStoreCoreMethod {

    public ListFilesForRunMethod(String rootDirectory, String webRoot, BigInteger runId, String salt) {
        super(rootDirectory, webRoot, runId, salt);
    }

    public List<DirectoryContentFileEntry> listFilesForRun() throws IOException {
        return readDirectoryContentFile().getFiles();
    }
}

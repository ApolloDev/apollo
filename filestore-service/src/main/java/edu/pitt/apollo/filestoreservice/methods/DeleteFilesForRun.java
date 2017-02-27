package edu.pitt.apollo.filestoreservice.methods;

import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import org.apache.cxf.helpers.FileUtils;

import java.io.File;
import java.math.BigInteger;

/**
 * Created by jdl50 on 2/17/16.
 */
public class DeleteFilesForRun extends FileStoreCoreMethod {
    public DeleteFilesForRun(String rootDirectory, String webRoot, BigInteger runId, String salt, Authentication authentication) {
        super(rootDirectory, webRoot, runId, salt, authentication);
    }

    public void delete() {
        File f = getDirectoryContentFile();
        if (f.getParentFile().exists())
            FileUtils.removeDir(f.getParentFile());

    }
}

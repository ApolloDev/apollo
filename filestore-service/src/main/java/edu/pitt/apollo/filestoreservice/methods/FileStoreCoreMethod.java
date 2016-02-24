package edu.pitt.apollo.filestoreservice.methods;

import com.google.gson.Gson;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservice.FileStoreService;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFile;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * Created by jdl50 on 2/15/16.
 */
public abstract class FileStoreCoreMethod {
    public static int DIRECTORY_CONTENT_FILE_DOES_NOT_EXIST = -1;
    public static int FILE_DOES_NOT_EXIST = -2;
    String rootDirectory;
    String webRoot;
    String salt = "";
    BigInteger runId;
    private Gson gson = new Gson();

    public FileStoreCoreMethod(String rootDirectory, String webRoot, BigInteger runId, String salt) {
        this.salt = salt;
        this.runId = runId;
        this.rootDirectory = rootDirectory;
        this.webRoot = webRoot;
    }

    private String getRunHash() {
        String hash = DigestUtils.md5Hex((salt + runId.toString()).getBytes());
        return hash;
    }

    protected File getDirectoryContentFile() {
        rootDirectory = FilenameUtils.getFullPath(rootDirectory);
        return new File(rootDirectory + File.separator + getRunHash() + File.separator + FileStoreService.DIRECTORY_CONTENT_FILENAME);
    }

    protected int checkIfFileExists(FileIdentification fileIdentification) throws IOException {
        DirectoryContentFile directoryContentFile = readDirectoryContentFile();
        if (directoryContentFile != null) {
            if (directoryContentFile.getFiles() != null) {
                for (DirectoryContentFileEntry entry : directoryContentFile.getFiles()) {
                    if (entry.getFileIdentification().getFormat() == fileIdentification.getFormat() &&
                            entry.getFileIdentification().getType() == fileIdentification.getType() &&
                            entry.getFileIdentification().getLabel().equals(fileIdentification.getLabel())) {
                        return entry.getUniqueFileNumber();
                    }
                }
            }
            return FILE_DOES_NOT_EXIST;
        }
        return DIRECTORY_CONTENT_FILE_DOES_NOT_EXIST;
    }

    protected DirectoryContentFile readDirectoryContentFile() throws IOException {
        File directoryContentFile = getDirectoryContentFile();
        if (directoryContentFile.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(directoryContentFile));
            DirectoryContentFile directoryContentFileContents = gson.fromJson(br, DirectoryContentFile.class);
            return directoryContentFileContents;
        } else {
            return null;
        }
    }

    protected void writeDirectoryContentFile(DirectoryContentFile directoryContentFileContents) throws IOException {
        File directoryContentFile = getDirectoryContentFile();
        new File(directoryContentFile.getParent()).mkdirs();
        BufferedWriter bw = new BufferedWriter(new FileWriter(getDirectoryContentFile()));
        bw.write(gson.toJson(directoryContentFileContents));
        bw.close();
    }

    protected File getLocalFile(DirectoryContentFileEntry directoryContentFileEntry) {
        String filename = rootDirectory + File.separator + getRunHash() + File.separator +
                directoryContentFileEntry.getUniqueFileNumber() + File.separator +
                directoryContentFileEntry.getFileIdentification().getLabel();
        return new File(filename);
    }

    protected File getLocalFileTemporaryName(DirectoryContentFileEntry directoryContentFileEntry) {
        String filename = rootDirectory + File.separator + getRunHash() + File.separator +
                directoryContentFileEntry.getUniqueFileNumber() + File.separator +
                directoryContentFileEntry.getFileIdentification().getLabel() + ".downloading";
        return new File(filename);
    }

    protected URL getWebserverUrl(DirectoryContentFileEntry directoryContentFileEntry) throws MalformedURLException {
        String filename = webRoot + File.separator + getRunHash() + File.separator +
                directoryContentFileEntry.getUniqueFileNumber() + File.separator +
                directoryContentFileEntry.getFileIdentification().getLabel();
        return new URL(filename);
    }
}
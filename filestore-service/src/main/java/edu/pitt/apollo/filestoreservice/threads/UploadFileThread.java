package edu.pitt.apollo.filestoreservice.threads;

import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.filestoreservice.methods.FileStoreCoreMethod;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFile;
import edu.pitt.apollo.filestoreservice.types.DirectoryContentFileEntry;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jdl50 on 3/9/16.
 */
public class UploadFileThread extends FileStoreCoreMethod implements Runnable {
    URL urlToFile;
    File temporaryFileDownloadLocation;
    File finalFileDownloadLocation;
    DirectoryContentFileEntry directoryContentFileEntry;


    public UploadFileThread(String rootDirectory, String webRoot, BigInteger runId, String salt, URL urlToFile, FileIdentification fileIdentification) throws Exception {
        super(rootDirectory, webRoot, runId, salt);
        this.urlToFile = urlToFile;
        directoryContentFileEntry = addFileToDirectoryContentFile(fileIdentification);
    }

    public URL getPublicUrlOfFile() throws MalformedURLException {
        return getWebserverUrl(directoryContentFileEntry);
    }

    /*
    File temporaryFileDownloadLocation = getLocalFileTemporaryName(directoryContentFileEntry);
        File finalDownloadFileLocation = getLocalFile(directoryContentFileEntry);
        File fileDownloadLocationDirectory = new File(temporaryFileDownloadLocation.getParent());
        fileDownloadLocationDirectory.mkdirs();
        FileDownloadThread fileDownloadThread = new FileDownloadThread(urlToFile, temporaryFileDownloadLocation, finalDownloadFileLocation);
        fileDownloadThread.start();
        FileDownloadMonitor fileDownloadMonitor = new FileDownloadMonitor(temporaryFileDownloadLocation);
        fileDownloadMonitor.start();
        return getWebserverUrl(directoryContentFileEntry);
     */
    private DirectoryContentFileEntry addFileToDirectoryContentFile(FileIdentification fileIdentification) throws Exception {
        Integer fileExistResult = checkIfFileExists(fileIdentification);
        if (fileExistResult == DIRECTORY_CONTENT_FILE_DOES_NOT_EXIST) {
            writeDirectoryContentFile(new DirectoryContentFile());
        }

        fileExistResult = checkIfFileExists(fileIdentification);
        if (fileExistResult == FILE_DOES_NOT_EXIST) {

            DirectoryContentFile directoryContentFile = readDirectoryContentFile();
            int maxFileNumber = 0;
            if (directoryContentFile.getFiles() != null) {
                for (DirectoryContentFileEntry entry : directoryContentFile.getFiles()) {
                    int fileNumber = entry.getUniqueFileNumber();
                    maxFileNumber = Math.max(maxFileNumber, fileNumber);
                }
            }
            int newUniqueFileNumber = maxFileNumber + 1;
            DirectoryContentFileEntry newEntry = new DirectoryContentFileEntry(newUniqueFileNumber, fileIdentification);
            directoryContentFile.getFiles().add(newEntry);
            writeDirectoryContentFile(directoryContentFile);
            return newEntry;
        } else {
            throw new Exception("File already exists!");
        }

    }

    private void removeFileFromDirectoryContentFile(FileIdentification fileIdentification) throws IOException {
        Integer fileExistResult = checkIfFileExists(fileIdentification);
        if (fileExistResult >= 0) {
            DirectoryContentFile directoryContentFile = readDirectoryContentFile();
            directoryContentFile.getFiles().remove(fileExistResult);
            writeDirectoryContentFile(directoryContentFile);
        } else {
            logger.warn("Tried to remove file: " + fileIdentification.getLabel() + " but file does not exist!");
        }

    }

    private URL downloadFile(DirectoryContentFileEntry directoryContentFileEntry, URL urlToFile) throws IOException {

        File finalDownloadFileLocation = getLocalFile(directoryContentFileEntry);
        File fileDownloadLocationDirectory = new File(temporaryFileDownloadLocation.getParent());
        fileDownloadLocationDirectory.mkdirs();
        FileDownloadThread fileDownloadThread = new FileDownloadThread(urlToFile, temporaryFileDownloadLocation, finalDownloadFileLocation);
        fileDownloadThread.start();
        FileDownloadMonitor fileDownloadMonitor = new FileDownloadMonitor(temporaryFileDownloadLocation);
        fileDownloadMonitor.start();
        return getWebserverUrl(directoryContentFileEntry);
    }


    public void run() {
        try {
            FileUtils.touch(temporaryFileDownloadLocation);
            FileUtils.copyURLToFile(urlToFile, temporaryFileDownloadLocation);
            FileUtils.copyFile(temporaryFileDownloadLocation, finalFileDownloadLocation);
            FileUtils.forceDelete(temporaryFileDownloadLocation);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

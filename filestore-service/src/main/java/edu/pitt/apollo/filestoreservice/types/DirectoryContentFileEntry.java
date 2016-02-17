package edu.pitt.apollo.filestoreservice.types;

import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;

/**
 * Created by jdl50 on 2/15/16.
 */
public class DirectoryContentFileEntry {
    int uniqueFileNumber;

    public DirectoryContentFileEntry(int uniqueFileNumber, FileIdentification fileIdentification) {
        this.uniqueFileNumber = uniqueFileNumber;
        this.fileIdentification = fileIdentification;
    }

    public FileIdentification getFileIdentification() {
        return fileIdentification;
    }

    public int getUniqueFileNumber() {
        return uniqueFileNumber;
    }

    FileIdentification fileIdentification;
}

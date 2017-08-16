package edu.pitt.apollo.filestoreservice.types;

import edu.pitt.apollo.filestore_service_types.v4_0_2.FileIdentification;

import java.util.Comparator;

/**
 * Created by jdl50 on 2/15/16.
 */
public class DirectoryContentFileEntry implements Comparator<DirectoryContentFileEntry> {
    int uniqueFileNumber;
    FileIdentification fileIdentification;


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

    @Override
    public int compare(DirectoryContentFileEntry o1, DirectoryContentFileEntry o2) {
        if (o1.getUniqueFileNumber() == o2.getUniqueFileNumber())
            return 0;
        else return -1;
    }
}

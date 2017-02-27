package edu.pitt.apollo.filestoreservice.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdl50 on 2/15/16.
 */
public class DirectoryContentFile {
    List<DirectoryContentFileEntry> files;

    public DirectoryContentFile() {
        files = new ArrayList<>();
    }

    public List<DirectoryContentFileEntry> getFiles() {
        return files;
    }
}

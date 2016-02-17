package edu.pitt.apollo.filestoreservice.threads;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by jdl50 on 2/17/16.
 */
public class FileDownloadThread extends Thread {

    URL urlToFile;
    File temporaryFileDownloadLocation;
    File finalFileDownloadLocation;

    public FileDownloadThread(URL urlToFile, File temporaryFileDownloadLocation, File finalFileDownloadLocation) {
        this.urlToFile = urlToFile;
        this.temporaryFileDownloadLocation = temporaryFileDownloadLocation;
        this.finalFileDownloadLocation = finalFileDownloadLocation;
    }

    public void run() {
        try {
            FileUtils.touch(temporaryFileDownloadLocation);
            FileUtils.copyURLToFile(urlToFile, temporaryFileDownloadLocation);
            FileUtils.copyFile(temporaryFileDownloadLocation, finalFileDownloadLocation);
            FileUtils.forceDelete(temporaryFileDownloadLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

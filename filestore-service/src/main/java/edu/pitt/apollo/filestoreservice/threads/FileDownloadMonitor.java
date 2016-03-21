package edu.pitt.apollo.filestoreservice.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by jdl50 on 3/9/16.
 */
public class FileDownloadMonitor extends Thread {

    Logger logger = LoggerFactory.getLogger(FileDownloadMonitor.class);
    File file;
    DecimalFormat df = new DecimalFormat("#0.0");

    public FileDownloadMonitor(File file) {
        this.file = file;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);

                if (file.exists()) {

                    double bytes = file.length();
                    double kilobytes = (bytes / 1024);
                    double megabytes = (kilobytes / 1024);
                    logger.debug("Downloaded " + df.format(megabytes) + " megabytes of file " + file.getAbsolutePath() + " so far.");

                } else {
                    logger.debug("File " + file.getAbsolutePath() + " does not exist!");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

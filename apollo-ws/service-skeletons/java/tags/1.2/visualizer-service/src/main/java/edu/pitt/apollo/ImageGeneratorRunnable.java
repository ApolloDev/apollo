package edu.pitt.apollo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 13, 2013
 * Time: 5:27:44 PM
 * Class: ImageGeneratorRunnable
 * IDE: NetBeans 6.9.1
 */
public class ImageGeneratorRunnable implements Runnable {

    public static final String STARTED_FILE = "started.txt";
    public static final String FINISHED_FILE = "finished.txt";
    private ImageGenerator ig = null;

    public void setImageGenerator(ImageGenerator ig) {
        this.ig = ig;
    }

    @Override
    public void run() {
        if (ig != null) {

            String runDirectory = ig.getRunDirectory();
            
            File finishedFile = new File(runDirectory + File.separator + FINISHED_FILE);
            if (finishedFile.exists()) {
                finishedFile.delete(); // delete previous finished file
            }
            
            // create file to show run is started
            File startedFile = new File(runDirectory + File.separator + STARTED_FILE);
            if (!startedFile.exists()) {
                startedFile.getParentFile().mkdirs();
                try {
                    startedFile.createNewFile();
                } catch (IOException ex) {
                    System.err.println("Started file could not be created");
                }
            }

            ig.createTimeSeriesImages();

            // create file to show run is finished
            
            System.out.println("finished file: " + finishedFile.getAbsolutePath() + "   finished file exists: " + finishedFile.exists());
            if (!finishedFile.exists()) {
                finishedFile.getParentFile().mkdirs();
                try {
                    finishedFile.createNewFile();
                } catch (IOException ex) {
                    System.err.println("Finished file could not be created");
                }
            }
        }

    }
}

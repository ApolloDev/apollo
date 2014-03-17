package edu.pitt.apollo.timeseriesvisualizer;

import edu.pitt.apollo.timeseriesvisualizer.ImageGenerator;
import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
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
    public static final String ERROR_FILE = "error.txt";
    private ImageGenerator ig = null;

    public void setImageGenerator(ImageGenerator ig) {
        this.ig = ig;
    }

    private void writeFile(String fileName, String content) {
        String runDirectory = ig.getRunDirectory();
        File file = new File(runDirectory + File.separator + fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.err.println("Started file could not be created");
            }
        }

        try {
            PrintStream ps = new PrintStream(file);
            ps.println(content);
            ps.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File \"" + file.getAbsolutePath() + "\" could not be found: " + ex.getMessage());
        }
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
            writeFile(STARTED_FILE, "");

            try {
                ig.createTimeSeriesImages();
            } catch (Exception ex) {
                writeFile(ERROR_FILE, "Exception running Time-series visualizer: " + ex.getMessage());
                return;
            }

            // create file to show run is finished
            // should only finish if there is no error file
            writeFile(FINISHED_FILE, "");
        }

    }
}

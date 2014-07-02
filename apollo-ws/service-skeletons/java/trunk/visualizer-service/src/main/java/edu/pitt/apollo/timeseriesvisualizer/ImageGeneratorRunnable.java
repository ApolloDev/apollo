package edu.pitt.apollo.timeseriesvisualizer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.utilities.RunUtils;
import edu.pitt.apollo.types.v2_0_2.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_2.SoftwareIdentification;

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

    private ImageGenerator ig = null;
    private BigInteger visualizerRunId;
    private SoftwareIdentification visuzlierId;
    private List<RunIdentificationAndLabel> runIdentificationsAndLabels;

    public ImageGeneratorRunnable(BigInteger virualizerRunId, List<RunIdentificationAndLabel> runIds, SoftwareIdentification visualizerIdentification) {
        this.runIdentificationsAndLabels = runIds;
        this.visuzlierId = visualizerIdentification;
        this.visualizerRunId = virualizerRunId;
    }

    @Override
    public void run() {

        String runDirectory = ImageGenerator.getRunDirectory(visualizerRunId);
        try {
            RunUtils.createRunDir(runDirectory);
        } catch (IOException ex) {
            try {
                RunUtils.setError(runDirectory, "IOException attempting to create run directory for run "
                        + visualizerRunId + ": " + ex.getMessage());
            } catch (IOException ex1) {
                System.err.println("IOException attempting to create run directory for run " + visualizerRunId + ": " + ex1.getMessage());
            }
        }
        try {
            // create file to show run is started
            RunUtils.setStarted(runDirectory);
        } catch (IOException ex) {
            try {
                RunUtils.setError(runDirectory, "IOException attempting to create started file for run "
                        + visualizerRunId + ": " + ex.getMessage());
            } catch (IOException ex1) {
                System.err.println("IOException attempting to create error file for run " + visualizerRunId + ": " + ex1.getMessage());
            }
        }
        try {
            ig = new ImageGenerator(runIdentificationsAndLabels, visuzlierId, visualizerRunId);
        } catch (TimeSeriesVisualizerException ex) {
            try {
                RunUtils.setError(runDirectory, "TimeSeriesVisualizerException creating ImageGenerator: " + ex.getMessage());
            } catch (IOException ex1) {
                System.err.println("IOException attempting to create error file for run " + visualizerRunId + ": " + ex1.getMessage());
            }
            return;
        }


        try {
            ig.createTimeSeriesImages();
        } catch (Exception ex) {
            try {
                RunUtils.setError(runDirectory, "Exception running Time-series visualizer: " + ex.getMessage());
            } catch (IOException ex1) {
                System.err.println("IOException attempting to create error file for run " + visualizerRunId + ": " + ex1.getMessage());
            }
            return;
        }

        try {
            // create file to show run is finished
            // should only finish if there is no error file
            RunUtils.setFinished(runDirectory);
        } catch (IOException ex) {
            try {
                RunUtils.setError(runDirectory, "IOException attempting to create finished file for run "
                        + visualizerRunId + ": " + ex.getMessage());
            } catch (IOException ex1) {
                System.err.println("IOException attempting to finished error file for run " + visualizerRunId + ": " + ex1.getMessage());
            }
        }


    }
}

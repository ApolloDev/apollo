package edu.pitt.apollo.timeseriesvisualizer;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunIdentificationAndLabel;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.utilities.DatabaseUtility;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 13, 2013 Time:
 * 5:27:44 PM Class: ImageGeneratorRunnable IDE: NetBeans 6.9.1
 */
public class ImageGeneratorRunnable implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ImageGeneratorRunnable.class);

	private ImageGenerator ig = null;
	private BigInteger visualizerRunId;
	private SoftwareIdentification visuzlierId;
	private List<RunIdentificationAndLabel> runIdentificationsAndLabels;
	private SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

	public ImageGeneratorRunnable(BigInteger virualizerRunId, List<RunIdentificationAndLabel> runIds,
			SoftwareIdentification visualizerIdentification) {
		this.runIdentificationsAndLabels = runIds;
		this.visuzlierId = visualizerIdentification;
		this.visualizerRunId = virualizerRunId;
	}

	
	@Override
	public void run() {
		ApolloDbUtils dbUtils = DatabaseUtility.getDbUtils();

		try {
			dbUtils.updateStatusOfRun(visualizerRunId, MethodCallStatusEnum.RUNNING,
					"Started at " + sdf.format(new Date(System.currentTimeMillis())));
		} catch (ApolloDatabaseException e) {
			logger.error("Error setting the status of {} run {} to RUNNING.  Error message was {}.", ImageGenerator.SOFTWARE_NAME,
					visualizerRunId, e.getMessage());
			logger.debug("Stack trace:" + e.getStackTrace().toString());
		}

		try {
			ig = new ImageGenerator(runIdentificationsAndLabels, visuzlierId, visualizerRunId);
		} catch (TimeSeriesVisualizerException e) {
			try {
				dbUtils.updateStatusOfRun(visualizerRunId, MethodCallStatusEnum.FAILED,
						"TimeSeriesVisualizerException creating ImageGenerator: " + e.getMessage());
			} catch (ApolloDatabaseException e1) {
				logger.error("Error setting the status of {} run {} to FAILED.  The error message was {}.",
						ImageGenerator.SOFTWARE_NAME, visualizerRunId, e1.getMessage());
				logger.debug("Stack trace:" + e1.getStackTrace().toString());
			}
			return;
		}

		try {
			ig.createTimeSeriesImages();
		} catch (Exception ex) {
			try {
				dbUtils.updateStatusOfRun(visualizerRunId, MethodCallStatusEnum.FAILED,
						"Exception running Time-series visualizer: " + ex.getMessage());
			} catch (ApolloDatabaseException e) {
				logger.error("Error setting the status of {} run {} to FAILED. Error message was {}.",
						ImageGenerator.SOFTWARE_NAME, visualizerRunId, e.getMessage());
				logger.debug("Stack trace:" + e.getStackTrace().toString());
			}
			return;
		}

		try {
			dbUtils.updateStatusOfRun(visualizerRunId, MethodCallStatusEnum.COMPLETED,
					"Completed at " + sdf.format(new Date(System.currentTimeMillis())));
		} catch (ApolloDatabaseException e) {
			logger.error("Error setting the status of {} run {} to COMPLETED.  Error message was {}.", ImageGenerator.SOFTWARE_NAME,
					visualizerRunId, e.getMessage());
			logger.debug("Stack trace:" + e.getStackTrace().toString());
		}

	}
}

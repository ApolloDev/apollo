package edu.pitt.apollo.timeseriesvisualizer;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.ApolloServiceThread;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v4_0_2.*;
import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.utilities.RunUtils;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.apollo.visualizer_service_types.v4_0_2.RunVisualizationMessage;
import edu.pitt.isg.objectserializer.JsonUtils;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 13, 2013 Time: 5:27:44 PM Class: ImageGeneratorRunnable IDE: NetBeans 6.9.1
 */
public class ImageGeneratorRunnable extends ApolloServiceThread {

	private static Logger logger = LoggerFactory.getLogger(ImageGeneratorRunnable.class);

	private ImageGenerator ig = null;
	private List<RunIdentificationAndLabel> runIdentificationsAndLabels;
	private SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    private Authentication authentication;

	public ImageGeneratorRunnable(BigInteger virualizerRunId, ApolloServiceQueue queue, Authentication authentication) {
		super(virualizerRunId, queue);
        this.authentication = authentication;
	}

	private void loadRunMessage() throws FilestoreException {

		String url = RunUtils.getUrlOfFile(runId, "run_message.json",
				ContentDataFormatEnum.TEXT, ContentDataTypeEnum.RUN_MESSAGE, authentication);
		try {
			String json = RunUtils.getContent(url);
			JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));
			RunVisualizationMessage message = (RunVisualizationMessage) jsonUtils.getObjectFromJson(json, RunVisualizationMessage.class);
			this.runIdentificationsAndLabels = message.getSimulationRunIds();
		} catch (IOException | JsonUtilsException ex) {
			throw new FilestoreException("Exception getting run message in visualizer service: " + ex.getMessage());
		}
	}

	@Override
	public void runApolloService() {

		try {
			loadRunMessage();
			RunUtils.updateStatus(runId, MethodCallStatusEnum.RUNNING,
					"Started at " + sdf.format(new Date(System.currentTimeMillis())), authentication);
		} catch (RunManagementException | FilestoreException e) {
			logger.error("Error setting the status of {} run {} to RUNNING.  Error message was {}.", ImageGenerator.SOFTWARE_NAME,
					runId, e.getMessage());
			logger.debug("Stack trace:" + e.getStackTrace().toString());
			return;
		}

		try {
			ig = new ImageGenerator(runIdentificationsAndLabels, runId, authentication);
		} catch (TimeSeriesVisualizerException | RunManagementException e) {
			try {
				RunUtils.updateStatus(runId, MethodCallStatusEnum.FAILED,
						"TimeSeriesVisualizerException creating ImageGenerator: " + e.getMessage(), authentication);
			} catch (RunManagementException e1) {
				logger.error("Error setting the status of {} run {} to FAILED.  The error message was {}.",
						ImageGenerator.SOFTWARE_NAME, runId, e1.getMessage());
				logger.debug("Stack trace:" + e1.getStackTrace().toString());
			}
			return;
		}

		try {
			ig.createTimeSeriesImages();
		} catch (Exception ex) {
			try {
				RunUtils.updateStatus(runId, MethodCallStatusEnum.FAILED,
						"Exception running Time-series visualizer: " + ex.getMessage(), authentication);
			} catch (RunManagementException e) {
				logger.error("Error setting the status of {} run {} to FAILED. Error message was {}.",
						ImageGenerator.SOFTWARE_NAME, runId, e.getMessage());
				logger.debug("Stack trace:" + e.getStackTrace().toString());
			}
			return;
		}

		try {
			RunUtils.updateStatus(runId, MethodCallStatusEnum.COMPLETED,
					"Completed at " + sdf.format(new Date(System.currentTimeMillis())), authentication);
		} catch (RunManagementException e) {
			logger.error("Error setting the status of {} run {} to COMPLETED.  Error message was {}.", ImageGenerator.SOFTWARE_NAME,
					runId, e.getMessage());
			logger.debug("Stack trace:" + e.getStackTrace().toString());
		}

	}
}

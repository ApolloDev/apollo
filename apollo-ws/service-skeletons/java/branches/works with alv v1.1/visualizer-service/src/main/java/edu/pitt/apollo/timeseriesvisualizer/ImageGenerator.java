package edu.pitt.apollo.timeseriesvisualizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.types.ChartTypeEnum;
import edu.pitt.apollo.timeseriesvisualizer.types.ChartTypeProperties;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeEnum;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainerList;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeList;
import edu.pitt.apollo.timeseriesvisualizer.utilities.DatabaseUtility;
import edu.pitt.apollo.timeseriesvisualizer.utilities.VisualizerChartUtility;
import edu.pitt.apollo.types.v2_1_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_1_0.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_1_0.SoftwareIdentification;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 12, 2013 Time:
 * 6:03:46 PM Class: ImageGenerator IDE: NetBeans 6.9.1
 */
public class ImageGenerator {

	private static final Logger logger = LoggerFactory.getLogger(ImageGenerator.class);

	public static final String SOFTWARE_NAME = "TimeSeriesVisualizer";
	private static final String VISUALIZER_PROPERTIES_FILENAME = "visualizer.properties";
	private static final String IMAGE_FILES_DIRECTORY_PARAM = "image_files_directory";
	private static final String IMAGE_FILE_TYPE_PARAM = "image_file_type";
	private static final String IMAGE_BASE_URL_PARAM = "image_base_url";
	private static final String IMAGE_FILES_DIRECTORY, IMAGE_FILE_TYPE, IMAGE_BASE_URL;
	private static final String INFECTION_STATES_IMAGE_NAME = "prevalence";
	private static final String INCIDENCE_IMAGE_NAME = "incidence";
	private static final String NEWLY_DECEASED_IMAGE_NAME = "newly_deceased";
	private static final String TREATMENT_COUNTS_IMAGE_NAME = "treatment";
	private static final String DISEASE_STATES_IMAGE_NAME = "disease_states";

	private List<BigInteger> runIds;
	private Map<BigInteger, String> runIdLabelMap;
	private String combinedIncidenceImagePath;
	private final boolean multipleRunsSpecified;
	private final BigInteger visualizerRunId;
//	private final boolean multiVaccChart = false;
	private final DatabaseUtility dbUtil;
	private static final String APOLLO_DIR;

	public ImageGenerator(List<RunIdentificationAndLabel> initialRunIds,
			SoftwareIdentification visualizerSoftwareId, BigInteger visualizerRunId)
			throws TimeSeriesVisualizerException {

		dbUtil = new DatabaseUtility(visualizerSoftwareId);
		setRunIdsAndLabels(initialRunIds); // need to do this first

		this.visualizerRunId = visualizerRunId;
		multipleRunsSpecified = (runIds.size() > 1);
		// get the visualizer ID from the run ID
		String runDirectory = getRunDirectory(visualizerRunId);

		// set file paths and urls
		if (!multipleRunsSpecified) {
		} else {
			combinedIncidenceImagePath = runDirectory + File.separator + INCIDENCE_IMAGE_NAME + "." + IMAGE_FILE_TYPE;
		}
	}

	public final String getRunDirectory(BigInteger visualizerRunId) {
		return IMAGE_FILES_DIRECTORY + File.separator + visualizerRunId;
	}

	private String getImageFileName(String imageFileName) {
		return imageFileName + "." + IMAGE_FILE_TYPE;
	}

	private String getURLForImage(String imageName) {
		if (IMAGE_BASE_URL.charAt(IMAGE_BASE_URL.length() - 1) == '/') {
			return IMAGE_BASE_URL + visualizerRunId + "/" + imageName + "." + IMAGE_FILE_TYPE;
		} else {
			return IMAGE_BASE_URL + "/" + visualizerRunId + "/" + imageName + "." + IMAGE_FILE_TYPE;
		}

	}

	private void setRunIdsAndLabels(List<RunIdentificationAndLabel> initialRunIdsAndLabels)
			throws TimeSeriesVisualizerException {
		runIds = new ArrayList<BigInteger>();
		runIdLabelMap = new HashMap<BigInteger, String>();
		for (RunIdentificationAndLabel runIdAndLabel : initialRunIdsAndLabels) {
			BigInteger runId = runIdAndLabel.getRunIdentification();
			runIds.add(runId);
			runIdLabelMap.put(runId, runIdAndLabel.getRunLabel());
		}
	}

	private ChartTypeProperties getChartTypeProperties(ChartTypeEnum chartType, String title, String name, String url) {

		ChartTypeProperties props = new ChartTypeProperties();
		props.setChartTypeEnum(chartType);
		props.setDirectoryForChart(getRunDirectory(visualizerRunId));
		props.setFileNameForChart(name);
		props.setTitleForChart(title);
		props.setUrlForChart(url);
		return props;
	}

	private TimeSeriesCurveTypeList getTimeSeriesCurveTypeListForMosquito() {
		// this is only for CLARA right now
		TimeSeriesCurveTypeList timeSeriesCurveTypesToUseForMosquito = new TimeSeriesCurveTypeList();
		timeSeriesCurveTypesToUseForMosquito.add(TimeSeriesCurveTypeEnum.SUSCEPTIBLE_MOSQUITO);
		timeSeriesCurveTypesToUseForMosquito.add(TimeSeriesCurveTypeEnum.LATENT_MOSQUITO);
		timeSeriesCurveTypesToUseForMosquito.add(TimeSeriesCurveTypeEnum.INFECTIOUS_MOSQUITO);
		timeSeriesCurveTypesToUseForMosquito.add(TimeSeriesCurveTypeEnum.NEWLY_LATENT_MOSQUITO);

		timeSeriesCurveTypesToUseForMosquito.setChartTypePropertiesForChart(ChartTypeEnum.INFECTION_STATES,
				getChartTypeProperties(ChartTypeEnum.INFECTION_STATES, "Mosquito infection states over time",
						getImageFileName(INFECTION_STATES_IMAGE_NAME + "_mosquito"), getURLForImage(INFECTION_STATES_IMAGE_NAME + "_mosquito")));

		timeSeriesCurveTypesToUseForMosquito.setChartTypePropertiesForChart(ChartTypeEnum.INCIDENCE,
				getChartTypeProperties(ChartTypeEnum.INCIDENCE, "Incidence of newly latent mosquitoes over time",
						getImageFileName(INCIDENCE_IMAGE_NAME + "_mosquito"), getURLForImage(INCIDENCE_IMAGE_NAME + "_mosquito")));

		return timeSeriesCurveTypesToUseForMosquito;
	}

	private TimeSeriesCurveTypeList getTimeSeriesCurveTypeListForHuman(String simulatorName) {
		TimeSeriesCurveTypeList timeSeriesCurveTypesToUseForHuman = new TimeSeriesCurveTypeList();
		if (simulatorName.equals("flute")) {
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.NEWLY_LATENT);
			timeSeriesCurveTypesToUseForHuman.setChartTypePropertiesForChart(ChartTypeEnum.INCIDENCE,
					getChartTypeProperties(ChartTypeEnum.INCIDENCE, "Incidence of newly latent over time",
							getImageFileName(INCIDENCE_IMAGE_NAME), getURLForImage(INCIDENCE_IMAGE_NAME)));
		} else if (simulatorName.equals("anthrax")) {
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.SUSCEPTIBLE);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.LATENT);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.RECOVERED);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.NEWLY_DECEASED);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.PROPHYLACTICS_GIVEN);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.ASYMPTOMATIC);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.SYMPTOMATIC);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.FULMINANT);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.DEAD);

			timeSeriesCurveTypesToUseForHuman.setChartTypePropertiesForChart(ChartTypeEnum.INFECTION_STATES,
					getChartTypeProperties(ChartTypeEnum.INFECTION_STATES, "Infection states over time",
							getImageFileName(INFECTION_STATES_IMAGE_NAME), getURLForImage(INFECTION_STATES_IMAGE_NAME)));

			timeSeriesCurveTypesToUseForHuman.setChartTypePropertiesForChart(ChartTypeEnum.NEWLY_DECEASED,
					getChartTypeProperties(ChartTypeEnum.NEWLY_DECEASED, "Count of newly deceased over time",
							getImageFileName(NEWLY_DECEASED_IMAGE_NAME), getURLForImage(NEWLY_DECEASED_IMAGE_NAME)));

			timeSeriesCurveTypesToUseForHuman.setChartTypePropertiesForChart(ChartTypeEnum.TREATMENT,
					getChartTypeProperties(ChartTypeEnum.TREATMENT, "Prophylactics given over time",
							getImageFileName(TREATMENT_COUNTS_IMAGE_NAME), getURLForImage(TREATMENT_COUNTS_IMAGE_NAME)));

			timeSeriesCurveTypesToUseForHuman.setChartTypePropertiesForChart(ChartTypeEnum.DISEASE_STATES,
					getChartTypeProperties(ChartTypeEnum.DISEASE_STATES, "Disease states over time",
							getImageFileName(DISEASE_STATES_IMAGE_NAME), getURLForImage(DISEASE_STATES_IMAGE_NAME)));

		} else {
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.SUSCEPTIBLE);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.LATENT);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.INFECTIOUS);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.RECOVERED);
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.NEWLY_LATENT);

			String infectionStatesTitle = "Infection states over time";
			String incidenceTitle = "Incidence of newly latent over time";
			if (simulatorName.equals("clara")) {
				infectionStatesTitle = "Human infection states over time";
				incidenceTitle = "Incidence of newly latent humans over time";
			}

			timeSeriesCurveTypesToUseForHuman.setChartTypePropertiesForChart(ChartTypeEnum.INFECTION_STATES,
					getChartTypeProperties(ChartTypeEnum.INFECTION_STATES, infectionStatesTitle,
							getImageFileName(INFECTION_STATES_IMAGE_NAME), getURLForImage(INFECTION_STATES_IMAGE_NAME)));

			timeSeriesCurveTypesToUseForHuman.setChartTypePropertiesForChart(ChartTypeEnum.INCIDENCE,
					getChartTypeProperties(ChartTypeEnum.INCIDENCE, incidenceTitle,
							getImageFileName(INCIDENCE_IMAGE_NAME), getURLForImage(INCIDENCE_IMAGE_NAME)));
		}

		return timeSeriesCurveTypesToUseForHuman;
	}

	public void createTimeSeriesImages() throws TimeSeriesVisualizerException {

		String chartXAxisLabel = "simulation time step (days)"; // this is a hack for now, should change in the future
		Map<String, TimeSeriesCurveTypeList> timeSeriesCurveTypesBySpecies = new HashMap<String, TimeSeriesCurveTypeList>();
		TimeSeriesCurveTypeList timeSeriesCurveTypesToUseForHuman = new TimeSeriesCurveTypeList();

		if (runIds.size() == 1) {
			String simulatorName = dbUtil.getSimulatorSoftwareNameForRun(runIds.get(0)).toLowerCase();
			if (simulatorName.equals("anthrax")) {
				chartXAxisLabel = "simulation time step (hours)";
			}

			timeSeriesCurveTypesToUseForHuman = getTimeSeriesCurveTypeListForHuman(simulatorName);
			timeSeriesCurveTypesToUseForHuman.processAddedCurveTypes();
			timeSeriesCurveTypesBySpecies.put("human", timeSeriesCurveTypesToUseForHuman);

			if (simulatorName.equals("clara")) {
				TimeSeriesCurveTypeList timeSeriesCurveTypeForMosquito = getTimeSeriesCurveTypeListForMosquito();
				timeSeriesCurveTypeForMosquito.processAddedCurveTypes();
				timeSeriesCurveTypesBySpecies.put("mosquito", timeSeriesCurveTypeForMosquito);
			}
		} else {
			timeSeriesCurveTypesToUseForHuman.add(TimeSeriesCurveTypeEnum.NEWLY_LATENT);
			timeSeriesCurveTypesToUseForHuman.processAddedCurveTypes();
			timeSeriesCurveTypesBySpecies.put("human", timeSeriesCurveTypesToUseForHuman);
		}

		boolean createCombinedIncidenceChart = multipleRunsSpecified;
		generateImages(createCombinedIncidenceChart, timeSeriesCurveTypesBySpecies, chartXAxisLabel);
	}

	// private void
	// adjustGlobalEpidemicSimulatorIncidence(TimeSeriesContainerList
	// imageSeriesMap) throws TimeSeriesVisualizerException {
	//
	// // Map<String, double[]> incidenceMap =
	// incidenceContainer.getIncidenceTimeSeriesMap();
	// if (imageSeriesMap.size() > 1) {
	// // only need to adjust if there is more than 1 incidence curve (combined
	// incidence)
	// int targetRunLength = 0;
	// String simulatorKey = null;
	// for (String runId : imageSeriesMap.keySet()) {
	// if (!runId.contains("Global_Epidemic_Simulator")) {
	// targetRunLength = imageSeriesMap.get(runId).getSeriesLength();
	// // all of the non GES simulators should have the same length, so it
	// doesn't matter if this is set multiple times
	// } else {
	// simulatorKey = runId;
	// }
	// }
	//
	// if (simulatorKey != null) {
	//
	// Double[] series =
	// imageSeriesMap.get(simulatorKey).getSeries(TimeSeriesCurveTypeEnum.NEWLY_LATENT);
	// Double[] newSeries = new Double[targetRunLength];
	//
	// // if (series.length < targetRunLength) {
	// // System.arraycopy(series, 0, newSeries, 0, series.length);
	// // // the rest of the newSeries array already contains zeros
	// // } else
	// if (series.length > targetRunLength) {
	// System.arraycopy(series, 0, newSeries, 0, newSeries.length);
	// }
	//
	// imageSeriesMap.get(simulatorKey).setSeries(TimeSeriesCurveTypeEnum.NEWLY_LATENT,
	// newSeries);
	// }
	//
	// }
	// }
	private void generateImages(boolean generateCombinedIncidence, Map<String, TimeSeriesCurveTypeList> timeSeriesCurveTypeListBySpecies,
			String chartXAxisLabel) throws TimeSeriesVisualizerException {

		logger.info("Creating images...");
		Map<String, String> resourceMap = new HashMap<String, String>();
		for (String species : timeSeriesCurveTypeListBySpecies.keySet()) {

			TimeSeriesCurveTypeList timeSeriesCurveTypeListForSpecies = timeSeriesCurveTypeListBySpecies.get(species);

			TimeSeriesContainerList timeSeriesContainerList = dbUtil.retrieveTimeSeriesFromDatabaseFiles(runIds,
					timeSeriesCurveTypeListForSpecies);

			// adjustGlobalEpidemicSimulatorIncidence(imageSeriesMap);
			VisualizerChartUtility chartUtility = new VisualizerChartUtility(timeSeriesContainerList);
			if (timeSeriesCurveTypeListForSpecies.listContainsInfectionStateCurveTypes()) {
				chartUtility.createTimeSeriesChart(timeSeriesCurveTypeListForSpecies.getChartTypePropertiesForChartType(ChartTypeEnum.INFECTION_STATES), chartXAxisLabel,
						TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_INFECTION_STATES_CHART, resourceMap);
			}
			if (timeSeriesCurveTypeListForSpecies.listContainsDiseaseStatesCurveTypes()) {
				chartUtility.createTimeSeriesChart(timeSeriesCurveTypeListForSpecies.getChartTypePropertiesForChartType(ChartTypeEnum.DISEASE_STATES), chartXAxisLabel,
						TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_DISEASE_STATES_CHART, resourceMap);
			}
			if (timeSeriesCurveTypeListForSpecies.listContainsTreatmentCurveTypes()) {
				chartUtility.createTimeSeriesChart(timeSeriesCurveTypeListForSpecies.getChartTypePropertiesForChartType(ChartTypeEnum.TREATMENT), chartXAxisLabel,
						TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_TREATMENT_COUNTS_CHART, resourceMap);
			}
			if (timeSeriesCurveTypeListForSpecies.listContainsNewlyDeceasedCurveTypes()) {
				chartUtility.createTimeSeriesChart(timeSeriesCurveTypeListForSpecies.getChartTypePropertiesForChartType(ChartTypeEnum.NEWLY_DECEASED), chartXAxisLabel,
						TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_NEWLY_DECEASED_CHART, resourceMap);
			}
			if (generateCombinedIncidence) {
				// comnined incidence
				chartUtility.createCombinedIncidenceTimeSeriesChart(combinedIncidenceImagePath, runIdLabelMap);
				resourceMap.put(INCIDENCE_IMAGE_NAME + "." + IMAGE_FILE_TYPE, getURLForImage(INCIDENCE_IMAGE_NAME));
			} else if (timeSeriesCurveTypeListForSpecies.listContainsIncidenceCurveTypes()) {
				chartUtility.createTimeSeriesChart(timeSeriesCurveTypeListForSpecies.getChartTypePropertiesForChartType(ChartTypeEnum.INCIDENCE), chartXAxisLabel,
						TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_INCIDENCE_CHART, resourceMap);
			}
		}

		dbUtil.insertURLsIntoDatabase(resourceMap, visualizerRunId);
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, TimeSeriesVisualizerException {

		SoftwareIdentification visualizerSoftwareId = new SoftwareIdentification();
		visualizerSoftwareId.setSoftwareDeveloper("UPitt");
		visualizerSoftwareId.setSoftwareName("Time Series Visualizer");
		visualizerSoftwareId.setSoftwareVersion("1.0");
		visualizerSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);

		List<RunIdentificationAndLabel> runIdsAndLabels = new ArrayList<RunIdentificationAndLabel>();

		// Map<String, String> runIdSeriesLabels = new HashMap<String,
		// String>();
		// runIdSeriesLabels.put("3", "FluTE");
		// runIdSeriesLabels.put("3", "SEIR");
		RunIdentificationAndLabel runIdAndLabel = new RunIdentificationAndLabel();
		runIdAndLabel.setRunIdentification(new BigInteger("22"));
		runIdAndLabel.setRunLabel("LABEL 1");

		runIdsAndLabels.add(runIdAndLabel);
		runIdAndLabel = new RunIdentificationAndLabel();
		runIdAndLabel.setRunIdentification(new BigInteger("25"));
		runIdAndLabel.setRunLabel("LABEL 2");
		runIdsAndLabels.add(runIdAndLabel);

		ImageGenerator generator = new ImageGenerator(runIdsAndLabels, visualizerSoftwareId, new BigInteger(
				"11"));
		try {
			generator.createTimeSeriesImages();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			APOLLO_DIR = apolloDir;
			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);

			boolean errored = false;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(APOLLO_DIR + VISUALIZER_PROPERTIES_FILENAME);
			} catch (FileNotFoundException e) {
				logger.info("Error initializing Visualizer Service.  Can not find file \""
						+ VISUALIZER_PROPERTIES_FILENAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
				errored = true;
			}

			Properties properties = new Properties();
			try {
				properties.load(fis);
			} catch (IOException e) {
				logger.info("Error initializing Visualizer Service.  Unable to read file \""
						+ VISUALIZER_PROPERTIES_FILENAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
				errored = true;
			}

			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				logger.info("Error initializing Visualizer Service.  Unable to close file \""
						+ VISUALIZER_PROPERTIES_FILENAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
				errored = true;
			}

			IMAGE_FILES_DIRECTORY = errored ? "" : properties.getProperty(IMAGE_FILES_DIRECTORY_PARAM);
			IMAGE_FILE_TYPE = errored ? "" : properties.getProperty(IMAGE_FILE_TYPE_PARAM);
			IMAGE_BASE_URL = errored ? "" : properties.getProperty(IMAGE_BASE_URL_PARAM);
		} else {
			logger.error(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE
					+ " environment variable not found!");
			APOLLO_DIR = "";
			IMAGE_FILES_DIRECTORY = "";
			IMAGE_FILE_TYPE = "";
			IMAGE_BASE_URL = "";
		}
		logger.info(SOFTWARE_NAME + " will use the following IMAGE_FILES_DIRECTORY: " + IMAGE_FILES_DIRECTORY);
		logger.info(SOFTWARE_NAME + " will use the following IMAGE_FILE_TYPE: " + IMAGE_FILE_TYPE);
		logger.info(SOFTWARE_NAME + " will use the following IMAGE_BASE_URL" + IMAGE_BASE_URL);

	}
}

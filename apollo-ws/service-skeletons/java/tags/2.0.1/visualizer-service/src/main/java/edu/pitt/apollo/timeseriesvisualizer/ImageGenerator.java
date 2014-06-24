package edu.pitt.apollo.timeseriesvisualizer;

import java.io.File;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainerList;
import edu.pitt.apollo.timeseriesvisualizer.utilities.DatabaseUtility;
import edu.pitt.apollo.timeseriesvisualizer.utilities.VisualizerChartUtility;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2013
 * Time: 6:03:46 PM
 * Class: ImageGenerator
 * IDE: NetBeans 6.9.1
 */
public class ImageGenerator {

    private static final ResourceBundle rb = ResourceBundle.getBundle("visualizer");
    private static final String IMAGE_FILES_DIRECTORY = rb.getString("image_files_directory");
    private static final String IMAGE_FILE_TYPE = rb.getString("image_file_type");
    private static final String IMAGE_BASE_URL = rb.getString("image_base_url");
    private List<BigInteger> runIds;
    private Map<BigInteger, String> runIdLabelMap;
    //private SoftwareIdentification visualizerSoftwareId;
    private Map<BigInteger, String> diseaseStateImagePathMap;
    private Map<BigInteger, String> incidenceImagePathMap;
//    private Map<String, String> runIdSeriesLabels;
    private String combinedIncidenceImagePath;
//    private String runDirectory;
    private boolean multiSimulatorChart;
    private BigInteger visualizerRunId;
    private boolean multiVaccChart = false;
    private DatabaseUtility dbUtil;

//    public ImageGenerator(List<String> runIds, boolean multiVaccChart, boolean multiSimulatorChart,
//            SoftwareIdentification visualizerSoftwareId) throws NoSuchAlgorithmException {
//        this.runIds = runIds;
//        this.visualizerSoftwareId = visualizerSoftwareId;
//        // get the visualizer ID from the run ID
//        visualizerId = getVisualizerId();
//        runDirectory = IMAGE_FILES_DIRECTORY + File.separator + visualizerId;
//        // set file paths
//        diseaseStateImagePathMap = new HashMap<String, String>();
//        incidenceImagePathMap = new HashMap<String, String>();
//        if (!multiSimulatorChart) {
//            diseaseStateImagePathMap.put(runIds.get(0), runDirectory + File.separator + "disease_states" + "." + IMAGE_FILE_TYPE);
//            if (multiVaccChart) {
//                combinedIncidenceImagePath = runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE;
//            } else {
//                incidenceImagePathMap.put(runIds.get(0), runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE);
//            }
//        } else {
//            combinedIncidenceImagePath = runDirectory + File.separator + "combined_incidence" + "." + IMAGE_FILE_TYPE;
//        }
//
//    }
    public ImageGenerator(List<RunIdentificationAndLabel> initialRunIds, SoftwareIdentification visualizerSoftwareId, BigInteger visualizerRunId) throws TimeSeriesVisualizerException {

        dbUtil = new DatabaseUtility(visualizerSoftwareId);
        setRunIdsAndLabels(initialRunIds); // need to do this first
        //this.visualizerSoftwareId = visualizerSoftwareId;
        this.visualizerRunId = visualizerRunId;
        multiSimulatorChart = (runIds.size() > 1);
        // get the visualizer ID from the run ID
        String runDirectory = getRunDirectory(visualizerRunId);

        diseaseStateImagePathMap = new HashMap<BigInteger, String>();
        incidenceImagePathMap = new HashMap<BigInteger, String>();

        // set file paths and urls
        if (!multiSimulatorChart) {
            // the first runId is either the only runId, or the no vacc model
            diseaseStateImagePathMap.put(runIds.get(0), runDirectory + File.separator + "prevalence" + "." + IMAGE_FILE_TYPE);
            if (multiVaccChart) {
                combinedIncidenceImagePath = runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE;
            } else {
                incidenceImagePathMap.put(runIds.get(0), runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE);
            }
        } else {
            combinedIncidenceImagePath = runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE;
        }

    }

    public static String getRunDirectory(BigInteger visualizerRunId) {
        return IMAGE_FILES_DIRECTORY + File.separator + visualizerRunId;
    }

    private String getURLForImage(String imageName) {
        if (IMAGE_BASE_URL.charAt(IMAGE_BASE_URL.length() - 1) == '/') {
            return IMAGE_BASE_URL + visualizerRunId + "/" + imageName + "." + IMAGE_FILE_TYPE;
        } else {
            return IMAGE_BASE_URL + "/" + visualizerRunId + "/" + imageName + "." + IMAGE_FILE_TYPE;
        }

    }

    private void setRunIdsAndLabels(List<RunIdentificationAndLabel> initialRunIdsAndLabels) throws TimeSeriesVisualizerException {
        runIds = new ArrayList<BigInteger>();
        runIdLabelMap = new HashMap<BigInteger, String>();
        for (RunIdentificationAndLabel runIdAndLabel : initialRunIdsAndLabels) {
            BigInteger runId = runIdAndLabel.getRunIdentification();
            runIds.add(runId);
            runIdLabelMap.put(runId, runIdAndLabel.getRunLabel());
        }
    }

    public void createTimeSeriesImages() throws TimeSeriesVisualizerException {

        // if a file does not exist we want to generate the image, so we use the negation
//        boolean generateDiseaseStates = !checkFile(diseaseStateImagePath);
//        boolean generateIncidence = !checkFile(incidenceImagePath);

        boolean simulatorSupportsDiseaseStates = true;
        if (runIds.size() == 1) {
            String simulatorName = dbUtil.getSimulatorSoftwareNameForRun(runIds.get(0)).toLowerCase();
            if (simulatorName.equals("flute")) {
                simulatorSupportsDiseaseStates = false;
            }
        }

        boolean createCombinedIncidenceChart = multiSimulatorChart | multiVaccChart;
        boolean createDiseaseStatesChart = !multiSimulatorChart && simulatorSupportsDiseaseStates;
        boolean createIncidenceChart = !multiSimulatorChart && !multiVaccChart;

        // for now always generate the first 2 images
        generateImages(createDiseaseStatesChart, createIncidenceChart, createCombinedIncidenceChart);
    }

//    private void adjustGlobalEpidemicSimulatorIncidence(TimeSeriesContainerList imageSeriesMap) throws TimeSeriesVisualizerException {
//
////        Map<String, double[]> incidenceMap = incidenceContainer.getIncidenceTimeSeriesMap();
//        if (imageSeriesMap.size() > 1) {
//            // only need to adjust if there is more than 1 incidence curve (combined incidence)
//            int targetRunLength = 0;
//            String simulatorKey = null;
//            for (String runId : imageSeriesMap.keySet()) {
//                if (!runId.contains("Global_Epidemic_Simulator")) {
//                    targetRunLength = imageSeriesMap.get(runId).getSeriesLength();
//                    // all of the non GES simulators should have the same length, so it doesn't matter if this is set multiple times
//                } else {
//                    simulatorKey = runId;
//                }
//            }
//
//            if (simulatorKey != null) {
//
//                Double[] series = imageSeriesMap.get(simulatorKey).getSeries(InfectionStateEnum.NEWLY_EXPOSED);
//                Double[] newSeries = new Double[targetRunLength];
//
////                if (series.length < targetRunLength) {
////                    System.arraycopy(series, 0, newSeries, 0, series.length);
////                    // the rest of the newSeries array already contains zeros
////                } else 
//                if (series.length > targetRunLength) {
//                    System.arraycopy(series, 0, newSeries, 0, newSeries.length);
//                }
//
//                imageSeriesMap.get(simulatorKey).setSeries(InfectionStateEnum.NEWLY_EXPOSED, newSeries);
//            }
//
//        }
//    }
    private void generateImages(boolean generateDiseaseStates, boolean generateIncidence, boolean generateCombinedIncidence) throws TimeSeriesVisualizerException {

//        TimeSeriesContainerList imageSeriesMap = dbUtil.retrieveTimeSeriesFromDatabaseTimeSeriesTable(generateDiseaseStates, (generateIncidence || generateCombinedIncidence));
        TimeSeriesContainerList imageSeriesMap = dbUtil.retrieveTimeSeriesFromDatabaseFiles(runIds, generateDiseaseStates, (generateIncidence
                || generateCombinedIncidence));

//        adjustGlobalEpidemicSimulatorIncidence(imageSeriesMap);

        VisualizerChartUtility chartUtility = new VisualizerChartUtility();

        System.out.println("Creating images...");
        Map<String, String> resourceMap = new HashMap<String, String>();
        if (generateDiseaseStates) {
            chartUtility.createSeirTimeSeriesChart(imageSeriesMap, diseaseStateImagePathMap);
            resourceMap.put("prevalence." + IMAGE_FILE_TYPE, getURLForImage("prevalence"));
        }
        if (generateIncidence) {
            chartUtility.createIncidenceTimeSeriesChart(imageSeriesMap, incidenceImagePathMap);
            resourceMap.put("incidence." + IMAGE_FILE_TYPE, getURLForImage("incidence"));
        } else if (generateCombinedIncidence) { // can't generate incidence and comnined incidence

            chartUtility.createCombinedIncidenceTimeSeriesChart(imageSeriesMap, combinedIncidenceImagePath, runIdLabelMap);
            resourceMap.put("incidence." + IMAGE_FILE_TYPE, getURLForImage("incidence"));
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

//        Map<String, String> runIdSeriesLabels = new HashMap<String, String>();
//        runIdSeriesLabels.put("3", "FluTE");
//        runIdSeriesLabels.put("3", "SEIR");

        RunIdentificationAndLabel runIdAndLabel = new RunIdentificationAndLabel();
        runIdAndLabel.setRunIdentification(new BigInteger("1"));
        runIdAndLabel.setRunLabel("LABEL 1");
        
        runIdsAndLabels.add(runIdAndLabel);
        runIdAndLabel = new RunIdentificationAndLabel();
        runIdAndLabel.setRunIdentification(new BigInteger("2"));
        runIdAndLabel.setRunLabel("LABEL 2");
        runIdsAndLabels.add(runIdAndLabel);
        
        
        ImageGenerator generator = new ImageGenerator(runIdsAndLabels, visualizerSoftwareId, new BigInteger("25"));
        try {
            generator.createTimeSeriesImages();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

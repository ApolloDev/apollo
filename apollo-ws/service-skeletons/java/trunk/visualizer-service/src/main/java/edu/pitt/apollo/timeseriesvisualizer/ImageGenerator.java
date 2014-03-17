package edu.pitt.apollo.timeseriesvisualizer;

import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.pitt.apollo.timeseriesvisualizer.types.ImageSeriesMap;
import edu.pitt.apollo.types.v2_0.UrlOutputResource;
import edu.pitt.apollo.timeseriesvisualizer.utilities.DatabaseUtility;
import edu.pitt.apollo.timeseriesvisualizer.utilities.VisualizerChartUtility;
import org.apache.commons.codec.digest.DigestUtils;


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
    private List<String> runIds;
    private String visualizerId;
    private Map<String, String> diseaseStateImagePathMap;
    private Map<String, String> incidenceImagePathMap;
    private Map<String, String> runIdSeriesLabels;
    private String combinedIncidenceImagePath;
    private String runDirectory;
    private boolean multiSimulatorChart;
    private boolean multiVaccChart;

    public ImageGenerator(List<String> runIds, boolean multiVaccChart, boolean multiSimulatorChart) throws NoSuchAlgorithmException {
        this.runIds = runIds;
        // get the visualizer ID from the run ID
        visualizerId = getVisualizerId();
        runDirectory = IMAGE_FILES_DIRECTORY + File.separator + visualizerId;
        // set file paths
        diseaseStateImagePathMap = new HashMap<String, String>();
        incidenceImagePathMap = new HashMap<String, String>();
        if (!multiSimulatorChart) {
            diseaseStateImagePathMap.put(runIds.get(0), runDirectory + File.separator + "disease_states" + "." + IMAGE_FILE_TYPE);
            if (multiVaccChart) {
                combinedIncidenceImagePath = runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE;
            } else {
                incidenceImagePathMap.put(runIds.get(0), runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE);
            }
        } else {
            combinedIncidenceImagePath = runDirectory + File.separator + "combined_incidence" + "." + IMAGE_FILE_TYPE;
        }

    }

    public ImageGenerator(List<String> runIds, List<UrlOutputResource> visResourceList, Map<String, String> runIdSeriesLabelMap,
            boolean multiVaccChart, boolean multiSimulatorChart) throws NoSuchAlgorithmException {
        this.runIds = runIds;
        this.multiSimulatorChart = multiSimulatorChart;
        this.multiVaccChart = multiVaccChart;
        this.runIdSeriesLabels = runIdSeriesLabelMap;
        // get the visualizer ID from the run ID
        visualizerId = getVisualizerId();
        runDirectory = IMAGE_FILES_DIRECTORY + File.separator + visualizerId;

        // create file urls
        Map<String, String> diseaseImageUrlMap = new HashMap<String, String>();
        Map<String, String> incidenceImageUrlMap = new HashMap<String, String>();
        diseaseStateImagePathMap = new HashMap<String, String>();
        incidenceImagePathMap = new HashMap<String, String>();

        // set file paths and urls
        if (!multiSimulatorChart) {
            // the first runId is either the only runId, or the no vacc model
            diseaseStateImagePathMap.put(runIds.get(0), runDirectory + File.separator + "disease_states" + "." + IMAGE_FILE_TYPE);
            if (multiVaccChart) {
                combinedIncidenceImagePath = runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE;
            } else {
                incidenceImagePathMap.put(runIds.get(0), runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE);
            }

            diseaseImageUrlMap.put(runIds.get(0), IMAGE_BASE_URL + visualizerId + "/" + "disease_states" + "." + IMAGE_FILE_TYPE);
            incidenceImageUrlMap.put(runIds.get(0), IMAGE_BASE_URL + visualizerId + "/" + "incidence" + "." + IMAGE_FILE_TYPE);

            UrlOutputResource diseaseStatesResource = new UrlOutputResource();
            diseaseStatesResource.setDescription("Disease states");
            diseaseStatesResource.setURL(diseaseImageUrlMap.get(runIds.get(0)));

            UrlOutputResource incidenceResource = new UrlOutputResource();
            incidenceResource.setDescription("Incidence");
            incidenceResource.setURL(incidenceImageUrlMap.get(runIds.get(0)));

            visResourceList.add(diseaseStatesResource);
            visResourceList.add(incidenceResource);
        } else {
            combinedIncidenceImagePath = runDirectory + File.separator + "combined_incidence" + "." + IMAGE_FILE_TYPE;
            String combinedIncidenceImageUrl = IMAGE_BASE_URL + visualizerId + "/" + "combined_incidence" + "." + IMAGE_FILE_TYPE;

            UrlOutputResource combinedIncidenceResource = new UrlOutputResource();
            combinedIncidenceResource.setDescription("Combined incidence");
            combinedIncidenceResource.setURL(combinedIncidenceImageUrl);

            visResourceList.add(combinedIncidenceResource);
        }
    }

    public String getRunDirectory() {
        return runDirectory;
    }

    public void createTimeSeriesImages() throws TimeSeriesVisualizerException {

        // if a file does not exist we want to generate the image, so we use the negation
//        boolean generateDiseaseStates = !checkFile(diseaseStateImagePath);
//        boolean generateIncidence = !checkFile(incidenceImagePath);

        boolean createCombinedIncidenceChart = multiSimulatorChart | multiVaccChart;
        boolean createDiseaseStatesChart = !multiSimulatorChart;
        boolean createIncidenceChart = !multiSimulatorChart && !multiVaccChart;

        // for now always generate the first 2 images
        generateImages(createDiseaseStatesChart, createIncidenceChart, createCombinedIncidenceChart);
    }

    private String getVisualizerId() throws NoSuchAlgorithmException {

        String md5 = null;

        if (runIds == null) {
            return null;
        }

        // create new ID string that is the concatenation of the runIDs
        StringBuilder stBuild = new StringBuilder();
        for (String runId : runIds) {
            stBuild.append(runId);
        }

        String id = stBuild.toString();
        md5 = DigestUtils.md5Hex(id);
        return md5;
    }

//    private void adjustGlobalEpidemicSimulatorIncidence(ImageSeriesMap imageSeriesMap) throws TimeSeriesVisualizerException {
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

        DatabaseUtility dbUtil = new DatabaseUtility(runIds);
        ImageSeriesMap imageSeriesMap = dbUtil.retrieveTimeSeriesFromDatabase(generateDiseaseStates, (generateIncidence || generateCombinedIncidence));

//        adjustGlobalEpidemicSimulatorIncidence(imageSeriesMap);

        VisualizerChartUtility chartUtility = new VisualizerChartUtility();

        System.out.println("Creating images...");
        if (generateDiseaseStates) {
            chartUtility.createSeirTimeSeriesChart(imageSeriesMap, diseaseStateImagePathMap);
        }
        if (generateIncidence) {
            chartUtility.createIncidenceTimeSeriesChart(imageSeriesMap, incidenceImagePathMap);
        }
        if (generateCombinedIncidence) {
            chartUtility.createCombinedIncidenceTimeSeriesChart(imageSeriesMap, combinedIncidenceImagePath, runIdSeriesLabels);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        List<UrlOutputResource> resource = new ArrayList<UrlOutputResource>();
        List<String> runIds = new ArrayList<String>();
        runIds.add("UPitt,PSC,CMU_FRED_2.0.1_i_1393616088");
//        runIds.add("UPitt,PSC,CMU_FRED_2.0.1_i_1393534706");
//        runIds.add("UPitt,PSC,CMU_FRED_2.0.1_i_1393019653");

        Map<String, String> runIdSeriesLabels = new HashMap<String, String>();
        runIdSeriesLabels.put("UPitt,PSC,CMU_FRED_2.0.1_i_1393616088", "UPitt,PSC,CMU_FRED_2.0.1_i_1393616088");
//        runIdSeriesLabels.put("UPitt,PSC,CMU_FRED_2.0.1_i_1393534706", "UPitt,PSC,CMU_FRED_2.0.1_i_1393534706");

        ImageGenerator generator = new ImageGenerator(runIds, resource, runIdSeriesLabels, false, false);
        try {
            generator.createTimeSeriesImages();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

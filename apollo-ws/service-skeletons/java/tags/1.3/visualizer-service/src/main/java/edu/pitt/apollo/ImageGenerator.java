package edu.pitt.apollo;

import edu.pitt.apollo.container.ImageSeriesContainer;
import edu.pitt.apollo.types._07._03._2013.UrlOutputResource;
import edu.pitt.apollo.utilities.DatabaseUtility;
import edu.pitt.apollo.utilities.VisualizerChartUtility;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void createTimeSeriesImages() {

        // if a file does not exist we want to generate the image, so we use the negation
//        boolean generateDiseaseStates = !checkFile(diseaseStateImagePath);
//        boolean generateIncidence = !checkFile(incidenceImagePath);

        boolean createCombinedIncidenceChart = multiSimulatorChart | multiVaccChart;
        boolean createDiseaseStatesChart = !multiSimulatorChart;
        boolean createIncidenceChart = !multiSimulatorChart && !multiVaccChart;

        // for now always generate the first 2 images
        generateImages(createDiseaseStatesChart, createIncidenceChart, createCombinedIncidenceChart);
    }

    private String getFileId(String id) throws NoSuchAlgorithmException {

        String md5 = null;

        if (id == null) {
            return null;
        }

        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
        //Update input string in message digest
        digest.update(id.getBytes(), 0, id.length());
        //Converts message digest value in base 16 (hex) 
        md5 = new BigInteger(1, digest.digest()).toString(16);

        return md5;
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

        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
        //Update input string in message digest
        digest.update(id.getBytes(), 0, id.length());
        //Converts message digest value in base 16 (hex) 
        md5 = new BigInteger(1, digest.digest()).toString(16);

        return md5;
    }

//    private String getVisualizerIdForRun() throws FileNotFoundException {
//        // return null if the table file doesn't exist or if
//        // the table does not contain the runId
//
//        File tableFile = new File(VISUALIZER_TABLE_FILE);
//        if (!tableFile.exists()) {
//            return null;
//        }
//
//        Scanner scanner = new Scanner(tableFile);
//        while (scanner.hasNextLine()) {
//            String[] line = scanner.nextLine().split("\t");
//            if (line[0].equals(runId)) {
//                return line[1];
//            }
//        }
//
//        return null; // this will be returned if no matching runId was found
//    }
//    private void storeVisualizerId() throws FileNotFoundException, IOException {
//        
//        File tableFile = new File(VISUALIZER_TABLE_FILE);
//        
//        if (!tableFile.exists()) {
//            tableFile.createNewFile();
//        }
//        
//        PrintStream ps = new PrintStream(tableFile);
//        ps.println(runId + "\t" + visualizerId);
//        ps.close();
//    }
    private boolean checkFile(String filePath) {

        File file = new File(filePath);

        return file.exists();
    }

    private void generateImages(boolean generateDiseaseStates, boolean generateIncidence, boolean generateCombinedIncidence) {

        DatabaseUtility dbUtil = new DatabaseUtility(runIds);
        ImageSeriesContainer container = dbUtil.retrieveTimeSeriesFromDatabase(generateDiseaseStates, (generateIncidence || generateCombinedIncidence));
        VisualizerChartUtility chartUtility = new VisualizerChartUtility();

        System.out.println("Creating images...");
        if (generateDiseaseStates) {
            chartUtility.createSeirTimeSeriesChart(container.getSeirSeriesContainer(), diseaseStateImagePathMap);
        }
        if (generateIncidence) {
            chartUtility.createIncidenceTimeSeriesChart(container.getIncidenceSeriesContainer(), incidenceImagePathMap);
        }
        if (generateCombinedIncidence) {
            chartUtility.createCombinedIncidenceTimeSeriesChart(container.getIncidenceSeriesContainer(), combinedIncidenceImagePath, runIdSeriesLabels);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        List<UrlOutputResource> resource = new ArrayList<UrlOutputResource>();
        List<String> runIds = new ArrayList<String>();
//        runIds.add("UPitt,PSC,CMU_FRED_2.0.1_231860");
        runIds.add("Chao-FredHutchinsonCancerCenter_FluTE_1.15_1002");
//        runIds.add("UPitt_SEIR_1.0_258");

        Map<String, String> runIdSeriesLabels = new HashMap<String, String>();
        runIdSeriesLabels.put("Chao-FredHutchinsonCancerCenter_FluTE_1.15_1002", "Chao-FredHutchinsonCancerCenter_FluTE_1.15_1002");
//        runIdSeriesLabels.put("UPitt_SEIR_1.0_258", "UPitt_SEIR_1.0_258_vaccination");

        ImageGenerator generator = new ImageGenerator(runIds, resource, runIdSeriesLabels, false, false);
        try {
            generator.createTimeSeriesImages();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

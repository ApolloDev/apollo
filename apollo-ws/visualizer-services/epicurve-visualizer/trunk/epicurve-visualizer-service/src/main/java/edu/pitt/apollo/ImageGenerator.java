package edu.pitt.apollo;

import edu.pitt.apollo.container.ImageSeriesContainer;
import edu.pitt.apollo.utilities.DatabaseUtility;
import edu.pitt.apollo.utilities.VisualizerChartUtility;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import edu.pitt.apollo.types.VisualizerOutputResource;
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
    private String combinedIncidenceImagePath;
    private String runDirectory;

    public ImageGenerator(List<String> runIds) throws NoSuchAlgorithmException {
        this.runIds = runIds;
        // get the visualizer ID from the run ID
        visualizerId = getVisualizerId();
        runDirectory = IMAGE_FILES_DIRECTORY + File.separator + visualizerId;
        // set file paths
        diseaseStateImagePathMap = new HashMap<String, String>();
        incidenceImagePathMap = new HashMap<String, String>();
        if (runIds.size() == 1) {
            diseaseStateImagePathMap.put(runIds.get(0), runDirectory + File.separator + "disease_states" + "." + IMAGE_FILE_TYPE);
            incidenceImagePathMap.put(runIds.get(0), runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE);
        } else {
            combinedIncidenceImagePath = runDirectory + File.separator + "combined_incidence" + "." + IMAGE_FILE_TYPE;
        }

    }

    public ImageGenerator(List<String> runIds, List<VisualizerOutputResource> visResourceList) throws NoSuchAlgorithmException {
        this.runIds = runIds;
        // get the visualizer ID from the run ID
        visualizerId = getVisualizerId();
        runDirectory = IMAGE_FILES_DIRECTORY + File.separator + visualizerId;

        // create file urls
        Map<String, String> diseaseImageUrlMap = new HashMap<String, String>();
        Map<String, String> incidenceImageUrlMap = new HashMap<String, String>();
        diseaseStateImagePathMap = new HashMap<String, String>();
        incidenceImagePathMap = new HashMap<String, String>();

        // set file paths and urls
        if (runIds.size() == 1) {
            diseaseStateImagePathMap.put(runIds.get(0), runDirectory + File.separator + "disease_states" + "." + IMAGE_FILE_TYPE);
            incidenceImagePathMap.put(runIds.get(0), runDirectory + File.separator + "incidence" + "." + IMAGE_FILE_TYPE);

            diseaseImageUrlMap.put(runIds.get(0), IMAGE_BASE_URL + visualizerId + "/" + "disease_states" + "." + IMAGE_FILE_TYPE);
            incidenceImageUrlMap.put(runIds.get(0), IMAGE_BASE_URL + visualizerId + "/" + "incidence" + "." + IMAGE_FILE_TYPE);

            VisualizerOutputResource diseaseStatesResource = new VisualizerOutputResource();
            diseaseStatesResource.setDescription("Disease states");
            diseaseStatesResource.setURL(diseaseImageUrlMap.get(runIds.get(0)));

            VisualizerOutputResource incidenceResource = new VisualizerOutputResource();
            incidenceResource.setDescription("Incidence");
            incidenceResource.setURL(incidenceImageUrlMap.get(runIds.get(0)));

            visResourceList.add(diseaseStatesResource);
            visResourceList.add(incidenceResource);
        } else {
            combinedIncidenceImagePath = runDirectory + File.separator + "combined_incidence" + "." + IMAGE_FILE_TYPE;
            String combinedIncidenceImageUrl = IMAGE_BASE_URL + visualizerId + "/" + "combined_incidence" + "." + IMAGE_FILE_TYPE;

            VisualizerOutputResource combinedIncidenceResource = new VisualizerOutputResource();
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

        boolean createCombinedIncidenceChart = (runIds.size() > 1);
        boolean createDiseaseStatesChart = (runIds.size() == 1);
        boolean createIncidenceChart = (runIds.size() == 1);

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
            chartUtility.createCombinedIncidenceTimeSeriesChart(container.getIncidenceSeriesContainer(), combinedIncidenceImagePath);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        List<VisualizerOutputResource> resource = new ArrayList<VisualizerOutputResource>();
        List<String> runIds = new ArrayList<String>();
        runIds.add("UPitt,PSC,CMU_FRED_2.0.1_231860");
        runIds.add("upitt_seir_1.0_19");
        runIds.add("upitt_seir_1.0_22");

        ImageGenerator generator = new ImageGenerator(runIds, resource);
        try {
            generator.createTimeSeriesImages();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

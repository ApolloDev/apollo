package edu.pitt.apollo.timeseriesvisualizer.utilities;

import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.types.ImageSeriesMap;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2013
 * Time: 4:33:35 PM
 * Class: DatabaseUtility
 * IDE: NetBeans 6.9.1
 */
public class DatabaseUtility {

    private static final String[] seirTimeSeriesNames = {"susceptible", "exposed", "infectious", "recovered"};
    private Connection connect = null;
    private ResultSet resultSet = null;
    private PreparedStatement statement = null;
    private List<String> runIds;
    static final Properties properties = new Properties();
    private static final String APOLLO_WORKDIR_ENVIRONMENT_VARIABLE = "APOLLO_20_WORK_DIR";

    static {
        InputStream input;
        Map<String, String> env = System.getenv();
        String dir = env.get(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
        String fn = dir
                + "/database.properties";
        try {

            input = new FileInputStream(fn);
            properties.load(input);
            System.out.println("Successfully loaded " + fn + " file.");
        } catch (Exception e) {
            System.out.println("\n\n\nError loading "
                    + fn + " file\n\n\n");
        }

    }

    public DatabaseUtility(List<String> runIds) {
        this.runIds = runIds;
    }

//    private TimeSeriesResultSetProcessor getDiseaseStateMapFromResultSet() throws SQLException {
//
//        TimeSeriesResultSetProcessor container = new TimeSeriesResultSetProcessor();
//        // disease state -> location code -> time step -> pop count
//        Map<String, Map<String, Map<Integer, Double>>> map = new HashMap<String, Map<String, Map<Integer, Double>>>();
//
//        int maxLength = Integer.MIN_VALUE;
//        while (resultSet.next()) {
//            String diseaseState = resultSet.getString("disease_state");
//            String location = resultSet.getString("apollo_location_code");
//            int timeStep = resultSet.getInt("time_step");
//            double populationCount = resultSet.getInt("pop_count");
//
//            if (timeStep > maxLength) {
//                maxLength = timeStep;
//            }
//
//            Map<String, Map<Integer, Double>> locationMap;
//            if (map.containsKey(diseaseState)) {
//                locationMap = map.get(diseaseState);
//            } else {
//                // use a tree map so that the locations are ordered
//                locationMap = new TreeMap<String, Map<Integer, Double>>();
//            }
//
//            Map<Integer, Double> timeStepMap;
//            if (locationMap.containsKey(location)) {
//                timeStepMap = locationMap.get(location);
//            } else {
//                // use tree map so that the time steps are ordered
//                timeStepMap = new TreeMap<Integer, Double>();
//            }
//
//            // store the population count for this time step
//            timeStepMap.put(timeStep, populationCount);
//        }
//
//        container.setMap(map);
//        container.setLength(maxLength);
//        return container;
//    }
    public ImageSeriesMap retrieveTimeSeriesFromDatabase(boolean getDiseaseStatesData, boolean getIncidenceData) throws TimeSeriesVisualizerException {

        ImageSeriesMap container = new ImageSeriesMap();

        String dbClass = properties.getProperty("class");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        StringBuilder timeSeriesQuery = new StringBuilder();
        InputStream queryInput = DatabaseUtility.class.getResourceAsStream("/time_series_query.sql");

        Scanner scanner = new Scanner(queryInput);

        while (scanner.hasNextLine()) {
            timeSeriesQuery.append(" ").append(scanner.nextLine());
        }
        timeSeriesQuery.deleteCharAt(0); // remove first space
        scanner.close();

        try {
            queryInput.close();
        } catch (IOException ex) {
            System.err.println("IO exception closing input stream");
        }

        String query = timeSeriesQuery.toString();
        TimeSeriesResultSetProcessor processor = new TimeSeriesResultSetProcessor();


        for (String runId : runIds) {
            TimeSeriesContainer timeSeriesContainer = new TimeSeriesContainer();
            try {
                Class.forName(dbClass);
                connect = DriverManager.getConnection(url, user, password);

                System.out.println("Retrieving data for run from the database...");
                statement = connect.prepareStatement(query);
                statement.setString(1, runId);
                statement.setString(2, runId);
                resultSet = statement.executeQuery();

                processor.storeTimeSeriesFromResultSet(resultSet);

                if (getDiseaseStatesData && !runId.toLowerCase().contains("flute")) { // can't make a disease states chart for flute
                    timeSeriesContainer = processor.getTimeSeriesForInfectionStates(getDiseaseStatesData, getIncidenceData);
                } else {
                    timeSeriesContainer = processor.getTimeSeriesForInfectionStates(false, getIncidenceData);
                }

                timeSeriesContainer.setRunId(runId);

                container.put(runId, timeSeriesContainer);

                resultSet.close();
            } catch (ClassNotFoundException ex) {
                throw new TimeSeriesVisualizerException("ClassNotFoundException attempting to get data from database: " + ex.getMessage());
            } catch (SQLException ex) {
                throw new TimeSeriesVisualizerException("SQLException attempting to get data from database: " + ex.getMessage());
            }



        }

        return container;

    }

    private void close() throws Exception {

        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (connect != null) {
                connect.close();
            }

            if (statement != null) {
                statement.close();
            }

        } catch (Exception ex) {
            throw ex;
        }

    }
//    public static void main(String[] args) {
//
//        String runId = "UPitt,PSC,CMU_FRED_2.0.1_230616";
//
//        DatabaseUtility dbConnect = new DatabaseUtility(runId);
//        try {
//            dbConnect.retrieveTimeSeriesFromDatabase(true, true);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
}

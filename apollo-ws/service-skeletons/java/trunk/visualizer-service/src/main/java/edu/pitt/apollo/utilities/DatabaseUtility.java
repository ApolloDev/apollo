package edu.pitt.apollo.utilities;

import edu.pitt.apollo.container.ImageSeriesContainer;
import edu.pitt.apollo.container.IncidenceTimeSeriesContainer;
import edu.pitt.apollo.container.SeirTimeSeriesContainer;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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

    private static final ResourceBundle databaseSettings = ResourceBundle.getBundle("database_connect");
    private static final String[] seirTimeSeriesNames = {"susceptible", "exposed", "infectious", "recovered"};
    private Connection connect = null;
    private ResultSet resultSet = null;
    private PreparedStatement statement = null;
    private List<String> runIds;

    public DatabaseUtility(List<String> runIds) {
        this.runIds = runIds;
    }

    public ImageSeriesContainer retrieveTimeSeriesFromDatabase(boolean getDiseaseStatesData, boolean getIncidenceData) {

        ImageSeriesContainer container = new ImageSeriesContainer();
        SeirTimeSeriesContainer seirContainer = new SeirTimeSeriesContainer();
        IncidenceTimeSeriesContainer incidenceContainer = new IncidenceTimeSeriesContainer();

        String dbHost = databaseSettings.getString("db_host");
        String dbUser = databaseSettings.getString("db_user");
        String dbPassword = databaseSettings.getString("db_password");
        String dbDatabase = databaseSettings.getString("db_database");

        // get the run id query
        StringBuilder runIdQuery = new StringBuilder();
        InputStream queryInput = DatabaseUtility.class.getResourceAsStream("/run_id_query.sql");
        // bcd query
        Scanner scanner = new Scanner(queryInput);

        while (scanner.hasNextLine()) {
            runIdQuery.append(" ").append(scanner.nextLine());
        }
        runIdQuery.deleteCharAt(0); // remove first space
        scanner.close();

        try {
            queryInput.close();
        } catch (IOException ex) {
            System.err.println("IO exception closing input stream");
        }

        // get the seir time series query
        StringBuilder timeSeriesQuery = new StringBuilder();
        queryInput = DatabaseUtility.class.getResourceAsStream("/time_series_query.sql");
        // bcd query
        scanner = new Scanner(queryInput);

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

        for (String runId : runIds) {

            List[] timeSeries = new List[4];
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connect = DriverManager.getConnection("jdbc:mysql://" + dbHost + "/" + dbDatabase, dbUser, dbPassword);
                statement = connect.prepareStatement(runIdQuery.toString());
                statement.setString(1, runId);
                resultSet = statement.executeQuery();
                resultSet.next(); // get the first row (there should only be 1)
                int id = resultSet.getInt("id");
                int runLength = 0;

                System.out.println("Retrieving disease state data from database...");
                if (getDiseaseStatesData && !runId.toLowerCase().contains("flute")) { // can't make a disease states chart for flute
                    // get the SEIR data
                    for (int i = 0; i < 4; i++) {
                        statement = connect.prepareStatement(timeSeriesQuery.toString());
                        statement.setString(2, Integer.toString(id));
                        statement.setString(1, seirTimeSeriesNames[i]);
                        resultSet = statement.executeQuery();
                        int numRows = 0;

                        ArrayList<Double> compartmentCount = new ArrayList<Double>();
                        while (resultSet.next()) {
                            compartmentCount.add(resultSet.getDouble("population_count"));
                            numRows++;
                        }

//                    if (runLength > 0 && numRows != runLength) {
//                        // this means that this series has a different length
//                        throw new Exception("Error: " + seirTimeSeriesNames[i] + " series has a different length than the others");
//                    }

                        runLength = numRows; // set the run length
                        timeSeries[i] = compartmentCount;
                    }

                    double[] susceptibleSeries = new double[runLength];
                    double[] exposedSeries = new double[runLength];
                    double[] infectiousSeries = new double[runLength];
                    double[] recoveredSeries = new double[runLength];

                    for (int i = 0; i < runLength; i++) {
                        susceptibleSeries[i] = (Double) timeSeries[0].get(i);
                        exposedSeries[i] = (Double) timeSeries[1].get(i);
                        infectiousSeries[i] = (Double) timeSeries[2].get(i);
                        recoveredSeries[i] = (Double) timeSeries[3].get(i);
                    }


                    seirContainer.setSusceptibleTimeSeries(runId, susceptibleSeries);
                    seirContainer.setExposedTimeSeries(runId, exposedSeries);
                    seirContainer.setInfectiousTimeSeries(runId, infectiousSeries);
                    seirContainer.setRecoveredTimeSeries(runId, recoveredSeries);
                }

                System.out.println("Retrieving incidence data from database");
                if (getIncidenceData) {
                    // get the incidence series
                    statement = connect.prepareStatement(timeSeriesQuery.toString());
                    statement.setString(2, Integer.toString(id));
                    statement.setString(1, "newly exposed");
                    resultSet = statement.executeQuery();

                    ArrayList<Double> compartmentCount = new ArrayList<Double>();
                    int numRows = 0;
                    while (resultSet.next()) {
                        compartmentCount.add(resultSet.getDouble("population_count"));
                        numRows++;
                    }

                    double[] incidenceSeries = new double[numRows];
                    for (int i = 0; i < numRows; i++) {
                        incidenceSeries[i] = (Double) compartmentCount.get(i);
                    }

                    incidenceContainer.setIncidenceTimeSeries(runId, incidenceSeries);
                }

            } catch (ClassNotFoundException ex) {
                System.err.println("Class not found exception: " + ex.getMessage());
            } catch (SQLException ex) {
                System.err.println("SQL exception: " + ex.getMessage());
            }

        }
        
        container.setSeirSeriesContainer(seirContainer);
        container.setIncidenceSeriesContainer(incidenceContainer);

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

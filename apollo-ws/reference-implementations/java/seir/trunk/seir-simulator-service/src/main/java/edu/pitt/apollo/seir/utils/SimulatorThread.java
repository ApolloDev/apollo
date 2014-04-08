package edu.pitt.apollo.seir.utils;

import edu.pitt.apollo.seirepidemicmodeljava.exception.SeirEpidemicModelException;
import edu.pitt.apollo.seirepidemicmodeljava.types.SeirModelOutput;
import edu.pitt.apollo.types.v2_0_1.Location;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import edu.pitt.apollo.SeirSimulatorServiceImpl;
import edu.pitt.apollo.db.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataFormatEnum;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataType;
import edu.pitt.apollo.seirepidemicmodeljava.file.ConfigurationFileLoader;
import edu.pitt.apollo.seirepidemicmodeljava.model.SeirModel;
import edu.pitt.apollo.seirepidemicmodeljava.types.SeirModelInput;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.ArrayUtils;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Mar 21, 2013 Time:
 * 1:22:18 PM Class: SimulatorThread IDE: NetBeans 6.9.1
 */
public class SimulatorThread extends Thread {

    private RunSimulationMessage message;
    private int runId;
    private boolean useFile;
    private boolean useDatabase;
    private ApolloDbUtils dbUtils;
    private SeirSimulatorServiceImpl impl;

    private SoftwareIdentification getVisualizerSoftwareIdentification() {
        SoftwareIdentification softwareId = new SoftwareIdentification();
        softwareId.setSoftwareName("Time Series Visualizer");
        softwareId.setSoftwareDeveloper("UPitt");
        softwareId.setSoftwareVersion("1.0");
        softwareId.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);
        return softwareId;
    }

    private SoftwareIdentification getTranslatorSoftwareIdentification() {
        SoftwareIdentification softwareId = new SoftwareIdentification();
        softwareId.setSoftwareName("Translator");
        softwareId.setSoftwareDeveloper("UPitt");
        softwareId.setSoftwareVersion("1.0");
        softwareId.setSoftwareType(ApolloSoftwareTypeEnum.TRANSLATOR);
        return softwareId;
    }

    public SimulatorThread(RunSimulationMessage message, ApolloDbUtils dbUtils, SeirSimulatorServiceImpl impl,
            int runId, boolean useFile, boolean useDatabase) {
        super();
        this.message = message;
        this.runId = runId;
        this.useFile = useFile;
        this.useDatabase = useDatabase;
        this.dbUtils = dbUtils;
        this.impl = impl;
    }

    @Override
    public void run() {

        try {
            // first get the configuration file from the database
            Map<String, ByteArrayOutputStream> map;
            try {
                System.out.println("runId: " + runId);
                int translatorKey = dbUtils.getSoftwareIdentificationKey(getTranslatorSoftwareIdentification());
                int simulatorKey = dbUtils.getSoftwareIdentificationKey(message.getSimulatorIdentification());
                System.out.println("translator key: " + translatorKey);
                System.out.println("simulatorKey: " + simulatorKey);
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException ex) {
//                    ex.printStackTrace();
//                }
                map = dbUtils.getDataContentForSoftware(runId,
                        translatorKey, simulatorKey);
                // update this
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "ApolloDatabaseKeyNotFoundException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            } catch (ClassNotFoundException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "ClassNotFoundException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            } catch (SQLException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "SQLException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            }

            String configurationFileContent = null;
            System.out.println("map size: " + map.size());
            for (String label : map.keySet()) {
                System.out.println("label: " + label);
                if (label.equals("config.txt")) {
                    configurationFileContent = map.get(label).toString();
                    break;
                }
            }

            if (configurationFileContent == null) {
                RunUtils.setError(impl.getRunDirectory(runId), "No label \"config.txt\" was found in the data content map"
                        + " for run " + runId);
                return;
            }

            SeirModelInput input;
            try {
                input = ConfigurationFileLoader.loadConfigurationfile(configurationFileContent);
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to load configuration file"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            } catch (SeirEpidemicModelException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "SeirEpidemicModelException attempting to load configuration file"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            }
            SeirModelOutput results = SeirModel.runSeirModel(input);

            // store the results
            if (useDatabase) {
                // get location
                Location location = message.getInfectiousDiseaseScenario().getLocation();
                String locationString;
                if (location.getApolloLocationCode() != null) {
                    locationString = location.getApolloLocationCode();
                } else {
                    // else use first location included
                    locationString = location.getLocationDefinition().getLocationsIncluded().get(0);
                }
                storeTimeSeriesTableOutput(results, locationString);
                storeFileOutputToDatabase(results, locationString);
            }
            if (useFile) {
                storeLocalFileOutput(results);
            }


            // create finished file
            try {
                RunUtils.setFinished(impl.getRunDirectory(runId));
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write finished file for run "
                        + runId + ": " + ex.getMessage());
            }
        } catch (IOException ex) {
            System.err.println("IOException attempting to write error file for run " + runId
                    + ": " + ex.getMessage());

        } finally {
            SeirSimulatorServiceImpl.simulatorRunFinished();
            SeirSimulatorServiceImpl.runSimulatorThreads();
        }
    }

    private void addTextDataContentForSeries(String content, String seriesName) throws IOException {
        int dataContentKey;
        try {
            dataContentKey = dbUtils.addTextDataContent(content);
        } catch (ClassNotFoundException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "ClassNotFoundException attempting to add text data "
                    + "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (SQLException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "SQLException attempting to add text data "
                    + "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        }

        int runDataDescriptionId;
        try {
            runDataDescriptionId = dbUtils.getRunDataDescriptionId(DbContentDataFormatEnum.TEXT, seriesName + ".txt",
                    DbContentDataType.SIMULATOR_LOG_FILE, message.getSimulatorIdentification(),
                    getVisualizerSoftwareIdentification());
        } catch (ApolloDatabaseKeyNotFoundException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "ApolloDatabaseKeyNotFoundException attempting to get run data "
                    + "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (ClassNotFoundException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "ClassNotFoundException attempting to get run data "
                    + "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (SQLException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "SQLException attempting to get run data "
                    + "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        }
        try {
            dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);
        } catch (ClassNotFoundException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "ClassNotFoundException attempting to associate "
                    + "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (SQLException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "SQLException attempting to associate "
                    + "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
        } catch (ApolloDatabaseKeyNotFoundException ex) {
            RunUtils.setError(impl.getRunDirectory(runId), "ApolloDatabaseKeyNotFoundException attempting to associate "
                    + "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
        }
    }

    private void storeFileOutputToDatabase(SeirModelOutput seirOutput, String locationString) {

        double[] susceptible = seirOutput.getSusceptible();
        double[] exposed = seirOutput.getExposed();
        double[] infectious = seirOutput.getInfectious();
        double[] recovered = seirOutput.getRecovered();
        double[] antiviralsGiven = seirOutput.getAntiviralsGiven();
        double[] vaccinesGiven = seirOutput.getVaccinesGiven();
        double[] newlyExposed = seirOutput.getNewlyExposed();

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < newlyExposed.length; i++) {
                    String line = "US" + locationString + " " + newlyExposed[i] + " " + i + ":1" + "\n";
                    stream.write(line.getBytes());
                }
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write newly_exposed series "
                        + "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
            }
            addTextDataContentForSeries(stream.toString(), "newly_exposed");

            // next array
            stream = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < susceptible.length; i++) {
                    String line = "US" + locationString + " " + susceptible[i] + " " + i + ":1" + "\n";
                    stream.write(line.getBytes());
                }
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write susceptible series "
                        + "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
            }
            addTextDataContentForSeries(stream.toString(), "susceptible");

            // next array
            stream = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < exposed.length; i++) {
                    String line = "US" + locationString + " " + exposed[i] + " " + i + ":1" + "\n";
                    stream.write(line.getBytes());
                }
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write exposed series "
                        + "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
            }
            addTextDataContentForSeries(stream.toString(), "exposed");

            // next array
            stream = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < infectious.length; i++) {
                    String line = "US" + locationString + " " + infectious[i] + " " + i + ":1" + "\n";
                    stream.write(line.getBytes());
                }
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write infectious series "
                        + "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
            }
            addTextDataContentForSeries(stream.toString(), "infectious");

            // next array
            stream = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < recovered.length; i++) {
                    String line = "US" + locationString + " " + recovered[i] + " " + i + ":1" + "\n";
                    stream.write(line.getBytes());
                }
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write recovered series "
                        + "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
            }
            addTextDataContentForSeries(stream.toString(), "recovered");

            // next array
            stream = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < vaccinesGiven.length; i++) {
                    String line = "US" + locationString + " " + vaccinesGiven[i] + " " + i + ":1" + "\n";
                    stream.write(line.getBytes());
                }
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write vaccines_given series "
                        + "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
            }
            addTextDataContentForSeries(stream.toString(), "vacc_administered");


            // next array
            stream = new ByteArrayOutputStream();
            try {
                for (int i = 0; i < antiviralsGiven.length; i++) {
                    String line = "US" + locationString + " " + antiviralsGiven[i] + " " + i + ":1" + "\n";
                    stream.write(line.getBytes());
                }
            } catch (IOException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "IOException attempting to write antivirals_given series "
                        + "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
            }
            addTextDataContentForSeries(stream.toString(), "av_administered");
        } catch (IOException ex) {
            System.err.println("IOException attempting to write error file for run " + runId + ": " + ex.getMessage()
                    + ". This error occured in the storeFileOutputToDatabase method.");
        }
    }

    private void storeTimeSeriesTableOutput(SeirModelOutput seirOutput, String location) {

        List<Double> sB = Arrays.asList(ArrayUtils.toObject(seirOutput.getSusceptible()));
        List<Double> eB = Arrays.asList(ArrayUtils.toObject(seirOutput.getExposed()));
        List<Double> iB = Arrays.asList(ArrayUtils.toObject(seirOutput.getInfectious()));
        List<Double> rB = Arrays.asList(ArrayUtils.toObject(seirOutput.getRecovered()));
        List<Double> avB = Arrays.asList(ArrayUtils.toObject(seirOutput.getAntiviralsGiven()));
        List<Double> vaccB = Arrays.asList(ArrayUtils.toObject(seirOutput.getVaccinesGiven()));
        List<Double> newE = Arrays.asList(ArrayUtils.toObject(seirOutput.getNewlyExposed()));
        List<Double> newI = Arrays.asList(ArrayUtils.toObject(seirOutput.getNewlyInfectious()));

        PreparedStatement pstmt;
        int expectedNumRows;
        try {
            try {
                int dsAxisId = dbUtils.getAxisId("disease_state");
                if (dsAxisId == -1) {
                    dbUtils.createAxisId("disease_state");
                    dsAxisId = dbUtils.getAxisId("disease_state");
                }

                int locAxisId = dbUtils.getAxisId("location");
                if (locAxisId == -1) {
                    dbUtils.createAxisId("location");
                    locAxisId = dbUtils.getAxisId("location");
                }

                int sPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId,
                        "susceptible", location);
                int ePopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId, "exposed", location);
                int iPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId, "infectious", location);
                int rPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId, "recovered", location);
                int vPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId,
                        "received vaccination treatment", location);
                int avPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId,
                        "received antiviral treatment", location);
                int newEPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId,
                        "newly exposed", location);
                int newIPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId,
                        "newly infectious in", location);

                // synchronized (SeirModelAdapter.class) {
                // dbUtils.getConn().setAutoCommit(false);

                pstmt = dbUtils.getConn().prepareStatement(
                        "INSERT INTO time_series (run_id, population_id, time_step, pop_count) VALUES (?,?,?,?)");

                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, sPopId,
                        "susceptible in " + location, sB);
                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, ePopId,
                        "exposed in " + location, eB);
                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, iPopId,
                        "infectious in " + location, iB);
                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, rPopId,
                        "recovered in " + location, rB);
                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, vPopId,
                        "received vaccination treatment in " + location, vaccB);
                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, avPopId,
                        "received antiviral treatment in " + location, avB);
                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, newEPopId,
                        "newly exposed in " + location, newE);
                dbUtils.insertDiseaseStateTimeSeries(pstmt, runId, newIPopId,
                        "newly infectious in " + location, newI);


                expectedNumRows = sB.size() + eB.size() + iB.size() + rB.size()
                        + vaccB.size() + avB.size() + newE.size() + newI.size();

                if (seirOutput.getVaccinesGivenBeforeEpidemic() != null) {
                    int preVaccPopId = dbUtils.getOrCreatePopulationId(dsAxisId, locAxisId,
                            "received preemptive vaccination treatment", location);
                    List<Double> preVacc = Arrays.asList(ArrayUtils.toObject(seirOutput.getVaccinesGivenBeforeEpidemic()));
                    dbUtils.insertDiseaseStateTimeSeriesNegative(pstmt, runId, preVaccPopId,
                            "received preemptive vaccination treatment in " + location, preVacc);
                    expectedNumRows += preVacc.size();
                }
            } catch (ClassNotFoundException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "ClassNotFoundException attempting to insert time series data for run "
                        + runId + ": " + ex.getMessage());
                return;
            } catch (SQLException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "SQLException attempting to insert time series data for run "
                        + runId + ": " + ex.getMessage());
                return;
            }
            try {
                pstmt.executeBatch();
            } catch (SQLException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "SQLException attempting to execute prepared statement batch for run "
                        + runId + ": " + ex.getMessage());
                return;
            }
            try {
                // we do this so that we never return "COMPLETED" until the data is
                // actually
                // available in the database
                dbUtils.awaitRowCountForTimeSeriesTable(runId, expectedNumRows);
            } catch (SQLException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "SQLException waiting for row count for run "
                        + runId + ": " + ex.getMessage());
                return;
            } catch (ClassNotFoundException ex) {
                RunUtils.setError(impl.getRunDirectory(runId), "ClassNotFoundException waiting for row count for run "
                        + runId + ": " + ex.getMessage());
                return;
            }
        } catch (IOException ex) {
            System.out.println("IOException attempting to write error file for run " + runId
                    + ": " + ex.getMessage() + ". The error occured while attempting to store the time series "
                    + "data in the database for this run.");
        }
    }

    private void storeLocalFileOutput(SeirModelOutput seirOutput) {
        // this output is used by Bioecon runnig the Reed-Frost model in batch mode

        double[] sB = seirOutput.getSusceptible();
        double[] newE = seirOutput.getNewlyExposed();
        double[] vaccB = seirOutput.getVaccinesGiven();
        double[] avB = seirOutput.getAntiviralsGiven();

        // NOTE: currently Bioecon does not support preemptive vaccination,
        // so that series is not currently accounted for in the output file.
        // This should be changed if Bioecon or another client needs it included
        // in this file output.

        File output = new File(impl.getRunDirectory(runId) + File.separator
                + "results.txt"); // use the working directory
        PrintStream ps;
        try {
            ps = new PrintStream(output);
        } catch (FileNotFoundException ex) {
            System.err.println("Error: file " + output.getAbsolutePath()
                    + " could not be found");
            return;
        }

        // print the following

        // 1. tot num sick
        // 2. tot num vacc
        // 3. tot num av
        // 4. incidence peak day
        // 5. peak incidence

        double totalSick = 0.0;
        int totalVaccinated = 0;
        int totalAv = 0;
        int incidencePeakDay = 0;
        double peakIncidence = 0;
        for (int i = 0; i < sB.length; i++) {

            double incidence = newE[i];
            totalSick += incidence;
            totalVaccinated += vaccB[i];
            totalAv += avB[i];

            if (incidence > peakIncidence) {
                peakIncidence = incidence;
                incidencePeakDay = i;
            }
        }

        ps.println("total_sick,total_vacc,total_av,peak_day,peak_day_incidence");
        ps.println(totalSick + "," + totalVaccinated + "," + totalAv + ","
                + incidencePeakDay + "," + peakIncidence);

        ps.close();


    }
}
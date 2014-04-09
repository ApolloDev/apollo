package edu.pitt.apollo.flute.utils;

import edu.pitt.apollo.flute.exception.FluteSimulatorServiceException;
import edu.pitt.apollo.flute.thread.SimulatorThread;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 19, 2014
 * Time: 2:18:13 PM
 * Class: FluteOutputProcessor
 * IDE: NetBeans 6.9.1
 */
public class FluteOutputProcessor {

    private static final Logger LOGGER = Logger.getLogger(SimulatorThread.class.getName());
    private static final String[] AGE_RANGES = {"0-4", "5-18", "19-29", "30-64", "65+"};
    private static final Map<String, String> LOCATION_FILES_MAP;
    private Map<String, List<Integer>> tractTimeSeriesMap;
    private List<Integer> tractSumTimeSeries;
    private String location;
    private int runLength;

    static {
        LOCATION_FILES_MAP = new HashMap<String, String>();
        LOCATION_FILES_MAP.put("06037", "la-tracts.txt");
        LOCATION_FILES_MAP.put("00000", "one-tracts.txt");
    }

    public FluteOutputProcessor(String fluteOutputFileLocation, String location, int runLength)
            throws FileNotFoundException, FluteSimulatorServiceException {

        if (LOCATION_FILES_MAP.get(location) == null) {
            this.location = "00000";
        } else {
            this.location = location;
        }
        this.runLength = runLength;
        processFluteOutputFile(fluteOutputFileLocation);
    }

    public void storeInputFileForGaiaToDatabase() {
        // this will create the GAIA input file and store it to the apollo database
    }

    public void storeFluteTimeSeriesDataToDatabase(String runId, String simConfigHash, String simConfigJson) 
            throws SQLException, ClassNotFoundException {
        // this will store the flute output to the time series table in the apollo database

        int internalRunId = ApolloDatabaseConnection.getInternalRunId(runId, simConfigHash, simConfigJson);
        int dsAxisId = ApolloDatabaseConnection.getAxisId("disease_state");
        if (dsAxisId == -1) {
            ApolloDatabaseConnection.createAxisId("disease_state");
            dsAxisId = ApolloDatabaseConnection.getAxisId("disease_state");
        }

        int locAxisId = ApolloDatabaseConnection.getAxisId("location");
        if (locAxisId == -1) {
            ApolloDatabaseConnection.createAxisId("location");
            locAxisId = ApolloDatabaseConnection.getAxisId("location");
        }

        int ageRangeAxisId = ApolloDatabaseConnection.getAxisId("age_range");
        if (ageRangeAxisId == -1) {
            ApolloDatabaseConnection.createAxisId("age_range");
            ageRangeAxisId = ApolloDatabaseConnection.getAxisId("age_range");
        }

        // for each tract listed in the tracts file
        for (String tract : tractTimeSeriesMap.keySet()) {

            // for storing by age range
//            for (int ageRangeIndex = 0; ageRangeIndex < ageRanges.length; ageRangeIndex++) {
//
//                int tractPopIdForAgeRange = ApolloDatabaseConnection.getOrCreatePopulationId(dsAxisId, locAxisId, ageRangeAxisId,
//                        "newly infectious and symptomatic", popLocation + tract, ageRanges[ageRangeIndex]);
//
//                // see if this tract has a time series
//                Map<String, List<Double>> tractTimeSeriesMapForAgeRange = tractTimeSeriesAgeRangeMaps.get(ageRangeIndex);
//                if (tractTimeSeriesMapForAgeRange.containsKey(tract)) {
//                    List<Double> timeSeriesForTract = tractTimeSeriesMapForAgeRange.get(tract);
//                    ApolloDatabaseConnection.insertTimeSeries(internalRunId, tractPopIdForAgeRange,
//                            "newly infectious and symptomatic in " + popLocation + tract + " age " + ageRanges[ageRangeIndex],
//                            timeSeriesForTract);
//                }
//            }

            // for storing by location only
            int tractPopIdForAgeRange = ApolloDatabaseConnection.getOrCreatePopulationId(dsAxisId, locAxisId,
                    "newly exposed", tract);

            // see if this tract has a time series
            if (tractTimeSeriesMap.containsKey(tract)) {
                List<Integer> timeSeriesForTract = tractTimeSeriesMap.get(tract);
                ApolloDatabaseConnection.insertTimeSeries(internalRunId, tractPopIdForAgeRange,
                        "newly exposed in " + tract,
                        timeSeriesForTract);
            }
        }

        // store the tract-sum dataset
        int popId = ApolloDatabaseConnection.getOrCreatePopulationId(dsAxisId, locAxisId, "newly exposed", location);
        ApolloDatabaseConnection.insertTimeSeries(internalRunId, popId, "newly exposed in " + location, tractSumTimeSeries);
    }

    private void processFluteOutputFile(String fluteOutputFileLocation) throws FluteSimulatorServiceException {


        LOGGER.log(Level.INFO, "Processing FluTE output");
        Map<String, String> tractIdMap = new HashMap<String, String>();
        String populationFile = populationFile = LOCATION_FILES_MAP.get(location);

        Scanner populationFileScanner = new Scanner(SimulatorThread.class.getResourceAsStream("/" + populationFile));
        int tractIdInc = 0;
        // state, county, tract, pop, lat, long
        while (populationFileScanner.hasNextLine()) {
            String[] splitLine = populationFileScanner.nextLine().split(",");
            String tract = splitLine[0] + splitLine[1] + splitLine[2]; // e.g. 06037101110
            tractIdMap.put(Integer.toString(tractIdInc), tract);
            tractIdInc++;
        }

        // load the output data
        File outputFile = new File(fluteOutputFileLocation);
        Scanner scanner;
        try {
            scanner = new Scanner(outputFile);
        } catch (FileNotFoundException ex) {
            throw new FluteSimulatorServiceException("Could not open the FluTE time series output file: " + fluteOutputFileLocation);
        }

//        List<Map<String, List<Double>>> tractTimeSeriesAgeRangeMaps = new ArrayList<Map<String, List<Double>>>();

        // used to store the total count per day (summed over the age ranges)
        tractTimeSeriesMap = new HashMap<String, List<Integer>>();
        // create time-series dataset for the sum of all the tracts
        tractSumTimeSeries = new ArrayList<Integer>(Collections.nCopies(runLength, 0)); // no tract will have a time series longer than runLength

        // time,TractID,sym0-4,sym5-18,sym19-29,sym30-64,sym65+,cumsym0-4,cumsym5-18,cumsym19-29,cumsym30-64,cumsym65+,
        // newInfected0-4,newInfected5-18,newInfected19-29,newInfected30-64,newInfected65+,
        // everInfected0-4,everInfected5-18,everInfected19-29,everInfected30-64,everInfected65+
        scanner.nextLine(); // skip the header line
        while (scanner.hasNextLine()) {
            String[] splitLine = scanner.nextLine().split(",");
            String tractId = splitLine[1]; // this is the tract ID, which needs to be looked up
            String tract = tractIdMap.get(tractId);
            int time = Integer.parseInt(splitLine[0]);
            if (tract == null) {
                throw new FluteSimulatorServiceException("Tract ID " + tractId
                        + " did not have an associated tract code in data file " + populationFile);
            }

            int totalCount = 0;
            for (int ageRangeIndex = 0; ageRangeIndex < AGE_RANGES.length; ageRangeIndex++) {
                // get the newly infected for the age range
                int countForAgeRange = Integer.parseInt(splitLine[ageRangeIndex + 12]);
                totalCount += countForAgeRange;

                // for storing by age range
//                Map<String, List<Double>> tractTimeSeriesMapForAgeRange;
//                if (tractTimeSeriesAgeRangeMaps.size() < ageRangeIndex + 1) { // if the list does not contain this age range
//                    tractTimeSeriesMapForAgeRange = new HashMap<String, List<Double>>();
//                    tractTimeSeriesAgeRangeMaps.add(tractTimeSeriesMapForAgeRange);
//                } else {
//                    tractTimeSeriesMapForAgeRange = tractTimeSeriesAgeRangeMaps.get(ageRangeIndex);
//                }
//
//                // check tract and add count
//                if (tractTimeSeriesMapForAgeRange.containsKey(tract)) {
//                    tractTimeSeriesMapForAgeRange.get(tract).add(countForAgeRange);
//                } else {
//                    List<Double> list = new ArrayList<Double>();
//                    list.add(countForAgeRange);
//                    tractTimeSeriesMapForAgeRange.put(tract, list);
//                }
            }

            // add count for this time step to the tract sum series
            tractSumTimeSeries.set(time, tractSumTimeSeries.get(time) + totalCount);

            if (getTractTimeSeriesMap().containsKey(tract)) {
                tractTimeSeriesMap.get(tract).add(totalCount);
            } else {
                List<Integer> tractList = new ArrayList<Integer>();
                tractList.add(totalCount);
                tractTimeSeriesMap.put(tract, tractList);
            }
        }
    }

    private String createInputFileForGaia() throws FluteSimulatorServiceException {
        // this takes the tract time series and creates a GAIA input file,
        // returning the content as a string

        if (tractTimeSeriesMap == null || tractTimeSeriesMap.isEmpty()) {
            throw new FluteSimulatorServiceException("Cannot create input file for GAIA because "
                    + "the tract time-series map is null or empty");
        }

        String state, county;
        if (location.length() >= 5) { // location is at least a county
            state = location.substring(0, 2);
            county = location.substring(2, 5);
        } else if (location.length() == 2 && !location.toLowerCase().equals("US")) {
            state = location;
            county = "*";
        } else if (location.toLowerCase().equals("us")) {
            state = "*";
            county = "*";
        } else {
            throw new FluteSimulatorServiceException("Location \"" + location + "\" has a string length that is"
                    + " not supported for creating GAIA input files");
        }

        StringBuilder stBuild = new StringBuilder();
        // First print the borders for each time step, e.g.:
        // USFIPS st42.ct003.tr*.bl* -1 1:-1, all tracts, all blocks, style -1
        for (int i = 0; i < runLength; i++) {
            stBuild.append(getGaiaInputFileStringForLocation(state, county, "*", "*", -1, i, -1));
        }

        for (String tract : tractTimeSeriesMap.keySet()) {
            state = tract.substring(0, 2);
            county = tract.substring(2, 5);
            String tractNumber = tract.substring(5, tract.length());

            List<Integer> timeSeries = tractTimeSeriesMap.get(tract);
            for (int i = 0; i < timeSeries.size(); i++) {
                int count = timeSeries.get(i);
                stBuild.append(getGaiaInputFileStringForLocation(state, county, tractNumber, "*", count, i, 1));
            }
        }

        return stBuild.toString();
    }

    private String getGaiaInputFileStringForLocation(String state, String county,
            String tract, String block, int count, int time, int style) {
        // An example is USFIPS st42.ct003.tr4090.bl2 11 3:1
        // This means state 42, county 003, tract 4090, all block 2, count is 11,
        // time step is 3, style is 1
        return "USFIPS st" + state + ".ct" + county + ".tr"
                + tract + ".bl" + block + " " + count + " " + time + ":" + style + "\n";
    }

    /**
     * @return the tractTimeSeriesMap
     */
    public Map<String, List<Integer>> getTractTimeSeriesMap() {
        return tractTimeSeriesMap;
    }

    /**
     * @return the tractSumTimeSeries
     */
    public List<Integer> getTractSumTimeSeries() {
        return tractSumTimeSeries;
    }

    public static void main(String[] args) throws FileNotFoundException, FluteSimulatorServiceException {
        FluteOutputProcessor procesor = new FluteOutputProcessor("C:/cygwin/home/NEM41/flute/Log0", "06037", 10);
        String content = procesor.createInputFileForGaia();

        PrintStream ps = new PrintStream(new File("C:/apollo_test/gaia_input.txt"));
        ps.print(content);
        ps.close();
    }
}

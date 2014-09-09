package edu.pitt.apollo.timeseriesvisualizer.utilities;

import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainer;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeEnum;
import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 27, 2014 Time:
 * 2:29:19 PM Class: DatabaseTimeSeriesProcessor IDE: NetBeans 6.9.1
 */
public class DatabaseTimeSeriesProcessor {
    
    private static final String DEFAULT_LOCATION = "00000";
    private Map<String, Map<String, Map<Integer, Double>>> map;
    private int length; // this is the longest time series length for the run
    private int largestLocationCodeLength; // this is the length of the location
    // code for the location with the
    // largest
    // granularity for the run (note there may be multiple locations with this
    // granularity, e.g. multiple states)
    private Set<String> largestLocations; // this is the set of all locations
    // which have the largest
    // granularity for the run
    private Set<String> allLocations;
    
    public DatabaseTimeSeriesProcessor() {
        length = Integer.MIN_VALUE;
        largestLocations = new HashSet<String>();
        allLocations = new HashSet<String>();
        largestLocationCodeLength = Integer.MAX_VALUE;
        map = new HashMap<String, Map<String, Map<Integer, Double>>>();
    }

    /**
     * @return the map
     */
    public Map<String, Map<String, Map<Integer, Double>>> getMap() {
        return map;
    }
    
    private Double[] getTimeSeriesArrayFromMap(
            Map<Integer, Double> timeSeriesMap) {

        // converts the map into a single double array
        // including time steps that may have been skipped

        Double[] series = new Double[length];
        for (int i = 0; i < length; i++) {
            if (timeSeriesMap.containsKey(i)) {
                series[i] = timeSeriesMap.get(i);
            } else {
                // for time steps which are missing, store zero
                series[i] = 0.0;
            }
        }
        
        return series;
    }
    
    private void setTimeSeriesEntry(String diseaseState, String location,
            int timeStep, double populationCount) {
        
        if (timeStep + 1 > length) {
            length = timeStep + 1; // if time step = 0; the length of the array
            // is 1, etc.
        }
        
        Map<String, Map<Integer, Double>> locationMap;
        if (map.containsKey(diseaseState)) {
            locationMap = map.get(diseaseState);
        } else {
            // use a tree map so that the locations are ordered
            locationMap = new TreeMap<String, Map<Integer, Double>>();
            map.put(diseaseState, locationMap);
        }
        
        if (location.length() < largestLocationCodeLength) {
            largestLocationCodeLength = location.length();
        }
        allLocations.add(location);
        
        Map<Integer, Double> timeStepMap;
        if (locationMap.containsKey(location)) {
            timeStepMap = locationMap.get(location);
        } else {
            // use tree map so that the time steps are ordered
            timeStepMap = new TreeMap<Integer, Double>();
            locationMap.put(location, timeStepMap);
        }

        // store the population count for this time step
        if (timeStepMap.containsKey(timeStep)) {
            // add the population to this time step
            timeStepMap.put(timeStep, populationCount + timeStepMap.get(timeStep));
        } else {
            timeStepMap.put(timeStep, populationCount);
        }
    }
    
    public void storeTimeSeriesFromResultSet(ResultSet resultSet)
            throws SQLException, TimeSeriesVisualizerException {
        
        if (!resultSet.isBeforeFirst()) {
            throw new TimeSeriesVisualizerException(
                    "The result set returned from the database query was empty");
        }
        
        while (resultSet.next()) {
            String diseaseState = resultSet.getString("disease_state");
            String location = resultSet.getString("apollo_location_code");
            int timeStep = resultSet.getInt("time_step");
            double populationCount = resultSet.getInt("pop_count");
            
            setTimeSeriesEntry(diseaseState, location, timeStep,
                    populationCount);
        }

        // get all the locations which match the largest location code length
        if (largestLocationCodeLength == 2 && allLocations.contains("US")) {
            // can't have a larger location than the US for now
            largestLocations.add("US");
        } else {
            for (String location : allLocations) {
                if (location.length() == largestLocationCodeLength) {
                    largestLocations.add(location);
                }
            }
        }
        
        if (largestLocations.size() > 1) {
            throw new TimeSeriesVisualizerException(
                    "More than one location of the largest granularity was found, "
                    + "but the visualizer only supports one at this time");
        }
    }
    
    public void storeTimeSeriesFromDatabaseFiles(
            Map<String, ByteArrayOutputStream> map) {
        
        for (String label : map.keySet()) {
            
            String diseaseState = label.substring(0, label.indexOf(".txt")).replace("_", " ");
			
			if (diseaseState.contains("exposed")) {
				diseaseState = diseaseState.replace("exposed", "latent");
			}
			
            String content = map.get(label).toString();
            Scanner scanner = new Scanner(content);
            try {
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(" ");
                    double populationCount = Double.parseDouble(line[1]);
                    String[] timeStepStyle = line[2].split(":");
                    int timeStep = Integer.parseInt(timeStepStyle[0]);
                    
                    if (populationCount < 0) {
                        continue; // this means the line is for drawing borders
                    }
                    
                    setTimeSeriesEntry(diseaseState, DEFAULT_LOCATION,
                            timeStep, populationCount);
                }
            } finally {
                scanner.close();
            }
        }
        
        largestLocations.add(DEFAULT_LOCATION);
    }
    
    public TimeSeriesContainer getTimeSeriesForInfectionStates(List<TimeSeriesCurveTypeEnum> statesToUse)
            throws TimeSeriesVisualizerException {
        
        if (map == null || map.isEmpty()) {
            throw new TimeSeriesVisualizerException(
                    "Attempting to get SEIR time series but the map "
                    + "created from result set is empty or does not exist!");
        }
        
        TimeSeriesContainer container = new TimeSeriesContainer();
        
        Iterator<String> itr = largestLocations.iterator();
        String largestLocation = itr.next();
        
//        List<InfectionStateEnum> statesToUse = null;
//        if (getPrevalenceStates) {
//            statesToUse = new ArrayList<InfectionStateEnum>(
//                    Arrays.asList(TimeSeriesCurveTypeEnum.PREVALENCE_INFECTION_STATES));
//        }
//        if (getIncidenceStates) {
//            if (statesToUse == null) {
//                statesToUse = new ArrayList<InfectionStateEnum>(
//                        Arrays.asList(TimeSeriesCurveTypeEnum.INCIDENCE_INFECTION_STATES));
//            } else {
//                statesToUse.addAll(Arrays.asList(TimeSeriesCurveTypeEnum.INCIDENCE_INFECTION_STATES));
//            }
//
//            // statesToUse = (List<InfectionStateEnum>) ((statesToUse == null) ?
//            // Arrays.asList(TimeSeriesCurveTypeEnum.INCIDENCE_INFECTION_STATES) :
//            // statesToUse.addAll(Arrays.asList(TimeSeriesCurveTypeEnum.INCIDENCE_INFECTION_STATES)));
//        }
        
        for (TimeSeriesCurveTypeEnum diseaseState : statesToUse) {
            
            String diseaseStateValue = diseaseState.getValue();
            if (map.containsKey(diseaseStateValue)) {
                if (map.get(diseaseStateValue).containsKey(largestLocation)) {
                    Map<Integer, Double> timeSeries = map.get(diseaseStateValue).get(largestLocation);
                    Double[] timeSeriesArray = getTimeSeriesArrayFromMap(timeSeries);
                    container.setSeries(diseaseState, timeSeriesArray);
                } else {
                    throw new TimeSeriesVisualizerException(
                            "The disease state \"" + diseaseStateValue
                            + "\" was not set for the largest location");
                }
            } else {
                throw new TimeSeriesVisualizerException(
                        "The map does not contain disease state \""
                        + diseaseStateValue + "\"");
            }
        }
        
        return container;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }
}

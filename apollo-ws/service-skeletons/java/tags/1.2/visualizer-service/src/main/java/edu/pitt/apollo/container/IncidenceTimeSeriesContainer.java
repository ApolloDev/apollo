package edu.pitt.apollo.container;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2013
 * Time: 7:15:34 PM
 * Class: IncidenceTimeSeriesContainer
 * IDE: NetBeans 6.9.1
 */
public class IncidenceTimeSeriesContainer {
    
    private Map<String, double[]> incidenceTimeSeriesMap;
    private int seriesLength;

    public IncidenceTimeSeriesContainer() {
        incidenceTimeSeriesMap = new HashMap<String, double[]>();
    }
    
    /**
     * @return the incidenceTimeSeries
     */
    public double[] getIncidenceTimeSeries(String ID) {
        return incidenceTimeSeriesMap.get(ID);
    }
    
    public Map<String, double[]> getIncidenceTimeSeriesMap() {
    
        return incidenceTimeSeriesMap;
    }

    /**
     * @param incidenceTimeSeries the incidenceTimeSeries to set
     */
    public void setIncidenceTimeSeries(String ID, double[] incidenceTimeSeries) {
        this.incidenceTimeSeriesMap.put(ID, incidenceTimeSeries);
    }

    /**
     * @return the seriesLength
     */
    public int getSeriesLength() {
        return seriesLength;
    }

    /**
     * @param seriesLength the seriesLength to set
     */
    public void setSeriesLength(int seriesLength) {
        this.seriesLength = seriesLength;
    }
    
}

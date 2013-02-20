package edu.pitt.apollo.container;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2013
 * Time: 4:28:21 PM
 * Class: SeirTimeSeriesContainer
 * IDE: NetBeans 6.9.1
 */
public class SeirTimeSeriesContainer {
 
    private Map<String, double[]> susceptibleTimeSeriesMap;
    private Map<String, double[]> exposedTimeSeriesMap;
    private Map<String, double[]> infectiousTimeSeriesMap;
    private Map<String, double[]> recoveredTimeSeriesMap;
    private int seriesLength;

    public SeirTimeSeriesContainer() {
        susceptibleTimeSeriesMap = new HashMap<String, double[]>();
        exposedTimeSeriesMap = new HashMap<String, double[]>();
        infectiousTimeSeriesMap = new HashMap<String, double[]>();
        recoveredTimeSeriesMap = new HashMap<String, double[]>();
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

    /**
     * @return the susceptibleTimeSeries
     */
    public double[] getSusceptibleTimeSeries(String ID) {
        return susceptibleTimeSeriesMap.get(ID);
    }

    /**
     * @param susceptibleTimeSeries the susceptibleTimeSeries to set
     */
    public void setSusceptibleTimeSeries(String ID, double[] susceptibleTimeSeries) {
        this.susceptibleTimeSeriesMap.put(ID, susceptibleTimeSeries);
    }

    /**
     * @return the exposedTimeSeries
     */
    public double[] getExposedTimeSeries(String ID) {
        return exposedTimeSeriesMap.get(ID);
    }

    /**
     * @param exposedTimeSeries the exposedTimeSeries to set
     */
    public void setExposedTimeSeries(String ID, double[] exposedTimeSeries) {
        this.exposedTimeSeriesMap.put(ID, exposedTimeSeries);
    }

    /**
     * @return the infectiousTimeSeries
     */
    public double[] getInfectiousTimeSeries(String ID) {
        return infectiousTimeSeriesMap.get(ID);
    }

    /**
     * @param infectiousTimeSeries the infectiousTimeSeries to set
     */
    public void setInfectiousTimeSeries(String ID, double[] infectiousTimeSeries) {
        this.infectiousTimeSeriesMap.put(ID, infectiousTimeSeries);
    }

    /**
     * @return the recoveredTimeSeries
     */
    public double[] getRecoveredTimeSeries(String ID) {
        return recoveredTimeSeriesMap.get(ID);
    }

    /**
     * @param recoveredTimeSeries the recoveredTimeSeries to set
     */
    public void setRecoveredTimeSeries(String ID, double[] recoveredTimeSeries) {
        this.recoveredTimeSeriesMap.put(ID, recoveredTimeSeries);
    }
    
}

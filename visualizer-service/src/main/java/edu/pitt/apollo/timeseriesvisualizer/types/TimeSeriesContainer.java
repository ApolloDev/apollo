package edu.pitt.apollo.timeseriesvisualizer.types;

import java.math.BigInteger;
import java.util.EnumMap;
import java.util.Map;

import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2013
 * Time: 4:28:21 PM
 * Class: TimeSeriesContainer
 * IDE: NetBeans 6.9.1
 */
public class TimeSeriesContainer {

    private Map<TimeSeriesCurveTypeEnum, Double[]> seriesMap;
    private Integer seriesLength;
    private BigInteger runId;

    public TimeSeriesContainer() {
        seriesMap = new EnumMap<TimeSeriesCurveTypeEnum, Double[]>(TimeSeriesCurveTypeEnum.class);
    }

    public void setSeries(TimeSeriesCurveTypeEnum state, Double[] series) throws TimeSeriesVisualizerException {
        if (seriesLength == null) {
            seriesLength = series.length;
        } else if (series.length != seriesLength) {
            throw new TimeSeriesVisualizerException("Attempting to add a series to the TimeSeriesContainer "
                    + "with a different run length than the series already stored!");
        }
        seriesMap.put(state, series);
    }

    public Double[] getSeries(TimeSeriesCurveTypeEnum state) {
        return seriesMap.get(state);
    }
    
    /**
     * @return the seriesLength
     */
    public int getSeriesLength() {
        return seriesLength;
    }

    /**
     * @return the runId
     */
    public BigInteger getRunId() {
        return runId;
    }

    /**
     * @param runId the runId to set
     */
    public void setRunId(BigInteger runId) {
        this.runId = runId;
    }
}

package edu.pitt.apollo.timeseriesvisualizer.types;

import java.util.List;
import org.jfree.data.xy.XYDataset;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Jul 9, 2014
 Time: 4:09:54 PM
 Class: XYDatasetAndTimeSeriesCurveTypes
 */
public class XYDatasetAndTimeSeriesCurveTypes {

    private XYDataset xyDataset;
    private List<TimeSeriesCurveTypeEnum> timeSeriesCurveTypesForDataset;

    /**
     * @return the xyDataset
     */
    public XYDataset getXyDataset() {
        return xyDataset;
    }

    /**
     * @param xyDataset the xyDataset to set
     */
    public void setXyDataset(XYDataset xyDataset) {
        this.xyDataset = xyDataset;
    }

    /**
     * @return the timeSeriesCurveTypesForDataset
     */
    public List<TimeSeriesCurveTypeEnum> getTimeSeriesCurveTypesForDataset() {
        return timeSeriesCurveTypesForDataset;
    }

    /**
     * @param timeSeriesCurveTypesForDataset the timeSeriesCurveTypesForDataset to set
     */
    public void setTimeSeriesCurveTypesForDataset(List<TimeSeriesCurveTypeEnum> timeSeriesCurveTypesForDataset) {
        this.timeSeriesCurveTypesForDataset = timeSeriesCurveTypesForDataset;
    }

}

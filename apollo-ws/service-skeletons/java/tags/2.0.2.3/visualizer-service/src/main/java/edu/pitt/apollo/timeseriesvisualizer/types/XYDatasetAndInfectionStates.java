package edu.pitt.apollo.timeseriesvisualizer.types;

import java.util.List;
import org.jfree.data.xy.XYDataset;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Jul 9, 2014
 Time: 4:09:54 PM
 Class: XYDatasetAndInfectionStates
 */
public class XYDatasetAndInfectionStates {

    private XYDataset xyDataset;
    private List<InfectionStateEnum> infectionStatesForDataset;

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
     * @return the infectionStatesForDataset
     */
    public List<InfectionStateEnum> getInfectionStatesForDataset() {
        return infectionStatesForDataset;
    }

    /**
     * @param infectionStatesForDataset the infectionStatesForDataset to set
     */
    public void setInfectionStatesForDataset(List<InfectionStateEnum> infectionStatesForDataset) {
        this.infectionStatesForDataset = infectionStatesForDataset;
    }

}

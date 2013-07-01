package edu.pitt.apollo.container;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2013
 * Time: 7:14:53 PM
 * Class: ImageSeriesContainer
 * IDE: NetBeans 6.9.1
 */
public class ImageSeriesContainer {
    
    private SeirTimeSeriesContainer seirSeriesContainer;
    private IncidenceTimeSeriesContainer incidenceSeriesContainer;

    /**
     * @return the seirSeriesContainer
     */
    public SeirTimeSeriesContainer getSeirSeriesContainer() {
        return seirSeriesContainer;
    }

    /**
     * @param seirSeriesContainer the seirSeriesContainer to set
     */
    public void setSeirSeriesContainer(SeirTimeSeriesContainer seirSeriesContainer) {
        this.seirSeriesContainer = seirSeriesContainer;
    }

    /**
     * @return the incidenceSeriesContainer
     */
    public IncidenceTimeSeriesContainer getIncidenceSeriesContainer() {
        return incidenceSeriesContainer;
    }

    /**
     * @param incidenceSeriesContainer the incidenceSeriesContainer to set
     */
    public void setIncidenceSeriesContainer(IncidenceTimeSeriesContainer incidenceSeriesContainer) {
        this.incidenceSeriesContainer = incidenceSeriesContainer;
    }
    
}

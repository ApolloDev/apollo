package edu.pitt.apollo.timeseriesvisualizer.utilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.timeseriesvisualizer.types.InfectionStateEnum;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainer;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainerList;
import edu.pitt.apollo.timeseriesvisualizer.types.XYDatasetAndInfectionStates;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 12, 2013 Time:
 * 6:09:29 PM Class: VisualizerChartUtility IDE: NetBeans 6.9.1
 */
class SimpleNumberAxis extends NumberAxis implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4979128805460961375L;
    static Logger logger = LoggerFactory.getLogger(SimpleNumberAxis.class);

    @Override
    public List<NumberTick> refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea,
            RectangleEdge edge) {

        // for a tick interval >= 5, we add a tick for 1 and then keep the
        // default ticks,
        // e.g. 1, 5, 10, 15,...
        // for a tick interval < 5, we add a tick for 1 and then start measuring
        // the ticks from there,
        // e.g. for a tick interval of 2, we would have 1, 3, 5, 7,...
        @SuppressWarnings("unchecked")
        List<NumberTick> allTicks = super.refreshTicks(g2, state, dataArea, edge);
        List<NumberTick> myTicks = new ArrayList<NumberTick>();

        // get the default tick interval
        int tickInterval = (int) super.getTickUnit().getSize();

        // first we need to sort the tick units array to make sure we skip the
        // right ticks
        Collections.sort(allTicks, new Comparator<NumberTick>() {

            @Override
            public int compare(NumberTick o1, NumberTick o2) {
                return ((Integer) o1.getNumber().intValue()).compareTo(o2.getNumber().intValue());
            }
        });

        int tickCount = 0;
        for (int i = 0; i < allTicks.size(); i++) {
            NumberTick numberTick = (NumberTick) allTicks.get(i);

            if (i == 0) {
                NumberTick newtick = new NumberTick(1, "1", numberTick.getTextAnchor(),
                        numberTick.getRotationAnchor(), numberTick.getAngle());
                myTicks.add(newtick);
                tickCount++;
                continue;
            }

            if (tickInterval >= 5) {
                // no adjustment needed in this case, the tick interval will be
                // maintained
                myTicks.add(numberTick);
            } else {
                // creating a new NumberTick and adding it to myTicks will plot
                // ALL ticks (1, 2, 3, 4,...) so we need to skip some
                // according to the tick interval
                if (tickCount % tickInterval == 0) {
                    NumberTick newtick = new NumberTick(numberTick.getNumber().intValue(),
                            Integer.toString((numberTick.getNumber().intValue())),
                            numberTick.getTextAnchor(), numberTick.getRotationAnchor(), numberTick.getAngle());
                    myTicks.add(newtick);
                }
            }

            tickCount++;
        }
        return myTicks;
    }
}

public class VisualizerChartUtility {

    static Logger logger = LoggerFactory.getLogger(VisualizerChartUtility.class);

    private static BasicStroke seriesStroke = new BasicStroke(3f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER);

    private double computeYAxisStep(double maxYValue) {
        double yAxisStep;

        if (maxYValue <= 6 && maxYValue >= 3) {
            yAxisStep = 0.5;
        } else {
            if (maxYValue > 6) {
                // this method of computing the step ensures it is a multiple of
                // 10
                yAxisStep = Math.round(maxYValue / 10.0);
                yAxisStep = (int) Math.pow(10, Math.floor(Math.log10(yAxisStep) - Math.log10(0.5))) * 2;
            } else {
                // first we transform the maxYValue by mutliplying it by the
                // largest
                // factor of 10 such that the product is < 1000. This allows the
                // yAxisStep
                // to be computed using the above method and which is then
                // converted back to
                // the appropriate scale
                int power = (int) Math.floor(1 / (10 * maxYValue));
                int multiplier = (int) Math.round(1000 * Math.pow(10, power));
                yAxisStep = Math.round(maxYValue * multiplier / 10.0);
                yAxisStep = (int) Math.pow(10, Math.floor(Math.log10(yAxisStep) - Math.log10(0.5))) * 2;
                yAxisStep = yAxisStep / multiplier;
            }

            if (maxYValue / yAxisStep > 10) {
                // make sure there are no more than 10 ticks
                double newYAxisStep = yAxisStep;
                while (maxYValue / newYAxisStep > 10) {
                    newYAxisStep += yAxisStep;
                }
                yAxisStep = newYAxisStep;
            } else if (maxYValue / yAxisStep < 4) {
                // make sure there are at least 4 ticks
                while (maxYValue / yAxisStep < 4) {
                    yAxisStep /= 2;
                }
            }
        }

        return yAxisStep;
    }

    private JFreeChart createIncidenceChart(final XYDataset dataset, String mainTitle, String xTitle,
            String yTitle) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(mainTitle, // chart
                // title
                xTitle, // x axis label
                yTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, // tooltips
                false // urls
        );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));

        // get greatest data value
        double maxYValue = 0.0;
        double maxXValue = 0.0;
        for (int i = 0; i < dataset.getItemCount(0); i++) {

            if (dataset.getYValue(0, i) > maxYValue) {
                maxYValue = dataset.getYValue(0, i);
            }

            if (dataset.getXValue(0, i) > maxXValue) {
                maxXValue = dataset.getXValue(0, i);
            }
        }

        double yAxisStep = computeYAxisStep(maxYValue);

        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setTickUnit(new NumberTickUnit(yAxisStep));
        axis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        axis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));

        SimpleNumberAxis xaxis = new SimpleNumberAxis();
        // NumberAxis xaxis = (NumberAxis) plot.getDomainAxis();
        xaxis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        xaxis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));
        xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xaxis.setRange(1, maxXValue);
        // xaxis.setLowerBound(1.0);
        plot.setDomainAxis(xaxis);

        LegendTitle legend = (LegendTitle) chart.getLegend();
        legend.setItemFont(new Font("calibri", Font.PLAIN, 20));
        legend.setItemLabelPadding(new RectangleInsets(5, 5, 5, 25));
        legend.setLegendItemGraphicPadding(new RectangleInsets(0, 25, 0, 0));
        legend.setPosition(RectangleEdge.TOP);

        TextTitle title = chart.getTitle();
        title.setFont(new Font("Calibri", Font.BOLD, 37));

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesStroke(0, seriesStroke);
        renderer.setSeriesPaint(0, new Color(235, 33, 33)); // RED
        renderer.setSeriesShapesVisible(0, false);

        plot.setRenderer(renderer);

        return chart;
    }

    private JFreeChart createCombinedIncidenceChart(final XYDataset dataset, String mainTitle, String xTitle,
            String yTitle) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(mainTitle, // chart
                // title
                xTitle, // x axis label
                yTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, // tooltips
                false // urls
        );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));

        // get greatest data value
        double maxYValue = 0.0, maxXValue = 0.0;
        // int seriesCount = dataset.getSeriesCount();
        for (int j = 0; j < dataset.getSeriesCount(); j++) {
            for (int i = 0; i < dataset.getItemCount(j); i++) {
                if (dataset.getYValue(j, i) > maxYValue) {
                    maxYValue = dataset.getYValue(j, i);
                }
                if (dataset.getXValue(0, i) > maxXValue) {
                    maxXValue = dataset.getXValue(0, i);
                }
            }
        }

        double yAxisStep = computeYAxisStep(maxYValue);

        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setTickUnit(new NumberTickUnit(yAxisStep));
        axis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        axis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));

        SimpleNumberAxis xaxis = new SimpleNumberAxis();
        // NumberAxis xaxis = (NumberAxis) plot.getDomainAxis();
        xaxis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        xaxis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));
        xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xaxis.setRange(1, maxXValue);
        // xaxis.setLowerBound(1.0);
        plot.setDomainAxis(xaxis);

        LegendTitle legend = (LegendTitle) chart.getLegend();
        legend.setItemFont(new Font("calibri", Font.PLAIN, 20));
        legend.setItemLabelPadding(new RectangleInsets(5, 5, 5, 25));
        legend.setLegendItemGraphicPadding(new RectangleInsets(0, 25, 0, 0));
        legend.setPosition(RectangleEdge.TOP);

        TextTitle title = chart.getTitle();
        title.setFont(new Font("Calibri", Font.BOLD, 37));

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesStroke(0, seriesStroke);
        renderer.setSeriesPaint(0, new Color(235, 33, 33)); // RED
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesStroke(1, seriesStroke);
        renderer.setSeriesPaint(1, new Color(55, 83, 196));
        renderer.setSeriesShapesVisible(1, false);
        renderer.setSeriesStroke(2, seriesStroke);
        renderer.setSeriesPaint(2, new Color(5, 158, 61)); // GREEN
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesStroke(3, seriesStroke);
        renderer.setSeriesPaint(3, new Color(216, 56, 224)); // PURPLE
        renderer.setSeriesShapesVisible(3, false);
        // for (int i = 0; i < seriesCount; i++) {
        // renderer.setSeriesStroke(i, seriesStroke);
        // renderer.setSeriesPaint(0, new Color(235, 33, 33)); // RED
        // renderer.setSeriesShapesVisible(i, false);
        // }

        plot.setRenderer(renderer);

        return chart;
    }

    private JFreeChart createDiseaseStatesChart(final XYDataset dataset, String mainTitle, String xTitle,
            String yTitle, List<InfectionStateEnum> infectionStateEnumsForDataset) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(mainTitle, // chart
                // title
                xTitle, // x axis label
                yTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, // tooltips
                false // urls
        );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));

        // get the greatest data value
        double maxYValue = 0.0, maxXValue = 0.0;
        for (int j = 0; j < dataset.getSeriesCount(); j++) {
            for (int i = 0; i < dataset.getItemCount(j); i++) {
                if (dataset.getYValue(j, i) > maxYValue) {
                    maxYValue = dataset.getYValue(j, i);
                }
                if (dataset.getXValue(0, i) > maxXValue) {
                    maxXValue = dataset.getXValue(0, i);
                }
            }
        }
        double yAxisStep = computeYAxisStep(maxYValue);

        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setTickUnit(new NumberTickUnit(yAxisStep));
        axis.setTickLabelFont(new Font("calibri", Font.PLAIN, 20));
        axis.setLabelFont(new Font("calibri", Font.PLAIN, 30));

        NumberAxis xaxis = (NumberAxis) plot.getDomainAxis();
        xaxis.setTickLabelFont(new Font("calibri", Font.PLAIN, 20));
        xaxis.setLabelFont(new Font("calibri", Font.PLAIN, 30));
        xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        xaxis.setRange(0.0, maxXValue);

        LegendTitle legend = (LegendTitle) chart.getLegend();
        legend.setItemFont(new Font("calibri", Font.PLAIN, 20));
        legend.setItemLabelPadding(new RectangleInsets(5, 5, 5, 25));
        legend.setLegendItemGraphicPadding(new RectangleInsets(0, 25, 0, 0));
        legend.setPosition(RectangleEdge.TOP);

        TextTitle title = chart.getTitle();
        title.setFont(new Font("calibri", Font.BOLD, 37));

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        for (int i = 0; i < infectionStateEnumsForDataset.size(); i++) {

            InfectionStateEnum isEnum = infectionStateEnumsForDataset.get(i);
            if (isEnum.equals(InfectionStateEnum.SUSCEPTIBLE)) {
                renderer.setSeriesPaint(i, new Color(55, 83, 196)); // BLUE -
            } else if (isEnum.equals(InfectionStateEnum.EXPOSED)) {
                renderer.setSeriesPaint(i, new Color(235, 33, 33)); // RED - EXPOSED
            } else if (isEnum.equals(InfectionStateEnum.INFECTIOUS)) {
                renderer.setSeriesPaint(i, new Color(216, 56, 224)); // PURPLE -
            } else if (isEnum.equals(InfectionStateEnum.RECOVERED)) {
                renderer.setSeriesPaint(i, new Color(5, 158, 61)); // GREEN - RECOVERED
            }

            renderer.setSeriesStroke(i, seriesStroke);
            renderer.setSeriesShapesVisible(i, false);
        }

        plot.setRenderer(renderer);

        return chart;
    }

    private XYSeries createXYSeries(Double[] data, String name, int startIndex) {

        final XYSeries series = new XYSeries(name);
        for (int i = startIndex; i < data.length; i++) {
            series.add(i, data[i]);
        }

        return series;
    }

    private XYDatasetAndInfectionStates createSeirXYDataset(TimeSeriesContainer container) {

        final XYSeriesCollection dataset = new XYSeriesCollection();

        List<InfectionStateEnum> isEnumsForSeries = new ArrayList<InfectionStateEnum>();
        for (InfectionStateEnum isEnum : InfectionStateEnum.PREVALENCE_INFECTION_STATES) {
            if (container.getSeries(isEnum) != null) {
                String name = isEnum.toString().toLowerCase();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                isEnumsForSeries.add(isEnum);
                dataset.addSeries(createXYSeries(container.getSeries(isEnum), name,
                        0));
            }
        }

//        dataset.addSeries(createXYSeries(container.getSeries(InfectionStateEnum.SUSCEPTIBLE), "Susceptible",
//                0));
//        dataset.addSeries(createXYSeries(container.getSeries(InfectionStateEnum.EXPOSED), "Exposed", 0));
//        dataset.addSeries(createXYSeries(container.getSeries(InfectionStateEnum.INFECTIOUS), "Infectious", 0));
//        dataset.addSeries(createXYSeries(container.getSeries(InfectionStateEnum.RECOVERED), "Recovered", 0));
        XYDatasetAndInfectionStates xyDatasetAndLabels = new XYDatasetAndInfectionStates();
        xyDatasetAndLabels.setXyDataset(dataset);
        xyDatasetAndLabels.setInfectionStatesForDataset(isEnumsForSeries);

        return xyDatasetAndLabels;
    }

    private XYDataset createIncidenceDataset(TimeSeriesContainer container, BigInteger runId) {

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(createXYSeries(container.getSeries(InfectionStateEnum.NEWLY_EXPOSED), "Incidence",
                1));

        return dataset;
    }

    private XYDataset createCombinedIncidenceDataset(TimeSeriesContainerList timeSeriesContainerList,
            Map<BigInteger, String> runIdSeriesLabels) {

        final XYSeriesCollection dataset = new XYSeriesCollection();
        // Map<String, double[]> incidenceMap =
        // container.getIncidenceTimeSeriesMap();

        for (TimeSeriesContainer container : timeSeriesContainerList) {
            BigInteger runId = container.getRunId();
            dataset.addSeries(createXYSeries(container.getSeries(InfectionStateEnum.NEWLY_EXPOSED),
                    runIdSeriesLabels.get(runId), 1));

        }

        return dataset;
    }

    public void createSeirTimeSeriesChart(TimeSeriesContainerList timeSeriesContainerList,
            Map<BigInteger, String> filepathMap) {

        for (TimeSeriesContainer container : timeSeriesContainerList) {

            BigInteger runId = container.getRunId();
            // if (runId.toLowerCase().contains("flute")) { // can't show
            // disease states for flute yet
            // continue;
            // }

            XYDatasetAndInfectionStates xyDatasetAndLabels = createSeirXYDataset(container);
            XYDataset dataset = xyDatasetAndLabels.getXyDataset();
            List<InfectionStateEnum> infectionStatesForDataset = xyDatasetAndLabels.getInfectionStatesForDataset();
            JFreeChart chart = createDiseaseStatesChart(dataset, "Disease states over time",
                    "simulation time step", "", infectionStatesForDataset);
            BufferedImage image = chart.createBufferedImage(1750, 1000, BufferedImage.TYPE_INT_RGB, null);

            File imageFile = new File(filepathMap.get(runId));
            logger.info("Using '{}' for prevalance chart image file.", imageFile.getAbsolutePath());
            if (!imageFile.exists()) {
                imageFile.getParentFile().mkdirs();
                try {
                    imageFile.createNewFile();
                } catch (IOException ex) {
                    System.err.println("Disease states image file could not be created" + ex.getMessage());
                }
            }

            try {
                ImageIO.write(image, "png", imageFile);
            } catch (IOException ex) {
                System.err.println("Disease states image file could not be written: " + ex.getMessage());
            }

        }
    }

    public void createIncidenceTimeSeriesChart(TimeSeriesContainerList timeSeriesContainerList,
            Map<BigInteger, String> filepathMap) {

        for (TimeSeriesContainer container : timeSeriesContainerList) {

            BigInteger runId = container.getRunId();
            XYDataset dataset = createIncidenceDataset(container, runId);
            JFreeChart chart = createIncidenceChart(dataset, "Incidence of newly exposed over time",
                    "simulation time step", "");
            BufferedImage image = chart.createBufferedImage(1750, 1000, BufferedImage.TYPE_INT_RGB, null);

            File imageFile = new File(filepathMap.get(runId));
            logger.info("Using '{}' for incidence chart image file.", imageFile.getAbsolutePath());
            if (!imageFile.exists()) {
                try {
                    imageFile.getParentFile().mkdirs();
                    imageFile.createNewFile();
                } catch (IOException ex) {
                    System.err.println("Incidence image file could not be created" + ex.getMessage());
                }
            }
            try {
                ImageIO.write(image, "png", imageFile);
            } catch (IOException ex) {
                System.err.println("Incidence image file could not be written: " + ex.getMessage());
            }
        }
    }

    public void createCombinedIncidenceTimeSeriesChart(TimeSeriesContainerList imageSeriesMap,
            String filepath, Map<BigInteger, String> runIdSeriesLabels) {

        XYDataset dataset = createCombinedIncidenceDataset(imageSeriesMap, runIdSeriesLabels);
        JFreeChart chart = createCombinedIncidenceChart(dataset, "Incidence of newly exposed over time",
                "simulation time step", "");
        BufferedImage image = chart.createBufferedImage(1750, 1000, BufferedImage.TYPE_INT_RGB, null);

        File imageFile = new File(filepath);
        logger.info("Using '{}' for combined incidence time series chart.", imageFile.getAbsolutePath());
        if (!imageFile.exists()) {
            try {
                imageFile.getParentFile().mkdirs();
                imageFile.createNewFile();
            } catch (IOException ex) {
                System.err.println("Incidence image file could not be created" + ex.getMessage());
            }
        }
        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException ex) {
            System.err.println("Incidence image file could not be written: " + ex.getMessage());
        }

    }

	// public static void main(String[] args) {
    //
    // Double[] series = new Double[128];
    // for (int i = 0; i < 128; i++) {
    // series[i] = 0.5 + i * 0.015;
    // }
    //
    // VisualizerChartUtility utility = new VisualizerChartUtility();
    // final XYSeriesCollection dataset = new XYSeriesCollection();
    // dataset.addSeries(utility.createXYSeries(series, "test", 0));
    //
    // JFreeChart chart = utility.createIncidenceChart(dataset, "test",
    // "simulation time step", "");
    // BufferedImage image = chart.createBufferedImage(1750, 1000,
    // BufferedImage.TYPE_INT_RGB, null);
    //
    // File imageFile = new File("./test.png");
    // if (!imageFile.exists()) {
    // try {
    // imageFile.createNewFile();
    // } catch (IOException ex) {
    // System.err.println("Incidence image file could not be created" +
    // ex.getMessage());
    // }
    // }
    // try {
    // ImageIO.write(image, "png", imageFile);
    // } catch (IOException ex) {
    // System.err.println("Incidence image file could not be written: " +
    // ex.getMessage());
    // }
    // }
}

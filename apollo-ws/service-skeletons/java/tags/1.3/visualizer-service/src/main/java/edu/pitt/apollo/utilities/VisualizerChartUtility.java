package edu.pitt.apollo.utilities;

import edu.pitt.apollo.container.IncidenceTimeSeriesContainer;
import edu.pitt.apollo.container.SeirTimeSeriesContainer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2013
 * Time: 6:09:29 PM
 * Class: VisualizerChartUtility
 * IDE: NetBeans 6.9.1
 */
public class VisualizerChartUtility {

    private static BasicStroke seriesStroke = new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    private JFreeChart createIncidenceChart(final XYDataset dataset, String mainTitle, String xTitle, String yTitle) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                mainTitle, // chart title
                xTitle, // x axis label
                yTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));

        // get greatest data value
        double maxYValue = 0.0;
        for (int i = 0; i < dataset.getItemCount(0); i++) {

            if (dataset.getYValue(0, i) > maxYValue) {
                maxYValue = dataset.getYValue(0, i);
            }
        }
        int yAxisStep = (int) Math.round(maxYValue / 10.0);
        yAxisStep = (int) Math.pow(10, Math.floor(Math.log10(yAxisStep) - Math.log10(0.5))) * 2;

        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setTickUnit(new NumberTickUnit(yAxisStep));
        axis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        axis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));


        NumberAxis xaxis = (NumberAxis) plot.getDomainAxis();
        xaxis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        xaxis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));

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

    private JFreeChart createCombinedIncidenceChart(final XYDataset dataset, String mainTitle, String xTitle, String yTitle) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                mainTitle, // chart title
                xTitle, // x axis label
                yTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));

        // get greatest data value
        double maxYValue = 0.0;
        int seriesCount = dataset.getSeriesCount();
        for (int j = 0; j < dataset.getSeriesCount(); j++) {
            for (int i = 0; i < dataset.getItemCount(j); i++) {
                if (dataset.getYValue(j, i) > maxYValue) {
                    maxYValue = dataset.getYValue(j, i);
                }
            }
        }
        int yAxisStep = (int) Math.round(maxYValue / 10.0);
        yAxisStep = (int) Math.pow(10, Math.floor(Math.log10(yAxisStep) - Math.log10(0.5))) * 2;

        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setTickUnit(new NumberTickUnit(yAxisStep));
        axis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        axis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));


        NumberAxis xaxis = (NumberAxis) plot.getDomainAxis();
        xaxis.setTickLabelFont(new Font("Calibri", Font.PLAIN, 20));
        xaxis.setLabelFont(new Font("Calibri", Font.PLAIN, 30));

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
//        for (int i = 0; i < seriesCount; i++) {
//            renderer.setSeriesStroke(i, seriesStroke);
//        renderer.setSeriesPaint(0, new Color(235, 33, 33)); // RED
//            renderer.setSeriesShapesVisible(i, false);
//        }

        plot.setRenderer(renderer);

        return chart;
    }

    private JFreeChart createDiseaseStatesChart(final XYDataset dataset, String mainTitle, String xTitle, String yTitle) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                mainTitle, // chart title
                xTitle, // x axis label
                yTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(255, 255, 255));

        // get the greatest data value
        double maxYValue = 0.0;
        for (int j = 0; j < dataset.getSeriesCount(); j++) {
            for (int i = 0; i < dataset.getItemCount(j); i++) {
                if (dataset.getYValue(j, i) > maxYValue) {
                    maxYValue = dataset.getYValue(j, i);
                }
            }
        }
        int yAxisStep = (int) Math.round(maxYValue / 10.0);
        yAxisStep = (int) Math.pow(10, Math.floor(Math.log10(yAxisStep) - Math.log10(0.5)));

        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setTickUnit(new NumberTickUnit(yAxisStep));
        axis.setTickLabelFont(new Font("calibri", Font.PLAIN, 20));
        axis.setLabelFont(new Font("calibri", Font.PLAIN, 30));


        NumberAxis xaxis = (NumberAxis) plot.getDomainAxis();
        xaxis.setTickLabelFont(new Font("calibri", Font.PLAIN, 20));
        xaxis.setLabelFont(new Font("calibri", Font.PLAIN, 30));

        LegendTitle legend = (LegendTitle) chart.getLegend();
        legend.setItemFont(new Font("calibri", Font.PLAIN, 20));
        legend.setItemLabelPadding(new RectangleInsets(5, 5, 5, 25));
        legend.setLegendItemGraphicPadding(new RectangleInsets(0, 25, 0, 0));
        legend.setPosition(RectangleEdge.TOP);

        TextTitle title = chart.getTitle();
        title.setFont(new Font("calibri", Font.BOLD, 37));

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

        plot.setRenderer(renderer);



        return chart;
    }

    private XYSeries createXYSeries(double[] data, String name) {

        final XYSeries series = new XYSeries(name);
        for (int i = 0; i < data.length; i++) {
            series.add(i, data[i]);
        }

        return series;
    }

    private XYDataset createSeirXYDataset(SeirTimeSeriesContainer container, String runId) {

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(createXYSeries(container.getSusceptibleTimeSeries(runId), "Susceptible"));
        dataset.addSeries(createXYSeries(container.getExposedTimeSeries(runId), "Exposed"));
        dataset.addSeries(createXYSeries(container.getInfectiousTimeSeries(runId), "Infectious"));
        dataset.addSeries(createXYSeries(container.getRecoveredTimeSeries(runId), "Recovered"));

        return dataset;
    }

    private XYDataset createIncidenceDataset(IncidenceTimeSeriesContainer container, String runId) {

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(createXYSeries(container.getIncidenceTimeSeries(runId), "Incidence"));

        return dataset;
    }

    private XYDataset createCombinedIncidenceDataset(IncidenceTimeSeriesContainer container,
            Map<String, String> runIdSeriesLabels) {

        final XYSeriesCollection dataset = new XYSeriesCollection();
        Map<String, double[]> incidenceMap = container.getIncidenceTimeSeriesMap();

        int i = 0;
        for (String runId : incidenceMap.keySet()) {
            dataset.addSeries(createXYSeries(incidenceMap.get(runId), runIdSeriesLabels.get(runId)));
            i++;
        }

        return dataset;
    }

    public void createSeirTimeSeriesChart(SeirTimeSeriesContainer seirContainer, Map<String, String> filepathMap) {

        for (String runId : filepathMap.keySet()) {

            if (runId.toLowerCase().contains("flute")) { // can't show disease states for flute yet
                continue;
            }
            
            XYDataset dataset = createSeirXYDataset(seirContainer, runId);
            JFreeChart chart = createDiseaseStatesChart(dataset, "Disease states over time", "simulation time step", "");
            BufferedImage image = chart.createBufferedImage(1750, 1000, BufferedImage.TYPE_INT_RGB, null);

            File imageFile = new File(filepathMap.get(runId));
            System.out.println(imageFile.getAbsolutePath());
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

    public void createIncidenceTimeSeriesChart(IncidenceTimeSeriesContainer incidenceContainer, Map<String, String> filepathMap) {

        for (String runId : filepathMap.keySet()) {

            XYDataset dataset = createIncidenceDataset(incidenceContainer, runId);
            JFreeChart chart = createIncidenceChart(dataset, "Incidence over time", "simulation time step", "");
            BufferedImage image = chart.createBufferedImage(1750, 1000, BufferedImage.TYPE_INT_RGB, null);

            File imageFile = new File(filepathMap.get(runId));
            System.out.println(imageFile.getAbsolutePath());
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

    public void createCombinedIncidenceTimeSeriesChart(IncidenceTimeSeriesContainer incidenceContainer, String filepath,
            Map<String, String> runIdSeriesLabels) {

        XYDataset dataset = createCombinedIncidenceDataset(incidenceContainer, runIdSeriesLabels);
        JFreeChart chart = createCombinedIncidenceChart(dataset, "Incidence over time", "simulation time step", "");
        BufferedImage image = chart.createBufferedImage(1750, 1000, BufferedImage.TYPE_INT_RGB, null);

        File imageFile = new File(filepath);
        System.out.println(imageFile.getAbsolutePath());
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

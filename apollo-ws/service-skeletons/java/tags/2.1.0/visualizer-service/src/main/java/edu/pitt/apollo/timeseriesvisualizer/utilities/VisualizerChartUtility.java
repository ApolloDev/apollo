package edu.pitt.apollo.timeseriesvisualizer.utilities;

import edu.pitt.apollo.timeseriesvisualizer.types.ChartTypeEnum;
import edu.pitt.apollo.timeseriesvisualizer.types.ChartTypeProperties;
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

import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeEnum;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainer;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainerList;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeList;
import edu.pitt.apollo.timeseriesvisualizer.types.XYDatasetAndTimeSeriesCurveTypes;
import java.util.EnumMap;

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
	private static final String COMBINED_INCIDENCE_CHART_TITLE = "Incidence of newly latent over time";
	private static final BasicStroke SERIES_STROKE = new BasicStroke(3f, BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER);
	private final TimeSeriesContainerList timeSeriesContainerList;
	private static final Map<TimeSeriesCurveTypeEnum, Color> colorsForTimeSeriesCurveTypes;

	public VisualizerChartUtility(TimeSeriesContainerList timeSeriesContainerList) {
		this.timeSeriesContainerList = timeSeriesContainerList;
	}

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
		xaxis.setLabel(xTitle);
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
		renderer.setSeriesStroke(0, SERIES_STROKE);
		renderer.setSeriesPaint(0, new Color(235, 33, 33)); // RED
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesStroke(1, SERIES_STROKE);
		renderer.setSeriesPaint(1, new Color(55, 83, 196));
		renderer.setSeriesShapesVisible(1, false);
		renderer.setSeriesStroke(2, SERIES_STROKE);
		renderer.setSeriesPaint(2, new Color(5, 158, 61)); // GREEN
		renderer.setSeriesShapesVisible(2, false);
		renderer.setSeriesStroke(3, SERIES_STROKE);
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

	private JFreeChart createChart(XYDatasetAndTimeSeriesCurveTypes datasetAndCurveTypes, String mainTitle, String xTitle,
			String yTitle) {

		final XYDataset dataset = datasetAndCurveTypes.getXyDataset();
		final TimeSeriesCurveTypeList curveTypeEnumsForDataset = datasetAndCurveTypes.getTimeSeriesCurveTypesForDataset();

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

		NumberAxis axis = (NumberAxis) plot.getRangeAxis();
		double yAxisStepComputeValue = (maxYValue > 0) ? maxYValue : 1;
		double yAxisStep = computeYAxisStep(yAxisStepComputeValue);
		axis.setTickUnit(new NumberTickUnit(yAxisStep));
		if (maxYValue == 0.0) {
			axis.setRange(0.0, 1.0);
		}

		axis.setTickLabelFont(new Font("calibri", Font.PLAIN, 20));
		axis.setLabelFont(new Font("calibri", Font.PLAIN, 30));

		NumberAxis xaxis;

		if (curveTypeEnumsForDataset.listContainsIncidenceCurveTypes()
				|| curveTypeEnumsForDataset.listContainsNewlyDeceasedCurveTypes()) {
			xaxis = new SimpleNumberAxis();
			plot.setDomainAxis(xaxis);

			xaxis.setRange(1, maxXValue);
			xaxis.setLabel(xTitle);
		} else {
			xaxis = (NumberAxis) plot.getDomainAxis();
			xaxis.setRange(0.0, maxXValue);
		}

		xaxis.setTickLabelFont(new Font("calibri", Font.PLAIN, 20));
		xaxis.setLabelFont(new Font("calibri", Font.PLAIN, 30));
		xaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		LegendTitle legend = (LegendTitle) chart.getLegend();
		legend.setItemFont(new Font("calibri", Font.PLAIN, 20));
		legend.setItemLabelPadding(new RectangleInsets(5, 5, 5, 25));
		legend.setLegendItemGraphicPadding(new RectangleInsets(0, 25, 0, 0));
		legend.setPosition(RectangleEdge.TOP);

		TextTitle title = chart.getTitle();
		title.setFont(new Font("calibri", Font.BOLD, 37));

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

		for (int i = 0; i < curveTypeEnumsForDataset.size(); i++) {

			TimeSeriesCurveTypeEnum seriesCurveTypeEnum = curveTypeEnumsForDataset.get(i);
			renderer.setSeriesPaint(i, colorsForTimeSeriesCurveTypes.get(seriesCurveTypeEnum));
//			if (isEnum.equals(TimeSeriesCurveTypeEnum.SUSCEPTIBLE)) {
//				renderer.setSeriesPaint(i, new Color(55, 83, 196)); // BLUE -
//			} else if (isEnum.equals(TimeSeriesCurveTypeEnum.LATENT)
//					|| (isEnum.equals(TimeSeriesCurveTypeEnum.NEWLY_LATENT))) {
//				renderer.setSeriesPaint(i, new Color(235, 33, 33)); // RED - LATENT
//			} else if (isEnum.equals(TimeSeriesCurveTypeEnum.INFECTIOUS)
//					|| (isEnum.equals(TimeSeriesCurveTypeEnum.NEWLY_DECEASED))) {
//				renderer.setSeriesPaint(i, new Color(216, 56, 224)); // PURPLE -
//			} else if (isEnum.equals(TimeSeriesCurveTypeEnum.RECOVERED)) {
//				renderer.setSeriesPaint(i, new Color(5, 158, 61)); // GREEN - RECOVERED
//			}

			renderer.setSeriesStroke(i, SERIES_STROKE);
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

	private XYDatasetAndTimeSeriesCurveTypes createXYDataset(TimeSeriesContainer container, TimeSeriesCurveTypeEnum[] curveTypes) {
		final XYSeriesCollection dataset = new XYSeriesCollection();

		TimeSeriesCurveTypeList curveTypeEnumsForSeries = new TimeSeriesCurveTypeList();
		for (TimeSeriesCurveTypeEnum curveTypeEnum : curveTypes) {
			if (container.getSeries(curveTypeEnum) != null) {
				String name = curveTypeEnum.toString().toLowerCase();
//				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				curveTypeEnumsForSeries.add(curveTypeEnum);
				dataset.addSeries(createXYSeries(container.getSeries(curveTypeEnum), name,
						0));
			}
		}

		curveTypeEnumsForSeries.processAddedCurveTypes();
		XYDatasetAndTimeSeriesCurveTypes xyDatasetAndLabels = new XYDatasetAndTimeSeriesCurveTypes();
		xyDatasetAndLabels.setXyDataset(dataset);
		xyDatasetAndLabels.setTimeSeriesCurveTypesForDataset(curveTypeEnumsForSeries);

		return xyDatasetAndLabels;
	}

	private XYDataset createCombinedIncidenceDataset(TimeSeriesContainerList timeSeriesContainerList,
			Map<BigInteger, String> runIdSeriesLabels) {

		final XYSeriesCollection dataset = new XYSeriesCollection();
		for (TimeSeriesContainer container : timeSeriesContainerList) {
			BigInteger runId = container.getRunId();
			dataset.addSeries(createXYSeries(container.getSeries(TimeSeriesCurveTypeEnum.NEWLY_LATENT),
					runIdSeriesLabels.get(runId), 1));

		}

		return dataset;
	}

	private void createTimeSeriesChart(XYDatasetAndTimeSeriesCurveTypes xyDatasetAndLabels,
			String filePath, String chartXAxisLabel, String chartTitle) {

		JFreeChart chart = createChart(xyDatasetAndLabels, chartTitle,
				chartXAxisLabel, "");
		BufferedImage image = chart.createBufferedImage(1750, 1000, BufferedImage.TYPE_INT_RGB, null);
		File imageFile = new File(filePath);

		logger.info("Using '{}' for prevalance chart image file.", imageFile.getAbsolutePath());
		if (!imageFile.exists()) {
			imageFile.getParentFile().mkdirs();
			try {
				imageFile.createNewFile();
			} catch (IOException ex) {
				System.err.println("Image file could not be created" + ex.getMessage());
			}
		}

		try {
			ImageIO.write(image, "png", imageFile);
		} catch (IOException ex) {
			System.err.println("Image file could not be written: " + ex.getMessage());
		}
	}

	public void createTimeSeriesChart(ChartTypeProperties chartTypeProps, String chartXAxisLabel,
			TimeSeriesCurveTypeEnum[] allCurveTypesForImage, Map<String, String> resourceMap) {

		String fileName = chartTypeProps.getFileNameForChart();
		String filePath = chartTypeProps.getDirectoryForChart() + File.separator + fileName;
		String chartTitle = chartTypeProps.getTitleForChart();
		for (TimeSeriesContainer container : timeSeriesContainerList) {
			XYDatasetAndTimeSeriesCurveTypes xyDatasetAndLabels = createXYDataset(container, allCurveTypesForImage);
			createTimeSeriesChart(xyDatasetAndLabels, filePath, chartXAxisLabel, chartTitle);
		}
		
		resourceMap.put(fileName, chartTypeProps.getUrlForChart());
	}

	public void createCombinedIncidenceTimeSeriesChart(String filepath, Map<BigInteger, String> runIdSeriesLabels) {

		XYDataset dataset = createCombinedIncidenceDataset(timeSeriesContainerList, runIdSeriesLabels);
		JFreeChart chart = createCombinedIncidenceChart(dataset, COMBINED_INCIDENCE_CHART_TITLE,
				"simulation time step (days)", "");
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

	static {
		
		colorsForTimeSeriesCurveTypes = new EnumMap<TimeSeriesCurveTypeEnum, Color>(TimeSeriesCurveTypeEnum.class);
		
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.SUSCEPTIBLE, new Color(55, 83, 196)); // BLUE
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.LATENT, new Color(235, 33, 33)); // RED
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.INFECTIOUS, new Color(216, 56, 224)); // PURPLE
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.RECOVERED, new Color(5, 158, 61)); // GREEN
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.NEWLY_DECEASED, new Color(216, 56, 224)); // PURPLE
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.PROPHYLACTICS_GIVEN, new Color(55, 83, 196)); // BLUE
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.ASYMPTOMATIC, new Color(55, 83, 196)); // BLUE
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.SYMPTOMATIC, new Color(235, 33, 33)); // RED
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.FULMINANT, new Color(216, 56, 224)); // PURPLE
		colorsForTimeSeriesCurveTypes.put(TimeSeriesCurveTypeEnum.DEAD, Color.ORANGE); // ORANGE
		
	}
}

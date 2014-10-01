package edu.pitt.apollo.timeseriesvisualizer.types;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Sep 19, 2014
 * Time: 11:27:50 AM
 * Class: ChartTypeProperties
 */
public class ChartTypeProperties {

	private ChartTypeEnum chartTypeEnum;
	private String titleForChart;
	private String directoryForChart;
	private String fileNameForChart;
	private String urlForChart;

	/**
	 * @return the chartTypeEnum
	 */
	public ChartTypeEnum getChartTypeEnum() {
		return chartTypeEnum;
	}

	/**
	 * @param chartTypeEnum the chartTypeEnum to set
	 */
	public void setChartTypeEnum(ChartTypeEnum chartTypeEnum) {
		this.chartTypeEnum = chartTypeEnum;
	}

	/**
	 * @return the titleForChart
	 */
	public String getTitleForChart() {
		return titleForChart;
	}

	/**
	 * @param titleForChart the titleForChart to set
	 */
	public void setTitleForChart(String titleForChart) {
		this.titleForChart = titleForChart;
	}

	/**
	 * @return the urlForChart
	 */
	public String getUrlForChart() {
		return urlForChart;
	}

	/**
	 * @param urlForChart the urlForChart to set
	 */
	public void setUrlForChart(String urlForChart) {
		this.urlForChart = urlForChart;
	}

	/**
	 * @return the directoryForChart
	 */
	public String getDirectoryForChart() {
		return directoryForChart;
	}

	/**
	 * @param directoryForChart the directoryForChart to set
	 */
	public void setDirectoryForChart(String directoryForChart) {
		this.directoryForChart = directoryForChart;
	}

	/**
	 * @return the fileNameForChart
	 */
	public String getFileNameForChart() {
		return fileNameForChart;
	}

	/**
	 * @param fileNameForChart the fileNameForChart to set
	 */
	public void setFileNameForChart(String fileNameForChart) {
		this.fileNameForChart = fileNameForChart;
	}
	
}

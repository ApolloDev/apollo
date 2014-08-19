package edu.pitt.apollo.timeseriesvisualizer.types;

import java.util.ArrayList;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 15, 2014
 * Time: 5:09:36 PM
 * Class: TimeSeriesCurveTypeList
 */
public class TimeSeriesCurveTypeList extends ArrayList<TimeSeriesCurveTypeEnum> {

	private boolean containsPrevalenceCurveTypes;
	private boolean containsRatesCurveTypes;
	private boolean containsTreatmentCurveTypes;
	private String titleForPrevalenceChart;
	private String titleForRatesChart;
	private String titleForTreatmentChart;

	public TimeSeriesCurveTypeList() {
		super();
	}

	public void processAddedCurveTypes() {

		containsPrevalenceCurveTypes = false;
		for (TimeSeriesCurveTypeEnum enumVal : TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_PREVALENCE_CHART) {
			if (this.contains(enumVal)) {
				containsPrevalenceCurveTypes = true;
				break;
			}
		}

		containsRatesCurveTypes = false;
		for (TimeSeriesCurveTypeEnum enumVal : TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_RATES_CHART) {
			if (this.contains(enumVal)) {
				containsRatesCurveTypes = true;
				break;
			}
		}

		containsTreatmentCurveTypes = false;
		for (TimeSeriesCurveTypeEnum enumVal : TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_TREATMENT_COUNTS_CHART) {
			if (this.contains(enumVal)) {
				containsTreatmentCurveTypes = true;
				break;
			}
		}
	}
	
	public boolean listContainsPrevalenceCurveTypes() {
		return containsPrevalenceCurveTypes;
	}
	
	public boolean listContainsRatesCurveTypes() {
		return containsRatesCurveTypes;
	}
	
	public boolean listContainsTreatmentCurveTypes() {
		return containsTreatmentCurveTypes;
	}

	/**
	 * @return the titleForPrevalenceChart
	 */
	public String getTitleForPrevalenceChart() {
		return titleForPrevalenceChart;
	}

	/**
	 * @param titleForPrevalenceChart the titleForPrevalenceChart to set
	 */
	public void setTitleForPrevalenceChart(String titleForPrevalenceChart) {
		this.titleForPrevalenceChart = titleForPrevalenceChart;
	}

	/**
	 * @return the titleForRatesChart
	 */
	public String getTitleForRatesChart() {
		return titleForRatesChart;
	}

	/**
	 * @param titleForRatesChart the titleForRatesChart to set
	 */
	public void setTitleForRatesChart(String titleForRatesChart) {
		this.titleForRatesChart = titleForRatesChart;
	}

	/**
	 * @return the titleForTreatmentChart
	 */
	public String getTitleForTreatmentChart() {
		return titleForTreatmentChart;
	}

	/**
	 * @param titleForTreatmentChart the titleForTreatmentChart to set
	 */
	public void setTitleForTreatmentChart(String titleForTreatmentChart) {
		this.titleForTreatmentChart = titleForTreatmentChart;
	}
}

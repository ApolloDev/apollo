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
	private boolean containsIncidenceCurveTypes;
	private boolean containsTreatmentCurveTypes;
	private boolean containsNewlyDeceasedCurveTypes;
	private String titleForPrevalenceChart;
	private String titleForIncidenceChart;
	private String titleForTreatmentChart;
	private String titleForNewlyDeceasedChart;

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

		containsIncidenceCurveTypes = false;
		for (TimeSeriesCurveTypeEnum enumVal : TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_INCIDENCE_CHART) {
			if (this.contains(enumVal)) {
				containsIncidenceCurveTypes = true;
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

		containsNewlyDeceasedCurveTypes = false;
		for (TimeSeriesCurveTypeEnum enumVal : TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_NEWLY_DECEASED_CHART) {
			if (this.contains(enumVal)) {
				containsNewlyDeceasedCurveTypes = true;
				break;
			}
		}
	}

	public boolean listContainsPrevalenceCurveTypes() {
		return containsPrevalenceCurveTypes;
	}

	public boolean listContainsIncidenceCurveTypes() {
		return containsIncidenceCurveTypes;
	}

	public boolean listContainsTreatmentCurveTypes() {
		return containsTreatmentCurveTypes;
	}

	public boolean listContainsNewlyDeceasedCurveTypes() {
		return containsNewlyDeceasedCurveTypes;
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
	 * @return the titleForIncidenceChart
	 */
	public String getTitleForIncidenceChart() {
		return titleForIncidenceChart;
	}

	/**
	 * @param titleForIncidenceChart the titleForIncidenceChart to set
	 */
	public void setTitleForIncidenceChart(String titleForIncidenceChart) {
		this.titleForIncidenceChart = titleForIncidenceChart;
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

	/**
	 * @return the titleForNewlyDeceasedChart
	 */
	public String getTitleForNewlyDeceasedChart() {
		return titleForNewlyDeceasedChart;
	}

	/**
	 * @param titleForNewlyDeceasedChart the titleForNewlyDeceasedChart to set
	 */
	public void setTitleForNewlyDeceasedChart(String titleForNewlyDeceasedChart) {
		this.titleForNewlyDeceasedChart = titleForNewlyDeceasedChart;
	}
}

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

	private boolean containsInfectionStateCurveTypes;
	private boolean containsIncidenceCurveTypes;
	private boolean containsTreatmentCurveTypes;
	private boolean containsNewlyDeceasedCurveTypes;
	private boolean containsDiseaseStateCurveTypes;
	private String titleForInfectionStatesChart;
	private String titleForIncidenceChart;
	private String titleForTreatmentChart;
	private String titleForNewlyDeceasedChart;
	private String titleForDiseaseStatesChart;

	public TimeSeriesCurveTypeList() {
		super();
	}

	public void processAddedCurveTypes() {

		containsInfectionStateCurveTypes = false;
		for (TimeSeriesCurveTypeEnum enumVal : TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_INFECTION_STATES_CHART) {
			if (this.contains(enumVal)) {
				containsInfectionStateCurveTypes = true;
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

		containsDiseaseStateCurveTypes = false;
		for (TimeSeriesCurveTypeEnum enumVal : TimeSeriesCurveTypeEnum.CURVE_TYPES_FOR_DISEASE_STATES_CHART) {
			if (this.contains(enumVal)) {
				containsDiseaseStateCurveTypes = true;
				break;
			}
		}

	}

	public boolean listContainsInfectionStateCurveTypes() {
		return containsInfectionStateCurveTypes;
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
	
	public boolean listContainsDiseaseStatesCurveTypes() {
		return containsDiseaseStateCurveTypes;
	}

	/**
	 * @return the titleForInfectionStatesChart
	 */
	public String getTitleForInfectionStatesChart() {
		return titleForInfectionStatesChart;
	}

	/**
	 * @param titleForInfectionStatesChart the titleForInfectionStatesChart to set
	 */
	public void setTitleForInfectionStatesChart(String titleForInfectionStatesChart) {
		this.titleForInfectionStatesChart = titleForInfectionStatesChart;
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

	/**
	 * @return the titleForDiseaseStatesChart
	 */
	public String getTitleForDiseaseStatesChart() {
		return titleForDiseaseStatesChart;
	}

	/**
	 * @param titleForDiseaseStatesChart the titleForDiseaseStatesChart to set
	 */
	public void setTitleForDiseaseStatesChart(String titleForDiseaseStatesChart) {
		this.titleForDiseaseStatesChart = titleForDiseaseStatesChart;
	}
}

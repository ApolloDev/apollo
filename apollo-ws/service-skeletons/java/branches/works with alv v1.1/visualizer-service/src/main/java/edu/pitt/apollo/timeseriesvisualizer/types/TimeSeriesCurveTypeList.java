package edu.pitt.apollo.timeseriesvisualizer.types;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;

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

	private final Map<ChartTypeEnum, ChartTypeProperties> propertiesForCharts;

	public TimeSeriesCurveTypeList() {
		super();
		propertiesForCharts = new EnumMap<ChartTypeEnum, ChartTypeProperties>(ChartTypeEnum.class);
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

	public ChartTypeProperties getChartTypePropertiesForChartType(ChartTypeEnum chartType) {
		return propertiesForCharts.get(chartType);
	}
	
	public void setChartTypePropertiesForChart(ChartTypeEnum chartType, ChartTypeProperties props) {
		propertiesForCharts.put(chartType, props);
	}
}

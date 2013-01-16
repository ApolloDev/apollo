package edu.pitt.apollo.client.wrapper;

import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.types.ControlMeasureTimeSeriesOutput;
import edu.pitt.apollo.types.EpidemicModelOutput;
import edu.pitt.apollo.types.PopulationTimeSeries;

public class EpidemicModelOutputHelper {

	private static String printPopulationTimeSeries(PopulationTimeSeries ts) {
		String result = "\n PopulationTimeSeries";
		for (int i = 0; i < ts.getSimulatedPopulation().size(); i++) {
			result += "\n    " + ts.getSimulatedPopulation().get(i) + "=[Array Length="
					+ ts.getPopCount().size() + "]"
					+ ts.getPopCount().get(i).toString();
		}
		return result;
	}

	public static String print(EpidemicModelOutput output) {
		return "EpidemicModelOutput"
				+ printPopulationTimeSeries(output.getPopulationTimeSeries())
				+ printControlMeasureTimeSeriesOutput(output
						.getControlMeasureTimeSeries());

	}

	private static String printControlMeasureTimeSeriesOutput(
			ControlMeasureTimeSeriesOutput o) {
		String result = "\n ControlMeasureTimeSeriesOutput "
				+ "\n   [receivedVaccination=" + "[Array Length="
				+ o.getReceivedVaccinationTimeSeries().size() + "]"
				+ o.getReceivedVaccinationTimeSeries().toString()
				+ "\n   receivedAntiviralTreatment="+ "[Array Length="
				+ +o.getReceivedAntiviralTreatmentTimeSeries().size() + "]"
				+ o.getReceivedAntiviralTreatmentTimeSeries().toString() + "]";
		return result;
	}

	public static double[] getIncidenceCurve(EpidemicModelOutput o,
			String infectiousCompartmentName, String recoveredCompartmentName) {
		int iIdx = -1, rIdx = -1;

		PopulationTimeSeries ts = o.getPopulationTimeSeries();
		int runLength = ts.getPopCount().size() / ts.getSimulatedPopulation().size();

		for (int i = 0; i < ts.getSimulatedPopulation().size(); i++) {
			String bin = ts.getSimulatedPopulation().get(i);
			if (bin.equalsIgnoreCase(infectiousCompartmentName))
				iIdx = i;
			if (bin.equalsIgnoreCase(recoveredCompartmentName))
				rIdx = i;
		}

		if ((iIdx == -1) || (rIdx == -1))
			return null;

		double[] result = new double[runLength];

		result[0] = 0d;
		List<Double> iArray = new ArrayList<Double>(runLength);
		List<Double> rArray = new ArrayList<Double>(runLength);
		int iStart = iIdx * runLength;
		int rStart = rIdx * runLength;
		
		for (int i = 0 ; i < runLength; i++) {
			iArray.add(ts.getPopCount().get(i + iStart));
			rArray.add(ts.getPopCount().get(i + rStart));
		}
		
		for (int i = 1; i < iArray.size(); i++) {
			result[i] = iArray.get(i) - iArray.get(i - 1) + rArray.get(i)
					- rArray.get(i - 1);
		}
		return result;
	}

}

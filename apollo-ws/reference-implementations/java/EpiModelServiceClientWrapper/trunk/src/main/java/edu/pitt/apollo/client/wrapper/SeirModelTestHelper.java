package edu.pitt.apollo.client.wrapper;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

import edu.pitt.apollo.types.ApolloFactory;
import edu.pitt.apollo.types.EpidemicModelInput;
import edu.pitt.apollo.types.SimulationRunResult;


public class SeirModelTestHelper {

//	public static void testRunBatch(URL wsdlURL, int numModels)
//			throws IOException {
//		SeirModelServiceWrapper wrapper = new SeirModelServiceWrapper(wsdlURL,
//				"/batch");
//		EpidemicModelInput input = SeirModelTestHelper.getDefaultObject();
//		List<EpidemicModelInput> list = new ArrayList<EpidemicModelInput>();
//
//		for (int i = 0; i < numModels; i++) {
//			list.add(input);
//		}
//
//		Iterator<EpidemicModelOutput> it = wrapper.runBatch(list);
//		while (it.hasNext()) {
//			EpidemicModelOutput o = it.next();
//			System.out.println(o.getPopulationTimeSeries().getPopulation()
//					.get(0));
//		}
//	}

	public static void testRun(URL wsdlURL) throws IOException {
		SeirModelServiceWrapper wrapper = new SeirModelServiceWrapper(wsdlURL,
				"/batch");
		EpidemicModelInput input = SeirModelTestHelper.getDefaultObject();
	
		System.out.println(EpidemicModelInputHelper.print(input));
		SimulationRunResult rr = wrapper.run(input);
		System.out.println(EpidemicModelOutputHelper.print(rr.getModelOutput()));
		System.out.println(rr.getModelOutput().getPopulationTimeSeries()
				.getSimulatedPopulation().get(0));
	}

	public static EpidemicModelInput getDefaultObject() {
		int runLength = 365;

		// noControlMeasureResults = new double[6 * runLength];
		// set_val(noControlMeasureResults, runLength, 0, 0, 1157474); //
		// initial susceptible
		// set_val(noControlMeasureResults, runLength, 1, 0, 0); // initial
		// exposed
		// set_val(noControlMeasureResults, runLength, 2, 0, 100); //
		// initial
		// infectious
		// set_val(noControlMeasureResults, runLength, 3, 0, 60920); //
		// initial
		// recovered
		// noControlMeasueParams->initial_compartment_sizes =
		// noControlMeasureResults;
		EpidemicModelInput input = ApolloFactory.createEpidemicModelInput();
		input.getDiseaseDynamics().getSimulatedPopulation().add("susceptible");
		input.getDiseaseDynamics().getSimulatedPopulation().add("exposed");
		input.getDiseaseDynamics().getSimulatedPopulation().add("infectious");
		input.getDiseaseDynamics().getSimulatedPopulation().add("recovered");

		input.getDiseaseDynamics().getPopCount().add(1157474d);
		input.getDiseaseDynamics().getPopCount().add(0d);
		input.getDiseaseDynamics().getPopCount().add(100d);
		input.getDiseaseDynamics().getPopCount().add(60920d);

		// noControlMeasueParams = new ModelParams();
		// noControlMeasueParams->antiviral_admin_supply_schedules = new
		// double[2 * runLength];
		for (int i = 0; i < runLength; i++) {
			input.getAntiviralControlMeasure().getAntiviralAdminSchedule().add(0d);
			input.getAntiviralControlMeasure().getAntiviralSupplySchedule().add(0d);
		}

		// noControlMeasueParams->antiviral_compliance = 0.0;
		input.getAntiviralControlMeasure().setAntiviralCmCompliance(0.0);
		// noControlMeasueParams->antiviral_efficacy = 0.0;
		input.getAntiviralControlMeasure().setAntiviralEfficacy(0.0);
		// noControlMeasueParams->antiviral_efficacy_delay = 0.0;
		input.getAntiviralControlMeasure().setAntiviralEfficacyDelay(0.0);

		// noControlMeasueParams->asymptomatic_infection_fraction = 0.5;
		input.getDiseaseDynamics().setAsymptomaticInfectionFraction(0.5);

		// noControlMeasueParams->infectious_period = 3.2;
		input.getDiseaseDynamics().setInfectiousPeriod(3.2);

		// noControlMeasueParams->latent_period = 2.0;
		input.getDiseaseDynamics().setLatentPeriod(2.0);

		// noControlMeasueParams->pop_count = 1218494;
		input.getSimulatorConfiguration().setPopCount(1218494d);

		// noControlMeasueParams->reproduction_number = 1.35;
		input.getDiseaseDynamics().setReproductionNumber(1.35d);

		// noControlMeasueParams->run_length = runLength;
		input.getSimulatorConfiguration().setRunLength(
				BigInteger.valueOf(runLength));

		// noControlMeasueParams->vacc_admin_supply_schedules = new double[2
		// *
		// runLength];
		for (int i = 0; i < runLength; i++) {
			input.getVaccinationControlMeasure().getVaccinationAdminSchedule().add(0d);
			input.getVaccinationControlMeasure().getVaccineSupplySchedule().add(0d);
		}

		// noControlMeasueParams->vaccine_compliance = 0.0;
		input.getVaccinationControlMeasure().setVaccineCmCompliance(0.0);

		// noControlMeasueParams->vaccine_efficacy = 0.0;
		input.getVaccinationControlMeasure().setVaccineEfficacy(0.0);

		// noControlMeasueParams->vaccine_efficacy_delay = 0.0;
		input.getVaccinationControlMeasure().setVaccineEfficacyDelay(0.0);

		return input;

	}
}

package edu.pitt.apollo.client.wrapper;

import java.util.List;

import edu.pitt.apollo.types.AntiviralControlMeasure;
import edu.pitt.apollo.types.Disease;
import edu.pitt.apollo.types.EpidemicModelInput;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SimulatorIdentification;
import edu.pitt.apollo.types.VaccinationControlMeasure;

public class EpidemicModelInputHelper {

	
	private static String printArray(List<Double> a) {
		String result = "[Array Length=" + a.size() + "] ";
		for (double d : a) {
		 result += d + " ";
		}
		return result;
	}
	
	private static String printSimulatorConfiguration(SimulatorConfiguration sc) {
		return "\n SimulatorConfiguration" 
				+ "\n   [timeStepUnit=" + sc.getTimeStepUnit()
				+ "\n    timeStepValue=" + sc.getTimeStepValue() 
				+ "\n    runLength=" + sc.getRunLength() 
				+ "\n    popCount=" + sc.getPopCount() 
				+ "\n    disease=" + sc.getDisease()
		        + "\n    populationLocation=" + sc.getPopulationLocation();
	}
	
	private static String printSimulatorIdentification(SimulatorIdentification si) {
		return "\n SimulatorIdentification"
				+ "\n   [simulatorDeveloper=" + si.getSimulatorDeveloper() 
				+ "\n    simulatorName=" + si.getSimulatorName() 
				+ "\n    simulatorVersion=" + si.getSimulatorVersion()  + "]";
		}
	
	private static String printDisease(Disease d) {
		return "\n Disease "
				+ "\n   [infectiousPeriod=" + d.getInfectiousPeriod()					
				+ "\n   latentPeriod=" + d.getLatentPeriod() 
				+ "\n   reproductionNumber=" + d.getReproductionNumber() 
				+ "\n   asymptomaticInfectionFraction=" + d.getAsymptomaticInfectionFraction()
				+ "\n   population=" + d.getSimulatedPopulation()
				+ "\n   popCount=" + d.getPopCount() + "]";
		

	}

	private static String printAntiviralControlMeasure(AntiviralControlMeasure acm) {
		return "\n AntiviralControlMeasure" 
				+ "\n   [antiviralCmCompliance=" + acm.getAntiviralCmCompliance() 
				+ "\n    antiviralSupplySchedule=" + printArray(acm.getAntiviralSupplySchedule()) 
				+ "\n    antiviralAdminSchedule=" + printArray(acm.getAntiviralAdminSchedule())
				+ "\n    antiviralEfficacy=" + acm.getAntiviralEfficacy()
				+ "\n    antiviralEfficacyDelay=" + acm.getAntiviralEfficacyDelay() + "]";
	}
	
	private static String printVaccControlMeasure(VaccinationControlMeasure vcm) {
		
		return "\n VaccinationControlMeasure" 
				+ "\n   [vaccineCmCompliance="+ vcm.getVaccineCmCompliance() 
				+ "\n    vaccineSupplySchedule=" + printArray(vcm.getVaccineSupplySchedule()) 
				+ "\n    vaccinationAdminSchedule="	+ printArray(vcm.getVaccinationAdminSchedule())  
				+ "\n    vaccineEfficacy=" + vcm.getVaccineEfficacy() 
				+ "\n    vaccineEfficacyDelay="	+ vcm.getVaccineEfficacyDelay() + "]";
	}
	
	public static String print(EpidemicModelInput input) {
		return "EpidemicModelInput"
				+ printSimulatorIdentification(input.getSimulatorIdentification())
				+ printSimulatorConfiguration(input.getSimulatorConfiguration())
				+ printDisease(input.getDiseaseDynamics())
				+ printAntiviralControlMeasure(input.getAntiviralControlMeasure())
				+ printVaccControlMeasure(input.getVaccinationControlMeasure());

	}

}

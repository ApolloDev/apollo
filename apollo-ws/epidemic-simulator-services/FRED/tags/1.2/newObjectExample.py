'''
Created on Mar 25, 2013

@author: jdl50 and stbrown
'''
    
from SimulatorService_services_types import *
import inspect
from ApolloFactory import *

def constant(f):
    def fset(self, value):
        raise SyntaxError
    def fget(self):
        return f()
    return property(fget, fset)

class _Const(object):
    @constant
    def SOFTWARE_DEVELOPER():
        return "UPitt,CMU,PSC"
    @constant
    def SOFTWARE_NAME():
        return "FRED"
    @constant
    def SOFTWARE_VERSION():
        return "1.0"
    @constant
    def SOFTWARE_TYPE():
        return "SIMULATOR"
    
    @constant
    def FRAC_S():
        return 0.9
    @constant
    def FRAC_E():
        return 0.01
    @constant
    def FRAC_I():
        return 0.01
    @constant
    def FRAC_R():
        return 0.08
    @constant
    def RUN_LEN():
        return 150
    
    
    
CONST = _Const()
factory = ApolloFactory()
 
class newObjectsExample(object):   
    def noControlMeasures(self, location):
        cfg = factory.new_SimulatorConfiguration();
        
        cfg._authentication._requesterId = "Example User"
        cfg._authentication._requesterPassword = "Example Password"
        
        cfg._simulatorIdentification._softwareDeveloper = CONST.SOFTWARE_DEVELOPER
        cfg._simulatorIdentification._softwareName = CONST.SOFTWARE_NAME
        cfg._simulatorIdentification._softwareVersion = CONST.SOFTWARE_VERSION
        cfg._simulatorIdentification._softwareType = CONST.SOFTWARE_TYPE
        
        cfg._populationInitialization._populationLocation = location
        pds_s = factory.new_PopulationDiseaseState();
        pds_s._diseaseState = 'susceptible'
        pds_s._fractionOfPopulation = CONST.FRAC_S
        pds_e = factory.new_PopulationDiseaseState();
        pds_e._diseaseState = 'exposed'
        pds_e._fractionOfPopulation = CONST.FRAC_E
        pds_i = factory.new_PopulationDiseaseState();
        pds_i._diseaseState ='infectious'
        pds_i._fractionOfPopulation = CONST.FRAC_I
        pds_r = factory.new_PopulationDiseaseState();
        pds_r._diseaseState ='recovered'
        pds_r._fractionOfPopulation = CONST.FRAC_R
        pds = cfg._populationInitialization._populationDiseaseState
        pds.append(pds_r)
        pds.append(pds_i)
        pds.append(pds_e)
        pds.append(pds_s)
       
        cfg._disease._diseaseName = "Influenza"
        cfg._disease._infectiousPeriod = 3.2    
        cfg._disease._latentPeriod = 2.0
        cfg._disease._reproductionNumber = 1.7
        cfg._disease._asymptomaticInfectionFraction = 0.5

        cfg._simulatorTimeSpecification._timeStepUnit = "days"
        cfg._simulatorTimeSpecification._timeStepValue = 1
        cfg._simulatorTimeSpecification._runLength = CONST.RUN_LEN
        
        return cfg
    
    def addFixedStartTimeVaccinationControlMeasure(self, cfg, prioritizationScheme):      
        vaccine = factory.new_Vaccine()
        vaccine._description = "H1N1 univalent vaccine"
        vaccine._vaccineId = "H1N1"
        vaccine._valence = [1]
        
        vaccination = factory.new_Vaccination()
        vaccination._description = "single-dose vaccination of human with univalent H1N1 vaccine"
        vaccination._vaccineId = "H1N1"
        vaccination._hostOrganism = "human"
        vaccination._numDosesInVaccinationCourse = 1
        vaccination._vaccinationEfficacy = factory.new_TreatmentEfficacyOverTime()
        vaccination._vaccinationEfficacy._efficacyValues = [0.7]
        
        vaccination_control_measure = factory.new_VaccinationControlMeasure()
        vaccination_control_measure._description = vaccination._description
        vaccination_control_measure._controlMeasureResponseDelay = 0
        vaccination_control_measure._controlMeasureCompliance = 0.9
        vaccination_control_measure._controlMeasureTargetPopulationsAndPrioritization = None
        vaccination_control_measure._controlMeasureNamedPrioritizationScheme = prioritizationScheme
        vaccination_control_measure._vaccination = vaccination
        vaccination_control_measure._vaccinationReactiveEndPointFraction = 1.0
        vaccination_control_measure._vaccineSupplySchedule = [5000] * CONST.RUN_LEN
        vaccination_control_measure._vaccinationAdministrationCapacity = [5000] * CONST.RUN_LEN
        
        fixed_start_time_vaccination_control_measure = factory.new_FixedStartTimeControlMeasure()
        fixed_start_time_vaccination_control_measure._controlMeasureFixedStartTime._fixedStartTime = 0;
        fixed_start_time_vaccination_control_measure._controlMeasure = vaccination_control_measure
        
        cfg._controlMeasures._fixedStartTimeControlMeasures.append(fixed_start_time_vaccination_control_measure)
        return cfg
    
    def addFixedStartTimeAntiviralControlMeasure(self, cfg):
        antiviral = factory.new_Antiviral()
        antiviral._description = "Tamiflu tablets or IV"
        antiviral._antiviralId = "tamiflu"

	antiviral_treatment_efficacy_over_time = factory.new_TreatmentEfficacyOverTime()
	
	print dir(antiviral_treatment_efficacy_over_time)
        
        antiviral_treatment = factory.new_AntiviralTreatment()
        antiviral_treatment._description = "treatment of newly symptomatic influenza cases with Tamiflu to reduce infectiousness"
        antiviral_treatment._antiviralId = "tamiflu"
        antiviral_treatment._hostOrganism = "human"
        antiviral_treatment._numDosesInTreatmentCourse = 10
        antiviral_treatment._antiviralTreatmentEfficacy = factory.new_TreatmentEfficacyOverTime()
        antiviral_treatment._antiviralTreatmentEfficacy._efficacyValues = [0.7]
        
        target_priority_population = factory.new_TargetPriorityPopulation()
        target_priority_population._label = "newly sick"
        ##next line should be ignored...but i have to set it anyway
        target_priority_population._fractionOfTargetPopulationToPrioritize = 1.0
        target_priority_population._priority = 1
        target_priority_population._targetPopulationDefinition = factory.new_PopulationStrataDefinition()    
        target_priority_population._targetPopulationDefinition._diseaseStates = ["newly_sick"]
        
        antiviral_treatment_control_measure = factory.new_AntiviralControlMeasure()
        antiviral_treatment_control_measure._description = "insert description above, but the object has the antiviralTreatment object"
        antiviral_treatment_control_measure._controlMeasureResponseDelay = 0
        antiviral_treatment_control_measure._controlMeasureCompliance = 0.8
        antiviral_treatment_control_measure._controlMeasureTargetPopulationsAndPrioritization = [target_priority_population]
        #antiviral_treatment_control_measure._controlMeasureNamedPrioritizationScheme = None
        antiviral_treatment_control_measure._antiviralTreatment = antiviral_treatment
        antiviral_treatment_control_measure._treatmentReactiveEndPointFraction = 1.0
        antiviral_treatment_control_measure._antiviralSupplySchedule = [2000] * 150
        antiviral_treatment_control_measure._antiviralTreatmentAdministrationCapacity = [2000] * 150
        
        fixed_start_time_antiviral_treatment_control_measure = factory.new_FixedStartTimeControlMeasure()
        fixed_start_time_antiviral_treatment_control_measure._controlMeasureFixedStartTime._fixedStartTime = 0;
        fixed_start_time_antiviral_treatment_control_measure._controlMeasure = antiviral_treatment_control_measure
        
        cfg._controlMeasures._fixedStartTimeControlMeasures.append(fixed_start_time_antiviral_treatment_control_measure)
        return cfg
        
    
    def addReactiveAllSchoolClosureControlMeasure(self, cfg):
        school_closure_control_measure = factory.new_SchoolClosureControlMeasure()
        school_closure_control_measure._schoolClosureDuration = 56
        #this means that all schools are involved in the control measure, not that you want to close all schools when the trigger fires
        school_closure_control_measure._schoolClosureTargetFacilities = "all"
        school_closure_control_measure._description = "Each school within the location of the simulation "\
						      +"closes when the disease incidence in its ??Student/student and faculty?? "\
						      +"population exceeds a threshold.  A school stays closed for 8 weeks"
        school_closure_control_measure._controlMeasureResponseDelay = 2
        school_closure_control_measure._controlMeasureCompliance = 1.0
        school_closure_control_measure._controlMeasureNamedPrioritizationScheme = "None"

        control_measure_reactive_trigger_definition = factory.new_ReactiveTriggersDefinition()
	control_measure_reactive_trigger_definition._reactiveControlMeasureTest = "all"
	control_measure_reactive_trigger_definition._reactiveControlMeasureThreshold = 0.03
	control_measure_reactive_trigger_definition._ascertainmentFraction = 1.0
	control_measure_reactive_trigger_definition._ascertainmentDelay = 0
	
        reactive_school_closure_control_measure = factory.new_ReactiveControlMeasure()
	reactive_school_closure_control_measure._controlMeasureReactiveTriggersDefinition = control_measure_reactive_trigger_definition
        reactive_school_closure_control_measure._controlMeasure = school_closure_control_measure
	cfg._controlMeasures._reactiveControlMeasures.append(reactive_school_closure_control_measure)
        return cfg
        
    def addFixedStartTimeAllSchoolClosureControlMeasure(self, cfg):
        school_closure_control_measure = factory.new_SchoolClosureControlMeasure()
        school_closure_control_measure._schoolClosureDuration = 56
        #this means that all schools are involved in the control measure, not that you want to close all schools when the trigger fires
        school_closure_control_measure._schoolClosureTargetFacilities = "all"
        school_closure_control_measure._description = "All schools within the location of the simulation close when "\
						      +"the disease incidence in the student/faculty?? population of "\
						      +"the location exceeds a threshold.  The schools stay closed for 8 weeks"
        school_closure_control_measure._controlMeasureResponseDelay = 2
        school_closure_control_measure._controlMeasureCompliance = 1.0
        school_closure_control_measure._controlMeasureNamedPrioritizationScheme = "None"
        
        fixed_start_time_school_closure_control_measure = factory.new_FixedStartTimeControlMeasure()
        fixed_start_time_school_closure_control_measure._controlMeasureFixedStartTime._fixedStartTime = 0;
        fixed_start_time_school_closure_control_measure._controlMeasure = school_closure_control_measure
        
        cfg._controlMeasures._fixedStartTimeControlMeasures.append(fixed_start_time_school_closure_control_measure)
        return cfg

 
        
if __name__ == "__main__":
    j  = newObjectsExample()
    
    #build the base case, no control measures run for allegheny county, and the next line, for the USA
    baseCfgAllegheny = j.noControlMeasures(42003);
    baseCfgUSA = j.noControlMeasures("US")
    
    #create a base case for allegheny, and add ACIP vaccination control measure to it
    vaccCfgAcipAllegheny = j.addFixedStartTimeVaccinationControlMeasure(j.noControlMeasures("42003"), "ACIP")
  
    #create a base case for allegheny, and add no prioritization vaccination control measure to it
    vaccCfgNoPrioritizationAllegheny = j.addFixedStartTimeVaccinationControlMeasure(j.noControlMeasures("42003"), "None")
    
    #create a base case for allegheny, and add an antiviral control measure to it
    antiviralCfgAllegheny = j.addFixedStartTimeAntiviralControlMeasure(j.noControlMeasures("42003"))
    
    #create a base case for allegheny, and add a fixed start time school closure control measure to it
    fixedStartTimeAllSchoolClosureCfgAllegheny = j.addFixedStartTimeAllSchoolClosureControlMeasure(j.noControlMeasures("42003"))
    
    #create a base case for allegheny, and add a reactive school closure control measure to it
    reactiveAllSchoolClosureCfgAllegheny = j.addReactiveAllSchoolClosureControlMeasure(j.noControlMeasures("42003"))
    

    
    
    
    
    
    
    

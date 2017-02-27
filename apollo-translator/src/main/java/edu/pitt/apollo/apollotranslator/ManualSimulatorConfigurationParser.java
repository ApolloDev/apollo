package edu.pitt.apollo.apollotranslator;

import edu.pitt.apollo.apollotranslator.exception.ApolloSetterException;
import edu.pitt.apollo.apollotranslator.setters.*;
import edu.pitt.apollo.apollotranslator.types.translator.SetterReturnObject;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_1.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v4_0_1.SimulatorTimeSpecification;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ManualSimulatorConfigurationParser {

    RunSimulationMessage sc = null;
    ApolloTranslationEngine apolloTranslationEngine;
    List<SetterReturnObject> setterReturnObjects = new ArrayList<SetterReturnObject>();

    public List<SetterReturnObject> getSetterReturnObjects() {

//        postProcessSetterReturnObjects();

        return setterReturnObjects;
    }

    public ManualSimulatorConfigurationParser(RunSimulationMessage sc,
            ApolloTranslationEngine apolloTranslationEngine) {
        super();
        this.sc = sc;
        this.apolloTranslationEngine = apolloTranslationEngine;
    }

    public List<String> ensureAllInstructionsParsed() {
        return apolloTranslationEngine.ensureAllInstructionsUsed();

    }

    public Map<String, List<SetterReturnObject>> getApolloLabelSetterReturnObjectsMap() {

        return postProcessSetterReturnObjects();
    }

    private Map<String, List<SetterReturnObject>> postProcessSetterReturnObjects() {

        // this will create a map of apollo labels to a list of setter return obejcts
        // LinkedHashMap keeps the keys in the order they are inserted
        Map<String, List<SetterReturnObject>> apolloLabelSroMap = new LinkedHashMap<String, List<SetterReturnObject>>();
        for (SetterReturnObject sro : setterReturnObjects) {
            String label = sro.getApolloParameter();
            List<SetterReturnObject> sroList;
            if (label == null) {
                label = AbstractSetter.UNSPECIFIED_PARAM_SECTION;
            }
            if (apolloLabelSroMap.containsKey(label)) {
                sroList = apolloLabelSroMap.get(label);
            } else {
                sroList = new ArrayList<SetterReturnObject>();
                apolloLabelSroMap.put(label, sroList);
            }
            sroList.add(sro);
        }

        return apolloLabelSroMap;
    }

    public List<String> parse() throws ApolloSetterException {

//        parseAuthentication(sc.getAuthentication());
//        parseSimulatorIdentification(sc.getSimulatorIdentification());
        parseInfectiousDiseaseScenario(sc.getInfectiousDiseaseScenario());
//        parseLocation(sc.getInfectiousDiseaseScenario().getLocation());
//        parseInfections(sc.getInfectiousDiseaseScenario().getInfections());
//        parseDiseases(sc.getInfectiousDiseaseScenario().getDiseases());
        parseSimulatorTimeSpecification(sc.getSimulatorTimeSpecification());
//        parsePopulationInfectionAndImmunityCensuses(sc.getInfectiousDiseaseScenario().getPopulationInfectionAndImmunityCensuses());
//
//        parseControlMeasures(sc.getInfectiousDiseaseScenario().getInfectiousDiseaseControlStrategies());

        parseNonApolloParameters();
        return ensureAllInstructionsParsed();
    }

    private void sroAddAll(SetterReturnObject sro) throws ApolloSetterException {
        if (sro == null) {
            throw new ApolloSetterException("A null setter return object was attempted to be added to the setter return object list");
        }

        if (setterReturnObjects.contains(sro)) {
            throw new ApolloSetterException("A setter return object (" + sro.getApolloParameter() + ") was attempted to be added to the setter return object list but"
                    + " which has an exact match already in the list (according to the SetterReturnObject equals() method)");
        }

        setterReturnObjects.add(sro);
    }

    private void sroAddAll(List<SetterReturnObject> sroList) throws ApolloSetterException {
        for (int i = 0; i < sroList.size(); i++) {
            sroAddAll(sroList.get(i));
        }
    }
    
//    private void parseLocation(LocationDefinition location) throws ApolloSetterException {
//        // the type and section should be made final variables, possibly move all sections and type declarations to this class
//        LocationDefinitionSetter setter = new LocationDefinitionSetter("location", "LOCATIONS SECTION", apolloTranslationEngine);
//        sroAddAll(setter.set(location));
//    }

    private void parseInfectiousDiseaseScenario(InfectiousDiseaseScenario scenario) throws ApolloSetterException {
        
        InfectiousDiseaseScenarioSetter setter = new InfectiousDiseaseScenarioSetter(apolloTranslationEngine);
        sroAddAll(setter.set(scenario));
    }
    
    // John
    private void parseSimulatorIdentification(
            SoftwareIdentification simulatorIdentification)
            throws ApolloSetterException {
        SimulatorIdentificationSetter sis = new SimulatorIdentificationSetter(
                apolloTranslationEngine);

        sroAddAll(sis.setSoftwareDeveloper(sc.getSoftwareIdentification().getSoftwareDeveloper()));
        sroAddAll(sis.setSoftwareName(sc.getSoftwareIdentification().getSoftwareName()));
        sroAddAll(sis.setSoftwareVersion(sc.getSoftwareIdentification().getSoftwareVersion()));
        sroAddAll(sis.setSoftwareType(sc.getSoftwareIdentification().getSoftwareType().toString()));
    }

    private void parseNonApolloParameters() throws ApolloSetterException {
        NonScenarioParameterSetter naps = new NonScenarioParameterSetter(
                apolloTranslationEngine);
        sroAddAll(naps.set("dummy"));
    }

    // Nick
    private void parseSimulatorTimeSpecification(
            SimulatorTimeSpecification simulatorTimeSpecification)
            throws ApolloSetterException {
        SimulatorTimeSpecificationSetter stss = new SimulatorTimeSpecificationSetter(
                apolloTranslationEngine);
//        sroAddAll(stss.setRunLength(simulatorTimeSpecification.getRunLength()));
//
//        sroAddAll(stss.setTimeStepUnit(simulatorTimeSpecification.getTimeStepUnit()));
//
//        sroAddAll(stss.setTimeStepValue(simulatorTimeSpecification.getTimeStepValue()));

        sroAddAll(stss.set(simulatorTimeSpecification));
    }

//    private void parseControlMeasures(List<InfectiousDiseaseControlMeasure> controlMeasures) throws ApolloSetterException {
//
////        InfectiousDiseaseControlStrategiesSetter cms = new InfectiousDiseaseControlStrategiesSetter(apolloTranslationEngine);
////        sroAddAll(cms.set(controlMeasures));
//
////        List<IndividualTreatmentControlMeasure> individualTreatmentControlStrategies = new ArrayList<IndividualTreatmentControlMeasure>();
////        //List<AntiviralTreatmentControlMeasure> antiviralTreatmentControlMeasures = new ArrayList<AntiviralTreatmentControlMeasure>();
////        List<SchoolClosureControlMeasure> schoolClosureControlMeasures = new ArrayList<SchoolClosureControlMeasure>();
////        List<InfectiousDiseaseControlMeasure> namedControlMeasures = new ArrayList<InfectiousDiseaseControlMeasure>();
//
////		// not even sure we use this stuff...looks like it gets handled in
////		// "parseVaccinationControlMeasures...etc"
////		for (InfectiousDiseaseControlMeasure cm : controlMeasures) {
////			if (cm instanceof IndividualTreatmentControlMeasure) {
////				individualTreatmentControlStrategies
////						.add((IndividualTreatmentControlMeasure) cm);
////			} else if (cm instanceof SchoolClosureControlMeasure) {
////				schoolClosureControlMeasures
////						.add((SchoolClosureControlMeasure) cm);
////				// } else if (cm instanceof AntiviralTreatmentControlMeasure) {
////				// antiviralTreatmentControlMeasures.add((AntiviralTreatmentControlMeasure)
////				// cm);
////			} else {
////				// the control measure must be a named control measure
////				namedControlMeasures.add(cm);
////			}
////
////		}
//
//        //parseVaccinationControlMeasures(sc.getControlMeasures().getControlMeasures());
//        parseIndividualTreatmentControlStrategies(controlMeasures);
//        //parseAntiviralTreatmentControlMeasures(sc.getControlMeasures().getControlMeasures());
//        parseSchoolClosureControlMeasures(controlMeasures);
//        parseNamedControlMeasures(controlMeasures);
//    }

//    private void parseAntiviralTreatmentControlMeasures(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
////        AntiviralTreatmentControlMeasureSetter atcms = new AntiviralTreatmentControlMeasureSetter(apolloTranslationEngine);
//        ListSetter setter = new ListSetter(AntiviralTreatmentControlMeasureSetter.class, AntiviralTreatmentControlMeasure.class,
//                cmList, apolloTranslationEngine, AntiviralTreatmentControlMeasureSetter.SECTION, AbstractControlMeasureSetter.TYPE_PREFIX);
//
//        sroAddAll(setter.set());
//    }
//
//    private void parseIndividualTreatmentControlStrategies(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
////        VaccinationControlMeasureSetter vcms = new VaccinationControlMeasureSetter(
////                apolloTranslationEngine);
//
//        ListSetter setter = new ListSetter(IndividualTreatmentControlMeasureSetter.class, IndividualTreatmentControlMeasure.class,
//                cmList, apolloTranslationEngine, IndividualTreatmentControlMeasureSetter.SECTION, InfectiousDiseaseControlMeasureSetter.TYPE_PREFIX);
//
//        sroAddAll(setter.set());
//    }
//
//    private void parseSchoolClosureControlMeasures(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
////        SchoolClosureControlMeasureSetter sccms = new SchoolClosureControlMeasureSetter(apolloTranslationEngine);
//        ListSetter setter = new ListSetter(SchoolClosureControlMeasureSetter.class, SchoolClosureControlMeasure.class,
//                cmList, apolloTranslationEngine, SchoolClosureControlMeasureSetter.SECTION, InfectiousDiseaseControlMeasureSetter.TYPE_PREFIX);
//        sroAddAll(setter.set());
//    }
//
//    private void parseNamedControlMeasures(List<InfectiousDiseaseControlMeasure> cmList) throws ApolloSetterException {
////        NamedControlMeasureSetter ncms = new NamedControlMeasureSetter(apolloTranslationEngine);
//        ListSetter setter = new ListSetter(NamedControlMeasureSetter.class, InfectiousDiseaseControlMeasure.class, cmList, apolloTranslationEngine,
//                NamedControlMeasureSetter.SECTION, InfectiousDiseaseControlMeasureSetter.TYPE_PREFIX);
//
//        sroAddAll(setter.set());
//    }

//    // John
//    private void parsePopulationInfectionAndImmunityCensuses(
//            List<PopulationInfectionAndImmunityCensus> censuses)
//            throws ApolloSetterException {
//        ListSetter setter = new ListSetter(PopulationInfectionAndImmunityCensusSetter.class,
//                PopulationInfectionAndImmunityCensus.class, censuses, apolloTranslationEngine,
//                PopulationInfectionAndImmunityCensusSetter.SECTION, PopulationInfectionAndImmunityCensusSetter.PIAICS_PREFIX);
//
//        sroAddAll(setter.set());
////        PopulationInfectionAndImmunityCensusSetter pis = new PopulationInfectionAndImmunityCensusSetter(
////                "populationInfectionAndImmunityCensus", apolloTranslationEngine);
////
////        sroAddAll(pis.set(populationInitialization));
//    }

    // John
//    private void parseInfections(List<Infection> infections)
//            throws ApolloSetterException {
//        ListSetter setter = new ListSetter(InfectionSetter.class, Infection.class, infections, apolloTranslationEngine,
//                InfectionSetter.SECTION, InfectionSetter.TYPE_PREFIX);
//
//        sroAddAll(setter.set());
//    }
//    
//    private void parseDiseases(List<InfectiousDisease> diseases)
//            throws ApolloSetterException {
//        ListSetter setter = new ListSetter(InfectiousDiseaseSetter.class, InfectiousDisease.class, diseases, apolloTranslationEngine,
//                InfectiousDiseaseSetter.SECTION, InfectiousDiseaseSetter.TYPE_PREFIX);
//
//        sroAddAll(setter.set());
//    }

    // John
    private void parseAuthentication(Authentication authentication)
            throws ApolloSetterException {
        AuthenticationSetter as = new AuthenticationSetter("authentication", "AUTHENTICATION",
                apolloTranslationEngine);

        sroAddAll(as.set(authentication));

    }
//    private void parseControlMeasure(AbstractControlMeasureSetter setter,
//            ControlMeasure cm) throws ApolloSetterException {
//
//        sroAddAll(setter.setControlMeasureDescription(cm.getDescription()));
//
//        ControlMeasureStartTime startTime = cm.getControlMeasureStartTime();
//        setterReturnObjects.addAll(setter.setControlMeasureStartTime(startTime));
//
//        sroAddAll(setter.setControlControlMeasureResponseDelay(cm.getControlMeasureResponseDelay()));
//
//        sroAddAll(setter.setControlMeasureCompliance(cm.getControlMeasureCompliance()));
//
//        if (cm.getControlMeasureReactiveEndPointFraction() != null) {
//            sroAddAll(setter.setControlMeasureReactiveEndPointFraction(cm.getControlMeasureReactiveEndPointFraction()));
//        }
//
//        sroAddAll(setter.setControlMeasureTargetPopulationsAndPrioritization(cm.getControlMeasureTargetPopulationsAndPrioritization()));
//        sroAddAll(setter.setControlMeasureNamedPrioritizationScheme(cm.getControlMeasureNamedPrioritizationScheme()));
//
//    }
}

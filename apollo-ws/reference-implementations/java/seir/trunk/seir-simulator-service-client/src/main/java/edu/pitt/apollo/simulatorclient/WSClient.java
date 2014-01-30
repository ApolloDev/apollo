/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.pitt.apollo.simulatorclient;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import edu.pitt.apollo.service.simulatorservice._10._28._2013.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice._10._28._2013.SimulatorServiceV131;
import edu.pitt.apollo.types._10._28._2013.ApolloSoftwareType;
import edu.pitt.apollo.types._10._28._2013.Authentication;
import edu.pitt.apollo.types._10._28._2013.ControlMeasures;
import edu.pitt.apollo.types._10._28._2013.DiseaseState;
import edu.pitt.apollo.types._10._28._2013.FixedStartTime;
import edu.pitt.apollo.types._10._28._2013.Infection;
import edu.pitt.apollo.types._10._28._2013.InfectionAcquisition;
import edu.pitt.apollo.types._10._28._2013.InfectiousDisease;
import edu.pitt.apollo.types._10._28._2013.LocationDefinition;
import edu.pitt.apollo.types._10._28._2013.PathogenTaxonID;
import edu.pitt.apollo.types._10._28._2013.PopulationDiseaseCensus;
import edu.pitt.apollo.types._10._28._2013.PopulationDiseaseCensusResult;
import edu.pitt.apollo.types._10._28._2013.PopulationStrataArray;
import edu.pitt.apollo.types._10._28._2013.PopulationStrataDefinition;
import edu.pitt.apollo.types._10._28._2013.ReactiveStartTime;
import edu.pitt.apollo.types._10._28._2013.ReactiveTriggersDefinition;
import edu.pitt.apollo.types._10._28._2013.SimulatorConfiguration;
import edu.pitt.apollo.types._10._28._2013.SimulatorTimeSpecification;
import edu.pitt.apollo.types._10._28._2013.SoftwareIdentification;
import edu.pitt.apollo.types._10._28._2013.TemporalDiseaseParameter;
import edu.pitt.apollo.types._10._28._2013.TimeStepUnit;
import edu.pitt.apollo.types._10._28._2013.Treatment;
import edu.pitt.apollo.types._10._28._2013.Vaccination;
import edu.pitt.apollo.types._10._28._2013.VaccinationControlMeasure;
import edu.pitt.apollo.types._10._28._2013.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types._10._28._2013.VaccinationPreventableOutcome;
import edu.pitt.apollo.types._10._28._2013.Vaccine;
import java.util.List;

public class WSClient {

    public static void main(String[] args) throws JsonGenerationException,
            JsonMappingException, IOException {
        SimulatorServiceV131 service = new SimulatorServiceV131(
                new URL(
                "http://localhost:8080/seirsimulatorservice1.3.1/services/seirsimulatorservice?wsdl"));
        SimulatorServiceEI port = service.getSimulatorServiceEndpoint();

        int runLength = 30;

        //
        // ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
        //

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            builder.append(",").append("8000");
        }

        builder.deleteCharAt(0);

        Authentication auth = new Authentication();
        auth.setRequesterId("fake_user");
        auth.setRequesterPassword("preEpidemicVaccinationAdminSchedule=" + builder.toString());
        // srr.setAuthentication(auth);
        //
        // // ServiceRecord sr = new ServiceRecord();
        SoftwareIdentification si = new SoftwareIdentification();
        si.setSoftwareDeveloper("UPitt");
        si.setSoftwareName("Reed-Frost");
        si.setSoftwareVersion("1.3.1");
        si.setSoftwareType(ApolloSoftwareType.SIMULATOR);

        SimulatorConfiguration simulatorConfiguration = new SimulatorConfiguration();
        simulatorConfiguration.setAuthentication(auth);

        ControlMeasures controlMeasures = new ControlMeasures();

        Vaccine vaccine = new Vaccine();
        vaccine.setDescription("H1N1 univalent vaccine");
        vaccine.setVaccineIdentifier("H1N1");
        vaccine.getValence().add(BigInteger.valueOf(1l));

        Vaccination vaccination = new Vaccination();
        vaccination.setDescription("single-dose vaccination of human with univalent H1N1 vaccine");
        vaccination.setVaccine(vaccine);
        vaccination.setDescription("human");
        vaccination.setNumDosesInTreatmentCourse(BigInteger.valueOf(1l));

        VaccinationEfficacyForSimulatorConfiguration ve = new VaccinationEfficacyForSimulatorConfiguration();
        ve.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.INFECTION);
        ve.setDescription("Vaccination Efficacy (VE) for prevention of the outcome infected");
        ve.setStrainIdentifier("H1N1");
        ve.setTreatment(new Treatment());
        ve.setVaccineIdentifier("H1N1");
        ve.setAverageVaccinationEfficacy(0.7);
        vaccination.getVaccinationEfficacies().add(ve);

        VaccinationControlMeasure vaccinationControlMeasure = new VaccinationControlMeasure();
        vaccinationControlMeasure.setDescription("not important");
        ReactiveStartTime st = new ReactiveStartTime();
        ReactiveTriggersDefinition rt = new ReactiveTriggersDefinition();
        rt.setAscertainmentDelay(new BigInteger("3"));
        rt.setAscertainmentFraction(0.5);
        rt.setReactiveControlMeasureTest("not important");
        rt.setReactiveControlMeasureThreshold(0.01);
        st.setTrigger(rt);
        vaccinationControlMeasure.setControlMeasureStartTime(st);

        // vaccinationControlMeasure.getControlMeasureTargetPopulationsAndPrioritization().add(tpp);

        vaccinationControlMeasure.setControlMeasureResponseDelay(0d);
        vaccinationControlMeasure.setControlMeasureCompliance(0.9);
        // vaccinationControlMeasure.setControlMeasureNamedPrioritizationScheme(scheme);
        vaccinationControlMeasure.setVaccination(vaccination);
        vaccinationControlMeasure.setControlMeasureReactiveEndPointFraction(1d);
        List<Integer> vaccineSupplySchedule = vaccinationControlMeasure.getVaccineSupplySchedule();


        for (int i = 0; i < runLength; i++) {
            vaccineSupplySchedule.add(5000);
        }

        List<BigInteger> vaccinationAdministationCapacity = vaccinationControlMeasure.getVaccinationAdministrationCapacity();
        for (int i = 0; i < runLength; i++) {
            vaccinationAdministationCapacity.add(BigInteger.valueOf(5000));
        }

        controlMeasures.getControlMeasures().add(vaccinationControlMeasure);

        simulatorConfiguration.setControlMeasures(controlMeasures);


        simulatorConfiguration.setSimulatorIdentification(si);

        TemporalDiseaseParameter tdp = new TemporalDiseaseParameter();
        tdp.setTimeStepUnit(TimeStepUnit.DAY);
        tdp.setTimeStepValue(6d);

        TemporalDiseaseParameter tdp2 = new TemporalDiseaseParameter();
        tdp2.setTimeStepUnit(TimeStepUnit.DAY);
        tdp2.setTimeStepValue(2d);

        Infection disease = new Infection();
        disease.setProbabilityNeverSymptomatic(0.5);
        disease.setHostTaxonID("human");
        disease.setPathogenTaxonID(PathogenTaxonID.INFLUENZA);
        disease.setInfectiousPeriod(tdp);
        disease.setLatentPeriod(tdp2);
        InfectionAcquisition ia = new InfectionAcquisition();
        ia.setContaminatedMaterialID("unknown");
        ia.setInfectiousHostTaxonID("unknown");
        ia.setPathogenTaxonID(PathogenTaxonID.INFLUENZA);
        ia.setReproductionNumber(1.4);
        ia.setSusceptibleHostTaxonID("unknown");

        disease.getInfectionAcquisition().add(ia);

        simulatorConfiguration.setInfection(disease);

        PopulationDiseaseCensus pdc = new PopulationDiseaseCensus();
        PopulationDiseaseCensusResult pdcr = new PopulationDiseaseCensusResult();
        LocationDefinition ld = new LocationDefinition();
        ld.setDescription("blah");
        ld.getLocationsIncluded().add("42003");

        pdcr.setDescription("Fake Location");
        pdcr.setLocation(ld);

        PopulationStrataArray psa = new PopulationStrataArray();
        PopulationStrataDefinition psd = new PopulationStrataDefinition();
        psd.getDiseaseStates().add(DiseaseState.SUSCEPTIBLE);
        psd.getDiseaseStates().add(DiseaseState.EXPOSED);
        psd.getDiseaseStates().add(DiseaseState.INFECTIOUS);
        psd.getDiseaseStates().add(DiseaseState.RECOVERED);
        psa.setDescription(psd);
        psa.getValues().add(0.51);
        psa.getValues().add(0.05);
        psa.getValues().add(0.05);
        psa.getValues().add(0.39);
        pdcr.setPopulationStates(psa);
        pdc.getPopulationStates().add(pdcr);

        simulatorConfiguration.setPopulationInitialization(pdc);

        simulatorConfiguration.setSimulatorTimeSpecification(new SimulatorTimeSpecification());
        SimulatorTimeSpecification stc = simulatorConfiguration.getSimulatorTimeSpecification();
        stc.setRunLength(new BigInteger(String.valueOf(runLength)));
        stc.setTimeStepUnit(TimeStepUnit.DAY);
        stc.setTimeStepValue(1d);

        port.run(simulatorConfiguration);

    }
}
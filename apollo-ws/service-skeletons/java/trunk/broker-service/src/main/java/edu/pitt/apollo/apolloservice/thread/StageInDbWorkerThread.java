package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.JsonUtils;
import edu.pitt.apollo.JsonUtilsException;
import edu.pitt.apollo.Md5Utils;
import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.methods.run.AbstractRunMethod;
import edu.pitt.apollo.apolloservice.methods.run.RunMethod;
import edu.pitt.apollo.apolloservice.methods.run.RunMethodForSimulationAndVisualization;
import edu.pitt.apollo.apolloservice.methods.run.RunResultAndSimulationGroupId;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_0.*;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jdl50 on 4/8/15.
 */
public class StageInDbWorkerThread implements Runnable {


    private final RunSimulationsMessage message;

    static Logger logger = LoggerFactory.getLogger(StageInDbWorkerThread.class);
    private final SynchronizedStringBuilder batchInputsWithRunIdsStringBuilder;
    private final XMLGregorianCalendar scenarioDate;
    private final BigInteger batchRunId;
    private final BigInteger simulationGroupId;
    private final SoftwareIdentification simulatorIdentification;
    private final RunSimulationsThread.BooleanRef errorRef;
    private final RunSimulationsThread.CounterRef counterRef;
    Md5Utils md5Utils = new Md5Utils();
    JsonUtils jsonUtils = new JsonUtils();

    private Authentication authentication;

    private final String line;

    private class BatchConfigRecord {

        Double r0;
        Double infectiousPeriod;
        Double latentPeriod;
        Double percentSusceptible;
        Double percentExposed;
        Double percentInfectious;
        Double percentRecovered;
        int dayOfWeekOffset;

        public BatchConfigRecord(String[] columns) {
            // column 0 is the ODS model ID
            percentSusceptible = Double.valueOf(columns[1]);
            percentExposed = Double.valueOf(columns[2]);
            percentInfectious = Double.valueOf(columns[3]);
            percentRecovered = Double.valueOf(columns[4]);
            r0 = Double.valueOf(columns[5]);
            latentPeriod = Double.valueOf(columns[6]);
            infectiousPeriod = Double.valueOf(columns[7]);
            dayOfWeekOffset = Integer.parseInt(columns[8]);
        }
    }

    public StageInDbWorkerThread(BigInteger batchRunId, BigInteger simulationGroupId, SoftwareIdentification simulatorIdentification, Authentication authentication, String line, RunSimulationsMessage message, XMLGregorianCalendar scenarioDate, SynchronizedStringBuilder batchInputsWithRunIdsStringBuilder, RunSimulationsThread.BooleanRef errorRef, RunSimulationsThread.CounterRef counterRef) throws ApolloDatabaseException {
        this.line = line;
        this.message = message;
        this.batchInputsWithRunIdsStringBuilder = batchInputsWithRunIdsStringBuilder;
        this.authentication = authentication;
        this.scenarioDate = scenarioDate;
        this.batchRunId = batchRunId;
        this.simulationGroupId = simulationGroupId;
        this.simulatorIdentification = simulatorIdentification;
        this.errorRef = errorRef;
        this.counterRef = counterRef;

    }

    private PopulationInfectionAndImmunityCensusDataCell createPiiDataCell(
            InfectionStateEnum infectionStateEnum, Double fractionInState) {
        PopulationInfectionAndImmunityCensusDataCell piiDataCell = new PopulationInfectionAndImmunityCensusDataCell();
        piiDataCell.setFractionInState(fractionInState);
        piiDataCell.setInfectionState(infectionStateEnum);
        return piiDataCell;
    }

    private InfectiousDiseaseScenario copyInfectiousDiseaseScenarioForTemplate(InfectiousDiseaseScenario ids) throws JsonUtilsException {
        return (InfectiousDiseaseScenario) jsonUtils.getObjectFromJson(jsonUtils.getJSONString(ids), ids.getClass());
    }

    public RunSimulationMessage populateTemplateWithRecord(
            RunSimulationsMessage template, BatchConfigRecord batchConfigRecord, XMLGregorianCalendar scenarioDate) throws DatatypeConfigurationException, JsonUtilsException {

        RunSimulationMessage runSimulationMessage = new RunSimulationMessage();
        runSimulationMessage.setAuthentication(template.getAuthentication());
        runSimulationMessage.setInfectiousDiseaseScenario(copyInfectiousDiseaseScenarioForTemplate(template.getBaseInfectiousDiseaseScenario()));
        runSimulationMessage.setSimulatorIdentification(template
                .getSimulatorIdentification());
        runSimulationMessage.setSimulatorTimeSpecification(template
                .getSimulatorTimeSpecification());

        ReproductionNumber r0 = new ReproductionNumber();
        r0.setExactValue(batchConfigRecord.r0);

        FixedDuration latentPeriod = new FixedDuration();
        latentPeriod.setValue(batchConfigRecord.latentPeriod);
        latentPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);

        FixedDuration infectiousPeriod = new FixedDuration();
        infectiousPeriod.setValue(batchConfigRecord.infectiousPeriod);
        infectiousPeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
        InfectionAcquisitionFromInfectiousHost infection = runSimulationMessage
                .getInfectiousDiseaseScenario().getInfections().get(0)
                .getInfectionAcquisitionsFromInfectiousHosts().get(0);

        infection.setLatentPeriodDuration(latentPeriod);
        infection.setInfectiousPeriodDuration(infectiousPeriod);
        infection.getBasicReproductionNumbers().set(0, r0);

        List<PopulationInfectionAndImmunityCensusDataCell> censusDataCells = runSimulationMessage
                .getInfectiousDiseaseScenario()
                .getPopulationInfectionAndImmunityCensuses().get(0)
                .getCensusData().getCensusDataCells();

        censusDataCells.clear();
        censusDataCells.add(createPiiDataCell(InfectionStateEnum.SUSCEPTIBLE,
                batchConfigRecord.percentSusceptible));
        censusDataCells.add(createPiiDataCell(InfectionStateEnum.LATENT,
                batchConfigRecord.percentExposed));
        censusDataCells.add(createPiiDataCell(InfectionStateEnum.INFECTIOUS,
                batchConfigRecord.percentInfectious));
        censusDataCells.add(createPiiDataCell(InfectionStateEnum.RECOVERED,
                batchConfigRecord.percentRecovered));
        GregorianCalendar gc = scenarioDate.toGregorianCalendar();
        gc.add(Calendar.DATE, batchConfigRecord.dayOfWeekOffset);
        scenarioDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        runSimulationMessage.getInfectiousDiseaseScenario().setScenarioDate(scenarioDate);
        return runSimulationMessage;

    }

    public boolean isNonErrorCachedStatus(
            MethodCallStatusEnum methodCallStatusEnum) {
        switch (methodCallStatusEnum) {
            case AUTHENTICATION_FAILURE:
            case FAILED:
            case UNAUTHORIZED:
            case UNKNOWN_RUNID:
                return false;
            default:
                return true;
        }
    }

    @Override
    public void run() {
        logger.trace("Thread {} running.", Thread.currentThread().getName());

        String paramLineOrNullIfEndOfStream = line;
        if (paramLineOrNullIfEndOfStream != null) {
            logger.info("Thread {} processing line: {}", Thread.currentThread().getName(), paramLineOrNullIfEndOfStream);
            String params[] = paramLineOrNullIfEndOfStream.split(",");

            BatchConfigRecord batchConfigRecord = new BatchConfigRecord(
                    params);
            if (message instanceof RunSimulationsMessage) {
                RunSimulationMessage currentRunSimulationMessage = null;
                try {
                    currentRunSimulationMessage = populateTemplateWithRecord(
                            message, batchConfigRecord, scenarioDate);
//                    try {
//                        System.out.println("From " + paramLineOrNullIfEndOfStream + " Creating worker for run " + md5Utils.getMd5(currentRunSimulationMessage) + " (" + currentRunSimulationMessage + ")" + "\n");
//                    } catch (Md5UtilsException e) {
//                        e.printStackTrace();
//                    }
                } catch (DatatypeConfigurationException ex) {
                    errorRef.value = true;
                    ApolloServiceErrorHandler
                            .reportError(
                                    "Error staging job. There was an exception setting the scenario date.", batchRunId
                            );
                    return;
                } catch (JsonUtilsException ex) {
                    errorRef.value = true;
                    ApolloServiceErrorHandler
                            .reportError(
                                    "Error staging job. There was an exception converting the baseInfectiousDiseaseScenario to JSON.", batchRunId
                            );
                    return;
                }
                RunMethod runMethod = new RunMethodForSimulationAndVisualization(
                        authentication,
                        message.getSimulatorIdentification(),
                        simulationGroupId,
                        currentRunSimulationMessage);
                RunResultAndSimulationGroupId runResultandSimulationGroupId = runMethod.stage();
                RunResult runResult = runResultandSimulationGroupId.getRunResult();

                String lineWithRunId = paramLineOrNullIfEndOfStream + "," + runResult.getRunId();
                batchInputsWithRunIdsStringBuilder.append(lineWithRunId).append("\n");



                if (!isNonErrorCachedStatus(runResult.getMethodCallStatus().getStatus())) {
                    errorRef.value = true;
                    ApolloServiceErrorHandler
                            .reportError(
                                    "Error staging job using line "
                                            + paramLineOrNullIfEndOfStream
                                            + " of the batch configuration file. "
                                            + "Broker returned the following status: "
                                            + "(" + runResult.getMethodCallStatus().getStatus() + ")" + runResult.getMethodCallStatus().getMessage() + " for software "
                                            + simulatorIdentification
                                            .getSoftwareVersion()
                                            + ", developer: "
                                            + simulatorIdentification
                                            .getSoftwareDeveloper()
                                            + "  run id " + batchRunId + ".",
                                    batchRunId);
                    return;
                }
                errorRef.value = false;
            }
        }
        counterRef.count += 1;
    }
}

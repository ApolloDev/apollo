package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.apollo_service_types.v4_0_2.RunSimulationsMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.Md5UtilsException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.runmanagerservice.methods.stage.StageMethod;
import edu.pitt.apollo.runmanagerservice.types.SynchronizedStringBuilder;
import edu.pitt.apollo.runmanagerservice.utils.ApolloServiceErrorHandler;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_2.*;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.JsonUtils;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jdl50 on 4/8/15.
 */
public class StageInDbWorkerThread implements Runnable {


    static Logger logger = LoggerFactory.getLogger(StageInDbWorkerThread.class);
    private final RunSimulationsMessage message;
    private final SynchronizedStringBuilder batchInputsWithRunIdsStringBuilder;
    private final XMLGregorianCalendar scenarioDate;
    private final BigInteger batchRunId;
    private final SoftwareIdentification simulatorIdentification;
    private final BatchStageThread.BooleanRef errorRef;
    private final BatchStageThread.CounterRef counterRef;
    private final List<BigInteger> failedRunIds;
    private final Authentication authentication;
    private final String line;
    JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));

    public StageInDbWorkerThread(BigInteger batchRunId, String line, RunSimulationsMessage message,
                                 XMLGregorianCalendar scenarioDate, SynchronizedStringBuilder batchInputsWithRunIdsStringBuilder,
                                 BatchStageThread.BooleanRef errorRef, BatchStageThread.CounterRef counterRef,
                                 List<BigInteger> failedRunIds, Authentication authentication) throws DatastoreException {
        this.line = line;
        this.message = message;
        this.batchInputsWithRunIdsStringBuilder = batchInputsWithRunIdsStringBuilder;
        this.scenarioDate = scenarioDate;
        this.batchRunId = batchRunId;
        this.simulatorIdentification = message.getSoftwareIdentification();
        this.errorRef = errorRef;
        this.counterRef = counterRef;
        this.failedRunIds = failedRunIds;
        this.authentication = authentication;
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
        runSimulationMessage.setInfectiousDiseaseScenario(copyInfectiousDiseaseScenarioForTemplate(template.getBaseInfectiousDiseaseScenario()));
        runSimulationMessage.setSoftwareIdentification(template
                .getSoftwareIdentification());
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
        InfectionAcquisitionFromInfectedHost infection = runSimulationMessage
                .getInfectiousDiseaseScenario().getInfections().get(0)
                .getInfectionAcquisitionsFromInfectedHosts().get(0);

        infection.setLatentPeriodDuration(latentPeriod);
        infection.setInfectiousPeriodDuration(infectiousPeriod);
        infection.getBasicReproductionNumbers().set(0, r0);

        List<PopulationInfectionAndImmunityCensusDataCell> censusDataCells = runSimulationMessage
                .getInfectiousDiseaseScenario()
                .getPopulations().get(0).getInfectionAndImmunityCensuses().get(0)
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
                                    "Error staging job. There was an exception setting the scenario date.", batchRunId,
                            authentication);
                    return;
                } catch (JsonUtilsException ex) {
                    errorRef.value = true;
                    ApolloServiceErrorHandler
                            .reportError(
                                    "Error staging job. There was an exception converting the baseInfectiousDiseaseScenario to JSON.", batchRunId,
                            authentication);
                    return;
                }
                StageMethod stageMethod = null;
                try {
                    stageMethod = new StageMethod(currentRunSimulationMessage, batchRunId, authentication);
                    InsertRunResult result = stageMethod.stage();
                    BigInteger runId = result.getRunId();

					DatastoreAccessor.addRunSimulationMessageFileToBatchDirectory(batchRunId, runId, currentRunSimulationMessage);
					
                    DatastoreAccessor dataServiceAccessor = new DatastoreAccessor();
                    MethodCallStatus status = dataServiceAccessor.getRunStatus(runId, authentication);
                    if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
                        failedRunIds.add(runId);
                    }

                    String lineWithRunId = paramLineOrNullIfEndOfStream + "," + runId;
                    batchInputsWithRunIdsStringBuilder.append(lineWithRunId).append("\n");
                } catch (RunManagementException | FileNotFoundException | Md5UtilsException | UnrecognizedMessageTypeException
						| ApolloDatabaseException e) {
                    errorRef.value = true;
                    ApolloServiceErrorHandler
                            .reportError(
                                    "Error staging job using line "
                                            + paramLineOrNullIfEndOfStream
                                            + " of the batch configuration file. "
                                            + "Broker returned the following status: "
                                            + "(" + e.getMessage() + " for software "
                                            + simulatorIdentification
                                            .getSoftwareVersion()
                                            + ", developer: "
                                            + simulatorIdentification
                                            .getSoftwareDeveloper()
                                            + "  run id " + batchRunId + ".",
                                    batchRunId, authentication);
                    return;
                }
            }
        }
        counterRef.count += 1;
    }

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
}

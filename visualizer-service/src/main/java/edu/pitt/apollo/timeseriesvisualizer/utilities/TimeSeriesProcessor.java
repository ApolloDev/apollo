package edu.pitt.apollo.timeseriesvisualizer.utilities;

import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.filestore_service_types.v4_0_2.FileIdentification;
import edu.pitt.apollo.query_service_types.v4_0_2.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_2.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0_2.FileTypeEnum;
import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainer;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainerList;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeEnum;
import edu.pitt.apollo.types.v4_0_2.InfectionStateEnum;
import edu.pitt.apollo.types.v4_0_2.SimulatorCountOutputSpecification;
import edu.pitt.apollo.types.v4_0_2.SoftwareIdentification;
import edu.pitt.apollo.types.v4_0_2.TemporalGranularityEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 12, 2013 Time: 4:33:35 PM Class: DatabaseUtility IDE: NetBeans 6.9.1
 */
public class TimeSeriesProcessor {

	static Logger logger = LoggerFactory.getLogger(TimeSeriesProcessor.class);
	private BigInteger visualizerRunId;
    private Authentication authentication;

	public TimeSeriesProcessor(BigInteger visualizerRunId, Authentication authentication) throws TimeSeriesVisualizerException {
		this.visualizerRunId = visualizerRunId;
        this.authentication = authentication;
	}

	private Map<InfectionStateEnum, TreeMap<Integer, Double>> processQueryResultFile(String filePath) throws TimeSeriesVisualizerException {

		// this will load the file and create a map of infection state to a treemap of
		// time step and count
		Map<InfectionStateEnum, TreeMap<Integer, Double>> infectionStateMap = new HashMap<>();

		File file = new File(filePath);
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException ex) {
			throw new TimeSeriesVisualizerException("File " + filePath + " could not be found");
		}
		scanner.nextLine(); // skip header
		// file should look like simulator_time,household_location_admin4,infection_state,count
		// this should be improved in the future
		int latestEndTime = 0;
		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(",");
			Integer simulatorTime = Integer.parseInt(line[0]);
			String infectionState = line[1];
			Double count = Double.parseDouble(line[2]);
			InfectionStateEnum isEnum = InfectionStateEnum.fromValue(infectionState);
			TreeMap<Integer, Double> series;
			
			if (simulatorTime > latestEndTime) {
				latestEndTime = simulatorTime;
			}
			
			if (infectionStateMap.containsKey(isEnum)) {
				series = infectionStateMap.get(isEnum);
			} else {
				series = new TreeMap<>();
				infectionStateMap.put(isEnum, series);
			}

			if (series.containsKey(simulatorTime)) {
				series.put(simulatorTime, count + series.get(simulatorTime));
			} else {
				series.put(simulatorTime, count);
			}
		}

		scanner.close();
		
		// now we need to check that all time series contain all time points
		for (InfectionStateEnum isEnum : infectionStateMap.keySet()) {
			TreeMap<Integer, Double> series = infectionStateMap.get(isEnum);
			for (int i = 0; i <= latestEndTime; i++) {
				if (!series.containsKey(i)) {
					series.put(i, 0.0);
				}
			}
		}

		return infectionStateMap;
	}

	private TimeSeriesContainer getTimeSeriesContainerFromInfectionStateMap(List<TimeSeriesCurveTypeEnum> infectionSatesToRetrieve,
			Map<InfectionStateEnum, TreeMap<Integer, Double>> infectionStateMap, TimeSeriesContainer container) throws TimeSeriesVisualizerException {

		for (InfectionStateEnum infectionState : infectionStateMap.keySet()) {

			Collection<Double> values = infectionStateMap.get(infectionState).values();
			Double[] series = values.toArray(new Double[values.size()]);

			switch (infectionState) {

				case SUSCEPTIBLE:
					container.setSeries(TimeSeriesCurveTypeEnum.SUSCEPTIBLE, series);
					break;
				case INFECTIOUS:
					container.setSeries(TimeSeriesCurveTypeEnum.INFECTIOUS, series);
					break;
				case LATENT:
					container.setSeries(TimeSeriesCurveTypeEnum.LATENT, series);
					break;
				case RECOVERED:
					container.setSeries(TimeSeriesCurveTypeEnum.RECOVERED, series);
					break;
				case NEWLY_SICK:
					container.setSeries(TimeSeriesCurveTypeEnum.NEWLY_LATENT, series);
					break;
			}
		}

		return container;
	}

	public TimeSeriesContainerList retrieveTimeSeriesFromSimulatorOutput(List<BigInteger> runIds,
			Map<BigInteger, SoftwareIdentification> runIdSoftwareMap,
			List<TimeSeriesCurveTypeEnum> infectionSatesToRetrieve) throws TimeSeriesVisualizerException {

		TimeSeriesContainerList container = new TimeSeriesContainerList();

		for (BigInteger runId : runIds) {
			RunSimulatorOutputQueryMessage message = new RunSimulatorOutputQueryMessage();
			message.setRunId(runId);

			// create simulator count output specifications
			message.getOutputFormats().add(FileTypeEnum.CSV);
			SimulatorCountOutputSpecification outputSpecification = new SimulatorCountOutputSpecification();
			// for now hard code to SEIR
			outputSpecification.getInfectionStates().add(InfectionStateEnum.INFECTIOUS);
			outputSpecification.getInfectionStates().add(InfectionStateEnum.SUSCEPTIBLE);
			outputSpecification.getInfectionStates().add(InfectionStateEnum.LATENT);
			outputSpecification.getInfectionStates().add(InfectionStateEnum.RECOVERED);
			outputSpecification.getInfectionStates().add(InfectionStateEnum.NEWLY_SICK);
			outputSpecification.setSimulatorCountOutputSpecificationId(new BigInteger("1"));
			outputSpecification.setSpeciesToCount("9606");
			outputSpecification.setTemporalGranularity(TemporalGranularityEnum.EACH_SIMULATION_TIMESTEP);
//			prevalenceSpecification.setSpatialGranularity(SpatialGranularityEnum.ADMIN_4);
			message.getSimulatorCountOutputSpecifications().add(outputSpecification);
			
			FileIdentification fileIdentification = new FileIdentification();
			String simulatorName = runIdSoftwareMap.get(runId).getSoftwareName();
			if (simulatorName.equalsIgnoreCase("fred")) {
				fileIdentification.setFormat(ContentDataFormatEnum.HDF);
				fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
				fileIdentification.setLabel(runId + ".apollo.h5");
			} else if (simulatorName.equalsIgnoreCase("seir")) {
				fileIdentification.setFormat(ContentDataFormatEnum.TEXT);
                fileIdentification.setType(ContentDataTypeEnum.SIMULATOR_LOG_FILE);
                fileIdentification.setLabel(runId + ".apollo.csv");
			} else {
                throw new TimeSeriesVisualizerException("Unsupported simulator for query message");
            }

			message.setOutputFileIdentification(fileIdentification);
			try {
				RunUtils.runQueryService(message, visualizerRunId, authentication);

				// process file
				String filePath = RunUtils.getLocalFilePath("1.csv", visualizerRunId);
				Map<InfectionStateEnum, TreeMap<Integer, Double>> infectionStateMap = processQueryResultFile(filePath);
				TimeSeriesContainer timeSeriesContainer = new TimeSeriesContainer();
				getTimeSeriesContainerFromInfectionStateMap(infectionSatesToRetrieve,
						infectionStateMap, timeSeriesContainer);

				container.add(timeSeriesContainer);

			} catch (RunManagementException ex) {
				throw new TimeSeriesVisualizerException("RunManagementException running query: " + ex.getMessage());
			}

		}

		return container;
	}
}

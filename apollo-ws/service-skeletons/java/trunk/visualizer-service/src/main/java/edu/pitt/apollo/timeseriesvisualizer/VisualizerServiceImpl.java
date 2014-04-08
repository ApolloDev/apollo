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
package edu.pitt.apollo.timeseriesvisualizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.visualizerservice.v2_0_1.VisualizerServiceEI;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0/", portName = "VisualizerServiceEndpoint", serviceName = "VisualizerService_v2.0", endpointInterface = "edu.pitt.apollo.service.visualizerservice.v2_0.VisualizerServiceEI")
class VisualizerServiceImpl implements VisualizerServiceEI {

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0/", className = "edu.pitt.apollo.service.visualizerservice.v2_0.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/v2_0/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0/", className = "edu.pitt.apollo.service.visualizerservice.v2_0.GetRunStatusResponse")
	public MethodCallStatus getRunStatus(@WebParam(name = "runId", targetNamespace = "") String runId) {
		MethodCallStatus rs = new MethodCallStatus();

		String runIdString = runId;
		List<String> runIds;
		runIds = new ArrayList<String>();
		boolean multiSimulatorChart;
		boolean multiVaccChart;
		if (runIdString.contains(":")) {
			// multi simulator
			multiSimulatorChart = true;
			if (runIdString.contains(";")) {
				// vacc and no vacc runs
				multiVaccChart = true;
				String[] simRunIds = runIdString.split(":");
				for (String simRunId : simRunIds) {
					String[] vaccStratRunIds = simRunId.split(";");
					for (int i = 0; i < 2; i++) {
						String id = vaccStratRunIds[i];
						runIds.add(id);
					}
				}
			} else {
				// only no vacc run
				multiVaccChart = false;
				String[] simRunIds = runIdString.split(":");
				for (String simRunId : simRunIds) {
					runIds.add(simRunId);
				}
			}
		} else {
			// single simulator
			multiSimulatorChart = false;
			if (runIdString.contains(";")) {
				// vacc and no vacc runs
				multiVaccChart = true;
				String[] vaccStratRunIds = runIdString.split(";");
				for (int i = 0; i < 2; i++) {
					runIds.add(vaccStratRunIds[i]);
				}
			} else {
				// only no vacc run
				multiVaccChart = false;
				runIds.add(runIdString);

			}
		}
		// rs.setMessage("it worked");
		// rs.setStatus(RunStatusEnum.COMPLETED);

		// // find the run directory
		ImageGenerator ig = null;
		try {
			ig = new ImageGenerator(runIds, multiVaccChart, multiSimulatorChart);
		} catch (NoSuchAlgorithmException ex) {
			System.err.println("Exception creatingImageGenerator: "
					+ ex.getMessage());
		}

		String runDirectory = ig.getRunDirectory();
		String finishedFilePath = runDirectory + File.separator
				+ ImageGeneratorRunnable.FINISHED_FILE;
		File finishedFile = new File(finishedFilePath);
		if (finishedFile.exists()) {
			rs.setMessage("Run with ID " + runId + " is completed");
			rs.setStatus(MethodCallStatusEnum.COMPLETED);
		} else {
                    
                    // check error file
                    String errorFilePath = runDirectory + File.separator + ImageGeneratorRunnable.ERROR_FILE;
                    File errorFile = new File(errorFilePath);
                    if (errorFile.exists()) {
                        rs.setStatus(MethodCallStatusEnum.FAILED);
                        try {
                            Scanner scanner = new Scanner(errorFile);
                            StringBuilder error = new StringBuilder();
                            while (scanner.hasNextLine()) {
                                error.append(scanner.nextLine()).append("\n");
                            }
                            
                            rs.setMessage(error.toString());
                        } catch (FileNotFoundException ex) {
                            System.err.println("Could not open error file \"" + errorFilePath + "\" for reading");
                            rs.setMessage("Could not open error file for run with ID " + runId);
                        }
                    } else {
                    
			// check started file
			String startedFilePath = runDirectory + File.separator
					+ ImageGeneratorRunnable.STARTED_FILE;

			File startedFile = new File(startedFilePath);
			if (startedFile.exists()) {
				rs.setMessage("Still running with run ID " + runId);
				rs.setStatus(MethodCallStatusEnum.RUNNING);
			} else {
				System.out.println("finished file path: "
						+ finishedFile.getAbsolutePath());
				rs.setMessage("Run with ID " + runId
						+ " has not been requested yet");
				rs.setStatus(MethodCallStatusEnum.FAILED);
			}
                    }
		}
		return rs;
	}

	@Override
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0_1/", className = "edu.pitt.apollo.service.visualizerservice.v2_0_1.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/v2_0_1/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0_1/", className = "edu.pitt.apollo.service.visualizerservice.v2_0_1.RunVisualizationResponse")
	public void runVisualization(@WebParam(name = "visualizationRunId", targetNamespace = "") String visualizationRunId,
			@WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {
//		String runIdString = runVisualizationMessage.getVisualizationOptions()
//				.getRunId();
//		List<String> runIds;
//		Map<String, String> runIdSeriesLabel = new HashMap<String, String>();
//		boolean multiSimulatorChart;
//		boolean multiVaccChart;
//		runIds = new ArrayList<String>();
//		if (runIdString.contains(":")) {
//			// multi simulator
//			multiSimulatorChart = true;
//			if (runIdString.contains(";")) {
//				// vacc and no vacc runs
//				multiVaccChart = true;
//				String[] simRunIds = runIdString.split(":");
//				for (String simRunId : simRunIds) {
//					String[] vaccStratRunIds = simRunId.split(";");
//					for (int i = 0; i < 2; i++) {
//						String id = vaccStratRunIds[i];
//						runIds.add(id);
//						switch (i) {
//						case 0:
//							runIdSeriesLabel
//									.put(id, id + "_no_control_measure");
//							break;
//						case 1:
//							runIdSeriesLabel.put(id, id + "_control_measure");
//							break;
//						}
//					}
//				}
//			} else {
//				// only no vacc run
//				multiVaccChart = false;
//				String[] simRunIds = runIdString.split(":");
//				for (String simRunId : simRunIds) {
//					runIds.add(simRunId);
//					runIdSeriesLabel.put(simRunId, simRunId);
//				}
//			}
//		} else {
//			// single simulator
//			multiSimulatorChart = false;
//			if (runIdString.contains(";")) {
//				// vacc and no vacc runs
//				multiVaccChart = true;
//				String[] vaccStratRunIds = runIdString.split(";");
//				for (int i = 0; i < 2; i++) {
//					runIds.add(vaccStratRunIds[i]);
//					switch (i) {
//					case 0:
//						runIdSeriesLabel.put(vaccStratRunIds[i],
//								"No Vaccination");
//						break;
//					case 1:
//						runIdSeriesLabel.put(vaccStratRunIds[i], "Vaccination");
//						break;
//					}
//				}
//			} else {
//				// only no vacc run
//				multiVaccChart = false;
//				runIds.add(runIdString);
//				runIdSeriesLabel.put(runIdString, "No control measure");
//
//			}
//		}
//
//		// System.out.println("multi vacc chart: " + multiVaccChart +
//		// "   multi sim chart: " + multiSimulatorChart);
//		// System.out.println("run ids size: " + runIds.size());
//		// System.out.println("run ids 0: " + runIds.get(0));
//
//		VisualizerResult result = new VisualizerResult();
//		result.setRunId(runVisualizationMessage.getVisualizationOptions()
//				.getRunId()); // use the same runId as in the request
//
//		ImageGenerator ig = null;
//
//		List<UrlOutputResource> outputResource = result
//				.getVisualizerOutputResource();
//
//		try {
//			ig = new ImageGenerator(runIds, outputResource, runIdSeriesLabel,
//					multiVaccChart, multiSimulatorChart);
//		} catch (NoSuchAlgorithmException ex) {
//			System.err.println("Exception: " + ex.getMessage());
//		}
//
//		// now the image URLs have been set, so start a thread to generate the
//		// images
//		ImageGeneratorRunnable igRunnable = new ImageGeneratorRunnable();
//		igRunnable.setImageGenerator(ig);
//		Thread thread = new Thread(igRunnable);
//		thread.start();
//
//		return result;
		
	}

}
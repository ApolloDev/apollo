/*
 * Copyright 2015 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.ApolloServiceThread;
import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.DeserializationException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SerializationException;
import edu.pitt.apollo.exception.UnsupportedSerializationFormatException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.utils.ErrorUtils;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.SerializationFormat;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0.InfectiousDiseaseControlMeasure;
import edu.pitt.apollo.types.v4_0.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v4_0.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v4_0.InfectiousDiseaseTransmissionExperimentSpecification;
import edu.pitt.apollo.types.v4_0.OneWaySensitivityAnalysisOfContinousVariableSpecification;
import edu.pitt.apollo.types.v4_0.SensitivityAnalysisSpecification;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.Deserializer;
import edu.pitt.apollo.utilities.DeserializerFactory;
import edu.pitt.apollo.utilities.XMLDeserializer;
import edu.pitt.apollo.utilities.XMLSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 *
 * @author nem41
 */
public class StageExperimentMethod extends ApolloServiceThread {

	private final RunInfectiousDiseaseTransmissionExperimentMessage message;
	private final InfectiousDiseaseTransmissionExperimentSpecification idtes;
	private final Authentication authentication;

	public StageExperimentMethod(BigInteger experimentId, ApolloServiceQueue queue,
                                 RunInfectiousDiseaseTransmissionExperimentMessage message, Authentication authentication) {
		super(experimentId, queue);
		this.message = message;
		this.idtes = message.getInfectiousDiseaseTransmissionExperimentSpecification();
		this.authentication = authentication;
	}

	@Override
	public void runApolloService() {

		XMLSerializer serializer = new XMLSerializer();
		XMLDeserializer deserializer = new XMLDeserializer();

		InfectiousDiseaseScenario baseScenario = idtes.getInfectiousDiseaseScenarioWithoutIntervention();
        // clear all set control strategies in base
        baseScenario.getInfectiousDiseaseControlStrategies().clear();

		List<SoftwareIdentification> modelIds = idtes.getInfectiousDiseaseTransmissionModelIds();
		try {
			DatastoreAccessor dataServiceAccessor = new DatastoreAccessor();
			
			for (SoftwareIdentification modelId : modelIds) {

				// create a base scenario copy
				String baseXml = serializer.serializeObject(baseScenario);
				InfectiousDiseaseScenario baseScenarioCopy = deserializer.getObjectFromMessage(baseXml, InfectiousDiseaseScenario.class);
				for (InfectiousDiseaseControlStrategy strategy : idtes.getInfectiousDiseaseControlStrategies()) {

					for (InfectiousDiseaseControlMeasure controlMeasure : strategy.getControlMeasures()) {
						baseScenarioCopy.getInfectiousDiseaseControlStrategies().add(controlMeasure);
					}
				}

				List<SensitivityAnalysisSpecification> sensitivityAnalyses = idtes.getSensitivityAnalyses();
				for (SensitivityAnalysisSpecification sensitivityAnalysis : sensitivityAnalyses) {
					if (sensitivityAnalysis instanceof OneWaySensitivityAnalysisOfContinousVariableSpecification) {
						OneWaySensitivityAnalysisOfContinousVariableSpecification owsaocv = (OneWaySensitivityAnalysisOfContinousVariableSpecification) sensitivityAnalysis;
						double min = owsaocv.getMinimumValue();
						double max = owsaocv.getMaximumValue();
						double increment = (max - min) / owsaocv.getNumberOfDiscretizations().intValueExact();

						String scenarioXML = serializer.serializeObject(baseScenarioCopy);

						double val = min;
						while (val <= max) {

                            String param = owsaocv.getXPathToVariable();

                            Document jdomDocument;
							SAXBuilder jdomBuilder = new SAXBuilder();
							try {
								jdomDocument = jdomBuilder.build(new ByteArrayInputStream(scenarioXML.getBytes(StandardCharsets.UTF_8)));
							} catch (JDOMException | IOException ex) {
								ErrorUtils.reportError(runId, "Error inserting experiment run. Error was " + ex.getMessage(), authentication);
								return;
							}

							Element e = jdomDocument.getRootElement();
							List<Namespace> namespaces = e.getNamespacesInScope();

							for (Namespace namespace : namespaces) {
								if (namespace.getURI().contains("http://types.apollo.pitt.edu")) {
									param = param.replaceAll("/", "/" + namespace.getPrefix() + ":");
									param = param.replaceAll("\\[", "\\[" + namespace.getPrefix() + ":");
									break;
								}
							}

							XPathFactory xpf = XPathFactory.instance();
							XPathExpression<Element> expr;
							expr = xpf.compile(param, Filters.element(), null, namespaces);
							List<Element> elements = expr.evaluate(jdomDocument);

							for (Element element : elements) {
								element.setText(Double.toString(val));
							}

							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							XMLOutputter xmlOutputter = new XMLOutputter();
							xmlOutputter.output(jdomDocument, baos);

							InfectiousDiseaseScenario newScenario = deserializer.getObjectFromMessage(new String(baos.toByteArray()),
									InfectiousDiseaseScenario.class);

							// new scenario is ready to be staged
							RunSimulationMessage runSimulationMessage = new RunSimulationMessage();
							runSimulationMessage.setAuthentication(authentication);
							runSimulationMessage.setSimulatorTimeSpecification(message.getSimulatorTimeSpecification());
							runSimulationMessage.setSoftwareIdentification(modelId);
							runSimulationMessage.setInfectiousDiseaseScenario(newScenario);

							StageMethod stageMethod = new StageMethod(runSimulationMessage, runId);
							InsertRunResult result = stageMethod.stage();
							BigInteger newRunId = result.getRunId();

							MethodCallStatus status = dataServiceAccessor.getRunStatus(newRunId, authentication);
							if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
								ErrorUtils.reportError(runId, "Error inserting run in experiment with run ID " + runId + ""
										+ ". Error was for inserting ID " + newRunId + " with message " + status.getMessage(), authentication);
								return;
							}

							val += increment;
						}
					}
				}
			}

			dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.TRANSLATION_COMPLETED,
					"All runs for this experiment have been translated", authentication);
		} catch (DeserializationException | IOException | SerializationException | RunManagementException ex) {
			ErrorUtils.reportError(runId, "Error inserting experiment run. Error was " + ex.getMessage(), authentication);
			return;
		}
	}

	public static void main(String[] args) throws IOException, UnsupportedSerializationFormatException, DeserializationException {

		byte[] encoded = Files.readAllBytes(Paths.get("../tests/infectious-disease-experiment-specification/infectious-disease-experiment-specification-element.xml"));
		String xml = new String(encoded, Charset.defaultCharset());

		Deserializer deserializer = DeserializerFactory.getDeserializer(SerializationFormat.XML);
		InfectiousDiseaseTransmissionExperimentSpecification test = deserializer.getObjectFromMessage(xml,
				InfectiousDiseaseTransmissionExperimentSpecification.class);

		RunInfectiousDiseaseTransmissionExperimentMessage runMessage = new RunInfectiousDiseaseTransmissionExperimentMessage();
		runMessage.setAuthentication(null);
		runMessage.setInfectiousDiseaseTransmissionExperimentSpecification(test);
		runMessage.setSimulatorTimeSpecification(null);

		StageExperimentMethod method = new StageExperimentMethod(BigInteger.ZERO, null, runMessage, null);
	}
}
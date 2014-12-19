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
package edu.pitt.apollo.apolloclient.tutorial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;

import edu.pitt.apollo.apolloclient.tutorial.ApolloServiceTypeFactory.SimulatorIdentificationEnum;
import edu.pitt.apollo.types.v2_1_0.RunSimulationMessage;

public class Chapter2_RunSimulationWithNoIntervention extends
		AbstractRunAndVisualizeSimulationClass {
	public Chapter2_RunSimulationWithNoIntervention() {
		super();
	}

	public String getJSONString(Object obj) {
		try {
			Map<String, Object> properties = new HashMap<String, Object>(2);
			properties
					.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
			properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
			JAXBContext jc = (JAXBContext) JAXBContext.newInstance(new Class[] {
					RunSimulationMessage.class, ObjectFactory.class },
					properties);
			JAXBMarshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			marshaller.marshal(obj, baos);
			System.out.println(baos.toString());
			return baos.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "hello";
	}

	public void runExample() {
		RunSimulationMessage runSimulationMessage = ApolloServiceTypeFactory
				.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.FRED);
		String contentForRun = getJSONString(runSimulationMessage);
		Map<String, Object> properties = new HashMap<String, Object>(2);
		properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
		properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
		JAXBContext jc;
		try {
			jc = (JAXBContext) JAXBContext.newInstance(new Class[] {
					RunSimulationMessage.class, ObjectFactory.class },
					properties);

			JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
			StreamSource json = new StreamSource(new ByteArrayInputStream(
					contentForRun.getBytes()));
			RunSimulationMessage runSimulationMessage2 = (RunSimulationMessage) unmarshaller
					.unmarshal(json, RunSimulationMessage.class).getValue();
			System.out.println(runSimulationMessage2
					.getInfectiousDiseaseScenario().getScenarioDate());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.exit(-1);
		runScenarioAndDisplayResults(runSimulationMessage);
	}

	public static void main(String args[]) throws java.lang.Exception {
		Chapter2_RunSimulationWithNoIntervention tutorialChapter2 = new Chapter2_RunSimulationWithNoIntervention();
		tutorialChapter2.runExample();

	}
}
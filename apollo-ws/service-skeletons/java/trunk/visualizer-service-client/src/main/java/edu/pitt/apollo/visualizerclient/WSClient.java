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
package edu.pitt.apollo.visualizerclient;

import java.net.MalformedURLException;

public class WSClient {

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
//        VisualizerServiceV202 service = new VisualizerServiceV202(new URL("http://warhol-fred.psc.edu:8092/gaia?wsdl"));
//        VisualizerServiceEI port = service.getVisualizerServiceEndpoint();
//
//        RunVisualizationMessage vizConfig = new RunVisualizationMessage();
////                vizConfig.
//        VisualizationOptions options = new VisualizationOptions();
//        options.setRunId("UPitt,PSC,CMU_FRED_2.0.1_295201"); // 42003, 127 days
//        options.setLocation("42003");
//        options.setOutputFormat("mp4");
//
//        vizConfig.setVisualizationOptions(options);
//
//        SoftwareIdentification sid = new SoftwareIdentification();
//        sid.setSoftwareName("GAIA");
//        sid.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);
//        sid.setSoftwareVersion("1.0");
//        sid.setSoftwareDeveloper("PSC");
////                sid.setSoftwareName("Image Visualizer");
////                sid.setSoftwareType(ApolloSoftwareType.VISUALIZER);
////                sid.setSoftwareVersion("1.0");
////                sid.setSoftwareDeveloper("UPitt");
//
//        vizConfig.setVisualizerIdentification(sid);
//
//        Authentication auth = new Authentication();
//        auth.setRequesterId("fake_id");
//        auth.setRequesterPassword("fake_password");
//
//        vizConfig.setAuthentication(auth);
//
//
//
//        VisualizerResult result = port.runVisualization(vizConfig);
//
//        String runId = result.getRunId();
//
//        MethodCallStatus status = port.getRunStatus(runId);
//
//        while (status.getStatus() != MethodCallStatusEnum.COMPLETED) {
//            System.out.println(status.getStatus() + "    " + status.getMessage());
//            System.out.println(result.getVisualizerOutputResource().get(0).getURL());
//
//            Thread.sleep(5000);
//            status = port.getRunStatus(runId);
//        }
//
//        System.out.println(status.getStatus());
    }
}

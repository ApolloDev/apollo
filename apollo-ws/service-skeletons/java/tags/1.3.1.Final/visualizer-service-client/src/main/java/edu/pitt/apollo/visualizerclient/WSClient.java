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



import edu.pitt.apollo.service.visualizerservice._10._28._2013.VisualizerServiceEI;
import edu.pitt.apollo.service.visualizerservice._10._28._2013.VisualizerServiceV131;
import edu.pitt.apollo.types._10._28._2013.ApolloSoftwareType;
import edu.pitt.apollo.types._10._28._2013.Authentication;
import edu.pitt.apollo.types._10._28._2013.RunStatus;
import edu.pitt.apollo.types._10._28._2013.RunStatusEnum;
import edu.pitt.apollo.types._10._28._2013.SoftwareIdentification;
import edu.pitt.apollo.types._10._28._2013.VisualizationOptions;
import edu.pitt.apollo.types._10._28._2013.VisualizerConfiguration;
import edu.pitt.apollo.types._10._28._2013.VisualizerResult;
import java.net.MalformedURLException;
import java.net.URL;

public class WSClient {
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		VisualizerServiceV131 service = new VisualizerServiceV131(new URL("http://warhol-fred.psc.edu:8091/gaia?wsdl"));
		VisualizerServiceEI port = service.getVisualizerServiceEndpoint();

                VisualizerConfiguration  vizConfig = new VisualizerConfiguration();
//                vizConfig.
                VisualizationOptions options = new VisualizationOptions();
                options.setRunId("UPitt,PSC,CMU_FRED_2.0.1_259387");
                options.setLocation("06037");
                options.setOutputFormat("mp4");
                
                vizConfig.setVisualizationOptions(options);
                
                SoftwareIdentification sid = new SoftwareIdentification();
                sid.setSoftwareName("GAIA");
                sid.setSoftwareType(ApolloSoftwareType.VISUALIZER);
                sid.setSoftwareVersion("1.0");
                sid.setSoftwareDeveloper("PSC");
                
                vizConfig.setVisualizerIdentification(sid);
                
                Authentication auth = new Authentication();
                auth.setRequesterId("fake_id");
                auth.setRequesterPassword("fake_password");
                
                vizConfig.setAuthentication(auth);
                
                
                
                VisualizerResult result =  port.run(vizConfig);
                
                String runId = result.getRunId();
                
                RunStatus status = port.getRunStatus(runId);
                
                while (status.getStatus() != RunStatusEnum.COMPLETED) {
                    System.out.println(status.getStatus() + "    " + status.getMessage());
                    System.out.println(result.getVisualizerOutputResource().get(0).getURL());
                    
                    Thread.sleep(5000);
                    status = port.getRunStatus(runId);
                }
                
		System.out.println(status.getStatus());
	}
}

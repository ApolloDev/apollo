/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0.1 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0.1
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.pitt.apollo;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.visualizerservice.v2_0_2.VisualizerServiceEI;
import edu.pitt.apollo.timeseriesvisualizer.ImageGenerator;
import edu.pitt.apollo.timeseriesvisualizer.ImageGeneratorRunnable;
import edu.pitt.apollo.timeseriesvisualizer.utilities.RunUtils;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_2.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationMessage;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0_2/", portName = "VisualizerServiceEndpoint", serviceName = "VisualizerService_v2.0.1", endpointInterface = "edu.pitt.apollo.service.visualizerservice.v2_0_2.VisualizerServiceEI")
class VisualizerServiceImpl implements VisualizerServiceEI {

    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0_2/", className = "edu.pitt.apollo.service.visualizerservice.v2_0_2.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/v2_0_2/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0_2/", className = "edu.pitt.apollo.service.visualizerservice.v2_0_2.GetRunStatusResponse")
    public MethodCallStatus getRunStatus(@WebParam(name = "runId", targetNamespace = "") BigInteger runId) {
        MethodCallStatus rs = new MethodCallStatus();
        try {
            rs = RunUtils.getStatus(ImageGenerator.getRunDirectory(runId));
        } catch (IOException ex) {
            MethodCallStatus status = new MethodCallStatus();
            status.setMessage("Error getting runStatus from web service, error is: "
                    + ex.getMessage());
            status.setStatus(MethodCallStatusEnum.FAILED);
            return status;
        }
        
        return rs;
    }

    @Override
    @RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0_2/", className = "edu.pitt.apollo.service.visualizerservice.v2_0_2.RunVisualization")
    @WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/v2_0_2/runVisualization")
    @ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/v2_0_2/", className = "edu.pitt.apollo.service.visualizerservice.v2_0_2.RunVisualizationResponse")
    public void runVisualization(@WebParam(name = "visualizationRunId", targetNamespace = "") BigInteger visualizationRunId,
            @WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {

        List<RunIdentificationAndLabel> runIdsAndLabels = runVisualizationMessage.getSimulationRunIds();

        ImageGeneratorRunnable runnable = new ImageGeneratorRunnable(visualizationRunId, runIdsAndLabels, runVisualizationMessage.getVisualizerIdentification());
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
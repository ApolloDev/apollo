package edu.pitt.apollo.seir.utils;

import com.googlecode.sardine.Sardine;
import com.googlecode.sardine.SardineFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.SeirSimulatorServiceImpl;
import edu.pitt.apollo.types.BatchRunResult;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.SoftwareIdentification;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.type.TypeReference;

public class BatchThread extends Thread {

    private String batchRunId;
    private String batchRunIdMd5Hash;
    private String batchConfigurationFileUrl;
    private BatchRunResult result;

    public BatchThread(String batchConfigurationFile,
            BatchRunResult result, String batchRunId,
            String batchRunIdMd5Hash) {
        this.batchRunId = batchRunId;
        this.batchConfigurationFileUrl = batchConfigurationFile;
        this.batchRunIdMd5Hash = batchRunIdMd5Hash;
        this.result = result;
    }

    @Override
    public void run() {
        try {
            List<String> allRunIds = new ArrayList<String>();
            List<String> finishedRunIDs = new ArrayList<String>();
            Map<Integer, String> lineWaitingToBeWritten = new HashMap<Integer, String>();
            int lastWrittenRunIdNumber = -1;

            // mark the run as started (write a started.txt file)
            RunUtils.setStarted(batchRunIdMd5Hash);

            // create completed file
            String runIdResultsFilePath = RunUtils.getWorkDir(batchRunIdMd5Hash) + batchRunIdMd5Hash + "_results.txt";
            File resultsFile = new File(runIdResultsFilePath);
            PrintStream ps = new PrintStream(resultsFile);

            // determine local file to save remote config file to, e.g.
            // http://blah.com/batchConfig.json should be saved as
            // /home/apollo/34234.json <--34234.json is the localFn
            String localFn = RunUtils.getWorkDir(batchRunIdMd5Hash)
                    + batchRunIdMd5Hash + "." + "json";

            // downlaod the file
            org.apache.commons.io.FileUtils.copyURLToFile(new URL(
                    batchConfigurationFileUrl), new File(localFn));

            // read each JSON encoded simulatorConfiguration one at a time, uses
            // Jackson
            FileInputStream fis = new FileInputStream(new File(localFn));
            SeirSimulatorServiceImpl impl = new SeirSimulatorServiceImpl();
            ObjectMapper mapper = new ObjectMapper();
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            for (Iterator it = mapper.readValues(
                    new JsonFactory().createJsonParser(fis),
                    SimulatorConfiguration.class); it.hasNext();) {

                // it.next will return the next simulatorConfiguration
                SimulatorConfiguration sc = (SimulatorConfiguration) it.next();

                // previously the following was done in the SeirSimulatorServiceImpl run method

                String simConfigJson = SeirSimulatorServiceImpl.getJSONString(sc);
                String simConfigHash = RunUtils.getMd5HashFromString(simConfigJson);

                SoftwareIdentification sid = sc.getSimulatorIdentification();
                RunIdProperties runIdProps = SeirSimulatorServiceImpl.getOrAddRunId(simConfigHash, sid);
                String runId = runIdProps.getRunId();

                // this should never store anything to the database
                Thread worker = new SimulatorThread(sc, simConfigHash, runId,
                        simConfigJson, true, true, false);

                // need to wait until the simulator queue has a spot to run the simulator
                boolean addedSimulatorToQueue = SeirSimulatorServiceImpl.addSimulatorThread(worker);
                while (!addedSimulatorToQueue) { // only enter this loop if the run could not be added

                    // keep trying to add the simulator thread to the queue until it is added
                    System.out.println("Waiting to add simulator thread to queue...");
                    Thread.sleep(1000); // sleep for 1 seconds
                    addedSimulatorToQueue = SeirSimulatorServiceImpl.addSimulatorThread(worker);
                }

                allRunIds.add(runId);
            }
            
            System.out.println("All run IDs size: " + allRunIds.size());

            // wait until all runs have finished
            while (!(finishedRunIDs.size() == allRunIds.size())) {

                if (finishedRunIDs.size() > allRunIds.size()) {
                    System.err.println("The number of finished run IDs in the batch is greater than the total number of run IDs!");
                    try {
                        RunUtils.setError(batchRunIdMd5Hash,
                                "The number of finished run IDs in the batch is greater than the total number of run IDs!");

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    ps.flush();
                    ps.close();
                    return;
                }

                BatchThread.sleep(5000);
                // if at least one run is not done, allRunsDone will be false
                for (int i = 0; i < allRunIds.size(); i++) {

                    String simulatorRunId = allRunIds.get(i);
                    if (finishedRunIDs.contains(simulatorRunId)) {
                        continue;
                    }

                    RunStatus runStatus = impl.getRunStatus(simulatorRunId);
                    RunStatusEnum status = runStatus.getStatus();
                    String message = runStatus.getMessage();
//                    RunStatusEnum status = RunStatusEnum.COMPLETED;
                    System.out.println("SEIR run with ID " + simulatorRunId + " has status " + status);
                    if (!(status == RunStatusEnum.RUNNING || (status == RunStatusEnum.FAILED && message.equalsIgnoreCase("unknown run")))) {
                        finishedRunIDs.add(simulatorRunId);

                        String resultsTextForLine = "";
                        String results = RunUtils.getResultsString(simulatorRunId);
                        if (results == null) {
                            System.err.println("Error: there was no results file for runID " + simulatorRunId);
                        } else {
                            String[] resultsArray = results.split(",");
                            resultsTextForLine = resultsArray[0] + "\t" + resultsArray[1] + "\t" + resultsArray[2]
                                    + "\t" + "not_simulated" + "\t" + resultsArray[3] + "\t" + resultsArray[4];
                        }

                        String text = simulatorRunId + "\t" + status + "\t" + message + "\t" + resultsTextForLine;
                        lineWaitingToBeWritten.put(i, text);
                    }

                    while (lineWaitingToBeWritten.containsKey(lastWrittenRunIdNumber + 1)) {
                        lastWrittenRunIdNumber++;
                        ps.println(lineWaitingToBeWritten.get(lastWrittenRunIdNumber));
                        lineWaitingToBeWritten.remove(lastWrittenRunIdNumber);
                    }
                }


            }

            ps.flush();
            ps.close();

            Sardine sardine = SardineFactory.begin();
            try {
                InputStream fis2 = new FileInputStream(resultsFile);
                sardine.put("https://betaweb.rods.pitt.edu/jdl50_web_dav-source/" + resultsFile.getName(), fis2);
                fis2.close();
            } catch (IOException ex) {
                System.err.println("Could not create file input stream");
            }

            RunUtils.setFinished(batchRunIdMd5Hash);
        } catch (Exception e) {
            try {
                e.printStackTrace();
                RunUtils.setError(batchRunIdMd5Hash,
                        e.getMessage() + e.getStackTrace());

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
}

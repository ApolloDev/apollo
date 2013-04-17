package edu.pitt.apollo.seir.utils;

import edu.pitt.apollo.SeirSimulatorServiceImpl;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.rods.apollo.SeirModelAdapter.SeirModelAdapter;
import java.io.IOException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2013
 * Time: 1:22:18 PM
 * Class: SimulatorThread
 * IDE: NetBeans 6.9.1
 */
public class SimulatorThread extends Thread {

    SimulatorConfiguration sc;
    String runId;
    String simConfigHash;
    String simConfigJson;
    boolean runSimulator;
    boolean useFile;
    boolean useDatabase;

    public SimulatorThread(SimulatorConfiguration sc, String simConfigHash, 
            String runId, String simConfigJson, boolean runSimulator,
            boolean useFile, boolean useDatabase) {
        super();      
        this.sc = sc;
        this.runId = runId;
        this.simConfigHash = simConfigHash;
        this.runSimulator = runSimulator;
        this.simConfigJson = simConfigJson;
        this.useFile = useFile;
        this.useDatabase = useDatabase;
    }

    @Override
    public void run() {

        String runIdHash = RunUtils.getMd5HashFromBytes(runId.getBytes());
        try {

            String directory = RunUtils.setStarted(runIdHash);
            if (runSimulator) {
                SeirModelAdapter.runSeirModel(sc, simConfigHash, runId, simConfigJson, directory, useFile, useDatabase);
//                SeirModelAdapter.newtest2();
//                while(true) {
//                    System.out.println(Thread.currentThread().getName());
//                }
            }
            RunUtils.setFinished(runIdHash);
            SeirSimulatorServiceImpl.removeRunFromQueuedList(runIdHash);
            
            SeirSimulatorServiceImpl.simulatorRunFinished();
            SeirSimulatorServiceImpl.runSimulatorThreads();
        } catch (Exception e) {
            try {
                RunUtils.setError(runIdHash, e.getMessage() + e.getStackTrace());
                e.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
     SeirModelAdapter.newTest();  
    }
}
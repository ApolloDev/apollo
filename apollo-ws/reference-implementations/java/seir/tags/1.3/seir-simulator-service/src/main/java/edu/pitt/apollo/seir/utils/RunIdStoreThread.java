package edu.pitt.apollo.seir.utils;

import edu.pitt.apollo.SeirSimulatorServiceImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2013
 * Time: 3:12:19 PM
 * Class: RunIdStoreThread
 * IDE: NetBeans 6.9.1
 */
public class RunIdStoreThread extends Thread {

    private Integer lastRunId = null;
    private boolean writeImmediate;

    public RunIdStoreThread(boolean writeImmediate) {
        this.writeImmediate = writeImmediate;
    }

    @Override
    public void run() {
        try {
            while (true) {

                if (!writeImmediate) {

                    Thread.sleep(60000);

                } else {
                    writeImmediate = false;
                }

                int currentId = SeirSimulatorServiceImpl.getCurrentRunId();
                if (lastRunId == null || currentId != lastRunId.intValue()) {
                    try {
                        RunUtils.writeCurrentId(currentId);
                        lastRunId = currentId;
                    } catch (IOException ex) {
                        System.err.println("Could not write current run ID to file");
                    }
                }
            }
        } catch (InterruptedException ex) {
            System.err.println("InterruptedException in RunIdStoreThread");
        }

    }
}

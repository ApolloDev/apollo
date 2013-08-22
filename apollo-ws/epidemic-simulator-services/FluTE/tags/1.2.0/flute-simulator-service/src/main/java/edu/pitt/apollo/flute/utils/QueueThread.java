package edu.pitt.apollo.flute.utils;

import edu.pitt.apollo.FluteSimulatorServiceImpl;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Mar 21, 2013
 * Time: 1:54:55 PM
 * Class: QueueThread
 * IDE: NetBeans 6.9.1
 */
public class QueueThread extends Thread {

    private Thread simulatorThread;

    public QueueThread(Thread simulatorThread) {
        this.simulatorThread = simulatorThread;
    }

    @Override
    public void run() {

        // keep trying to add the thread to the queue
        boolean addedSimulatorThreadToQueue = false;
        while (!addedSimulatorThreadToQueue) {

            addedSimulatorThreadToQueue = FluteSimulatorServiceImpl.addSimulatorThread(simulatorThread);
            if (!addedSimulatorThreadToQueue) { // only need to sleep if it was false

                try {
                    Thread.sleep(2000); // sleep for 2 seconds
                } catch (InterruptedException ex) {
                    System.err.println("Interrupted exception trying to sleep in QueueThread");
                }
            }
        }

    }
}

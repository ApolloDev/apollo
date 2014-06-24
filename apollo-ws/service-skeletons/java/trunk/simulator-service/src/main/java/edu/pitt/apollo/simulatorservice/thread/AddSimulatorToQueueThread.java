package edu.pitt.apollo.simulatorservice.thread;

import edu.pitt.apollo.simulatorservice.queue.SimulatorServiceQueue;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Mar 21, 2013
 Time: 1:54:55 PM
 Class: AddSimulatorToQueueThread
 IDE: NetBeans 6.9.1
 */
public class AddSimulatorToQueueThread extends Thread {

    private final SimulatorThread simulatorThread;

    public AddSimulatorToQueueThread(SimulatorThread simulatorThread) {
        this.simulatorThread = simulatorThread;
    }

    @Override
    public void run() {

        // keep trying to add the thread to the queue
        boolean addedSimulatorThreadToQueue = false;
        while (!addedSimulatorThreadToQueue) {

            addedSimulatorThreadToQueue = SimulatorServiceQueue.addSimulatorThreadToQueueAndRun(simulatorThread);
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

package edu.pitt.apollo.simulatorservice.queue;

import edu.pitt.apollo.simulatorservice.thread.AddSimulatorToQueueThread;
import edu.pitt.apollo.simulatorservice.thread.SimulatorThread;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jun 20, 2014 Time: 4:05:02 PM Class: SimulatorServiceQueue IDE: NetBeans 6.9.1
 */
public class SimulatorServiceQueue {

    private static final int MAX_NUM_SIMULATOR_THREADS = 100;
    private static final int MAX_QUEUE_SIZE = 200;
    private static final List<Integer> runIdsOfQueuedSimulatorThreads = new ArrayList<Integer>();
    private static final Queue<SimulatorThread> simulatorThreadQueue;
    private static int numRunningSimulatorThreads;

    public static void startAddSimulatorToQueueThread(SimulatorThread simulatorThread) {

        int runId = simulatorThread.getRunId().intValue();
        if (!runIdsOfQueuedSimulatorThreads.contains(runId)) {
            // only need to add it if it is not already in the list
            runIdsOfQueuedSimulatorThreads.add(runId);
        }

        AddSimulatorToQueueThread queueThread = new AddSimulatorToQueueThread(simulatorThread);
        queueThread.start();
    }

    public static synchronized boolean addSimulatorThreadToQueueAndRun(SimulatorThread runnable) {

        if (simulatorThreadQueue.size() < MAX_QUEUE_SIZE) {
            simulatorThreadQueue.add(runnable);
            startQueuedSimulatorThreads(); // try to run a new thread
            return true;
        } else {
            return false;
        }
    }

    public static synchronized void removeRunFromQueuedList(Integer runId) {
        runIdsOfQueuedSimulatorThreads.remove(runId);
    }

    public static synchronized void startQueuedSimulatorThreads() {
        // start as many runs as possible
        while (numRunningSimulatorThreads < MAX_NUM_SIMULATOR_THREADS
                && simulatorThreadQueue.size() > 0) {
            SimulatorThread thread = simulatorThreadQueue.poll();
            numRunningSimulatorThreads++;
            removeRunFromQueuedList(thread.getRunId().intValue());

            thread.start();
        }
    }

    public static synchronized boolean isRunQueued(Integer runId) {
        return runIdsOfQueuedSimulatorThreads.contains(runId);
    }

    public static synchronized void simulatorThreadFinished() {
        numRunningSimulatorThreads--;
    }

    static {
        simulatorThreadQueue = new LinkedList<SimulatorThread>();
        numRunningSimulatorThreads = 0;
    }
}

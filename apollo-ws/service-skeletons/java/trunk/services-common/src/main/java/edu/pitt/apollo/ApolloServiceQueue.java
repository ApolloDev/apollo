package edu.pitt.apollo;

import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jun 20, 2014 Time: 4:05:02 PM Class: SimulatorServiceQueue IDE: NetBeans 6.9.1
 */
public class ApolloServiceQueue {

	private final int MAX_NUM_THREADS;
	private final int MAX_QUEUE_SIZE;
	private final List<Integer> runIdsOfQueuedSimulatorThreads = new ArrayList<Integer>();
	private final Queue<ApolloServiceThread> threadQueue;
	private int numRunningThreads;

	public ApolloServiceQueue() {
		MAX_NUM_THREADS = 12;
		MAX_QUEUE_SIZE = 200;
		threadQueue = new LinkedList<ApolloServiceThread>();
		numRunningThreads = 0;
	}

	public ApolloServiceQueue(int maxNumberOfThreads, int maxQueueSize) {
		MAX_NUM_THREADS = maxNumberOfThreads;
		MAX_QUEUE_SIZE = maxQueueSize;
		threadQueue = new LinkedList<ApolloServiceThread>();
		numRunningThreads = 0;
	}

	public synchronized MethodCallStatus addThreadToQueueAndRun(ApolloServiceThread runnable) {

		MethodCallStatus status = new MethodCallStatus();
		if (threadQueue.size() < MAX_QUEUE_SIZE) {
			runIdsOfQueuedSimulatorThreads.add(runnable.getRunId().intValue());
			threadQueue.add(runnable);
			startQueuedThreads(); // try to run a new thread
			status.setStatus(MethodCallStatusEnum.QUEUED);
			status.setMessage("The thread was added to the queue");
		} else {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage("The queue is full, please submit the thread again later.");
		}

		return status;
	}

	private synchronized void removeRunFromQueuedList(Integer runId) {
		runIdsOfQueuedSimulatorThreads.remove(runId);
	}

	public synchronized void startQueuedThreads() {
		// start as many runs as possible
		while (numRunningThreads < MAX_NUM_THREADS && threadQueue.size() > 0) {
			ApolloServiceThread thread = threadQueue.poll();
			numRunningThreads++;
			removeRunFromQueuedList(thread.getRunId().intValue());
			thread.start();
		}
	}

	public synchronized boolean isRunQueued(Integer runId) {
		return runIdsOfQueuedSimulatorThreads.contains(runId);
	}

	public synchronized void threadFinished() {
		numRunningThreads--;
	}
}

package edu.pitt.apollo;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 16, 2015
 * Time: 4:48:40 PM
 * Class: ApolloServiceThread
 */
public abstract class ApolloServiceThread extends Thread {

	protected final BigInteger runId;
	private final ApolloServiceQueue queue;
	
	public ApolloServiceThread(BigInteger runId, ApolloServiceQueue queue) {
		this.runId = runId;
		this.queue = queue;
	}
	
	public BigInteger getRunId() {
		return runId;
	}
	
	@Override
	public void run() {
		
		try {
		runApolloService();
		} finally {
			queue.threadFinished();
			queue.startQueuedThreads();
		}
	}
	
	public abstract void runApolloService();
	
}

package edu.pitt.apollo.seir.utils;

import java.io.IOException;

import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.rods.apollo.SeirModelAdapter.DbUtils;
import edu.pitt.rods.apollo.SeirModelAdapter.SeirModelAdapter;

public class WorkerThread extends Thread {

	SimulatorConfiguration sc;
	String runId;

	public WorkerThread(SimulatorConfiguration sc, String runId) {
		super();
		this.sc = sc;
		this.runId = runId;
	}

	public void run() {
		try {
			RunUtils.setStarted(runId);
			SeirModelAdapter.run(sc, runId);
			RunUtils.setFinished(runId);
		} catch (Exception e) {
			try {
				RunUtils.setError(runId, e.getMessage() + e.getStackTrace());
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

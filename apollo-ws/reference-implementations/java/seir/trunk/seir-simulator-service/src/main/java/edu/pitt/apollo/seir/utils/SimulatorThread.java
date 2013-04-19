package edu.pitt.apollo.seir.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

import edu.pitt.apollo.SeirSimulatorServiceImpl;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.rods.apollo.InvalidSimulatorConfigurationException;
import edu.pitt.rods.apollo.SeirModelAdapter.SeirModelAdapter;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Mar 21, 2013 Time:
 * 1:22:18 PM Class: SimulatorThread IDE: NetBeans 6.9.1
 */
public class SimulatorThread extends Thread {

	SimulatorConfiguration sc;
	String runId;
	String simConfigHash;
	String simConfigJson;
	boolean runSimulator = true;
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

				SeirModelAdapter.runSeirModel(sc, simConfigHash, runId,
						simConfigJson, directory, useFile, useDatabase);
			}
			RunUtils.setFinished(runIdHash);
			SeirSimulatorServiceImpl.removeRunFromQueuedList(runIdHash);

			SeirSimulatorServiceImpl.simulatorRunFinished();
			SeirSimulatorServiceImpl.runSimulatorThreads();
		} catch (NullPointerException e) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				e.printStackTrace(ps);
				String stackTrace = baos.toString("ISO-8859-1");

				RunUtils.setError(
						runIdHash,
						"Enountered unexpected null value.  If one is attached, please send the following "
								+ "stack trace to the developer of the simulator:\n"
								+ stackTrace);
				
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			try {
				RunUtils.setError(runIdHash,
						"Error creating run status file.  Specific error was:\n"
								+ e.getMessage());
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			try {
				RunUtils.setError(runIdHash,
						"Database error during simulator execution.  Specific message was:\n"
								+ e.getMessage());
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			try {
				RunUtils.setError(runIdHash,
						"Database driver not found on server.  Specific message was:\n"
								+ e.getMessage());
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (InvalidSimulatorConfigurationException e) {
			try {
				RunUtils.setError(runIdHash, e.getMessage());
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	
}
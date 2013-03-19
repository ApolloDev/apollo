package edu.pitt.apollo.seir.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;

import edu.pitt.apollo.types.BatchRunSimulatorResult;
import edu.pitt.apollo.types.SimulatorConfiguration;

public class BatchThread extends Thread {

	private String batchRunId;
	private String batchRunIdMd5Hash;
	private String batchConfigurationFileUrl;
	private BatchRunSimulatorResult result;

	public BatchThread(String batchConfigurationFile,
			BatchRunSimulatorResult result, String batchRunId,
			String batchRunIdMd5Hash) {
		this.batchRunId = batchRunId;
		this.batchConfigurationFileUrl = batchConfigurationFile;
		this.batchRunIdMd5Hash = batchRunIdMd5Hash;
		this.result = result;
	}

	public void run() {
		try {
			// mark the run as started (write a started.txt file)
			RunUtils.setStarted(batchRunIdMd5Hash);

			// determine local file to save remote config file to, e.g.
			// http://blah.com/batchConfig.json should be saved as
			// /home/apollo/34234.json <--34234.json is the localFn
			String localFn = RunUtils.getWorkDir(batchRunIdMd5Hash)
					+ batchRunIdMd5Hash;

			// downlaod the file
			org.apache.commons.io.FileUtils.copyURLToFile(new URL(
					batchConfigurationFileUrl), new File(localFn));

			// read each JSON encoded simulatorConfiguration one at a time, uses
			// Jackson
			FileInputStream fis = new FileInputStream(new File(localFn));
			for (Iterator it = new ObjectMapper().readValues(
					new JsonFactory().createJsonParser(fis),
					SimulatorConfiguration.class); it.hasNext();) {

				// it.next will return the next simulatorConfiguration
				SimulatorConfiguration sc = (SimulatorConfiguration) it.next();
				
				//here run the simulator
				System.out.println(sc.getSimulatorIdentification()
						.getSoftwareDeveloper());

				//for each simulatorConfiguration call edu.pitt.apollo.SeirSimulatorServiceImpl.runSimulation(sc)
			}
			fis.close();
			

			// SeirModelAdapter.run(sc, batchRunId);

			RunUtils.setFinished(batchRunIdMd5Hash);
		} catch (Exception e) {
			try {
				RunUtils.setError(batchRunIdMd5Hash,
						e.getMessage() + e.getStackTrace());
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

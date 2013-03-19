package edu.pitt.apollo.seir.utils;

import java.io.IOException;
import java.net.URL;

public class BatchThread extends Thread {

	private String batchRunId;
	private String batchRunIdMd5Hash;
	private String batchConfigurationFile;

	public BatchThread(String batchConfigurationFile, String batchRunId,
			String batchRunIdMd5Hash) {
		this.batchRunId = batchRunId;
		this.batchConfigurationFile = batchConfigurationFile;
		this.batchRunIdMd5Hash = batchRunIdMd5Hash;
	}

	public void run() {
		try {
		RunUtils.setStarted(batchRunIdMd5Hash);
		//dl the file!
		
		//FileUtils.downloadFile(new URL("http://i.imgur.com/XQkplNG.jpg"), RunUtils.getWorkDir(batchRunIdMd5Hash) + "dog.jpg");
		FileUtils.downloadFile(new URL(batchConfigurationFile), RunUtils.getWorkDir(batchRunIdMd5Hash) + batchRunIdMd5Hash);
		
		//run 1 at a time for now
		
		
		// SeirModelAdapter.run(sc, batchRunId);
				
		RunUtils.setFinished(batchRunIdMd5Hash);
		} catch (Exception e) {
			try {
				RunUtils.setError(batchRunIdMd5Hash, e.getMessage() + e.getStackTrace());
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}

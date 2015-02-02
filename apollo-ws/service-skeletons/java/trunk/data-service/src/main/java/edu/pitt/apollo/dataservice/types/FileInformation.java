package edu.pitt.apollo.dataservice.types;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 20, 2015
 * Time: 4:27:29 PM
 * Class: FileInformation
 */
public class FileInformation {

	private int runId;
	private String filePath;
	private String fileName;
	private String URL;

	/**
	 * @return the runId
	 */
	public int getRunId() {
		return runId;
	}

	/**
	 * @param runId the runId to set
	 */
	public void setRunId(int runId) {
		this.runId = runId;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the URL
	 */
	public String getURL() {
		return URL;
	}

	/**
	 * @param URL the URL to set
	 */
	public void setURL(String URL) {
		this.URL = URL;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}

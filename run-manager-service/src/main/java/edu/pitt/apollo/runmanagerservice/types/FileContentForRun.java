
package edu.pitt.apollo.runmanagerservice.types;

/**
 *
 * @author nem41
 */
public class FileContentForRun {
	
	private int runId;
	private String fileContent;
	private String fileName;

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
	 * @return the fileContent
	 */
	public String getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
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
